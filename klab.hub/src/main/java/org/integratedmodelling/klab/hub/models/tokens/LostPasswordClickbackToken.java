package org.integratedmodelling.klab.hub.models.tokens;

public class LostPasswordClickbackToken extends ClickbackToken{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6509338408998770751L;

	public LostPasswordClickbackToken(String username) {
		super(username);
	}

	@Override
	public ClickbackAction getClickbackAction() {
		return ClickbackAction.lostPassword;
	}
	

}
