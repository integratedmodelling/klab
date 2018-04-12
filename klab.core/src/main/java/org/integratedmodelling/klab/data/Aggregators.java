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
package org.integratedmodelling.klab.data;

// TODO: Auto-generated Javadoc
/**
 * The Enum Aggregators.
 */
public enum Aggregators {

  INSTANCE;

  /**
   * The Interface IAggregator.
   */
  public static interface IAggregator {
    
    /**
     * Aggregate.
     *
     * @param objects the objects
     * @return the aggregated object
     */
    public Object aggregate(Iterable<Object> objects);
  }

  /**
   * The Class Mean.
   */
  public static class Mean implements IAggregator {

      @Override
      public Object aggregate(Iterable<Object> objects) {

          double sum = 0.0;
          double n = 0;

          for (Object o : objects) {
              sum += INSTANCE.toDouble(o);
              n += 1.0;
          }

          return sum / n;
      }
  }

  /**
   * The Class Sum.
   */
  public static class Sum implements IAggregator {

      @Override
      public Object aggregate(Iterable<Object> objects) {

          double sum = 0.0;
          for (Object o : objects) {
              sum += INSTANCE.toDouble(o);
          }
          return sum;
      }

  }

  /**
   * The Class Min.
   */
  public static class Min implements IAggregator {

      @Override
      public Object aggregate(Iterable<Object> objects) {

          double min = Double.NaN;

          for (Object o : objects) {
              double d = INSTANCE.toDouble(o);
              if (!Double.isNaN(d) && (Double.isNaN(min) || d < min)) {
                  min = d;
              }
          }

          return min;
      }
  }

  /**
   * The Class Max.
   */
  public static class Max implements IAggregator {

      @Override
      public Object aggregate(Iterable<Object> objects) {

          double max = Double.NaN;

          for (Object o : objects) {
              double d = INSTANCE.toDouble(o);
              if (!Double.isNaN(d) && (Double.isNaN(max) || d > max)) {
                  max = d;
              }
          }

          return max;
      }
  }
  
  /**
   * To double.
   *
   * @param o the o
   * @return the double
   */
  public double toDouble(Object o) {

    double ret = Double.NaN;
    if (o instanceof String) {
        ret = Double.parseDouble(o.toString());
    } else if (o instanceof Number) {
        ret = ((Number) o).doubleValue();
    }
    return ret;
}
}
