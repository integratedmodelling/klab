/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify it under the terms of the Affero
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root directory of the k.LAB
 * distribution (LICENSE.txt). If this cannot be found see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned in author tags. All
 * rights reserved.
 */
package org.integratedmodelling.klab.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.integratedmodelling.klab.utils.markdown.Table;

// TODO: Auto-generated Javadoc
/**
 * The Class StringUtils.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

	/** The Constant WHITESPACE. */
	public static final int WHITESPACE = 0x0001;

	/** The Constant NONLETTERS. */
	public static final int NONLETTERS = 0x0002;

	/** The Constant UPPERCASE. */
	public static final int UPPERCASE = 0x0004;

	/**
	 * Split into lines and indent each by the given amount.
	 *
	 * @param s      the s
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
	 * Use this for simple API. Also replicated in our StringUtils extension as
	 * matchWildcards().
	 *
	 * @param string  the string
	 * @param pattern the pattern
	 * @return a boolean.
	 */
	public static boolean matchWildcards(String string, String pattern) {
		return new WildcardMatcher().match(string, pattern);
	}

	/**
	 * Justify left to passed width with wordwrap.
	 *
	 * @param text  the text
	 * @param width the width
	 * @return the string
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
	 * Remove all leading and trailing whitespace; pack whitespace in between to
	 * single space; leave a blank line if there are at least two newlines in the
	 * original whitespace. Good for formatting indented and bullshitted text like
	 * what you put in XML files into something more suitable for text processing or
	 * wiki translation.
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
	 * Pack the inside of a string but preserve leading and trailing whitespace.
	 * Written simply and expensively.
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
	 * Join a collection of strings into one string using the given char as
	 * separator. Can pass any collection; toString() will be used.
	 *
	 * @param strings   the strings
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
	 * @param strings   the strings
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
	 * @param objects   the objects
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
	 * @param objects   the objects
	 * @param separator the separator
	 * @return the string
	 */
	static public String joinObjects(Collection<?> objects, char separator) {
		return joinObjects(objects.toArray(), separator);
	}

	/**
	 * Join objects.
	 *
	 * @param objects   the objects
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
	 * Remove character at position.
	 *
	 * @param s   the s
	 * @param pos the pos
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

	/**
	 * Insert char at.
	 *
	 * @param s   the s
	 * @param c   the c
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
	 * @param str     the str
	 * @param index   the index
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
	 * @param nspc  the nspc
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
	 * @param text        the text
	 * @param replacement the replacement
	 * @return the string
	 */
	public static String replaceWhitespace(String text, String replacement) {
		return text.replaceAll("\\s+", replacement);
	}

	/**
	 * Join.
	 *
	 * @param objects   the objects
	 * @param separator the separator
	 * @param max       the max
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
		int[] ret = new int[] { 0, 0 };

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
	 * Return the collection of strings that contains the passed pattern within the
	 * passed collection, ordered so that the earliest matches come first (if
	 * pattern is 'aaa', 'aaabbb' will come before 'bbbaaa').
	 *
	 * @param pattern              the pattern
	 * @param source               the source
	 * @param minLenForInsideMatch the min len for inside match
	 * @return the list
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

		Collections.sort(matches, new Comparator<Pair<Integer, String>>() {
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
	 * Compute the initial part that does not change in the string representation of
	 * the passed objects.
	 *
	 * @param strings the strings
	 * @return the string
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

	public static String beautify(String predictor) {
		return capitalize(predictor.replaceAll("_", " "));
	}

	/**
	 * Takes a multi-line string and strips the smallest amount of whitespace that
	 * is common to all lines, basically removing the first level of indentation.
	 * One or more lines starting without any leading whitespace will cause this to
	 * return the same string.
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
	 * Ad-hoc behavior in need for generalization: insert the patch after every
	 * newline unless the previous line was empty.
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
	 * Replace any of the characters contained in the first argument with the string
	 * passed as third argument.
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

	public static String fillUpAligned(String value, String fill, int length, int alignment) {
		switch (alignment) {
		case Table.ALIGN_RIGHT: {
			return fillUpRightAligned(value, fill, length);
		}
		case Table.ALIGN_CENTER: {
			return fillUpCenterAligned(value, fill, length);
		}
		default: {
			return fillUpLeftAligned(value, fill, length);
		}
		}
	}

	public static String fillUpLeftAligned(String value, String fill, int length) {
		if (value.length() >= length) {
			return value;
		}
		while (value.length() < length) {
			value += fill;
		}
		return value;
	}

	public static String fillUpRightAligned(String value, String fill, int length) {
		if (value.length() >= length) {
			return value;
		}
		while (value.length() < length) {
			value = fill + value;
		}
		return value;
	}

	public static String fillUpCenterAligned(String value, String fill, int length) {
		if (value.length() >= length) {
			return value;
		}
		boolean left = true;
		while (value.length() < length) {
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
