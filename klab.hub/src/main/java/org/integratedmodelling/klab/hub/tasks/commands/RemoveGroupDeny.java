package org.integratedmodelling.klab.hub.tasks.commands;

import org.integratedmodelling.klab.hub.repository.TokenRepository;
import org.integratedmodelling.klab.hub.tasks.GroupRequestTask;
import org.integratedmodelling.klab.hub.tasks.RemoveGroupTask;
import org.integratedmodelling.klab.hub.tasks.Task;
import org.integratedmodelling.klab.hub.tasks.TaskStatus;
import org.integratedmodelling.klab.hub.tokens.ClickbackToken;

public class RemoveGroupDeny implements TaskCommand {
	
	private final RemoveGroupTask task;
	
	private final TokenRepository tokenRepository;
	
	public RemoveGroupDeny(RemoveGroupTask task, TokenRepository tokenRepository) {
		this.task = task;
		this.tokenRepository = tokenRepository;
	}

	public RemoveGroupTask execute() {
		ClickbackToken token = task.getToken();
		tokenRepository.delete(token);
		task.setStatus(TaskStatus.denied);
		return task;
	}

	@Override
	public void executeAccept(Task task) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executeDeny(Task task) {
		// TODO Auto-generated method stub
		
	}

}
