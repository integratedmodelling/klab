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
package org.integratedmodelling.klab.api.knowledge.observation.scale;

// TODO: Auto-generated Javadoc
/**
 * The Interface ITopology.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 * @param <T> the generic type
 */
public interface ITopology<T> extends ITopologicallyComparable<T> {

    /**
     * Return the total number of distinct subdivisions in this topology. {@link #INFINITE} is
     * an option when applicable.
     *
     * @return number of subdivisions
     */
    public long size();

}
