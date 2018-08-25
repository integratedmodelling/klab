package org.integratedmodelling.klab.ide.navigator.model.beans;

import java.util.logging.Level;

public interface ERuntimeObject {
	
	ERuntimeObject getEParent(DisplayPriority displayPriority);
	
	ERuntimeObject[] getEChildren(DisplayPriority displayPriority, Level level);
	
}
