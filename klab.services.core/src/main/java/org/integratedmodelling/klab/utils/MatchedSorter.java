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

import java.util.Comparator;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * Sorts an array according to the sort order of a matched other using a given comparator. Makes up
 * for the lovely matched sort available in C# and missing in Java collections.
 *
 * @author Ferd
 * @version $Id: $Id
 * @param <T1> the generic type
 * @param <T2> the generic type
 */
public class MatchedSorter<T1, T2> {

    List<T1> _a;
    List<T2> _criteria;
    Comparator<T2> _comparator;

    /**
     * Instantiates a new matched sorter.
     *
     * @param a the a
     * @param criteria the criteria
     * @param comparator the comparator
     */
    public MatchedSorter(List<T1> a, List<T2> criteria, Comparator<T2> comparator) {
        _a = a;
        _criteria = criteria;
        _comparator = comparator;
        if (a.size() > 0)
            quicksort(0, a.size() - 1);
    }

    /**
     * Gets the sorted values.
     *
     * @return the sorted values
     */
    public List<T1> getSortedValues() {
        return _a;
    }

    /**
     * Gets the sorted criteria.
     *
     * @return the sorted criteria
     */
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
