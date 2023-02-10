package org.integratedmodelling.klab.hub.api;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.auth.Role;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.tasks.services.CommandFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.stereotype.Component;

@TypeAlias("RoleSetTask")
public class RoleSetTask extends Task {

	private static RoleSetTask.Command command = (RoleSetTask.Command)CommandFactory.getCommand(RoleSetTask.class);

	public static class Parameters extends TaskParameters.TaskParametersWithRoleRequirement {
		private String username;
		private Set<Role> rolesToSet;
		Class<? extends RoleSetTask> clazz;
		
		public Parameters(HttpServletRequest request, String username, Set<Role> rolesToSet, Class<? extends RoleSetTask> clazz) {
			super(request, Role.ROLE_ADMINISTRATOR);
			this.username = username;
			this.rolesToSet = rolesToSet;
			this.clazz = clazz;
		}

		public Set<Role> getRolesToSet() {
			return rolesToSet;
		}
	}
	
	@Component
	public static class Builder extends TaskBuilder {
		@Override
		public List<Task> build(TaskParameters parameters) {
			RoleSetTask.Parameters param;
			if (parameters instanceof RoleSetTask.Parameters) {
				param = (RoleSetTask.Parameters)parameters;
			} else {
				throw new ClassCastException();
			}
			
			ArrayList<Task> ret = new ArrayList<Task>(1);
			
			Constructor<? extends RoleSetTask> constructor = null;
			try {
				constructor = param.clazz.getConstructor(String.class, Set.class);
			} catch (IllegalArgumentException
					| NoSuchMethodException | SecurityException e) {
				throw new RuntimeException("Problem creating set role task", e);
			}
			
			Task setRoleTask;
			try {
				setRoleTask = constructor.newInstance(param.username, param.rolesToSet);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				throw new RuntimeException("Problem creating set role task", e);
			}

			ret.add(setRoleTask);
			return ret;
		}
	}
	@Component
	public static class Command extends TaskCommand {
		@Autowired
		private UserRepository userRepository;
		
		@Override
		public void executeAccept(Task task) {
			RoleSetTask roleSetTask = (RoleSetTask)task;
			
			User user = userRepository.findByNameIgnoreCase(roleSetTask.getUsername()).get();
			Set<Role> rolesToSet = roleSetTask.getRolesToSet();
			user.addRoles(rolesToSet.toArray(new Role[rolesToSet.size()]));
			userRepository.save(user);
			
			task.setStatus(TaskStatus.accepted);
		}
		

	}
	
	private String username;
	private Set<Role> rolesToSet;
	
	public String getUsername() {
		return this.username;
	}
	
	public Set<Role> getRolesToSet() {
		return this.rolesToSet;
	}
	
	
	public RoleSetTask(String username, Set<Role> rolesToSet) {
		super();
		this.username = username;
		this.rolesToSet = rolesToSet;
		this.setAutoAccepted(true);
		this.setRoleRequirement(Role.ROLE_ADMINISTRATOR);
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

	@Override
	public void setType() {
		setType(TaskType.setRoles);
	}
	
	private TaskCommand getCommand() {
		return command;
	}
}
