package org.integratedmodelling.klab.hub.commands;

import java.util.List;

import org.integratedmodelling.klab.hub.api.TokenAuthentication;
import org.integratedmodelling.klab.hub.api.TokenVerifyAccountClickback;
import org.integratedmodelling.klab.hub.config.LinkConfig;
import org.integratedmodelling.klab.hub.repository.TokenRepository;

public class CreateVerifyAccountToken extends CreateTokenCommand{
	
	private TokenRepository tokenRepository;
	private String username;	

	public CreateVerifyAccountToken(TokenRepository tokenRepository, String username, LinkConfig linkConfig) {
		this.tokenRepository = tokenRepository;
		this.username = username;
		setLinkConfig(linkConfig);
	}

	@Override
	public TokenVerifyAccountClickback execute() {
		List<TokenAuthentication> tokens = tokenRepository.findByUsername(username);

		for(TokenAuthentication token : tokens) {
			tokenRepository.delete(token);
		}
		
		TokenVerifyAccountClickback token = new TokenVerifyAccountClickback(username);
		token.setCallbackUrl(getLinkConfig());
		token.setAuthenticated(true);
		tokenRepository.save(token);
		return token;
	}

}
