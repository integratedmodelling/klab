/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify it under the terms of the Affero
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root directory of the k.LAB
 * distribution (LICENSE.txt). If this cannot be found see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned in author tags. All
 * rights reserved.
 */
package org.integratedmodelling.klab.api.data.mediation;

import java.util.Collection;
import java.util.Map;

import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.ExtentDimension;
import org.integratedmodelling.klab.api.observations.scale.ExtentDistribution;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.services.IObservableService;
import org.integratedmodelling.klab.utils.Pair;

/**
 * Units of measurement. Creation and inquiry methods are provided by
 * {@link org.integratedmodelling.klab.api.services.IUnitService}.
 * <p>
 * Much more complex than (not-really-)standard JRS-375 units due to the need of scale awareness and
 * semantic-driven value aggregation and propagation. When a quality is extensive (i.e.,
 * {@link IObservableService#isExtensive(IObservable)} returns true), its value can be translated to
 * units that are incompatible because they either add or remove one or more contextual dimensions
 * (space and/or time). Also, the unit may already contain an implicit contextualization and be
 * valid for the semantics of the observable once the contextualization is factored in: for example,
 * volume of rain can be measured in mm as long as the context is 2D space, in which case mm means
 * the density of the volume (mm^3/mm^2) and is valid for the semantics of a volume.
 * <p>
 * k.LAB validates unit appropriateness <em>in context</em> and requires declaration of any
 * discrepancy between intensive/extensive character using annotations. If those are provided
 * correctly, context translations are possible even within the same model: for example, a weather
 * process model may produce extensive rainfall in both space and time (e.g. in m^2, applied to each
 * timestep and area subdivision) and area/time intensive snow (e.g. in mm/day) at the same time.
 * <p>
 * The unit API enables contextual conversion by using a specialized {@link IUnit} implementation.
 * When the context is factored in, the scale of computation for the unit is incorporated in the
 * declared unit by obtaining a contextualized unit from
 * 
 * @author Ferd
 * @version $Id: $Id
 */
public interface IUnit extends IValueMediator {

    /**
     * The result of a {@link IUnit#contextualize(IGeometry, Map)} operation. TODO probably hide
     * behind the API and normalize the contextualization interface as in IValueMediator.
     * 
     * @author Ferd
     *
     */
    public interface UnitContextualization {

        /**
         * All the admissible units corresponding to the contextualization of another to a geometry,
         * each one reporting the extents that have been aggregated in it and including the
         * "original" admissible unit with no aggregations.
         * 
         * @return
         */
        Collection<IUnit> getCandidateUnits();

        /**
         * The correct unit for contextualization to the geometry, taking into account the geometry
         * and any constraints passed to the method that produced this descriptor.
         * 
         * @return
         */
        IUnit getChosenUnit();
    }

    /**
     * Return a new unit multiplied by the passed one.
     *
     * @param unit a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
     * @return a new product unit
     */
    IUnit multiply(IUnit unit);

    /**
     * Return a new unit divided by the passed one.
     *
     * @param unit a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
     * @return a new unit
     */
    IUnit divide(IUnit unit);

    /**
     * Return a new unit scaled according to the passed double.
     *
     * @param scale a double.
     * @return a new unit
     */
    IUnit scale(double scale);

    /**
     * Return the set of aggregated dimensions in case this one results from re-contextualizing a
     * stated unit to a geometry, so that we can reconstruct the original values.
     * 
     * TODO hide from API
     * 
     * @return
     */
    Map<ExtentDimension, ExtentDistribution> getAggregatedDimensions();

//    /**
//     * Pass an observable with unit to obtain a mediator that will convert a value to this unit
//     * crossing extentual boundaries over the passed scale, i.e. aggregating to this unit over any
//     * dimension that is in the original value (and in the scale) and is not in this unit.
//     * 
//     * The specialized mediator returned should have additional API to check if it is stable over
//     * the scale or needs to be redefined at each locator (i.e., the scale is regular or not over
//     * the aggregated extent(s)).
//     * 
//     * TODO hide from API
//     * 
//     * @param observable
//     * @param scale
//     * @return
//     */
//    IValueMediator getContextualizingUnit(IObservable observable, IScale scale, ILocator locator);

    /**
     * Assuming the unit is distributed over the passed extent, split the unit from the extent and
     * return them separately. This will also infer spatial unit if called on the factorized form
     * (e.g. for mm over AREAL, it will return (mm^3, mm^2)).
     * 
     * @param dimension
     * @return
     */
    Pair<IUnit, IUnit> splitExtent(ExtentDimension dimension);

    /**
     * Obtain a target unit representing this one, pre-contextualized to the passed scale, so that
     * it can accept contextually compatible mediators at {@link #convert(Number, IValueMediator)}
     * and handle them appropriately. The mediator passed to convert called on the result must be
     * compatible <em>once the context is factored in</em>; this means that, for example, mm will be
     * compatible with m^3 if the scale is distributed in space, making "mm" nothing more than
     * mm^3/mm^2 and generating the appropriate conversion factors automatically.
     * <p>
     * The scale is cached in the unit and, for extensive values, used to transform the result as
     * needed, so the result can only be reused across scale swaps on <em>regular</em> extents. On
     * irregular extents, the original, uncontextualized mediator <em>must</em> be contextualized at
     * every step.
     * <p>
     * Overrides the return type from the original in {@link IValueMediator} for fluency.
     * 
     * @param observable
     * @param scale
     * @return
     */
    @Override
    IUnit contextualize(IObservable observable, IScale scale);

    /**
     * True if unitless.
     * 
     * @return
     */
    boolean isUnitless();

}
