package org.integratedmodelling.klab.data.table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections4.keyvalue.MultiKey;
import org.apache.commons.collections4.map.MultiKeyMap;
import org.integratedmodelling.klab.api.data.IResource.Attribute;
import org.integratedmodelling.klab.api.data.general.IReducible;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.data.general.ITable.Filter.Type;
import org.integratedmodelling.klab.api.knowledge.ICodelist;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.data.Aggregator;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.utils.Utils;

/**
 * This is the table that gets into states when a IArtifact.Type.TABLE is the
 * representation type in a state. The table is in fact a multiple-key hash with
 * one and only one value column of type T, and is optimized for size and speed
 * of comparison. The keys can be of any type, while T describes the type of the
 * value. Like anything that is in a state, it can be reduced to a single atomic
 * value for display or conventional export. (IReducible interface TBI).
 * 
 * @author Ferd
 *
 * @param <T>
 */
public class TableValue implements ITable<Object>, IReducible {

	Map<String, Metadata> metadata = new HashMap<>();
	MultiKeyMap<String, Object> data;
	Map<String, ICodelist> codelists = new HashMap<>();
	Object scalar;
	private Aggregator aggregator;

	private TableValue() {
	}

	/**
	 * 
	 * @param data       the data, either a list of values or a list of lists, each
	 *                   a row indexed by allFields
	 * @param allFields  may be more than keyFields + valueField; those that aren't
	 *                   in there must be aggregated when keys are same at reduce()
	 * @param keyFields  fields whose identity we need to preserve
	 * @param valueField the key holding the value to report at reduce()
	 * @param codelists  any codelists for the key fields
	 * @param aggregator an aggregator to use when the keys match for multiple
	 *                   values (or there are no keys)
	 */
	public TableValue(List<Object> data, List<String> allFields, Set<String> keyFields, String valueField,
			Map<String, ICodelist> codelists, Aggregator aggregator) {

		this.codelists = codelists;
		this.aggregator = aggregator;

		List<Object> scalarData = null;

		for (Object o : data) {
			if (keyFields != null && !keyFields.isEmpty() && o instanceof List && valueField != null) {
				if (this.data == null) {
					this.data = new MultiKeyMap<>();
				}

				List<?> row = (List<?>) o;
				int i = 0;
				/*
				 * 
				 */
				List<String> keys = new ArrayList<>();
				Object value = null;
				for (String field : allFields) {
					if (keyFields.contains(field)) {
						keys.add(row.get(i) == null ? "null" : row.get(i).toString());
					}
					if (valueField.equals(field)) {
						value = row.get(i);
					}
					i++;
				}

				MultiKey<String> key = new MultiKey<>(keys.toArray(new String[keys.size()]));
				if (this.data.containsKey(key)) {
					Object current = this.data.get(key);
					if (current instanceof List) {
						((List<Object>) current).add(value);
					} else {
						List<Object> crn = new ArrayList<>();
						crn.add(current);
						crn.add(value);
						this.data.put(key, crn);
					}
				} else {
					this.data.put(key, value);
				}

			} else {
				if (scalarData == null) {
					scalarData = new ArrayList<>();
				}
				scalarData.add(o);
			}
		}

		if (scalarData != null) {
			this.scalar = scalarData;
		}
		
		if (aggregator != null) {
			if (scalar != null) {
				if (scalar instanceof Collection) {
					scalar = aggregator.aggregate((Collection<?>) scalar);
				}
			} else if (this.data != null) {
				for (Entry<MultiKey<? extends String>, Object> entry : this.data.entrySet()) {
					if (entry.getValue() instanceof Collection) {
						entry.setValue(aggregator.aggregate((Collection<?>) entry.getValue()));
					}
				}
			}
		}
	}

	@Override
	public Iterator<Iterable<?>> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getDimensions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E> E get(Class<E> cls, IContextualizationScope scope, Object... locators) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> asList(Object... locators) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITable<Object> filter(Type target, Object... locators) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITable<Object> filter(Filter filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> getRowItems(Object... rowLocator) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> getColumnItems(Object... columnLocator) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getItem(Object rowLocator, Object columnLocator) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITable<Object> collectIndices(List<Integer> indices) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Attribute getColumnDescriptor(String columnName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Attribute getColumnDescriptor(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITable<?> resetFilters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		return scalar == null && (data == null || data.isEmpty());
	}

	@Override
	public List<Filter> getFilters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITable<Object> contextualize(IContextualizationScope scope) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] size() {
		return null;
	}

	@Override
	public ICodelist getCodelist(String columnId) {
		return this.codelists == null ? null : this.codelists.get(columnId);
	}

	public IMetadata getColumnMetadata(String columnId) {
		return metadata.get(columnId);
	}

	@Override
	public String getSignature() {
		return null;
	}

	@Override
	public Object reduce(Class<?> cls, boolean forceReduction) {

		if (isEmpty()) {
			return null;
		}
		
		if (scalar != null) {
			return Utils.asType((scalar instanceof Collection && forceReduction && aggregator != null
					? aggregator.aggregate((Collection<?>) scalar)
					: scalar), cls);
		} else if (this.data != null && this.data.size() == 1) {
			return this.data.get(this.data.keySet().iterator().next());
		} else if (this.data != null && forceReduction && aggregator != null) {
			return aggregator.aggregate(this.data.values());
		}

		return this;

	}

	public static TableValue empty() {
		return new TableValue();
	}

}
