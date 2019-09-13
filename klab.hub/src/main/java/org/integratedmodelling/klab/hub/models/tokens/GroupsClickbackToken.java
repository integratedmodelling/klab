package org.integratedmodelling.klab.hub.models.tokens;

import org.integratedmodelling.klab.hub.config.TokenClickbackConfig;

public class GroupsClickbackToken extends ClickbackToken {

    private static final long serialVersionUID = -6813740740798681807L;

	public GroupsClickbackToken(String username) {
        super(username);
    }

    @Override
    public String getSuccessUrl(TokenClickbackConfig tokenClickbackConfig) {
        return null;
    }

    @Override
    public ClickbackAction getClickbackAction() {
        return ClickbackAction.groups;
    }

}