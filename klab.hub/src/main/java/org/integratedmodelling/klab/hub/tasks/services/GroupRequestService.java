package org.integratedmodelling.klab.hub.tasks.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.hub.tasks.Task;
import org.springframework.stereotype.Service;

@Service
public interface GroupRequestService extends TaskBaseService {
	public abstract Task createTask(String requestee, List<String> groupNames, HttpServletRequest request);
}
