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

import javax.measure.converter.UnitConverter;
import javax.measure.unit.UnitFormat;

import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.exceptions.KlabRuntimeException;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.Pair;

public class Unit implements IUnit {

  javax.measure.unit.Unit<?> _unit;
  int _startLine;
  int _endLine;
  String statement;

  /**
   * Create a unit from a string.
   * 
   * @param string
   * @return
   */
  public static Unit create(String string) {

    Pair<Double, String> pd = MiscUtilities.splitNumberFromString(string);
    javax.measure.unit.Unit<?> unit = null;

    double factor = 1.0;
    if (pd.getFirst() != null) {
      factor = pd.getFirst();
    }

    try {
      unit = (javax.measure.unit.Unit<?>) UnitFormat.getUCUMInstance().parseObject(string);
    } catch (Exception e) {
      throw new KlabRuntimeException(e);
    }
    if (factor != 1.0) {
      unit = unit.times(factor);
    }

    return new Unit(unit, string);
  }

  /**
   * Convert a quantity from a unit to another.
   * 
   * @param value
   * @param unitFrom
   * @param unitTo
   * @return
   */
  public static double convert(double value, String unitFrom, String unitTo) {
    return unitFrom.equals(unitTo) ? value
        : create(unitTo).convert(value, create(unitFrom)).doubleValue();
  }


  @Override
  public boolean isCompatible(IValueMediator other) {
    return other instanceof Unit && ((Unit) other)._unit.isCompatible(_unit);
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof Unit && toString().equals(((Unit) o).toString());
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  public Unit(javax.measure.unit.Unit<?> unit, String statement) {
    this._unit = unit;
    this.statement = statement;
  }

  public Unit(javax.measure.unit.Unit<?> unit) {
    this._unit = unit;
    this.statement = unit.toString();
  }


  static public void main(String[] a) {
    System.out.println(convert(120, "m", "mm"));
  }

  @Override
  public Number convert(Number value, IValueMediator unit) {

    if (!(unit instanceof Unit)) {
      throw new KlabRuntimeException("illegal conversion " + this + " to " + unit);
    }

    UnitConverter converter = ((Unit) unit).getUnit().getConverterTo(_unit);
    return converter.convert(value.doubleValue());
  }

  public javax.measure.unit.Unit<?> getUnit() {
    return _unit;
  }

  @Override
  public String toString() {
    return statement;
  }

  @Override
  public IUnit multiply(IUnit unit) {
    return new Unit(_unit.times(((Unit) unit)._unit));
  }

  @Override
  public IUnit divide(IUnit unit) {
    return new Unit(_unit.divide(((Unit) unit)._unit));
  }

  @Override
  public IUnit scale(double scale) {
    return new Unit(_unit.times(scale));
  }
}
