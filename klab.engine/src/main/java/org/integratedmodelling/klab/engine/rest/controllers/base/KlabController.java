package org.integratedmodelling.klab.engine.rest.controllers.base;

import java.security.Principal;

import org.integratedmodelling.klab.API;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.auth.Roles;
import org.integratedmodelling.klab.data.rest.resources.responses.Capabilities;
import org.integratedmodelling.klab.engine.Engine;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
		return Klab.INSTANCE.getCapabilities();
	}

	@RequestMapping(value = API.PING, method = {RequestMethod.GET, RequestMethod.HEAD}, produces="text/plain")
	public String ping() {
	    Engine engine = Klab.INSTANCE.getRootMonitor().getIdentity().getParentIdentity(Engine.class);
	    if (engine == null) {
	        return "0";
	    }
	    return "" + (System.currentTimeMillis() - engine.getBootTime().getTime());
	}
	
}
