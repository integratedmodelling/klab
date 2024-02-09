package org.integratedmodelling.klab.hub.api;

import org.integratedmodelling.klab.hub.config.LinkConfig;
import org.springframework.data.annotation.TypeAlias;

@TypeAlias("VerifyEmail")
public class TokenVerifyEmailClickback extends TokenClickback {
	
	private static final long serialVersionUID = 2577854654763037014L;
	private String notVerifiedEmail;
	
	

	public TokenVerifyEmailClickback(String username, String notVerifiedEmail) {
		super(username);
		this.notVerifiedEmail = notVerifiedEmail;
	}


	
	@Override
    public String getSuccessUrl(LinkConfig tokenClickbackConfig) {
        return null;
    }

	@Override
	public ClickbackAction getClickbackAction() {
		return ClickbackAction.changeEmail;
	}

	public String getNotVerifiedEmail() {
		return notVerifiedEmail;
	}	

	public void setNotVerifiedEmail(String notVerifiedEmail) {
		this.notVerifiedEmail = notVerifiedEmail;
	}
	
	
}
