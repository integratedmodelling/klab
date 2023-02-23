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
package org.integratedmodelling.klab.api.lang;

import org.integratedmodelling.klab.api.exceptions.KlabValidationException;

// TODO: Auto-generated Javadoc
/**
 * <p>A logical connector, representing one of the possible four connectors (union (or), intersection (and),
 *    exclusion (not), disjunction (xor)).</p>
 *
 * <p>A bit complicated as a wrapper for an int, but it solves several problems related to string conversions and
 * equality. Private constructors force use of parseLogicalConnector to obtain one of the four possible static
 * instances. Because only the static members are used, == can be used to check for equality, although equals()
 * can be also used with both integers, LogicalConnectors and Strings. toString() will work properly.</p>
 *
 * @author Ferdinando Villa
 * @version $Id: $Id
 */
public class LogicalConnector {

    /** The Constant _UNION. */
    static public final int _UNION          = 0;
    
    /** The Constant _INTERSECTION. */
    static public final int _INTERSECTION   = 1;
    
    /** The Constant _EXCLUSION. */
    static public final int _EXCLUSION      = 2;
    
    /** The Constant _DISJOINT_UNION. */
    static public final int _DISJOINT_UNION = 3;

    /** The union. */
    static public LogicalConnector UNION          = new LogicalConnector(_UNION);
    
    /** The intersection. */
    static public LogicalConnector INTERSECTION   = new LogicalConnector(_INTERSECTION);
    
    /** The exclusion. */
    static public LogicalConnector EXCLUSION      = new LogicalConnector(_EXCLUSION);
    
    /** The disjoint union. */
    static public LogicalConnector DISJOINT_UNION = new LogicalConnector(_DISJOINT_UNION);

    /**
     * The value of a connector. Use this one in switch statements, or use equality 
     * with static connector members in if statements.
     */
    public int value;

    /**
     * Checks if string is a valid representation of a logical connector.
     *
     * @param s a string
     * @return true if string represents a connector.
     */
    public static boolean isLogicalConnector(String s) {
        // TODO
        boolean ret = true;
        try {
            parseLogicalConnector(s);
        } catch (KlabValidationException e) {
            ret = false;
        }
        return ret;
    }

    /**
     * Parses string into connector and returns result. Will never allocate new connectors, but only
     * return the static instance corresponding to the string.
     *
     * @param s A string representing a logical connector.
     * @return a LogicalConnector
     * @throws KlabValidationException the klab validation exception
     */
    public static LogicalConnector parseLogicalConnector(String s) {

        LogicalConnector value = null;

        s = s.trim().toLowerCase();

        // TODO support formal notation quantifiers too
        if (s.equals("and") || s.equals("intersection"))
            value = INTERSECTION;
        else if (s.equals("or") || s.equals("union"))
            value = UNION;
        else if (s.equals("not") || s.equals("exclusion"))
            value = EXCLUSION;
        else if (s.equals("xor") || s.equals("disjoint-union"))
            value = DISJOINT_UNION;
        else
            throw new KlabValidationException(s + " is not a valid logical connector");

        return value;
    }

    private LogicalConnector(int i) {
        value = i;
    }

    /**
     * Equals.
     *
     * @param c the c
     * @return true if same
     */
    public boolean equals(LogicalConnector c) {
        return c.value == value;
    }

    /**
     * Equals.
     *
     * @param c the c
     * @return a boolean.
     */
    public boolean equals(int c) {
        return value == c;
    }

    /**
     * Equals.
     *
     * @param s the s
     * @return a boolean.
     * @throws KlabValidationException the klab validation exception
     */
    public boolean equals(String s) {
        return value == parseLogicalConnector(s).value;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {

        String ret = "";
        switch (value) {
        case _UNION:
            ret = "or";
            break;
        case _INTERSECTION:
            ret = "and";
            break;
        case _EXCLUSION:
            ret = "not";
            break;
        case _DISJOINT_UNION:
            ret = "xor";
            break;
        }

        return ret;
    }
}
