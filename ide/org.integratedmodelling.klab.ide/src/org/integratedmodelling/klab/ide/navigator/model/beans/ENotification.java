package org.integratedmodelling.klab.ide.navigator.model.beans;

import org.integratedmodelling.klab.rest.Notification;

public class ENotification extends Notification implements ERuntimeObject {
	
	private ERuntimeObject parent;
	
	@Override
	public ERuntimeObject getEParent() {
		return parent;
	}

	@Override
	public ERuntimeObject[] getEChildren(DisplayPriority priority) {
		return new ERuntimeObject[] {};
	}
	
	public void setParent(ERuntimeObject parent) {
		this.parent = parent;
	}
}
