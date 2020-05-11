package org.integratedmodelling.klab.hub.api;

public class TokenVerifyEmailClickback extends TokenClickback {
	
	private static final long serialVersionUID = -9175135534782007340L;

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
