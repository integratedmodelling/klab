package org.integratedmodelling.klab.components.network.algorithms;

import java.util.List;
import java.util.SortedMap;

/**
 * This interface is a specialized SortedMap where values are List of values
 * 
 * @see TreeMapList
 * @author gvuidel
 */
public interface SortedMapList<K, V> extends SortedMap<K, List<V>>, MapList<K, V> {

	/**
	 * Return a SortedMapList which contains all entry between from to to excluded.
	 * After return, modification on this is not reflect in the SortedMapList return
	 * 
	 * @return a copy of a part of this SortedMapList
	 */
	public SortedMapList<K, V> subMapList(K from, K to);

}
