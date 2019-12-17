package org.integratedmodelling.klab.hub.tokens.commands;

import java.util.List;

import org.integratedmodelling.klab.hub.config.LinkConfig;
import org.integratedmodelling.klab.hub.repository.TokenRepository;
import org.integratedmodelling.klab.hub.tokens.AuthenticationToken;
import org.integratedmodelling.klab.hub.tokens.VerifyAccountClickbackToken;

public class CreateVerifyAccountToken extends CreateTokenCommand{
	
	private TokenRepository tokenRepository;
	private String username;	

	public CreateVerifyAccountToken(TokenRepository tokenRepository, String username, LinkConfig linkConfig) {
		this.tokenRepository = tokenRepository;
		this.username = username;
		setLinkConfig(linkConfig);
	}

	@Override
	public VerifyAccountClickbackToken execute() {
		List<AuthenticationToken> tokens = tokenRepository.findByUsername(username);

		for(AuthenticationToken token : tokens) {
			tokenRepository.delete(token);
		}
		
		VerifyAccountClickbackToken token = new VerifyAccountClickbackToken(username);
		token.setCallbackUrl(getLinkConfig());
		token.setAuthenticated(true);
		tokenRepository.save(token);
		return token;
	}

}
