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
package org.integratedmodelling.klab.utils;

import java.util.Collection;
import java.util.List;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * Utility to reduce the ugliness of casting generic collections in Java. If you have say a
 * Collection<A> (ca) that you know is a Collection<B extends A> and you need a Collection<B>, do
 * the following:
 * 
 * Collection<B> cb = new Cast<A,B>.cast(ca);
 * 
 * and type safety be damned. This will not generate any warning and will avoid any silly copy.
 * Works for generic collections, arraylists and hashsets - add more if needed. Needs something else
 * for maps.
 *
 * @author ferdinando.villa
 * @param <B> the generic type
 * @param <T> the generic type
 */
public class CastUtils<B, T> {

    /**
     * Cast.
     *
     * @param b the b
     * @return the collection
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Collection<T> cast(Collection<B> b) {
        return (Collection<T>) (Collection) b;
    }

    /**
     * Cast.
     *
     * @param b the b
     * @return the list
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<T> cast(List<B> b) {
        return (List<T>) (List) b;
    }

    /**
     * Cast.
     *
     * @param b the b
     * @return the sets the
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Set<T> cast(Set<B> b) {
        return (Set<T>) (Set) b;
    }
}
