package org.integratedmodelling.klab.api.utils;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.integratedmodelling.klab.api.collections.impl.Pair;
import org.integratedmodelling.klab.api.exceptions.KIOException;
import org.integratedmodelling.klab.api.exceptions.KIllegalArgumentException;
import org.integratedmodelling.klab.api.knowledge.KArtifact;
import org.integratedmodelling.klab.api.knowledge.KArtifact.Type;
import org.integratedmodelling.klab.api.knowledge.KConcept;
import org.integratedmodelling.klab.api.knowledge.observation.KObservation;
import org.integratedmodelling.klab.api.lang.kim.KKimScope;
import org.integratedmodelling.klab.api.services.runtime.KNotification;

public class Utils {

    /**
     * Utility to reduce the ugliness of casting generic collections in Java. If you have say a
     * Collection<A> (ca) that you know is a Collection<B extends A> and you need a Collection<B>,
     * do the following:
     * 
     * Collection<B> cb = new Cast<A,B>.cast(ca);
     * 
     * and type safety be damned. This will not generate any warning and will avoid any silly copy.
     * Works for generic collections, arraylists and hashsets - add more if needed. Needs something
     * else for maps.
     *
     * @author ferdinando.villa
     * @version $Id: $Id
     * @param <B> the generic type
     * @param <T> the generic type
     */
    public static class Casts<B, T> {

        /**
         * Cast.
         *
         * @param b the b
         * @return the collection
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Collection<T> cast(Collection<B> b) {
            return (Collection<T>) (Collection) b;
        }

        /**
         * Cast.
         *
         * @param b the b
         * @return the list
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        public List<T> cast(List<B> b) {
            return (List<T>) (List) b;
        }

        /**
         * Cast.
         *
         * @param b the b
         * @return the sets the
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        public Set<T> cast(Set<B> b) {
            return (Set<T>) (Set) b;
        }
    }

    public static class Paths {

        /**
         * Gets the last.
         *
         * @param path the path
         * @param separator the separator
         * @return the last
         */
        static public String getLast(String path, char separator) {
            int n = path.lastIndexOf(separator);
            String ret = path;
            if (n >= 0) {
                ret = path.substring(n + 1);
            }
            return ret;
        }

        /**
         * Gets the last.
         *
         * @param path the path
         * @return the last
         */
        static public String getLast(String path) {
            return getLast(path, '/');
        }

        /**
         * Gets the leading.
         *
         * @param path the path
         * @param separator the separator
         * @return the leading
         */
        public static String getLeading(String path, char separator) {
            int n = path.lastIndexOf(separator);
            if (n > 0) {
                return path.substring(0, n);
            }
            return null;
        }

        /**
         * Join.
         *
         * @param pth the pth
         * @param start the start
         * @param separator the separator
         * @return the string
         */
        public static String join(String[] pth, int start, char separator) {
            String ret = "";
            for (int i = start; i < pth.length; i++)
                ret += (ret.isEmpty() ? "" : ".") + pth[i];
            return ret;
        }

        /**
         * Gets the first.
         *
         * @param path the path
         * @param separator the separator
         * @return the first
         */
        public static String getFirst(String path, String separator) {
            int n = path.indexOf(separator);
            String ret = path;
            if (n >= 0) {
                ret = path.substring(0, n);
            }
            return ret;
        }

        /**
         * Get reminder after first
         * 
         * @param path
         * @param separator
         * @return
         */
        public static String getRemainder(String path, String separator) {
            int n = path.indexOf(separator);
            String ret = path;
            if (n >= 0) {
                ret = path.substring(n + 1);
            }
            return ret;
        }

        public static String getFrom(String path, int n, char separatpr) {
            String s = path;
            for (int i = 0; i < n; i++) {
                int idx = s.indexOf(separatpr);
                if (idx >= 0) {
                    s = s.substring(idx);
                } else {
                    return null;
                }
            }
            return s;
        }

    }

    public static class Notifications {

        /**
         * Organizes a set of inputs into a message and a severity level.
         *
         * @param objects the objects
         * @return the message
         */
        public static Pair<String, KNotification.Type> getMessage(Object... objects) {

            StringBuffer ret = new StringBuffer(256);
            KNotification.Type ntype = null;

            for (Object o : objects) {
                if (o instanceof String) {
                    ret.append((ret.length() == 0 ? "" : " ") + o);
                } else if (o instanceof Throwable) {
                    ret.append((ret.length() == 0 ? "" : " ") + ((Throwable) o).getLocalizedMessage());
                } else if (o instanceof KKimScope) {
                    ret.insert(0, ((KKimScope) o).getLocationDescriptor() + ": ");
                } else if (o instanceof KNotification.Type) {
                    ntype = (KNotification.Type) o;
                } else if (o instanceof KNotification) {
                    ntype = ((KNotification) o).getType();
                    ret.append(((KNotification) o).getMessage());
                }
                // TODO continue
            }

            return new Pair<>(ret.toString(), ntype);
        }
    }

    public static class Escape {

        /**
         * List for all ASCII characters whether it can be part of an URL line.
         */
        static boolean isacceptable[] = {false, false, false, false, false, false, false, false, // !"#$%&'
                false, false, true, true, true, true, true, false, // ()*+,-./
                true, true, true, true, true, true, true, true, // 01234567
                true, true, true, false, false, false, false, false, // 89:;<=>?
                true, true, true, true, true, true, true, true, // @ABCDEFG
                true, true, true, true, true, true, true, true, // HIJKLMNO
                true, true, true, true, true, true, true, true, // PQRSTUVW
                true, true, true, false, false, false, false, true, // XYZ[\]^_
                false, true, true, true, true, true, true, true, // `abcdefg
                true, true, true, true, true, true, true, true, // hijklmno
                true, true, true, true, true, true, true, true, // pqrstuvw
                true, true, true, false, false, false, false, false // xyz{|}~
        };

        /**
         * Hex characters
         */
        static char hex[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        /**
         * Character to use for escaping invalid characters
         */
        static char HEX_ESCAPE = '%';

        /**
         * Escape a url. Replaces 'invalid characters' with their Escaped code, i.e. the
         * questionmark (?) is escaped with %3F.
         *
         * @param str the urls to escape
         * @return the escaped url.
         */
        public static String escapeurl(String str) {
            int i, a;
            StringBuffer esc = new StringBuffer();
            byte[] buf = str.getBytes();

            for (i = 0; i < str.length(); i++) {
                a = buf[i] & 0xff;
                if (a >= 32 && a < 128 && isacceptable[a - 32]) {
                    esc.append((char) a);
                } else {
                    esc.append(HEX_ESCAPE);
                    esc.append(hex[a >> 4]);
                    esc.append(hex[a & 15]);
                }
            }
            return esc.toString();
        }

        /**
         * converts a HEX-character to its approprtiate byte value. i.e. 'A' is returned as '/011'
         * 
         * @param c teh Hex character
         * @return the byte value as a <code>char</code>
         */
        private static char from_hex(char c) {
            return (char) (c >= '0' && c <= '9'
                    ? c - '0'
                    : c >= 'A' && c <= 'F'
                            ? c - 'A' + 10
                            : c - 'a' + 10); /* accept small letters just in case */
        }

        /**
         * Unescape a url. Replaces escapesequenced with the actual character. i.e %3F is replaced
         * with the the questionmark (?).
         *
         * @param str the urls to unescape
         * @return the unescaped url.
         */
        public static String unescapeurl(String str) {
            int i;
            char j, t;
            StringBuffer esc = new StringBuffer();
            for (i = 0; i < str.length(); i++) {
                t = str.charAt(i);
                if (t == HEX_ESCAPE) {
                    t = str.charAt(++i);
                    j = (char) (from_hex(t) * 16);
                    t = str.charAt(++i);
                    j += from_hex(t);
                    esc.append(j);
                } else {
                    esc.append(t);
                }
            }
            return esc.toString();
        }

        /**
         * Escape characters for text appearing in HTML markup.
         * 
         * <P>
         * This method exists as a defence against Cross Site Scripting (XSS) hacks. This method
         * escapes all characters recommended by the Open Web App Security Project -
         * <a href='http://www.owasp.org/index.php/Cross_Site_Scripting'>link</a>.
         * 
         * <P>
         * Note that JSTL's {@code c:out} escapes <em>only the first five</em> of the above
         * characters.
         * 
         * @param aText
         * @return the escaped string
         */
        public static String forHTML(String aText) {
            final StringBuilder result = new StringBuilder();
            final StringCharacterIterator iterator = new StringCharacterIterator(aText);
            char character = iterator.current();
            while(character != CharacterIterator.DONE) {
                if (character == '<') {
                    result.append("&lt;");
                } else if (character == '>') {
                    result.append("&gt;");
                } else if (character == '&') {
                    result.append("&amp;");
                } else if (character == '\"') {
                    result.append("&quot;");
                } else if (character == '\'') {
                    result.append("&#039;");
                } else if (character == '(') {
                    result.append("&#040;");
                } else if (character == ')') {
                    result.append("&#041;");
                } else if (character == '#') {
                    result.append("&#035;");
                } else if (character == '%') {
                    result.append("&#037;");
                } else if (character == ';') {
                    result.append("&#059;");
                } else if (character == '+') {
                    result.append("&#043;");
                } else if (character == '-') {
                    result.append("&#045;");
                } else {
                    // the char is not a special one
                    // add it to the result as is
                    result.append(character);
                }
                character = iterator.next();
            }
            return result.toString();
        }

        /**
         * Synonym for <tt>URLEncoder.encode(String, "UTF-8")</tt>.
         *
         * <P>
         * Used to ensure that HTTP query strings are in proper form, by escaping special characters
         * such as spaces.
         *
         * <P>
         * It is important to note that if a query string appears in an <tt>HREF</tt> attribute,
         * then there are two issues - ensuring the query string is valid HTTP (it is URL-encoded),
         * and ensuring it is valid HTML (ensuring the ampersand is escaped).
         * 
         * @param aURLFragment
         * @return the escaped string
         */
        public static String forURL(String aURLFragment) {
            String result = null;
            try {
                result = URLEncoder.encode(aURLFragment, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                throw new RuntimeException("UTF-8 not supported", ex);
            }
            return result;
        }

        /**
         * Synonym for <tt>URLEncoder.encode(String, "UTF-8")</tt>.
         *
         * <P>
         * Used to ensure that HTTP query strings are in proper form, by escaping special characters
         * such as spaces.
         *
         * <P>
         * It is important to note that if a query string appears in an <tt>HREF</tt> attribute,
         * then there are two issues - ensuring the query string is valid HTTP (it is URL-encoded),
         * and ensuring it is valid HTML (ensuring the ampersand is escaped).
         * 
         * @param aURLFragment
         * @return the escaped string
         */
        public static String fromURL(String aURLFragment) {
            String result = null;
            try {
                result = URLDecoder.decode(aURLFragment, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                throw new RuntimeException("UTF-8 not supported", ex);
            }
            return result;
        }

        /**
         * Escape characters for text appearing as XML data, between tags.
         * <p>
         * Note that JSTL's {@code <c:out>} escapes the exact same set of characters as this method.
         * <span class='highlight'>That is, {@code <c:out>} is good for escaping to produce valid
         * XML, but not for producing safe HTML.</span>
         *
         * @param aText
         * @return the escaped string
         */
        public static String forXML(String aText) {
            final StringBuilder result = new StringBuilder();
            final StringCharacterIterator iterator = new StringCharacterIterator(aText);
            char character = iterator.current();
            while(character != CharacterIterator.DONE) {
                if (character == '<') {
                    result.append("&lt;");
                } else if (character == '>') {
                    result.append("&gt;");
                } else if (character == '\"') {
                    result.append("&quot;");
                } else if (character == '\'') {
                    result.append("&#039;");
                } else if (character == '&') {
                    result.append("&amp;");
                } else {
                    // the char is not a special one
                    // add it to the result as is
                    result.append(character);
                }
                character = iterator.next();
            }
            return result.toString();
        }

        /**
         * Return <tt>aText</tt> with all <tt>'<'</tt> and <tt>'>'</tt> characters replaced by their
         * escaped equivalents.
         * 
         * @param aText
         * @return the escaped string
         */
        public static String toDisableTags(String aText) {
            final StringBuilder result = new StringBuilder();
            final StringCharacterIterator iterator = new StringCharacterIterator(aText);
            char character = iterator.current();
            while(character != CharacterIterator.DONE) {
                if (character == '<') {
                    result.append("&lt;");
                } else if (character == '>') {
                    result.append("&gt;");
                } else {
                    // the char is not a special one
                    // add it to the result as is
                    result.append(character);
                }
                character = iterator.next();
            }
            return result.toString();
        }

        /**
         * Replace characters having special meaning in regular expressions with their escaped
         * equivalents, preceded by a '\' character.
         *
         * @param aRegexFragment
         * @return the escaped string
         */
        public static String forRegex(String aRegexFragment) {
            final StringBuilder result = new StringBuilder();

            final StringCharacterIterator iterator = new StringCharacterIterator(aRegexFragment);
            char character = iterator.current();
            while(character != CharacterIterator.DONE) {
                /*
                 * All literals need to have backslashes doubled.
                 */
                if (character == '.') {
                    result.append("\\.");
                } else if (character == '\\') {
                    result.append("\\\\");
                } else if (character == '?') {
                    result.append("\\?");
                } else if (character == '*') {
                    result.append("\\*");
                } else if (character == '+') {
                    result.append("\\+");
                } else if (character == '&') {
                    result.append("\\&");
                } else if (character == ':') {
                    result.append("\\:");
                } else if (character == '{') {
                    result.append("\\{");
                } else if (character == '}') {
                    result.append("\\}");
                } else if (character == '[') {
                    result.append("\\[");
                } else if (character == ']') {
                    result.append("\\]");
                } else if (character == '(') {
                    result.append("\\(");
                } else if (character == ')') {
                    result.append("\\)");
                } else if (character == '^') {
                    result.append("\\^");
                } else if (character == '$') {
                    result.append("\\$");
                } else {
                    // the char is not a special one
                    // add it to the result as is
                    result.append(character);
                }
                character = iterator.next();
            }
            return result.toString();
        }

        public static String forSQL(String aRegexFragment) {
            final StringBuilder result = new StringBuilder();

            final StringCharacterIterator iterator = new StringCharacterIterator(aRegexFragment);
            char character = iterator.current();
            while(character != CharacterIterator.DONE) {
                /*
                 * All literals need to have backslashes doubled.
                 */
                if (character == '\'') {
                    result.append("''");
                } else if (character == '\\') {
                    result.append("\\\\");
                } else {
                    // the char is not a special one
                    // add it to the result as is
                    result.append(character);
                }
                character = iterator.next();
            }
            return result.toString();
        }

        public static String forDoubleQuotedString(String s, boolean removeNewlines) {
            final StringBuilder result = new StringBuilder();

            final StringCharacterIterator iterator = new StringCharacterIterator(s);
            char character = iterator.current();
            while(character != CharacterIterator.DONE) {
                /*
                 * All literals need to have backslashes doubled.
                 */
                if (character == '"') {
                    result.append("\\\"");
                } else if (character == '\n' && removeNewlines) {
                    result.append(' ');
                } else {
                    // the char is not a special one
                    // add it to the result as is
                    result.append(character);
                }
                character = iterator.next();
            }
            return result.toString();
        }

        public static String collapseWhitespace(String s) {

            final StringBuilder result = new StringBuilder();
            final StringCharacterIterator iterator = new StringCharacterIterator(s);
            char character = iterator.current();
            while(character != CharacterIterator.DONE) {
                /*
                 * All literals need to have backslashes doubled.
                 */
                boolean wasw = false;
                while(Character.isWhitespace(character)) {
                    wasw = true;
                    character = iterator.next();
                }
                if (wasw) {
                    result.append(' ');
                } else {
                    result.append(character);
                    character = iterator.next();
                }
            }
            return result.toString();
        }

    }

    public static class CamelCase {

        /**
         * Converts a string to upper underscore case (Ex. "justASimple_String" becomes
         * "JUST_A_SIMPLE_STRING"). This function is not idempotent (Eg. "abc" => "ABC" => "A_B_C").
         * 
         * @param value
         * @param sep
         * @return the upper case string with the chosen separator
         */
        public static String toUpperCase(String value, char sep) {
            return toUnderscoreCaseHelper(value, true, sep);
        }

        /**
         * Converts a string to lower underscore case (Ex. "justASimple_String" becomes
         * "just_a_simple_string"). This function is idempotent.
         * 
         * @param value
         * @param sep
         * @return the lower case string with the chosen separator
         */
        public static String toLowerCase(String value, char sep) {
            return toUnderscoreCaseHelper(normalizeAcronyms(value), false, sep);
        }

        /**
         * Turn any ABCD consecutive string of uppercase characters into Abcd so that lowercase
         * conversion becomes abcd_ instead of a_b_c_d_.
         * 
         * @param value
         * @return the normalized string
         */
        public static String normalizeAcronyms(String value) {

            StringBuilder result = new StringBuilder(value.length());
            boolean inAcronym = false;
            for (int i = 0; i < value.length(); i++) {
                char ch = value.charAt(i);
                if ((ch >= 'A') && (ch <= 'Z')) {
                    if (inAcronym) {
                        result.append(toAsciiLowerCase(ch));
                    } else {
                        inAcronym = true;
                        result.append(ch);
                    }
                } else {
                    inAcronym = false;
                    result.append(ch);
                }
            }
            return result.toString();
        }

        /**
         * Converts a string to lower came case (Ex. "justASimple_String" becomes
         * "JustASimpleString"). This function is idempotent.
         * 
         * @param value
         * @param sep
         * @return the upper underscore case string
         */
        public static String toUpperCamelCase(String value, char sep) {
            return toCamelCaseHelper(value, true, sep);
        }

        /**
         * Converts a string to lower came case (Ex. "justASimple_String" becomes
         * "justASimpleString"). This function is idempotent.
         * 
         * @param value
         * @param sep
         * @return the upper underscore case string
         */
        public static String toLowerCamelCase(String value, char sep) {
            return toCamelCaseHelper(value, false, sep);
        }

        /**
         * Helper function for underscore case functions.
         * 
         * @param value
         * @param upperCase
         * @return
         */
        private static String toUnderscoreCaseHelper(String value, boolean upperCase, char sep) {
            if (value == null) {
                return null;
            }
            // 10% percent increase estimation, minimum 8
            int estimatedSize = value.length() * 11 / 10;
            if (value.length() < 8) {
                estimatedSize = 8;
            }
            StringBuilder result = new StringBuilder(estimatedSize);
            boolean underscoreWritten = true;
            for (int i = 0; i < value.length(); i++) {
                char ch = value.charAt(i);
                if ((ch >= 'A') && (ch <= 'Z')) {
                    if (!underscoreWritten) {
                        result.append(sep);
                    }

                }
                result.append((upperCase) ? toAsciiUpperCase(ch) : toAsciiLowerCase(ch));
                underscoreWritten = (ch == sep);
            }
            return result.toString();
        }

        /**
         * Helper function for camel case functions.
         * 
         * @param value
         * @param upperCase
         * @return
         */
        private static String toCamelCaseHelper(String value, boolean upperCase, char sep) {
            if (value == null) {
                return null;
            }
            if (value.length() == 0) {
                return "";
            }
            char firstChar = value.charAt(0);
            char firstCharCorrected = (upperCase) ? toAsciiUpperCase(firstChar) : toAsciiLowerCase(firstChar);
            if (value.indexOf(sep) == -1) {
                if (firstChar != firstCharCorrected) {
                    return firstCharCorrected + value.substring(1);
                } else {
                    return value;
                }
            }
            StringBuilder result = new StringBuilder(value.length());
            result.append(firstCharCorrected);
            boolean nextIsUpperCase = false;
            for (int i = 1; i < value.length(); i++) {
                char ch = value.charAt(i);
                if (ch == sep) {
                    nextIsUpperCase = true;
                } else {
                    if (nextIsUpperCase) {
                        result.append(toAsciiUpperCase(ch));
                        nextIsUpperCase = false;
                    } else {
                        result.append(ch);
                    }
                }
            }
            return result.toString();
        }

        /**
         * Converts the specified character to upper case if it is a valid latin letter, otherwise
         * the same character is returned.
         * 
         * @param ch
         * @return the upper cased char
         */
        public static char toAsciiUpperCase(char ch) {
            if ((ch >= 'a') && (ch <= 'z')) {
                return (char) (ch + 'A' - 'a');
            } else {
                return ch;
            }
        }

        /**
         * Converts the specified character to lower case if it is a valid latin letter, otherwise
         * the same character is returned.
         * 
         * @param ch
         * @return the upper cased char
         */
        public static char toAsciiLowerCase(char ch) {
            if ((ch >= 'A') && (ch <= 'Z')) {
                return (char) (ch + 'a' - 'A');
            } else {
                return ch;
            }
        }
    }

    public static class Numbers {

        public static Object[] boxArray(double[] a) {
            Object[] ret = new Object[a.length];
            int i = 0;
            for (double aa : a) {
                ret[i++] = aa;
            }
            return ret;
        }

        public static Object[] boxArray(long[] a) {
            Object[] ret = new Object[a.length];
            int i = 0;
            for (long aa : a) {
                ret[i++] = aa;
            }
            return ret;
        }

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

        /**
         * 2 ^ -24 - this is for FLOAT precision, but I'm using it for doubles as well. See:
         * http://en.wikipedia.org/wiki/Machine_epsilon#Values_for_standard_hardware_floating_point_arithmetics
         */
        public static final double EPSILON = 5.96e-08;
        public static final Pattern DOUBLE_PATTERN = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?");
        public static final Pattern INTEGER_PATTERN = Pattern.compile("^-?\\d+$");

        public static boolean encodesDouble(String s) {
            return DOUBLE_PATTERN.matcher(s).matches();
        }

        public static boolean encodesLong(String s) {
            return INTEGER_PATTERN.matcher(s).matches();
        }

        public static boolean encodesInteger(String s) {
            return INTEGER_PATTERN.matcher(s).matches();
        }

        public static List<Integer> scanRange(int[] range) {
            List<Integer> ret = new ArrayList<>();
            if (range != null && range.length > 0) {
                ret.add(range[0]);
                if (range.length > 1) {
                    for (int i = range[0]; i <= range[1]; i++) {
                        ret.add(i);
                    }
                }
            }
            return ret;
        }

        /**
         * Double comparison done as recommended by IBM.
         * 
         * @param a
         * @param b
         * @return true if "equal"
         */
        public static boolean equal(double a, double b) {
            if (b == 0)
                return Double.compare(a, b) == 0;
            return Math.abs(a / b - 1) < EPSILON;
        }

        public static boolean isInteger(Number n) {
            if (n instanceof Double || n instanceof Float) {
                double d = n.doubleValue();
                return Math.abs(d - Math.round(d)) <= EPSILON;
            }
            return true;
        }

        /**
         * Convert an integer array to an easily parseable string for GET commands and the like.
         * 
         * @param array
         * @return string
         */
        public static String toString(int[] array) {
            String s = "";
            for (int i = 0; i < array.length; i++) {
                s += (i > 0 ? "," : "") + array[i];
            }
            return s;
        }

        /**
         * Convert a string returned by toString(int[]) into the original array.
         * 
         * @param array
         * @return int array
         */
        public static int[] intArrayFromString(String array) {
            return intArrayFromString(array, ",");
        }

        /**
         * 
         * @param array
         * @param splitRegex
         * @return the int array
         */
        public static int[] intArrayFromString(String array, String splitRegex) {

            if (array.startsWith("[")) {
                array = array.substring(1);
            }
            if (array.endsWith("]")) {
                array = array.substring(0, array.length() - 1);
            }
            String[] s = array.split(splitRegex);
            int[] ret = new int[s.length];
            for (int i = 0; i < s.length; i++) {
                ret[i] = Integer.parseInt(s[i].trim());
            }
            return ret;
        }

        public static Object[] objectArrayFromString(String array, String splitRegex, Class<?> cls) {

            if (array.startsWith("[")) {
                array = array.substring(1);
            }
            if (array.endsWith("]")) {
                array = array.substring(0, array.length() - 1);
            }
            String[] s = array.split(splitRegex);
            Object[] ret = new Object[s.length];
            for (int i = 0; i < s.length; i++) {

                if (cls != null && !Object.class.equals(cls)) {
                    ret[i] = Data.parseAsType(s[i], cls);
                } else {
                    if (encodesDouble(s[i].trim())) {
                        ret[i] = Double.parseDouble(s[i].trim());
                    } else if (encodesInteger(s[i].trim())) {
                        ret[i] = Integer.parseInt(s[i].trim());
                    } else {
                        ret[i] = s[i];
                    }
                }
            }
            return ret;
        }

        @SuppressWarnings("unchecked")
        public static <T> List<T> podListFromString(String array, String splitRegex, Class<? extends T> cls) {

            Object[] pods = objectArrayFromString(array, splitRegex, cls);
            List<?> ret = null;

            int cl = 0;
            for (int i = 0; i < pods.length; i++) {
                if (pods[i] instanceof Double) {
                    if (ret == null) {
                        ret = new ArrayList<Double>();
                    }
                    cl = 1;
                    ((List<Double>) ret).add((Double) pods[i]);
                } else if (pods[i] instanceof Integer) {
                    if (ret == null) {
                        ret = new ArrayList<Integer>();
                    }
                    cl = 2;
                    ((List<Integer>) ret).add((Integer) pods[i]);
                } else if (pods[i] instanceof Long) {
                    if (ret == null) {
                        ret = new ArrayList<Long>();
                    }
                    cl = 3;
                    ((List<Long>) ret).add((Long) pods[i]);
                } else if (pods[i] instanceof Float) {
                    if (ret == null) {
                        ret = new ArrayList<Float>();
                    }
                    cl = 4;
                    ((List<Float>) ret).add((Float) pods[i]);
                } else if (pods[i] instanceof Boolean) {
                    if (ret == null) {
                        ret = new ArrayList<Boolean>();
                    }
                    cl = 5;
                    ((List<Boolean>) ret).add((Boolean) pods[i]);
                }
            }

            switch(cl) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return (List<T>) ret;
            }

            throw new KIllegalArgumentException("cannot turn array into PODs: type not handled");
        }

        public static Object podArrayFromString(String array, String splitRegex, Class<?> cls) {
            Object[] pods = objectArrayFromString(array, splitRegex, cls);
            double[] dret = new double[pods.length];
            int[] iret = new int[pods.length];
            long[] lret = new long[pods.length];
            float[] fret = new float[pods.length];
            boolean[] bret = new boolean[pods.length];
            int cl = 0;
            for (int i = 0; i < pods.length; i++) {
                if (pods[i] instanceof Double) {
                    cl = 1;
                    dret[i] = (Double) pods[i];
                } else if (pods[i] instanceof Integer) {
                    cl = 2;
                    iret[i] = (Integer) pods[i];
                } else if (pods[i] instanceof Long) {
                    cl = 3;
                    lret[i] = (Long) pods[i];
                } else if (pods[i] instanceof Float) {
                    cl = 4;
                    fret[i] = (Float) pods[i];
                } else if (pods[i] instanceof Boolean) {
                    cl = 5;
                    bret[i] = (Boolean) pods[i];
                }
            }

            switch(cl) {
            case 1:
                return dret;
            case 2:
                return iret;
            case 3:
                return lret;
            case 4:
                return fret;
            case 5:
                return bret;
            }

            throw new KIllegalArgumentException("cannot turn array into PODs: type not handled");
        }

        public static double[] doubleArrayFromString(String array, String splitRegex) {

            if (array.startsWith("[")) {
                array = array.substring(1);
            }
            if (array.endsWith("]")) {
                array = array.substring(0, array.length() - 1);
            }
            String[] s = array.split(splitRegex);
            double[] ret = new double[s.length];
            for (int i = 0; i < s.length; i++) {
                ret[i] = Double.parseDouble(s[i].trim());
            }
            return ret;
        }

        public static double[] normalize(double[] vals) {
            double[] ret = new double[vals.length];
            double min = Double.NaN, max = Double.NaN;
            for (int i = 0; i < vals.length; i++) {
                if (!Double.isNaN(vals[i])) {
                    if (Double.isNaN(min) || min > vals[i]) {
                        min = vals[i];
                    }
                    if (Double.isNaN(max) || max < vals[i]) {
                        max = vals[i];
                    }
                }
            }

            if (!Double.isNaN(min)) {
                for (int i = 0; i < vals.length; i++) {
                    ret[i] = Double.isNaN(vals[i]) ? Double.NaN : ((vals[i] - min) / (max - min));
                }
            } else {
                ret = vals;
            }

            return ret;
        }

        public static double sumWithoutNan(double[] data) {
            double ret = 0;
            for (double v : data) {
                if (!Double.isNaN(v)) {
                    if (!Double.isNaN(v)) {
                        ret += v;
                    }
                }
            }
            return ret;
        }

        public static double averageWithoutNan(double[] data) {
            int n = 0;
            double ret = 0;
            for (double v : data) {
                if (!Double.isNaN(v)) {
                    if (!Double.isNaN(v)) {
                        ret += v;
                        n++;
                    }
                }
            }
            return ret / (double) n;
        }

        public static Number convertNumber(Number object, Class<?> cls) {
            if (Double.class.isAssignableFrom(cls)) {
                return object.doubleValue();
            }
            if (Integer.class.isAssignableFrom(cls)) {
                return object.intValue();
            }
            if (Long.class.isAssignableFrom(cls)) {
                return object.longValue();
            }
            if (Float.class.isAssignableFrom(cls)) {
                return object.floatValue();
            }
            return object;
        }

        /**
         * Greatest common divisor of two integers
         * 
         * @param a
         * @param b
         * @return the GCD
         */
        public static long gcd(long a, long b) {
            while(b > 0) {
                long temp = b;
                b = a % b;
                a = temp;
            }
            return a;
        }

        /**
         * Greatest common divisor of an array of integers
         * 
         * @param a
         * @param b
         * @return the GCD
         */
        public static long gcd(long[] input) {
            long result = input[0];
            for (int i = 1; i < input.length; i++)
                result = gcd(result, input[i]);
            return result;
        }

        /**
         * Least common multiple of two integers
         * 
         * @param a
         * @param b
         * @return the LCM
         */
        public static long lcm(long a, long b) {
            return a * (b / gcd(a, b));
        }

        /**
         * Least common multiple of an array of integers
         * 
         * @param a
         * @param b
         * @return the LCM
         */
        public static long lcm(long[] input) {
            long result = input[0];
            for (int i = 1; i < input.length; i++)
                result = lcm(result, input[i]);
            return result;
        }

        public static double[] doubleArrayFromCollection(List<Double> vals) {
            double[] ret = new double[vals.size()];
            int i = 0;
            for (Double d : vals) {
                ret[i++] = d;
            }
            return ret;
        }

        public static long[] longArrayFromCollection(Collection<? extends Number> vals) {
            long[] ret = new long[vals.size()];
            int i = 0;
            for (Number d : vals) {
                ret[i++] = d.longValue();
            }
            return ret;
        }

        public static int[] intArrayFromCollection(Collection<? extends Number> vals) {
            int[] ret = new int[vals.size()];
            int i = 0;
            for (Number d : vals) {
                ret[i++] = d.intValue();
            }
            return ret;
        }

        /**
         * Index of largest number in double array
         * 
         * @param a
         * @return index (0 if all NaN or equal)
         */
        public static int indexOfLargest(double[] a) {
            double max = a[0];
            int index = 0;
            for (int i = 0; i < a.length; i++) {
                if (!Double.isNaN(a[i]) && max < a[i]) {
                    max = a[i];
                    index = i;
                }
            }
            return index;
        }

        private static int[] decimal = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        private static String[] roman = new String[]{"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        public static String toRoman(int num) {
            String result = "";
            for (int i = 0; i <= decimal.length; i++) {
                while(num % decimal[i] < num) {
                    result += roman[i];
                    num -= decimal[i];
                }
            }
            return result;
        }

        public static int fromRoman(String str) {
            int result = 0;
            for (int i = 0; i <= decimal.length; i++) {
                while(str.indexOf(roman[i]) == 0) {
                    result += decimal[i];
                    str = str.replace(roman[i], "");
                }
            }
            return result;
        }

        public static boolean isEven(int x) {
            return x % 2 == 0;
        }

        public static boolean isOdd(int x) {
            return x % 2 != 0;
        }

        public static int nextPowerOf2(int n) {
            // decrement n (to handle cases when n itself is a power of 2)
            n--;

            // Set all bits after the last set bit
            n |= n >> 1;
            n |= n >> 2;
            n |= n >> 4;
            n |= n >> 8;
            n |= n >> 16;

            // increment n and return
            return ++n;
        }

        public static long[] asLong(int[] vals) {
            long[] ret = new long[vals.length];
            for (int i = 0; i < vals.length; i++) {
                ret[i] = vals[i];
            }
            return ret;
        }

        /**
         * Create the ordered enumeration of all values from 0 to n (excluded).
         * 
         * @param n
         * @return
         */
        public static LinkedHashSet<Integer> enumerateAsSet(int n) {
            LinkedHashSet<Integer> ret = new LinkedHashSet<>();
            for (int i = 0; i < n; i++) {
                ret.add(i);
            }
            return ret;
        }

        public static Number fromString(String value) {
            if (encodesInteger(value)) {
                return Integer.parseInt(value);
            } else if (encodesLong(value)) {
                return Long.parseLong(value);
            } else if (encodesDouble(value)) {
                return Double.parseDouble(value);
            }
            return Double.NaN;
        }
        /**
         * Binary root of integer.
         * 
         * @param x
         * @return
         */
        public static int log2int(int x) {
            int y, v;
            if (x <= 0) {
                throw new KIllegalArgumentException("" + x + " <= 0");
            }
            v = x;
            y = -1;
            while(v > 0) {
                v >>= 1;
                y++;
            }
            return y;
        }
    }

    public static class Strings {

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

        public static String join(Iterable<?> list, String separator) {
            StringBuffer ret = new StringBuffer(256);
            boolean first = true;
            for (Object o : list) {
                if (!first) {
                    ret.append(separator);
                }
                ret.append(o.toString());
                first = false;
            }
            return ret.toString();
        }

        /**
         * Parse something like xxx(a, b, ... c) into an array [xxx a b c]. Accept xxx alone and
         * send an array of one element. Not particularly smart, so use when the coder is.
         * 
         * @param string
         * @return
         */
        public static String[] parseAsFunctionCall(String string) {

            String id = string;
            String[] parms = null;
            if (string.contains("(") && string.endsWith(")")) {
                int n = string.indexOf('(');
                id = string.substring(0, n);
                String parmstr = string.substring(n + 1, string.length() - 1);
                parms = parmstr.split(",");
            }

            String[] ret = new String[1 + (parms == null ? 0 : parms.length)];
            ret[0] = id.trim();
            if (parms != null) {
                int n = 1;
                for (String p : parms) {
                    ret[n++] = p.trim();
                }
            }

            return ret;
        }

        public static String propertiesToString(Properties prop) {
            StringWriter writer = new StringWriter();
            prop.list(new PrintWriter(writer));
            return writer.getBuffer().toString();
        }

        /**
         * Remove prefix: from prefix:xxx.
         * 
         * @param string
         * @return
         */
        public static String removePrefix(String string) {
            int idx = string.lastIndexOf(':');
            if (idx >= 0) {
                string = string.substring(idx + 1);
            }
            return string;
        }

        public static Object getIgnoreCase(Map<String, ?> map, String key) {
            for (String k : map.keySet()) {
                if (key.compareToIgnoreCase(k) == 0) {
                    return map.get(k);
                }
            }
            return null;
        }

        /** The Constant WHITESPACE. */
        public static final int WHITESPACE = 0x0001;

        /** The Constant NONLETTERS. */
        public static final int NONLETTERS = 0x0002;

        /** The Constant UPPERCASE. */
        public static final int UPPERCASE = 0x0004;

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
         * Gets a CharSequence length or {@code 0} if the CharSequence is {@code null}.
         *
         * @param cs a CharSequence or {@code null}
         * @return CharSequence length or {@code 0} if the CharSequence is {@code null}.
         * @since 2.4
         * @since 3.0 Changed signature from length(String) to length(CharSequence)
         */
        public static int length(final CharSequence cs) {
            return cs == null ? 0 : cs.length();
        }

        /**
         * <p>
         * Capitalizes a String changing the first character to title case as per
         * {@link Character#toTitleCase(int)}. No other characters are changed.
         * </p>
         *
         * <p>
         * For a word based algorithm, see
         * {@link org.apache.commons.lang3.text.WordUtils#capitalize(String)}. A {@code null} input
         * String returns {@code null}.
         * </p>
         *
         * <pre>
         * StringUtils.capitalize(null)  = null
         * StringUtils.capitalize("")    = ""
         * StringUtils.capitalize("cat") = "Cat"
         * StringUtils.capitalize("cAt") = "CAt"
         * StringUtils.capitalize("'cat'") = "'cat'"
         * </pre>
         *
         * @param str the String to capitalize, may be null
         * @return the capitalized String, {@code null} if null String input
         * @see org.apache.commons.lang3.text.WordUtils#capitalize(String)
         * @see #uncapitalize(String)
         * @since 2.0
         */
        public static String capitalize(final String str) {
            final int strLen = length(str);
            if (strLen == 0) {
                return str;
            }

            final int firstCodepoint = str.codePointAt(0);
            final int newCodePoint = Character.toTitleCase(firstCodepoint);
            if (firstCodepoint == newCodePoint) {
                // already capitalized
                return str;
            }

            final int[] newCodePoints = new int[strLen]; // cannot be longer than the char array
            int outOffset = 0;
            newCodePoints[outOffset++] = newCodePoint; // copy the first codepoint
            for (int inOffset = Character.charCount(firstCodepoint); inOffset < strLen;) {
                final int codepoint = str.codePointAt(inOffset);
                newCodePoints[outOffset++] = codepoint; // copy the remaining ones
                inOffset += Character.charCount(codepoint);
            }
            return new String(newCodePoints, 0, outOffset);
        }

        /**
         * Convert from UTF 8.
         *
         * @param s the s
         * @return the string
         */
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

        /**
         * Convert to UTF 8.
         *
         * @param s the s
         * @return the string
         */
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
         * Justify left to passed width with wordwrap.
         *
         * @param text the text
         * @param width the width
         * @return the string
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
         * Remove all leading and trailing whitespace; pack whitespace in between to single space;
         * leave a blank line if there are at least two newlines in the original whitespace. Good
         * for formatting indented and bullshitted text like what you put in XML files into
         * something more suitable for text processing or wiki translation.
         *
         * @param s the s
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

        /**
         * Gets the leading whitespace.
         *
         * @param s the s
         * @return the leading whitespace
         */
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

        /**
         * Gets the trailing whitespace.
         *
         * @param s the s
         * @return the trailing whitespace
         */
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
         * Pack the inside of a string but preserve leading and trailing whitespace. Written simply
         * and expensively.
         *
         * @param s the s
         * @return the string
         */
        public static String packInternally(String s) {
            if (s.trim().isEmpty()) {
                return s;
            }
            return getLeadingWhitespace(s) + pack(s) + getTrailingWhitespace(s);
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
         * Divide up a string into tokens, correctly handling double quotes.
         *
         * @param s the s
         * @return tokens
         */
        static public Collection<String> tokenize(String s) {

            ArrayList<String> ret = new ArrayList<>();
            String regex = "\"([^\"]*)\"|(\\S+)";
            Matcher m = Pattern.compile(regex).matcher(s);
            while(m.find()) {
                if (m.group(1) != null) {
                    ret.add(m.group(1));
                } else {
                    ret.add(m.group(2));
                }
            }
            return ret;
        }

        /**
         * Join a collection of strings into one string using the given char as separator. Can pass
         * any collection; toString() will be used.
         *
         * @param strings the strings
         * @param separator the separator
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
         * @param strings the strings
         * @param separator the separator
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
         * @param objects the objects
         * @param separator the separator
         * @return joined objects
         */
        static public String joinObjects(Object[] objects, char separator) {
            String ret = "";
            for (Object s : objects) {
                ret += (ret.isEmpty() ? "" : ("" + separator)) + (s == null ? "" : s.toString());
            }
            return ret;
        }

        /**
         * Join objects.
         *
         * @param objects the objects
         * @param separator the separator
         * @return the string
         */
        static public String joinObjects(Collection<?> objects, char separator) {
            return joinObjects(objects.toArray(), separator);
        }

        /**
         * Join objects.
         *
         * @param objects the objects
         * @param separator the separator
         * @return the string
         */
        static public String joinObjects(Map<?, ?> objects, char separator) {
            String ret = "";
            for (Object s : objects.keySet()) {
                ret += (ret.isEmpty() ? "" : ("" + separator)) + s + "="
                        + (objects.get(s) == null ? "" : objects.get(s).toString());
            }
            return ret;
        }

        /**
         * Insert char at.
         *
         * @param s the s
         * @param c the c
         * @param pos the pos
         * @return the string
         */
        public static String insertCharAt(String s, char c, int pos) {

            StringBuffer buf = new StringBuffer(s);
            buf.insert(pos, c);
            return buf.toString();
        }

        /**
         * Replace at.
         *
         * @param str the str
         * @param index the index
         * @param replace the replace
         * @return the string
         */
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

        /**
         * Percent.
         *
         * @param d the d
         * @return the string
         */
        public static String percent(double d) {
            return (int) (Math.round(d * 100.0)) + "%";
        }

        /**
         * Contains any.
         *
         * @param nspc the nspc
         * @param flags the flags
         * @return a boolean.
         */
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

        /**
         * Replace whitespace.
         *
         * @param text the text
         * @param replacement the replacement
         * @return the string
         */
        public static String replaceWhitespace(String text, String replacement) {
            return text.replaceAll("\\s+", replacement);
        }

        /**
         * Join.
         *
         * @param objects the objects
         * @param separator the separator
         * @param max the max
         * @return the string
         */
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
         * Takes a multi-line string and strips the smallest amount of whitespace that is common to
         * all lines, basically removing the first level of indentation. One or more lines starting
         * without any leading whitespace will cause this to return the same string.
         * 
         * @param string
         * @return
         */
        public static String stripLeadingWhitespace(String string) {

            string = string.replaceAll("\\t", "   ");

            String lines[] = string.split("\\r?\\n");
            int ofs = 10000;
            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                int n = getLeadingWhitespace(line).length();
                if (n < ofs) {
                    ofs = n;
                }
            }

            if (ofs == 0) {
                return string;
            }

            StringBuffer ret = new StringBuffer(string.length());
            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    ret.append("\n");
                } else {
                    ret.append(line.substring(ofs) + "\n");
                }
            }
            return ret.toString();
        }

        /**
         * Ad-hoc behavior in need for generalization: insert the patch after every newline unless
         * the previous line was empty.
         * 
         * @param stripLeadingWhitespace
         * @param string
         * @return
         */
        public static String insertBeginning(String string, String patch, Function<String, Boolean> filter) {
            String lines[] = string.split("\\r?\\n");
            StringBuffer ret = new StringBuffer(string.length() + (lines.length * patch.length()));
            boolean wasEmpty = true;
            for (String line : lines) {
                if (!wasEmpty && !line.trim().isEmpty() && (filter == null || !filter.apply(line))) {
                    ret.append(patch);
                }
                ret.append(line + "\n");
                wasEmpty = line.trim().isEmpty();
            }
            return ret.toString();
        }

        public static boolean containsWhitespace(String string) {
            for (int i = 0; i < string.length(); i++) {
                if (Character.isWhitespace(string.charAt(i))) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Replace any of the characters contained in the first argument with the string passed as
         * third argument.
         * 
         * @param charsToReplace
         * @param sourceString
         * @param replacement
         * @return
         */
        public static String replaceAny(String charsToReplace, String sourceString, String replacement) {
            StringBuffer sbuf = new StringBuffer(sourceString.length());
            for (int i = 0; i < sourceString.length(); i++) {
                if (charsToReplace.contains("" + sourceString.charAt(i))) {
                    sbuf.append(replacement);
                } else {
                    sbuf.append(sourceString.charAt(i));
                }
            }
            return sbuf.toString();
        }

        public static String getFirstLine(String string) {
            String lines[] = string.split("\\r?\\n");
            return lines.length >= 1 ? lines[0] : "";
        }

        public static String fillUpLeftAligned(String value, String fill, int length) {
            if (value.length() >= length) {
                return value;
            }
            while(value.length() < length) {
                value += fill;
            }
            return value;
        }

        public static String fillUpRightAligned(String value, String fill, int length) {
            if (value.length() >= length) {
                return value;
            }
            while(value.length() < length) {
                value = fill + value;
            }
            return value;
        }

        public static String fillUpCenterAligned(String value, String fill, int length) {
            if (value.length() >= length) {
                return value;
            }
            boolean left = true;
            while(value.length() < length) {
                if (left) {
                    value = fillUpLeftAligned(value, fill, value.length() + 1);
                } else {
                    value = fillUpRightAligned(value, fill, value.length() + 1);
                }
                left = !left;
            }
            return value;
        }

        public static String surroundValueWith(String value, String surrounding) {
            return surrounding + value + surrounding;
        }
    }

    public static class Files {

        /**
         * Return the path leading to file without the file itself.
         *
         * @param lf the lf
         * @return path string
         */
        public static File getPath(String lf) {

            int n = lf.lastIndexOf(File.separator);
            String s = ".";
            if (n > -1) {
                s = lf.substring(0, n);
            }
            return new File(s);
        }

        /**
         * This encoding is not supported by Java, yet it is useful. A UTF-8 file that begins with
         * 0xEFBBBF.
         */
        public static final String UTF_8_Y = "UTF-8Y";

        /**
         * Extract the file extension from a file name.
         *
         * @param s the s
         * @return the file extension
         */
        public static String getFileExtension(String s) {

            String ret = "";

            int sl = s.lastIndexOf(".");
            if (sl > 0)
                ret = s.substring(sl + 1);

            return ret;
        }

        /**
         * Return file path without extension if any.
         *
         * @param s the s
         * @return s with no .xxx at end.
         */
        public static String getFileBasePath(String s) {

            String ret = s;

            int sl = s.lastIndexOf(".");
            if (sl > 0)
                ret = s.substring(0, sl);

            return ret;
        }

        /**
         * Return file path without extension if any.
         *
         * @param s the s
         * @return path for passed file name (directory it's in).
         */
        public static String getFilePath(String s) {

            String ret = s;

            int sl = s.lastIndexOf("/");
            if (sl < 0)
                sl = s.lastIndexOf(File.separator);
            if (sl > 0)
                ret = s.substring(0, sl);

            return ret;
        }

        /**
         * Return file name with no path or extension.
         *
         * @param s the s
         * @return the simple name of the file without extension or path.
         */
        public static String getFileBaseName(String s) {

            String ret = s;

            int sl = ret.lastIndexOf(File.separator);
            if (sl > 0)
                ret = ret.substring(sl + 1);
            sl = ret.lastIndexOf(".");
            if (sl > 0)
                ret = ret.substring(0, sl);

            return ret;
        }

        /**
         * Gets the file extension.
         *
         * @param f the f
         * @return the file extension
         */
        public static String getFileExtension(File f) {
            return getFileExtension(f.toString());
        }

        /**
         * Gets the file base name.
         *
         * @param f the f
         * @return the file base name
         */
        public static String getFileBaseName(File f) {
            return getFileBaseName(f.toString());
        }

        /**
         * Gets the file name.
         *
         * @param f the f
         * @return the file name
         */
        public static String getFileName(File f) {
            return getFileName(f.toString());
        }

        /**
         * Return file name with no path but with extension.
         *
         * @param s the s
         * @return name of file without path, preserving any extension.
         */
        public static String getFileName(String s) {

            String ret = s;

            int sl = ret.lastIndexOf(File.separator);
            if (sl < 0)
                sl = ret.lastIndexOf('/');
            if (sl > 0)
                ret = ret.substring(sl + 1);

            return ret;
        }

        public static boolean isRelativePath(String export) {
            Path path = Path.of(export);
            return !path.isAbsolute();
        }

    }

    public static class Data {

        @SuppressWarnings("unchecked")
        public static <E> E emptyValue(Class<E> cls) {
            if (cls.isAssignableFrom(Collection.class)) {
                return (E) new ArrayList<Object>();
            } else if (cls.isAssignableFrom(Map.class)) {
                return (E) new HashMap<Object, Object>();
            }
            return null;
        }

        public static Object convertValue(Object value, Type type) {

            if (value == null || value instanceof Collection || value.getClass().isArray()) {
                return value;
            }

            if (type == Type.CONCEPT) {
                if (value instanceof KConcept) {
                    return value;
                } else {
                    // value =
                    // Services.INSTANCE.getService(IConceptService.class).getConcept(value.toString());
                }
            } else {

                switch(type) {
                case BOOLEAN:
                    if (!(value instanceof Boolean)) {
                        return "true".equals(value.toString().toLowerCase());
                    }
                case LIST:
                    return value instanceof List ? (List<?>) value : null;
                case MAP:
                    return value instanceof Map ? (Map<?, ?>) value : null;
                case NUMBER:
                    if (value instanceof Number) {
                        return value;
                    } else
                        return Numbers.fromString(value.toString());
                case OBJECT:
                    break;
                case PROCESS:
                    break;
                case TEXT:
                    return value.toString();
                case VALUE:
                    return value;
                default:
                    break;

                }
            }

            return value;
        }

        public static Object[] boxArray(double[] a) {
            Object[] ret = new Object[a.length];
            int i = 0;
            for (double aa : a) {
                ret[i++] = aa;
            }
            return ret;
        }

        public static Object[] boxArray(long[] a) {
            Object[] ret = new Object[a.length];
            int i = 0;
            for (long aa : a) {
                ret[i++] = aa;
            }
            return ret;
        }

        public static boolean isFloatingPoint(Number number) {
            return number instanceof Double || number instanceof Float;
        }

        /**
         * Choose the first non-null object among the passed ones.
         * 
         * @param <T>
         * @param objects
         * @return
         */
        @SuppressWarnings("unchecked")
        public static <T> T chooseNotNull(T... objects) {
            for (T o : objects) {
                if (o != null) {
                    return o;
                }
            }
            return null;
        }

        /**
         * Flatten any collections in the source collection and accumulate values after conversion
         * to T in the destination collection.
         * 
         * @param arguments
         * @param indices
         * @param class1
         */
        public static <T> void collectValues(Collection<?> source, Collection<? extends T> destination, Class<T> class1) {
            for (Object o : source) {
                if (o instanceof Collection) {
                    collectValues((Collection<?>) o, destination, class1);
                } else {
                    destination.add(asType(o, class1));
                }
            }
        }

        /**
         * Call the valueOf method on an enum and return the value or null if there is no such
         * value, without errors.
         * 
         * @param <T>
         * @param name
         * @param enumClass
         * @return
         */
        @SuppressWarnings("unchecked")
        public static <T extends Enum<?>> T valueOf(String name, Class<T> enumClass) {
            try {
                Method method = enumClass.getDeclaredMethod("valueOf", String.class);
                Object ret = method.invoke(enumClass, name);
                return (T) ret;
            } catch (Throwable e) {
            }
            return null;
        }

        /**
         * Return file name with no path or extension
         * 
         * @param s
         * @return the simple name of the file without extension or path.
         */
        public static String getFileBaseName(File s) {

            String ret = s.toString();

            int sl = ret.lastIndexOf(File.separator);
            if (sl > 0)
                ret = ret.substring(sl + 1);
            sl = ret.lastIndexOf(".");
            if (sl > 0)
                ret = ret.substring(0, sl);

            return ret;
        }

        /**
         * Return URL base name with no path or extension. Just like getFileBaseName but uses /
         * instead of system separator.
         * 
         * @param s
         * @return extracted name from URL
         */
        public static String getURLBaseName(String s) {

            /* just in case */
            String ret = s.replace('\\', '/');

            if (ret.contains("?")) {
                ret = ret.substring(0, ret.indexOf('?'));
            }

            if (ret.contains("#")) {
                ret = ret.substring(0, ret.indexOf('#'));
            }

            int sl = ret.lastIndexOf(".");
            if (sl > 0)
                ret = ret.substring(0, sl);
            sl = ret.lastIndexOf("/");
            if (sl >= 0)
                ret = ret.substring(sl + 1);

            return ret;
        }

        public static long[] newArray(long value, int size) {
            long[] ret = new long[size];
            Arrays.fill(ret, value);
            return ret;
        }

        public static long[] toLongArray(List<Long> list) {
            long[] ret = new long[list.size()];
            for (int i = 0; i < list.size(); i++) {
                ret[i] = list.get(i) == null ? 0L : list.get(i);
            }
            return ret;
        }

        public static int[] getIntArrayFromLongArray(long[] data) {

            if (data == null) {
                return null;
            }
            int[] ints = new int[data.length];

            for (int i = 0; i < data.length; i++) {
                ints[i] = (int) data[i];
            }
            return ints;
        }

        public static long[] getLongArrayFromIntArray(int[] data) {

            if (data == null) {
                return null;
            }
            long[] longs = new long[data.length];

            for (int i = 0; i < data.length; i++) {
                longs[i] = data[i];
            }
            return longs;
        }

        public static long[] getLongArrayFromFloatArray(float[] data) {

            if (data == null) {
                return null;
            }
            long[] longs = new long[data.length];

            for (int i = 0; i < data.length; i++) {
                longs[i] = (long) data[i];
            }
            return longs;
        }

        public static long[] getLongArrayFromNumberList(List<?> data) {

            if (data == null) {
                return null;
            }
            long[] longs = new long[data.size()];

            int i = 0;
            for (Object number : data) {
                if (number == null) {
                    longs[i++] = 0;
                } else {
                    longs[i++] = ((Number) number).longValue();
                }
            }
            return longs;
        }

        public static boolean isPOD(Object value) {
            if (value instanceof Class<?>) {
                return Number.class.isAssignableFrom((Class<?>) value) || String.class.isAssignableFrom((Class<?>) value)
                        || Boolean.class.isAssignableFrom((Class<?>) value);
            }
            return value instanceof Number || value instanceof String || value instanceof Boolean;
        }

        public static Class<?> getPODClass(Object value) {
            if (value instanceof Number) {
                return Double.class;
            }
            if (value instanceof Boolean) {
                return Boolean.class;
            }
            if (value instanceof KConcept) {
                return KConcept.class;
            }
            return String.class;
        }

        /**
         * Return the closest POD that the value can be parsed into. For now only handle int and
         * double. May add k.IM - like maps, lists, ranges.
         * 
         * @param value
         * @return
         */
        public static Object asPOD(String value) {

            try {
                return Integer.parseInt(value);
            } catch (Throwable e) {
            }
            try {
                return Long.parseLong(value);
            } catch (Throwable e) {
            }
            try {
                return Double.parseDouble(value);
            } catch (Throwable e) {
            }

            if (value.toLowerCase().equals("true") || value.toLowerCase().equals("false")) {
                return value.toLowerCase().equals("true");
            }

            return value;
        }

        public static boolean validateAs(Object pod, KArtifact.Type type) {
            if (pod == null) {
                return false;
            }
            KArtifact.Type tp = getArtifactType(pod.getClass());
            if (type == tp) {
                return true;
            }
            if (tp == Type.TEXT) {
                Object converted = asPOD(pod.toString());
                if (converted != null) {
                    return getArtifactType(converted.getClass()) == type;
                }
            }
            return false;
        }

        /**
         * Basic conversions to match a type, including null -> NaN when what's wanted is a double
         * or float. Any value will be put into a list if a list is asked for.
         * 
         * @param ret
         * @param cls
         * @return the object as a cls or null
         */
        @SuppressWarnings("unchecked")
        public static <T> T asType(Object ret, Class<?> cls) {

            if (cls.equals(Object.class)) {
                return (T) ret;
            }

            if (cls.isArray() && ret.getClass().isArray()) {

                int length = Array.getLength(ret);

                if (cls.equals(int[].class)) {
                    int[] a = new int[length];
                    for (int i = 0; i < length; i++) {
                        a[i] = asType(Array.get(ret, i), Integer.class);
                    }
                    return (T) a;
                } else if (cls.equals(long[].class)) {
                    long[] a = new long[length];
                    for (int i = 0; i < length; i++) {
                        a[i] = asType(Array.get(ret, i), Long.class);
                    }
                    return (T) a;

                } else if (cls.equals(float[].class)) {
                    float[] a = new float[length];
                    for (int i = 0; i < length; i++) {
                        a[i] = asType(Array.get(ret, i), Float.class);
                    }
                    return (T) a;

                } else if (cls.equals(double[].class)) {
                    double[] a = new double[length];
                    for (int i = 0; i < length; i++) {
                        a[i] = asType(Array.get(ret, i), Double.class);
                    }
                    return (T) a;

                } else if (cls.equals(String[].class)) {
                    String[] a = new String[length];
                    for (int i = 0; i < length; i++) {
                        a[i] = asType(Array.get(ret, i), String.class);
                    }
                    return (T) a;

                }
            }

            if (cls.equals(List.class)) {
                List<Object> list = new ArrayList<>();
                if (ret instanceof Collection) {
                    list.addAll((Collection<?>) ret);
                } else {
                    list.add(ret);
                }
                return (T) list;
            }

            if (ret == null) {
                if (cls.equals(Double.class)) {
                    return (T) Double.valueOf(Double.NaN);
                }
                if (cls.equals(Float.class)) {
                    return (T) Float.valueOf(Float.NaN);
                }

                return null;
            }

            if (cls.isAssignableFrom(ret.getClass())) {
                return (T) ret;
            }

            if (cls.equals(String.class)) {
                return (T) ret.toString();
            }

            if (ret instanceof Number) {
                if (Number.class.isAssignableFrom(cls)) {
                    if (cls.equals(Double.class)) {
                        return (T) Double.valueOf(((Number) ret).doubleValue());
                    }
                    if (cls.equals(Long.class)) {
                        return (T) Long.valueOf(((Number) ret).longValue());
                    }
                    if (cls.equals(Integer.class)) {
                        return (T) Integer.valueOf(((Number) ret).intValue());
                    }
                    if (cls.equals(Short.class)) {
                        return (T) Short.valueOf(((Number) ret).shortValue());
                    }
                    if (cls.equals(Float.class)) {
                        return (T) Float.valueOf(((Number) ret).floatValue());
                    }
                } else if (Boolean.class.isAssignableFrom(cls)) {
                    if (cls.equals(Boolean.class)) {
                        return (T) (Numbers.equal(((Number) ret).doubleValue(), 0) ? Boolean.FALSE : Boolean.TRUE);
                    }
                }
            } else if (ret instanceof Boolean) {

                if (cls.equals(Double.class)) {
                    return (T) Double.valueOf(((Boolean) ret) ? 1 : 0);
                }
                if (cls.equals(Long.class)) {
                    return (T) Long.valueOf(((Boolean) ret) ? 1 : 0);
                }
                if (cls.equals(Short.class)) {
                    return (T) Short.valueOf(((Boolean) ret) ? (short) 1 : 0);
                }
                if (cls.equals(Integer.class)) {
                    return (T) Integer.valueOf(((Boolean) ret) ? 1 : 0);
                }
                if (cls.equals(Float.class)) {
                    return (T) Float.valueOf(((Boolean) ret) ? 1 : 0);
                }

            } else if (ret instanceof String) {
                if (cls.equals(Double.class)) {
                    return (T) Double.valueOf((String) ret);
                }
                if (cls.equals(Long.class)) {
                    return (T) Long.valueOf((String) ret);
                }
                if (cls.equals(Short.class)) {
                    return (T) Short.valueOf((String) ret);
                }
                if (cls.equals(Integer.class)) {
                    return (T) Integer.valueOf((String) ret);
                }
                if (cls.equals(Float.class)) {
                    return (T) Float.valueOf((String) ret);
                }
                if (cls.equals(Boolean.class)) {
                    return (T) Boolean.valueOf((String) ret);
                }
                if (cls.equals(KConcept.class)) {
                    // IConceptService service =
                    // Services.INSTANCE.getService(IConceptService.class);
                    // if (service != null) {
                    // return (T)service.declare(service.declare(ret.toString()));
                    // }
                }
            }

            throw new KIllegalArgumentException("cannot interpret value " + ret + " as a " + cls.getCanonicalName());
        }

        @SuppressWarnings("unchecked")
        public static <T> T parseAsType(String ret, Class<?> cls) {

            if (cls.equals(Object.class) || cls.equals(String.class)) {
                return (T) ret;
            }

            if (Number.class.isAssignableFrom(cls)) {
                if (cls.equals(Double.class)) {
                    return (T) Double.valueOf(Double.parseDouble(ret));
                }
                if (cls.equals(Long.class)) {
                    if (ret.contains(".") || ret.contains("E")) {
                        // fix legacy issues
                        ret = "" + Double.valueOf(Double.parseDouble(ret)).longValue();
                    }
                    return (T) Long.valueOf(Long.parseLong(ret));
                }
                if (cls.equals(Integer.class)) {
                    if (ret.contains(".") || ret.contains("E")) {
                        // fix legacy issues
                        ret = "" + Double.valueOf(Double.parseDouble(ret)).intValue();
                    }
                    return (T) Integer.valueOf(Integer.parseInt(ret));
                }
                if (cls.equals(Short.class)) {
                    if (ret.contains(".") || ret.contains("E")) {
                        // fix legacy issues
                        ret = "" + Double.valueOf(Double.parseDouble(ret)).shortValue();
                    }
                    return (T) Short.valueOf(Short.parseShort(ret));
                }
                if (cls.equals(Float.class)) {
                    return (T) Float.valueOf(Float.parseFloat(ret));
                }
            } else if (Boolean.class.isAssignableFrom(cls)) {
                if (cls.equals(Boolean.class)) {
                    return (T) Boolean.valueOf(Boolean.parseBoolean(ret));
                }
            }

            throw new KIllegalArgumentException("cannot interpret value " + ret + " as a " + cls.getCanonicalName());
        }

        /**
         * Return the most logical non-null value for the passed type, assuming there is one.
         * 
         * @param ret
         * @param cls
         * @return the object as a cls or null
         */
        @SuppressWarnings("unchecked")
        public static <T> T notNull(Class<?> cls) {

            if (cls.isArray()) {

                if (cls.equals(int[].class)) {
                    return (T) new int[]{};
                } else if (cls.equals(long[].class)) {
                    return (T) new long[]{};
                } else if (cls.equals(float[].class)) {
                    return (T) new float[]{};
                } else if (cls.equals(double[].class)) {
                    return (T) new double[]{};
                } else if (cls.equals(String[].class)) {
                    return (T) new String[]{};
                }
            }

            if (cls.equals(List.class)) {
                List<Object> list = new ArrayList<>();
                return (T) list;
            } else if (cls.equals(Integer.class)) {
                return (T) Integer.valueOf(0);
            } else if (cls.equals(Long.class)) {
                return (T) Long.valueOf(0);
            } else if (cls.equals(Double.class)) {
                return (T) Double.valueOf(Double.NaN);
            } else if (cls.equals(Float.class)) {
                return (T) Float.valueOf(Float.NaN);
            } else if (cls.equals(String.class)) {
                return (T) "";
            } else if (cls.equals(Boolean.class)) {
                return (T) Boolean.FALSE;
            }

            // give up
            return null;
        }

        public static Type getArtifactType(Class<?> cls) {

            Type ret = cls == null ? Type.VOID : Type.VALUE;
            if (String.class.isAssignableFrom(cls)) {
                ret = Type.TEXT;
            } else if (Number.class.isAssignableFrom(cls)) {
                ret = Type.NUMBER;
            } else if (Boolean.class.isAssignableFrom(cls)) {
                ret = Type.BOOLEAN;
            } else if (KConcept.class.isAssignableFrom(cls)) {
                ret = Type.CONCEPT;
            }
            return ret;
        }

        public static Class<?> getClassForType(KArtifact.Type type) {
            switch(type) {
            case BOOLEAN:
                return Boolean.class;
            case NUMBER:
                return Double.class;
            case TEXT:
                return String.class;
            case CONCEPT:
                return KConcept.class;
            default:
                break;
            }
            throw new KIllegalArgumentException("type " + type + " has no Java class equivalent");
        }

    }

    public static class Exceptions {

    }

    public static class URLs {

        /**
         * Pattern to validate a RFC 2141-compliant URN.
         */
        public final static Pattern URN_PATTERN = Pattern
                .compile("^urn:[a-z0-9][a-z0-9-]{0,31}:([a-z0-9()+,\\-.:=@;$_!*']|%[0-9a-f]{2})+$", Pattern.CASE_INSENSITIVE);

        /**
         * Check if passed URN string can be really called a URN according to RFC 2141 conventions.
         *
         * @param urn the urn
         * @return true if compliant
         */
        public static boolean isCompliant(String urn) {
            return URN_PATTERN.matcher(urn).matches();
        }

        /**
         * Ping the url by requesting the header and inspecting the return code.
         *
         * @param url the url
         * @return true if ping succeeds
         */
        public static boolean ping(String url) {

            // < 100 is undertermined.
            // 1nn is informal (shouldn't happen on a GET/HEAD)
            // 2nn is success
            // 3nn is redirect
            // 4nn is client error
            // 5nn is server error

            HttpURLConnection connection = null;
            boolean ret = false;
            try {
                connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestMethod("HEAD");
                int responseCode = connection.getResponseCode();
                if (responseCode > 100 && responseCode < 400) {
                    ret = true;
                }
            } catch (Exception e) {
            }
            return ret;
        }

        /**
         * Return true if the passed host (not URL) responds on port 80.
         *
         * @param url the url
         * @return true if host responds
         */
        public static boolean pingHost(String url) {
            Socket socket = null;
            boolean reachable = false;
            try {
                socket = new Socket(url, 80);
                reachable = true;
            } catch (Exception e) {
            } finally {
                if (socket != null)
                    try {
                        socket.close();
                    } catch (IOException e) {
                    }
            }
            return reachable;
        }

        // /**
        // * Look for thinklab.resource.path in properties, if found scan the path to
        // resolve
        // * the passed name as a file url. If the url is already resolved, just return
        // it. If
        // * the path contains a http-based URL prefix just use that without checking.
        // *
        // * @param url
        // * @param properties
        // * @return a resolved url or the original one if not resolved.
        // */
        // public static String resolveUrl(String url, Properties properties) {
        //
        // String ret = url;
        //
        // if (ret.contains(":/"))
        // return ret;
        //
        // String prop = ".";
        //
        // for (String path : prop.split(";")) {
        //
        // if (path.startsWith("http") && path.contains("/")) {
        // ret = path + url;
        // break;
        // }
        //
        // File pth = new File(path + File.separator + url);
        //
        // if (pth.exists()) {
        // try {
        // ret = pth.toURI().toURL().toString();
        // break;
        // } catch (MalformedURLException e) {
        // }
        // }
        // }
        //
        // return ret;
        // }

        /**
         * Copy the given URL to the given local file, return number of bytes copied.
         *
         * @param url the URL
         * @param file the File
         * @return the number of bytes copied.
         * @throws KlabIOException the klab IO exception
         */
        public static long copy(URL url, File file) throws KIOException {
            long count = 0;
            int oneChar = 0;

            try {
                InputStream is = url.openStream();
                FileOutputStream fos = new FileOutputStream(file);

                while((oneChar = is.read()) != -1) {
                    fos.write(oneChar);
                    count++;
                }

                is.close();
                fos.close();
            } catch (Exception e) {
                throw new KIOException(e.getMessage());
            }

            return count;
        }

        /**
         * The listener interface for receiving copy events. The class that is interested in
         * processing a copy event implements this interface, and the object created with that class
         * is registered with a component using the component's <code>addCopyListener</code> method.
         * When the copy event occurs, that object's appropriate method is invoked.
         */
        public interface CopyListener {
            void onProgress(int percent);
        }

        /**
         * Copy.
         *
         * @param url the url
         * @param file the file
         * @param listener the listener
         * @param size pass an approx size in case the server does not pass the length
         * @return nothing
         * @throws KlabIOException the klab IO exception
         */
        public static long copy(URL url, File file, CopyListener listener, long size, int timeoutMs) throws KIOException {

            long count = 0;

            try {

                URLConnection connection = url.openConnection();

                /*
                 * set configured timeout
                 */
                if (timeoutMs > 0) {
                    connection.setConnectTimeout(timeoutMs);
                    connection.setReadTimeout(timeoutMs);
                }

                long stated = connection.getContentLengthLong();
                if (stated > 0) {
                    size = stated;
                }

                InputStream is = url.openStream();
                FileOutputStream fos = new FileOutputStream(file);

                byte[] buf = new byte[1024];
                int len;
                int progress = 0;
                while((len = is.read(buf)) > 0) {
                    fos.write(buf, 0, len);
                    count += len;
                    progress = (int) (((double) count / (double) size) * 100.0);
                    listener.onProgress(progress);
                }

                if (progress < 100) {
                    listener.onProgress(100);
                }

                is.close();
                fos.close();

            } catch (Exception e) {
                throw new KIOException(e.getMessage());
            }

            return count;
        }

        /**
         * Copy channeled.
         *
         * @param url the url
         * @param file the file
         * @throws KlabIOException the klab IO exception
         */
        public static void copyChanneled(URL url, File file) throws KIOException {

            InputStream is = null;
            FileOutputStream fos = null;

            try {
                is = url.openStream();
                fos = new FileOutputStream(file);
                ReadableByteChannel rbc = Channels.newChannel(is);
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            } catch (Exception e) {
                throw new KIOException(e.getMessage());
            } finally {
                if (is != null)
                    try {
                        is.close();
                    } catch (IOException e) {
                    }
                if (fos != null)
                    try {
                        fos.close();
                    } catch (IOException e) {
                    }
            }
        }

        /**
         * Copy the given File to the given local file, return number of bytes copied.
         *
         * @param url the URL
         * @param file the File
         * @return the number of bytes copied.
         * @throws KlabIOException the klab IO exception
         */
        public static long copy(File url, File file) throws KIOException {
            long count = 0;

            try {
                InputStream is = new FileInputStream(url);
                FileOutputStream fos = new FileOutputStream(file);

                int oneChar;
                while((oneChar = is.read()) != -1) {
                    fos.write(oneChar);
                    count++;
                }

                is.close();
                fos.close();
            } catch (Exception e) {
                throw new KIOException(e.getMessage());
            }

            return count;
        }

        /**
         * Copy buffered.
         *
         * @param src the src
         * @param dst the dst
         * @throws KlabIOException the klab IO exception
         */
        public static void copyBuffered(File src, File dst) throws KIOException {

            try {
                InputStream in = new FileInputStream(src);
                OutputStream out = new FileOutputStream(dst);

                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
            } catch (Exception e) {
                throw new KIOException(e.getMessage());
            }

        }

        /**
         * Gets the file for URL.
         *
         * @param url the url
         * @return the file for URL
         * @throws KlabIOException the klab IO exception
         */
        public static File getFileForURL(URL url) throws KIOException {
            if (url.toString().startsWith("file:")) {
                return new File(Escape.unescapeurl(url.getFile()));
            } else {
                File temp;
                try {
                    temp = File.createTempFile("url", "url");
                } catch (IOException e) {
                    throw new KIOException(e);
                }
                copy(url, temp);
                return temp;
            }
        }

        /**
         * Return the size of the file pointed to by the URL, or -1 if it can't be assessed.
         * 
         * @param url
         * @return
         */
        public static long getFileSize(URL url) {
            URLConnection conn = null;
            try {
                conn = url.openConnection();
                if (conn instanceof HttpURLConnection) {
                    ((HttpURLConnection) conn).setRequestMethod("HEAD");
                }
                conn.getInputStream();
                return conn.getContentLengthLong();
            } catch (IOException e) {
                return -1;
            } finally {
                if (conn instanceof HttpURLConnection) {
                    ((HttpURLConnection) conn).disconnect();
                }
            }
        }

        /**
         * Return URL base name with no path or extension. Just like getFileBaseName but uses /
         * instead of system separator.
         *
         * @param s the s
         * @return extracted name from URL
         */
        public static String getURLBaseName(String s) {

            /* just in case */
            String ret = s.replace('\\', '/');

            if (ret.contains("?")) {
                ret = ret.substring(0, ret.indexOf('?'));
            }

            if (ret.contains("#")) {
                ret = ret.substring(0, ret.indexOf('#'));
            }

            int sl = ret.lastIndexOf(".");
            if (sl > 0)
                ret = ret.substring(0, sl);
            sl = ret.lastIndexOf("/");
            if (sl >= 0)
                ret = ret.substring(sl + 1);

            return ret;
        }

        /**
         * Gets the name from URL.
         *
         * @param uu the uu
         * @return the name from URL
         */
        public static String getNameFromURL(String uu) {
            int sl = uu.lastIndexOf('/');
            String name = sl == -1 ? uu : uu.substring(sl + 1);
            int dt = name.lastIndexOf('.');
            return dt == -1 ? name : name.substring(0, dt);
        }

        /*
         * This code is inspired by the <code>sanitize</code> method from <code>com.google.common.html.types.SafeUrls</code>.
         * The original library is not up to date and contains several vulnerabilities.
         * @see <a href="https://www.javadoc.io/doc/com.google.common.html.types/types/1.0.4/com/google/common/html/types/SafeUrls.html#sanitize-java.lang.String-java.util.Set-">Original documentation</a>
         * Comments are taken from the original code 
         */
        private static final Set<String> DEFAULT_SAFE_STARTS = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("http", "https", "mailto", "ftp")));
        private static final String SAFE_URL = "about:invalid";
        
        /**
         * Sanitizes the given {@code url}, validating that the input string matches a pattern of commonly
         * used safe URLs. If {@code url} fails validation, this method returns
         * {@code about:invalid}
         * 
         * <p>Specifically, {@code url} may be a URL with any of the default safe schemes (http, https,
         * ftp, mailto), or a relative URL (i.e., a URL without a scheme; specifically, a scheme-relative,
         * absolute-path-relative, or path-relative URL).
         * 
         * @see http://url.spec.whatwg.org/#concept-relative-url
         * 
         * <p>A sanitized URL is a string that carries the security type contract that its value
         * will not cause untrusted script execution when evaluated as a hyperlink URL in a browser.
         *
         * <p>Values of this type are guaranteed to be safe to use in URL/hyperlink contexts, such as
         * assignment to URL-valued DOM properties, in the sense that the use will not result in a
         * Cross-Site-Scripting vulnerability. Similarly, a safe url can be interpolated into the URL context
         * of an HTML template (e.g., inside a href attribute). However, appropriate HTML-escaping must
         * still be applied.
         *
         * <p>Note that this type's contract does not imply any guarantees regarding the resource the URL
         * refers to. In particular, the sanitized URLs are <b>not</b> safe to use in a context where the referred-to
         * resource is interpreted as trusted code, e.g., as the src of a script tag.
         * 
         */
        public static String sanitize(String url) {
            String lowerCased = url.toLowerCase();

            // If some Unicode character lower cases to something that ends up matching these ASCII ones, it's harmless.
            for (String protocol : DEFAULT_SAFE_STARTS) {
              if (lowerCased.startsWith(protocol + ":")) {
                return url;
              }
            }
            
            for (int i = 0; i < url.length(); i++) {
              switch (url.charAt(i)) {
                case '/':
                case '?':
                case '#':
                  // After this the string can end or contain anything else, it won't be interpreted
                  // as the scheme.
                  return url;
                case ':':
                  // This character is not allowed before seeing one of the above characters.
                  return SAFE_URL;
                default:
                  // Other characters ok.
                  continue;
              }
            }
            return url;
        }
    }

    public static class Collections {

        public static <T> List<T> arrayToList(T[] objects) {
            List<T> ret = new ArrayList<>();
            if (objects != null) {
                for (T obj : objects) {
                    ret.add(obj);
                }
            }
            return ret;
        }

        @SafeVarargs
        public static <T> List<T> join(Collection<T>... resources) {
            List<T> ret = new ArrayList<>();
            for (Collection<T> list : resources) {
                ret.addAll(list);
            }
            return ret;
        }

        @SafeVarargs
        public static <T> List<T> join(Iterable<T>... resources) {
            List<T> ret = new ArrayList<>();
            for (Iterable<T> list : resources) {
                for (T o : list) {
                    ret.add(o);
                }
            }
            return ret;
        }

        /**
         * Return a single collection containing all the artifacts in each artifact passed.
         * 
         * @param artifacts
         * @return
         */
        public static Collection<KArtifact> joinArtifacts(Collection<KArtifact> artifacts) {
            List<KArtifact> ret = new ArrayList<>();
            for (KArtifact artifact : artifacts) {
                for (KArtifact a : artifact) {
                    ret.add(a);
                }
            }
            return ret;
        }

        public static Collection<KObservation> joinObservations(Collection<KObservation> artifacts) {
            List<KObservation> ret = new ArrayList<>();
            for (KObservation artifact : artifacts) {
                for (KArtifact a : artifact) {
                    ret.add((KObservation) a);
                }
            }
            return ret;
        }

        /**
         * Pack the arguments into a collection; if any argument is a collection, add its elements
         * but do not unpack below the first level.
         * 
         * @param objects
         * @return
         */
        public static Collection<Object> shallowCollection(Object... objects) {
            List<Object> ret = new ArrayList<>();
            for (Object o : objects) {
                if (o instanceof Collection) {
                    ret.addAll((Collection<?>) o);
                } else {
                    ret.add(o);
                }
            }
            return ret;
        }

        /**
         * Pack the arguments into a collection; if any argument is a collection, unpack its
         * elements recursively so that no collections remain.
         * 
         * @param objects
         * @return
         */
        public static Collection<Object> flatCollection(Object... objects) {
            List<Object> ret = new ArrayList<>();
            addToCollection(ret, objects);
            return ret;
        }

        private static void addToCollection(List<Object> ret, Object... objects) {
            for (Object o : objects) {
                if (o instanceof Collection) {
                    for (Object oo : ((Collection<?>) o)) {
                        addToCollection(ret, oo);
                    }
                } else if (o != null && o.getClass().isArray()) {
                    for (int i = 0; i < Array.getLength(o); i++) {
                        addToCollection(ret, Array.get(o, i));
                    }
                } else {
                    ret.add(o);
                }
            }
        }
    }

    public static class Colors {

        static Map<String, int[]> rgb = new HashMap<>();

        static {
            rgb.put("aqua", new int[]{0, 255, 255});
            rgb.put("black", new int[]{0, 0, 0});
            rgb.put("blue", new int[]{0, 0, 255});
            rgb.put("fuchsia", new int[]{255, 0, 255});
            rgb.put("gray", new int[]{128, 128, 128});
            rgb.put("green", new int[]{0, 128, 0});
            rgb.put("lime", new int[]{0, 255, 0});
            rgb.put("maroon", new int[]{128, 0, 0});
            rgb.put("navy", new int[]{0, 0, 128});
            rgb.put("olive", new int[]{128, 128, 0});
            rgb.put("orange", new int[]{255, 165, 0});
            rgb.put("purple", new int[]{128, 0, 128});
            rgb.put("red", new int[]{255, 0, 0});
            rgb.put("silver", new int[]{192, 192, 192});
            rgb.put("teal", new int[]{0, 128, 128});
            rgb.put("white", new int[]{255, 255, 255});
            rgb.put("yellow", new int[]{255, 255, 0});
            rgb.put("aliceblue", new int[]{240, 248, 255});
            rgb.put("antiquewhite", new int[]{250, 235, 215});
            rgb.put("aqua", new int[]{0, 255, 255});
            rgb.put("aquamarine", new int[]{127, 255, 212});
            rgb.put("azure", new int[]{1240, 255, 255});
            rgb.put("beige", new int[]{245, 245, 220});
            rgb.put("bisque", new int[]{255, 228, 196});
            rgb.put("black", new int[]{0, 0, 0});
            rgb.put("blanchedalmond", new int[]{255, 235, 205});
            rgb.put("blue", new int[]{0, 0, 255});
            rgb.put("blueviolet", new int[]{138, 43, 226});
            rgb.put("brown", new int[]{165, 42, 42});
            rgb.put("burlywood", new int[]{222, 184, 135});
            rgb.put("cadetblue", new int[]{95, 158, 160});
            rgb.put("chartreuse", new int[]{95, 158, 160});
            rgb.put("chocolate", new int[]{210, 105, 30});
            rgb.put("coral", new int[]{255, 127, 80});
            rgb.put("cornflowerblue", new int[]{100, 149, 237});
            rgb.put("cornsilk", new int[]{255, 248, 220});
            rgb.put("crimson", new int[]{220, 20, 60});
            rgb.put("cyan", new int[]{0, 255, 255});
            rgb.put("darkblue", new int[]{0, 0, 139});
            rgb.put("darkcyan", new int[]{0, 139, 139});
            rgb.put("darkgoldenrod", new int[]{184, 134, 11});
            rgb.put("darkgray", new int[]{169, 169, 169});
            rgb.put("darkgreen", new int[]{0, 100, 0});
            rgb.put("darkkhaki", new int[]{189, 183, 107});
            rgb.put("darkmagenta", new int[]{139, 0, 139});
            rgb.put("darkolivegreen", new int[]{85, 107, 47});
            rgb.put("darkorange", new int[]{255, 140, 0});
            rgb.put("darkorchid", new int[]{153, 50, 204});
            rgb.put("darkred", new int[]{139, 0, 0});
            rgb.put("darksalmon", new int[]{233, 150, 122});
            rgb.put("darkseagreen", new int[]{143, 188, 143});
            rgb.put("darkslateblue", new int[]{72, 61, 139});
            rgb.put("darkslategray", new int[]{47, 79, 79});
            rgb.put("darkturquoise", new int[]{0, 206, 209});
            rgb.put("darkviolet", new int[]{148, 0, 211});
            rgb.put("deeppink", new int[]{255, 20, 147});
            rgb.put("deepskyblue", new int[]{0, 191, 255});
            rgb.put("dimgray", new int[]{0, 191, 255});
            rgb.put("dodgerblue", new int[]{30, 144, 255});
            rgb.put("firebrick", new int[]{178, 34, 34});
            rgb.put("floralwhite", new int[]{255, 250, 240});
            rgb.put("forestgreen", new int[]{34, 139, 34});
            rgb.put("fuchsia", new int[]{255, 0, 255});
            rgb.put("gainsboro", new int[]{220, 220, 220});
            rgb.put("ghostwhite", new int[]{248, 248, 255});
            rgb.put("gold", new int[]{255, 215, 0});
            rgb.put("goldenrod", new int[]{218, 165, 32});
            rgb.put("gray", new int[]{127, 127, 127});
            rgb.put("green", new int[]{0, 128, 0});
            rgb.put("greenyellow", new int[]{173, 255, 47});
            rgb.put("honeydew", new int[]{240, 255, 240});
            rgb.put("hotpink", new int[]{255, 105, 180});
            rgb.put("indianred", new int[]{205, 92, 92});
            rgb.put("indigo", new int[]{75, 0, 130});
            rgb.put("ivory", new int[]{255, 255, 240});
            rgb.put("khaki", new int[]{240, 230, 140});
            rgb.put("lavender", new int[]{230, 230, 250});
            rgb.put("lavenderblush", new int[]{255, 240, 245});
            rgb.put("lawngreen", new int[]{124, 252, 0});
            rgb.put("lemonchiffon", new int[]{255, 250, 205});
            rgb.put("lightblue", new int[]{173, 216, 230});
            rgb.put("lightcoral", new int[]{240, 128, 128});
            rgb.put("lightcyan", new int[]{224, 255, 255});
            rgb.put("lightgoldenrodyellow", new int[]{250, 250, 210});
            rgb.put("lightgreen", new int[]{144, 238, 144});
            rgb.put("lightgrey", new int[]{211, 211, 211});
            rgb.put("lightpink", new int[]{255, 182, 193});
            rgb.put("lightsalmon", new int[]{255, 160, 122});
            rgb.put("lightseagreen", new int[]{32, 178, 170});
            rgb.put("lightskyblue", new int[]{135, 206, 250});
            rgb.put("lightslategray", new int[]{119, 136, 153});
            rgb.put("lightsteelblue", new int[]{176, 196, 222});
            rgb.put("lightyellow", new int[]{255, 255, 224});
            rgb.put("lime", new int[]{0, 255, 0});
            rgb.put("limegreen", new int[]{50, 205, 50});
            rgb.put("linen", new int[]{250, 240, 230});
            rgb.put("magenta", new int[]{255, 0, 255});
            rgb.put("maroon", new int[]{128, 0, 0});
            rgb.put("mediumaquamarine", new int[]{102, 205, 170});
            rgb.put("mediumblue", new int[]{0, 0, 205});
            rgb.put("mediumorchid", new int[]{186, 85, 211});
            rgb.put("mediumpurple", new int[]{147, 112, 219});
            rgb.put("mediumseagreen", new int[]{60, 179, 113});
            rgb.put("mediumslateblue", new int[]{123, 104, 238});
            rgb.put("mediumspringgreen", new int[]{0, 250, 154});
            rgb.put("mediumturquoise", new int[]{72, 209, 204});
            rgb.put("mediumvioletred", new int[]{199, 21, 133});
            rgb.put("midnightblue", new int[]{25, 25, 112});
            rgb.put("mintcream", new int[]{245, 255, 250});
            rgb.put("mistyrose", new int[]{255, 228, 225});
            rgb.put("moccasin", new int[]{255, 228, 181});
            rgb.put("navajowhite", new int[]{255, 222, 173});
            rgb.put("navy", new int[]{0, 0, 128});
            rgb.put("navyblue", new int[]{159, 175, 223});
            rgb.put("oldlace", new int[]{253, 245, 230});
            rgb.put("olive", new int[]{128, 128, 0});
            rgb.put("olivedrab", new int[]{107, 142, 35});
            rgb.put("orange", new int[]{255, 165, 0});
            rgb.put("orangered", new int[]{255, 69, 0});
            rgb.put("orchid", new int[]{218, 112, 214});
            rgb.put("palegoldenrod", new int[]{238, 232, 170});
            rgb.put("palegreen", new int[]{152, 251, 152});
            rgb.put("paleturquoise", new int[]{175, 238, 238});
            rgb.put("palevioletred", new int[]{219, 112, 147});
            rgb.put("papayawhip", new int[]{255, 239, 213});
            rgb.put("peachpuff", new int[]{255, 218, 185});
            rgb.put("peru", new int[]{205, 133, 63});
            rgb.put("pink", new int[]{255, 192, 203});
            rgb.put("plum", new int[]{221, 160, 221});
            rgb.put("powderblue", new int[]{176, 224, 230});
            rgb.put("purple", new int[]{128, 0, 128});
            rgb.put("red", new int[]{255, 0, 0});
            rgb.put("rosybrown", new int[]{188, 143, 143});
            rgb.put("royalblue", new int[]{65, 105, 225});
            rgb.put("saddlebrown", new int[]{139, 69, 19});
            rgb.put("salmon", new int[]{250, 128, 114});
            rgb.put("sandybrown", new int[]{244, 164, 96});
            rgb.put("seagreen", new int[]{46, 139, 87});
            rgb.put("seashell", new int[]{255, 245, 238});
            rgb.put("sienna", new int[]{160, 82, 45});
            rgb.put("silver", new int[]{192, 192, 192});
            rgb.put("skyblue", new int[]{135, 206, 235});
            rgb.put("slateblue", new int[]{106, 90, 205});
            rgb.put("slategray", new int[]{112, 128, 144});
            rgb.put("snow", new int[]{255, 250, 250});
            rgb.put("springgreen", new int[]{0, 255, 127});
            rgb.put("steelblue", new int[]{70, 130, 180});
            rgb.put("tan", new int[]{210, 180, 140});
            rgb.put("teal", new int[]{0, 128, 128});
            rgb.put("thistle", new int[]{216, 191, 216});
            rgb.put("tomato", new int[]{255, 99, 71});
            rgb.put("turquoise", new int[]{64, 224, 208});
            rgb.put("violet", new int[]{238, 130, 238});
            rgb.put("wheat", new int[]{245, 222, 179});
            rgb.put("white", new int[]{255, 255, 255});
            rgb.put("whitesmoke", new int[]{245, 245, 245});
            rgb.put("yellow", new int[]{255, 255, 0});
            rgb.put("yellowgreen", new int[]{139, 205, 50});
        }

        /**
         * Return RGB values for a named CSS color. Case insensitive.
         * 
         * @param colorName
         * @return r,g,b byte values (0-255) or null
         */
        public static int[] getRGB(String colorName) {
            return rgb.get(colorName.toLowerCase());
        }

        /**
         * Encode the passed color as a hex string for a browser.
         * 
         * @param color
         * @return
         */
        public static String encodeRGB(Color color) {
            return "#" + Integer.toHexString(color.getRGB()).substring(2);
        }
    }

}
