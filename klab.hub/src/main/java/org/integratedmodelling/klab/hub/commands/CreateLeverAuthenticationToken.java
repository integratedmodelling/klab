package org.integratedmodelling.klab.hub.commands;

import org.integratedmodelling.klab.hub.api.MongoLever;
import org.integratedmodelling.klab.hub.api.TokenLever;
import org.integratedmodelling.klab.hub.repository.TokenRepository;

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
