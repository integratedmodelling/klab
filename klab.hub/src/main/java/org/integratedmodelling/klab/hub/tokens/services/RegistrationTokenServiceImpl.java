package org.integratedmodelling.klab.hub.tokens.services;

import org.integratedmodelling.klab.hub.config.LinkConfig;
import org.integratedmodelling.klab.hub.repository.TokenRepository;
import org.integratedmodelling.klab.hub.tokens.ActivateAccountClickbackToken;
import org.integratedmodelling.klab.hub.tokens.AuthenticationToken;
import org.integratedmodelling.klab.hub.tokens.ChangePasswordClickbackToken;
import org.integratedmodelling.klab.hub.tokens.ClickbackAction;
import org.integratedmodelling.klab.hub.tokens.ClickbackToken;
import org.integratedmodelling.klab.hub.tokens.NewUserClickbackToken;
import org.integratedmodelling.klab.hub.tokens.commands.CreateActivateAccountToken;
import org.integratedmodelling.klab.hub.tokens.commands.CreateChangePasswordToken;
import org.integratedmodelling.klab.hub.tokens.commands.CreateNewUserAccountToken;
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
	public AuthenticationToken createToken(String username, Class<? extends ClickbackToken> tokenType) {
		if (tokenType.equals(ActivateAccountClickbackToken.class)) {
			return new CreateActivateAccountToken(repository, username, linkConfig).execute();
		} else if (tokenType.equals(ChangePasswordClickbackToken.class)) {
			return new CreateChangePasswordToken(repository, username, linkConfig).execute();
		} else {
			return null;
		}
	}

	@Override
	public AuthenticationToken createChildToken(String username, String parentToken,
			Class<? extends ClickbackToken> tokenType) {
		if (tokenType.equals(NewUserClickbackToken.class)) {
			return new CreateNewUserAccountToken(repository, username, linkConfig).execute();
		} else {
			return null;
		}
	}

	@Override
	public boolean verifyToken(String username, String id) {
		return repository.findByTokenString(id)
			.filter(token -> token.getPrincipal().equals(username))
			.filter(token -> token.getClass().equals(ActivateAccountClickbackToken.class))
			.map(ActivateAccountClickbackToken.class::cast)
			.filter(token -> token.getClickbackAction().equals(ClickbackAction.activate))
			.map(token -> setAuthentication(token))
			.isPresent();
	}
	
	private AuthenticationToken setAuthentication(AuthenticationToken token) {
		SecurityContextHolder.getContext().setAuthentication(token);
		return token;
	}

}
