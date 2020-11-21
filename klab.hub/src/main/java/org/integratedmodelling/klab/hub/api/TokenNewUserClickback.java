package org.integratedmodelling.klab.hub.api;

import org.integratedmodelling.klab.hub.config.LinkConfig;
import org.springframework.data.annotation.TypeAlias;

@TypeAlias("NewUser")
public class TokenNewUserClickback extends TokenClickback {

	private static final long serialVersionUID = -6237803138942268818L;

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