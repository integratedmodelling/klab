package org.integratedmodelling.klab.api.data;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

/**
 * The result of adapting a resource to a callable function. Normally usable
 * with scalar resources, uses the adapter to extract values with expression
 * semantics.
 * 
 * @author ferdinando.villa
 *
 */
public interface IResourceCalculator<T> {

	/**
	 * Simple use case. for scalar use. Should also enable the situation where the
	 * arguments is a context and the needed parameters are computed from existing
	 * observations.
	 * 
	 * @param arguments
	 * @return
	 */
	T eval(IParameters<String> arguments, IMonitor monitor);

	/**
	 * Use case with a locator and a scope (scalar for scalar resources, but also
	 * with higher multiplicity). Should enable the situation where the arguments is
	 * a context and the needed parameters are computed from existing observations.
	 * 
	 * @param arguments
	 * @param locator
	 * @return
	 */
	T eval(IContextualizationScope scope, ILocator locator, IMonitor monitor);

	/**
	 * Produce the resource being computed.
	 * 
	 * @return
	 */
	IResource getResource();

}
