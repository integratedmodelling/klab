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

import java.util.Vector;

// TODO: Auto-generated Javadoc
/**
 *This class is a utility for finding
 * the String based upon the wild card
 * pattern. For example if the actual
 * String "John" and your wild card pattern
 * is "J*", it will return true.
 *
 * @author Debadatta Mishra(PIKU)
 *
 * TODO make it static and stateless - do away with the silly object semantics.
 * @version $Id: $Id
 */
public class WildcardMatcher {
    /**
     * String variable for wild card pattern 
     */
    private String wildCardPatternString;
    /**
     * Variable for the length of the wild card pattern
     */
    private int wildCardPatternLength;
    /**
     * Boolean variable to for checking wild cards,
     * It is false by default.
     */
    private boolean ignoreWildCards;
    /**
     * Boolean variable to know whether the pattern
     * has leading * or not.
     */
    private boolean hasLeadingStar;
    /**
     * Boolean variable to know whether the pattern
     * has * at the end.
     */
    private boolean hasTrailingStar;
    /**
     * A String array to contain chars
     */
    private String charSegments[];
    /**
     * Variable to maintain the boundary of the String.
     */
    private int charBound;

    /**
     * Default constructor.
     */
    public WildcardMatcher() {
        ignoreWildCards = false;
    }

    /**
     * Use this for simple API. Also replicated in our StringUtils extension as matchWildcards().
     *
     * @param string the string
     * @param pattern the pattern
     * @return a boolean.
     */
    public static boolean matches(String string, String pattern) {
    	return new WildcardMatcher().match(string, pattern);
    }
    
    /**
     *This is the public method which will be called to match a String
     * with the wild card pattern.
     *
     * @param actualString of type String indicating the String to be matched
     * @param wildCardString of type String indicating the wild card String
     * @return true if matches
     */
    public boolean match(String actualString, String wildCardString) {
        wildCardPatternString = wildCardString;
        wildCardPatternLength = wildCardString.length();
        setWildCards();
        return doesMatch(actualString, 0, actualString.length());
    }

    /**
     * This method is used to set the wild cards.
     * The pattern for the wild card may be *, ? or 
     * a combination of *,? and alphanumeric character.
     */
    private void setWildCards() {
        if (wildCardPatternString.startsWith("*")) {
            hasLeadingStar = true;
        }
        if (wildCardPatternString.endsWith("*") && wildCardPatternLength > 1) {
            hasTrailingStar = true;
        }
        Vector temp = new Vector();
        int pos = 0;
        StringBuffer buf = new StringBuffer();
        while (pos < wildCardPatternLength) {
            char c = wildCardPatternString.charAt(pos++);
            switch (c) {
            case 42: // It refers to *
                if (buf.length() > 0) {
                    temp.addElement(buf.toString());
                    charBound += buf.length();
                    buf.setLength(0);
                }
                break;
            case 63: // It refers to ? 
                buf.append('\0');
                break;

            default:
                buf.append(c);
                break;
            }
        }
        if (buf.length() > 0) {
            temp.addElement(buf.toString());
            charBound += buf.length();
        }
        charSegments = new String[temp.size()];
        temp.copyInto(charSegments);
    }

    /**This is the actual method which makes comparison
     * with the wild card pattern.
     * @param text of type String indicating the actual String
     * @param startPoint of type int indicating the start index
     * of the String
     * @param endPoint of type int indicating the end index of
     * the String
     * @return true if matches.
     */
    private final boolean doesMatch(String text, int startPoint, int endPoint) {
        int textLength = text.length();

        if (startPoint > endPoint) {
            return false;
        }
        if (ignoreWildCards) {
            return endPoint - startPoint == wildCardPatternLength
                    && wildCardPatternString.regionMatches(false, 0, text, startPoint, wildCardPatternLength);
        }
        int charCount = charSegments.length;
        if (charCount == 0 && (hasLeadingStar || hasTrailingStar)) {
            return true;
        }
        if (startPoint == endPoint) {
            return wildCardPatternLength == 0;
        }
        if (wildCardPatternLength == 0) {
            return startPoint == endPoint;
        }
        if (startPoint < 0) {
            startPoint = 0;
        }
        if (endPoint > textLength) {
            endPoint = textLength;
        }
        int currPosition = startPoint;
        int bound = endPoint - charBound;
        if (bound < 0) {
            return false;
        }
        int i = 0;
        String currString = charSegments[i];
        int currStringLength = currString.length();
        if (!hasLeadingStar) {
            if (!isExpressionMatching(text, startPoint, currString, 0, currStringLength)) {
                return false;
            }
            i++;
            currPosition += currStringLength;
        }
        if (charSegments.length == 1 && !hasLeadingStar && !hasTrailingStar) {
            return currPosition == endPoint;
        }
        for (; i < charCount; i++) {
            currString = charSegments[i];
            int k = currString.indexOf('\0');
            int currentMatch;
            currentMatch = getTextPosition(text, currPosition, endPoint, currString);
            if (k < 0) {
                if (currentMatch < 0) {
                    return false;
                }
            }
            currPosition = currentMatch + currString.length();
        }
        if (!hasTrailingStar && currPosition != endPoint) {
            int clen = currString.length();
            return isExpressionMatching(text, endPoint - clen, currString, 0, clen);
        }
        return i == charCount;
    }

    /**This method finds the position of the String based upon the
     * wild card pattern. It also considers some special case
     * like *.* and ???.? and their combination.
     * @param textString of type String indicating the String
     * @param start of type int indicating the start index of the String
     * @param end of type int indicating the end index of the String
     * @param posString of type indicating the position after wild card
     * @return the position of the String
     */
    private final int getTextPosition(String textString, int start, int end, String posString) {
        /*
         * String after *
         */
        int plen = posString.length();
        int max = end - plen;
        int position = -1;
        int i = textString.indexOf(posString, start);
        /*
         * The following conditions are met for the
         * special case where user give *.* 
         */
        if (posString.equals(".")) {
            position = 1;
        }
        if (i == -1 || i > max) {
            position = -1;
        } else {
            position = i;
        }
        return position;
    }

    /**This method is used to match the wild card with the String
     * based upon the start and end index.
     * @param textString of type String indicating the String
     * @param stringStartIndex of type int indicating the start
     * index of the String.
     * @param patternString of type String indicating the pattern
     * @param patternStartIndex of type int indicating the start index
     * @param length of type int indicating the length of pattern
     * @return true if matches otherwise false
     */
    private boolean isExpressionMatching(String textString, int stringStartIndex, String patternString,
            int patternStartIndex, int length) {
        while (length-- > 0) {
            char textChar = textString.charAt(stringStartIndex++);
            char patternChar = patternString.charAt(patternStartIndex++);
            if ((ignoreWildCards || patternChar != 0) && patternChar != textChar
                    && (textChar != patternChar && textChar != patternChar)) {
                return false;
            }
        }
        return true;
    }

}
