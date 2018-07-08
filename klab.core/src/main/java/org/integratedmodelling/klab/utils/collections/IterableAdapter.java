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
package org.integratedmodelling.klab.utils.collections;

import java.util.Iterator;

// TODO: Auto-generated Javadoc
/**
 * The Class IterableAdapter.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 * @param <T> the generic type
 */
public class IterableAdapter<T> implements Iterable<T> {

    private Iterable<?> iterable;

    /**
     * Instantiates a new iterable adapter.
     *
     * @param iterable the iterable
     */
    public IterableAdapter(Iterable<?> iterable) {
        this.iterable = iterable;
    }

    /** {@inheritDoc} */
    @Override
    public Iterator<T> iterator() {
        return new IteratorAdapter<T>(iterable.iterator());
    }

}
