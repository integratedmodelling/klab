package org.integratedmodelling.klab.api.data.general;

import java.util.List;
import java.util.Map;

/**
 * Unstructured n-dimensional table, where objects are addressed by 1+ keys of
 * any type and views can be extracted as maps, lists or sub-tables.
 * 
 * @author Ferd
 *
 * @param <T>
 */
public interface ITable<T> {

	public enum Filter {
		COLUMN_HEADER, ROW_HEADER, ATTRIBUTE_VALUE, INCLUDE_COLUMNS, EXCLUDE_COLUMNS, INCLUDE_ROWS, EXCLUDE_ROWS
	};

	int[] getDimensions();

	/**
	 * Get a single value from the table, passing enough locators to uniquely
	 * identify one. Locators may be indices, keys, expressions or anything else
	 * supported by the table implementation.
	 * 
	 * @param locators
	 * @return
	 */
	T get(Object... locators);

	/**
	 * Typed version of get(), which should endeavor to make any meaningful
	 * conversions as long as they are compatible with the storage. This is affected
	 * by any configured filters.
	 * 
	 * @param cls
	 * @param locators
	 * @return <E>
	 */
	<E> E get(Class<E> cls, Object... locators);

	/**
	 * Return a map view of the table. Intended to subset the table based on the
	 * passed objects and return either another table, a map or a list. This is
	 * affected by any configured filters.
	 * 
	 * @param <E>
	 * @param cls
	 * @param locators
	 * @return
	 */
	Map<Object, T> asMap(Object... columnLocators);

	/**
	 * Return a map view of the table. Intended to subset the table based on the
	 * passed objects and return either another table, a map or a list. This is
	 * affected by any configured filters.
	 * 
	 * @param <E>
	 * @param cls
	 * @param locators
	 * @return
	 */
	List<T> asList(Object... locators);

	/**
	 * Return a new table representing a filtered view of this one.
	 * 
	 * @param <E>
	 * @param cls
	 * @param locators
	 * @return
	 */
	ITable<T> filter(Filter target, Object... locators);

	/**
	 * Return all the elements in a given row, disregarding any filters.
	 * 
	 * @param rowLocator either a row identifier or a 0-based index
	 * @return
	 */
	List<T> getRowItems(Object rowLocator);

	/**
	 * Return all the elements in a given column, disregarding any filters.
	 * 
	 * @param columnLocator either a row identifier or a 0-based index
	 * @return
	 */
	List<T> getColumnItems(Object columnLocator);

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

}
