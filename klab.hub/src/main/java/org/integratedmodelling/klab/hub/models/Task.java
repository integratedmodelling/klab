package org.integratedmodelling.klab.hub.models;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;

import org.integratedmodelling.klab.hub.models.tokens.ClickbackToken;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "AdminTasks")
public class Task {

	@Id
	String id;
	
	@NotEmpty
	String requestee;
	
	@NotEmpty
	ClickbackToken token;
	
    DateTime issued;
    
    DateTime closed;
    
    @Enumerated(EnumType.STRING)
    Role roleRequirement;
	
    @Enumerated(EnumType.STRING)
    TaskStatus status;
	
    DateTime expirationDate;

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

	public ClickbackToken getToken() {
		return token;
	}

	public void setToken(ClickbackToken token) {
		this.token = token;
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
    
}
