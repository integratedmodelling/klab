package org.integratedmodelling.klab.ide.navigator.model.beans;

import java.util.logging.Level;

import org.integratedmodelling.klab.api.runtime.rest.IDataflowReference;
import org.integratedmodelling.klab.rest.DataflowReference;

public class EDataflowReference implements IDataflowReference, ERuntimeObject {

	private IDataflowReference delegate;
	private ERuntimeObject parent;
	private String id;

	public EDataflowReference(DataflowReference reference, String id, ERuntimeObject parent) {
		this.delegate = reference;	
		this.parent = parent;
		this.id = id;
	}
	
	public String getTaskId() {
		return delegate.getTaskId();
	}

	public String getKdlCode() {
		return delegate.getKdlCode();
	}

	@Override
	public ERuntimeObject getEParent(DisplayPriority priority) {
		return parent;
	}

	@Override
	public ERuntimeObject[] getEChildren(DisplayPriority priority, Level level) {
		return new ERuntimeObject[] {};
	}
	
	@Override
	public String toString() {
	    return "[DATAFLOW for " + getTaskId() + "]";
	}


    @Override
    public boolean equals(Object o) {
        return o instanceof EDataflowReference && ((EDataflowReference)o).id.equals(this.id);
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
    
}
