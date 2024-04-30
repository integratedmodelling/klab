/**
 * 
 */
package org.integratedmodelling.klab.hub.api;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.auth.Role;
import org.integratedmodelling.klab.hub.groups.dto.GroupEntry;
import org.integratedmodelling.klab.hub.groups.dto.MongoGroup;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.tasks.commands.TaskCommand;
import org.integratedmodelling.klab.hub.tasks.support.TaskBuilder;
import org.integratedmodelling.klab.hub.users.dto.User;
import org.integratedmodelling.klab.hub.users.exceptions.GroupDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonGetter;

/**
 * @author Enrico Girotto
 *
 */
public abstract class ModifyGroupsTask extends Task {

public static class Parameters extends TaskParameters {
		
		String username;
		List<String> groupNames;
		Class<? extends ModifyGroupsTask> clazz;
		
		public Parameters(HttpServletRequest request, String username, List<String> groupNames, Class<? extends ModifyGroupsTask> clazz) {
			super(request);
			this.username = username;
			this.groupNames = groupNames;
			this.clazz = clazz;
		}
	}
	
	@Component
	public static class Builder extends TaskBuilder {
		
		@Autowired
		MongoGroupRepository groupRepository;
		@Autowired
		UserRepository userRepository;
		
		@Override
		public List<Task> build(TaskParameters parameters) {
			
			ModifyGroupsTask.Parameters param;
			if (parameters instanceof ModifyGroupsTask.Parameters) {
				param = (ModifyGroupsTask.Parameters)parameters;
			} else {
				throw new ClassCastException();
			}
			User user = userRepository.findByName(param.username).orElseThrow();
			ArrayList<Task> ret = new ArrayList<Task>(2);
			
			Set<GroupEntry> optIn = new HashSet<>();
			Set<GroupEntry> requestGroups = new HashSet<GroupEntry>();
			
			List<Boolean> exists = param.groupNames.stream()
					.map(groupRepository::existsByNameIgnoreCase)
					.collect(Collectors.toList());
			
			if(exists.contains(false) | param.groupNames.size() == 0) {
				throw new GroupDoesNotExistException("A requested Group does not exist or no groups requested");
			}
			
			Set<String> userGroups = user.getAgreements().stream().findFirst().get().getAgreement().getGroupEntries().stream()
			        .map(GroupEntry::getGroupName).collect(Collectors.toCollection(HashSet<String>::new));
			// check dependencies
			List<String> dependencies = new ArrayList<String>();
			List<MongoGroup> groups = new ArrayList<MongoGroup>();
			
			for (String groupName : param.groupNames) {
				Optional<MongoGroup> group = groupRepository.findByNameIgnoreCase(groupName);
				if (group.isPresent() && (param.clazz.equals(RemoveGroupTask.class) || !userGroups.contains(group.get().getName()))) {
					groups.add(group.get());
					
					if (param.clazz.equals(GroupRequestTask.class) && group.get().getDependsOn() != null) {
					    for (String dependecy : group.get().getDependsOn()) {
					        if (!userGroups.contains(dependecy)) {
					            dependencies.add(dependecy);
					        }
					    }
					}
				}
			}
			if (dependencies.size() > 0) {
				for (String dependency : dependencies) {
					Optional<MongoGroup> group = groupRepository.findByNameIgnoreCase(dependency);
					if (group.isPresent() && groups.stream()
							.filter(g -> g.getName().equals(group.get().getName())).count() == 0) {
						groups.add(group.get());
					}
				}
			}
			
			for (MongoGroup group : groups) {
				if (group.isOptIn()) {
					optIn.add(new GroupEntry(group));
				} else {
					requestGroups.add(new GroupEntry(group));
				}
			}
			Constructor<? extends ModifyGroupsTask> constructor = null;
			try {
				constructor = param.clazz.getConstructor(String.class, Set.class);
			} catch (IllegalArgumentException
					| NoSuchMethodException | SecurityException e) {
				throw new RuntimeException("Problem creating modify groups task", e);
			}
			try {
				if(!optIn.isEmpty()) {
					Task optInTask = constructor.newInstance(user.getUsername(), optIn);
					optInTask.setAutoAccepted(true);
					optInTask.setRoleRequirement(user.getRoles().iterator().next());
					ret.add(optInTask);
				}
				if(!requestGroups.isEmpty()) {
					Task requestGroupsTask = constructor.newInstance(param.username, requestGroups);
					requestGroupsTask.setRoleRequirement(Role.ROLE_ADMINISTRATOR);
					ret.add(requestGroupsTask);
				}
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				throw new RuntimeException("Problem creating modify groups task", e);
			}
			return ret;
		}
		
	}
	
	String username;
	Set<GroupEntry> requestGroups;
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * @return the requestGroups
	 */
	public Set<GroupEntry> getRequestGroups() {
		return requestGroups;
	}
	
	@JsonGetter("requestGroups")
	public List<String> getGroupsName() {
		if (this.requestGroups.size() == 0) {
			return null;
		}
		return this.requestGroups.stream()
        	.map(GroupEntry::getGroupName)
        	.collect(Collectors.toList());
	}

	protected ModifyGroupsTask(String username, Set<GroupEntry> requestGroups) {
		super();
		this.username = username;
		this.requestGroups = requestGroups;
	}

	@Override
	public void acceptTaskAction(HttpServletRequest request) {
		if (request.isUserInRole(this.getRoleRequirement())) {
			getCommand().executeAccept(this);
		}
	}

	@Override
	public void denyTaskAction(HttpServletRequest request, String deniedMessage) {
		if (request.isUserInRole(this.getRoleRequirement())) {
			getCommand().executeDeny(this, deniedMessage);
		}
	}

	protected abstract TaskCommand getCommand();

}
