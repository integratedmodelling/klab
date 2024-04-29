package org.integratedmodelling.klab.hub.tokens.commands;

import org.integratedmodelling.klab.hub.tokens.dto.TokenAuthentication;

public interface TokenCommand {
	
	public TokenAuthentication execute();

}
