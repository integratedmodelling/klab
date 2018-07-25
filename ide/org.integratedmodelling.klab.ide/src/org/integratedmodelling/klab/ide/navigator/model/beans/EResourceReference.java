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

	public EResourceReference() {
	}

	public EResourceReference(ResourceReference other) {
		super(other);
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

}
