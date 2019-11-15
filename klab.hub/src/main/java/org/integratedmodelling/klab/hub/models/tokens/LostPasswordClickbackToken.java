package org.integratedmodelling.klab.hub.models.tokens;

import org.integratedmodelling.klab.hub.config.TokenClickbackConfig;

public class LostPasswordClickbackToken extends ClickbackToken{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6509338408998770751L;

	public LostPasswordClickbackToken(String username) {
		super(username);
	}
	
    @Override
    public String getSuccessUrl(TokenClickbackConfig tokenClickbackConfig) {
        return null;
    }

	@Override
	public ClickbackAction getClickbackAction() {
		return ClickbackAction.lostPassword;
	}
	

}
