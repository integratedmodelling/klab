package org.integratedmodelling.klab.utils;

/*******************************************************************************
 * Copyright (C) 2007, 2015:
 * 
 * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any
 * other authors listed in @author annotations
 *
 * All rights reserved. This file is part of the k.LAB software suite, meant to enable
 * modular, collaborative, integrated development of interoperable data and model
 * components. For details, see http://integratedmodelling.org.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms
 * of the Affero General Public License Version 3 or any later version.
 *
 * This program is distributed in the hope that it will be useful, but without any
 * warranty; without even the implied warranty of merchantability or fitness for a
 * particular purpose. See the Affero General Public License for more details.
 * 
 * You should have received a copy of the Affero General Public License along with this
 * program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite
 * 330, Boston, MA 02111-1307, USA. The license is also available at:
 * https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.integratedmodelling.klab.exceptions.KlabException;

public class MapUtils extends org.apache.commons.collections.MapUtils {

	/**
	 * Creates a regular hashmap with the objects passed taken in pairs, but does
	 * not add a pair where the value object is null. Does not check that objects
	 * come in even number, so use carefully. Also encodes booleans as "true" or
	 * "false" when the key ends with ?.
	 * 
	 * @param o
	 * @return the map
	 */
	public static Map<String, Object> of(Object... o) {
		Map<String, Object> ret = new HashMap<>();
		for (int i = 0; i < o.length; i += 2) {
			Object value = o[i + 1];
			if (o[i].toString().endsWith("?") && value instanceof Boolean) {
				value = (Boolean) value ? "true" : "false";
			}
			if (value != null) {
				ret.put(o[i].toString(), value);
			}
		}
		return ret;
	}

	/**
	 * Create and save a properties file with the passed contents, using same
	 * conventions as {@link #of(Object...) } Will only throw a runtime exception if
	 * save fails, assuming it's unlikely.
	 * 
	 * @param file
	 * @param o
	 */
	public static void saveProperties(File file, Object... o) {

		Properties prop = new Properties();
		for (int i = 0; i < o.length; i += 2) {
			Object value = o[i + 1];
			if (o[i].toString().endsWith("?") && value instanceof Boolean) {
				value = (Boolean) value ? "true" : "false";
			}
			if (value != null) {
				prop.setProperty(o[i].toString(), value.toString());
			}
		}
		try (FileOutputStream out = new FileOutputStream(file)) {
			prop.store(out, null);
		} catch (Exception e) {
			throw new KlabException(e);
		}
	}

	/**
	 * Creates a regular hashmap with the objects passed taken in pairs; let the
	 * null value in and make no changes to values. Does not check that objects come
	 * in even number, so use carefully.
	 * 
	 * @param o
	 * @return the map
	 */
	public static Map<String, Object> ofWithNull(Object... o) {
		Map<String, Object> ret = new HashMap<>();
		for (int i = 0; i < o.length; i += 2) {
			Object value = o[i + 1];
			ret.put(o[i].toString(), value);
		}
		return ret;
	}

	/**
	 * Print a map on a string in a legible format.
	 * 
	 * @param map
	 * @return
	 */
	public static String dump(Map<?, ?> map) {

		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		final PrintStream outPrint = new PrintStream(out);

		MapUtils.debugPrint(outPrint, "Print Map", map);

		try {
			return out.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "MAP PRINT ERROR: " + e.getMessage();
		}
	}

	public static String toString(Map<?, ?> map) {

		final StringBuffer ret = new StringBuffer(256);

		ret.append("{");
		int i = 0;
		for (Entry<?, ?> entry : map.entrySet()) {
			ret.append((i == 0 ? "" : ", ") + entry.getKey() + ":" + entry.getValue());
			i++;
		}
		ret.append("}");

		return ret.toString();

	}

	/**
	 * Return the object at the given path as the passed class. Each slash-separated
	 * path component, except the last, must point to another map.
	 * 
	 * @param <T>
	 * @param path
	 * @param cls
	 * @return
	 */
	public static <T> T get(Map<?,?> map, String path, Class<T> cls) {
		String[] paths = path.split("/");
		Map<?,?> o = map;
		for (int i = 0; i < paths.length - 1; i++) {
			Object to = o.get(paths[i]);
			if (!(to instanceof Map)) {
				return null;
			}
			o = (Map<?,?>)to;
		}
		return o == null ? null : Utils.asType(o.get(paths[paths.length - 1]), cls);
	}

	/**
	 * Unfold a map into an array with each key followed by the corresponding value.
	 * 
	 * @param vars
	 * @return
	 */
	public static Object[] unfold(Map<?, ?> vars) {

	    if (vars == null) {
	        return new Object[0];
	    }
		
	    Object[] ret = new Object[vars.size() * 2];
		int i = 0;
		for (Object key : vars.keySet()) {
			ret[i] = key;
			ret[++i] = vars.get(key);
		}
		return ret;
	}

}