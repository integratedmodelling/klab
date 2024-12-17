package org.integratedmodelling.klab.engine.rest.security;

import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.utils.IPUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

public class PreauthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (auth != null) {
		    // send anything already known downstream
			if (Authentication.INSTANCE.getIdentity(auth, IIdentity.class) != null) {
			    return auth;
			}
			return null;
		}
		if (IPUtils.isLocal(request.getRemoteAddr())) {
			return Authentication.LOCAL_USER_ID;
		}
		return Authentication.ANONYMOUS_USER_ID;
	}

	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
		// returning null will refuse authentication
		return auth == null ? "dummycredentials" : auth;
	}

}
