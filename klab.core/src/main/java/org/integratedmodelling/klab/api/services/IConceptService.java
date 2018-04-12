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
package org.integratedmodelling.klab.api.services;

import java.util.Collection;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IProperty;

/**
 * The Interface IConceptService.
 */
public interface IConceptService {

  IProperty getProperty(String propertyId);

  IConcept getConcept(String conceptId);

  Collection<IConcept> getLeastGeneral(Collection<IConcept> cc);

  IConcept getLeastGeneralCommonConcept(Collection<IConcept> cc);

  IConcept getLeastGeneralCommonConcept(IConcept concept1, IConcept c);

  IConcept getLeastGeneralConcept(Collection<IConcept> cc);

  /**
   * Finds a metadata field in the inheritance chain of a concept. {@link IConcept#getMetadata()}
   * only retrieves metadata for the concept it's called on.
   * 
   * @param concept
   * @param field
   * @return the metadata field or null
   */
  Object getMetadata(IConcept concept, String field);

  /**
   * Return the number of parent "generations" between from and to, or -1 if the concepts have no
   * common lineage.
   * 
   * @param from
   * @param to
   * @return 0 or a positive number if the concepts have common lineage, -1 otherwise.
   */
  int getAssertedDistance(IConcept from, IConcept to);

  /**
   * Find a common denominator and return n > 0 if c1 is more specific than c2, 0 if same, n < 0
   * otherwise. Return {@link Integer#MIN_VALUE} if the concepts have nothing in common.
   * 
   * @param c1
   * @param c2
   * @param useBaseTrait if true, compare against the base trait of both concepts instead of the
   *        least general common ancestor. Integer.MIN_VALUE is returned if the base trait does not
   *        exist or is not the same.
   * @return the result of comparing, or Integer.MIN_VALUE if concepts are not comparable.
   */
  int compareSpecificity(IConcept c1, IConcept c2, boolean useBaseTrait);

}
