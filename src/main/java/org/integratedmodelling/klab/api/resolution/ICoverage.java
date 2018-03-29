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

/**
 * A {@code ICoverage} A coverage is a scale that keeps information about how much of its extents is
 * being covered by other extents. Coverage is initialized at 0 or 1 and can be modified only by
 * merging in conformant scales. It represents the total coverage for an observation or a
 * computation during or after resolution. Merge operations do not change the underlying scale but
 * will modify the fraction of the original extents that is covered.
 * <p>
 * A {@code ICoverage} redefines the
 * {@link IScale#merge(ITopologicallyComparable, LogicalConnector)} method to only perform a union
 * when the resulting coverage adds enough coverage. The {@link #getGain()} can be called on the
 * result to check if the merge produced any significant increment or decrement in coverage.
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
   * coverage and implement {@code ICoverage}-specific behavior. Note that this breaks the
   * {@link IScale} contract by returning a coverage where the underlying scale is
   * <strong>unmodified</strong>, but only its coverage information has potentially changed.
   * <p>
   * If the coverage is a union, it will return the unaltered receiver {@code this}) unless the
   * <strong>additional</strong> coverage resulting from the union is higher than the proportion
   * returned by {@link #isRelevant()}. The proportion of coverage that has changed should be
   * checked after this is called using {@link #getGain()} to see if anything has changed.
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
   * True if the coverage is at least as much as the minimum required coverage of a context (95% by
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

  /**
   * Proportion of coverage gained or lost during the merge operation that generated this coverage,
   * if any.
   * 
   * @return The coverage gained or lost (negative if lost). Always [-1, 1]. If zero, the coverage
   *         was not created by a merge.
   */
  double getGain();

}
