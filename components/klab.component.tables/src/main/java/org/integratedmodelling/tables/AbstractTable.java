package org.integratedmodelling.tables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Attribute;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.data.general.IExpression.CompilerOption;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.data.Aggregator;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.exceptions.KlabResourceAccessException;
import org.integratedmodelling.klab.rest.AttributeReference;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.integratedmodelling.klab.utils.Utils;

public abstract class AbstractTable<T> implements ITable<T> {

	static class FilterDescriptor implements Filter {

		Filter.Type filter;
		List<Object> locators = new ArrayList<>();
		Object matched = null;

		static FilterDescriptor stop() {
			return new FilterDescriptor(Filter.Type.NO_RESULTS, null);
		}

		public FilterDescriptor(Filter.Type filter, Object[] locators) {
			this.filter = filter;
			if (locators != null) {
				for (Object o : locators) {
					this.locators.add(o);
				}
			}
		}

		boolean matches(AbstractTable<?> table, Object o, int... location) {

			switch (filter) {
			case ATTRIBUTE_VALUE:
				break;
			case COLUMN_HEADER:
				if (locators != null && locators.size() > 0 && locators.get(0) instanceof Pattern) {
					Attribute attr = table.getColumnDescriptor(location[1]);
					if (attr != null) {
						String cname = attr.getName();
						Matcher matcher = ((Pattern) locators.get(0)).matcher(cname);
						if (matcher.matches()) {
							if (matcher.groupCount() > 0) {
								this.matched = matcher.group(1);
							}
							System.out.println("MATCHED " + cname + " with value " + this.matched + " and INDEX = " + attr.getIndex());
							return true;
						}
					}
				}
				break;
			case EXCLUDE_COLUMNS:
				break;
			case EXCLUDE_ROWS:
				break;
			case INCLUDE_COLUMNS:
				break;
			case INCLUDE_ROWS:
				break;
			case ROW_HEADER:
				break;
			case NO_RESULTS:
				break;
			case COLUMN_EXPRESSION:
				break;
			case COLUMN_MATCH:
				break;
			}

			return false;
		}

		Object filter(Object o) {
			if (filter == Filter.Type.NO_RESULTS) {
				return null;
			}
			if (matched != null) {
				o = matched;
				this.matched = null;
			}
			return o;
		}

		@Override
		public Type getType() {
			return filter;
		}

		@Override
		public List<Object> getArguments() {
			return locators;
		}
		
		@Override
		public String toString() {
			return "FilterDescriptor [filter=" + filter + ", locators=" + locators + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((filter == null) ? 0 : filter.hashCode());
			result = prime * result + ((locators == null) ? 0 : locators.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			FilterDescriptor other = (FilterDescriptor) obj;
			if (filter != other.filter)
				return false;
			if (locators == null) {
				if (other.locators != null)
					return false;
			} else if (!locators.equals(other.locators))
				return false;
			return true;
		}

	}

	protected IResource resource;
	Map<String, Attribute> attributes_ = null;
	Map<Integer, Attribute> attributesByIndex_ = null;
	List<Filter> filters = new ArrayList<>();
	Class<? extends T> valueClass;
	protected List<Integer> lastScannedIndices;
	protected boolean empty = false;

	public AbstractTable(IResource resource, Class<? extends T> cls) {
		this.resource = resource;
		this.valueClass = cls;
	}

	protected AbstractTable(AbstractTable<T> table) {
		this.resource = table.resource;
		this.attributes_ = table.attributes_;
		this.attributesByIndex_ = table.attributesByIndex_;
		this.filters.addAll(table.filters);
		this.valueClass = table.valueClass;
		this.empty = table.empty;
	}

	private void validateFilters() {
		// TODO
	}
	
	@Override
	public List<Filter> getFilters() {
		return filters;
	}

	protected abstract AbstractTable<T> copy();

	/**
	 * Builds the attribute set and adds the column index.
	 * 
	 * @return
	 */
	@Override
	public Attribute getColumnDescriptor(String columnName) {
		if (attributes_ == null) {
			buildAttributeIndex();
		}
		return attributes_.get(columnName);
	}

	@Override
	public Attribute getColumnDescriptor(int index) {
		if (attributesByIndex_ == null) {
			buildAttributeIndex();
		}
		return attributesByIndex_.get(index);
	}

	private void buildAttributeIndex() {
		attributes_ = new HashMap<>();
		attributesByIndex_ = new HashMap<>();
		for (Attribute a : resource.getAttributes()) {
			this.attributes_.put(a.getName(), a);
			int index = Integer.parseInt(resource.getParameters().get("column." + a.getName() + ".index").toString());
			((AttributeReference)a).setIndex(index);
			attributesByIndex_.put(index, a);
		}
	}

	@Override
	public int[] getDimensions() {
		return new int[] { Integer.parseInt(resource.getParameters().get("rows.data").toString()),
				Integer.parseInt(resource.getParameters().get("rows.data").toString()) };
	}

	@Override
	public T get(Object... locators) {

		return null;
	}

	@Override
	public Map<Object, T> asMap(Object... locators) {

		return null;
	}

	@Override
	public List<T> asList(Object... locators) {

		int row = -1;
		int col = -1;

		if (this.lastScannedIndices != null) {
			this.lastScannedIndices.clear();
		}
		
		if (locators != null) {
			if (locators.length == 2) {
				row = getIndex(locators[0], 0);
				col = getIndex(locators[1], 1);
			} else if (locators.length == 1) {
				col = getIndex(locators[0], 1);
			}
		}

		if (row < 0 && col < 0) {
			throw new KlabResourceAccessException(
					"CSV: need one or two indices or header names to turn a table resource into a list");
		}

		List<T> ret = new ArrayList<>();

		if (empty) {
			return ret;
		}

		if (col < 0) {
			// take all rows items for a given column
			int i = 0;
			for (Object o : getRowItems(row)) {
				boolean ok = true;
				for (Filter f : filters) {
					if (ok && ((FilterDescriptor) f).matches(this, o, row, i)) {
						o = ((FilterDescriptor) f).filter(o);
					} else {
						ok = false;
						break;
					}
				}
				if (ok) {
					ret.add(Utils.asType(o, valueClass));
					if (this.lastScannedIndices != null) {
						this.lastScannedIndices.add(i);
					}
				}
				i++;
			}
		} else if (row < 0) {
			// take all columns for a given row
			ret.addAll(getColumnItems(col));
		} else {
			// return a list with a single element for row,col, no filtering
			ret.add(getItem(row, col));
		}

		return ret;
	}

	/**
	 * Get an aggregated value that reflects the current filters, aggregating as
	 * necessary. For now this only works if columns and rows are positively chosen
	 * by filters - no filter means no result, unless the table has exactly one
	 * element.
	 * 
	 * @param aggregator may be null, but an exception will be thrown if aggregation
	 *                   is needed.
	 * @return
	 */
	public Object get(Aggregator aggregator) {

		/*
		 * TODO if all dimensions are 1, just return the only element we have.
		 */


			Set<Integer> filteredColumns = new LinkedHashSet<>();
			Set<Integer> filteredRows = new LinkedHashSet<>();

			Map<String, Object> attributeFilters = new HashMap<>();
			IExpression filterExpression = null;

			for (Filter filter : this.filters) {
				switch (filter.getType()) {
				case ATTRIBUTE_VALUE:
					for (int i = 0; i < filter.getArguments().size(); i++) {
						attributeFilters.put(filter.getArguments().get(i).toString(), filter.getArguments().get(++i));
					}
					break;
				case COLUMN_EXPRESSION:
					//  TODO store in filter to avoid recompiling each time
					filterExpression = Extensions.INSTANCE.compileExpression(filter.getArguments().get(0).toString(),
							Extensions.DEFAULT_EXPRESSION_LANGUAGE, CompilerOption.ForcedScalar,
							CompilerOption.IgnoreContext);
					break;
				case COLUMN_MATCH:
					break;
				case EXCLUDE_COLUMNS:
					break;
				case EXCLUDE_ROWS:
					break;
				case INCLUDE_COLUMNS:
					addIndices(filteredColumns, filter.getArguments());
					break;
				case INCLUDE_ROWS:
					addIndices(filteredRows, filter.getArguments());
					break;
				case COLUMN_HEADER:
				case ROW_HEADER:
					break;
				case NO_RESULTS:
					return null;
				}
			}

			if (!attributeFilters.isEmpty() || filterExpression != null) {
				for (int i = 0; i < getDimensions()[0]; i++) {
					List<?> row = getRowItems(i);
					if (matches(row, attributeFilters, filterExpression)) {
						filteredRows.add(i);
					}
				}
			}


		/*
		 * TODO this implies that a filter must exist - without filters, no items will
		 * be aggregated.
		 */
		if (filteredColumns.size() > 0 && filteredRows.size() > 0) {
			List<Object> ret = new ArrayList<>();
			for (int row : filteredRows) {
				for (int col : filteredColumns) {
					Object item = this.getItem(row, col);
					if (item != null) {
						ret.add(item);
					}
				}
			}

			if (aggregator == null && ret.size() > 1) {
				throw new KlabIllegalStateException(
						"filtered table produces multiple values but no aggregator is specified");
			}

			System.out.println("GOT " + ret);
			
			return ret.isEmpty() ? null : (ret.size() == 1 ? ret.get(0) : (aggregator.aggregate(ret)));
		}

		return null;
	}

	private boolean matches(List<?> row, Map<String, Object> attributeFilters, IExpression filterExpression) {

		if (row.size() != attributes_.size()) {
			return false;
		}
		
		boolean ok = true;
		if (attributeFilters != null) {
			for (String attribute : attributeFilters.keySet()) {
				Attribute attr = getColumnDescriptor(attribute);
				if (attr == null || !checkEquals(row.get(attr.getIndex()), attributeFilters.get(attribute))) {
					ok = false;
					break;
				}
			}
		}
		if (ok && filterExpression != null) {
			// TODO
		}
		
		return ok;
	}

	private boolean checkEquals(Object object1, Object object2) {
		if (object1 == null || object2 == null) {
			return object1 == null && object2 == null;
		}
		
		if (object1.equals(object2) || object1.toString().equals(object2.toString())) {
			return true;
		}
		
		if (object1 instanceof Number && object2 instanceof String) {
			try {
				if (NumberUtils.equal(((Number)object1).doubleValue(), Double.parseDouble((String)object2))) {
					return true;
				}
			} catch (Throwable t) {
				// just return false
			}
		}
		
		/*
		 * TODO check for classifier and/or numeric match
		 */
		
		return false;
	}

	private void addIndices(Set<Integer> collection, Collection<?> toadd) {
		for (Object o : toadd) {
			if (o instanceof Integer) {
				collection.add((Integer) o);
			} else if (o instanceof Collection) {
				addIndices(collection, (Collection<?>) o);
			}
		}
	}

	private int getIndex(Object object, int dimension) {
		if (object instanceof Integer) {
			return (Integer) object;
		} 
		else if (dimension == 1) {
			/*
			 * column descriptor
			 */
			Attribute attribute = getColumnDescriptor(object.toString());
			if (attribute != null) {
				return attribute.getIndex();
			}
		}
		// TODO
		return -1;
	}

	@Override
	public ITable<T> filter(Filter.Type target, Object... locators) {
		AbstractTable<T> ret = copy();
		if (target == Filter.Type.NO_RESULTS) {
			ret.empty = true;
		}
		ret.filters.add(new FilterDescriptor(target, locators));
		validateFilters();
		return ret;
	}

	@Override
	public ITable<T> filter(Filter filter) {
		AbstractTable<T> ret = copy();
		ret.filters.add(filter);
		validateFilters();
		return ret;
	}

	@Override
	public ITable<T> resetFilters() {
		AbstractTable<T> ret = copy();
		ret.filters.clear();
		return ret;
	}

	@Override
	public boolean isEmpty() {
		return empty /* TODO also check that we have columns and rows */;
	}

	@Override
	public <E> E get(Class<E> cls, Object... locators) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITable<T> collectIndices(List<Integer> indices) {
		this.lastScannedIndices = indices;
		return this;
	}

}
