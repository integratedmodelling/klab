package org.integratedmodelling.klab.hub.tasks.services;

import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.hub.groups.MongoGroup;
import org.integratedmodelling.klab.hub.tasks.Task;
import org.springframework.stereotype.Service;

@Service
public interface CreateGroupService extends TaskBaseService{
	public abstract Task createTask(String requestee, MongoGroup group, HttpServletRequest request);
}
