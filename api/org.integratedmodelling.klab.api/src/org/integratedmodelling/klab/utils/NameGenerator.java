/*******************************************************************************
 * Copyright (C) 2007, 2014:
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
package org.integratedmodelling.klab.utils;

import java.util.Collection;

/**
 * A utility class that knows how to create unique temporary names. Just a wrapper around Java nanotime
 * with some recognition methods.
 * 
 * TODO not sure if these methods need synchronization.
 * This was changed from the call so UUID because under some conditions the system can run out of entropy
 * resulting in blocking and long read times.
 * 
 * @author Ferdinando Villa, Ecoinformatics Collaboratory, UVM
 *
 */
public class NameGenerator {

  static long index = 0;

  static public String newName() {
    return "_uu_" + System.nanoTime();
  }

  static public String newName(String prefix) {
    return "_uu_" + prefix + ":" + System.nanoTime();
  }

  static public boolean isGenerated(String name) {
    return name.startsWith("_uu_");
  }

  /**
   * Short ID with the same data of a the JVM nanoTime.
   * 
   * @return a short uuid
   */
  public static String shortUUID() {
    long l = System.nanoTime();
    return Long.toString(l, Character.MAX_RADIX);
  }

  /**
   * Create an identifier of the maximum given length from a passed name, avoiding conflicts with
   * others. Use dash to separate parts if required.
   * 
   * @param source
   * @param maxLength
   * @param makeLowercase
   * @param blacklist
   * @return a new identifier
   */
  public static String getIdentifier(String source, int maxLength, boolean makeLowercase,
      Collection<String> blacklist) {

    String ret = source;
    if (ret.length() > maxLength) {
      String[] spl = ret.split("\\-");
      if (spl.length > 1) {
        int n = maxLength / spl.length;
        ret = "";
        for (String p : spl) {
          ret += p.substring(0, Math.min(n, p.length() - 1));
        }
      } else {
        ret = ret.substring(0, maxLength);
      }
    }

    if (makeLowercase) {
      ret = ret.toLowerCase();
    }

    if (blacklist != null && blacklist.contains(ret)) {
      int i = 1;
      int cutoff = ret.length() - 2;
      if (cutoff > maxLength - 2) {
        cutoff = maxLength - 2;
      }
      while (blacklist.contains(ret)) {
        ret = ret.substring(0, cutoff) + (i++);
      }
    }

    return ret;
  }
}
