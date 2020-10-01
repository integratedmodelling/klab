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
package org.integratedmodelling.klab.api.data;

/**
 * Aggregation mode. The default is AVERAGE for intensive properties and
 * non-physical properties or SUM for extensive properties, but data reduction
 * traits in the target may modify it (e.g. we may want the MAX if we tag the
 * final observer with im:Maximum). MAJORITY will be the default for qualitative
 * and semi-qualitative observers; at some point we may want to add fuzzy
 * membership and other more sophisticated strategies for probabilistic observers.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public enum Aggregation {
    NONE,
    SUM,
    MEAN,
    STD,
    MIN,
    MAX,
    MAJORITY,
    ANY_PRESENT,
    MAXIMUM_LIKELIHOOD,
    COUNT,
    VARIANCE
}
