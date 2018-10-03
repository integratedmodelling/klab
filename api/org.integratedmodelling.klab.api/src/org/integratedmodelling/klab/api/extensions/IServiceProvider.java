package org.integratedmodelling.klab.api.extensions;

import java.util.Collection;

/**
 * If a component is a provider of services, it will be asked to produce the
 * corresponding prototypes when loaded. This is a revision of the strategy
 * based on storing KDL files along with the component class, which is causing
 * unexplained issues with classpath scanning not working in JARs compiled on
 * Linux.
 * 
 * @author Ferd
 *
 */
public interface IServiceProvider {

	/**
	 * Return all the relative paths to KDL resources declaring the services
	 * provided by this component.
	 * 
	 * @return
	 */
	Collection<String> getServiceDefinitions();

}
