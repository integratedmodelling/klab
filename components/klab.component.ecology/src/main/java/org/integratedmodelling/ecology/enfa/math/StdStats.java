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
package org.integratedmodelling.ecology.enfa.math;

import java.util.Arrays;

/*************************************************************************
 *  Compilation:  javac StdStats.java
 *  Execution:    java StdStats < input.txt
 *
 *  Library of statistical functions.
 *
 *  The test client reads an array of real numbers from standard
 *  input, and computes the minimum, mean, maximum, and
 *  standard deviation.
 *
 *  The functions all throw a NullPointerException if the array
 *  passed in is null.

 *  % more tiny.txt
 *  5
 *  3.0 1.0 2.0 5.0 4.0
 *
 *  % java StdStats < tiny.txt
 *         min   1.000
 *        mean   3.000
 *         max   5.000
 *     std dev   1.581
 *
 *************************************************************************/

/**
 *  <i>Standard statistics</i>. This class provides methods for computing
 *  statistics such as min, max, mean, sample standard deviation, and
 *  sample variance.
 *  <p>
 *  For additional documentation, see
 *  <a href="http://introcs.cs.princeton.edu/22library">Section 2.2</a> of
 *  <i>Introduction to Programming in Java: An Interdisciplinary Approach</i>
 *  by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public final class StdStats {

    private StdStats() {
    }

    /**
      * Returns the maximum value in the array a[], -infinity if no such value.
      */
    public static double max(double[] a) {
        double max = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < a.length; i++) {
            if (a[i] > max)
                max = a[i];
        }
        return max;
    }

    /**
      * Returns the maximum value in the subarray a[lo..hi], -infinity if no such value.
      */
    public static double max(double[] a, int lo, int hi) {
        if (lo < 0 || hi >= a.length || lo > hi)
            throw new RuntimeException("Subarray indices out of bounds");
        double max = Double.NEGATIVE_INFINITY;
        for (int i = lo; i <= hi; i++) {
            if (a[i] > max)
                max = a[i];
        }
        return max;
    }

    /**
     * Returns the maximum value in the array a[], Integer.MIN_VALUE if no such value.
     */
    public static int max(int[] a) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < a.length; i++) {
            if (a[i] > max)
                max = a[i];
        }
        return max;
    }

    /**
     * Returns the minimum value in the array a[], +infinity if no such value.
     */
    public static double min(double[] a) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < a.length; i++) {
            if (a[i] < min)
                min = a[i];
        }
        return min;
    }

    /**
      * Returns the minimum value in the subarray a[lo..hi], +infinity if no such value.
      */
    public static double min(double[] a, int lo, int hi) {
        if (lo < 0 || hi >= a.length || lo > hi)
            throw new RuntimeException("Subarray indices out of bounds");
        double min = Double.POSITIVE_INFINITY;
        for (int i = lo; i <= hi; i++) {
            if (a[i] < min)
                min = a[i];
        }
        return min;
    }

    /**
     * Returns the minimum value in the array a[], Integer.MAX_VALUE if no such value.
     */
    public static int min(int[] a) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < a.length; i++) {
            if (a[i] < min)
                min = a[i];
        }
        return min;
    }

    /**
     * Returns the average value in the array a[], NaN if no such value.
     */
    public static double mean(double[] a) {
        if (a.length == 0)
            return Double.NaN;
        double sum = sum(a);
        return sum / a.length;
    }

    /**
     * Returns the average value in the subarray a[lo..hi], NaN if no such value.
     */
    public static double mean(double[] a, int lo, int hi) {
        int length = hi - lo + 1;
        if (lo < 0 || hi >= a.length || lo > hi)
            throw new RuntimeException("Subarray indices out of bounds");
        if (length == 0)
            return Double.NaN;
        double sum = sum(a, lo, hi);
        return sum / length;
    }

    /**
     * Returns the average value in the array a[], NaN if no such value.
     */
    public static double mean(int[] a) {
        if (a.length == 0)
            return Double.NaN;
        double sum = 0.0;
        for (int i = 0; i < a.length; i++) {
            sum = sum + a[i];
        }
        return sum / a.length;
    }

    /**
     * Returns the sample variance in the array a[], NaN if no such value.
     */
    public static double var(double[] a) {
        if (a.length == 0)
            return Double.NaN;
        double avg = mean(a);
        double sum = 0.0;
        for (int i = 0; i < a.length; i++) {
            sum += (a[i] - avg) * (a[i] - avg);
        }
        return sum / (a.length - 1);
    }

    /**
     * Returns the sample variance in the subarray a[lo..hi], NaN if no such value.
     */
    public static double var(double[] a, int lo, int hi) {
        int length = hi - lo + 1;
        if (lo < 0 || hi >= a.length || lo > hi)
            throw new RuntimeException("Subarray indices out of bounds");
        if (length == 0)
            return Double.NaN;
        double avg = mean(a, lo, hi);
        double sum = 0.0;
        for (int i = lo; i <= hi; i++) {
            sum += (a[i] - avg) * (a[i] - avg);
        }
        return sum / (length - 1);
    }

    /**
     * Returns the sample variance in the array a[], NaN if no such value.
     */
    public static double var(int[] a) {
        if (a.length == 0)
            return Double.NaN;
        double avg = mean(a);
        double sum = 0.0;
        for (int i = 0; i < a.length; i++) {
            sum += (a[i] - avg) * (a[i] - avg);
        }
        return sum / (a.length - 1);
    }

    /**
     * Returns the population variance in the array a[], NaN if no such value.
     */
    public static double varp(double[] a) {
        if (a.length == 0)
            return Double.NaN;
        double avg = mean(a);
        double sum = 0.0;
        for (int i = 0; i < a.length; i++) {
            sum += (a[i] - avg) * (a[i] - avg);
        }
        return sum / a.length;
    }

    /**
     * Returns the population variance in the subarray a[lo..hi], NaN if no such value.
     */
    public static double varp(double[] a, int lo, int hi) {
        int length = hi - lo + 1;
        if (lo < 0 || hi >= a.length || lo > hi)
            throw new RuntimeException("Subarray indices out of bounds");
        if (length == 0)
            return Double.NaN;
        double avg = mean(a, lo, hi);
        double sum = 0.0;
        for (int i = lo; i <= hi; i++) {
            sum += (a[i] - avg) * (a[i] - avg);
        }
        return sum / length;
    }

    /**
     * Returns the sample standard deviation in the array a[], NaN if no such value.
     */
    public static double stddev(double[] a) {
        return Math.sqrt(var(a));
    }

    /**
     * Returns the sample standard deviation in the subarray a[lo..hi], NaN if no such value.
     */
    public static double stddev(double[] a, int lo, int hi) {
        return Math.sqrt(var(a, lo, hi));
    }

    /**
     * Returns the sample standard deviation in the array a[], NaN if no such value.
     */
    public static double stddev(int[] a) {
        return Math.sqrt(var(a));
    }

    /**
     * Returns the population standard deviation in the array a[], NaN if no such value.
     */
    public static double stddevp(double[] a) {
        return Math.sqrt(varp(a));
    }

    /**
     * Returns the population standard deviation in the subarray a[lo..hi], NaN if no such value.
     */
    public static double stddevp(double[] a, int lo, int hi) {
        return Math.sqrt(varp(a, lo, hi));
    }

    /**
     * Returns the sum of all values in the array a[].
     */
    public static double sum(double[] a) {
        double sum = 0.0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i];
        }
        return sum;
    }

    /**
     * Returns the sum of all values in the subarray a[lo..hi].
     */
    public static double sum(double[] a, int lo, int hi) {
        if (lo < 0 || hi >= a.length || lo > hi)
            throw new RuntimeException("Subarray indices out of bounds");
        double sum = 0.0;
        for (int i = lo; i <= hi; i++) {
            sum += a[i];
        }
        return sum;
    }

    /**
     * Returns the sum of all values in the array a[].
     */
    public static int sum(int[] a) {
        int sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i];
        }
        return sum;
    }

    /**
     * Calculates the median of a sample. The value of the sample is stored in a double array
     * @param a the values of sample
     * @return the median value
     */
    public static double median(double[] a) {
        double[] m = a.clone();
        Arrays.sort(m);
        int n = m.length;
        if (n % 2 == 1) {
            return m[((n + 1) / 2) - 1];
        } else { // if sample length is even
            return ((m[(n / 2) - 1] + m[((n / 2) + 1) - 1]) / 2.0);
        }
    }
}
