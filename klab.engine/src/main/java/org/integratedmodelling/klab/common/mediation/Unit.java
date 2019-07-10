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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.measure.converter.UnitConverter;
import javax.measure.unit.Dimension;
import javax.measure.unit.ProductUnit;
import javax.measure.unit.UnitFormat;

import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension.Type;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.ExtentDimension;
import org.integratedmodelling.klab.api.observations.scale.ExtentDistribution;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.Pair;

import com.google.common.collect.Sets;

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
	Set<ExtentDimension> aggregatedDimensions = EnumSet.noneOf(ExtentDimension.class);
	boolean wasContextualized = false;

	/**
	 * Create a unit from a string.
	 *
	 * @param string
	 *            the string
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
			unit = (javax.measure.unit.Unit<?>) UnitFormat.getUCUMInstance().parseObject(string);
		} catch (Throwable e) {
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
	 * @param value
	 *            the value
	 * @param unitFrom
	 *            the unit from
	 * @param unitTo
	 *            the unit to
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
	 * @param unit
	 *            the unit
	 * @param statement
	 *            the statement
	 */
	public Unit(javax.measure.unit.Unit<?> unit, String statement) {
		this._unit = unit;
		this.statement = statement;
	}

	/**
	 * Instantiates a new unit.
	 *
	 * @param unit
	 *            the unit
	 */
	public Unit(javax.measure.unit.Unit<?> unit) {
		this._unit = unit;
		this.statement = unit.toString();
	}

	/**
	 * The main method.
	 *
	 * @param a
	 *            the arguments
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
	public Set<ExtentDimension> getAggregatedDimensions() {
		return aggregatedDimensions;
	}

	/**
	 * Problem is: if we know it's extentual but it's been declared in the
	 * factorized form, we must find the spatial unit that it was divided by, i.e.
	 * the one that will legitimately make it extentual in that dimension.
	 * 
	 * @param extent
	 *            the extent unit. This will assume that the unit DOES contain it
	 *            even if it's not explicitly declared in the unit.
	 * @return
	 */
	public IUnit getExtentUnit(ExtentDimension extent) {

		IUnit ret = null;
		switch (extent) {
		case AREAL:
			ret = Units.INSTANCE.getArealExtentUnit(this);
			if (ret == null) {
				Pair<javax.measure.unit.Unit<?>, Integer> uret = getUnit(Dimension.LENGTH);
				// must be 1, i.e. a volume divided by area
				if (uret != null && uret.getSecond() == 1) {
					return new Unit(uret.getFirst().pow(2));
				}
			}
		case LINEAL:
			ret = Units.INSTANCE.getLengthExtentUnit(this);
			if (ret == null) {
				Pair<javax.measure.unit.Unit<?>, Integer> uret = getUnit(Dimension.LENGTH);
				// must be 2, i.e. a volume divided by lenght, or 2 (area by length)
				if (uret != null && (uret.getSecond() == 2 || uret.getSecond() == 1)) {
					return new Unit(uret.getFirst().root(1));
				}
			}
		case TEMPORAL:
			ret = Units.INSTANCE.getTimeExtentUnit(this);
			// AAGH
		case VOLUMETRIC:
			ret = Units.INSTANCE.getVolumeExtentUnit(this);
			// AAGH
		case PUNTAL:
		case CONCEPTUAL:
		default:
			break;
		}
		return ret;
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

	// public int getActualPower(javax.measure.unit.Unit<?> unit) {
	// int ret = 1;
	// if (unit instanceof ProductUnit) {
	// for (int i = 0; i < ((ProductUnit<?>) _unit).getUnitCount(); i++) {
	// ret *= getActualPower(((ProductUnit<?>) _unit).getUnit(i)) *
	// ((ProductUnit<?>) _unit).getUnitPow(i);
	// }
	// }
	// return ret;
	// }

	/**
	 * Assuming the unit is distributed over the passed extent, split the unit from
	 * the extent and return them separately. This will also infer spatial unit if
	 * called on the factorized form.
	 * 
	 * @param dimension
	 * @return
	 */
	public Pair<Unit, Unit> splitExtent(ExtentDimension dimension) {

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

	// get unit component with given dimension and return the power it's at
	public Pair<javax.measure.unit.Unit<?>, Integer> getUnit(Dimension dimension) {

		if (_unit instanceof ProductUnit<?>) {
			ProductUnit<?> pu = (ProductUnit<?>) _unit;
			for (int i = 0; i < pu.getUnitCount(); i++) {
				javax.measure.unit.Unit<?> su = pu.getUnit(i);
				if (su.getDimension().equals(dimension)) {
					return new Pair<>(su, pu.getUnitPow(i));
				}
			}
		} else if (_unit.getDimension().equals(dimension)) {
			return new Pair<>(_unit, 1);
		}
		return null;
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
		Contextualization contextualization = Units.INSTANCE.getDefaultUnitFor(observable)
				.contextualize(((Scale) scale).asGeometry(), null);

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
		for (ExtentDimension ed : matching.getAggregatedDimensions()) {

			IExtent dim = ((Scale) scale).getDimension(ed.spatial ? Type.SPACE : Type.TIME);
			Pair<Unit, Unit> split = recontextualizer.splitExtent(ed);
			if (split != null && split.getSecond() != null) {

				recontextualizer = split.getFirst();
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

		/*
		 * TODO locator size!
		 */

		return new RecontextualizingUnit((Unit)observable.getUnit(), recontextualizer, contextualConversion, !regular);
	}

	@Override
	public Contextualization contextualize(IGeometry geometry, Map<ExtentDimension, ExtentDistribution> constraints) {

		/*
		 * produce all possible base units: gather the extents in the geometry
		 */
		Set<ExtentDimension> aggregatable = new HashSet<>();
		for (IGeometry.Dimension dimension : geometry.getDimensions()) {
			if (dimension.size() > 1 || dimension.isRegular()) {
				aggregatable.add(dimension.getExtentDimension());
			}
		}

		IUnit fullyContextualized = Units.INSTANCE.contextualize(this, aggregatable);

		List<Unit> potentialUnits = new ArrayList<>();
		for (Set<ExtentDimension> set : Sets.powerSet(aggregatable)) {
			Unit aggregated = (Unit) Units.INSTANCE.removeExtents(fullyContextualized, set);
			potentialUnits.add(aggregated.withAggregatedDimensions(set));
		}

		IUnit chosen = null;

		if (constraints == null || constraints.isEmpty()) {
			chosen = fullyContextualized;
		} else {

			Set<ExtentDimension> whitelist = new HashSet<>();
			Set<ExtentDimension> blacklist = new HashSet<>();
			for (ExtentDimension d : constraints.keySet()) {
				if (!aggregatable.contains(d)) {
					continue;
				}
				if (constraints.get(d) == ExtentDistribution.EXTENSIVE) {
					whitelist.add(d);
				} else {
					blacklist.add(d);
				}
			}

			for (Unit punit : potentialUnits) {
				if (Sets.intersection(punit.getAggregatedDimensions(), whitelist).size() == whitelist.size()
						&& Sets.intersection(punit.getAggregatedDimensions(), blacklist).size() == 0) {
					chosen = punit;
					break;
				}
			}
		}

		final Set<IUnit> candidates = new HashSet<IUnit>(potentialUnits);
		final IUnit correctUnit = chosen;

		return new Contextualization() {

			@Override
			public IUnit getChosenUnit() {
				return correctUnit;
			}

			@Override
			public Collection<IUnit> getCandidateUnits() {
				return candidates;
			}
		};
	}

	private Unit withAggregatedDimensions(Set<ExtentDimension> set) {
		this.aggregatedDimensions.addAll(set);
		wasContextualized = true;
		return this;
	}
}
