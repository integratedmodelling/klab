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

public class Path {

    static public String getLast(String path, char separator) {
        int n = path.lastIndexOf(separator);
        String ret = path;
        if (n >= 0) {
            ret = path.substring(n + 1);
        }
        return ret;
    }

    static public String getLast(String path) {
        return getLast(path, '/');
    }

    public static String getLeading(String path, char separator) {
        int n = path.lastIndexOf(separator);
        if (n > 0) {
            return path.substring(0, n);
        }
        return null;
    }

    public static String join(String[] pth, int start, char separator) {
        String ret = "";
        for (int i = start; i < pth.length; i++)
            ret += (ret.isEmpty() ? "" : ".") + pth[i];
        return ret;
    }

    public static String getFirst(String path, String separator) {
        int n = path.indexOf(separator);
        String ret = path;
        if (n >= 0) {
            ret = path.substring(0, n);
        }
        return ret;
    }

}
