package org.integratedmodelling.klab.hub.tokens.commands;

import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.repository.TokenRepository;
import org.integratedmodelling.klab.hub.tokens.dto.TokenAuthentication;

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
