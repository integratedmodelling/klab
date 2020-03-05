package org.integratedmodelling.klab.hub.tasks;

import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.tasks.services.CommandFactory;
import org.integratedmodelling.klab.hub.users.GroupEntry;
import org.integratedmodelling.klab.hub.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.stereotype.Component;

@TypeAlias("GroupRequestTask")
public class GroupRequestTask extends ModifyGroupsTask{

	private static TaskCommand command = CommandFactory.getCommand(GroupRequestTask.class);

	@Component
	public static class Command extends TaskCommand {
		
		@Autowired
		private UserRepository userRepository;
		
		@Override
		public void executeAccept(Task task) {
			GroupRequestTask grt = (GroupRequestTask)task;
			User user = userRepository.findByUsernameIgnoreCase(grt.getUsername()).get();
			Set<GroupEntry> newGroupEntries = grt.getRequestGroups();
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
			userRepository.save(user);
			task.setStatus(TaskStatus.accepted);
		}

	}

	public GroupRequestTask(String username, Set<GroupEntry> requestGroups) {
		super(username, requestGroups);
	}

	@Override
	public void setType() {
		this.setType(TaskType.groupRequest);
	}

	@Override
	protected TaskCommand getCommand() {
		return command;
	}

}
