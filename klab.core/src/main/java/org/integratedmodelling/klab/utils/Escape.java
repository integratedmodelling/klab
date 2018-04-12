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
package org.integratedmodelling.klab.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

// TODO: Auto-generated Javadoc
/**
 * Convenience methods for escaping special characters related to HTML, XML,
 * and regular expressions.
 * </ul>
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public final class Escape {

    /**
     * Escape characters for text appearing in HTML markup.
     *
     * <P>
     * This method exists as a defence against Cross Site Scripting (XSS) hacks. This method escapes
     * all characters recommended by the Open Web App Security Project -
     * <a href='http://www.owasp.org/index.php/Cross_Site_Scripting'>link</a>.
     *
     * <P>
     * The following characters are replaced with corresponding HTML character entities :
     * <table border='1' cellpadding='3' cellspacing='0'>
     * <tr>
     * <th>Character</th>
     * <th>Encoding</th>
     * </tr>
     * <tr>
     * <td><</td>
     * <td>&lt;</td>
     * </tr>
     * <tr>
     * <td>></td>
     * <td>&gt;</td>
     * </tr>
     * <tr>
     * <td>&</td>
     * <td>&amp;</td>
     * </tr>
     * <tr>
     * <td>"</td>
     * <td>&quot;</td>
     * </tr>
     * <tr>
     * <td>'</td>
     * <td>&#039;</td>
     * </tr>
     * <tr>
     * <td>(</td>
     * <td>&#040;</td>
     * </tr>
     * <tr>
     * <td>)</td>
     * <td>&#041;</td>
     * </tr>
     * <tr>
     * <td>#</td>
     * <td>&#035;</td>
     * </tr>
     * <tr>
     * <td>%</td>
     * <td>&#037;</td>
     * </tr>
     * <tr>
     * <td>;</td>
     * <td>&#059;</td>
     * </tr>
     * <tr>
     * <td>+</td>
     * <td>&#043;</td>
     * </tr>
     * <tr>
     * <td>-</td>
     * <td>&#045;</td>
     * </tr>
     * </table>
     *
     * <P>
     * Note that JSTL's {@code <c:out>} escapes <em>only the first five</em> of the above
     * characters.
     *
     * @param aText the a text
     * @return the string
     */
    public static String forHTML(String aText) {
        final StringBuilder result = new StringBuilder();
        final StringCharacterIterator iterator = new StringCharacterIterator(aText);
        char character = iterator.current();
        while (character != CharacterIterator.DONE) {
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
     * It is important to note that if a query string appears in an <tt>HREF</tt> attribute, then
     * there are two issues - ensuring the query string is valid HTTP (it is URL-encoded), and
     * ensuring it is valid HTML (ensuring the ampersand is escaped).
     *
     * @param aURLFragment the a URL fragment
     * @return the string
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
     * It is important to note that if a query string appears in an <tt>HREF</tt> attribute, then
     * there are two issues - ensuring the query string is valid HTTP (it is URL-encoded), and
     * ensuring it is valid HTML (ensuring the ampersand is escaped).
     *
     * @param aURLFragment the a URL fragment
     * @return the string
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
     *
     * <P>
     * The following characters are replaced with corresponding character entities :
     * <table border='1' cellpadding='3' cellspacing='0'>
     * <tr>
     * <th>Character</th>
     * <th>Encoding</th>
     * </tr>
     * <tr>
     * <td><</td>
     * <td>&lt;</td>
     * </tr>
     * <tr>
     * <td>></td>
     * <td>&gt;</td>
     * </tr>
     * <tr>
     * <td>&</td>
     * <td>&amp;</td>
     * </tr>
     * <tr>
     * <td>"</td>
     * <td>&quot;</td>
     * </tr>
     * <tr>
     * <td>'</td>
     * <td>&#039;</td>
     * </tr>
     * </table>
     *
     * <P>
     * Note that JSTL's {@code <c:out>} escapes the exact same set of characters as this method.
     * <span class='highlight'>That is, {@code <c:out>} is good for escaping to produce valid XML,
     * but not for producing safe HTML.</span>
     *
     * @param aText the a text
     * @return the string
     */
    public static String forXML(String aText) {
        final StringBuilder result = new StringBuilder();
        final StringCharacterIterator iterator = new StringCharacterIterator(aText);
        char character = iterator.current();
        while (character != CharacterIterator.DONE) {
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
     * @param aText the a text
     * @return the string
     */
    public static String toDisableTags(String aText) {
        final StringBuilder result = new StringBuilder();
        final StringCharacterIterator iterator = new StringCharacterIterator(aText);
        char character = iterator.current();
        while (character != CharacterIterator.DONE) {
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
     * <P>
     * The escaped characters include :
     * <ul>
     * <li>.
     * <li>\
     * <li>?, * , and +
     * <li>&
     * <li>:
     * <li>{ and }
     * <li>[ and ]
     * <li>( and )
     * <li>^ and $
     * </ul>
     *
     * @param aRegexFragment the a regex fragment
     * @return the string
     */
    public static String forRegex(String aRegexFragment) {
        final StringBuilder result = new StringBuilder();

        final StringCharacterIterator iterator = new StringCharacterIterator(aRegexFragment);
        char character = iterator.current();
        while (character != CharacterIterator.DONE) {
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

    /**
     * For SQL.
     *
     * @param aRegexFragment the a regex fragment
     * @return the string
     */
    public static String forSQL(String aRegexFragment) {
        final StringBuilder result = new StringBuilder();

        final StringCharacterIterator iterator = new StringCharacterIterator(aRegexFragment);
        char character = iterator.current();
        while (character != CharacterIterator.DONE) {
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

    /**
     * For double quoted string.
     *
     * @param s the s
     * @param removeNewlines the remove newlines
     * @return the string
     */
    public static String forDoubleQuotedString(String s, boolean removeNewlines) {
        final StringBuilder result = new StringBuilder();

        final StringCharacterIterator iterator = new StringCharacterIterator(s);
        char character = iterator.current();
        while (character != CharacterIterator.DONE) {
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

    /**
     * Collapse whitespace.
     *
     * @param s the s
     * @return the string
     */
    public static String collapseWhitespace(String s) {

        final StringBuilder result = new StringBuilder();
        final StringCharacterIterator iterator = new StringCharacterIterator(s);
        char character = iterator.current();
        while (character != CharacterIterator.DONE) {
            /*
             * All literals need to have backslashes doubled.
             */
            boolean wasw = false;
            while (Character.isWhitespace(character)) {
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
