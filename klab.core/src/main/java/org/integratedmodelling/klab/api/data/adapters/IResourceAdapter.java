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
package org.integratedmodelling.klab.api.data.adapters;

import org.integratedmodelling.klab.api.extensions.ResourceAdapter;
import org.integratedmodelling.klab.api.extensions.component.Initialize;
import org.integratedmodelling.klab.api.extensions.component.Shutdown;

/**
 * A {@code IResourceAdapter} is the interface for a plug-in providing a new adapter for a resource
 * type. A class implementing {@code IResourceAdapter} must be annotated with a
 * {@link ResourceAdapter} annotation in order to be discovered by the runtime.
 * 
 * The implementing class may specify initialization and finalization methods by annotating them
 * with the {@link Initialize} and {@link Shutdown} annotations also used for components.
 * 
 * @author Ferd
 *
 */
public interface IResourceAdapter {

  /**
   * Produce a new instance of the {@link IResourceValidator} for this resource type.
   *
   * @return
   */
  IResourceValidator getValidator();

  /**
   * Produce a new instance of the {@link IResourcePublisher} for this resource type.
   * 
   * @return
   */
  IResourcePublisher getPublisher();

  /**
   * Produce a new instance of the {@link IResourceEncoder} for this resource type.
   * 
   * @return
   */
  IResourceEncoder getEncoder();
  
}
