package org.integratedmodelling.klab.hub.tasks.service;

import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.hub.models.KlabGroup;
import org.integratedmodelling.klab.hub.tasks.Task;
import org.springframework.stereotype.Service;

@Service
public interface CreateGroupService extends TaskBaseService{
	public abstract Task createTask(String requestee, KlabGroup group, HttpServletRequest request);
}
