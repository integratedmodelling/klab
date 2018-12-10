package org.integratedmodelling.klab.ide.navigator.model.beans;


import java.util.logging.Level;

import org.integratedmodelling.klab.rest.Notification;
import org.integratedmodelling.klab.rest.ResourceReference;

/**
 * Augmented {@link ResourceReference} for runtime bookkeeping.
 * 
 * @author ferdinando.villa
 *
 */
public class EResourceReference extends ResourceReference {

	private boolean online;
	private boolean error;
	private boolean authorized;
	
	public EResourceReference() {
	}

	public EResourceReference(ResourceReference other) {
		super(other);
		// TODO
		this.authorized = true;
		for (Notification notification : other.getNotifications()) {
		    if (notification.getLevel().equals(Level.SEVERE.getName())) {
		        error = true;
		    }
		}
	}
	
	public EResourceReference(ResourceReference other, boolean online) {
		this(other);
		// TODO
		this.authorized = true;
		this.online = online;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public boolean isUnauthorized() {
		// TODO Auto-generated method stub
		return !authorized;
	}
	
	

}
