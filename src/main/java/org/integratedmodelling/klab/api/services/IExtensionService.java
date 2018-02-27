package org.integratedmodelling.klab.api.services;

import java.util.Collection;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.extensions.component.IComponent;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;

/**
 * This service manages extensions, i.e. components and services that can be added to the system
 * through plug-ins. It also provides the global dictionary for service prototypes and manages their
 * execution from service calls (corresponding to functions in k.IM).
 * 
 * @author ferdinando.villa
 *
 */
public interface IExtensionService {

  /**
   * All components registered with the runtime, active or not.
   * 
   * @return all components
   */
  Collection<IComponent> getComponents();

  /**
   * Return a specific component. If active, it will have already been initialized.
   * 
   * @param componentId
   * @return the component, or null if unknown.
   */
  IComponent getComponent(String componentId);

  /**
   * Return the prototype for the named service or function.
   * 
   * @param service id
   * @return a prototype, or null if the service is unknown.
   */
  IPrototype getPrototype(String service);

  /**
   * Any k.IM function call stated in k.IM and contained in a k.IM object is executed here.
   * 
   * @param functionCall
   * @param monitor
   * @return the return value of the function
   * @throws KlabResourceNotFoundException if the function is unknown
   * @throws KlabException if any exception was thrown during evaluation
   */
  Object callFunction(IServiceCall functionCall, IMonitor monitor) throws KlabException;

}
