package org.integratedmodelling.klab.components.network.algorithms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * HashMapList is a convenient class for HashMap when multiple values need to be
 * kept for each key HashMapList override HashMap where V is a List of V
 * 
 * @author Gilles Vuidel
 */
public class HashMapList<K, V> extends HashMap<K, List<V>> implements MapList<K, V> {

	private static final long serialVersionUID = -7757284542722893714L;

	/**
	 * Create an empty HashMapList
	 */
	public HashMapList() {
		super();
	}

	/**
	 * Add value to the key. If the key does not exist, create a new entry with a
	 * new ArrayList containing value
	 * 
	 * @param key
	 * @param value
	 */
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

}
