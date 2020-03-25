package org.integratedmodelling.klab.hub.api;

import org.joda.time.DateTime;

abstract class IdentityModel extends GenericModel{
	
	DateTime registrationDate;
    DateTime lastConnection;
	
    public DateTime getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(DateTime registrationDate) {
		this.registrationDate = registrationDate;
	}
    public void setLastConnection(DateTime lastConnection) {
    	this.lastConnection = lastConnection;
	}
	public DateTime getLastConnection() {
		return lastConnection;
	}
	public void setLastConnection() {
		lastConnection = DateTime.now();
	}

}
