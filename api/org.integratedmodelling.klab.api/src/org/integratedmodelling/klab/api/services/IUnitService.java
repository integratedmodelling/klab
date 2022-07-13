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
package org.integratedmodelling.klab.api.services;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.klab.api.data.Aggregation;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.ExtentDimension;
import org.integratedmodelling.klab.api.observations.scale.ExtentDistribution;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.utils.Pair;

/**
 * The Interface IUnitService.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IUnitService {

    /**
     * <p>
     * getUnit.
     * </p>
     *
     * @param unitDef a {@link java.lang.String} object.
     * @return a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
     */
    IUnit getUnit(String unitDef);

    /**
     * <p>
     * addExtents.
     * </p>
     *
     * @param refUnit a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
     * @param extentDimensions a {@link java.util.Collection} object.
     * @return a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
     */
    IUnit addExtents(IUnit refUnit, Collection<ExtentDimension> extentDimensions);

    /**
     * Remove extents.
     * 
     * @param refUnit
     * @param extentDimensions
     * @return
     */
    IUnit removeExtents(IUnit refUnit, Collection<ExtentDimension> extentDimensions);

//    /**
//     * <p>
//     * isDensity.
//     * </p>
//     *
//     * @param unit a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
//     * @param extent a {@link org.integratedmodelling.klab.api.knowledge.IConcept} object.
//     * @return a boolean.
//     */
//    boolean isDensity(IUnit unit, IConcept extent);

    /**
     * <p>
     * isSpatialDensity.
     * </p>
     *
     * @param unit a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
     * @param space a {@link org.integratedmodelling.klab.api.observations.scale.IExtent} object.
     * @return a boolean.
     */
    boolean isSpatialDensity(IUnit unit, IExtent space);

    /**
     * <p>
     * isUnitless.
     * </p>
     *
     * @param unit a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
     * @return a boolean.
     */
    boolean isUnitless(IUnit unit);

    /**
     * <p>
     * getVolumeExtentUnit.
     * </p>
     *
     * @param unit a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
     * @return a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
     */
    IUnit getVolumeExtentUnit(IUnit unit);

    /**
     * <p>
     * isVolumeDensity.
     * </p>
     *
     * @param unit a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
     * @return a boolean.
     */
    boolean isVolumeDensity(IUnit unit);

    /**
     * <p>
     * getArealExtentUnit.
     * </p>
     *
     * @param unit a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
     * @return a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
     */
    IUnit getArealExtentUnit(IUnit unit);

    /**
     * <p>
     * isArealDensity.
     * </p>
     *
     * @param unit a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
     * @return a boolean.
     */
    boolean isArealDensity(IUnit unit);

    /**
     * <p>
     * getLengthExtentUnit.
     * </p>
     *
     * @param unit a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
     * @return a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
     */
    IUnit getLengthExtentUnit(IUnit unit);

    /**
     * <p>
     * isLengthDensity.
     * </p>
     *
     * @param unit a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
     * @return a boolean.
     */
    boolean isLengthDensity(IUnit unit);

    /**
     * <p>
     * getTimeExtentUnit.
     * </p>
     *
     * @param unit a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
     * @return a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
     */
    IUnit getTimeExtentUnit(IUnit unit);

    /**
     * <p>
     * isRate.
     * </p>
     *
     * @param unit a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
     * @return a boolean.
     */
    boolean isRate(IUnit unit);

    /**
     * <p>
     * getDefaultUnitFor.
     * </p>
     * 
     * Ideally cache the result, at least until OWLAPI becomes faster.
     *
     * @param observable a {@link org.integratedmodelling.klab.api.knowledge.IObservable} object.
     * @return a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
     */
    IUnit getDefaultUnitFor(IObservable observable);

    /**
     * Ensure that the passed unit is distributed in the passed dimensions and return the result.
     * Only add a dimension if it's not there already. If the unit already has an incompatible
     * dimension, return null.
     * 
     * @param unit
     * @param aggregatable
     */
    IUnit contextualize(IUnit refUnit, Set<ExtentDimension> aggregatable);

    /**
     * Return whether the observable needs units. This is true if the observable is a non-rescaled
     * physical property or if it's an extensive count or money value, in which case the unit will
     * be unitless but may express distribution over the context.
     * 
     * @param observable
     * @return
     */
    boolean needsUnits(IObservable observable);

    /**
     * Return true if the observable should be rescaled based on contextual extents when measured
     * extensively. This will apply to (non-rescaled) extensive physical properties, numerosities,
     * money and monetary values. The actual need for rescaling depends on the units in the
     * observable vs. the scale of observation.
     * 
     * @param observable
     * @return
     */
    boolean needsUnitScaling(IObservable observable);

//    IUnit getLinealExtentUnit(IUnit unit);

    /**
     * Analyze an observable in a scale and return a contextualized unit and the needed
     * transformation to aggregate the values. If the transformation isn't stable, this will need to
     * be repeated for each locator, otherwise the result can be reused within the same
     * contextualization.
     * 
     * @return
     */
    Pair<IValueMediator, Aggregation> getAggregationStrategy(IObservable observable, IScale locator);

}
