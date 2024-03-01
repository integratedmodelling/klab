package org.integratedmodelling.klab.hub.commands;

import org.integratedmodelling.klab.hub.config.LinkConfig;

public abstract class CreateTokenCommand implements TokenCommand {
	
	private LinkConfig linkConfig;

	public CreateTokenCommand() {
	}

	public LinkConfig getLinkConfig() {
		return linkConfig;
	}

	public CreateTokenCommand setLinkConfig(LinkConfig linkConfig) {
		this.linkConfig = linkConfig;
		return this;
	}
}
