package org.integratedmodelling.klab.hub.api;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.integratedmodelling.klab.auth.Role;
import org.integratedmodelling.klab.hub.api.TaskParameters.TaskParametersWithRoleRequirement;
import org.integratedmodelling.klab.hub.api.User.AccountStatus;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.tasks.services.CommandFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class ModifyUserAccountStatusTask extends Task {

	private static TaskCommand command = CommandFactory.getCommand(ModifyUserAccountStatusTask.class);

	@Component
	public static class Command extends TaskCommand {
		
		@Autowired
		private UserRepository userRepository;
		
		@Override
		public void executeAccept(Task task) {
			ModifyUserAccountStatusTask modifyUserAccountStatusTask = (ModifyUserAccountStatusTask)task;
			
			User user = userRepository.findByNameIgnoreCase(modifyUserAccountStatusTask.getUsername()).get();
			modifyUserAccountStatusTask.previousAccountStatus = user.getAccountStatus();
			
			AccountStatus accountStatus = modifyUserAccountStatusTask.getRequestedAccountStatus();
			
			// Check if the status modification is allowed
			switch(accountStatus) {
			case active:
				if(modifyUserAccountStatusTask.previousAccountStatus != AccountStatus.locked) {
					break;
				}
				user.setAccountStatus(accountStatus);
				userRepository.save(user);
				task.setStatus(TaskStatus.accepted);
				return;
			case locked:
				if(modifyUserAccountStatusTask.previousAccountStatus != AccountStatus.active) {
					break;
				}
				user.setAccountStatus(accountStatus);
				userRepository.save(user);
				task.setStatus(TaskStatus.accepted);
				return;
			case deleted:
				// TODO delete the user
				user.setAccountStatus(accountStatus);
				userRepository.save(user);
				task.setStatus(TaskStatus.accepted);
				return;
			default:
				// nothing to do here
			}
			task.setStatus(TaskStatus.denied);
		}
	}

	public static class Parameters extends TaskParametersWithRoleRequirement {
		
		String username;
		AccountStatus accountStatus;
		Class<? extends ModifyUserAccountStatusTask> clazz;
		
		public Parameters(HttpServletRequest request, String username, AccountStatus accountStatus, Class<? extends ModifyUserAccountStatusTask> clazz) {
			super(request, Role.ROLE_ADMINISTRATOR);
			this.username = username;
			this.accountStatus = accountStatus;
			this.clazz = clazz;
		}
	}
	
	@Component
	public static class Builder extends TaskBuilder {

		@Override
		public List<Task> build(TaskParameters parameters) {
			ModifyUserAccountStatusTask.Parameters param;
			if (parameters instanceof ModifyUserAccountStatusTask.Parameters) {
				param = (ModifyUserAccountStatusTask.Parameters)parameters;
			} else {
				throw new ClassCastException();
			}

			ArrayList<Task> ret = new ArrayList<Task>(1);			
			Constructor<? extends ModifyUserAccountStatusTask> constructor = null;
			try {
				constructor = param.clazz.getConstructor(String.class, AccountStatus.class);
			} catch (IllegalArgumentException
					| NoSuchMethodException | SecurityException e) {
				throw new RuntimeException("Problem creating modify user account status task", e);
			}
			try {
				Task modifyUserAccountStatusTask = constructor.newInstance(param.username, param.accountStatus);
				ret.add(modifyUserAccountStatusTask);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				throw new RuntimeException("Problem creating modify user account status task", e);
			}
			
			return ret;
		}
	}
	
	private String username;
	private AccountStatus previousAccountStatus;
	private AccountStatus requestedAccountStatus;
	
	public String getUsername() {
		return username;
	}
	
	public AccountStatus getRequestedAccountStatus() {
		return requestedAccountStatus;
	}
	
	public ModifyUserAccountStatusTask(String username, AccountStatus requestedAccountStatus) {
		super();
		this.username = username;
		this.requestedAccountStatus = requestedAccountStatus;
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
		setType(TaskType.modifyUserAccountStatus);
	}
	
	private TaskCommand getCommand() {
		return command;
	}

}
