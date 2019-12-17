package org.integratedmodelling.klab.hub.tokens.services;

import org.integratedmodelling.klab.hub.tokens.AuthenticationToken;
import org.integratedmodelling.klab.hub.tokens.ClickbackToken;
import org.integratedmodelling.klab.hub.tokens.TokenType;

public interface TokenBaseService {
	public AuthenticationToken createToken(String username, TokenType verify);
	public AuthenticationToken createChildToken(String username, String parentToken, TokenType verify);
	public abstract boolean verifyToken(String username, String tokenString, TokenType verify);
	public abstract void deleteToken(String tokenString);
}
