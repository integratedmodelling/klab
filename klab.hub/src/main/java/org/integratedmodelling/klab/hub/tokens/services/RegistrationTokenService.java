package org.integratedmodelling.klab.hub.tokens.services;

import org.integratedmodelling.klab.hub.tokens.dto.TokenAuthentication;
import org.integratedmodelling.klab.hub.tokens.dto.TokenClickback;
import org.integratedmodelling.klab.hub.tokens.enums.TokenType;
import org.springframework.stereotype.Service;

@Service
public interface RegistrationTokenService extends TokenBaseService<TokenClickback> {
	public  TokenClickback createChildToken(String username, String parentToken, TokenType verify);
	public abstract boolean verifyToken(String username, String tokenString, TokenType verify);
	public abstract boolean verifyTokens(String username, String tokenString, TokenType ...verify);
	public TokenClickback createToken(String username, String email, TokenType type);
	public TokenAuthentication getAndVerifyToken(String username, String id, TokenType type);
	
}
