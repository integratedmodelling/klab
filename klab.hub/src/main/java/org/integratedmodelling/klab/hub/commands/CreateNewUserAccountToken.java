package org.integratedmodelling.klab.hub.commands;

import org.integratedmodelling.klab.hub.api.TokenNewUserClickback;
import org.integratedmodelling.klab.hub.config.LinkConfig;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.repository.TokenRepository;

public class CreateNewUserAccountToken extends CreateTokenCommand{
	
	private TokenRepository tokenRepository;
	private String username;	

	public CreateNewUserAccountToken(TokenRepository tokenRepository, String username, LinkConfig linkConfig) {
		this.tokenRepository = tokenRepository;
		this.username = username;
		setLinkConfig(linkConfig);
	}

	@Override
	public TokenNewUserClickback execute() {
		String parentTokenId = getParentTokenId(username);
		TokenNewUserClickback token = new TokenNewUserClickback(username);
		token.setParetToken(parentTokenId);
		token.setCallbackUrl(getLinkConfig());
		token.setAuthenticated(true);
		tokenRepository.save(token);
		return token;
	}
	
	private String getParentTokenId(String username) {
		return tokenRepository.findByUsernameAndClass(username, "VerifyUser")
			.map(token -> token.getTokenString())
			.orElseThrow(()-> new BadRequestException("Should have an activate Account clickback, but does not."));		
	}
	

}
