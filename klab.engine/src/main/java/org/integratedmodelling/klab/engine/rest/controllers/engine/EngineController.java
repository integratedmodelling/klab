package org.integratedmodelling.klab.engine.rest.controllers.engine;

import java.security.Principal;

import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.auth.Roles;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.runtime.SessionState;
import org.integratedmodelling.klab.rest.AuthorizeSessionResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller implementing the
 * {@link org.integratedmodelling.klab.api.API.ENGINE engine API}. These use the
 * engine to create sessions and are available to all users of the engine.
 * 
 * @author ferdinando.villa
 *
 */
@RestController
@Secured({ Roles.ENGINE_USER, Roles.ADMIN })
public class EngineController {

	@RequestMapping(value = API.ENGINE.SESSION.AUTHORIZE, params = { "join",
			"relay" }, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public AuthorizeSessionResponse openSession(@RequestParam(name = "join") String previousToJoin,
			@RequestParam(name = "relay", required = false) String relayId, Principal principal,
			@RequestHeader(HttpHeaders.USER_AGENT) String agent) {

		IIdentity user = Authentication.INSTANCE.getIdentity(principal);
		Engine engine = Authentication.INSTANCE.getAuthenticatedIdentity(Engine.class);
		if (engine == null) {
			throw new IllegalAccessError("engine is not present: cannot create a session");
		}

		String info = null;
		ISession session = Authentication.INSTANCE.getIdentity(previousToJoin, ISession.class);
		if (session == null && user instanceof IUserIdentity) {
			session = engine.createSession((IUserIdentity) user);
			info = "requested session was unavailable: returning a new session";
		}

		if (relayId != null) {
			((Session) session).addRelayId(relayId);
		}

		if (agent != null && agent.startsWith("k.LAB/")) {
			((SessionState)session.getState()).setApplicationName("k.Modeler");
		}

		AuthorizeSessionResponse ret = new AuthorizeSessionResponse();
		ret.setInfo(info);
		ret.setSessionId(session.getId());
		return ret;
	}

	@RequestMapping(value = API.ENGINE.SESSION.AUTHORIZE, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public AuthorizeSessionResponse createSession(Principal principal,
			@RequestHeader(HttpHeaders.USER_AGENT) String agent) {

		ISession session = null;
		Engine engine = Authentication.INSTANCE.getAuthenticatedIdentity(Engine.class);
		IIdentity user = Authentication.INSTANCE.getIdentity(principal);

		if (engine == null) {
			throw new IllegalAccessError("engine is not present: cannot create a session");
		}

		session = user instanceof IUserIdentity ? engine.createSession((IUserIdentity) user) : engine.createSession();
		
		if (agent != null && agent.startsWith("k.LAB/")) {
			((SessionState)session.getState()).setApplicationName("k.Modeler");
		}
		
		AuthorizeSessionResponse ret = new AuthorizeSessionResponse();

		ret.setSessionId(session.getId());
		// TODO the rest!

		return ret;
	}
}
