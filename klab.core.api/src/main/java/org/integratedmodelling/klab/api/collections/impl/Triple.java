/*******************************************************************************
 *  Copyright (C) 2007, 2015:
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
package org.integratedmodelling.klab.api.collections.impl;

import org.integratedmodelling.klab.api.collections.KTriple;
import org.integratedmodelling.klab.api.exceptions.KIllegalArgumentException;


/**
  * Stupid generic pair class.
 * @param <T1> 
 * @param <T2> 
 * @param <T3> 
 */
public class Triple<T1, T2, T3> implements KTriple<T1, T2, T3> {
    
    private static final long serialVersionUID = 8017164648757388295L;
    protected T1              first            = null;
    protected T2              second           = null;
    protected T3              third            = null;

    /**
     *  Pair constructor comment.
     */
    public Triple() {
    }

    /**
     * @param  first java.lang.Object
     * @param  second java.lang.Object
     * @param third 
     */
    public Triple(T1 first, T2 second, T3 third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public Triple(Triple<T1, T2, T3> tc) {
        this.first = tc.first;
        this.second = tc.second;
        this.third = tc.third;
    }

    /**
     * @param  newValue java.lang.Object
     */
    public void setFirst(T1 newValue) {
        this.first = newValue;
    }

    /**
     * @param  newValue java.lang.Object
     */
    public void setSecond(T2 newValue) {
        this.second = newValue;
    }

    /**
     * @param newValue 
     */
    public void setThird(T3 newValue) {
        this.third = newValue;
    }

    /**
     * @return  java.lang.Object
     */
    public T1 getFirst() {
        return first;
    }

    /**
     * @return  java.lang.Object
     */
    public T2 getSecond() {
        return second;
    }

    public T3 getThird() {
        return third;
    }

    /**
     * @return  java.lang.String
     */
    @Override
    public String toString() {
        return "{" + getFirst() + "," + getSecond() + "," + getThird() + "}";
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((second == null) ? 0 : second.hashCode());
		result = prime * result + ((third == null) ? 0 : third.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Triple<?,?,?> other = (Triple<?,?,?>) obj;
		if (first == null) {
			if (other.first != null)
				return false;
		} else if (!first.equals(other.first))
			return false;
		if (second == null) {
			if (other.second != null)
				return false;
		} else if (!second.equals(other.second))
			return false;
		if (third == null) {
			if (other.third != null)
				return false;
		} else if (!third.equals(other.third))
			return false;
		return true;
	}

    public Object get(int columnIndex) {
        switch (columnIndex) {
        case 0:
            return first;
        case 1:
            return second;
        case 2:
            return third;
        }
        throw new KIllegalArgumentException("cannot retrieve " + columnIndex + "-th element from a triple");
    }


}
