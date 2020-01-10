package org.integratedmodelling.klab.hub.tokens.services;

import org.integratedmodelling.klab.hub.payload.LoginResponse;
import org.integratedmodelling.klab.hub.tokens.AuthenticationToken;

public interface UserAuthTokenService extends TokenBaseService {
	public AuthenticationToken getUserAuthenticationToken(String username, String password);

	public LoginResponse getAuthResponse(String username, String password);

}
