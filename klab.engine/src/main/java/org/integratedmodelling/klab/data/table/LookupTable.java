package org.integratedmodelling.klab.data.table;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IKimClassifier;
import org.integratedmodelling.kim.api.IKimLookupTable;
import org.integratedmodelling.klab.api.data.classification.IClassifier;
import org.integratedmodelling.klab.api.data.classification.ILookupTable;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.data.classification.Classifier;
import org.integratedmodelling.klab.utils.Pair;

public class LookupTable implements ILookupTable {

	Table<IClassifier> table;
	List<String> variables;
	
	public LookupTable(IKimLookupTable lookupTable) {

		List<IClassifier[]> rows = new ArrayList<>();
		List<String> headers = lookupTable.getHeaders();
		
		for (int i = 0; i < lookupTable.getRowCount(); i++) {
			IClassifier[] row = new IClassifier[lookupTable.getColumnCount()];
			int y = 0;
			for (IKimClassifier element : lookupTable.getRow(i)) {
				row[y] = new Classifier(element);
				y++;
			}
			rows.add(row);
		}
		
		this.table = new Table<IClassifier>(rows, headers);
		this.variables = lookupTable.getArguments();
		
		// analyze the vars and establish if we are functional
		
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

}
