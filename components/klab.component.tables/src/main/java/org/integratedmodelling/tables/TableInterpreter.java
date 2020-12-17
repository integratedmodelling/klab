package org.integratedmodelling.tables;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;


public abstract class TableInterpreter implements ITableInterpreter {
	
	
	IGeometry mergeGeometry(IResource resource, IResource distributing) {
		return null;
	}


}
