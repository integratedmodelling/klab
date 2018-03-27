/*******************************************************************************
 * Copyright (C) 2007, 2015:
 * 
 * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any other
 * authors listed in @author annotations
 *
 * All rights reserved. This file is part of the k.LAB software suite, meant to enable modular,
 * collaborative, integrated development of interoperable data and model components. For details,
 * see http://integratedmodelling.org.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * Affero General Public License Version 3 or any later version.
 *
 * This program is distributed in the hope that it will be useful, but without any warranty; without
 * even the implied warranty of merchantability or fitness for a particular purpose. See the Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the Affero General Public License along with this program; if
 * not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA. The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.api.resolution;

import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.ITopologicallyComparable;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * A {@code ICoverage} A coverage is a scale that keeps coverage information compared with other
 * extents along with its own extents. It represents the total coverage for an observation or a
 * computation during or after resolution. Like a scale, a coverage may be empty, in which case it
 * cannot be relied on for any other method call. A non-empty coverage can only be created from a
 * scale, passing an initial coverage. It will store the scale internally. Successive merge
 * operations do not change the underlying scale but will modify the fraction of the original
 * extents that is covered.
 * <p>
 * A {@code ICoverage} redefines the {@link IScale#merge(IScale, LogicalConnector)} method to only
 * perform a union when the resulting coverage adds enough coverage.
 * <p>
 * TODO Partial scale specifications, such as those only mentioning a specific resolution or a range
 * of extents, should also be represented as ICoverage and returned by extent functions with partial
 * specifications. Scales should be able to merge an extent and return either a coverage or a
 * complete scale.
 * <p>
 * 
 * @author Ferd
 *
 */
public interface ICoverage extends IScale {

  /**
   * Return the proportion of total coverage as a double 0-1. It is the product of the coverages for
   * all the extents.
   * 
   * @return the proportional coverage
   */
  double getCoverage();

  /**
   * Return the proportion of total coverage for one extent as a double 0-1.
   * 
   * @param dimension
   * 
   * @return the proportional coverage covered in the passed extent.
   */
  double getCoverage(Dimension.Type dimension);


  /**
   * Reimplements {@link IScale#merge(ITopologicallyComparable, LogicalConnector)} to return a
   * coverage and implement {@code ICoverage}-specific behavior.
   * <p>
   * If the coverage is a union, it will return the unaltered receiver {@code this}) unless the
   * <strong>additional</strong> coverage resulting from the union is higher than the proportion
   * returned by {@link #isRelevant()}. The proportion of coverage should be checked after this is
   * called to see if anything has changed.
   * <p>
   * Must not modify the original scales.
   * 
   * @param coverage
   * @param how
   *
   * @return a new merged coverage
   */
  @Override
  ICoverage merge(ITopologicallyComparable<?> coverage, LogicalConnector how);

  /**
   * True if the coverage is less than the global setting defining a usable coverage (default 1%).
   * 
   * @return true if coverage is below accepted defaults.
   */
  boolean isEmpty();

  /**
   * true if the coverage is at least as much as the minimum required coverage of a context (95% by
   * default). Note that setting this to 1.0 may trigger lots of resolutions to resolve minute
   * portions of the context.
   * 
   * @return true if coverage is enough to declare an observation consistent.
   */
  boolean isComplete();

  /**
   * true if the coverage is relevant enough for a model to be accepted by the resolver (default
   * smallest extent intersection covers 25% of scale).
   * 
   * @return true if coverage is enough to keep
   */
  boolean isRelevant();

  // /**
  // *
  // * @return
  // */
  // IScale getScale();

}
