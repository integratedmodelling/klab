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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.util.Pair;

public class NumberUtils extends org.apache.commons.lang.math.NumberUtils {

    /**
     * 2 ^ -24 - this is for FLOAT precision, but I'm using it for doubles as well.
     * See:
     * http://en.wikipedia.org/wiki/Machine_epsilon#Values_for_standard_hardware_floating_point_arithmetics
     */
    public static final double EPSILON = 5.96e-08;

    public static List<Integer> scanRange(int[] range) {
        List<Integer> ret = new ArrayList<>();
        if (range != null && range.length > 0) {
            ret.add(range[0]);
            if (range.length > 1) {
                for (int i = range[0]; i <= range[1]; i++) {
                    ret.add(i);
                }
            }
        }
        return ret;
    }

    
    public static Pair<Double, String> separateUnit(Object o) {
        if (o == null || o.toString().trim().isEmpty()) {
            return new Pair<>(Double.NaN, "");
        }
        String s = o.toString().trim();
        String num = ""; String uni = "";
        for (int i = s.length() - 1; i >= 0; i--) {
            if (Character.isDigit(s.charAt(i))) {
                num = s.substring(0, i+1).trim();
                uni = s.substring(i+1).trim();
                break;
            }
        }
        
        return new Pair<>(num.isEmpty() ? Double.NaN : Double.parseDouble(num), uni);
    }
    
    /**
     * Double comparison done as recommended by IBM.
     * 
     * @param a
     * @param b 
     * @return true if "equal" 
     */
    public static boolean equal(double a, double b) {
        if (b == 0)
            return Double.compare(a, b) == 0;
        return Math.abs(a / b - 1) < EPSILON;
    }

    public static boolean isInteger(Number n) {
        if (n instanceof Double || n instanceof Float) {
            double d = n.doubleValue();
            return Math.abs(d - Math.round(d)) <= EPSILON;
        }
        return true;
    }

    /**
     * Convert an integer array to an easily parseable string for GET commands and the like.
     * 
     * @param array
     * @return string
     */
    public static String toString(int[] array) {
        String s = "";
        for (int i = 0; i < array.length; i++) {
            s += (i > 0 ? "," : "") + array[i];
        }
        return s;
    }

    /**
     * Convert a string returned by toString(int[]) into the original array.
     * 
     * @param array
     * @return int array
     */
    public static int[] fromString(String array) {
        String[] s = array.split(",");
        int[] ret = new int[s.length];
        for (int i = 0; i < s.length; i++) {
            ret[i] = Integer.parseInt(s[i]);
        }
        return ret;
    }

    public static double[] normalize(double[] vals) {
        double[] ret = new double[vals.length];
        double min = Double.NaN, max = Double.NaN;
        for (int i = 0; i < vals.length; i++) {
            if (!Double.isNaN(vals[i])) {
                if (Double.isNaN(min) || min > vals[i]) {
                    min = vals[i];
                }
                if (Double.isNaN(max) || max < vals[i]) {
                    max = vals[i];
                }
            }
        }

        if (!Double.isNaN(min)) {
            for (int i = 0; i < vals.length; i++) {
                ret[i] = Double.isNaN(vals[i]) ? Double.NaN : ((vals[i] - min) / (max - min));
            }
        } else {
            ret = vals;
        }

        return ret;
    }


    public static double sumWithoutNan(double[] data) {
        double ret = 0;
        for (double v : data) {
            if (!Double.isNaN(v)) {
                if (!Double.isNaN(v)) {
                    ret += v;
                }
            }
        }
        return ret;
    }

    public static double averageWithoutNan(double[] data) {
        int n = 0;
        double ret = 0;
        for (double v : data) {
            if (!Double.isNaN(v)) {
                if (!Double.isNaN(v)) {
                    ret += v;
                    n++;
                }
            }
        }
        return ret/(double)n;
    }

    
}
