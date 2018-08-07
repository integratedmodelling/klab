package org.integratedmodelling.klab.data.table;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IKimLookupTable;
import org.integratedmodelling.klab.api.data.classification.IClassifier;
import org.integratedmodelling.klab.api.data.classification.ILookupTable;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.utils.Pair;

public class LookupTable implements ILookupTable {

	protected List<IClassifier[]> rows = new ArrayList<>();
	protected List<String> headers = null;

	public LookupTable(IKimLookupTable lookupTable) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ITable<?> getTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int reverseLookup(Object value) {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<String> getLabels() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isOrdered() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Pair<Object, String>> getAllValues() {
		// TODO Auto-generated method stub
		return null;
	}

}
