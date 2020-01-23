package org.integratedmodelling.klab.hub.tokens;

public class VerifyEmailClickbackToken extends ClickbackToken {
	
	private static final long serialVersionUID = -9175135534782007340L;

	public VerifyEmailClickbackToken(String username) {
		super(username);
	}

    public void setNewEmailAddress(String newEmailAddress) {
    }

	@Override
	public ClickbackAction getClickbackAction() {
		return null;
	}
	
}
