package org.integratedmodelling.klab.ide.navigator.model.beans;

import java.util.List;
import java.util.logging.Level;

import org.integratedmodelling.klab.utils.Pair;

public interface ERuntimeObject {
	
	ERuntimeObject getEParent(DisplayPriority displayPriority);
	
	ERuntimeObject[] getEChildren(DisplayPriority displayPriority, Level level);

    List<Pair<String, String>> getProperties();
	
}
