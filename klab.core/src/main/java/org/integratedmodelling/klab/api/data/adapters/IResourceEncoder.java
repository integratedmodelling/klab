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

import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

/**
 * The Interface IResourceEncoder.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IResourceEncoder {
  
  /**
   * <p>getEncodedData.</p>
   *
   * @param resource a {@link org.integratedmodelling.klab.api.data.IResource} object.
   * @param geometry a {@link org.integratedmodelling.kim.api.data.IGeometry} object.
   * @param monitor for notifications and identity retrieval
   * @return a {@link org.integratedmodelling.klab.api.data.adapters.IKlabData} object.
   */
  IKlabData getEncodedData(IResource resource, IGeometry geometry, IMonitor monitor);
}
