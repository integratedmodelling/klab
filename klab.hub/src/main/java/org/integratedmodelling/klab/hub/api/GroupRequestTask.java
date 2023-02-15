package org.integratedmodelling.klab.hub.api;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.tasks.services.CommandFactory;
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
			User user = userRepository.findByNameIgnoreCase(grt.getUsername()).get();
			Set<GroupEntry> newGroupEntries = grt.getRequestGroups();
			Set<GroupEntry> currentGroupEntries = user.getAgreements().stream().findFirst().get().getGroupEntries();
			
			Set<String> currentGroupNameList = currentGroupEntries.stream()
					.map(GroupEntry::getGroupName)
					.collect(Collectors.toCollection(HashSet<String>::new));
			
			
			Set<String> newGroupNameList = newGroupEntries.stream()
					.map(GroupEntry::getGroupName)
					.collect(Collectors.toCollection(HashSet<String>::new));
			
			boolean added = false;
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
					
					if(userGrpEntry.getExperation() !=null && !userGrpEntry.getExperation().isAfter(newGrpEntry.getExperation())) {
						currentGroupEntries.remove(userGrpEntry);
						currentGroupEntries.add(newGrpEntry);
						task.addToLog("Group "+newGrpEntry.getGroupName()+" experation updated to "+newGrpEntry.getExperation());
						added = true;
					}
					
				} else {
					GroupEntry newGrpEntry = newGroupEntries.stream()
							.filter(e -> e.getGroupName().equals(groupName))
							.findFirst()
							.get();
					currentGroupEntries.add(newGrpEntry);
					task.addToLog("Group "+newGrpEntry.getGroupName()+" added with experation "+newGrpEntry.getExperation());
					added = true;
				}
			}
			if (added) {
				user.getAgreements().stream().findFirst().get().setGroupEntries(currentGroupEntries);
				userRepository.save(user);
			} else {
				task.addToLog("No group(s) added");
			}
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
