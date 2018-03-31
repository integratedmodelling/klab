/*******************************************************************************
 * Copyright (C) 2007, 2015:
 * 
 * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any other
 * authors listed in @author annotations
 *
 * All rights reserved. This file is part of the k.LAB software suite, meant to enable modular,
 * collaborative, integrated development of interoperable data and model components. For details,
 * see http://integratedmodelling.org.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * Affero General Public License Version 3 or any later version.
 *
 * This program is distributed in the hope that it will be useful, but without any warranty; without
 * even the implied warranty of merchantability or fitness for a particular purpose. See the Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the Affero General Public License along with this program; if
 * not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA. The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.common.mediation;

import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.kim.utils.Range;
import org.integratedmodelling.klab.api.data.mediation.INumericRange;
import org.integratedmodelling.klab.exceptions.KlabRuntimeException;

/**
 * A simple object that allows numeric ranking scales to be defined and in some cases mediated.
 * 
 * @author Ferd
 *
 */
public class NumericRange extends Range implements INumericRange {

  boolean integerScale = false;

  public NumericRange(double left, double right, boolean leftExclusive, boolean rightExclusive) {
    super(left, right, leftExclusive, rightExclusive);
  }

  public NumericRange(int left, int right, boolean leftExclusive, boolean rightExclusive) {
    super((double) left, (double) right, leftExclusive, rightExclusive);
    this.integerScale = true;
  }

  public NumericRange(long left, long right, boolean leftExclusive, boolean rightExclusive) {
    super((double) left, (double) right, leftExclusive, rightExclusive);
    this.integerScale = true;
  }

  public NumericRange(String intvs) {
    super(intvs);
  }

  /**
   * Unbounded ranking, basically a no-op to filter values through.
   */
  public NumericRange() {
    super(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, true, true);
  }

  /**
   * Full specification - can be unbounded, partially bounded of fully bounded.
   * 
   * @param from
   * @param to
   */
  public NumericRange(Number from, Number to) {
    super(from.doubleValue(), to.doubleValue(), false, true);
  }

  /**
   * Convert passed value in passed scale to our own scale and number representation. If anyone is
   * unbounded or the passed scale is null, shut up and just return the value as passed.
   * 
   * @param d
   * @param scale
   * @return the converted number
   */
  @Override
  public Number convert(Number d, IValueMediator scale) {

    if (!(scale instanceof NumericRange)) {
      throw new KlabRuntimeException("illegal conversion in ranking: " + scale);
    }

    if (scale != null && isBounded() && ((NumericRange) scale).isBounded()) {

      double conversion = (getUpperBound() - getLowerBound())
          / (((NumericRange) scale).getUpperBound() - ((NumericRange) scale).getLowerBound());
      d = getLowerBound() + (d.doubleValue() * conversion);
      if (integerScale) {
        d = (int) Math.rint(d.doubleValue());
      }
    }

    return d;
  }

  @Override
  public boolean isInteger() {
    return integerScale;
  }

  @Override
  public boolean isCompatible(IValueMediator other) {
    return other instanceof NumericRange && ((NumericRange) other).canMediate(this);
  }

  private boolean canMediate(NumericRange rankingScale) {
    return isBounded() && rankingScale.isBounded();
  }


}
