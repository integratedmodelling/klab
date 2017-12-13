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

import java.util.Comparator;
import java.util.List;

/**
 * Sorts an array according to the sort order of a matched other using a given 
 * comparator. Makes up for the lovely matched sort available in C# and missing
 * in Java collections.
 * 
 * @author Ferd
 *
 * @param <T1>
 * @param <T2>
 */
public class MatchedSorter<T1, T2> {

    List<T1> _a;
    List<T2> _criteria;
    Comparator<T2> _comparator;

    public MatchedSorter(List<T1> a, List<T2> criteria, Comparator<T2> comparator) {
        _a = a;
        _criteria = criteria;
        _comparator = comparator;
        if (a.size() > 0)
            quicksort(0, a.size() - 1);
    }

    public List<T1> getSortedValues() {
        return _a;
    }

    public List<T2> getSortedCriteria() {
        return _criteria;
    }

    private void swap(int lft, int rt) {
        T1 temp;
        temp = _a.get(lft);
        _a.set(lft, _a.get(rt));
        _a.set(rt, temp);

        T2 otemp = _criteria.get(lft);
        _criteria.set(lft, _criteria.get(rt));
        _criteria.set(rt, otemp);
    }

    private void quicksort(int low, int high) {

        int i = low, j = high;

        // Get the pivot element from the middle of the list
        T2 pivot = _criteria.get(low + (high - low) / 2);

        // Divide into two lists
        while (i <= j) {
            // If the current value from the left list is smaller then the pivot
            // element then get the next element from the left list
            while (_comparator.compare(_criteria.get(i), pivot) < 0) {
                i++;
            }
            // If the current value from the right list is larger then the pivot
            // element then get the next element from the right list
            while (_comparator.compare(_criteria.get(j), pivot) > 0) {
                j--;
            }

            // If we have found a values in the left list which is larger then
            // the pivot element and if we have found a value in the right list
            // which is smaller then the pivot element then we exchange the
            // values.
            // As we are done we can increase i and j
            if (i <= j) {
                swap(i, j);
                i++;
                j--;
            }
        }

        // Recursion
        if (low < j)
            quicksort(low, j);
        if (i < high)
            quicksort(i, high);
    }

}
