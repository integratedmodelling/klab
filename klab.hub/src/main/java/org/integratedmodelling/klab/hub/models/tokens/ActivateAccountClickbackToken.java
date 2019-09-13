package org.integratedmodelling.klab.hub.models.tokens;

import org.integratedmodelling.klab.hub.config.TokenClickbackConfig;

public class ActivateAccountClickbackToken extends ChangePasswordClickbackToken {

    private static final long serialVersionUID = -9175135534782007340L;

    public ActivateAccountClickbackToken(String username) {
        super(username);
    }

    @Override
    public ClickbackAction getClickbackAction() {
        return ClickbackAction.activate;
    }

    @Override
    public String getSuccessUrl(TokenClickbackConfig tokenClickbackConfig) {
        return null;
    }
}