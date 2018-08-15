package org.integratedmodelling.klab.clitool.utils;

import org.integratedmodelling.klab.utils.StringUtils;

public class TableUtils {

    /**
     * Nicely format a string containing <strong>correct</strong> k.IM table data.
     * 
     * @param table
     * @param lead
     * @param spacing
     * @return the formatted table
     */
    public static String formatTable(String table, int lead, int spacing) {

        String ret = null;
        String[] rows = table.split("\\,");
        String[] items = rows[0].split("\\|");
        int[] maxSizes = new int[items.length];

        for (String row : rows) {
            String[] its = row.split("\\|");
            int i = 0;
            for (String it : its) {
                if (it.trim().length() > maxSizes[i]) {
                    maxSizes[i] = it.trim().length();
                }
                i++;
            }
        }

        for (int row = 0; row < rows.length; row++) {
            ret += StringUtils.spaces(lead);
            String[] its = rows[row].split("\\|");
            for (int i = 0; i < its.length; i++) {
                String item = its[i].trim();
                int missing = maxSizes[i] - item.length() + spacing;
                ret += StringUtils.spaces(i == 0 ? lead : spacing) + item + StringUtils.spaces(missing)
                        + (i < its.length - 1 ? "|" : (row < rows.length - 1 ? ",\n" : "\n"));
            }
        }

        return ret;

    }

}
