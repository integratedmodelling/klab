package org.integratedmodelling.klab.hub.api;

import org.springframework.data.annotation.TypeAlias;

@TypeAlias("VerifyEmail")
public class TokenVerifyEmailClickback extends TokenClickback {
	
	private static final long serialVersionUID = 2577854654763037014L;

	public TokenVerifyEmailClickback(String username) {
		super(username);
	}

    public void setNewEmailAddress(String newEmailAddress) {
    }

	@Override
	public ClickbackAction getClickbackAction() {
		return null;
	}
	
}
