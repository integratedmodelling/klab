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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.measure.converter.UnitConverter;
import javax.measure.unit.Dimension;
import javax.measure.unit.NonSI;
import javax.measure.unit.ProductUnit;
import javax.measure.unit.SI;
import javax.measure.unit.UnitFormat;

import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension.Type;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.ExtentDimension;
import org.integratedmodelling.klab.api.observations.scale.ExtentDistribution;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Triple;

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
	// this is the adopted contextualization, if any (results only from a
	// contextualize op)
	Map<ExtentDimension, ExtentDistribution> aggregatedDimensions = new HashMap<>();
	boolean wasContextualized = false;

	/**
	 * Non-SI units we can't handle directly if we need to preserve comparability
	 * with same semantics. E.g. volumes in liters wouldn't be compatible with
	 * volumes in m^3 from a unit perspective, so we turn liters into cubic
	 * decimeters at the moment of declaration.
	 * 
	 * TODO hopefully not necessary, as liters DO translate to volumes properly. For
	 * the time being the code is there in standardize() but is not used.
	 */
	private static Map<String, String> translations;

	static {
		translations = new HashMap<>();
//		translations.put("L", "dm^3");
	}

	public static Unit create(IUnit unit) {
		return new Unit(((Unit)unit)._unit);
	}
	
	/**
	 * Create a unit from a string.
	 *
	 * @param string the string
	 * @return the unit
	 */
	public static Unit create(String string) {

		if (string.trim().isEmpty()) {
			return null;
		}

		Pair<Double, String> pd = MiscUtilities.splitNumberFromString(string);
		javax.measure.unit.Unit<?> unit = null;

		double factor = 1.0;
		if (pd.getFirst() != null) {
			factor = pd.getFirst();
		}

		try {
			unit = (javax.measure.unit.Unit<?>) UnitFormat.getInstance().parseObject(string);
		} catch (Throwable e) {
			// KLAB-156: Error getting the default unit
			// catched in org.integratedmodelling.klab.model.Model.java:488
			throw new KlabValidationException("Invalid unit: " + string);
		}
		if (factor != 1.0) {
			unit = unit.times(factor);
		}

		return new Unit(unit, string);
	}

	public static Unit unitless() {
		return new Unit(javax.measure.unit.Unit.ONE);
	}

	/**
	 * Convert a quantity from a unit to another.
	 *
	 * @param value    the value
	 * @param unitFrom the unit from
	 * @param unitTo   the unit to
	 * @return the double
	 */
	public static double convert(double value, String unitFrom, String unitTo) {
		return unitFrom.equals(unitTo) ? value : create(unitTo).convert(value, create(unitFrom)).doubleValue();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isCompatible(IValueMediator other) {
		return other instanceof Unit && ((Unit) other)._unit.isCompatible(_unit);
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object o) {
		return o instanceof Unit && toUTFString().equals(((Unit) o).toUTFString());
	}

	@Override
	public boolean isUnitless() {
		return equals(unitless());
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	/**
	 * Instantiates a new unit.
	 *
	 * @param unit      the unit
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
		System.out.println(convert(120, "mg/L", "mg/dm^3"));
		Unit dio = create("mg/L");
		System.out.println("DIO era " + dio);
		System.out.println("DIO ora " + dio.standardize());
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

	public String toUTFString() {
		return _unit.toString();
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

	@Override
	public Map<ExtentDimension, ExtentDistribution> getAggregatedDimensions() {
		return aggregatedDimensions;
	}

	private Dimension getUnitDimension(ExtentDimension dimension) {
		switch (dimension) {
		case AREAL:
		case LINEAL:
		case VOLUMETRIC:
			return Dimension.LENGTH;
		case TEMPORAL:
			return Dimension.TIME;
		case CONCEPTUAL:
		case PUNTAL:
		default:
			break;

		}
		return Dimension.NONE;
	}

	/**
	 * Return the unit with any incompatible non-SI units turned into their
	 * correspondent SI.
	 * 
	 * @return
	 */
	public Unit standardize() {
		return new Unit(standardize(this._unit));
	}

	private javax.measure.unit.Unit<?> standardize(javax.measure.unit.Unit<?> unit) {
		String alternate = translations.get(unit.toString());
		if (alternate != null) {
			try {
				return (javax.measure.unit.Unit<?>) UnitFormat.getInstance().parseObject(alternate);
			} catch (ParseException e) {
				throw new KlabInternalErrorException(e);
			}
		}
		if (unit instanceof ProductUnit<?>) {
			List<Triple<javax.measure.unit.Unit<?>, Integer, Integer>> elements = new ArrayList<>();
			for (int i = 0; i < ((ProductUnit<?>) unit).getUnitCount(); i++) {
				elements.add(new Triple<>(standardize(((ProductUnit<?>) unit).getUnit(i)),
						((ProductUnit<?>) unit).getUnitPow(i), ((ProductUnit<?>) unit).getUnitRoot(i)));
			}

			javax.measure.unit.Unit<?> ret = null;
			for (int i = 0; i < elements.size(); i++) {
				javax.measure.unit.Unit<?> u = elements.get(i).getFirst().root(elements.get(i).getThird())
						.pow(elements.get(i).getSecond());
				ret = ret == null ? u : ret.times(u);
			}
			return ret;

		}
		return unit;
	}

	@Override
	public Pair<IUnit, IUnit> splitExtent(ExtentDimension dimension) {

		Dimension dim = getUnitDimension(dimension);

		if (dim == Dimension.NONE) {
			return new Pair<>(this, null);
		}

		List<javax.measure.unit.Unit<?>> components = new ArrayList<>();
		List<Integer> powers = new ArrayList<>();
		javax.measure.unit.Unit<?> extentual = null;
		int dimensionality = dimension.dimensionality;
		Dimension powered = dim.pow(dimensionality);
		boolean raiseExtentual = false;

		/*
		 * split into components, keeping the extentual component separated
		 */
		int n = _unit instanceof ProductUnit ? ((ProductUnit<?>) _unit).getUnitCount() : 1;
		for (int i = 0; i < n; i++) {

			javax.measure.unit.Unit<?> component = _unit instanceof ProductUnit ? ((ProductUnit<?>) _unit).getUnit(i)
					: _unit;
			int power = _unit instanceof ProductUnit ? ((ProductUnit<?>) _unit).getUnitPow(i) : 1;

			if (component.getDimension().equals(dim) && power == -dimensionality) {
				extentual = component;
				raiseExtentual = true;
			} else if (component.getDimension().equals(powered) && power == -1) {
				extentual = component;
			} else {
				components.add(component);
				powers.add(power);
			}
		}

		/*
		 * if the extentual component wasn't found, there must be one of the same
		 * dimension that has been yielded by dividing by the same
		 */
		if (extentual == null) {
			for (int i = 0; i < components.size(); i++) {
				if (components.get(i).getDimension() == dim) {
					extentual = components.get(i);
					powers.set(i, powers.get(i) + dimensionality);
					raiseExtentual = true;
				}
			}
		}

		if (extentual /* still */ == null) {
			return new Pair<>(this, null);
		}

		/*
		 * reconstruct the unit with the new dimensionality
		 */
		javax.measure.unit.Unit<?> decontextualized = components.get(0).pow(powers.get(0));
		for (int i = 1; i < components.size(); i++) {
			decontextualized = decontextualized.times(components.get(i).pow(powers.get(i)));
		}

		return new Pair<>(new Unit(decontextualized),
				new Unit(raiseExtentual ? extentual.pow(dimensionality) : extentual));
	}

	@Override
	public IValueMediator getContextualizingUnit(IObservable observable, IScale scale, ILocator locator) {

		if (observable.getUnit() == null || !Units.INSTANCE.needsUnitScaling(observable)
				|| this.isCompatible(observable.getUnit())) {
			return this;
		}

		/*
		 * Contextualize the passed unit and find the base unit that matches it
		 */
		UnitContextualization contextualization = Units.INSTANCE.getContextualization(observable, scale, null);

		IUnit matching = null;
		for (IUnit unit : contextualization.getCandidateUnits()) {
			if (unit.isCompatible(observable.getUnit())) {
				matching = unit;
				break;
			}
		}

		if (matching == null) {
			// shouldn't happen
			throw new IllegalStateException("trying to recontextualize a unit in an incompatible scale");
		}

		boolean regular = true;
		Unit recontextualizer = this;
		double contextualConversion = 1.0;

		/**
		 * FIXME revise!
		 */
		for (ExtentDimension ed : matching.getAggregatedDimensions().keySet()) {

			IExtent dim = ((Scale) scale).getDimension(ed.spatial ? Type.SPACE : Type.TIME);
			Pair<IUnit, IUnit> split = recontextualizer.splitExtent(ed);
			if (split != null && split.getSecond() != null) {

				recontextualizer = (Unit) split.getFirst();
				Pair<Double, IUnit> dimsize = dim.getStandardizedDimension(locator);
				contextualConversion *= split.getSecond().convert(dimsize.getFirst(), dimsize.getSecond())
						.doubleValue();

				if (dim.size() > 0 || dim.isRegular()) {
					if (!dim.isRegular()) {
						regular = false;
						break;
					}
				}

			}
		}

		return new RecontextualizingUnit((Unit) observable.getUnit(), recontextualizer, contextualConversion, !regular);
	}

	public Unit withAggregatedDimensions(Map<ExtentDimension, ExtentDistribution> set) {
		this.aggregatedDimensions.putAll(set);
		wasContextualized = true;
		return this;
	}
}
