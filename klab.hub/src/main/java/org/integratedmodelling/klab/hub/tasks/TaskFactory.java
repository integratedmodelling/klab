package org.integratedmodelling.klab.hub.tasks;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.hub.tasks.CreateGroupTask;

public class TaskFactory {
	
	private static TaskFactory INSTANCE;
	private Map<Class<? extends Task>, TaskBuilder> taskBuilders;
	
	private TaskFactory(Map<Class<? extends Task>, TaskBuilder> taskBuilders) {
		if (INSTANCE == null) {
			INSTANCE = new TaskFactory(new HashMap<Class<? extends Task>, TaskBuilder>());
		}
	}
	
	public static TaskBuilder getBuilder(Class<? extends Task> clazz) {
		TaskBuilder builder = INSTANCE.getStoredBuilder(clazz);
		if (builder == null) {
			if (CreateGroupTask.class.equals(clazz)) {
				builder = new CreateGroupTask.Builder();
				INSTANCE.addBuilder(CreateGroupTask.class, builder);
			}
		}
		return builder;
	}
	
	protected TaskBuilder getStoredBuilder(Class<? extends Task> clazz) {
		return this.taskBuilders.get(clazz);
	}
	
	protected void addBuilder(Class<? extends Task> clazz, TaskBuilder builder) {
		this.taskBuilders.put(clazz, builder);
	}
	
}
