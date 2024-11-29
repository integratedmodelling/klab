package org.integratedmodelling.klab.engine.rest.security;

import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.KlabHttpHeaders;
import org.integratedmodelling.klab.utils.IPUtils;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

public class PreauthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {

		String klabAuth = request.getHeader(KlabHttpHeaders.KLAB_AUTHORIZATION);
		
		if (klabAuth != null ) {
		    // send anything already known downstream
			if (Authentication.INSTANCE.getIdentity(klabAuth, IIdentity.class) != null) {
			    return klabAuth;
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

		String klabAuth = request.getHeader(KlabHttpHeaders.KLAB_AUTHORIZATION);
		// returning null will refuse authentication
		if (klabAuth == null) {
			return "dummycredentials";
		} else {
			return klabAuth;
		}
		
	}

}
