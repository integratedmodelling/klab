package org.integratedmodelling.klab.engine.runtime.api;

import java.util.Collection;

/**
 * Use to tag a contextualizer that picks dependencies from the context in ways
 * that can only be defined at initialization time. Used to build the dataflow.
 * 
 * @author ferdinando.villa
 *
 */
public interface IDependencyCollector {

	/**
	 * Return the name of all dependencies that have been used.
	 * 
	 * @return
	 */
	Collection<String> getDependencyNames();

}
