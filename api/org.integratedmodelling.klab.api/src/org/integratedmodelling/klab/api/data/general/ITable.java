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
	 * conversions as long as they are compatible with the storage.
	 * 
	 * @param cls
	 * @param locators
	 * @return
	<E> E get(Class<E> cls, Object... locators);

	/**
	 * Return a map view of the table. Intended to subset the table based on the
	 * passed objects and return either another table, a map or a list.
	 * 
	 * @param <E>
	 * @param cls
	 * @param locators
	 * @return
	 */
	Map<Object, T> asMap(Object... locators);

	/**
	 * Return a map view of the table. Intended to subset the table based on the
	 * passed objects and return either another table, a map or a list.
	 * 
	 * @param <E>
	 * @param cls
	 * @param locators
	 * @return
	 */
	List<T> asList(Object... locators);

	/**
	 * Return a map view of the table. Intended to subset the table based on the
	 * passed objects and return either another table, a map or a list.
	 * 
	 * @param <E>
	 * @param cls
	 * @param locators
	 * @return
	 */
	ITable<T> filter(Object... locators);

}
