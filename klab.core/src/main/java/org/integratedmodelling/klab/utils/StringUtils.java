/*******************************************************************************
 * Copyright (C) 2007, 2015:
 * 
 * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any
 * other authors listed in @author annotations
 *
 * All rights reserved. This file is part of the k.LAB software suite, meant to enable
 * modular, collaborative, integrated development of interoperable data and model
 * components. For details, see http://integratedmodelling.org.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms
 * of the Affero General Public License Version 3 or any later version.
 *
 * This program is distributed in the hope that it will be useful, but without any
 * warranty; without even the implied warranty of merchantability or fitness for a
 * particular purpose. See the Affero General Public License for more details.
 * 
 * You should have received a copy of the Affero General Public License along with this
 * program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite
 * 330, Boston, MA 02111-1307, USA. The license is also available at:
 * https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.apache.commons.lang.StringUtils {

    public static final int WHITESPACE = 0x0001;
    public static final int NONLETTERS = 0x0002;
    public static final int UPPERCASE  = 0x0004;

    /**
     * Split into lines and indent each by the given amount.
     * 
     * @param s
     * @param indent
     * @return
     */
    public static String leftIndent(String s, int indent) {
        String pad = spaces(indent);
        String[] strings = s.split("\n");
        StringBuffer buf = new StringBuffer(s.length() + (indent*strings.length));
        for (String ss : strings) {
            buf.append(pad + ss.trim() + "\n");
        }
        return buf.toString();
    }
    
    // convert from UTF-8 -> internal Java String format
    public static String convertFromUTF8(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("ISO-8859-1"), "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }
 
    // convert from internal Java String format -> UTF-8
    public static String convertToUTF8(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("UTF-8"), "ISO-8859-1");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }
    
    /**
     * Use this for simple API. Also replicated in our StringUtils extension as
     * matchWildcards().
     * 
     * @param string
     * @param pattern
     * @return
     */
    public static boolean matchWildcards(String string, String pattern) {
    	return new WildcardMatcher().match(string, pattern);
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

    public static String spaces(int n) {
        return repeat(' ', n);
    }

    public static String repeat(char c, int n) {
        String ret = "";
        for (int i = 0; i < n; i++)
            ret += c;
        return ret;
    }

    /**
     * Split a string of comma-separated values into components. Handles whitespace
     * between commas and strings and returns an empty collection if the passed string is
     * null.
     * 
     * @param s
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
     * Split a string of comma-separated values into doubles. Handles whitespace between
     * commas and strings and returns an empty collection if the passed string is null.
     * 
     * @param s
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
     * Split a string of comma-separated values into integers. Handles whitespace between
     * commas and strings and returns an empty collection if the passed string is null.
     * 
     * @param s
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

    /**
     * Divide up a string into tokens, correctly handling double quotes.
     *
     * @param s
     * @return tokens
     */
    static public Collection<String> tokenize(String s) {

        ArrayList<String> ret = new ArrayList<>();
        String regex = "\"([^\"]*)\"|(\\S+)";
        Matcher m = Pattern.compile(regex).matcher(s);
        while (m.find()) {
            if (m.group(1) != null) {
                ret.add(m.group(1));
            } else {
                ret.add(m.group(2));
            }
        }
        return ret;
    }

    /**
     * Join a collection of strings into one string using the given char as separator. Can
     * pass any collection; toString() will be used.
     * 
     * @param strings
     * @param separator
     * @return joined collection
     */
    static public String joinCollection(Collection<?> strings, char separator) {
        String ret = "";
        for (Object s : strings) {
            ret += (ret.isEmpty() ? "" : ("" + separator)) + s;
        }
        return ret;
    }

    /**
     * Join an array of strings into one string using the given char as separator.
     * 
     * @param strings
     * @param separator
     * @return joined array
     */
    static public String join(String[] strings, char separator) {
        String ret = "";
        for (String s : strings) {
            ret += (ret.isEmpty() ? "" : ("" + separator)) + s;
        }
        return ret;
    }

    /**
     * Join an array of strings into one string using the given char as separator.
     * 
     * @param objects
     * @param separator
     * @return joined objects
     */
    static public String joinObjects(Object[] objects, char separator) {
        String ret = "";
        for (Object s : objects) {
            ret += (ret.isEmpty() ? "" : ("" + separator)) + (s == null ? "" : s.toString());
        }
        return ret;
    }

    static public String joinObjects(Collection<?> objects, char separator) {
        return joinObjects(objects.toArray(), separator);
    }

    static public String joinObjects(Map<?, ?> objects, char separator) {
        String ret = "";
        for (Object s : objects.keySet()) {
            ret += (ret.isEmpty() ? "" : ("" + separator)) + s + "="
                    + (objects.get(s) == null ? "" : objects.get(s).toString());
        }
        return ret;
    }

    /**
     * Remove character at position
     * 
     * @param s
     * @param pos
     * @return string without pos-th character
     */
    public static String removeCharAt(String s, int pos) {

        if (pos == s.length() - 1) {
            return chop(s);
        }

        StringBuffer buf = new StringBuffer(s.length() - 1);
        buf.append(s.substring(0, pos)).append(s.substring(pos + 1));
        return buf.toString();
    }

    public static String insertCharAt(String s, char c, int pos) {

        StringBuffer buf = new StringBuffer(s);
        buf.insert(pos, c);
        return buf.toString();
    }

    public static String replaceAt(String str, int index, char replace) {
        if (str == null) {
            return str;
        } else if (index < 0 || index >= str.length()) {
            return str;
        }
        char[] chars = str.toCharArray();
        chars[index] = replace;
        return String.valueOf(chars);
    }

    public static String percent(double d) {
        return (int) (Math.round(d * 100.0)) + "%";
    }

    public static boolean containsAny(String nspc, int flags) {

        for (int i = 0; i < nspc.length(); i++) {
            char c = nspc.charAt(i);
            if ((flags | NONLETTERS) != 0) {
                if ((c < 'A' || c > 'z') && !(c == '.' || c == '_'))
                    return true;
            }
            if ((flags | UPPERCASE) != 0) {
                if (c >= 'A' && c <= 'Z')
                    return true;
            }
            if ((flags | WHITESPACE) != 0) {
                if (Character.isWhitespace(c))
                    return true;
            }
        }
        return false;
    }

    public static String replaceWhitespace(String text, String replacement) {
        return text.replaceAll("\\s+", replacement);
    }

    public static String join(String[] objects, String separator, int max) {
        String ret = "";
        int n = 0;
        for (Object s : objects) {
            n++;
            if (n > max) {
                break;
            }
            ret += (ret.isEmpty() ? "" : ("" + separator)) + (s == null ? "" : s.toString());
        }
        return ret;
    }

    /**
     * Return the max line length and the number of lines in the passed paragraph.
     * 
     * @return
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
     * Return the collection of strings that contains the passed pattern within the passed
     * collection, ordered so that the earliest matches come first (if pattern is 'aaa', 
     * 'aaabbb' will come before 'bbbaaa').
     * @param source
     * @return
     */
    public static List<String> matchIn(String pattern, Collection<String> source, int minLenForInsideMatch) {
        KMP kmp = new KMP(pattern);
        List<Pair<Integer, String>> matches = new ArrayList<>();
        for (String s : source) {
            int n = kmp.search(s);
            if (n < s.length() && (n < minLenForInsideMatch)) {
                matches.add(new Pair<>(n, s));
            }
        }
        
        Collections.sort(matches, new Comparator<Pair<Integer,String>>(){
            @Override
            public int compare(Pair<Integer, String> arg0, Pair<Integer, String> arg1) {
                return arg0.getFirst().compareTo(arg1.getFirst());
            }
        });
        
        List<String> ret = new ArrayList<>();
        for (Pair<Integer, String> p : matches) {
            ret.add(p.getSecond());
        }
        return ret;
    }
    
    /**
     * Compute the initial part that does not change in the string representation of the 
     * passed objects.
     * 
     * @param strings
     * @return
     */
    public static String computeInvariantPrefix(Collection<?> strings) {
        
        String ret = null;
        for (Object o : strings) {
            
            if (o == null) {
                continue;
            }
            
            String str = o.toString();
            
            if (ret == null) {
                ret = str;
            } else {

                while (!str.startsWith(ret) && !ret.isEmpty()) {
                    ret = chop(ret);
                }
                
                if (ret.isEmpty()) {
                    return ret;
                }
            }
        }
        return ret;
    }

}
