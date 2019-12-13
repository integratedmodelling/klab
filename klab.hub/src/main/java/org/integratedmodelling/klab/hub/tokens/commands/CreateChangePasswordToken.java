package org.integratedmodelling.klab.hub.tokens.commands;

import org.integratedmodelling.klab.hub.config.LinkConfig;
import org.integratedmodelling.klab.hub.repository.TokenRepository;
import org.integratedmodelling.klab.hub.tokens.ChangePasswordClickbackToken;

public class CreateChangePasswordToken extends CreateTokenCommand {
	
	private TokenRepository tokenRepository;
	private String username;	

	public CreateChangePasswordToken(TokenRepository tokenRepository, String username, LinkConfig linkConfig) {
		this.tokenRepository = tokenRepository;
		this.username = username;
		setLinkConfig(linkConfig);
	}

	@Override
	public ChangePasswordClickbackToken execute() {
		ChangePasswordClickbackToken token = new ChangePasswordClickbackToken(username);
		token.setCallbackUrl(getLinkConfig());
		token.setAuthenticated(true);
		tokenRepository.save(token);
		return token;
	}

}
