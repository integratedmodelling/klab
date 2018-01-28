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
package org.integratedmodelling.klab.api.observations.scale;

import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * A topological object can be compared with topological operators to another of a 
 * compatible class.
 * 
 * @author Ferdinando Villa
 * @param <T> 
 *
 */
public interface ITopologicallyComparable<T> {

    /**
     * 
     * @param o
     * @return true if o is contained in this
     * @throws KlabException
     */
    public abstract boolean contains(T o) throws KlabException;

    /**
     * 
     * @param o
     * @return true if o overlaps this
     * @throws KlabException
     */
    public abstract boolean overlaps(T o) throws KlabException;

    /**
     * 
     * @param o
     * @return true if o intersects this
     * @throws KlabException
     */
    public abstract boolean intersects(T o) throws KlabException;

    /**
     * FIXME this is in Topology<?> which I forgot about.
     * @param other
     * @return the union of other and this
     * @throws KlabException
     */
    public abstract T union(T other)
            throws KlabException;

    /**
     * FIXME this is in Topology<?> which I forgot about.
     * 
     * @param other
     * @return the intersection between this and other
     * @throws KlabException
     */
    public abstract T intersection(T other)
            throws KlabException;

    /**
     * Return a double that describes the extent of this topological object. It should only be
     * used to compare objects of the same type.
     * 
     * @return the covered extent
     */
    public abstract double getCoveredExtent();

}
