package org.integratedmodelling.klab.hub.api;

import java.time.LocalDateTime;

abstract class IdentityModel extends GenericModel{
	
    LocalDateTime registrationDate;
    LocalDateTime lastConnection;
	
    public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}
    public void setLastConnection(LocalDateTime lastConnection) {
    	this.lastConnection = lastConnection;
	}
	public LocalDateTime getLastConnection() {
		return lastConnection;
	}
	public void setLastConnection() {
		lastConnection = LocalDateTime.now();
	}

}
