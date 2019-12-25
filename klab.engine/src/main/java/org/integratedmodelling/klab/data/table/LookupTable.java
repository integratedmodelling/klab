package org.integratedmodelling.klab.data.table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IKimLookupTable;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.api.data.classification.IClassifier;
import org.integratedmodelling.klab.api.data.classification.ILookupTable;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.utils.Pair;

public class LookupTable implements ILookupTable {

	Table<IClassifier> table;
	List<String> variables;
	IArtifact.Type type;
	int searchIndex;

	Map<IConcept, Integer> key;
	Map<String, Object> cache = new HashMap<>();

	class RowProxy {
		int row;
		RowProxy(int row) {
			this.row = row;
		}
	}
	
	public LookupTable(IKimLookupTable lookupTable) {

		this.table = Table.create(lookupTable.getTable());
		this.variables = lookupTable.getArguments();
		this.searchIndex = lookupTable.getLookupColumnIndex();
		this.type = lookupTable.getLookupType();

		if (this.type == Type.CONCEPT) {
			this.key = new LinkedHashMap<>();
			for (int i = 0; i < table.getRowCount(); i++) {
				this.key.put((IConcept) table.getRow(i)[searchIndex].asValue(null), i);
			}
		}

	}

	/**
	 * True if the table is functional, i.e. it matches one input to one output.
	 * 
	 * @return
	 */
	public boolean isKey() {
		return key != null;
	}

	@Override
	public ITable<IClassifier> getTable() {
		return table;
	}

	@Override
	public int reverseLookup(Object value) {
		return key.get(value);
	}

	@Override
	public int size() {
		return table.getRowCount();
	}

	@Override
	public int getResultColumn() {
		return searchIndex;
	}

	@Override
	public List<String> getLabels() {
		List<String> ret = new ArrayList<>();
		for (IConcept concept : key.keySet()) {
			ret.add(Concepts.INSTANCE.getDisplayName(concept));
		}
		return ret;
	}

	@Override
	public boolean isOrdered() {
		return key != null && ((IConcept) table.getRow(0)[searchIndex].asValue(null)).is(IKimConcept.Type.ORDERING);
	}

	@Override
	public List<Pair<Integer, String>> getAllValues() {
		List<Pair<Integer, String>> ret = new ArrayList<>();
		for (Entry<IConcept, Integer> entry : key.entrySet()) {
			ret.add(new Pair<>(entry.getValue(), Concepts.INSTANCE.getDisplayName(entry.getKey())));
		}
		return ret;
	}

	@Override
	public List<String> getArguments() {
		return variables;
	}

	@Override
	public Object lookup(IParameters<String> parameters, IContextualizationScope context) {

		StringBuffer s = new StringBuffer(1024);
		Object[] values = new Object[variables.size()];

		for (int i = 0; i < variables.size(); i++) {
			if (i == searchIndex || variables.get(i).charAt(0) == '*') {
				continue;
			}
			values[i] = parameters.get(variables.get(i));
			s.append("|");
			s.append(values[i] == null ? "null" : values[i].toString());
		}
		String key = s.toString();

		Object ret = cache.get(key);
		if (ret == null) {

			int rind = 0;
			boolean storeProxy = false;
			for (IClassifier[] row : table.getRows()) {
				boolean ok = true;
				for (int i = 0; i < variables.size(); i++) {
					if (i == searchIndex || variables.get(i).charAt(0) == '*') {
						continue;
					}
					if (!row[i].classify(values[i], context)) {
						ok = false;
						break;
					}
				}
				if (ok) {
					if (row[searchIndex].isComputed()) {
						storeProxy = true;
					}
					ret = row[searchIndex].asValue(context);
					break;
				}
				rind ++;
			}

			cache.put(key, ret == null ? Optional.empty() : (storeProxy ? new RowProxy(rind) : ret));

		} else if (ret instanceof RowProxy) {
			ret = table.getRows().get(((RowProxy)ret).row)[searchIndex].asValue(context);
		} else {
			ret = ret instanceof Optional ? null : ret;
		}

		return ret;
	}

	@Override
	public IArtifact.Type getResultType() {
		return type;
	}

	@Override
	public Object lookup(int index) {
		return (IConcept) table.getRow(index)[searchIndex].asValue(null);
	}

	public Collection<IKimExpression> getUniqueExpressions() {
		return table.getExpressions();
	}

	@Override
	public List<String> getSerializedObjects() {
		List<String> ret = new ArrayList<>();
		for (IConcept concept : key.keySet()) {
			ret.add(concept.getDefinition());
		}
		return ret;
	}

}
