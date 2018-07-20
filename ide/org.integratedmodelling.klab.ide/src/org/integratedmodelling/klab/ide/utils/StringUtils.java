package org.integratedmodelling.klab.ide.utils;

public class StringUtils {

    /**
     * Return the max line length and the number of lines in the passed paragraph.
     *
     * @param text the text
     * @return the paragraph size
     */
    public static int[] getParagraphSize(String text) {
        int[] ret = new int[] {0,0};
        
        int llen = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '\n') {
                if (ret[0] < llen) {
                    ret[0] = llen;
                }
                llen = 0;
                ret[1] = ret[1] + 1;
            }
            llen ++;
        }
        
        return ret;
    }
    
    /**
     * Spaces.
     *
     * @param n the n
     * @return the string
     */
    public static String spaces(int n) {
        return repeat(' ', n);
    }

    /**
     * Repeat.
     *
     * @param c the c
     * @param n the n
     * @return the repeated string
     */
    public static String repeat(char c, int n) {
        String ret = "";
        for (int i = 0; i < n; i++)
            ret += c;
        return ret;
    }

}
