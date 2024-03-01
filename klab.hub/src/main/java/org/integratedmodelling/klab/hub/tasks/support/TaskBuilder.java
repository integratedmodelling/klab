package org.integratedmodelling.klab.hub.tasks.support;

import java.util.List;

import org.integratedmodelling.klab.hub.api.Task;
import org.integratedmodelling.klab.hub.api.TaskParameters;

public abstract class TaskBuilder {
		
	public abstract List<Task> build(TaskParameters parameters);	
	
}
