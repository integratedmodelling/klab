package org.integratedmodelling.klab.hub.tasks;

import java.util.List;

public abstract class TaskBuilder {
		
	public abstract List<Task> build(TaskParameters parameters);	
	
}
