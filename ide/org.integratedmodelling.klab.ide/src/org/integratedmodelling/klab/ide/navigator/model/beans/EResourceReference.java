package org.integratedmodelling.klab.ide.navigator.model.beans;


import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;

import org.integratedmodelling.klab.ide.Activator;
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
	private Map<String, String> formats = new LinkedHashMap<>();
	
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
		this.formats.putAll(other.getExportFormats());
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
	
    public Map<String, String> getExportFormats() {
    	Map<String, String> ret = new LinkedHashMap<>();
    	if (formats.isEmpty()) {
    		ret.putAll(Activator.klab().getResourceAdapter(getAdapterType()).getExportCapabilities());
    	} else {
    		ret.putAll(formats);
    	}
    	return ret;
    }

}
