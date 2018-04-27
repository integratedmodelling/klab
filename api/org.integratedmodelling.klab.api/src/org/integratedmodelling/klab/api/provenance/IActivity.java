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
package org.integratedmodelling.klab.api.provenance;

import java.util.Optional;

/**
 * Actions are the edges of the provenance graph. In k.LAB, these are represented by the internal
 * type IActuator. Action type determines the role of their vertices and can be translated into OPM
 * relationships, which typically represent the inverse action. Actions may be linked to each other
 * in a causal graph that is independent from the primary graph. Actions that are not caused by
 * another action are called "primary" and can be obtained in chronological order from the
 * provenance graph.
 *
 * @author Ferd
 * @version $Id: $Id
 */
public interface IActivity {

  /**
   * <p>getStart.</p>
   *
   * @return a long.
   */
  long getStart();

  /**
   * <p>getEnd.</p>
   *
   * @return a long.
   */
  long getEnd();

  /**
   * If the action was caused by another action, return the action that caused it.
   *
   * @return a {@link java.util.Optional} object.
   */
  Optional<IActivity> getCause();

  /**
   * Actions are made by agents. We keep them with the actions and out of the graph.
   *
   * @return a {@link org.integratedmodelling.klab.api.provenance.IAgent} object.
   */
  IAgent getAgent();

}
