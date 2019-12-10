package org.integratedmodelling.klab.hub.tasks.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.hub.tasks.GroupRequestTask;
import org.integratedmodelling.klab.hub.tasks.Task;
import org.springframework.stereotype.Service;

@Service
public interface GroupRequestService {
	public abstract GroupRequestTask createTask(String requestee, List<String> groupNames, HttpServletRequest request);
	public abstract GroupRequestTask acceptTask(String id, HttpServletRequest request);
	public abstract GroupRequestTask denyTask(String id, HttpServletRequest request);
	public abstract List<Task> getTasks();
}
