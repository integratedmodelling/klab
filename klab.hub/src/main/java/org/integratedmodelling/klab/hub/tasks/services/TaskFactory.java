package org.integratedmodelling.klab.hub.tasks.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.integratedmodelling.klab.hub.api.CreateGroupTask;
import org.integratedmodelling.klab.hub.api.GroupRequestTask;
import org.integratedmodelling.klab.hub.api.ModifyUserAccountStatusTask;
import org.integratedmodelling.klab.hub.api.RemoveGroupTask;
import org.integratedmodelling.klab.hub.api.RemoveRoleTask;
import org.integratedmodelling.klab.hub.api.SetRoleTask;
import org.integratedmodelling.klab.hub.api.Task;
import org.integratedmodelling.klab.hub.api.TaskBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskFactory {
	
	@Autowired
	private List<TaskBuilder> builders;
	
	private static final Map<Class<? extends TaskBuilder>, TaskBuilder> taskBuildersCache = new HashMap<Class<? extends TaskBuilder>, TaskBuilder>();
	
	@PostConstruct
    public void initList() {
        for(TaskBuilder builder : builders) {
        	taskBuildersCache.put(builder.getClass(), builder);
        }
    }
	
	public static TaskBuilder getBuilder(Class<? extends Task> clazz) {
		Class<? extends TaskBuilder> builderClazz = null;
		if (CreateGroupTask.class.equals(clazz)) {
			builderClazz = CreateGroupTask.Builder.class;
		} else if (GroupRequestTask.class.equals(clazz)) {
			builderClazz = GroupRequestTask.Builder.class;
		} else if (RemoveGroupTask.class.equals(clazz)) {
			builderClazz = RemoveGroupTask.Builder.class;
		} else if (ModifyUserAccountStatusTask.class.equals(clazz)) {
			builderClazz = ModifyUserAccountStatusTask.Builder.class;
		} else if (SetRoleTask.class.equals(clazz)) {
			builderClazz = SetRoleTask.Builder.class;
		} else if (RemoveRoleTask.class.equals(clazz)) {
			builderClazz = RemoveRoleTask.Builder.class;
		}
		if (builderClazz == null) {
			throw new RuntimeException("No builder for type " + clazz);
		}
		TaskBuilder builder = taskBuildersCache.get(builderClazz);
        if(builder == null) {
        	throw new RuntimeException("Unknown builder type: " + builderClazz);
        }
		return builder;
	}
	
}
