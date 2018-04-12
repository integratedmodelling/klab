/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify
 * it under the terms of the Affero GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root
 * directory of the k.LAB distribution (LICENSE.txt). If this cannot be found 
 * see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned
 * in author tags. All rights reserved.
 */
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
