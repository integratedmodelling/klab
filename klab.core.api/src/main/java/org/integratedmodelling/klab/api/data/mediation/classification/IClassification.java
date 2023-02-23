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
package org.integratedmodelling.klab.api.data.mediation.classification;

import org.integratedmodelling.klab.api.collections.impl.Pair;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.observation.scope.IContextScope;

/**
 * The Interface IClassification.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IClassification extends IDataKey, Iterable<Pair<IConcept, IClassifier>> {

	/**
	 * Return the main concept that subsumes all those expressed by the classifiers.
	 * In some situations this may be null. In normal situations this will be an
	 * abstract concept and subsume all others.
	 *
	 * @return a {@link org.integratedmodelling.klab.api.knowledge.IConcept} object.
	 */
	IConcept getConcept();

	/**
	 * True if this has been declared and validated as a discretization. Subsumes
	 * {@link #isContiguousAndFinite()} == true.
	 *
	 * @return a boolean.
	 */
	boolean isDiscretization();

	/**
	 * Return true if all the intervals are contiguous and the extreme intervals
	 * have finite boundaries.
	 *
	 * @return true if we express the discretization of a finite domain
	 */
	boolean isContiguousAndFinite();

	/**
	 * Return the concept that the passed object classifies to, or null if no
	 * classifiers match.
	 *
	 * @param object
	 *            a {@link java.lang.Object} object.
	 * @param scope
	 *            context of computation
	 * @return a {@link org.integratedmodelling.klab.api.knowledge.IConcept} object.
	 */
	IConcept classify(Object object, IContextScope scope);

	/**
	 * Get the undiscretized value for the passed concept. If the concept is not in
	 * the classification or this is not a discretization, return Double.NaN.
	 *
	 * @param object
	 *            the object
	 * @return the double
	 */
	double undiscretize(IConcept object);

	/**
	 * Return a sensible numeric value for the passed concept. NaN should be
	 * reserved for no-data, concepts for which suitable classifiers are not
	 * defined, or unrecognized concepts; ranking order for orderings should be
	 * respected. If the data encode a discretization, it is OK to return the
	 * undiscretized values.
	 *
	 * @param o
	 *            the o
	 * @return the number we can use to encode the concept, which must be one of the
	 *         getConcepts()
	 */
	double getNumericValue(IConcept o);

	/**
	 * Classify to the numeric ranking of the concept instead of the concept.
	 *
	 * @param o
	 *            the object to classify
	 * @param scope
	 *            a context
	 * @return a numeric ranking - equivalent to calling getNumericCode(classify(o))
	 */
	int classifyToIndex(Object o, IContextScope scope);

}
