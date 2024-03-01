package org.integratedmodelling.klab.hub.tokens.dto;

import org.integratedmodelling.klab.hub.config.LinkConfig;
import org.integratedmodelling.klab.hub.tokens.enums.ClickbackAction;
import org.springframework.data.annotation.TypeAlias;

@TypeAlias("ChangePassword")
public class TokenChangePasswordClickback extends TokenClickback {

    private static final long serialVersionUID = 8432748073739754935L;

	public TokenChangePasswordClickback(String username) {
        super(username);
    }

    @Override
    public String getSuccessUrl(LinkConfig tokenClickbackConfig) {
        return null;
    }

    @Override
    public ClickbackAction getClickbackAction() {
        return ClickbackAction.password;
    }
}