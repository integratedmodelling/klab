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
package org.integratedmodelling.klab.utils;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Utility to reduce the ugliness of casting generic collections in Java. If you 
 * have say a Collection<A> (ca) that you know is a Collection<B extends A> and you need
 * a Collection<B>, do the following:
 * 
 * Collection<B> cb = new Cast<A,B>.cast(ca);
 * 
 * and type safety be damned. This will not generate any warning and will avoid 
 * any silly copy. Works for generic collections, arraylists and hashsets - add
 * more if needed. Needs something else for maps.
 * 
 * @author ferdinando.villa
 *
 * @param <B>
 * @param <T>
 */
public class CastUtils<B, T> {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Collection<T> cast(Collection<B> b) {
        return (Collection<T>) (Collection) b;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<T> cast(List<B> b) {
        return (List<T>) (List) b;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Set<T> cast(Set<B> b) {
        return (Set<T>) (Set) b;
    }
}
