package org.integratedmodelling.klab.hub.api;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.integratedmodelling.klab.hub.agreements.dto.Agreement;
import org.integratedmodelling.klab.hub.repository.AgreementRepository;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.tasks.commands.TaskCommand;
import org.integratedmodelling.klab.hub.tasks.enums.TaskStatus;
import org.integratedmodelling.klab.hub.tasks.enums.TaskType;
import org.integratedmodelling.klab.hub.tasks.services.CommandFactory;
import org.integratedmodelling.klab.hub.users.dto.GroupEntry;
import org.integratedmodelling.klab.hub.users.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class RemoveGroupTask extends ModifyGroupsTask{
	
	private static TaskCommand command = CommandFactory.getCommand(RemoveGroupTask.class);

	@Component
	public static class Command extends TaskCommand {
		
		@Autowired
		private UserRepository userRepository;
		@Autowired
        private AgreementRepository agreementRepository;
		
		@Override
		public void executeAccept(Task task) {
			RemoveGroupTask rgt = (RemoveGroupTask)task;
			User user = userRepository.findByNameIgnoreCase(rgt.getUsername()).get();
			
			Set<GroupEntry> currentGroupEntries = user.getAgreements().stream().findFirst().get().getAgreement().getGroupEntries();
			
			Set<GroupEntry> toRemoveGroupEntries = rgt.getRequestGroups();
			
			// check dependencies
			Set<String> dependent = new HashSet<String>();
			currentGroupEntries.forEach(group -> {
				if (group.getGroup().getDependsOn() != null) {
					group.getGroup().getDependsOn().forEach(dep -> {
						toRemoveGroupEntries.stream()
							.filter(ge -> ge.getGroup().getName().equals(dep))
							.findFirst()
							.ifPresent(ge -> dependent.add(group.getGroup().getName()));
					});
				}
			});
			if (dependent.size() > 0) {
				for (String d : dependent) {
					if (toRemoveGroupEntries.stream().filter(ge -> ge.getGroup().getName().equals(d)).count() == 0) {
						task.setStatus(TaskStatus.error);
						task.addToLog("Try to remove a group that is dependecy of another");
						return;
					}
				}
			}
			
			Set<String> currentGroupNameList = currentGroupEntries.stream()
					.map(GroupEntry::getGroupName)
					.collect(Collectors.toCollection(HashSet<String>::new));
			Set<String> removeGroupNameList = toRemoveGroupEntries.stream()
					.map(GroupEntry::getGroupName)
					.collect(Collectors.toCollection(HashSet<String>::new));
			
			boolean removed = false;
			for (String groupName : removeGroupNameList) {
				if(currentGroupNameList.contains(groupName)) {
					currentGroupEntries.stream()
							.filter(e -> e.getGroupName().equals(groupName))
							.findFirst()
							.ifPresent(grpEntry -> currentGroupEntries.remove(grpEntry));
					removed = true;
					task.addToLog("Group "+groupName+" removed");
				} else {
					task.addToLog("Try to remove unassigned group "+groupName);
				}
			}
			if (removed) {
			    Agreement agreement = user.getAgreements().stream().findFirst().get().getAgreement();
			    agreement.setGroupEntries(currentGroupEntries);
				//user.setGroupEntries(currentGroupEntries);
				agreementRepository.save(agreement);
				task.setStatus(TaskStatus.accepted);
			} else {
				task.setStatus(TaskStatus.error);
			}
			
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
