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
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.utils.Pair;

/**
 * Units of measurement. Creation and inquiry methods are provided by
 * {@link org.integratedmodelling.klab.api.services.IUnitService}.
 * 
 * Much more complex than (not-really-)standard JRS-275 units due to the need of
 * scale awareness and semantic-driven value aggregation and propagation.
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
	public interface UnitContextualization {

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
	 * Pass an observable with unit to obtain a mediator that will convert a value
	 * to this unit crossing extentual boundaries over the passed scale, i.e.
	 * aggregating to this unit over any dimension that is in the original value
	 * (and in the scale) and is not in this unit.
	 * 
	 * The specialized mediator returned should have additional API to check if it
	 * is stable over the scale or needs to be redefined at each locator (i.e., the
	 * scale is regular or not over the aggregated extent(s)).
	 * 
	 * @param observable
	 * @param scale
	 * @return
	 */
	IValueMediator getContextualizingUnit(IObservable observable, IScale scale, ILocator locator);

	/**
	 * Assuming the unit is distributed over the passed extent, split the unit from
	 * the extent and return them separately. This will also infer spatial unit if
	 * called on the factorized form (e.g. for mm over AREAL, it will return (mm^3,
	 * mm^2)).
	 * 
	 * @param dimension
	 * @return
	 */
	Pair<IUnit, IUnit> splitExtent(ExtentDimension dimension);

	/**
	 * True if unitless.
	 * 
	 * @return
	 */
	boolean isUnitless();

}
