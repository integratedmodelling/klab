package org.integratedmodelling.klab.hub.tasks;

import org.integratedmodelling.klab.hub.tokens.ClickbackToken;
import org.springframework.data.annotation.Reference;
import org.springframework.data.annotation.TypeAlias;

@TypeAlias("GroupRequestTask")
public class GroupRequestTask extends Task{

	@Reference
	ClickbackToken token;
	
    public GroupRequestTask(String requestee) {
        super(requestee, TaskType.groupRequest);
    }
	
	public ClickbackToken getToken() {
		return token;
	}

	public void setToken(ClickbackToken token) {
		this.token = token;
	}

}
