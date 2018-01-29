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

import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * An object that represents the total coverage for a subject after resolution in a context.
 * 
 * @author Ferd
 *
 */
public interface ICoverage {

  /**
   * Return the proportion of total coverage as a double 0-1.
   * 
   * @return the proportional coverage
   */
  Double getCoverage();

  /**
   * 
   * Union of the coverages. NOTE: this will not unite the passed coverage if the ADDITIONAL
   * coverage resulting from the union is less than the proportion returned by isRelevant(). The
   * proportion of coverage should be checked after or() to see if anything has changed.
   * 
   * @param coverage
   * @return the union of coverates
   * 
   * @throws KlabException
   */
  ICoverage or(ICoverage coverage) throws KlabException;

  /**
   * 
   * @param coverage
   * @return the intersection of coverages
   * @throws KlabException
   */
  ICoverage and(ICoverage coverage) throws KlabException;

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

}
