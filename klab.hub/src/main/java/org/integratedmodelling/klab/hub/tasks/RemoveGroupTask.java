package org.integratedmodelling.klab.hub.tasks;

import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.tasks.services.CommandFactory;
import org.integratedmodelling.klab.hub.users.GroupEntry;
import org.integratedmodelling.klab.hub.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class RemoveGroupTask extends ModifyGroupsTask{
	
	private static TaskCommand command = CommandFactory.getCommand(RemoveGroupTask.class);

	@Component
	public static class Command extends TaskCommand {
		
		@Autowired
		private UserRepository userRepository;
		
		@Override
		public void executeAccept(Task task) {
			RemoveGroupTask rgt = (RemoveGroupTask)task;
			User user = userRepository.findByUsernameIgnoreCase(rgt.getUsername()).get();
			
			Set<GroupEntry> currentGroupEntries = user.getGroupEntries();
			
			Set<String> currentGroupNameList = new HashSet<>();
			currentGroupEntries.forEach(e -> {
				currentGroupNameList.add(e.getGroupName());
			});
			
			
			Set<String> removeGroupNameList = new HashSet<>();
			rgt.getRequestGroups().forEach(e -> {
				removeGroupNameList.add(e.getGroupName());
			});
			
			for (String groupName : removeGroupNameList) {
				if(currentGroupNameList.contains(groupName)) {
					currentGroupEntries.stream()
							.filter(e -> e.getGroupName().equals(groupName))
							.findFirst()
							.ifPresent(grpEntry -> currentGroupEntries.remove(grpEntry));
				} else {
					executeDeny(task, "Try to remove unassigned group "+groupName+" to user "+user.getUsername());
				}
			}
			user.setGroupEntries(currentGroupEntries);
			userRepository.save(user);
			task.setStatus(TaskStatus.accepted);
		}

	}

	public RemoveGroupTask(String username, Set<GroupEntry> requestGroups) {
		super(username, requestGroups);
	}
	
		@Override
	public void setType() {
		setType(TaskType.removeGroupRequest);
	}

	@Override
	protected TaskCommand getCommand() {
		return command;
	}

}
