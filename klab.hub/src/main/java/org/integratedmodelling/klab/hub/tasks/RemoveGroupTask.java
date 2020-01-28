package org.integratedmodelling.klab.hub.tasks;

import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.hub.tokens.ClickbackToken;
import org.springframework.data.annotation.Reference;

public class RemoveGroupTask extends Task{
	
	@Reference
	ClickbackToken token;

	public RemoveGroupTask(String requestee) {
		super(requestee);
	}
	
	public ClickbackToken getToken() {
		return token;
	}

	public void setToken(ClickbackToken token) {
		this.token = token;
	}

	@Override
	public void acceptTaskAction(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void denyTaskAction(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

}
