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

import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * The resolver for a {@link IState} when the state must be resolved in a point-by-point fashion.
 * 
 * @author ferdinando.villa
 *
 */
public interface IStateResolver extends IContextualizer {

  /**
   * Return the individual value at the locator identified by
   * {@link IComputationContext#getGeometry() the context's geometry}. This will be called for as
   * many times as there are subdivisions in the scale.
   * 
   * @param observable the semantics for what is being computed and returned
   * @param context the computation context, located at the specific state
   * @return the computed value at the locator
   * @throws KlabException
   */
  Object resolve(IObservable observable, IComputationContext context) throws KlabException;

}
