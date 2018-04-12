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

import java.util.List;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * Instantiators are contextualizers that can be provided for any direct observation and are called
 * to produce observations in a context.
 * 
 * @author ferdinando.villa
 *
 */
public interface IInstantiator extends IContextualizer {

  /**
   * Instantiate and return the target observations in the passed context. Those observations will
   * be independently resolved afterwards by the dataflow.
   * 
   * @param semantics the direct observable we must incarnate in the context.
   * @param context the context observation.
   *
   * @return a list of observations, possibly empty but never null.
   * @throws KlabException 
   */
  List<IObjectArtifact> instantiate(IObservable semantics, IComputationContext context) throws KlabException;

}
