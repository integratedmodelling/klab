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
package org.integratedmodelling.klab.api.observations;

import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.knowledge.IObservable;

/**
 * A {@link IState} is the semantic {@link IObservation} that specializes a non-semantic
 * {@link IDataArtifact data artifact}. Its {@link #getObservable() observable} is always a quality.
 * <p>
 * @author ferdinando.villa
 *
 */
public interface IState extends IObservation, IDataArtifact {

  /**
   * True if the state has the same value overall independent of scale.
   * 
   * @return true if constant
   */
  boolean isConstant();

  /**
   * True if the state is expected to change at every time transition. This depends on semantics and
   * context: it will be expected to change if the observable is affected by a process that exists
   * in the context. States are expected to be changeable even if not dynamic, but will define their
   * storage more conservatively (and less efficiently) if so.
   * 
   * @return true if dynamic
   */
  boolean isDynamic();

  /**
   * Return either the original state or a wrapper that will allow get/set of values in a specified
   * observation semantics.
   * 
   * @param observable an observable that must be identical semantically but may have different
   *        observation semantics, e.g. a "by" clause or different units/currencies.
   * @return the (possibly wrapped) state
   */
  IState as(IObservable observable);

}
