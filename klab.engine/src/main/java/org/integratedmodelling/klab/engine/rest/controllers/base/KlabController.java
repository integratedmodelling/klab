package org.integratedmodelling.klab.engine.rest.controllers.base;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.Roles;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.rest.Capabilities;
import org.integratedmodelling.klab.rest.PingResponse;
import org.integratedmodelling.klab.utils.IPUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The root controller for the {@link API base API}. All endpoints are public
 * but the response depends on the privileges of the logged in user, remapped to
 * anonymous (or local anonymous) if accessed without authentication.
 * 
 * @author ferdinando.villa
 *
 */
@RestController
@Secured(Roles.PUBLIC)
public class KlabController {

	@RequestMapping(value = API.CAPABILITIES, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Capabilities capabilities(Principal user) {
		return Klab.INSTANCE.getCapabilities(Authentication.INSTANCE.getIdentity(user));
	}

	@RequestMapping(value = API.SCHEMA, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String resourceSchemata() {
		return Klab.INSTANCE.getResourceSchema();
	}

	@RequestMapping(value = API.SCHEMA, params = "resource", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String resourceSchema(@RequestParam("resource") String what) {
		return Klab.INSTANCE.getResourceSchema(what);
	}

	@RequestMapping(value = API.SCHEMA, params = "list", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String resourceSchema() {
		return Klab.INSTANCE.getResourceSchema("all");
	}

	@RequestMapping(value = API.PING, method = { RequestMethod.GET, RequestMethod.HEAD }, produces = "application/json")
	@ResponseBody
	public PingResponse ping(Principal user, HttpServletRequest request) {

		PingResponse ret = new PingResponse();
		Engine engine = Authentication.INSTANCE.getAuthenticatedIdentity(Engine.class);
		ret.setOnline(engine != null);
		if (engine != null) {
			ret.setUptime(System.currentTimeMillis() - engine.getBootTime().getTime());
			ret.setEngineId(engine.getId());
			if (IPUtils.isLocal(request.getRemoteAddr())) {
				ISession session = Authentication.INSTANCE.getDefaultSession();
				if (session != null) {
					ret.setLocalSessionId(session.getId());
				}
			}
		}

		return ret;
	}

}
