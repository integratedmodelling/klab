package org.integratedmodelling.klab.engine.runtime.api;

import java.util.function.Consumer;

import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IStorage;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;

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

	/**
	 * Record a that significant modification happened in the passed transition.
	 * Calling this should record an activity in the history of the connected
	 * artifact.
	 * 
	 * @param time
	 */
	void touch(ITime time);

	/**
	 * Add a listener function to be called every time a temporal contextualization
	 * redefines the time of validity.
	 * 
	 * @param listener
	 */
	void addContextualizationListener(Consumer<ILocator> listener);

}
