/**
 * 
 */
/**
 * The services provided in this package give full API access to the k.LAB environment through a series of singletons
 * managers, which are only guaranteed to perform as advertised once an {@link org.integratedmodelling.klab.engine.Engine}
 * has been started. Each service implements one of the interfaces in org.integratedmodelling.klab.api.services, such as:
 * 
 * <pre>
 * enum Observables implements IObservableService {
 *      INSTANCE;
 *      ...
 * }
 * </pre>
 * 
 * so that each singleton can be referenced as
 * 
 * <pre>
 * Observables.INSTANCE.method(args);
 * </pre>
 * 
 * Test packages can use the singletons or employ dependency injection to inject a different implementation as needed.
 * 
 * @author Ferd
 * @see org.integratedmodelling.klab.api.services
 */
package org.integratedmodelling.klab;
