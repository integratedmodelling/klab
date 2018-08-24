package org.integratedmodelling.kim.model;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IKimLookupTable;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.api.IKimTable;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;

public class KimLookupTable extends KimStatement implements IKimLookupTable {

    private static final long serialVersionUID = -8962809767778643579L;

    List<String> arguments = new ArrayList<>();
    IKimTable table;
    int searchColumn = -1;
    IArtifact.Type lookupType;
    String error;
    
	public KimLookupTable(IKimTable table, List<String> arguments, IKimStatement parent) {
        super(((KimStatement)table).getEObject(), parent);
        this.table = table;
        this.arguments.addAll(arguments);
        int ncols = -1;
        boolean haveSearch = arguments.contains("?");
        // pad any needed argument with the most likely implied
        while (arguments.size() < ncols) {
        	arguments.add((ncols == 2 && arguments.size() == 1 && !haveSearch) ? "?" : "*");
        }
        for (int i = 0; i < arguments.size(); i++) {
        	if (arguments.get(i).equals("?")) {
        		searchColumn = i;
        		break;
        	}
        }
        
        // if no ? is given and the arguments are one less than the columns, the
        // last column is the search column
        if (searchColumn < 0 && arguments.size() == table.getColumnCount() - 1) {
            searchColumn = table.getColumnCount() - 1;
        }
        
		if (searchColumn >= 0) {
			for (int i = 0; i < table.getRowCount(); i++) {
				if (lookupType == null) {
					lookupType = table.getRow(i)[searchColumn].getType();
				} else if (table.getRow(i)[searchColumn].getType() != lookupType) {
					this.error = "the type of the objects in the search column must be uniform";
				}
			}
		}
    }

	public String getError() {
		return error;
	}
	
	@Override
	public List<String> getArguments() {
		return arguments;
	}

	@Override
	public Type getLookupType() {
		return this.lookupType;
	}

	@Override
	public IKimTable getTable() {
		return this.table;
	}

	@Override
	public int getLookupColumnIndex() {
		return searchColumn;
	}

}
