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

@TypeAlias("RemoveRoleTask")
public class RemoveRoleTask extends Task {

	private static RemoveRoleTask.Command command = (RemoveRoleTask.Command)CommandFactory.getCommand(RemoveRoleTask.class);

	public static class Parameters extends TaskParameters.TaskParametersWithRoleRequirement {
		private String username;
		private Set<Role> rolesToRemove;
		Class<? extends RemoveRoleTask> clazz;
		
		public Parameters(HttpServletRequest request, String username, Set<Role> rolesToRemove, Class<? extends RemoveRoleTask> clazz) {
			super(request, Role.ROLE_ADMINISTRATOR);
			this.username = username;
			this.rolesToRemove = rolesToRemove;
			this.clazz = clazz;
		}

		public Set<Role> getRolesToRemove() {
			return this.rolesToRemove;
		}
	}

	@Component
	public static class Builder extends TaskBuilder {
		@Override
		public List<Task> build(TaskParameters parameters) {
			RemoveRoleTask.Parameters param;
			if (parameters instanceof RemoveRoleTask.Parameters) {
				param = (RemoveRoleTask.Parameters)parameters;
			} else {
				throw new ClassCastException();
			}
			
			ArrayList<Task> ret = new ArrayList<Task>(1);
			
			Constructor<? extends RemoveRoleTask> constructor = null;
			try {
				constructor = param.clazz.getConstructor(String.class, Set.class);
			} catch (IllegalArgumentException
					| NoSuchMethodException | SecurityException e) {
				throw new RuntimeException("Problem creating remove role task", e);
			}
			
			Task setRoleTask;
			try {
				setRoleTask = constructor.newInstance(param.username, param.rolesToRemove);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				throw new RuntimeException("Problem creating remove role task", e);
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
			RemoveRoleTask removeRoleTask = (RemoveRoleTask)task;
			
			User user = userRepository.findByNameIgnoreCase(removeRoleTask.getUsername()).get();
			Set<Role> rolesToRemove = removeRoleTask.getRolesToRemove();
			user.removeRoles(rolesToRemove);
			userRepository.save(user);
			
			task.setStatus(TaskStatus.accepted);
		}
	}
	
	private String username;
	private Set<Role> rolesToRemove;
	
	public String getUsername() {
		return this.username;
	}
	
	public Set<Role> getRolesToRemove() {
		return this.rolesToRemove;
	}
	
	public RemoveRoleTask(String username, Set<Role> rolesToRemove) {
		super();
		this.username = username;
		this.rolesToRemove = rolesToRemove;
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
		setType(TaskType.removeRoles);
	}
	
	private TaskCommand getCommand() {
		return command;
	}
}
