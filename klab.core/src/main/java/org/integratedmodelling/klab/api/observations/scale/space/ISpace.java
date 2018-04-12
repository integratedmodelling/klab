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

import org.integratedmodelling.kim.api.data.ILocator;
import org.integratedmodelling.klab.api.observations.scale.IExtent;

/**
 * The Interface ISpace.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface ISpace extends IExtent {

    /** Constant <code>MIN_SCALE_RANK=0</code> */
    int MIN_SCALE_RANK = 0;
    /** Constant <code>MAX_SCALE_RANK=21</code> */
    int MAX_SCALE_RANK = 21;

    /**
     * Get the envelope, providing boundaries.
     *
     * @return the referenced envelope
     */
    IEnvelope getEnvelope();

    /**
     * Projection. Just repeats same in envelope and shape. It's not legal to have different projections in
     * different elements of a spatial extent.
     *
     * @return coordinate reference system
     */
    IProjection getProjection();
    /**
     * Get the shape of this extent - usually it's the same as getExtent but with the additional
     * type constraint.
     *
     * @return full shape
     */
    IShape getShape();
    
    /**
     * {@inheritDoc}
     *
     * The space implementation of {@link ILocator#at(ILocator)} always return a time and
     * can only use another space as locator.
     */
    @Override
    ISpace at(ILocator locator);

}
