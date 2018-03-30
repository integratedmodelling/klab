/*******************************************************************************
 *  Copyright (C) 2007, 2015:
 *  
 *    - Ferdinando Villa <ferdinando.villa@bc3research.org>
 *    - integratedmodelling.org
 *    - any other authors listed in @author annotations
 *
 *    All rights reserved. This file is part of the k.LAB software suite,
 *    meant to enable modular, collaborative, integrated 
 *    development of interoperable data and model components. For
 *    details, see http://integratedmodelling.org.
 *    
 *    This program is free software; you can redistribute it and/or
 *    modify it under the terms of the Affero General Public License 
 *    Version 3 or any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but without any warranty; without even the implied warranty of
 *    merchantability or fitness for a particular purpose.  See the
 *    Affero General Public License for more details.
 *  
 *     You should have received a copy of the Affero General Public License
 *     along with this program; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *     The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.common;

import org.integratedmodelling.klab.exceptions.KlabRuntimeException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public class Quantifier {

    static public final int ERROR    = -2;
    static public final int ANY      = 0;
    static public final int ALL      = 1;
    static public final int EXACT    = 2;
    static public final int RANGE    = 3;
    static public final int NONE     = 4;
    static public final int INFINITE = -1;

    /**
     * Create the specified quantifier.
     * @return ANY
     */
    static public Quantifier ANY() {
        return new Quantifier(ANY);
    }

    /**
     * Create the specified quantifier.
     * @return ALL
     */
    static public Quantifier ALL() {
        return new Quantifier(ALL);
    }

    /**
     * Create the specified quantifier.
     * @return NONE
     */
    static public Quantifier NONE() {
        return new Quantifier(NONE);
    }

    /**
     * Create the specified quantifier.
     * @param n 
     * @return EXACTLY N
     */
    static public Quantifier EXACTLY(int n) {
        Quantifier q = new Quantifier(EXACT);
        q.min = q.max = n;
        return q;
    }

    /**
     * Create the specified quantifier.
     * @param min 
     * @param max 
     * @return RANGE min,max
     */
    static public Quantifier RANGE(int min, int max) {
        Quantifier q = new Quantifier(RANGE);
        q.min = min;
        q.max = max;
        return q;
    }

    public int type = ERROR;

    // INFINITE == unbounded
    public int min = 0;
    public int max = 0;

    public Quantifier(int type) {
        this.type = type;
    }

    public int getExactValue() {
        return min;
    }

    public int getMinValue() {
        return min;
    }

    public int getMaxValue() {
        return max;
    }

    public Quantifier(String s) throws KlabValidationException {
        parse(s);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
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
    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    /**
     * Compares the type of a quantifier but not the actual ranges if any.
     * @param quantifierType 
     * @return true if compatible
     */
    public boolean is(int quantifierType) {
        return type == quantifierType;
    }

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

    public static boolean isQuantifier(String s) {
        boolean ret = false;

        try {
            ret = parseQuantifier(s) != null;
        } catch (KlabValidationException e) {
        }
        return ret;
    }

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

    public boolean isMaxUnbound() {
        return max < 0;
    }

    public boolean isMinUnbound() {
        return min < 0;
    }

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

    public String asText() {
        return toString();
    }

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

    public int getType() {
        return type;
    }

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
