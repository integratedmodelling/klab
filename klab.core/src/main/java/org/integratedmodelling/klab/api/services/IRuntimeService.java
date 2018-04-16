/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify it under the terms of the Affero
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root directory of the k.LAB
 * distribution (LICENSE.txt). If this cannot be found see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned in author tags. All
 * rights reserved.
 */
package org.integratedmodelling.klab.api.services;

import org.integratedmodelling.klab.api.data.IStorageProvider;
import org.integratedmodelling.klab.api.runtime.IRuntimeProvider;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * The k.LAB runtime.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IRuntimeService {

  /**
   * Storage for states is provided by a component found in the classpath. If more storage
   * components are available, configuration must have been defined to choose it. This allows
   *
   * @return the storage provider.
   * @throws KlabException if no storage provider is installed or there is more than one
   *         without appropriate configuration.
   */
  IStorageProvider getStorageProvider();

  /**
   * <p>
   * getRuntimeProvider.
   * </p>
   *
   * @return a {@link org.integratedmodelling.klab.api.runtime.IRuntimeProvider} object.
   */
  IRuntimeProvider getRuntimeProvider();

  /**
   * Return the JSON source code of a map containing all the JSON level 4 schemata for REST beans
   * indexed by Java class name. These should be harvested and built at runtime from the package
   * containing all resource beans. Called by JS code to validate resources before use.
   * 
   * @return the JSON schema source code
   */
  String getResourceSchema();

//  /**
//   * Call this whenever REST services must be started - i.e. to visualize data through the
//   * k.Explorer or to let clients connect. The details of how services are implemented are left to
//   * the implementation. Should be callable multiple times and have no overhead if the services have
//   * been started before. Spawns services as a new thread.
//   * 
//   * @return true if the services are started or were started before.
//   */
//  boolean requireNetworkServices();

}
