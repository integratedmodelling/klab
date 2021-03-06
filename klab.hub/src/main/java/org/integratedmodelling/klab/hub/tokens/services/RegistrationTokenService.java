package org.integratedmodelling.klab.hub.tokens.services;

import org.integratedmodelling.klab.hub.api.TokenClickback;
import org.integratedmodelling.klab.hub.api.TokenType;
import org.springframework.stereotype.Service;

@Service
public interface RegistrationTokenService extends TokenBaseService<TokenClickback> {
	public  TokenClickback createChildToken(String username, String parentToken, TokenType verify);
	public abstract boolean verifyToken(String username, String tokenString, TokenType verify);
	public abstract boolean verifyTokens(String username, String tokenString, TokenType ...verify);
	
}
