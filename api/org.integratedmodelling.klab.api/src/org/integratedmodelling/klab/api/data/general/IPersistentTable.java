package org.integratedmodelling.klab.api.data.general;

import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

public interface IPersistentTable<K,V> extends ITable<V>, Iterable<V> {

	K store(V object, IMonitor monitor);
	
	V retrieve(K id);
	
	boolean delete(K id);
	
	boolean update(K key, V object, IMonitor monitor);
	
	Iterable<V> query(String query);
	
	long count();

	long count(String query);

	boolean isEmpty();
}
