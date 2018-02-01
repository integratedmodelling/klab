package org.integratedmodelling.klab.data;

public enum Aggregators {

  INSTANCE;

  public static interface IAggregator {
    /**
     * @param objects
     * @return the aggregated object
     */
    public Object aggregate(Iterable<Object> objects);
  }

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
