package org.integratedmodelling.klab.hub.tasks.commands;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.integratedmodelling.klab.hub.repository.TokenRepository;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.tasks.GroupRequestTask;
import org.integratedmodelling.klab.hub.tasks.RemoveGroupTask;
import org.integratedmodelling.klab.hub.tasks.Task;
import org.integratedmodelling.klab.hub.tasks.TaskCommand;
import org.integratedmodelling.klab.hub.tasks.TaskStatus;
import org.integratedmodelling.klab.hub.tokens.GroupsClickbackToken;
import org.integratedmodelling.klab.hub.users.GroupEntry;
import org.integratedmodelling.klab.hub.users.User;

public class RemoveGroupAccept implements TaskCommand{
	
	private final RemoveGroupTask task;
	
	private final UserRepository userRepository;
	
	private final TokenRepository tokenRepository;
	
	public RemoveGroupAccept(RemoveGroupTask task, 
			UserRepository userRepository,
			TokenRepository tokenRepository	) {
		this.task = task;
		this.userRepository = userRepository;
		this.tokenRepository = tokenRepository;
	}

	
	public RemoveGroupTask execute() {
		GroupsClickbackToken token = (GroupsClickbackToken) task.getToken();
		Set<GroupEntry> removeGroupEntries = token.getGroups().stream().collect(Collectors.toSet());
		User user = removeUserGroupEntries(task.getRequestee(), removeGroupEntries);
		userRepository.save(user);
		tokenRepository.delete(token);
		task.setStatus(TaskStatus.accepted);
		return task;
	}

	
	private User removeUserGroupEntries(String username, Set<GroupEntry> removeGroupEntries) {
		User user = userRepository.findByUsernameIgnoreCase(username).get();
		
		Set<GroupEntry> currentGroupEntries = user.getGroupEntries();
		
		Set<String> currentGroupNameList = new HashSet<>();
		currentGroupEntries.forEach(e -> {
			currentGroupNameList.add(e.getGroupName());
		});
		
		
		Set<String> removeGroupNameList = new HashSet<>();
		removeGroupEntries.forEach(e -> {
			removeGroupNameList.add(e.getGroupName());
		});
		
		for (String groupName : removeGroupNameList) {
			if(currentGroupNameList.contains(groupName)) {
				currentGroupEntries.stream()
						.filter(e -> e.getGroupName().equals(groupName))
						.findFirst()
						.ifPresent(grpEntry -> currentGroupEntries.remove(grpEntry));
			}
		}
		user.setGroupEntries(currentGroupEntries);
		return user;
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
