package org.integratedmodelling.tables;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Attribute;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.exceptions.KlabResourceAccessException;
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

	}

	protected IResource resource;
	Map<String, Attribute> attributes_ = null;
	Map<Integer, Attribute> attributesByIndex_ = null;
	List<Filter> filters = new ArrayList<>();
	Class<? extends T> valueClass;
	protected List<Integer> lastScannedIndices;
	protected boolean empty = false;
	
	/*
	 * these are only set as a result of filtering
	 */
	Set<Integer> filteredRows;
	Set<Integer> filteredColumns;
	
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Object, T> asMap(Object... locators) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> asList(Object... locators) {

		int row = -1;
		int col = -1;

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
					if (ok && ((FilterDescriptor)f).matches(this, o, row, i)) {
						o = ((FilterDescriptor)f).filter(o);
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

	private int getIndex(Object object, int dimension) {
		if (object instanceof Integer) {
			return (Integer) object;
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
