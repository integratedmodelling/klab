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

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

/**
 * Utility class to implement a read-only object list, such as those used in kbox query results.
 * Just implements the interface for all modifying methods and make them throw and
 * UnsupportedOperationException, so that only the relevant methods need to be implemented.
 *
 * @author Ferd
 * @param <T> the generic type
 * @version $Id: $Id
 */
public abstract class ImmutableList<T> implements List<T> {

    /** {@inheritDoc} */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /** {@inheritDoc} */
    @Override
    public boolean add(T arg0) {
        throw new UnsupportedOperationException("cannot modify read-only list");
    }

    /** {@inheritDoc} */
    @Override
    public boolean addAll(Collection<? extends T> arg0) {
        throw new UnsupportedOperationException("cannot modify read-only list");
    }

    /** {@inheritDoc} */
    @Override
    public void clear() {
        throw new UnsupportedOperationException("cannot modify read-only list");
    }

    /** {@inheritDoc} */
    @Override
    public boolean containsAll(Collection<?> arg0) {
        throw new UnsupportedOperationException("cannot modify read-only list");
    }

    /** {@inheritDoc} */
    @Override
    public boolean remove(Object arg0) {
        throw new UnsupportedOperationException("cannot modify read-only list");
    }

    /** {@inheritDoc} */
    @Override
    public boolean removeAll(Collection<?> arg0) {
        throw new UnsupportedOperationException("cannot modify read-only list");
    }

    /** {@inheritDoc} */
    @Override
    public boolean retainAll(Collection<?> arg0) {
        throw new UnsupportedOperationException("cannot modify read-only list");
    }

    /** {@inheritDoc} */
    @Override
    public void add(int arg0, T arg1) {
        throw new UnsupportedOperationException("cannot modify read-only list");
    }

    /** {@inheritDoc} */
    @Override
    public boolean addAll(int arg0, Collection<? extends T> arg1) {
        throw new UnsupportedOperationException("cannot modify read-only list");
    }

    /** {@inheritDoc} */
    @Override
    public int indexOf(Object arg0) {
        throw new UnsupportedOperationException("cannot modify read-only list");
    }

    /** {@inheritDoc} */
    @Override
    public int lastIndexOf(Object arg0) {
        throw new UnsupportedOperationException("cannot modify read-only list");
    }

    /** {@inheritDoc} */
    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException("cannot modify read-only list");
    }

    /** {@inheritDoc} */
    @Override
    public ListIterator<T> listIterator(int arg0) {
        throw new UnsupportedOperationException("cannot modify read-only list");
    }

    /** {@inheritDoc} */
    @Override
    public T remove(int arg0) {
        throw new UnsupportedOperationException("cannot modify read-only list");
    }

    /** {@inheritDoc} */
    @Override
    public T set(int arg0, T arg1) {
        throw new UnsupportedOperationException("cannot modify read-only list");
    }

    /** {@inheritDoc} */
    @Override
    public List<T> subList(int arg0, int arg1) {
        throw new UnsupportedOperationException("cannot modify read-only list");
    }
}
