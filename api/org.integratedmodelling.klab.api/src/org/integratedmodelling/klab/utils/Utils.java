package org.integratedmodelling.klab.utils;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;

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
	
	public static long[] toLongArray(List<Long> list) {
		long[] ret = new long[list.size()];
		for (int i = 0; i < list.size(); i++) {
			ret[i] = list.get(i) == null ? 0L : list.get(i);
		}
		return ret;
	}

	public static boolean isPOD(Object value) {
		if (value instanceof Class<?>) {
			return Number.class.isAssignableFrom((Class<?>) value) || String.class.isAssignableFrom((Class<?>) value)
					|| Boolean.class.isAssignableFrom((Class<?>) value);
		}
		return value instanceof Number || value instanceof String || value instanceof Boolean;
	}

	public static Class<?> getPODClass(Object value) {
		if (value instanceof Number) {
			return Double.class;
		}
		if (value instanceof Boolean) {
			return Boolean.class;
		}
		return String.class;
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

	public static boolean validateAs(Object pod, IArtifact.Type type) {
		if (pod == null) {
			return false;
		}
		IArtifact.Type tp = getArtifactType(pod.getClass());
		if (type == tp) {
			return true;
		}
		if (tp == Type.TEXT) {
			Object converted = asPOD(pod.toString());
			if (converted != null) {
				return getArtifactType(converted.getClass()) == type;
			}
		}
		return false;
	}

	/**
	 * Basic conversions to match a type, including null -> NaN when what's wanted
	 * is a double or float. Any value will be put into a list if a list is asked
	 * for.
	 * 
	 * @param ret
	 * @param cls
	 * @return the object as a cls or null
	 */
	@SuppressWarnings("unchecked")
	public static <T> T asType(Object ret, Class<?> cls) {

		if (cls.equals(Object.class)) {
			return (T) ret;
		}

		if (cls.isArray() && ret.getClass().isArray()) {

			int length = Array.getLength(ret);

			if (cls.equals(int[].class)) {
				int[] a = new int[length];
				for (int i = 0; i < length; i++) {
					a[i] = asType(Array.get(ret, i), Integer.class);
				}
				return (T) a;
			} else if (cls.equals(long[].class)) {
				long[] a = new long[length];
				for (int i = 0; i < length; i++) {
					a[i] = asType(Array.get(ret, i), Long.class);
				}
				return (T) a;

			} else if (cls.equals(float[].class)) {
				float[] a = new float[length];
				for (int i = 0; i < length; i++) {
					a[i] = asType(Array.get(ret, i), Float.class);
				}
				return (T) a;

			} else if (cls.equals(double[].class)) {
				double[] a = new double[length];
				for (int i = 0; i < length; i++) {
					a[i] = asType(Array.get(ret, i), Double.class);
				}
				return (T) a;

			} else if (cls.equals(String[].class)) {
				String[] a = new String[length];
				for (int i = 0; i < length; i++) {
					a[i] = asType(Array.get(ret, i), String.class);
				}
				return (T) a;

			}
		}

		if (cls.equals(List.class)) {
			List<Object> list = new ArrayList<>();
			if (ret instanceof Collection) {
				list.addAll((Collection<?>) ret);
			} else {
				list.add(ret);
			}
			return (T) list;
		}

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

		if (cls.equals(String.class)) {
			return (T) ret.toString();
		}

		if (ret instanceof Number) {
			if (Number.class.isAssignableFrom(cls)) {
				if (cls.equals(Double.class)) {
					return (T) new Double(((Number) ret).doubleValue());
				}
				if (cls.equals(Long.class)) {
					return (T) new Long(((Number) ret).longValue());
				}
				if (cls.equals(Integer.class)) {
					return (T) new Integer(((Number) ret).intValue());
				}
				if (cls.equals(Short.class)) {
					return (T) new Short(((Number) ret).shortValue());
				}
				if (cls.equals(Float.class)) {
					return (T) new Float(((Number) ret).floatValue());
				}
			} else if (Boolean.class.isAssignableFrom(cls)) {
				if (cls.equals(Boolean.class)) {
					return (T) (NumberUtils.equal(((Number) ret).doubleValue(), 0) ? Boolean.FALSE : Boolean.TRUE);
				}
			}
		} else if (ret instanceof Boolean) {

			if (cls.equals(Double.class)) {
				return (T) new Double(((Boolean) ret) ? 1 : 0);
			}
			if (cls.equals(Long.class)) {
				return (T) new Long(((Boolean) ret) ? 1 : 0);
			}
			if (cls.equals(Short.class)) {
				return (T) new Short(((Boolean) ret) ? (short)1 : 0);
			}
			if (cls.equals(Integer.class)) {
				return (T) new Integer(((Boolean) ret) ? 1 : 0);
			}
			if (cls.equals(Float.class)) {
				return (T) new Float(((Boolean) ret) ? 1 : 0);
			}

		} else if (ret instanceof String) {
			if (cls.equals(Double.class)) {
				return (T) new Double(Double.parseDouble((String) ret));
			}
			if (cls.equals(Long.class)) {
				return (T) new Long(Long.parseLong((String) ret));
			}
			if (cls.equals(Short.class)) {
				return (T) new Short(Short.parseShort((String) ret));
			}
			if (cls.equals(Integer.class)) {
				return (T) new Integer(Integer.parseInt((String) ret));
			}
			if (cls.equals(Float.class)) {
				return (T) new Float(Float.parseFloat((String) ret));
			}
			if (cls.equals(Boolean.class)) {
				return (T) new Boolean(Boolean.parseBoolean((String) ret));
			}
		}

		throw new IllegalArgumentException("cannot interpret value " + ret + " as a " + cls.getCanonicalName());
	}

	/**
	 * Return the most logical non-null value for the passed type, assuming there is
	 * one.
	 * 
	 * @param ret
	 * @param cls
	 * @return the object as a cls or null
	 */
	@SuppressWarnings("unchecked")
	public static <T> T notNull(Class<?> cls) {

		if (cls.isArray()) {

			if (cls.equals(int[].class)) {
				return (T) new int[] {};
			} else if (cls.equals(long[].class)) {
				return (T) new long[] {};
			} else if (cls.equals(float[].class)) {
				return (T) new float[] {};
			} else if (cls.equals(double[].class)) {
				return (T) new double[] {};
			} else if (cls.equals(String[].class)) {
				return (T) new String[] {};
			}
		}

		if (cls.equals(List.class)) {
			List<Object> list = new ArrayList<>();
			return (T) list;
		} else if (cls.equals(Integer.class)) {
			return (T) new Integer(0);
		} else if (cls.equals(Long.class)) {
			return (T) new Long(0);
		} else if (cls.equals(Double.class)) {
			return (T) new Double(Double.NaN);
		} else if (cls.equals(Float.class)) {
			return (T) new Float(Float.NaN);
		} else if (cls.equals(String.class)) {
			return (T) "";
		} else if (cls.equals(Boolean.class)) {
			return (T) Boolean.FALSE;
		}

		// give up
		return null;
	}

	public static Type getArtifactType(Class<?> cls) {

		Type ret = cls == null ? Type.VOID : Type.VALUE;
		if (String.class.isAssignableFrom(cls)) {
			ret = Type.TEXT;
		} else if (Number.class.isAssignableFrom(cls)) {
			ret = Type.NUMBER;
		} else if (Boolean.class.isAssignableFrom(cls)) {
			ret = Type.BOOLEAN;
		} else if (IConcept.class.isAssignableFrom(cls)) {
			ret = Type.CONCEPT;
		}
		return ret;
	}

	public static Class<?> getClassForType(IArtifact.Type type) {
		switch (type) {
		case BOOLEAN:
			return Boolean.class;
		case NUMBER:
			return Double.class;
		case TEXT:
			return String.class;
		case CONCEPT:
			return IConcept.class;
		default:
			break;
		}
		throw new IllegalArgumentException("type " + type + " has no Java class equivalent");
	}

	/**
	 * Binary root of integer.
	 * 
	 * @param x
	 * @return
	 */
	public static int log2int(int x) {
		int y, v;
		if (x <= 0) {
			throw new IllegalArgumentException("" + x + " <= 0");
		}
		v = x;
		y = -1;
		while (v > 0) {
			v >>= 1;
			y++;
		}
		return y;
	}

	/**
	 * Remove prefix: from prefix:xxx.
	 * 
	 * @param string
	 * @return
	 */
	public static String removePrefix(String string) {
		int idx = string.lastIndexOf(':');
		if (idx >= 0) {
			string = string.substring(idx + 1);
		}
		return string;
	}

	public static Object getIgnoreCase(Map<String, ?> map, String key) {
		for (String k : map.keySet()) {
			if (key.compareToIgnoreCase(k) == 0) {
				return map.get(k);
			}
		}
		return null;
	}

	public static Object[] boxArray(double[] a) {
		Object[] ret = new Object[a.length];
		int i = 0;
		for (double aa : a) {
			ret[i++] = aa;
		}
		return ret;
	}

	public static Object[] boxArray(long[] a) {
		Object[] ret = new Object[a.length];
		int i = 0;
		for (long aa : a) {
			ret[i++] = aa;
		}
		return ret;
	}

	public static boolean isFloatingPoint(Number number) {
		return number instanceof Double || number instanceof Float;
	}

	/**
	 * Choose the first non-null object among the passed ones.
	 * 
	 * @param <T>
	 * @param objects
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T chooseNotNull(T... objects) {
		for (T o : objects) {
			if (o != null) {
				return o;
			}
		}
		return null;
	}

	/**
	 * Parse something like xxx(a, b, ... c) into an array [xxx a b c]. Accept xxx
	 * alone and send an array of one element. Not particularly smart, so use when
	 * the coder is.
	 * 
	 * @param string
	 * @return
	 */
	public static String[] parseAsFunctionCall(String string) {

		String id = string;
		String[] parms = null;
		if (string.contains("(") && string.endsWith(")")) {
			int n = string.indexOf('(');
			id = string.substring(0, n);
			String parmstr = string.substring(n + 1, string.length() - 1);
			parms = parmstr.split(",");
		}

		String[] ret = new String[1 + (parms == null ? 0 : parms.length)];
		ret[0] = id.trim();
		if (parms != null) {
			int n = 1;
			for (String p : parms) {
				ret[n++] = p.trim();
			}
		}

		return ret;
	}

}
