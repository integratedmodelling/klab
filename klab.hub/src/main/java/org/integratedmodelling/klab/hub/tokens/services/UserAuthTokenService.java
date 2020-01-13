package org.integratedmodelling.klab.hub.tokens.services;

import org.integratedmodelling.klab.hub.payload.LoginResponse;
import org.integratedmodelling.klab.hub.payload.LogoutResponse;
import org.integratedmodelling.klab.hub.tokens.AuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public interface UserAuthTokenService extends TokenBaseService {
	public AuthenticationToken getUserAuthenticationToken(String username, String password);

	public LoginResponse getAuthResponse(String username, String password);
	
	public LogoutResponse getLogoutResponse(String token);

}
