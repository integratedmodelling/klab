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
package org.integratedmodelling.klab.api.data.mediation;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.ExtentDimension;
import org.integratedmodelling.klab.api.observations.scale.ExtentDistribution;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.utils.Pair;

/**
 * Units of measurement. Creation and inquiry methods are provided by
 * {@link org.integratedmodelling.klab.api.services.IUnitService}.
 *
 * @author Ferd
 * @version $Id: $Id
 */
public interface IUnit extends IValueMediator {

	/**
	 * The result of a {@link IUnit#contextualize(IGeometry, Map)} operation.
	 * 
	 * @author Ferd
	 *
	 */
	public interface Contextualization {

		/**
		 * All the admissible units corresponding to the contextualization of another to
		 * a geometry, each one reporting the extents that have been aggregated in it
		 * and including the "original" admissible unit with no aggregations.
		 * 
		 * @return
		 */
		Collection<IUnit> getCandidateUnits();

		/**
		 * The correct unit for contextualization to the geometry, taking into account
		 * the geometry and any constraints passed to the method that produced this
		 * descriptor.
		 * 
		 * @return
		 */
		IUnit getChosenUnit();
	}

	/**
	 * Return a new unit multiplied by the passed one.
	 *
	 * @param unit
	 *            a {@link org.integratedmodelling.klab.api.data.mediation.IUnit}
	 *            object.
	 * @return a new product unit
	 */
	IUnit multiply(IUnit unit);

	/**
	 * Return a new unit divided by the passed one.
	 *
	 * @param unit
	 *            a {@link org.integratedmodelling.klab.api.data.mediation.IUnit}
	 *            object.
	 * @return a new unit
	 */
	IUnit divide(IUnit unit);

	/**
	 * Return a new unit scaled according to the passed double.
	 *
	 * @param scale
	 *            a double.
	 * @return a new unit
	 */
	IUnit scale(double scale);

	/**
	 * Return the set of aggregated dimensions in case this one results from
	 * re-contextualizing a stated unit to a geometry, so that we can reconstruct
	 * the original values.
	 * 
	 * @return
	 */
	Set<ExtentDimension> getAggregatedDimensions();

	/**
	 * Contextualize this <em>base unit</em> to the passed geometry, returning a
	 * descriptor that contains all the acceptable units paired with the set of
	 * extents that are aggregated in them. The descriptor also contains a chosen
	 * unit that corresponds to an optional set of constraints, pairing a dimension
	 * to a choice of extensive (aggregated) or intensive (distributed). If the
	 * constraints are null, the chosen unit is the one that is distributed over all
	 * the extents in the geometry.
	 * <p>
	 * In order to work properly, this must be called on the <b>DEFAULT BASE
	 * UNIT</b> of an observable, stripped of any contextualization.
	 * 
	 * @param geometry
	 *            a geometry to contextualize to
	 * @param constraints
	 *            a map of requested constraints on the chosen unit (may be null)
	 * @return
	 */
	Contextualization contextualize(IGeometry geometry, Map<ExtentDimension, ExtentDistribution> constraints);

	/**
	 * Ppass another unit to obtain the multiplication factor to convert a value to
	 * this unit in a given scale. Return the factor and whether the factor should
	 * be re-evaluated at every call.
	 * 
	 * E.g. I am m^2; call (m^2).getContextualizationFactor([extensive observable in
	 * mm], spatialScaleS2) -> factor to convert n mm into (n*factor) m^2
	 * 
	 * @param observable
	 * @param scale
	 * @return
	 */
	Pair<IValueMediator, Boolean> getContextualizationFactor(IObservable observable, IScale scale, ILocator locator);

}
