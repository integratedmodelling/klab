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
package org.integratedmodelling.klab.common.mediation;

import javax.measure.converter.UnitConverter;
import javax.measure.unit.UnitFormat;

import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.MiscUtilities;

// TODO: Auto-generated Javadoc
/**
 * The Class Unit.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public class Unit implements IUnit {

  javax.measure.unit.Unit<?> _unit;
  int _startLine;
  int _endLine;
  String statement;

  /**
   * Create a unit from a string.
   *
   * @param string the string
   * @return the unit
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
      throw new KlabValidationException(e);
    }
    if (factor != 1.0) {
      unit = unit.times(factor);
    }

    return new Unit(unit, string);
  }

  /**
   * Convert a quantity from a unit to another.
   *
   * @param value the value
   * @param unitFrom the unit from
   * @param unitTo the unit to
   * @return the double
   */
  public static double convert(double value, String unitFrom, String unitTo) {
    return unitFrom.equals(unitTo) ? value
        : create(unitTo).convert(value, create(unitFrom)).doubleValue();
  }


  /** {@inheritDoc} */
  @Override
  public boolean isCompatible(IValueMediator other) {
    return other instanceof Unit && ((Unit) other)._unit.isCompatible(_unit);
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(Object o) {
    return o instanceof Unit && toString().equals(((Unit) o).toString());
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  /**
   * Instantiates a new unit.
   *
   * @param unit the unit
   * @param statement the statement
   */
  public Unit(javax.measure.unit.Unit<?> unit, String statement) {
    this._unit = unit;
    this.statement = statement;
  }

  /**
   * Instantiates a new unit.
   *
   * @param unit the unit
   */
  public Unit(javax.measure.unit.Unit<?> unit) {
    this._unit = unit;
    this.statement = unit.toString();
  }


  /**
   * The main method.
   *
   * @param a the arguments
   */
  static public void main(String[] a) {
    System.out.println(convert(120, "m", "mm"));
  }

  /** {@inheritDoc} */
  @Override
  public Number convert(Number value, IValueMediator unit) {

    if (!(unit instanceof Unit)) {
      throw new IllegalArgumentException("illegal conversion " + this + " to " + unit);
    }

    UnitConverter converter = ((Unit) unit).getUnit().getConverterTo(_unit);
    return converter.convert(value.doubleValue());
  }

  /**
   * Gets the unit.
   *
   * @return the unit
   */
  public javax.measure.unit.Unit<?> getUnit() {
    return _unit;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return statement;
  }

  /** {@inheritDoc} */
  @Override
  public IUnit multiply(IUnit unit) {
    return new Unit(_unit.times(((Unit) unit)._unit));
  }

  /** {@inheritDoc} */
  @Override
  public IUnit divide(IUnit unit) {
    return new Unit(_unit.divide(((Unit) unit)._unit));
  }

  /** {@inheritDoc} */
  @Override
  public IUnit scale(double scale) {
    return new Unit(_unit.times(scale));
  }
}
