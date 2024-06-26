package org.integratedmodelling.klab.hub.tokens.services;

import java.util.Optional;

import org.integratedmodelling.klab.hub.licenses.dto.MongoLever;
import org.integratedmodelling.klab.hub.repository.MongoLeverRepository;
import org.integratedmodelling.klab.hub.repository.TokenRepository;
import org.integratedmodelling.klab.hub.tokens.commands.CreateLeverAuthenticationToken;
import org.integratedmodelling.klab.hub.tokens.enums.TokenLever;
import org.integratedmodelling.klab.hub.tokens.enums.TokenType;
import org.integratedmodelling.klab.hub.tokens.exceptions.AuthenticationFailedException;
import org.springframework.stereotype.Service;

@Service
public class LeverAuthTokenServiceImpl implements LeverAuthTokenService {
	
	private MongoLeverRepository leverRepo;
	private TokenRepository tokenRepository;
	
	public LeverAuthTokenServiceImpl(MongoLeverRepository leverRepo, TokenRepository tokenRepository) {
		super();
		this.leverRepo = leverRepo;
		this.tokenRepository = tokenRepository;
	}

	@Override
	public TokenLever createToken(String name, TokenType type) {
		if(TokenType.lever == type) {
			TokenLever token = null;
			Optional<MongoLever> lever = leverRepo.findByNameIgnoreCase(name);
			if(lever.isPresent()) {
				token = new CreateLeverAuthenticationToken(lever.get(), tokenRepository).execute();
			}
			return token;
		} else {
			throw new AuthenticationFailedException("Incorrect workflow for creating token");
		}
	}

	@Override
	public void deleteToken(String tokenString) {
		// TODO Auto-generated method stub
		
	}

}
