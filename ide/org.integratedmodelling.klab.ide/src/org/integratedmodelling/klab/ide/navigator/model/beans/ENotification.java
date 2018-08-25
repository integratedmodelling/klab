package org.integratedmodelling.klab.ide.navigator.model.beans;

import java.util.logging.Level;

import org.integratedmodelling.klab.rest.Notification;

public class ENotification extends Notification implements ERuntimeObject {
	
	private ETaskReference parent;
	private boolean continuation;
	private String id;
	
	public ENotification(String id) {
	    this.id = id;
	}
	
	@Override
	public ERuntimeObject getEParent(DisplayPriority priority) {
		return parent;
	}

	@Override
	public ERuntimeObject[] getEChildren(DisplayPriority priority, Level level) {
		return new ERuntimeObject[] {};
	}
	
	public void setParent(ETaskReference parent) {
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
        return o instanceof ENotification && ((ENotification)o).id.equals(this.id);
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
    

}
