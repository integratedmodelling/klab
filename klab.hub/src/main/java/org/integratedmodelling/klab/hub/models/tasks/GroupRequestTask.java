package org.integratedmodelling.klab.hub.models.tasks;

import javax.validation.constraints.NotEmpty;

import org.integratedmodelling.klab.hub.models.tokens.ClickbackToken;

public class GroupRequestTask extends Task{
	
	@NotEmpty
	ClickbackToken token;
	
    public GroupRequestTask(String requestee) {
        super(requestee);
    }
	
	public ClickbackToken getToken() {
		return token;
	}

	public void setToken(ClickbackToken token) {
		this.token = token;
	}

}
