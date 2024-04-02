package org.integratedmodelling.klab.hub.api;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.integratedmodelling.klab.auth.Role;
import org.integratedmodelling.klab.hub.tasks.enums.TaskStatus;
import org.integratedmodelling.klab.hub.tasks.enums.TaskType;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Document(collection = "Tasks")
public abstract class Task {

	@Id
	String id;
	
	String user;
	
	LocalDateTime issued;
    
	LocalDateTime closed;
    
    @Enumerated(EnumType.STRING)
    Role roleRequirement;
	
    @Enumerated(EnumType.STRING)
    TaskStatus status;
    
    @Enumerated(EnumType.STRING)
    TaskStatus parentStatus;
    
    List<String> log = new ArrayList<String>();
    
    LocalDateTime expirationDate;
    
    /**
     * The next task to be accepted or deny
     */
    @Reference
    List<Task> next = new ArrayList<Task>();
    
	/**
     * If true, after create, we do accept
     */
    boolean autoAccepted;
    
    @JsonInclude()
    @Transient
    private TaskType type;
    
    public Task() {
    	this(null, null);
    }
    
    public Task(Role roleRequirement) {
    	this(roleRequirement, null);
    }
    
    public Task(TaskStatus parentStatus) {
    	this(null, parentStatus);
    }
    protected Task(Role roleRequirement, TaskStatus parentStatus) {
    	this.setUser(((KeycloakPrincipal<KeycloakSecurityContext>) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getKeycloakSecurityContext().getToken().getPreferredUsername());
    	this.setRoleRequirement(roleRequirement);;
    	this.setIssued();
		this.setStatus(TaskStatus.pending);
		this.setParentStatus(parentStatus);
		this.setType(); // force to set the type
		// this.setDescription(this.getType().name());
    }

	public String getId() {
		return id;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDateTime getIssued() {
		return issued;
	}

	public void setIssued() {
		this.issued = LocalDateTime.now();
	}

	public LocalDateTime getClosed() {
		return closed;
	}

	public void setClosed() {
		this.closed = LocalDateTime.now();
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public LocalDateTime getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDateTime expirationDate) {
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
	 * @return the deniedMessage
	 */
	public List<String> getLog() {
		return log;
	}

	/**
	 * @param deniedMessage the deniedMessage to set
	 */
	public void addToLog(String message) {
		this.log.add(message);
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
	 * @param request the request, used if needed to check roles
	 * @throws DeniedException 
	 */
	abstract public void acceptTaskAction(HttpServletRequest request);
	/**
	 * Specific code for deny action
	 * The status of task after this operation is the final one, it must be changed inside the method
	 * The new status is not persisted
	 * @param request the request, used if needed to check roles
	 * @return message is necessary or null
	 */
	abstract public void denyTaskAction(HttpServletRequest request, String message);
	
	/**
	 * Return the type
	 * @return the TaskType, 
	 */
	public TaskType getType() {
		return type;
	}
	
	public void setType(TaskType type) {
		this.type = type;
	}
	
	/**
	 * Added to force implementation to set the type, is called in constructor
	 */
	public abstract void setType();

}
