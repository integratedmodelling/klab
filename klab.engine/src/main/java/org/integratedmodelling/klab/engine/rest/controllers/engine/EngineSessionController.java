package org.integratedmodelling.klab.engine.rest.controllers.engine;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.KlabHttpHeaders;
import org.integratedmodelling.klab.api.auth.Roles;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.engine.rest.utils.EngineHttpUtils;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.rest.SessionReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * The controller implementing the
 * {@link org.integratedmodelling.klab.api.API.ENGINE.SESSION session API}.
 * 
 * @author ferdinando.villa
 *
 */
@RestController
@CrossOrigin(origins = "*")
@Secured(Roles.SESSION)
public class EngineSessionController {

	private static final Logger logger = LoggerFactory.getLogger(EngineSessionController.class);

	@RequestMapping(value = API.ENGINE.SESSION.INFO, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public SessionReference describeObservation(Principal principal, HttpServletRequest httpRequest) {
;
		ISession session = getSession(principal);
		return ((Session) session).getSessionReference();
	}

	public static ISession getSession(Principal principal) {

		if (principal instanceof PreAuthenticatedAuthenticationToken) {
			return (ISession) ((PreAuthenticatedAuthenticationToken) principal).getPrincipal();
		}

		if (EngineHttpUtils.getCurrentHttpRequest() != null) {
			String klabAuth = EngineHttpUtils.getCurrentHttpRequest().getHeader(KlabHttpHeaders.KLAB_AUTHORIZATION);

			if (klabAuth != null) {
				// send anything already known downstream
				if (Authentication.INSTANCE.getIdentity(klabAuth, IIdentity.class) != null) {

					IIdentity identity = Authentication.INSTANCE.getIdentity(klabAuth, IIdentity.class);
					// known k.LAB identities are UserDetails and have established roles
					if (identity instanceof UserDetails) {
						return (ISession) identity;
					} else if (identity != null) {
						throw new KlabInternalErrorException(
								"internal error: non-conformant session identity in Authentication catalog! "
										+ identity);
					}
				}
			}
		}

		throw new IllegalStateException(
				"request was not authenticated using a session token or did not use preauthentication");

	}

	

}
