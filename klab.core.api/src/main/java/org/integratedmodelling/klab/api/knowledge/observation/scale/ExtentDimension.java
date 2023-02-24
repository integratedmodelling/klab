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
package org.integratedmodelling.klab.api.knowledge.observation.scale;

import org.integratedmodelling.klab.api.geometry.KGeometry.Dimension;

/**
 * All extent dimensions understood in this implementation, for both space and time. Used to
 * decompose units to validate distribution over time and space.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public enum ExtentDimension {

    PUNTAL(0, true, false, Dimension.Type.SPACE), LINEAL(1, true, false, Dimension.Type.SPACE), AREAL(2, true, false,
            Dimension.Type.SPACE), VOLUMETRIC(3, true, false,
                    Dimension.Type.SPACE), TEMPORAL(1, false, true, Dimension.Type.TIME), CONCEPTUAL(1, false, false, null);

    public int dimensionality;
    public Dimension.Type type;
    public boolean spatial;
    public boolean temporal;

    ExtentDimension(int dimensionality, boolean spatial, boolean temporal, Dimension.Type type) {
        this.dimensionality = dimensionality;
        this.spatial = spatial;
        this.temporal = temporal;
        this.type = type;
    }

    public static ExtentDimension spatial(int spaceDimensionality) {
        switch(spaceDimensionality) {
        case 0:
            return PUNTAL;
        case 1:
            return LINEAL;
        case 2:
            return AREAL;
        case 3:
            return VOLUMETRIC;
        }
        throw new IllegalArgumentException(
                "ExtentDimension: dimensionality " + spaceDimensionality + " is not handled in this implementation");
    }
}
