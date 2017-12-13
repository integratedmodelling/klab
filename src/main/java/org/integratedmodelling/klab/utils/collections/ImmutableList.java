/*******************************************************************************
 *  Copyright (C) 2007, 2014:
 *  
 *    - Ferdinando Villa <ferdinando.villa@bc3research.org>
 *    - integratedmodelling.org
 *    - any other authors listed in @author annotations
 *
 *    All rights reserved. This file is part of the k.LAB software suite,
 *    meant to enable modular, collaborative, integrated 
 *    development of interoperable data and model components. For
 *    details, see http://integratedmodelling.org.
 *    
 *    This program is free software; you can redistribute it and/or
 *    modify it under the terms of the Affero General Public License 
 *    Version 3 or any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but without any warranty; without even the implied warranty of
 *    merchantability or fitness for a particular purpose.  See the
 *    Affero General Public License for more details.
 *  
 *     You should have received a copy of the Affero General Public License
 *     along with this program; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *     The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.utils.collections;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

/**
 * Utility class to implement a read-only object list, such as those used in kbox query
 * results. Just implements the interface for all modifying methods and make them throw
 * and UnsupportedOperationException, so that only the relevant methods need to be
 * implemented.
 * 
 * @author Ferd
 * @param <T> 
 *
 */
public abstract class ImmutableList<T> implements List<T> {

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean add(T arg0) {
        throw new UnsupportedOperationException("cannot modify read-only list");
    }

    @Override
    public boolean addAll(Collection<? extends T> arg0) {
        throw new UnsupportedOperationException("cannot modify read-only list");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("cannot modify read-only list");
    }

    @Override
    public boolean containsAll(Collection<?> arg0) {
        throw new UnsupportedOperationException("cannot modify read-only list");
    }

    @Override
    public boolean remove(Object arg0) {
        throw new UnsupportedOperationException("cannot modify read-only list");
    }

    @Override
    public boolean removeAll(Collection<?> arg0) {
        throw new UnsupportedOperationException("cannot modify read-only list");
    }

    @Override
    public boolean retainAll(Collection<?> arg0) {
        throw new UnsupportedOperationException("cannot modify read-only list");
    }

    @Override
    public void add(int arg0, T arg1) {
        throw new UnsupportedOperationException("cannot modify read-only list");
    }

    @Override
    public boolean addAll(int arg0, Collection<? extends T> arg1) {
        throw new UnsupportedOperationException("cannot modify read-only list");
    }

    @Override
    public int indexOf(Object arg0) {
        throw new UnsupportedOperationException("cannot modify read-only list");
    }

    @Override
    public int lastIndexOf(Object arg0) {
        throw new UnsupportedOperationException("cannot modify read-only list");
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException("cannot modify read-only list");
    }

    @Override
    public ListIterator<T> listIterator(int arg0) {
        throw new UnsupportedOperationException("cannot modify read-only list");
    }

    @Override
    public T remove(int arg0) {
        throw new UnsupportedOperationException("cannot modify read-only list");
    }

    @Override
    public T set(int arg0, T arg1) {
        throw new UnsupportedOperationException("cannot modify read-only list");
    }

    @Override
    public List<T> subList(int arg0, int arg1) {
        throw new UnsupportedOperationException("cannot modify read-only list");
    }
}
