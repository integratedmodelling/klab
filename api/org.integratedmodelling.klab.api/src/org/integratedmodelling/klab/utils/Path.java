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

// TODO: Auto-generated Javadoc
/**
 * The Class Path.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public class Path {

    /**
     * Gets the last.
     *
     * @param path the path
     * @param separator the separator
     * @return the last
     */
    static public String getLast(String path, char separator) {
        int n = path.lastIndexOf(separator);
        String ret = path;
        if (n >= 0) {
            ret = path.substring(n + 1);
        }
        return ret;
    }

    /**
     * Gets the last.
     *
     * @param path the path
     * @return the last
     */
    static public String getLast(String path) {
        return getLast(path, '/');
    }

    /**
     * Gets the leading.
     *
     * @param path the path
     * @param separator the separator
     * @return the leading
     */
    public static String getLeading(String path, char separator) {
        int n = path.lastIndexOf(separator);
        if (n > 0) {
            return path.substring(0, n);
        }
        return null;
    }

    /**
     * Join.
     *
     * @param pth the pth
     * @param start the start
     * @param separator the separator
     * @return the string
     */
    public static String join(String[] pth, int start, char separator) {
        String ret = "";
        for (int i = start; i < pth.length; i++)
            ret += (ret.isEmpty() ? "" : ".") + pth[i];
        return ret;
    }

    /**
     * Gets the first.
     *
     * @param path the path
     * @param separator the separator
     * @return the first
     */
    public static String getFirst(String path, String separator) {
        int n = path.indexOf(separator);
        String ret = path;
        if (n >= 0) {
            ret = path.substring(0, n);
        }
        return ret;
    }

    public static String getFrom(String path, int n, char separatpr) {
        String s = path;
        for (int i = 0; i < n; i++ ) {
            int idx = s.indexOf(separatpr);
            if (idx >= 0) {
                s = s.substring(idx);
            } else {
                return null;
            }
        }
        return s;
    }

}
