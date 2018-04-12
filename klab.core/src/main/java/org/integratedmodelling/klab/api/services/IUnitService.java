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
 */
public interface IUnitService {

	IUnit getUnit(String unitDef);

    IUnit addExtents(IUnit refUnit, Collection<ExtentDimension> extentDimensions);

    boolean isDensity(IUnit unit, IConcept extent);

    boolean isSpatialDensity(IUnit unit, IExtent space);

    boolean isUnitless(IUnit unit);

    IUnit getVolumeExtentUnit(IUnit unit);

    boolean isVolumeDensity(IUnit unit);

    IUnit getArealExtentUnit(IUnit unit);

    boolean isArealDensity(IUnit unit);

    IUnit getLengthExtentUnit(IUnit unit);

    boolean isLengthDensity(IUnit unit);

    IUnit getTimeExtentUnit(IUnit unit);

    boolean isRate(IUnit unit);

    IUnit getDefaultUnitFor(IConcept concept);

}
