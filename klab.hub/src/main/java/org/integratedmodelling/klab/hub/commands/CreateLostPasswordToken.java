package org.integratedmodelling.klab.hub.commands;

import org.integratedmodelling.klab.hub.api.TokenLostPasswordClickback;
import org.integratedmodelling.klab.hub.config.LinkConfig;
import org.integratedmodelling.klab.hub.repository.TokenRepository;

public class CreateLostPasswordToken extends CreateTokenCommand {
	
	private TokenRepository tokenRepository;
	private String username;
	
	public CreateLostPasswordToken(TokenRepository tokenRepository, String username, LinkConfig linkConfig) {
		this.tokenRepository = tokenRepository;
		this.username = username;
		setLinkConfig(linkConfig);
	}

	@Override
	public  TokenLostPasswordClickback execute() {
		TokenLostPasswordClickback token = new TokenLostPasswordClickback(username);
		token.setCallbackUrl(getLinkConfig());
		token.setAuthenticated(true);
		tokenRepository.save(token);
		return token;
	}

}
