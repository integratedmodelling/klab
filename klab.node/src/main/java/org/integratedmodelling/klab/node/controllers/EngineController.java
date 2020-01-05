package org.integratedmodelling.klab.node.controllers;

import java.security.Principal;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.node.auth.Role;
import org.integratedmodelling.klab.rest.NodeCapabilities;
import org.integratedmodelling.klab.rest.ResourceAdapterReference;
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

	/**
	 * In a node, the capabilities endpoint is secured and the result depends on the
	 * authorized privileges.
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = API.CAPABILITIES, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public NodeCapabilities capabilities(Principal user) {

		NodeCapabilities ret = new NodeCapabilities();

		String submitting = Configuration.INSTANCE.getProperty("klab.node.submitting", "NONE");
		String searching = Configuration.INSTANCE.getProperty("klab.node.searching", "NONE");

		for (ResourceAdapterReference adapter : Resources.INSTANCE.describeResourceAdapters()) {
			// check if the adapter is authorized for this user
			String authorized = Configuration.INSTANCE
					.getProperty("klab.adapter." + adapter.getName().toLowerCase() + ".auth", "NONE");
			if (isAuthorized(user, authorized)) {
				ret.getResourceAdapters().add(adapter);
			}
		}

		ret.setAcceptSubmission(isAuthorized(user, submitting));
		ret.setAcceptQueries(isAuthorized(user, searching));

		// synchronized projects
		// components
		// authorities
		// online and offline resources
		// authorization to submit

		return ret;
	}

	private boolean isAuthorized(Principal user, String permissions) {
		if ("*".equals(permissions)) {
			return true;
		} else if ("NONE".equals(permissions)) {
			return false;
		} else {
			// TODO check groups
		}
		return false;
	}
}
