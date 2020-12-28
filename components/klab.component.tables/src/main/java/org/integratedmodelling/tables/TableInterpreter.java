package org.integratedmodelling.tables;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.general.ITable;

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

	@Override
	public void categorize(IResource resource, IParameters<String> parameters) {
		// TODO Auto-generated method stub
		ITable<?> table = this.getTable(resource, null);
		for (Object o : table.asList(parameters.get("dimension"))) {
			
		}
		System.out.println("TORTELLO");
	}

}
