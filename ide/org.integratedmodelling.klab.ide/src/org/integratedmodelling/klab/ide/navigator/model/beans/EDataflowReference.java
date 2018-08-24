package org.integratedmodelling.klab.ide.navigator.model.beans;

import org.integratedmodelling.klab.api.runtime.rest.IDataflowReference;
import org.integratedmodelling.klab.rest.DataflowReference;

public class EDataflowReference implements IDataflowReference, ERuntimeObject {

	private IDataflowReference delegate;
	private ERuntimeObject parent;

	public EDataflowReference(DataflowReference reference, ERuntimeObject parent) {
		this.delegate = reference;	
		this.parent = parent;
	}
	
	public String getTaskId() {
		return delegate.getTaskId();
	}

	public String getKdlCode() {
		return delegate.getKdlCode();
	}

	@Override
	public ERuntimeObject getEParent() {
		return parent;
	}

	@Override
	public ERuntimeObject[] getEChildren(DisplayPriority priority) {
		return new ERuntimeObject[] {};
	}
	
	@Override
	public String toString() {
	    return "[DATAFLOW for " + getTaskId() + "]";
	}

}
