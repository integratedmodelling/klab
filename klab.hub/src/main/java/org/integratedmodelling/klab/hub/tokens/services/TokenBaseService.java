package org.integratedmodelling.klab.hub.tokens.services;

import org.integratedmodelling.klab.hub.api.TokenAuthentication;
import org.integratedmodelling.klab.hub.api.TokenType;

public interface TokenBaseService {
	public TokenAuthentication createToken(String username, TokenType verify);
	public TokenAuthentication createChildToken(String username, String parentToken, TokenType verify);
	public abstract boolean verifyToken(String username, String tokenString, TokenType verify);
	public abstract boolean verifyTokens(String username, String tokenString, TokenType ...verify);
	public abstract void deleteToken(String tokenString);
}
