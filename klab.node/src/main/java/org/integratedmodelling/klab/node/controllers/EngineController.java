package org.integratedmodelling.klab.node.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.KlabPermissions;
import org.integratedmodelling.klab.api.data.adapters.IUrnAdapter;
import org.integratedmodelling.klab.auth.Role;
import org.integratedmodelling.klab.node.Node;
import org.integratedmodelling.klab.node.auth.EngineAuthorization;
import org.integratedmodelling.klab.node.resources.ResourceManager;
import org.integratedmodelling.klab.rest.Group;
import org.integratedmodelling.klab.rest.NodeCapabilities;
import org.integratedmodelling.klab.rest.ResourceAdapterReference;
import org.springframework.beans.factory.annotation.Autowired;
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
public class EngineController {

	@Autowired
	ResourceManager resourceManager;

	@RequestMapping(value = API.NODE.WHO, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<?, ?> getUserDetails(Principal user) {

		EngineAuthorization u = (EngineAuthorization) user;
		Map<String, Object> ret = new HashMap<>();
		ret.put("username", u.getName());
		List<String> roles = new ArrayList<>();
		List<String> groups = new ArrayList<>();
		for (Role role : u.getRoles()) {
			roles.add(role.name());
		}
		for (Group group : u.getGroups()) {
			groups.add(group.getName());
		}

		ret.put("roles", roles);
		ret.put("groups", groups);

		/*
		 * TODO access details, other users if requested and admin
		 */

		return ret;
	}

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

		ret.setVersion(Version.CURRENT);
		ret.setBuild(Version.VERSION_BUILD + " "
				+ (Version.VERSION_COMMIT == null ? ":unknown" : (Version.VERSION_COMMIT.substring(0, 7))));

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

		/**
		 * Temporarily stick the time since boot in here instead of adding a proper
		 * field, in fear that this would kill the entire k.LAB deployed network.
		 */
		ret.setRefreshFrequencyMillis(System.currentTimeMillis() - Node.getBootTime());

		ret.getResourceUrns().addAll(universalResourceUrns);
		ret.setOnline(true);

		return ret;
	}

	public static boolean isAuthorized(EngineAuthorization user, String permissions) {

		if (user == null) {
			return false;
		}

		if ("*".equals(permissions)) {
			return true;
		} else if ("NONE".equals(permissions)) {
			return false;
		} else {
			KlabPermissions perms = KlabPermissions.create(permissions);
			Collection<String> groups = new ArrayList<>();
			user.getGroups().forEach(g -> groups.add(g.getName()));
			return perms.isAuthorized(user.getUsername(), groups);
		}
	}

}
