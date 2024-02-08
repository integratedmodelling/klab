package org.integratedmodelling.klab.hub.api;

import org.integratedmodelling.klab.hub.commands.CreateTokenCommand;
import org.integratedmodelling.klab.hub.config.LinkConfig;
import org.integratedmodelling.klab.hub.repository.TokenRepository;

public class CreateVerifyEmailToken extends CreateTokenCommand {

	private TokenRepository tokenRepository;
	private String username;

	public CreateVerifyEmailToken(TokenRepository tokenRepository, String username,
			LinkConfig linkConfig) {
		this.tokenRepository = tokenRepository;
		this.username = username;
		setLinkConfig(linkConfig);
	}

	@Override
	public TokenVerifyEmailClickback execute() {

		TokenVerifyEmailClickback token = new TokenVerifyEmailClickback(username);
		token.setCallbackUrl(getLinkConfig());
		token.setAuthenticated(true);
		tokenRepository.save(token);
		return token;

	}
}