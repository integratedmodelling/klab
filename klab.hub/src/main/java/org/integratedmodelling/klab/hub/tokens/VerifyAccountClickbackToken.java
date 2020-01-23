package org.integratedmodelling.klab.hub.tokens;

import org.integratedmodelling.klab.hub.config.LinkConfig;

public class VerifyAccountClickbackToken extends ChangePasswordClickbackToken {

    private static final long serialVersionUID = -9175135534782007340L;

    public VerifyAccountClickbackToken(String username) {
        super(username);
    }

    @Override
    public ClickbackAction getClickbackAction() {
        return ClickbackAction.activate;
    }

    @Override
    public String getSuccessUrl(LinkConfig tokenClickbackConfig) {
        return null;
    }
}