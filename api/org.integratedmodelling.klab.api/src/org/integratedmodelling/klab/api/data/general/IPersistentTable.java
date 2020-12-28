package org.integratedmodelling.klab.api.data.general;

import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

public interface IPersistentTable<K,V> extends IStructuredTable<V>, Iterable<V> {

	K store(V object, IMonitor monitor);
	
	V retrieve(K id);
	
	boolean delete(K id);
	
	boolean update(V object, IMonitor monitor);
	
	Iterable<V> query(String query);
	
	long count();

	long count(String query);

	boolean isEmpty();
}
