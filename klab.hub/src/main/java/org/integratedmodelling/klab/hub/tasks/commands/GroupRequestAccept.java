package org.integratedmodelling.klab.hub.tasks.commands;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.integratedmodelling.klab.hub.models.tokens.GroupsClickbackToken;
import org.integratedmodelling.klab.hub.repository.TokenRepository;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.tasks.GroupRequestTask;
import org.integratedmodelling.klab.hub.tasks.TaskStatus;
import org.integratedmodelling.klab.hub.users.GroupEntry;
import org.integratedmodelling.klab.hub.users.User;

public class GroupRequestAccept implements TaskCommand{
	
	private final GroupRequestTask task;
	
	private final UserRepository userRepository;
	
	private final TokenRepository tokenRepository;
	
	public GroupRequestAccept(GroupRequestTask task, 
			UserRepository userRepository,
			TokenRepository tokenRepository	) {
		this.task = task;
		this.userRepository = userRepository;
		this.tokenRepository = tokenRepository;
	}

	@Override
	public GroupRequestTask execute() {
		GroupsClickbackToken token = (GroupsClickbackToken) task.getToken();
		Set<GroupEntry> newGroupEntries = token.getGroups().stream().collect(Collectors.toSet());
		User user = addUserGroupEntries(task.getRequestee(), newGroupEntries);
		userRepository.save(user);
		tokenRepository.delete(token);
		task.setStatus(TaskStatus.acceptedTask);
		return task;
	}

	
	private User addUserGroupEntries(String username, Set<GroupEntry> newGroupEntries) {
		User user = userRepository.findByUsernameIgnoreCase(username).get();
		
		Set<GroupEntry> currentGroupEntries = user.getGroupEntries();
		
		Set<String> currentGroupNameList = new HashSet<>();
		currentGroupEntries.forEach(e -> {
			currentGroupNameList.add(e.getGroupName());
		});
		
		
		Set<String> newGroupNameList = new HashSet<>();
		newGroupEntries.forEach(e -> {
			newGroupNameList.add(e.getGroupName());
		});
		
		for (String groupName : newGroupNameList) {
			if(currentGroupNameList.contains(groupName)) {
				
				GroupEntry userGrpEntry = currentGroupEntries.stream()
						.filter(e -> e.getGroupName().equals(groupName))
						.findFirst()
						.get();
				
				GroupEntry newGrpEntry = newGroupEntries.stream()
						.filter(e -> e.getGroupName().equals(groupName))
						.findFirst()
						.get();
				
				if(userGrpEntry.getExperation() !=null) {
					if (!userGrpEntry.getExperation().isAfter(newGrpEntry.getExperation())) {
						currentGroupEntries.remove(userGrpEntry);
						currentGroupEntries.add(newGrpEntry);
					}
				}
				
			} else {
				GroupEntry newGrpEntry = newGroupEntries.stream()
						.filter(e -> e.getGroupName().equals(groupName))
						.findFirst()
						.get();
				currentGroupEntries.add(newGrpEntry);
			}
		}
		user.setGroupEntries(currentGroupEntries);
		return user;
	}
}
