package org.integratedmodelling.klab.hub.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.auth.Role;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.groups.services.GroupService;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.integratedmodelling.klab.hub.tasks.services.CommandFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonGetter;

@TypeAlias("CreateGroupTask")
public class CreateGroupTask extends Task{
	
	private static CreateGroupTask.Command command = (CreateGroupTask.Command)CommandFactory.getCommand(CreateGroupTask.class);

	public static class Parameters extends TaskParameters.TaskParametersWithRoleRequirement {
		MongoGroup group;
		
		public Parameters(Role roleRequirement, HttpServletRequest request, MongoGroup group) {
			super(request, roleRequirement);
			this.group = group;
		}
		/**
		 * @return the group
		 */
		public MongoGroup getGroup() {
			return group;
		}

	}
	
	@Component
	public static class Builder extends TaskBuilder {
		
		@Autowired
		private MongoGroupRepository groupRepository;
		
		/*
		public Builder() {
			groupRepository = BeanUtil.getBean(MongoGroupRepository.class);
		}
		*/

		@Override
		public List<Task> build(TaskParameters parameters) {
			ArrayList<Task> ret = new ArrayList<Task>(1);
			CreateGroupTask.Parameters param;
			if (parameters instanceof CreateGroupTask.Parameters) {
				param = (CreateGroupTask.Parameters)parameters;
			} else {
				throw new ClassCastException();
			}
			Boolean exists = groupRepository
					.findByNameIgnoreCase(param.getGroup().getName())
					.isPresent();
			
			if(exists) {
				throw new BadRequestException("Group by that name already present.");
			}
			ret.add(new CreateGroupTask(param.getRoleRequirement(), param.getGroup()));
			return ret;
		}
		
	}
	
	@Component
	public static class Command extends TaskCommand {
		
		@Autowired
		private GroupService service;

		/*
		public Command() {
			service = BeanUtil.getBean(GroupService.class);
		}
		*/
		
		@Override
		public void executeAccept(Task task) {
			service.create(((CreateGroupTask)task).getGroup());
			task.setStatus(TaskStatus.accepted);
		}

	}

	
	private CreateGroupTask(Role roleRequirement, MongoGroup group) {
		super(roleRequirement);
		this.group = group;
	}

	@Reference
	MongoGroup group;
	
	public MongoGroup getGroup() {
		return group;
	}
	
	@JsonGetter("group")
	public String getGroupName() {
		if (this.group != null) {
			return this.group.getName();
		}
		return null;
	}

	public void setGroup(MongoGroup group) {
		this.group = group;
	}

	@Override
	public void acceptTaskAction(HttpServletRequest request) {
		if (request.isUserInRole(this.getRoleRequirement())) {
			command.executeAccept(this);
		}
	}

	@Override
	public void denyTaskAction(HttpServletRequest request, String deniedMessage) {
		if (request.isUserInRole(this.getRoleRequirement())) {
			command.executeDeny(this, deniedMessage);
		}
	}

	@Override
	public void setType() {
		this.setType(TaskType.createGroup);
	}
}