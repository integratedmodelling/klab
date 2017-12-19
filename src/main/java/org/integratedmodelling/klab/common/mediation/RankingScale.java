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
package org.integratedmodelling.klab.common.mediation;

import java.util.Map;

import org.integratedmodelling.klab.api.data.mediation.INumericRange;
import org.integratedmodelling.klab.api.data.mediation.IValueMediator;
import org.integratedmodelling.klab.exceptions.KlabRuntimeException;
import org.integratedmodelling.klab.utils.Pair;

/**
 * A simple object that allows numeric ranking scales to be defined and in some cases
 * mediated.
 * 
 * @author Ferd
 *
 */
public class RankingScale implements INumericRange {

    Number  lowerBound   = null;
    Number  upperBound   = null;
    boolean integerScale = false;
    boolean bounded      = false;

    /**
     * Unbounded ranking, basically a no-op to filter values through.
     */
    public RankingScale() {
    }

    /**
     * String representation that could reconstruct the k.IM statement.
     * 
     * @return
     */
    public String asText() {
        if (lowerBound == null && upperBound == null) {
            return "";
        }
        return (lowerBound == null ? " " : lowerBound.toString()) + " to "
                + (upperBound == null ? " " : upperBound.toString()) + 
                (integerScale ? " integer" : "");
    }
    
    @Override
    public String toString() {
        if (lowerBound == null && upperBound == null) {
            return "";
        }
        return (lowerBound == null ? " " : lowerBound.toString()) + " - "
                + (upperBound == null ? " " : upperBound.toString());
    }

    /**
     * Full specification - can be unbounded, partially bounded of fully bounded.
     * 
     * @param from
     * @param to
     */
    public RankingScale(Number from, Number to) {
        lowerBound = from;
        upperBound = to;
        integerScale = ((from instanceof Integer || from instanceof Long)
                && (to instanceof Integer || to instanceof Long));
        bounded = lowerBound != null && upperBound != null && !checkInfinity(lowerBound)
                && !checkInfinity(upperBound);
    }

    private boolean checkInfinity(Number n) {

        if (n instanceof Double && (Double.isInfinite(n.doubleValue()) || Double.isNaN(n.doubleValue())))
            return true;

        if (n instanceof Float && (Float.isInfinite(n.floatValue()) || Float.isNaN(n.floatValue())))
            return true;

        return false;
    }

    public Pair<Number, Number> getRange() {
        return new Pair<Number, Number>(lowerBound, upperBound);
    }

    /**
     * Convert passed value in passed scale to our own scale and number 
     * representation. If anyone is unbounded or the passed scale is
     * null, shut up and just return the value as passed.
     * 
     * @param d
     * @param scale
     * @return the converted number
     */
    @Override
    public Number convert(Number d, IValueMediator scale) {

        if (!(scale instanceof RankingScale)) {
            throw new KlabRuntimeException("illegal conversion in ranking: " + scale);
        }

        if (scale != null && bounded && ((RankingScale) scale).bounded) {

            double conversion = (upperBound.doubleValue() - lowerBound.doubleValue())
                    / (((RankingScale) scale).upperBound.doubleValue() - ((RankingScale) scale).lowerBound
                            .doubleValue());
            d = lowerBound.doubleValue() + (d.doubleValue() * conversion);
            if (integerScale) {
                d = (int) Math.rint(d.doubleValue());
            }
        }

        return d;
    }

    @Override
    public boolean isBounded() {
        return bounded;
    }

    @Override
    public boolean isInteger() {
        return integerScale;
    }

    public RankingScale(Map<?, ?> map) {
        bounded = map.get("bounded?").equals("true");
        integerScale = map.get("bounded?").equals("true");
        lowerBound = (Number) map.get("lower");
        upperBound = (Number) map.get("upper");
    }

    @Override
    public boolean isCompatible(IValueMediator other) {
        return other instanceof RankingScale &&
                ((RankingScale) other).canMediate(this);
    }

    private boolean canMediate(RankingScale rankingScale) {
        return bounded && rankingScale.bounded;
    }

    @Override
    public Number getRangeMin() {
        return lowerBound;
    }

    @Override
    public Number getRangeMax() {
        return upperBound;
    }


}
