package org.integratedmodelling.klab.hub.tokens.commands;

import java.util.List;

import org.integratedmodelling.klab.hub.config.LinkConfig;
import org.integratedmodelling.klab.hub.repository.TokenRepository;
import org.integratedmodelling.klab.hub.tokens.ActivateAccountClickbackToken;
import org.integratedmodelling.klab.hub.tokens.AuthenticationToken;

public class CreateActivateAccountToken extends CreateTokenCommand{
	
	private TokenRepository tokenRepository;
	private String username;	

	public CreateActivateAccountToken(TokenRepository tokenRepository, String username, LinkConfig linkConfig) {
		this.tokenRepository = tokenRepository;
		this.username = username;
		setLinkConfig(linkConfig);
	}

	@Override
	public ActivateAccountClickbackToken execute() {
		List<AuthenticationToken> tokens = tokenRepository.findByUsername(username);

		for(AuthenticationToken token : tokens) {
			tokenRepository.delete(token);
		}
		
		ActivateAccountClickbackToken token = new ActivateAccountClickbackToken(username);
		token.setCallbackUrl(getLinkConfig());
		token.setAuthenticated(true);
		tokenRepository.save(token);
		return token;
	}

}
