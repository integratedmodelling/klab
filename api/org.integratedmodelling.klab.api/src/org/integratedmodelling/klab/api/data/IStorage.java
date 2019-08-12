package org.integratedmodelling.klab.api.data;

import java.io.Closeable;

import org.integratedmodelling.klab.api.provenance.IArtifact;

/**
 * An appropriate store for arbitrary values that can be initialized with a
 * geometry and accessed in read and write using a locator. Storage is normally
 * called only with locators that map to exactly one offset.
 * 
 * @author ferdinando.villa
 *
 */
public interface IStorage<T> extends Closeable {

	/**
	 * Type-checked method to put a value at a locator.
	 * 
	 * @param value
	 * @param locator
	 */
	long put(T value, ILocator locator);

	/**
	 * Type-checked get.
	 * 
	 * @param locator
	 * @return
	 */
	T get(ILocator locator);
	
	/**
	 * The artifact type corresponding to the storage.
	 * 
	 * @return
	 */
	IArtifact.Type getType();

}
