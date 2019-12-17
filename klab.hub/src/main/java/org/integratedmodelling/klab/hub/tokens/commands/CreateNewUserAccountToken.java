package org.integratedmodelling.klab.hub.tokens.commands;

import org.integratedmodelling.klab.hub.config.LinkConfig;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.repository.TokenRepository;
import org.integratedmodelling.klab.hub.tokens.NewUserClickbackToken;

public class CreateNewUserAccountToken extends CreateTokenCommand{
	
	private TokenRepository tokenRepository;
	private String username;	

	public CreateNewUserAccountToken(TokenRepository tokenRepository, String username, LinkConfig linkConfig) {
		this.tokenRepository = tokenRepository;
		this.username = username;
		setLinkConfig(linkConfig);
	}

	@Override
	public NewUserClickbackToken execute() {
		String parentTokenId = getParentTokenId(username);
		NewUserClickbackToken token = new NewUserClickbackToken(username);
		token.setParetToken(parentTokenId);
		token.setCallbackUrl(getLinkConfig());
		token.setAuthenticated(true);
		tokenRepository.save(token);
		return token;
	}
	
	private String getParentTokenId(String username) {
		return tokenRepository.findByUsernameAndClass(username, CreateVerifyAccountToken.class.getName())
			.map(token -> token.getTokenString())
			.orElseThrow(()-> new BadRequestException("Should have an activate Account clickback, but does not."));		
	}
	

}
