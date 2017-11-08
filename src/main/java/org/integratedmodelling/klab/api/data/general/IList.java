package org.integratedmodelling.klab.api.data.general;

/*******************************************************************************
 * Copyright (C) 2007, 2014:
 * 
 * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any
 * other authors listed in @author annotations
 *
 * All rights reserved. This file is part of the k.LAB software suite, meant to enable
 * modular, collaborative, integrated development of interoperable data and model
 * components. For details, see http://integratedmodelling.org.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms
 * of the Affero General Public License Version 3 or any later version.
 *
 * This program is distributed in the hope that it will be useful, but without any
 * warranty; without even the implied warranty of merchantability or fitness for a
 * particular purpose. See the Affero General Public License for more details.
 * 
 * You should have received a copy of the Affero General Public License along with this
 * program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite
 * 330, Boston, MA 02111-1307, USA. The license is also available at:
 * https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/

import java.util.List;

/**
 * Open linked list, LISP-style. Immutable and general, not generic. Supports the obvious
 * basic operations.
 * 
 * Not meant to contain itself or lists that refer back to itself. I.e., it's suboptimal
 * and laborious to build a graph representation with this one. If that's what you want,
 * look at IReferenceList.
 * 
 * @author Ferd
 */
public interface IList extends Iterable<Object> {

    /**
     * isEmpty() tells whether the list is empty.
     * 
     * @return true if empty
     */
    public abstract boolean isEmpty();

    /**
     * Return a new list with the passed objects appended to it.
     * 
     * @param o
     * @return merged list
     */
    public abstract IList append(Object... o);

    /**
     * first() returns the first element of a non-empty Polylist.
     * 
     * @return first object
     */
    public abstract Object first();

    /**
     * Returns a nicely indented string representation of the list.
     * 
     * @return pretty-printed representation
     */
    public abstract String prettyPrint();

    /**
     * rest() returns the rest of a non-empty Polylist.
     * @return print version
     */
    public abstract IList rest();

    /**
     * cons returns a new Polylist given a First and this as a Rest
     * @param First 
     * @return cons list
     */
    public abstract IList cons(Object First);

    /**
     * return the length of this list
     * @return length
     */
    public abstract int length();

    /**
     * reverse() returns the reverse of this
     * @return reversed list
     */
    public abstract IList reverse();

    /**
     * contains(A) tells whether A is a member of this
     * @param A 
     * @return true if this contains a
     */
    public abstract boolean contains(Object A);

    /**
     * nth selects list item by index (0, 1, 2, ...).
     * @param n 
     * @return n-th object
     *
     */
    public abstract Object nth(long n);

    /**
     * array() returns an array of the elements in list
     * @return array
     */
    public abstract Object[] toArray();

    /**
     * Return a List of the object in the list.
     * 
     * @return collection
     */
    public abstract List<Object> toCollection();

}
