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

import org.integratedmodelling.klab.api.lang.LogicalConnector;

/**
 * A topological object can be compared with topological operators to another of a compatible class.
 *
 * @author Ferdinando Villa
 * @version $Id: $Id
 * @param <T> the generic type
 */
public interface ITopologicallyComparable<T> {

    /**
     * Tentative - options for the merge functions in children.
     * 
     * @author Ferd
     *
     */
    enum MergingOption {
        BOUNDARIES_ONLY
    }

    /**
     * <p>
     * contains.
     * </p>
     *
     * @param o a T object.
     * @return true if o is contained in this
     * @throws org.integratedmodelling.klab.exceptions.KlabException
     */
    boolean contains(T o);

    /**
     * <p>
     * overlaps.
     * </p>
     *
     * @param o a T object.
     * @return true if o overlaps this
     * @throws org.integratedmodelling.klab.exceptions.KlabException
     */
    boolean overlaps(T o);

    /**
     * <p>
     * intersects.
     * </p>
     *
     * @param o a T object.
     * @return true if o intersects this
     * @throws org.integratedmodelling.klab.exceptions.KlabException
     */
    boolean intersects(T o);

    /**
     * <p>
     * merge.
     * </p>
     *
     * @param other a
     *        {@link org.integratedmodelling.klab.api.observations.scale.ITopologicallyComparable}
     *        object.
     * @param how a {@link org.integratedmodelling.klab.common.LogicalConnector} object.
     * @param options affect the merge result
     * @return a
     *         {@link org.integratedmodelling.klab.api.observations.scale.ITopologicallyComparable}
     *         object.
     */
    ITopologicallyComparable<? extends T> merge(ITopologicallyComparable<?> other, LogicalConnector how,
            MergingOption... options);

}
