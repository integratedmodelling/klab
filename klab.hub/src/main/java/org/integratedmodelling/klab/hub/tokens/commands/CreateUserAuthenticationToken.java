package org.integratedmodelling.klab.hub.tokens.commands;

import org.integratedmodelling.klab.hub.repository.TokenRepository;
import org.integratedmodelling.klab.hub.tokens.dto.TokenAuthentication;
import org.integratedmodelling.klab.hub.users.dto.User;

public class CreateUserAuthenticationToken implements TokenCommand{
	
	private User user;
	
	private TokenRepository tokenRepository;
	
	public CreateUserAuthenticationToken(TokenRepository tokenRepository, User user) {
		this.tokenRepository = tokenRepository;
		this.user = user;
	}

	@Override
	public TokenAuthentication execute() {
		TokenAuthentication result = new TokenAuthentication(user.getUsername());
		result.setAuthorities(user.getAuthorities());
		result.setAuthenticated(true);
		tokenRepository.save(result);
		return result;
	}

}
