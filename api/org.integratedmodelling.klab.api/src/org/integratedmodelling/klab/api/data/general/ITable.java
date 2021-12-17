package org.integratedmodelling.klab.api.data.general;

import java.util.List;

import org.integratedmodelling.klab.api.data.IResource.Attribute;
import org.integratedmodelling.klab.api.knowledge.ICodelist;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;

/**
 * Unstructured n-dimensional table, where objects are addressed by 1+ keys of
 * any type and views can be extracted as maps, lists or sub-tables.
 * <p>
 * A table is an iterable of iterables, which may be in turn be iterate
 * iterables if the table is a hypertable with dimensions > 2. For now we assume
 * that the dimension is always 2, i.e. the implementation won't cover the
 * generic datacube.
 * 
 * @author Ferd
 *
 * @param <T>
 */
public interface ITable<T> extends Iterable<Iterable<?>> {

	public enum SearchOptions {
		UNIQUE, SORTED_ASCENDENT, SORTED_DESCENDENT
	}

	interface Filter {
		public enum Type {
			COLUMN_HEADER, ROW_HEADER/* , ATTRIBUTE_VALUE */, INCLUDE_COLUMNS/* , EXCLUDE_COLUMNS */,
			INCLUDE_ROWS/* , EXCLUDE_ROWS */, NO_RESULTS, COLUMN_EXPRESSION, COLUMN_MATCH, ROW_MATCH
		}

		Type getType();

		List<Object> getArguments();

		String getSignature();

		int getDimension();

		/**
		 * Return the same filter or a new one if the scope contains information that
		 * can localize it. For now this applies to concrete predicates when the filter
		 * may check an abstract one, but it may also do parameter substitution and the
		 * like.
		 * 
		 * @param scope
		 * @return
		 */
		Filter contextualize(IContextualizationScope scope);

		/**
		 * If true, the filter implies queries on the table and its results should be
		 * cached for speed for as long as the underlying table doesn't change.
		 * 
		 * @return
		 */
		boolean isCached();
	}

	/**
	 * The table dimensions, i.e. rows by the number of <em>value</em> columns.
	 * Could be >2 in length for a generic datacube, although at the moment not
	 * everything is ready for that kind of use and individual adapter may expect
	 * dimensionality <= 2.
	 * 
	 * @return
	 */
	int[] getDimensions();

	/**
	 * Size in terms of n. of rows and columns, independent of dimensions.
	 * 
	 * @return
	 */
	int[] size();

	/**
	 * Typed version of get(), which should endeavor to make any meaningful
	 * conversions as long as they are compatible with the storage. This is affected
	 * by any configured filters. If an aggregator is passed as a locator, it should
	 * be used to aggregate any multiple values.
	 * 
	 * @param cls
	 * @param scope    the scope to translate contextual filters. May be null.
	 * @param locators
	 * @return <E>
	 */
	<E> E get(Class<E> cls, IContextualizationScope scope, Object... locators);

	/**
	 * Return a map view of the table. Intended to subset the table based on the
	 * passed objects and return either another table, a map or a list. This is
	 * affected by any configured filters if the locators aren't passed.
	 * 
	 * @param <E>
	 * @param cls
	 * @param locators
	 * @return
	 */
	List<T> asList(Object... locators);

	/**
	 * Return a new table representing a filtered view of this one, creating a
	 * filter based on the parameters.
	 * 
	 * @param <E>
	 * @param cls
	 * @param locators
	 * @return
	 */
	ITable<T> filter(Filter.Type target, Object... locators);

	/**
	 * Return a new table representing a filtered view of this one, using an
	 * externally provided filter.
	 * 
	 * @param <E>
	 * @param cls
	 * @param locators
	 * @return
	 */
	ITable<T> filter(Filter filter);

	/**
	 * Return all the elements in a given row, disregarding any filters.
	 * 
	 * @param rowLocator either a row identifier or a 0-based index
	 * @return
	 */
	List<T> getRowItems(Object... rowLocator);

	/**
	 * Return all the elements in a given column, disregarding any filters.
	 * 
	 * @param columnLocator either a row identifier or a 0-based index
	 * @return
	 */
	List<T> getColumnItems(Object... columnLocator);

	/**
	 * Return the element at the given positions, disregarding any filters. Locators
	 * should be indices or headers if specified and unique.
	 * 
	 * @param rowLocator
	 * @param columnLocator
	 * @return
	 */
	T getItem(Object rowLocator, Object columnLocator);

	/**
	 * Configure a collection to collect all row or column indices scanned in the
	 * asList() call immediately succeeding this one. The indices collected
	 * 
	 * @param indices
	 * @return
	 */
	ITable<T> collectIndices(List<Integer> indices);

	/**
	 * Get column descriptor from column header (if no headers, 1-based "c<n>" can
	 * be used).
	 * 
	 * @param columnName
	 * @return
	 */
	Attribute getColumnDescriptor(String columnName);

	/**
	 * Get column descriptor based on 0-based index.
	 * 
	 * @param index
	 * @return
	 */
	Attribute getColumnDescriptor(int index);

	/**
	 * Return an identical table with no filters.
	 * 
	 * @return
	 */
	ITable<?> resetFilters();

	/**
	 * True if the table, after filtering if any, has at least one row and column of
	 * size() > 0.
	 * 
	 * @return
	 */
	boolean isEmpty();

	/**
	 * 
	 * @return
	 */
	List<Filter> getFilters();

	/**
	 * Contextualize all filters and any other info so that the returned table fits
	 * the passed context. This will be called when contextualizing, expecting one
	 * value from get() with an aggregator after that.
	 * 
	 * @param scope
	 * @return
	 */
	ITable<T> contextualize(IContextualizationScope scope);

	/**
	 * Any column may be associated to a codelist, which maps its values to others
	 * and may incarnate or represent an authority.
	 * 
	 * @param columnId
	 * @return the codelist for the passed column ID, or null
	 */
	ICodelist getCodelist(String columnId);

}
