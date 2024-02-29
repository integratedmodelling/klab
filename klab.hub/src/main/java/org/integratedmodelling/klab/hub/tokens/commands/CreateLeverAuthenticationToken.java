package org.integratedmodelling.klab.hub.tokens.commands;

import org.integratedmodelling.klab.hub.licenses.dto.MongoLever;
import org.integratedmodelling.klab.hub.repository.TokenRepository;
import org.integratedmodelling.klab.hub.tokens.enums.TokenLever;

public class CreateLeverAuthenticationToken implements TokenCommand {
	
	private MongoLever lever;
	private TokenRepository repo;
	
	public CreateLeverAuthenticationToken(MongoLever lever, TokenRepository repo) {
		super();
		this.lever = lever;
		this.repo = repo;
	}

	@Override
	public TokenLever execute() {
		TokenLever result = new TokenLever(lever.getName());
		result.setAuthorities(lever.getAuthorities());
		result.setAuthenticated(true);
		result = repo.insert(result);
		return result;
	}

}
