package org.integratedmodelling.klab.hub.api;

import org.integratedmodelling.klab.hub.config.LinkConfig;
import org.springframework.data.annotation.TypeAlias;

@TypeAlias("VerifyEmail")
public class TokenVerifyEmailClickback extends TokenClickback {
	
	private static final long serialVersionUID = 2577854654763037014L;
	private String newEmail;
	
	

	public TokenVerifyEmailClickback(String username, String newEmail) {
		super(username);
		this.newEmail = newEmail;
	}


	
	@Override
    public String getSuccessUrl(LinkConfig tokenClickbackConfig) {
        return null;
    }

	@Override
	public ClickbackAction getClickbackAction() {
		return ClickbackAction.changeEmail;
	}

	public String getNewEmail() {
		return newEmail;
	}	

	public void setNewEmail(String newEmail) {
		this.newEmail = newEmail;
	}
	
	
}
