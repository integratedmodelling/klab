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
package org.integratedmodelling.klab.api.model.contextualization;

import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * A Resolver is a {@link org.integratedmodelling.klab.api.model.contextualization.IContextualizer}
 * that <i>explains</i> an existing observation by ensuring that it conforms to its definition.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 * @param <T> the observation type resolved
 */
public abstract interface IResolver<T extends IArtifact> extends IContextualizer {

  /**
   * Called once per temporal transition for the scale and the geometry of the observation being
   * resolved.
   *
   * @param ret the observation being resolved.
   * @param context the runtime context of the computation.
   * @return the final observation - either the same passed or a new one if mediation was necessary.
   * @throws org.integratedmodelling.klab.exceptions.KlabException
   */
  T resolve(T ret, IComputationContext context) throws KlabException;

}
