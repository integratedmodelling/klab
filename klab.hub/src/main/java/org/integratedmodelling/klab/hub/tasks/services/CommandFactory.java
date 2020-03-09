package org.integratedmodelling.klab.hub.tasks.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.integratedmodelling.klab.hub.tasks.CreateGroupTask;
import org.integratedmodelling.klab.hub.tasks.GroupRequestTask;
import org.integratedmodelling.klab.hub.tasks.RemoveGroupTask;
import org.integratedmodelling.klab.hub.tasks.Task;
import org.integratedmodelling.klab.hub.tasks.TaskCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandFactory {

	@Autowired
	private List<TaskCommand> commands;
	
	private static final Map<Class<? extends TaskCommand>, TaskCommand> taskCommandsCache = new HashMap<Class<? extends TaskCommand>, TaskCommand>();
	
	@PostConstruct
    public void initList() {
        for(TaskCommand command : commands) {
        	taskCommandsCache.put(command.getClass(), command);
        }
    }
	
	public static TaskCommand getCommand(Class<? extends Task> clazz) {
		Class<? extends TaskCommand> commandClazz = null;
		if (CreateGroupTask.class.equals(clazz)) {
			commandClazz = CreateGroupTask.Command.class;
		} else if (GroupRequestTask.class.equals(clazz)) {
			commandClazz = GroupRequestTask.Command.class;
		} if (RemoveGroupTask.class.equals(clazz)) {
			commandClazz = RemoveGroupTask.Command.class;
		} 
		if (commandClazz == null) {
			throw new RuntimeException("No commands for type " + clazz);
		}
		TaskCommand command = taskCommandsCache.get(commandClazz);
        if(command == null) {
        	throw new RuntimeException("Unknown builder type: " + commandClazz);
        }
		return command;
	}
}
