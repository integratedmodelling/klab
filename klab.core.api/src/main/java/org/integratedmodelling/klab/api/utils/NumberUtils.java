/*******************************************************************************
 * Copyright (C) 2007, 2015:
 * 
 * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any other
 * authors listed in @author annotations
 *
 * All rights reserved. This file is part of the k.LAB software suite, meant to enable modular,
 * collaborative, integrated development of interoperable data and model components. For details,
 * see http://integratedmodelling.org.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * Affero General Public License Version 3 or any later version.
 *
 * This program is distributed in the hope that it will be useful, but without any warranty; without
 * even the implied warranty of merchantability or fitness for a particular purpose. See the Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the Affero General Public License along with this program; if
 * not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA. The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.api.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Pattern;

import org.integratedmodelling.klab.api.collections.impl.Pair;
import org.integratedmodelling.klab.api.exceptions.KlabIllegalArgumentException;

public class NumberUtils {

	/**
	 * Separate unit.
	 *
	 * @param o the o
	 * @return the pair
	 */
	public static Pair<Double, String> separateUnit(Object o) {
		if (o == null || o.toString().trim().isEmpty()) {
			return new Pair<>(Double.NaN, "");
		}
		String s = o.toString().trim();
		String num = "";
		String uni = "";
		for (int i = s.length() - 1; i >= 0; i--) {
			if (Character.isDigit(s.charAt(i))) {
				num = s.substring(0, i + 1).trim();
				uni = s.substring(i + 1).trim();
				break;
			}
		}

		return new Pair<>(num.isEmpty() ? Double.NaN : Double.parseDouble(num), uni);
	}

	/**
	 * 2 ^ -24 - this is for FLOAT precision, but I'm using it for doubles as well.
	 * See:
	 * http://en.wikipedia.org/wiki/Machine_epsilon#Values_for_standard_hardware_floating_point_arithmetics
	 */
	public static final double EPSILON = 5.96e-08;
	public static final Pattern DOUBLE_PATTERN = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?");
	public static final Pattern INTEGER_PATTERN = Pattern.compile("^-?\\d+$");

	public static boolean encodesDouble(String s) {
		return DOUBLE_PATTERN.matcher(s).matches();
	}

	public static boolean encodesLong(String s) {
		return INTEGER_PATTERN.matcher(s).matches();
	}

	public static boolean encodesInteger(String s) {
		return INTEGER_PATTERN.matcher(s).matches();
	}

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
	 * Convert an integer array to an easily parseable string for GET commands and
	 * the like.
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
	public static int[] intArrayFromString(String array) {
		return intArrayFromString(array, ",");
	}

	/**
	 * 
	 * @param array
	 * @param splitRegex
	 * @return the int array
	 */
	public static int[] intArrayFromString(String array, String splitRegex) {

		if (array.startsWith("[")) {
			array = array.substring(1);
		}
		if (array.endsWith("]")) {
			array = array.substring(0, array.length() - 1);
		}
		String[] s = array.split(splitRegex);
		int[] ret = new int[s.length];
		for (int i = 0; i < s.length; i++) {
			ret[i] = Integer.parseInt(s[i].trim());
		}
		return ret;
	}

	public static Object[] objectArrayFromString(String array, String splitRegex, Class<?> cls) {

		if (array.startsWith("[")) {
			array = array.substring(1);
		}
		if (array.endsWith("]")) {
			array = array.substring(0, array.length() - 1);
		}
		String[] s = array.split(splitRegex);
		Object[] ret = new Object[s.length];
		for (int i = 0; i < s.length; i++) {

			if (cls != null && !Object.class.equals(cls)) {
				ret[i] = Utils.parseAsType(s[i], cls);
			} else {
				if (encodesDouble(s[i].trim())) {
					ret[i] = Double.parseDouble(s[i].trim());
				} else if (encodesInteger(s[i].trim())) {
					ret[i] = Integer.parseInt(s[i].trim());
				} else {
					ret[i] = s[i];
				}
			}
		}
		return ret;
	}

	public static Object podArrayFromString(String array, String splitRegex, Class<?> cls) {
		Object[] pods = objectArrayFromString(array, splitRegex, cls);
		double[] dret = new double[pods.length];
		int[] iret = new int[pods.length];
		long[] lret = new long[pods.length];
		float[] fret = new float[pods.length];
		boolean[] bret = new boolean[pods.length];
		int nd = 0, ni = 0;
		int cl = 0;
		for (int i = 0; i < pods.length; i++) {
			if (pods[i] instanceof Double) {
				cl = 1;
				dret[i] = (Double) pods[i];
				nd++;
			} else if (pods[i] instanceof Integer) {
				cl = 2;
				iret[i] = (Integer) pods[i];
				ni++;
			} else if (pods[i] instanceof Long) {
				cl = 3;
				lret[i] = (Long) pods[i];
				ni++;
			} else if (pods[i] instanceof Float) {
				cl = 4;
				fret[i] = (Float) pods[i];
				ni++;
			} else if (pods[i] instanceof Boolean) {
				cl = 5;
				bret[i] = (Boolean) pods[i];
				ni++;
			}
		}
		
		switch (cl) {
		case 1:
			return dret;
		case 2:
			return iret;
		case 3:
			return lret;
		case 4:
			return fret;
		case 5:
			return bret;
		}
		
		throw new KlabIllegalArgumentException("cannot turn array into PODs: type not handled");
	}

	public static double[] doubleArrayFromString(String array, String splitRegex) {

		if (array.startsWith("[")) {
			array = array.substring(1);
		}
		if (array.endsWith("]")) {
			array = array.substring(0, array.length() - 1);
		}
		String[] s = array.split(splitRegex);
		double[] ret = new double[s.length];
		for (int i = 0; i < s.length; i++) {
			ret[i] = Double.parseDouble(s[i].trim());
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
		return ret / (double) n;
	}

	public static Number convertNumber(Number object, Class<?> cls) {
		if (Double.class.isAssignableFrom(cls)) {
			return object.doubleValue();
		}
		if (Integer.class.isAssignableFrom(cls)) {
			return object.intValue();
		}
		if (Long.class.isAssignableFrom(cls)) {
			return object.longValue();
		}
		if (Float.class.isAssignableFrom(cls)) {
			return object.floatValue();
		}
		return object;
	}

	/**
	 * Greatest common divisor of two integers
	 * 
	 * @param a
	 * @param b
	 * @return the GCD
	 */
	public static long gcd(long a, long b) {
		while (b > 0) {
			long temp = b;
			b = a % b;
			a = temp;
		}
		return a;
	}

	/**
	 * Greatest common divisor of an array of integers
	 * 
	 * @param a
	 * @param b
	 * @return the GCD
	 */
	public static long gcd(long[] input) {
		long result = input[0];
		for (int i = 1; i < input.length; i++)
			result = gcd(result, input[i]);
		return result;
	}

	/**
	 * Least common multiple of two integers
	 * 
	 * @param a
	 * @param b
	 * @return the LCM
	 */
	public static long lcm(long a, long b) {
		return a * (b / gcd(a, b));
	}

	/**
	 * Least common multiple of an array of integers
	 * 
	 * @param a
	 * @param b
	 * @return the LCM
	 */
	public static long lcm(long[] input) {
		long result = input[0];
		for (int i = 1; i < input.length; i++)
			result = lcm(result, input[i]);
		return result;
	}

	public static double[] doubleArrayFromCollection(List<Double> vals) {
		double[] ret = new double[vals.size()];
		int i = 0;
		for (Double d : vals) {
			ret[i++] = d;
		}
		return ret;
	}

	public static long[] longArrayFromCollection(Collection<? extends Number> vals) {
		long[] ret = new long[vals.size()];
		int i = 0;
		for (Number d : vals) {
			ret[i++] = d.longValue();
		}
		return ret;
	}

	public static int[] intArrayFromCollection(Collection<? extends Number> vals) {
		int[] ret = new int[vals.size()];
		int i = 0;
		for (Number d : vals) {
			ret[i++] = d.intValue();
		}
		return ret;
	}

	/**
	 * Index of largest number in double array
	 * 
	 * @param a
	 * @return index (0 if all NaN or equal)
	 */
	public static int indexOfLargest(double[] a) {
		double max = a[0];
		int index = 0;
		for (int i = 0; i < a.length; i++) {
			if (!Double.isNaN(a[i]) && max < a[i]) {
				max = a[i];
				index = i;
			}
		}
		return index;
	}

	private static int[] decimal = new int[] { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
	private static String[] roman = new String[] { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV",
			"I" };

	public static String toRoman(int num) {
		String result = "";
		for (int i = 0; i <= decimal.length; i++) {
			while (num % decimal[i] < num) {
				result += roman[i];
				num -= decimal[i];
			}
		}
		return result;
	}

	public static int fromRoman(String str) {
		int result = 0;
		for (int i = 0; i <= decimal.length; i++) {
			while (str.indexOf(roman[i]) == 0) {
				result += decimal[i];
				str = str.replace(roman[i], "");
			}
		}
		return result;
	}

	public static boolean isEven(int x) {
		return x % 2 == 0;
	}

	public static boolean isOdd(int x) {
		return x % 2 != 0;
	}

	public static int nextPowerOf2(int n) {
		// decrement n (to handle cases when n itself is a power of 2)
		n--;

		// Set all bits after the last set bit
		n |= n >> 1;
		n |= n >> 2;
		n |= n >> 4;
		n |= n >> 8;
		n |= n >> 16;

		// increment n and return
		return ++n;
	}

	public static long[] asLong(int[] vals) {
		long[] ret = new long[vals.length];
		for (int i = 0; i < vals.length; i++) {
			ret[i] = vals[i];
		}
		return ret;
	}

	/**
	 * Create the ordered enumeration of all values from 0 to n (excluded).
	 * 
	 * @param n
	 * @return
	 */
	public static LinkedHashSet<Integer> enumerateAsSet(int n) {
		LinkedHashSet<Integer> ret = new LinkedHashSet<>();
		for (int i = 0; i < n; i++) {
			ret.add(i);
		}
		return ret;
	}

	public static Number fromString(String value) {
		if (encodesInteger(value)) {
			return Integer.parseInt(value);
		} else if (encodesLong(value)) {
			return Long.parseLong(value);
		} else if (encodesDouble(value)) {
			return Double.parseDouble(value);
		}
		return Double.NaN;
	}
}
