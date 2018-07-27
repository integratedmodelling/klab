package org.integratedmodelling.klab.ide.navigator.model.beans;

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
	
	public EResourceReference() {
	}

	public EResourceReference(ResourceReference other) {
		super(other);
	}
	
	public EResourceReference(ResourceReference other, boolean online) {
		super(other);
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
	
	

}
