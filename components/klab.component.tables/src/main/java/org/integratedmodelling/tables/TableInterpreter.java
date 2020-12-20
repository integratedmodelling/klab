package org.integratedmodelling.tables;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;

/**
 * @author Ferd
 *
 */
public abstract class TableInterpreter implements ITableInterpreter {
	
	
	
	IGeometry mergeGeometry(IResource resource, IResource distributing) {

		/*
		 * space must create the union of all the represented shapes after joining through attribute
		 */
		
		return null;
	}


}
