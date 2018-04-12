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
package org.integratedmodelling.klab.common;

import org.integratedmodelling.klab.exceptions.KlabRuntimeException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

// TODO: Auto-generated Javadoc
/**
 * The Class Quantifier.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public class Quantifier {

    /** The Constant ERROR. */
    static public final int ERROR    = -2;
    
    /** The Constant ANY. */
    static public final int ANY      = 0;
    
    /** The Constant ALL. */
    static public final int ALL      = 1;
    
    /** The Constant EXACT. */
    static public final int EXACT    = 2;
    
    /** The Constant RANGE. */
    static public final int RANGE    = 3;
    
    /** The Constant NONE. */
    static public final int NONE     = 4;
    
    /** The Constant INFINITE. */
    static public final int INFINITE = -1;

    /**
     * Create the specified quantifier.
     *
     * @return ANY
     */
    static public Quantifier ANY() {
        return new Quantifier(ANY);
    }

    /**
     * Create the specified quantifier.
     *
     * @return ALL
     */
    static public Quantifier ALL() {
        return new Quantifier(ALL);
    }

    /**
     * Create the specified quantifier.
     *
     * @return NONE
     */
    static public Quantifier NONE() {
        return new Quantifier(NONE);
    }

    /**
     * Create the specified quantifier.
     *
     * @param n the n
     * @return EXACTLY N
     */
    static public Quantifier EXACTLY(int n) {
        Quantifier q = new Quantifier(EXACT);
        q.min = q.max = n;
        return q;
    }

    /**
     * Create the specified quantifier.
     *
     * @param min the min
     * @param max the max
     * @return RANGE min,max
     */
    static public Quantifier RANGE(int min, int max) {
        Quantifier q = new Quantifier(RANGE);
        q.min = min;
        q.max = max;
        return q;
    }

    /** The type. */
    public int type = ERROR;

    /** The min. */
    // INFINITE == unbounded
    public int min = 0;
    
    /** The max. */
    public int max = 0;

    /**
     * Instantiates a new quantifier.
     *
     * @param type the type
     */
    public Quantifier(int type) {
        this.type = type;
    }

    /**
     * Gets the exact value.
     *
     * @return the exact value
     */
    public int getExactValue() {
        return min;
    }

    /**
     * Gets the min value.
     *
     * @return the min value
     */
    public int getMinValue() {
        return min;
    }

    /**
     * Gets the max value.
     *
     * @return the max value
     */
    public int getMaxValue() {
        return max;
    }

    /**
     * Instantiates a new quantifier.
     *
     * @param s the s
     * @throws org.integratedmodelling.klab.exceptions.KlabValidationException the klab validation exception
     */
    public Quantifier(String s) throws KlabValidationException {
        parse(s);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    /** {@inheritDoc} */
    @Override
    public boolean equals(Object obj) {

        // harsh, but just returning false may lead to unnoticed errors with e.g. equals(Quantifier.ALL) which
        // wouldn't work
        // even when it should. is() should be used for that, and it won't compare ranges.
        if (!(obj instanceof Quantifier))
            throw new KlabRuntimeException("comparing quantifier with non-quantifier");

        return ((Quantifier) obj).type == type && ((Quantifier) obj).min == min
                && ((Quantifier) obj).max == max;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    /**
     * Compares the type of a quantifier but not the actual ranges if any.
     *
     * @param quantifierType the quantifier type
     * @return true if compatible
     */
    public boolean is(int quantifierType) {
        return type == quantifierType;
    }

    /**
     * Parses the quantifier.
     *
     * @param s the s
     * @return the quantifier
     * @throws org.integratedmodelling.klab.exceptions.KlabValidationException the klab validation exception
     */
    public static Quantifier parseQuantifier(String s) throws KlabValidationException {

        Quantifier q = null;
        try {
            q = new Quantifier(s);
        } catch (Exception e) {
            throw new KlabValidationException(s);
        }
        if (q.type == ERROR)
            throw new KlabValidationException(s);
        return q;
    }

    /**
     * Checks if is quantifier.
     *
     * @param s the s
     * @return a boolean.
     */
    public static boolean isQuantifier(String s) {
        boolean ret = false;

        try {
            ret = parseQuantifier(s) != null;
        } catch (KlabValidationException e) {
        }
        return ret;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {

        String ret = null;

        switch (type) {
        case ANY:
            ret = "any";
            break;
        case ALL:
            ret = "all";
            break;
        case EXACT:
            ret = (min + max) == 0 ? "none" : Integer.toString(min);
            break;
        case RANGE:
            ret = (min < 0 ? "" : Integer.toString(min)) + ":" + (max < 0 ? "" : Integer.toString(max));
            break;
        case NONE:
            ret = "none";
            break;
        }

        return ret;
    }

    /**
     * Checks if is max unbound.
     *
     * @return a boolean.
     */
    public boolean isMaxUnbound() {
        return max < 0;
    }

    /**
     * Checks if is min unbound.
     *
     * @return a boolean.
     */
    public boolean isMinUnbound() {
        return min < 0;
    }

    /**
     * Parses the.
     *
     * @param s the s
     */
    public void parse(String s) {

        if (s.toLowerCase().equals("any")) {
            type = ANY;
        } else if (s.toLowerCase().equals("all")) {
            type = ALL;
        } else if (s.toLowerCase().equals("none")) {
            type = NONE;
            min = max = 0;
        } else if (s.startsWith(":")) {
            type = RANGE;
            max = Integer.parseInt(s.substring(1));
            min = INFINITE;
            if (max == 0)
                type = NONE;
        } else if (s.endsWith(":")) {
            type = RANGE;
            min = Integer.parseInt(s.substring(0, s.length() - 1));
            max = INFINITE;
        } else if (s.contains(":")) {
            type = RANGE;
            String[] ss = s.split(":");
            min = Integer.parseInt(ss[0]);
            max = Integer.parseInt(ss[1]);
            if (min == 0 && max == 0)
                type = NONE;
        } else {
            type = EXACT;
            min = max = Integer.parseInt(s);
            if (min == 0 && max == 0)
                type = NONE;
        }
    }

    /**
     * As text.
     *
     * @return the string
     */
    public String asText() {
        return toString();
    }

    /**
     * Match.
     *
     * @param matches the matches
     * @return a boolean.
     */
    public boolean match(int matches) {

        if (type == EXACT) {
            return matches == min;
        } else if (type == NONE) {
            return matches == 0;
        } else if (type == ANY) {
            return matches > 0;
        } else if (type == RANGE) {

            if (min < 0) {
                return matches <= max;
            } else if (max < 0) {
                return matches >= min;
            } else {
                return matches <= max && matches >= min;
            }
        }
        return false;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * As text operator.
     *
     * @return the string
     */
    public String asTextOperator() {
        String ret = null;

        switch (type) {
        case ANY:
            ret = "OR";
            break;
        case ALL:
            ret = "AND";
            break;
        case EXACT:
            ret = (min + max) == 0 ? "none" : Integer.toString(min);
            break;
        case RANGE:
            ret = (min < 0 ? "" : Integer.toString(min)) + ":" + (max < 0 ? "" : Integer.toString(max));
            break;
        case NONE:
            ret = "NOT";
            break;
        }

        return ret;
    }

}
