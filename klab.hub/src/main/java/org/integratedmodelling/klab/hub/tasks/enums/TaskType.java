package org.integratedmodelling.klab.hub.tasks.enums;

import org.integratedmodelling.klab.hub.api.CreateGroupTask;
import org.integratedmodelling.klab.hub.api.GroupRequestTask;
import org.integratedmodelling.klab.hub.api.ModifyUserAccountStatusTask;
import org.integratedmodelling.klab.hub.api.RemoveGroupTask;
import org.integratedmodelling.klab.hub.api.RemoveRoleTask;
import org.integratedmodelling.klab.hub.api.SetRoleTask;
import org.integratedmodelling.klab.hub.api.Task;

public enum TaskType {
	groupRequest(
			GroupRequestTask.class),
	createGroup(
			CreateGroupTask.class),
	removeGroupRequest(
			RemoveGroupTask.class),
	setRoles(
			SetRoleTask.class),
	removeRoles(
			RemoveRoleTask.class),
	modifyUserAccountStatus(
			ModifyUserAccountStatusTask.class);
	
	private final Class<? extends Task> clazz;
	
    public Class<? extends Task> getClazz() {
        return clazz;
    }
    
    TaskType(Class<? extends Task> clazz) {
        this.clazz = clazz;
    }
}