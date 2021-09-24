package org.integratedmodelling.klab.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StringUtil {

    public static final int WHITESPACE = 0x0001;
    public static final int NONLETTERS = 0x0002;
    public static final int UPPERCASE = 0x0004;
    public static final int SPACES = 0x0008;

    public static String percent(double d) {
        return (int) (Math.round(d * 100.0)) + "%";
    }
    
    /*
     * TODO add options for empty lines handling etc
     */
    public static String[] lines(String text) {
        return text.split("\\r?\\n");
    }

    public static boolean containsAny(String nspc, int flags) {

        for (int i = 0; i < nspc.length(); i++) {
            char c = nspc.charAt(i);
            if ((flags & NONLETTERS) != 0) {
                if ((c < 'A' || c > 'z') && !(c == '.' || c == '_'))
                    return true;
            }
            if ((flags & UPPERCASE) != 0) {
                if (c >= 'A' && c <= 'Z')
                    return true;
            }
            if ((flags & WHITESPACE) != 0) {
                if (Character.isWhitespace(c))
                    return true;
            }
            if ((flags & SPACES) != 0) {
                if (c == ' ' || c == '\t' || c == '\n' || c == '\r')
                    return true;
            }
        }
        return false;
    }

    /**
     * Split into lines and indent each by the given amount.
     *
     * @param s the s
     * @param indent the indent
     * @return the string
     */
    public static String leftIndent(String s, int indent) {
        String pad = spaces(indent);
        String[] strings = s.split("\n");
        StringBuffer buf = new StringBuffer(s.length() + (indent * strings.length));
        for (String ss : strings) {
            buf.append(pad + ss.trim() + "\n");
        }
        return buf.toString();
    }

    /**
     * Return the max line length and the number of lines in the passed paragraph.
     *
     * @param text the text
     * @return the paragraph size
     */
    public static int[] getParagraphSize(String text) {
        int[] ret = new int[]{0, 0};

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
            llen++;
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

    public static String indent(String text, int spaces) {
        String pad = spaces(spaces);
        StringBuffer ret = new StringBuffer(text.length());
        ret.append(pad);
        text = text.trim();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            ret.append(c);
            if (c == '\n') {
                ret.append(pad);
            }
        }
        return ret.toString();
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

        while(i < buf.length()) {
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
     * Remove all leading and trailing whitespace; pack whitespace in between to single space; leave
     * a blank line if there are at least two newlines in the original whitespace. Good for
     * formatting indented and bullshitted text like what you put in XML files into something more
     * suitable for text processing or wiki translation.
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
            while(Character.isWhitespace(s.charAt(i))) {
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
     * Pack the inside of a string but preserve leading and trailing whitespace. Written simply and
     * expensively.
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

    public static String capitalize(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }
        return new StringBuffer(strLen).append(Character.toTitleCase(str.charAt(0))).append(str.substring(1)).toString();
    }

    /**
     * <p>
     * Counts how many times the substring appears in the larger String.
     * </p>
     *
     * <p>
     * A <code>null</code> or empty ("") String input returns <code>0</code>.
     * </p>
     *
     * <pre>
     * StringUtils.countMatches(null, *)       = 0
     * StringUtils.countMatches("", *)         = 0
     * StringUtils.countMatches("abba", null)  = 0
     * StringUtils.countMatches("abba", "")    = 0
     * StringUtils.countMatches("abba", "a")   = 2
     * StringUtils.countMatches("abba", "ab")  = 1
     * StringUtils.countMatches("abba", "xxx") = 0
     * </pre>
     *
     * @param str the String to check, may be null
     * @param sub the substring to count, may be null
     * @return the number of occurrences, 0 if either String is <code>null</code>
     */
    public static int countMatches(String str, String sub) {
        if (isEmpty(str) || isEmpty(sub)) {
            return 0;
        }
        int count = 0;
        int idx = 0;
        while((idx = str.indexOf(sub, idx)) != -1) {
            count++;
            idx += sub.length();
        }
        return count;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static String join(String[] cmds, int i, String string) {
        String ret = "";
        for (int n = i; n < cmds.length; n++) {
            ret += (ret.isEmpty() ? "" : string) + cmds[n];
        }
        return ret;
    }

    public static String join(Collection<String> set, String separator) {
        StringBuffer ret = new StringBuffer(1024);
        for (String string : set) {
            ret.append((ret.length() == 0 ? "" : separator) + string);
        }
        return ret.toString();
    }

    public static String join(String[] set, String separator, int limit) {
        StringBuffer ret = new StringBuffer(1024);
        int i = 0;
        for (String string : set) {
            if (i >= limit) {
                break;
            }
            ret.append((ret.length() == 0 ? "" : separator) + string);
            i++;
        }
        return ret.toString();
    }

    public static int countHeaderCharacters(String string, char c) {
        int ret = 0;
        while(ret < string.length() && string.charAt(ret) == c)
            ret++;
        return ret;
    }

    public static String abbreviate(String string, int n) {
        if (string.length() <= n) {
            return string;
        }
        return string.substring(0, n - 3) + "...";
    }

    /**
     * Split a string of comma-separated values into components. Handles whitespace between commas
     * and strings and returns an empty collection if the passed string is null.
     *
     * @param s the s
     * @return split string
     */
    static public List<String> splitOnCommas(String s) {

        List<String> ret = new ArrayList<>();
        if (s == null) {
            return ret;
        }
        String[] sp = s.split(",");
        for (String ss : sp) {
            ret.add(ss.trim());
        }
        return ret;
    }

    /**
     * Split a string of comma-separated values into doubles. Handles whitespace between commas and
     * strings and returns an empty collection if the passed string is null.
     *
     * @param s the s
     * @return doubles
     */
    static public double[] splitToDoubles(String s) {

        List<String> r = splitOnCommas(s);
        int i = 0;
        double[] ret = new double[r.size()];
        for (String ss : r) {
            ret[i++] = Double.parseDouble(ss);
        }
        return ret;
    }

    /**
     * Split a string of comma-separated values into integers. Handles whitespace between commas and
     * strings and returns an empty collection if the passed string is null.
     *
     * @param s the s
     * @return ints
     */
    static public int[] splitToIntegers(String s) {

        List<String> r = splitOnCommas(s);
        int i = 0;
        int[] ret = new int[r.size()];
        for (String ss : r) {
            ret[i++] = Integer.parseInt(ss);
        }
        return ret;
    }

}
