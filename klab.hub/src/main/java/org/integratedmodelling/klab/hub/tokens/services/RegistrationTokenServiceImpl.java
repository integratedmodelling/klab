package org.integratedmodelling.klab.hub.tokens.services;

import org.integratedmodelling.klab.hub.config.LinkConfig;
import org.integratedmodelling.klab.hub.repository.TokenRepository;
import org.integratedmodelling.klab.hub.tokens.AuthenticationToken;
import org.integratedmodelling.klab.hub.tokens.ClickbackToken;
import org.integratedmodelling.klab.hub.tokens.TokenType;
import org.integratedmodelling.klab.hub.tokens.commands.CreateChangePasswordToken;
import org.integratedmodelling.klab.hub.tokens.commands.CreateNewUserAccountToken;
import org.integratedmodelling.klab.hub.tokens.commands.CreateVerifyAccountToken;
import org.integratedmodelling.klab.hub.tokens.commands.DeleteAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationTokenServiceImpl implements RegistrationTokenService {
	
	private TokenRepository repository;
	private LinkConfig linkConfig;
	
	RegistrationTokenServiceImpl(TokenRepository repository,
			LinkConfig linkConfig) {
		this.repository = repository;
		this.linkConfig =  linkConfig;
	}

	@Override
	public AuthenticationToken createToken(String username, TokenType type) {
		if (type.equals(TokenType.verify)) {
			return new CreateVerifyAccountToken(repository, username, linkConfig).execute();
		} else if (type.equals(TokenType.password)) {
			return new CreateChangePasswordToken(repository, username, linkConfig).execute();
		} else {
			return null;
		}
	}

	@Override
	public AuthenticationToken createChildToken(String username, String parentToken, TokenType type) {
		if (type.equals(TokenType.newUser)) {
			return new CreateNewUserAccountToken(repository, username, linkConfig).execute();
		} else {
			return null;
		}
	}

	@Override
	public boolean verifyToken(String username, String id, TokenType type) {
		return repository.findByTokenString(id)
			.filter(token -> token.getPrincipal().equals(username))
			.map(ClickbackToken.class::cast)
			.filter(token -> token.getClickbackAction().getTokenType().equals(type))
			.map(token -> setAuthentication(token))
			.isPresent();
	}
	
	private AuthenticationToken setAuthentication(AuthenticationToken token) {
		SecurityContextHolder.getContext().setAuthentication(token);
		return token;
	}

	@Override
	public void deleteToken(String tokenString) {
		new DeleteAuthenticationToken(repository, tokenString).execute();
	}

}
