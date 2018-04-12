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
package org.integratedmodelling.klab.api.observations.scale;

import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * A topological object can be compared with topological operators to another of a compatible class.
 *
 * @author Ferdinando Villa
 * @param <T> the generic type
 */
public interface ITopologicallyComparable<T> {

  /**
   * 
   * @param o
   * @return true if o is contained in this
   * @throws KlabException
   */
  boolean contains(T o) throws KlabException;

  /**
   * 
   * @param o
   * @return true if o overlaps this
   * @throws KlabException
   */
  boolean overlaps(T o) throws KlabException;

  /**
   * 
   * @param o
   * @return true if o intersects this
   * @throws KlabException
   */
  boolean intersects(T o) throws KlabException;

  ITopologicallyComparable<? extends T> merge(ITopologicallyComparable<?> other,
      LogicalConnector how);

}
