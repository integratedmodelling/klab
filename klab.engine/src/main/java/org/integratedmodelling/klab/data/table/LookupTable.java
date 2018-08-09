package org.integratedmodelling.klab.data.table;

import java.util.List;

import org.integratedmodelling.kim.api.IKimLookupTable;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.classification.IClassifier;
import org.integratedmodelling.klab.api.data.classification.ILookupTable;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.utils.Pair;

public class LookupTable implements ILookupTable {

	Table<IClassifier> table;
	List<String> variables;
	IArtifact.Type type;
	int searchIndex;
	
	public LookupTable(IKimLookupTable lookupTable) {

		this.table = Table.create(lookupTable.getTable());
		this.variables = lookupTable.getArguments();
		this.searchIndex = lookupTable.getLookupColumnIndex();
		this.type = lookupTable.getLookupType();
		
		// analyze the vars and establish if we are functional and if the result column is a possible key, in which
		// case build the necessary structures to support value indexing
		
	}

	/**
	 * True if the table is functional, i.e. it matches one input to one output.
	 * 
	 * @return
	 */
	public boolean isKey() {
		return false;
	}
		
	@Override
	public ITable<IClassifier> getTable() {
		return table;
	}

	@Override
	public int reverseLookup(Object value) {
		return -1;
	}

	@Override
	public int size() {
		return table.getRowCount();
	}

	@Override
	public List<String> getLabels() {
		return null;
	}

	@Override
	public boolean isOrdered() {
		return false;
	}

	@Override
	public List<Pair<Object, String>> getAllValues() {
		return null;
	}

	@Override
	public List<String> getArguments() {
		return variables;
	}

	@Override
	public Object lookup(IParameters<String> parameters, IComputationContext context) {
	    
        for (IClassifier[] row : table.getRows()) {
            boolean ok = true;
            for (int i = 0; i < variables.size(); i++) {
            	if (i == searchIndex || variables.get(i).charAt(0) == '*') {
            		continue;
            	}
                if (!row[i].classify(parameters.get(variables.get(i)), context)) {
                    ok = false;
                    break;
                }
            }
            if (ok) {
                return row[searchIndex].asValue();
            }
        }
		return null;
	}
	
	@Override
	public IArtifact.Type getResultType() {
		return type;
	}

}
