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
package org.integratedmodelling.klab.utils;

import org.integratedmodelling.klab.api.data.utils.IPair;

/**
  * Stupid generic pair class.
 * @param <T1> 
 * @param <T2> 
 */
public class Pair<T1, T2> implements IPair<T1, T2> {

    static boolean cmpObj(Object o1, Object o2) {
        return (o1 == null && o2 == null) || (o1 != null && o2 != null && o1.equals(o2));
    }

    private static final long serialVersionUID = 1L;
    protected T1              first            = null;
    protected T2              second           = null;

    /**
     *  Pair constructor comment.
     */
    public Pair() {
    }

    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    public Pair(Pair<T1, T2> pc) {
        this.first = pc.first;
        this.second = pc.second;
    }

    public void setFirst(T1 newValue) {
        this.first = newValue;
    }

    public void setSecond(T2 newValue) {
        this.second = newValue;
    }

    public T1 getFirst() {
        return first;
    }

    public T2 getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return "{" + getFirst() + "," + getSecond() + "}";
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof Pair))
            return false;

        return cmpObj(first, ((Pair<?, ?>) obj).first) && cmpObj(first, ((Pair<?, ?>) obj).second);
    }

    @Override
    public int hashCode() {
        return (first == null ? 0 : first.hashCode()) + (second == null ? 0 : second.hashCode());
    }

}
