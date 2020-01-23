package org.integratedmodelling.klab.hub.tokens.commands;

import org.integratedmodelling.klab.hub.tokens.AuthenticationToken;

public interface TokenCommand {
	
	public AuthenticationToken execute();

}
