package org.integratedmodelling.klab.node.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.KlabPermissions;
import org.integratedmodelling.klab.api.data.adapters.IUrnAdapter;
import org.integratedmodelling.klab.node.auth.EngineAuthorization;
import org.integratedmodelling.klab.node.auth.Role;
import org.integratedmodelling.klab.node.resources.ResourceManager;
import org.integratedmodelling.klab.rest.NodeCapabilities;
import org.integratedmodelling.klab.rest.ResourceAdapterReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoints used by engines to inquire about a node's capabilities and
 * offerings.
 * 
 * @author ferdinando.villa
 *
 */
@RestController
@Secured(Role.ENGINE)
public class EngineController {

	@Autowired
	ResourceManager resourceManager;

	/**
	 * In a node, the capabilities endpoint is secured and the result depends on the
	 * authorized privileges.
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = API.CAPABILITIES, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public NodeCapabilities capabilities(EngineAuthorization user) {

		NodeCapabilities ret = new NodeCapabilities();

		String submitting = Configuration.INSTANCE.getProperty("klab.node.submitting", "NONE");
		String searching = Configuration.INSTANCE.getProperty("klab.node.searching", "NONE");
		List<String> universalResourceUrns = new ArrayList<>();
		
		for (ResourceAdapterReference adapter : Resources.INSTANCE.describeResourceAdapters()) {
			// check if the adapter is authorized for this user
			String authorized = Configuration.INSTANCE
					.getProperty("klab.adapter." + adapter.getName().toLowerCase() + ".auth", "");
			if (isAuthorized(user, authorized)) {
				ret.getResourceAdapters().add(adapter);
				if (adapter.isUniversal()) {
					IUrnAdapter uad = Resources.INSTANCE.getUrnAdapter(adapter.getName());
					universalResourceUrns.addAll(uad.getResourceUrns());
				}
			}
		}

		ret.setAcceptSubmission(isAuthorized(user, submitting));
		ret.setAcceptQueries(isAuthorized(user, searching));
		ret.getResourceCatalogs().addAll(resourceManager.getCatalogs());
		ret.getResourceNamespaces().addAll(resourceManager.getNamespaces());
		ret.getResourceCatalogs().add(resourceManager.getDefaultCatalog());
		ret.getResourceNamespaces().add(resourceManager.getDefaultNamespace());

		for (String urn : resourceManager.getOnlineResources()) {
			if (resourceManager.canAccess(urn, (EngineAuthorization) user)) {
				ret.getResourceUrns().add(urn);
			}
		}
		
		ret.getResourceUrns().addAll(universalResourceUrns);
		
		return ret;
	}

	public static boolean isAuthorized(EngineAuthorization user, String permissions) {

		if ("*".equals(permissions)) {
			return true;
		} else if ("NONE".equals(permissions)) {
			return false;
		} else {
			KlabPermissions perms = KlabPermissions.create(permissions);
			Collection<String> groups = new ArrayList<>();
			user.getGroups().forEach(g -> groups.add(g.getId()));
			return perms.isAuthorized(user.getUsername(), groups);
		}
	}
}
