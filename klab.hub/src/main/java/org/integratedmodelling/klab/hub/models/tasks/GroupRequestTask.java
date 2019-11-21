package org.integratedmodelling.klab.hub.models.tasks;

import javax.validation.constraints.NotEmpty;

import org.integratedmodelling.klab.hub.models.tokens.ClickbackToken;
import org.springframework.data.annotation.TypeAlias;

@TypeAlias("GroupRequestTask")
public class GroupRequestTask extends Task{
	
	@NotEmpty
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
