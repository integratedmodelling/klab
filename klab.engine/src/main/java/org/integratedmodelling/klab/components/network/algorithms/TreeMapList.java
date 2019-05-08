package org.integratedmodelling.klab.components.network.algorithms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

/**
 * This class is a specialized TreeMap where values are List of values
 * 
 * @author Gilles Vuidel
 */
public class TreeMapList<K, V> extends TreeMap<K, List<V>> implements SortedMapList<K, V> {

	/**
	 * Create a new empty TreeMapList
	 */
	public TreeMapList() {
		super();
	}

	@Override
	public void putValue(K key, V value) {
		List<V> lst;
		if (!containsKey(key)) {
			lst = new ArrayList<V>();
			put(key, lst);
		} else {
			lst = get(key);
		}

		lst.add(value);
	}

	@Override
	public V removeElem(K key, int ind) {
		V val = get(key).remove(ind);
		if (get(key).isEmpty()) {
			remove(key);
		}

		return val;
	}

	@Override
	public boolean removeElem(K key, V v) {
		boolean b = get(key).remove(v);
		if (get(key).isEmpty()) {
			remove(key);
		}

		return b;
	}

	@Override
	public int totalSize() {
		int size = 0;
		for (List<V> lst : values()) {
			size += lst.size();
		}

		return size;
	}

	@Override
	public Collection<V> allValues() {
		ArrayList<V> list = new ArrayList<V>(totalSize());
		for (List<V> lst : values()) {
			list.addAll(lst);
		}

		return list;
	}

	/**
	 * Create a TreeMapList which contains all entry between from to to excluded.
	 * After return, modification on this is not reflect in the SortedMapList return
	 * 
	 * @return a copy of a part of this TreeMapList
	 */
	@Override
	public TreeMapList<K, V> subMapList(K from, K to) {
		TreeMapList<K, V> map = new TreeMapList<K, V>();
		for (K k : subMap(from, to).keySet()) {
			map.put(k, get(k));
		}

		return map;
	}
}
