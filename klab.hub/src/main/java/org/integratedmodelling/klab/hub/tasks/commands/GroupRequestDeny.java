package org.integratedmodelling.klab.hub.tasks.commands;

import org.integratedmodelling.klab.hub.models.tokens.ClickbackToken;
import org.integratedmodelling.klab.hub.repository.TokenRepository;
import org.integratedmodelling.klab.hub.tasks.GroupRequestTask;
import org.integratedmodelling.klab.hub.tasks.TaskStatus;

public class GroupRequestDeny implements TaskCommand {
	
	private final GroupRequestTask task;
	
	private final TokenRepository tokenRepository;
	
	public GroupRequestDeny(GroupRequestTask task, TokenRepository tokenRepository) {
		this.task = task;
		this.tokenRepository = tokenRepository;
	}

	@Override
	public GroupRequestTask execute() {
		ClickbackToken token = task.getToken();
		tokenRepository.delete(token);
		task.setStatus(TaskStatus.denied);
		return task;
	}

}
