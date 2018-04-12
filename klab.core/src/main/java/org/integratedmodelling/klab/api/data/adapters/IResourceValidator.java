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

import java.net.URL;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

/**
 * The Interface IResourceValidator.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IResourceValidator {

  /**
   * Validate the resource pointed to by the URL and tagged with the passed user-provided data
   * (possibly empty). Returns a builder that will be used to produce the resource to be published
   * or to report any errors resulting from unsuccessful validation.
   *
   * @param url a {@link java.net.URL} object.
   * @param userData a {@link org.integratedmodelling.kim.api.IParameters} object.
   * @param monitor for notifications and identity retrieval
   * @return a builder for the resource, containing any validation errors. Do not return null.
   */
  Builder validate(URL url, IParameters userData, IMonitor monitor);

}
