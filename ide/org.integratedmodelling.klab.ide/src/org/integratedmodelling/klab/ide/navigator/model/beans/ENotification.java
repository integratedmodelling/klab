package org.integratedmodelling.klab.ide.navigator.model.beans;

import org.integratedmodelling.klab.rest.Notification;

public class ENotification extends Notification implements ERuntimeObject {
	
	private ERuntimeObject parent;
	private boolean continuation;
	
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
	
    @Override
    public String toString() {
        return "[NOTIFICATION " + getLevel() + " " + getMessage() + "]";
    }

    /**
     * If true, the notification continues a previous one that did not fit on
     * one line.
     * 
     * @return
     */
    public boolean isContinuation() {
        return continuation;
    }

    public void setContinuation(boolean continuation) {
        this.continuation = continuation;
    }
    
    @Override
    public boolean equals(Object o) {
        return o instanceof ENotification && ((ENotification)o).getMessage() == this.getMessage();
    }
    
    @Override
    public int hashCode() {
        return getMessage().hashCode();
    }
    

}
