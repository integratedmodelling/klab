package org.integratedmodelling.klab.hub.tokens.dto;

import org.integratedmodelling.klab.hub.config.LinkConfig;
import org.integratedmodelling.klab.hub.tokens.enums.ClickbackAction;
import org.springframework.data.annotation.TypeAlias;

@TypeAlias("LostPassword")
public class TokenLostPasswordClickback extends TokenClickback{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6509338408998770751L;

	public TokenLostPasswordClickback(String username) {
		super(username);
	}
	
    @Override
    public String getSuccessUrl(LinkConfig tokenClickbackConfig) {
        return null;
    }

	@Override
	public ClickbackAction getClickbackAction() {
		return ClickbackAction.lostPassword;
	}
	

}
