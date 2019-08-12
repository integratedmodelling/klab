package org.integratedmodelling.klab.engine.runtime.api;

import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IStorage;

public interface IDataStorage<T> extends IStorage<T> {

	/**
	 * This is needed to satisfy the layering in State.
	 * 
	 * @param locator
	 * @return
	 */
	Object getObject(ILocator locator);

	/**
	 * * This is needed to satisfy the layering in State.
	 * 
	 * @param value
	 * @param locator
	 * @return the offset of the result
	 */
	long putObject(Object value, ILocator locator);

}
