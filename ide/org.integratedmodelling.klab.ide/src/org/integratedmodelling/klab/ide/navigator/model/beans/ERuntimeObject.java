package org.integratedmodelling.klab.ide.navigator.model.beans;

public interface ERuntimeObject {
	
	ERuntimeObject getEParent();
	
	ERuntimeObject[] getEChildren(DisplayPriority displayPriority);
	
}
