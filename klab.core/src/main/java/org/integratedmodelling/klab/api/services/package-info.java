/**
 * The services provided in this package represent the components of the k.LAB environment that are
 * available everywhere as singletons. In the engine API, these are implemented by singleton enums
 * using the service's name and providing the singleton through an INSTANCE field:
 * 
 * <pre>
 * enum Observables implements IObservableService {
 *      INSTANCE;
 *      ...
 * }
 * </pre>
 * 
 * so that API users can always call the services as
 * 
 * <pre>
 * Observables.INSTANCE.method(args);
 * </pre>
 * 
 * All of these services are meant to be used after an engine has been successfully started. Test
 * packages can use dependency injection to swap the runtime to a different implementation as
 * needed.
 * 
 * @author Ferd
 *
 */
package org.integratedmodelling.klab.api.services;
