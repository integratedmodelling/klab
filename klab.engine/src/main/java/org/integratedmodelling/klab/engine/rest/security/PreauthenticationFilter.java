package org.integratedmodelling.klab.engine.rest.security;

import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.Auth;
import org.integratedmodelling.klab.utils.IPUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

public class PreauthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (auth != null) {
			// TODO return whatever, engine, session, user or anything
		}
		if (IPUtils.isLocal(request.getRemoteAddr())) {
			return Auth.LOCAL_USER_ID;
		}
		return Auth.ANONYMOUS_USER_ID;
	}

	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
		return auth == null ? "CAZZO" : auth;
	}

}
