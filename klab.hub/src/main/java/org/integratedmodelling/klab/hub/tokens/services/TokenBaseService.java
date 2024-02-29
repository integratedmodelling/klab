package org.integratedmodelling.klab.hub.tokens.services;

import org.integratedmodelling.klab.hub.tokens.dto.TokenAuthentication;
import org.integratedmodelling.klab.hub.tokens.enums.TokenType;

public interface TokenBaseService<T extends TokenAuthentication> {
	public T createToken(String username, TokenType verify);
	public abstract void deleteToken(String tokenString);
}
