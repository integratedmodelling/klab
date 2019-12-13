package org.integratedmodelling.klab.hub.tokens.services;

import org.integratedmodelling.klab.hub.tokens.AuthenticationToken;
import org.integratedmodelling.klab.hub.tokens.ClickbackToken;

public interface TokenBaseService {
	public AuthenticationToken createToken(String username, Class<? extends ClickbackToken> tokenType);
	public AuthenticationToken createChildToken(String username, String parentToken, Class<? extends ClickbackToken> tokenType);
	public abstract boolean verifyToken(String username, String tokenString);
}
