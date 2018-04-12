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

import org.integratedmodelling.klab.api.data.IStorageProvider;
import org.integratedmodelling.klab.api.runtime.IRuntimeProvider;
import org.integratedmodelling.klab.exceptions.KlabRuntimeException;

/**
 * The k.LAB runtime.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IRuntimeService {

  /**
   * <p>info.</p>
   *
   * @param o a {@link java.lang.Object} object.
   */
  void info(Object... o);

  /**
   * <p>warn.</p>
   *
   * @param o a {@link java.lang.Object} object.
   */
  void warn(Object... o);

  /**
   * <p>error.</p>
   *
   * @param o a {@link java.lang.Object} object.
   */
  void error(Object... o);

  /**
   * <p>debug.</p>
   *
   * @param o a {@link java.lang.Object} object.
   */
  void debug(Object... o);

  /**
   * Storage for states is provided by a component found in the classpath. If more storage
   * components are available, configuration must have been defined to choose it. This allows
   *
   * @return the storage provider.
   * @throws KlabRuntimeException if no storage provider is installed or there is more than one
   *         without appropriate configuration.
   */
  IStorageProvider getStorageProvider();

  /**
   * <p>getRuntimeProvider.</p>
   *
   * @return a {@link org.integratedmodelling.klab.api.runtime.IRuntimeProvider} object.
   */
  IRuntimeProvider getRuntimeProvider();

}
