package org.integratedmodelling.klab.hub.tasks;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.hub.tokens.ClickbackToken;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Component;

public class RemoveGroupTask extends Task{
	
	@Reference
	ClickbackToken token;
	
	@Component
	public static class Builder extends TaskBuilder {

		@Override
		public List<Task> build(TaskParameters parameters) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	@Component
	public static class Command extends TaskCommand {}

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
