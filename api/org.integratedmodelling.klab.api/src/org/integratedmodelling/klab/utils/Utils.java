package org.integratedmodelling.klab.utils;

import java.io.File;
import java.util.Arrays;

public class Utils {

	/**
	 * Return file name with no path or extension
	 * 
	 * @param s
	 * @return the simple name of the file without extension or path.
	 */
	public static String getFileBaseName(File s) {

		String ret = s.toString();

		int sl = ret.lastIndexOf(File.separator);
		if (sl > 0)
			ret = ret.substring(sl + 1);
		sl = ret.lastIndexOf(".");
		if (sl > 0)
			ret = ret.substring(0, sl);

		return ret;
	}

	/**
	 * Return URL base name with no path or extension. Just like getFileBaseName but
	 * uses / instead of system separator.
	 * 
	 * @param s
	 * @return extracted name from URL
	 */
	public static String getURLBaseName(String s) {

		/* just in case */
		String ret = s.replace('\\', '/');

		if (ret.contains("?")) {
			ret = ret.substring(0, ret.indexOf('?'));
		}

		if (ret.contains("#")) {
			ret = ret.substring(0, ret.indexOf('#'));
		}

		int sl = ret.lastIndexOf(".");
		if (sl > 0)
			ret = ret.substring(0, sl);
		sl = ret.lastIndexOf("/");
		if (sl >= 0)
			ret = ret.substring(sl + 1);

		return ret;
	}

	public static long[] newArray(long value, int size) {
		long[] ret = new long[size];
		Arrays.fill(ret, value);
		return ret;
	}

	public static boolean isPOD(Object value) {
		return value instanceof Number || value instanceof String || value instanceof Boolean;
	}
	
	/**
	 * Return the closest POD that the value can be parsed into. For now only handle
	 * int and double. May add k.IM - like maps, lists, ranges.
	 * 
	 * @param value
	 * @return
	 */
	public static Object asPOD(String value) {

		try {
			return Integer.parseInt(value);
		} catch (Throwable e) {
		}
		try {
			return Double.parseDouble(value);
		} catch (Throwable e) {
		}
		
		if (value.toLowerCase().equals("true") || value.toLowerCase().equals("false")) {
			return value.toLowerCase().equals("true");
		}

		return value;
	}

	/**
	 * Basic conversions to match a type, including null -> NaN when what's wanted
	 * is a double or float
	 * 
	 * @param ret
	 * @param cls
	 * @return the object as a cls or null
	 */
	@SuppressWarnings("unchecked")
	public static <T> T asType(Object ret, Class<?> cls) {

		if (ret == null) {
			if (cls.equals(Double.class)) {
				return (T) new Double(Double.NaN);
			}
			if (cls.equals(Float.class)) {
				return (T) new Float(Float.NaN);
			}

			return null;
		}

		if (cls.isAssignableFrom(ret.getClass())) {
			return (T) ret;
		}

		if (ret instanceof Number && Number.class.isAssignableFrom(cls)) {
			if (cls.equals(Double.class)) {
				return (T) new Double(((Number) ret).doubleValue());
			}
			if (cls.equals(Long.class)) {
				return (T) new Long(((Number) ret).longValue());
			}
			if (cls.equals(Integer.class)) {
				return (T) new Integer(((Number) ret).intValue());
			}
			if (cls.equals(Float.class)) {
				return (T) new Float(((Number) ret).floatValue());
			}
		} else if (cls.equals(String.class)) {
			return (T) ret.toString();
		} else if (Boolean.class.isAssignableFrom(cls)) {
			if (ret instanceof Number) {
				if (cls.equals(Boolean.class)) {
					return (T) (NumberUtils.equal(((Number) ret).doubleValue(), 0) ? Boolean.FALSE : Boolean.TRUE);
				}
			} else {
				return (T)Boolean.TRUE;
			}
		}
		throw new IllegalArgumentException("cannot interpret value " + ret + " as a " + cls.getCanonicalName());
	}

}
