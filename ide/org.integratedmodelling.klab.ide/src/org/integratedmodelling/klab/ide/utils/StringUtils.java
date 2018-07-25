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

    /**
     * Justify left to passed width with wordwrap.
     * 
     * @param text
     * @param width
     * @return
     */
    public static String justifyLeft(String text, int width) {
        StringBuffer buf = new StringBuffer(text);
        int lastspace = -1;
        int linestart = 0;
        int i = 0;

        while (i < buf.length()) {
            if (buf.charAt(i) == ' ')
                lastspace = i;
            if (buf.charAt(i) == '\n') {
                lastspace = -1;
                linestart = i + 1;
            }
            if (i > linestart + width - 1) {
                if (lastspace != -1) {
                    buf.setCharAt(lastspace, '\n');
                    linestart = lastspace + 1;
                    lastspace = -1;
                } else {
                    buf.insert(i, '\n');
                    linestart = i + 1;
                }
            }
            i++;
        }
        return buf.toString();
    }

    /**
     * Remove all leading and trailing whitespace; pack whitespace in between to single
     * space; leave a blank line if there are at least two newlines in the original
     * whitespace. Good for formatting indented and bullshitted text like what you put in
     * XML files into something more suitable for text processing or wiki translation.
     *
     * @param s
     * @return packed string
     */
    static public String pack(String s) {

        if (s == null) {
            return "";
        }

        StringBuffer ret = new StringBuffer(s.length());

        s = s.trim();

        for (int i = 0; i < s.length(); i++) {

            int nlines = 0;
            int wp = 0;
            while (Character.isWhitespace(s.charAt(i))) {
                if (s.charAt(i) == '\n') {
                    nlines++;
                }
                i++;
                wp++;
            }
            if (wp > 0) {
                ret.append(nlines > 1 ? "\n\n" : " ");
            }
            ret.append(s.charAt(i));
        }

        return ret.toString();
    }
    
    public static String getLeadingWhitespace(String s) {
        StringBuffer ret = new StringBuffer(20);
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                break;
            } 
            ret.append(s.charAt(i));
        }
        return ret.toString();
    }
    
    public static String getTrailingWhitespace(String s) {
        StringBuffer ret = new StringBuffer(20);
        for (int i = s.length() - 1; i >= 0; i--) {
            if (!Character.isWhitespace(s.charAt(i))) {
                break;
            } 
            ret.append(s.charAt(i));
        }
        return ret.toString();
    }

    /**
     * Pack the inside of a string but preserve leading and trailing whitespace. Written
     * simply and expensively.
     * 
     * @param s
     * @return
     */
    public static String packInternally(String s) {
        if (s.trim().isEmpty()) {
            return s;
        }
        return getLeadingWhitespace(s) + pack(s) + getTrailingWhitespace(s);
    }


}
