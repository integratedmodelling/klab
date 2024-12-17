package org.integratedmodelling.klab.engine.rest.controllers.engine;

import java.security.Principal;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.Roles;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.rest.SessionReference;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller implementing the {@link org.integratedmodelling.klab.api.API.ENGINE.SESSION session API}.
 * 
 * @author ferdinando.villa
 *
 */
@RestController
@CrossOrigin(origins = "*")
@Secured(Roles.SESSION)
public class EngineSessionController {

	@RequestMapping(value = API.ENGINE.SESSION.INFO, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public SessionReference describeObservation(Principal principal) {
		ISession session = getSession(principal);
		return ((Session)session).getSessionReference();
	}

	public static ISession getSession(Principal principal) {
		if (principal instanceof PreAuthenticatedAuthenticationToken
				|| !(((PreAuthenticatedAuthenticationToken) principal).getPrincipal() instanceof ISession)) {
			return (ISession) ((PreAuthenticatedAuthenticationToken) principal).getPrincipal();
		}
		throw new IllegalStateException(
				"request was not authenticated using a session token or did not use preauthentication");
	}

}
