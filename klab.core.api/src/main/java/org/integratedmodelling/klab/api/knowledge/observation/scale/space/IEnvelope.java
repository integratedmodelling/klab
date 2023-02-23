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
package org.integratedmodelling.klab.api.knowledge.observation.scale.space;

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
     * 
     * @return the width
     */
    double getWidth();
    
    /**
     * 
     * @return the height
     */
    double getHeight();

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
     * @param b use lenient transformation if necessary (see JTS docs).
     * @return a {@link org.integratedmodelling.klab.api.observations.scale.space.IEnvelope} object.
     */
    IEnvelope transform(IProjection projection, boolean lenient);

    /**
     * Return the envelope in a predictable, standard projection.
     * 
     * @return
     */
    IEnvelope standard();
    
    /**
     * <p>getCenterCoordinates.</p>
     *
     * @return an array of {@link double} objects.
     */
    double[] getCenterCoordinates();

    /**
     * Return the distance in the original units that corresponds to the passed distance in
     * meters evaluated in this envelope.
     * 
     * @param metersDistance
     * @return the meters distance converted into native envelope units
     */
	double metersToDistance(double metersDistance);
	
    /**
     * Return the distance in meters that corresponds to the passed distance in
     * original units for in this envelope.
     * 
     * @param metersDistance
     * @return the meters distance converted into native envelope units
     */
	double distanceToMeters(double originalDistance);

	   /**
     * Compute the zoom level a'la Google/OSM.
     * <p>
     * 
     * <pre>
     * Level  Degrees  Area            m/pixel     ~Scale          # Tiles
     * 0      360      whole world     156,412     1:500 million   1
     * 1      180                      78,206      1:250 million   4
     * 2      90                       39,103      1:150 million   16
     * 3      45                       19,551      1:70 million    64
     * 4      22.5                     9,776       1:35 million    256
     * 5      11.25                    4,888       1:15 million    1,024
     * 6      5.625                    2,444       1:10 million    4,096
     * 7      2.813                    1,222       1:4 million     16,384
     * 8      1.406                    610.984     1:2 million     65,536
     * 9      0.703    wide area       305.492     1:1 million     262,144
     * 10     0.352                    152.746     1:500,000       1,048,576
     * 11     0.176    area            76.373      1:250,000       4,194,304
     * 12     0.088                    38.187      1:150,000       16,777,216
     * 13     0.044    village or town 19.093      1:70,000        67,108,864
     * 14     0.022                    9.547       1:35,000        268,435,456
     * 15     0.011                    4.773       1:15,000        1,073,741,824
     * 16     0.005    small road      2.387       1:8,000         4,294,967,296
     * 17     0.003                    1.193       1:4,000         17,179,869,184
     * 18     0.001                    0.596       1:2,000         68,719,476,736
     * 19     0.0005                   0.298       1:1,000         274,877,906,944
     * </pre>
     * 
     * @return the scale rank (zoom level) for the envelope
     * 
     */
    int getScaleRank();
    
    /**
     * Basic 2D operations
     * 
     * @param other
     * @return
     */
    boolean overlaps(IEnvelope other);
    
    /**
     * Return a larger envelope containing the previous.
     * 
     * @param factor
     * @return
     */
    IEnvelope grow(double factor);
}
