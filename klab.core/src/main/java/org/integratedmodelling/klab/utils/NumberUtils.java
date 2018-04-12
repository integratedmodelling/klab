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
 * The Class NumberUtils.
 */
public class NumberUtils extends org.integratedmodelling.kim.utils.NumberUtils {

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

}
