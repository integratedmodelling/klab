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
package org.integratedmodelling.klab.persistence.h2;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.utils.MatchedSorter;
import org.integratedmodelling.klab.utils.collections.ImmutableList;

/**
 * List to return results of a Kbox query. Only stores object IDs, creating
 * any object lazily.
 * 
 * Allows specifying a sorting score from outside for each result. If the scores
 * are given, iteration will be in descending score order. Scores are doubles and
 * expected to be 0-1; the default value of a score when only some are specified 
 * is 0.
 * 
 * @author Ferd
 * @param <T> 
 *
 */
public class H2Result<T> extends ImmutableList<T> {

    List<Long> results;
    H2Kbox     kbox;
    IMonitor   monitor;
    Class<? extends T> cls;

    class KboxIterator implements Iterator<T> {

        int idx = 0;

        @Override
        public boolean hasNext() {
            return idx < results.size();
        }

        @Override
        public T next() {
            return (T)get(idx++);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("cannot modify read-only kbox iterator");
        }
    }
    
    @Override
    public int size() {
        return results.size();
    }

    public H2Result(H2Kbox kbox, List<Long> res, List<Object> sortingCriteria,
            Comparator<Object> comparator, Class<? extends T> cls, IMonitor monitor) {

        this.kbox = kbox;
        this.cls = cls;
        MatchedSorter<Long, Object> sorter = new MatchedSorter<Long, Object>(res, sortingCriteria, comparator);
        results = sorter.getSortedValues();
    }

    public H2Result(H2Kbox h2Kbox, IMonitor monitor) {
        this.kbox = h2Kbox;
        this.monitor = monitor;
    }

    @Override
    public T get(int arg0) {
        try {
            return (T)kbox.retrieve(results.get(arg0), this.cls);
        } catch (Exception e) {
            monitor.error(e);
            return null;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new KboxIterator();
    }

    @Override
    public String toString() {
        return size() + ": " + Arrays.toString(results.toArray());
    }

    public void addId(long l) {
        results.add(l);
    }

    @Override
    public boolean contains(Object arg0) {
        return arg0 instanceof Long ? results.contains(arg0) : false;
    }

    @Override
    public Object[] toArray() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * toArray - pass a long array to get the ids and a Metadata array to get the 
     * metadata. Quite obscure.
     */
    @Override
    public <T> T[] toArray(T[] arg0) {

        if (arg0.getClass().getComponentType().equals(Long.TYPE))
            return results.toArray(arg0);

        return null;
    }
}
