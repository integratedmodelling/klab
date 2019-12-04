package org.integratedmodelling.klab.hub.models.tokens;

import org.integratedmodelling.klab.hub.config.LinkConfig;

public class NewUserClickbackToken extends ClickbackToken {

	private static final long serialVersionUID = -6813740740798681807L;

    public NewUserClickbackToken(String username) {
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