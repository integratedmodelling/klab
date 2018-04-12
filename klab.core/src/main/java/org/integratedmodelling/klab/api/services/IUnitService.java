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
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.scale.ExtentDimension;
import org.integratedmodelling.klab.api.observations.scale.IExtent;

/**
 * The Interface IUnitService.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IUnitService {

	/**
	 * <p>getUnit.</p>
	 *
	 * @param unitDef a {@link java.lang.String} object.
	 * @return a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
	 */
	IUnit getUnit(String unitDef);

    /**
     * <p>addExtents.</p>
     *
     * @param refUnit a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
     * @param extentDimensions a {@link java.util.Collection} object.
     * @return a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
     */
    IUnit addExtents(IUnit refUnit, Collection<ExtentDimension> extentDimensions);

    /**
     * <p>isDensity.</p>
     *
     * @param unit a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
     * @param extent a {@link org.integratedmodelling.klab.api.knowledge.IConcept} object.
     * @return a boolean.
     */
    boolean isDensity(IUnit unit, IConcept extent);

    /**
     * <p>isSpatialDensity.</p>
     *
     * @param unit a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
     * @param space a {@link org.integratedmodelling.klab.api.observations.scale.IExtent} object.
     * @return a boolean.
     */
    boolean isSpatialDensity(IUnit unit, IExtent space);

    /**
     * <p>isUnitless.</p>
     *
     * @param unit a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
     * @return a boolean.
     */
    boolean isUnitless(IUnit unit);

    /**
     * <p>getVolumeExtentUnit.</p>
     *
     * @param unit a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
     * @return a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
     */
    IUnit getVolumeExtentUnit(IUnit unit);

    /**
     * <p>isVolumeDensity.</p>
     *
     * @param unit a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
     * @return a boolean.
     */
    boolean isVolumeDensity(IUnit unit);

    /**
     * <p>getArealExtentUnit.</p>
     *
     * @param unit a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
     * @return a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
     */
    IUnit getArealExtentUnit(IUnit unit);

    /**
     * <p>isArealDensity.</p>
     *
     * @param unit a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
     * @return a boolean.
     */
    boolean isArealDensity(IUnit unit);

    /**
     * <p>getLengthExtentUnit.</p>
     *
     * @param unit a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
     * @return a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
     */
    IUnit getLengthExtentUnit(IUnit unit);

    /**
     * <p>isLengthDensity.</p>
     *
     * @param unit a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
     * @return a boolean.
     */
    boolean isLengthDensity(IUnit unit);

    /**
     * <p>getTimeExtentUnit.</p>
     *
     * @param unit a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
     * @return a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
     */
    IUnit getTimeExtentUnit(IUnit unit);

    /**
     * <p>isRate.</p>
     *
     * @param unit a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
     * @return a boolean.
     */
    boolean isRate(IUnit unit);

    /**
     * <p>getDefaultUnitFor.</p>
     *
     * @param concept a {@link org.integratedmodelling.klab.api.knowledge.IConcept} object.
     * @return a {@link org.integratedmodelling.klab.api.data.mediation.IUnit} object.
     */
    IUnit getDefaultUnitFor(IConcept concept);

}
