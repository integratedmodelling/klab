package org.integratedmodelling.klab.hub.api;

public enum TaskType {
	groupRequest(
			GroupRequestTask.class),
	createGroup(
			CreateGroupTask.class),
	removeGroupRequest(
			RemoveGroupTask.class),
	setRoles(
			SetRoleTask.class);
	
	private final Class<? extends Task> clazz;
	
    public Class<? extends Task> getClazz() {
        return clazz;
    }
    
    TaskType(Class<? extends Task> clazz) {
        this.clazz = clazz;
    }
}