package org.integratedmodelling.klab.hub.api;

import org.integratedmodelling.klab.hub.config.LinkConfig;

public class TokenInviteUserClickback extends TokenClickback{

	private static final long serialVersionUID = -7290369988203926731L;

	public TokenInviteUserClickback(String username) {
        super(username);
    }

    @Override
    public String getSuccessUrl(LinkConfig tokenClickbackConfig) {
        return null;
    }

    @Override
    public ClickbackAction getClickbackAction() {
        return ClickbackAction.invite;
    }
}
