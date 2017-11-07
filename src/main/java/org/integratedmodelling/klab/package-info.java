/**
 * 
 */
/**
 * The services provided in this package implement the k.LAB environment, available everywhere as singletons. A
 * singleton enum is provided for each of the services in org.integratedmodelling.klab.api.services, such as:
 * 
 * <pre>
 * enum Observables implements IObservableService {
 *      INSTANCE;
 *      ...
 * }
 * </pre>
 * 
 * so that each service singleton can be referenced as
 * 
 * <pre>
 * Observables.INSTANCE.method(args);
 * </pre>
 * 
 * Test packages can use dependency injection to swap the runtime to a different implementation as needed.
 * 
 * @author Ferd
 * @see org.integratedmodelling.klab.api.services
 */
package org.integratedmodelling.klab;
