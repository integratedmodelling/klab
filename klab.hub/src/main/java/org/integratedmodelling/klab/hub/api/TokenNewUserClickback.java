package org.integratedmodelling.klab.hub.api;

import org.integratedmodelling.klab.hub.config.LinkConfig;

public class TokenNewUserClickback extends TokenClickback {

	private static final long serialVersionUID = -6813740740798681807L;

    public TokenNewUserClickback(String username) {
		super(username);
	}

    @Override
    public String getSuccessUrl(LinkConfig tokenClickbackConfig) {
        return null;
    }

    @Override
    public ClickbackAction getClickbackAction() {
        return ClickbackAction.newUser;
    }
}