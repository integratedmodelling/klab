package org.integratedmodelling.klab.hub.tasks;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;

import org.integratedmodelling.klab.hub.users.Role;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Tasks")
public abstract class Task {

	@Id
	String id;
	
	@NotEmpty
	String requestee;
	
    DateTime issued;
    
    DateTime closed;
    
    @Enumerated(EnumType.STRING)
    Role roleRequirement;
	
    @Enumerated(EnumType.STRING)
    TaskStatus status;
    
    @Enumerated(EnumType.STRING)
    TaskStatus parentStatus;
    
    DateTime expirationDate;
    
    /**
     * The next task to be accepted or deny
     */
    @Reference
    List<Task> next = new ArrayList<Task>();
    
	/**
     * If true, after create, we do accept
     */
    boolean autoAccepted;
    
    public Task(String requestee) {
    	this.requestee = requestee;
    	this.setIssued();
		this.setStatus(TaskStatus.pending);
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRequestee() {
		return requestee;
	}

	public void setRequestee(String requestee) {
		this.requestee = requestee;
	}

	public DateTime getIssued() {
		return issued;
	}

	public void setIssued() {
		this.issued = DateTime.now();
	}

	public DateTime getClosed() {
		return closed;
	}

	public void setClosed() {
		this.closed = DateTime.now();
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public DateTime getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(DateTime expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getRoleRequirement() {
		return roleRequirement.toString();
	}
	
	public void setRoleRequirement(Role role) {
		this.roleRequirement = role;
	}

	public boolean isAutoAccepted() {
		return this.autoAccepted;
	}
	
	public void setAutoAccepted(boolean autoAccepted) {
		this.autoAccepted = autoAccepted;
	}
	
	/**
	 * @return the parentStatus
	 */
	public TaskStatus getParentStatus() {
		return parentStatus;
	}

	/**
	 * @param parentStatus the parentStatus to set
	 */
	public void setParentStatus(TaskStatus parentStatus) {
		this.parentStatus = parentStatus;
	}

	/**
	 * @return the next
	 */
	public List<Task> getNext() {
		return next;
	}

	/**
	 * @param next task to add to next
	 */
	public void add(Task next) {
		this.next.add(next);
	}

	/**
	 * Specific code for accept action
	 * The status of task after this operation is the final one, it will be change inside the method 
	 * The new status is not persisted
	 */
	abstract public void acceptTaskAction(HttpServletRequest request);
	/**
	 * Specific code for deny action
	 * The status of task after this operation is the final one, it must be changed inside the method
	 * The new status is not persisted
	 */
	abstract public void denyTaskAction(HttpServletRequest request);
}
