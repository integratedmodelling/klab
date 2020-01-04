package org.integratedmodelling.klab.node.controllers;

import java.security.Principal;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.node.auth.Role;
import org.integratedmodelling.klab.rest.NodeCapabilities;
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
		
		if ("*".equals(submitting)) {
			ret.setAcceptSubmission(true);
		} else if ("NONE".equals(submitting)) {
			ret.setAcceptSubmission(false);
		} else {
			/*
			 * TODO check user groups inside the value
			 */
		}
		
		// synchronized projects
		// components
		// authorities
		// online and offline resources
		// authorization to submit
		
		
		return ret;
	}
}
