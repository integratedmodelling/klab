package org.integratedmodelling.klab.hub.tokens.services;

import org.integratedmodelling.klab.hub.api.TokenType;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public interface TokenBaseService<T extends AbstractAuthenticationToken> {
	public T createToken(String username, TokenType verify);
	public abstract void deleteToken(String tokenString);
}
