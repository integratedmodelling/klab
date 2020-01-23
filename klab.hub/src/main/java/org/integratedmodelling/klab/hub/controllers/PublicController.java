package org.integratedmodelling.klab.hub.controllers;

import java.security.Principal;
import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.authentication.HubAuthenticationManager;
import org.integratedmodelling.klab.hub.authentication.GroupManager;
import org.integratedmodelling.klab.hub.network.NetworkManager;
import org.integratedmodelling.klab.rest.PingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicController {

	@Autowired
	NetworkManager networkManager;

	@Autowired
	GroupManager groupManager;

	@RequestMapping(value = API.PING, method = { RequestMethod.GET, RequestMethod.HEAD }, produces = "application/json")
	@ResponseBody
	public PingResponse ping(Principal user, HttpServletRequest request) {

		PingResponse ret = new PingResponse();
		Runtime runtime = Runtime.getRuntime();
		ret.setVersion(Version.CURRENT);
		ret.setTotalMemory(runtime.totalMemory() / 1048576);
		ret.setFreeMemory(runtime.freeMemory() / 1048576);
		ret.setProcessorCount(runtime.availableProcessors());
		ret.setRequestTime(System.currentTimeMillis());

		return ret;
	}

}
