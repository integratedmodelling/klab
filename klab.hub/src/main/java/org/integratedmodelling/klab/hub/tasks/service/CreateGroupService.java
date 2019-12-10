package org.integratedmodelling.klab.hub.tasks.service;

import org.integratedmodelling.klab.hub.models.KlabGroup;
import org.integratedmodelling.klab.hub.tasks.CreateGroupTask;
import org.springframework.stereotype.Service;

@Service
public interface CreateGroupService {
	public abstract CreateGroupTask createTask(String requestee, KlabGroup group);

}
