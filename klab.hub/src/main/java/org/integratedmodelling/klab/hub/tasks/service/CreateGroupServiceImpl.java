package org.integratedmodelling.klab.hub.tasks.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.hub.models.KlabGroup;
import org.integratedmodelling.klab.hub.models.Role;
import org.integratedmodelling.klab.hub.repository.KlabGroupRepository;
import org.integratedmodelling.klab.hub.repository.TaskRepository;
import org.integratedmodelling.klab.hub.service.KlabGroupService;
import org.integratedmodelling.klab.hub.tasks.Task;
import org.integratedmodelling.klab.hub.tasks.TaskStatus;
import org.integratedmodelling.klab.hub.tasks.TaskType;
import org.springframework.stereotype.Service;

@Service
public class CreateGroupServiceImpl implements CreateGroupService {

	private KlabGroupRepository groupRepository;
	
	private TaskRepository taskRepository;
	
	public CreateGroupServiceImpl(KlabGroupRepository groupService,
			TaskRepository taskRepository) {
		this.groupRepository = groupRepository;
		this.taskRepository = taskRepository;
	}
	
	@Override
	public Task createTask(String requestee, KlabGroup group, HttpServletRequest request) {
		if (request.isUserInRole(Role.SYSTEM )) {
		}
		return null;
	}
	
	@Override
	public Task acceptTask(String id, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Task denyTask(String id, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Task> getTasks(TaskType type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Task> getTasksByStatus(TaskType type, TaskStatus status) {
		// TODO Auto-generated method stub
		return null;
	}

}
