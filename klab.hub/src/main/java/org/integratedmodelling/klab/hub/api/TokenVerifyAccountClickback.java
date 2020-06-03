package org.integratedmodelling.klab.hub.api;

import org.integratedmodelling.klab.hub.config.LinkConfig;
import org.springframework.data.annotation.TypeAlias;

@TypeAlias("VerifyUser")
public class TokenVerifyAccountClickback extends TokenChangePasswordClickback {

    private static final long serialVersionUID = -9175135534782007340L;

    public TokenVerifyAccountClickback(String username) {
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