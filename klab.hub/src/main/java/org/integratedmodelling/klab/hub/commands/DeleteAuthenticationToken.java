package org.integratedmodelling.klab.hub.commands;

import org.integratedmodelling.klab.hub.api.TokenAuthentication;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.repository.TokenRepository;

public class DeleteAuthenticationToken implements TokenCommand{

	private TokenRepository repository;
	private String tokenString;

	public DeleteAuthenticationToken(TokenRepository repository, String tokenString) {
		this.repository = repository;
		this.tokenString = tokenString;
	}

	@Override
	public TokenAuthentication execute() {
		return repository.findByTokenString(tokenString)
			.map(token -> {
				repository.delete(token);
				return token;
				})
			.orElseThrow(() -> new BadRequestException("Bad Token String"));
			
	}

}
