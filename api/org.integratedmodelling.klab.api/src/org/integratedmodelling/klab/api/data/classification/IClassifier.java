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
package org.integratedmodelling.klab.api.data.classification;

import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

// TODO: Auto-generated Javadoc
/**
 * The Interface IClassifier.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IClassifier {

    /**
     * True if passed object matches the conditions of the classifier.
     *
     * @param o the o
     * @param monitor the monitor
     * @return True if passed object matches the conditions of the classifier
     */
    public boolean classify(Object o, IMonitor monitor);

    /**
     * True if this classifier matches everything.
     *
     * @return True if this classifier matches everything
     */
    boolean isUniversal();

    /**
     * True if this classifier only matches null (unknown).
     *
     * @return True if this classifier only matches null
     */
    boolean isNil();

    /**
     * True if this is an interval classifier.
     *
     * @return True if this is an interval classifier
     */
    boolean isInterval();

    /**
     * Classifiers may be used as a value; this one should return the most appropriate
     * value translation of the classifier, i.e. the matched object if it's matching a
     * single one, or possibly a random object among the choices if it's in OR.
     *
     * @return the value this classifier resolves to.
     */
    public Object asValue();
}
