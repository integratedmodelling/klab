package org.integratedmodelling.klab.hub.tasks;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.users.GroupEntry;
import org.integratedmodelling.klab.hub.users.Role;
import org.integratedmodelling.klab.hub.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.stereotype.Component;

@TypeAlias("GroupRequestTask")
public class GroupRequestTask extends Task{

	private static GroupRequestTask.Command command = new GroupRequestTask.Command();

	public static class Parameters extends TaskParameters {
		
		List<String> groupNames;
		
		public Parameters(String requestee, HttpServletRequest request, List<String> groupNames) {
			super(requestee, request);
			this.groupNames = groupNames;
		}
	}
	
	@Component
	public static class Builder extends TaskBuilder {
		
		MongoGroupRepository groupRepository;
		UserRepository userRepository;
		
		public Builder() {
			groupRepository = BeanUtil.getBean(MongoGroupRepository.class);
			userRepository = BeanUtil.getBean(UserRepository.class);
		}
		
		@Override
		public List<Task> build(TaskParameters parameters) {
			
			GroupRequestTask.Parameters param;
			if (parameters instanceof GroupRequestTask.Parameters) {
				param = (GroupRequestTask.Parameters)parameters;
			} else {
				throw new ClassCastException();
			}
			ArrayList<Task> ret = new ArrayList<Task>(2);
			
			Set<GroupEntry> optIn = new HashSet<>();
			Set<GroupEntry> requestGroups = new HashSet<GroupEntry>();
			
			Optional<User> user = userRepository.findByUsernameIgnoreCase(param.getRequestee());
			Set<GroupEntry> userGroupEntries = user
					.map(User::getGroupEntries)
					.orElse(new HashSet<>());
			
			List<String> currentGroups = new ArrayList<String>();
			
			
			userGroupEntries.forEach(entry -> {
				currentGroups.add(entry.getGroupName());	
			});
			
			if(currentGroups.containsAll(param.groupNames)) {
				throw new BadRequestException("Requested Groups already available to user.");
			}
			
			List<Boolean> exists = param.groupNames.stream()
					.map(groupRepository::existsByGroupNameIgnoreCase)
					.collect(Collectors.toList());
			
			if(exists.contains(false) | param.groupNames.size() == 0) {
				throw new BadRequestException("A requested Group does not exist or no groups requested");
			}
			for (String groupName : param.groupNames) {
				groupRepository
					.findByGroupNameIgnoreCase(groupName)
					.filter(group -> param.getRequest().isUserInRole(group.getRoleRequirement().toString()))
					.ifPresent(group -> optIn.add(new GroupEntry(group)));
				
				groupRepository.findByGroupNameIgnoreCase(groupName)
					.filter(group -> !param.getRequest().isUserInRole(group.getRoleRequirement().toString()))
					.ifPresent(group -> requestGroups.add(new GroupEntry(group)));
			}
			
			
			if(!optIn.isEmpty()) {
				Task optInTask = new GroupRequestTask(param.getRequestee(), optIn);
				optInTask.setAutoAccepted(true);
				optInTask.setRoleRequirement(optIn.iterator().next().getGroup().getRoleRequirement());
				ret.add(optInTask);
			}
			
			if(!requestGroups.isEmpty()) {
				Task requestGroupsTask = new GroupRequestTask(param.getRequestee(), requestGroups);
				requestGroupsTask.setRoleRequirement(Role.ROLE_ADMINISTRATOR);
				ret.add(requestGroupsTask);
			}
			
			return ret;
		}
		
	}
	
	public static class Command extends TaskCommand {
		
		@Autowired
		private UserRepository userRepository;
		
		@Override
		public void executeAccept(Task task) {
			GroupRequestTask grt = (GroupRequestTask)task;
			User user = userRepository.findByUsernameIgnoreCase(task.getRequestee()).get();
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

		@Override
		public void executeDeny(Task task) {
			task.setStatus(TaskStatus.denied);
		}

	}

	Set<GroupEntry> requestGroups;
	
	/**
	 * @return the requestGroups
	 */
	public Set<GroupEntry> getRequestGroups() {
		return requestGroups;
	}

	private GroupRequestTask(String requestee, Set<GroupEntry> requestGroups) {
		super(requestee);
		this.requestGroups = requestGroups;
	}

	@Override
	public void acceptTaskAction(HttpServletRequest request) {
		if (request.isUserInRole(this.getRoleRequirement())) {
			command.executeAccept(this);
		}
	}

	@Override
	public void denyTaskAction(HttpServletRequest request) {
		if (request.isUserInRole(this.getRoleRequirement())) {
			command.executeDeny(this);
		}
	}

}
