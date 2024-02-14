package org.integratedmodelling.klab.hub.tokens.services;

import org.integratedmodelling.klab.hub.api.TokenAuthentication;
import org.integratedmodelling.klab.hub.payload.LoginResponse;
import org.integratedmodelling.klab.hub.payload.LogoutResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserAuthTokenService extends TokenBaseService<TokenAuthentication> {
	public TokenAuthentication getUserAuthenticationToken(String username, String password);

	public LoginResponse<?> getAuthResponse(String username, String password, boolean jwtToken);
	
	public LogoutResponse getLogoutResponse(String token);

}
