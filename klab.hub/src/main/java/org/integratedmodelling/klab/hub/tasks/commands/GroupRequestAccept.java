package org.integratedmodelling.klab.hub.tasks.commands;

import java.util.Set;
import java.util.stream.Collectors;

import org.integratedmodelling.klab.hub.models.GroupEntry;
import org.integratedmodelling.klab.hub.models.tokens.GroupsClickbackToken;
import org.integratedmodelling.klab.hub.repository.TokenRepository;
import org.integratedmodelling.klab.hub.service.UserService;
import org.integratedmodelling.klab.hub.tasks.GroupRequestTask;
import org.integratedmodelling.klab.hub.tasks.TaskStatus;

public class GroupRequestAccept implements TaskCommand{
	
	private final GroupRequestTask task;
	
	private final UserService userService;
	
	private final TokenRepository tokenRepository;
	
	public GroupRequestAccept(GroupRequestTask task, 
			UserService userService,
			TokenRepository tokenRepository	) {
		this.task = task;
		this.userService = userService;
		this.tokenRepository = tokenRepository;
	}

	@Override
	public GroupRequestTask execute() {
		GroupsClickbackToken token = (GroupsClickbackToken) task.getToken();
		Set<GroupEntry> groups = token.getGroups().stream().collect(Collectors.toSet());
		userService.addUserGroupEntries(token.getName(), groups);
		tokenRepository.delete(token);
		task.setStatus(TaskStatus.acceptedTask);
		return task;
	}
}
