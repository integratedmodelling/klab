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
package org.integratedmodelling.klab.api.observations.scale.space;

/**
 * Opaque interface for a referenced envelope.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IEnvelope extends IReferenced {

    /**
     * <p>getMinX.</p>
     *
     * @return a double.
     */
    double getMinX();

    /**
     * <p>getMaxX.</p>
     *
     * @return a double.
     */
    double getMaxX();

    /**
     * <p>getMinY.</p>
     *
     * @return a double.
     */
    double getMinY();

    /**
     * <p>getMaxY.</p>
     *
     * @return a double.
     */
    double getMaxY();

    /**
     * <p>asShape.</p>
     *
     * @return a {@link org.integratedmodelling.klab.api.observations.scale.space.IShape} object.
     */
    IShape asShape();

    
    /**
     * <p>transform.</p>
     *
     * @param projection a {@link org.integratedmodelling.klab.api.observations.scale.space.IProjection} object.
     * @param b a boolean.
     * @return a {@link org.integratedmodelling.klab.api.observations.scale.space.IEnvelope} object.
     */
    IEnvelope transform(IProjection projection, boolean b);

    /**
     * <p>getCenterCoordinates.</p>
     *
     * @return an array of {@link double} objects.
     */
    double[] getCenterCoordinates();
}
