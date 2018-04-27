package org.integratedmodelling.klab.utils;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtils {
  
  public static <T> List<T> arrayToList(T[] objects) {
    List<T> ret = new ArrayList<>();
    if (objects != null) {
      for (T obj : objects) {
        ret.add(obj);
      }
    }
    return ret;
  }
}
