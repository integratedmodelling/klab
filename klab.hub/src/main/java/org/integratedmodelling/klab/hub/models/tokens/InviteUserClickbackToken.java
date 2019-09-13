package org.integratedmodelling.klab.hub.models.tokens;

import org.integratedmodelling.klab.hub.config.TokenClickbackConfig;

public class InviteUserClickbackToken extends ClickbackToken{

	private static final long serialVersionUID = -7290369988203926731L;

	public InviteUserClickbackToken(String username) {
        super(username);
    }

    @Override
    public String getSuccessUrl(TokenClickbackConfig tokenClickbackConfig) {
        return null;
    }

    @Override
    public ClickbackAction getClickbackAction() {
        return ClickbackAction.invite;
    }
}
