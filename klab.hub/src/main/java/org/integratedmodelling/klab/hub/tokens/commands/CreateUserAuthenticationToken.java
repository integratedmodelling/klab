package org.integratedmodelling.klab.hub.tokens.commands;

import org.integratedmodelling.klab.hub.repository.TokenRepository;
import org.integratedmodelling.klab.hub.tokens.AuthenticationToken;
import org.integratedmodelling.klab.hub.users.User;

public class CreateUserAuthenticationToken implements TokenCommand{
	
	private User user;
	
	private TokenRepository tokenRepository;
	
	public CreateUserAuthenticationToken(TokenRepository tokenRepository, User user) {
		this.tokenRepository = tokenRepository;
		this.user = user;
	}

	@Override
	public AuthenticationToken execute() {
		AuthenticationToken result = new AuthenticationToken(user.getUsername());
		result.setAuthorities(user.getAuthorities());
		result.setAuthenticated(true);
		tokenRepository.save(result);
		return result;
	}

}
