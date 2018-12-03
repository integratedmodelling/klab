package org.integratedmodelling.klab;

import java.util.HashMap;
import java.util.Map;

/**
 * The singleton that gives access to all other services. Most of them will only
 * be available (non-null) in a running engine.
 * 
 * @author Ferd
 *
 */
public enum Services {
	INSTANCE;

	Map<Class<?>, Object> catalog = new HashMap<>();

	@SuppressWarnings("unchecked")
	public <T> T getService(Class<T> serviceClass) {
		return (T) catalog.get(serviceClass);
	}

	public void registerService(Object service, Class<?> serviceClass) {
		catalog.put(serviceClass, service);
	}
}
