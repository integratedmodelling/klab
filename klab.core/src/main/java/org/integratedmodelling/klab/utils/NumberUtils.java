package org.integratedmodelling.klab.utils;

public class NumberUtils extends org.integratedmodelling.kim.utils.NumberUtils {

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
