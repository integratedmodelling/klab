package org.integratedmodelling.klab.hub.tasks;

import org.integratedmodelling.klab.hub.tokens.ClickbackToken;
import org.springframework.data.annotation.Reference;

public class RemoveGroupTask extends Task{
	
	@Reference
	ClickbackToken token;

	public RemoveGroupTask(String requestee) {
		super(requestee, TaskType.removeGroupRequest);
	}
	
	public ClickbackToken getToken() {
		return token;
	}

	public void setToken(ClickbackToken token) {
		this.token = token;
	}

}
