package org.integratedmodelling.klab.components.network.algorithms;


import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * MapList is a convenient interface for Map when multiple values need to be kept for each key
 * MapList extends Map where V is a List of V
 * 
 * @author Gilles Vuidel
 */
public interface MapList<K, V> extends Map<K, List<V>> {
    
    /**
     * Add value to the key.
     * If the key does not exist, create a new entry with a new List containing value
     * @param key
     * @param value 
     */
    public void putValue(K key, V value);

    /**
     * Remove the value at index ind in the list associated with the key.
     * If the list become empty the key is removed from the map
     * @param key
     * @param ind
     * @return the value removed
     */
    public V removeElem(K key, int ind);
    
    /**
     * Remove the first element equals to v in the list associated with the key.
     * If the list become empty the key is removed from the map
     * @param key
     * @param v the value to remove
     * @return true if an element has been removed
     */
    public boolean removeElem(K key, V v);

    /**
     * Return the sum of all list size.
     * @return the number of elements V stored in this HashMapList
     */
    public int totalSize();

    /**
     * Create a collection which contains all values V
     * after return modification on this MapList is not reflect in the collection return
     * @return a copy of all values contained in this MapList
     */
    public Collection<V> allValues();
}

