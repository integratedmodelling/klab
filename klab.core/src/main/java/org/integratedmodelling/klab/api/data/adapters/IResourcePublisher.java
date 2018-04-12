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

import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;

// TODO: Auto-generated Javadoc
/**
 * The Interface IResourcePublisher.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IResourcePublisher {

  /**
   * Publish a local resource, which must have no errors. If no errors happen during publishing,
   * produce a new public IResource with a valid URN, ready for storage in the public resource
   * catalog.
   *
   * @param localResource the local resource
   * @param monitor for notifications and identity retrieval
   * @return a new resource. If errors happen, throw an exception; if the function returns, the
   *         resource must be valid.
   * @throws org.integratedmodelling.klab.exceptions.KlabException if the passed resource is not local, has errors, or anything happens
   *         during publication.
   */
  public IResource publish(IResource localResource, IMonitor monitor) throws KlabException;

}
