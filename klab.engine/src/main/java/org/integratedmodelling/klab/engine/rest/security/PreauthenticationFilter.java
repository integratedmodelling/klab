package org.integratedmodelling.klab.engine.rest.security;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.KlabHttpHeaders;
import org.integratedmodelling.klab.utils.IPUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

public class PreauthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		Map<String, String> headers = Collections.list(httpRequest.getHeaderNames())
		    .stream()
		    .collect(Collectors.toMap(h -> h, httpRequest::getHeader));
		String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
		String klabAuth = request.getHeader(KlabHttpHeaders.KLAB_AUTHORIZATION);
		
		if (auth != null ) {
		    // send anything already known downstream
			if (Authentication.INSTANCE.getIdentity(auth, IIdentity.class) != null) {
			    return auth;
			}
			
		}
		
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
		Map<String, String> headers = Collections.list(request.getHeaderNames())
			    .stream()
			    .collect(Collectors.toMap(h -> h, request::getHeader));
		String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
		String klabAuth = request.getHeader(KlabHttpHeaders.KLAB_AUTHORIZATION);
		// returning null will refuse authentication
		if (auth == null && klabAuth == null) {
			return "dummycredentials";
		} else if (auth != null && klabAuth != null) {
			return klabAuth;
		} else if (auth == null && klabAuth != null) {
			return klabAuth;
		} else {
			return auth;
		}
		
	}

}
