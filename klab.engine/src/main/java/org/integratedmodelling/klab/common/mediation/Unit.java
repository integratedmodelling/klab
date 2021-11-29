/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify it under the terms of the Affero
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root directory of the k.LAB
 * distribution (LICENSE.txt). If this cannot be found see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned in author tags. All
 * rights reserved.
 */
package org.integratedmodelling.klab.common.mediation;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.measure.Dimension;
import javax.measure.UnitConverter;

import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension.Type;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.ExtentDimension;
import org.integratedmodelling.klab.api.observations.scale.ExtentDistribution;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Triple;

import si.uom.NonSI;
import tech.units.indriya.AbstractUnit;
import tech.units.indriya.format.SimpleUnitFormat;
import tech.units.indriya.unit.ProductUnit;
import tech.units.indriya.unit.UnitDimension;

// TODO: Auto-generated Javadoc
/**
 * The Class Unit.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public class Unit implements IUnit {

	javax.measure.Unit _unit;
	int _startLine;
	int _endLine;
	String statement;
	Map<ExtentDimension, ExtentDistribution> aggregatedDimensions = new HashMap<>();
	double contextualizationFactor = 1.0;
	boolean wasContextualized = false;

	/**
	 * if the next three are not null, the unit must apply contextualization logic
	 */
	private IObservable observable;
	private IScale scale;
	// each different source unit implies a different triple containing the
	// decontextualized target
	// unit (which we represent), the decontextualized source unit for conversion,
	// and the scale
	// factor.
	private Map<Unit, Triple<Unit, Unit, Double>> contextualization;

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
		// translations.put("L", "dm^3");
	}

	public static Unit create(IUnit unit) {
		return new Unit(((Unit) unit)._unit);
	}

	public IUnit getSpace() {
		return Units.INSTANCE.getDimensionUnit(this, Type.SPACE);
	}

	public IUnit getTime() {
		return Units.INSTANCE.getDimensionUnit(this, Type.SPACE);
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
		javax.measure.Unit<?> unit = null;

		double factor = 1.0;
		if (pd.getFirst() != null) {
			factor = pd.getFirst();
		}

		try {
			SimpleUnitFormat formatter = SimpleUnitFormat.getInstance(SimpleUnitFormat.Flavor.ASCII);
			formatter.label(tech.units.indriya.unit.Units.LITRE, "L");
			formatter.label(tech.units.indriya.unit.Units.WEEK, "wk");
			// TODO enable when the lib is updated
			// formatter.label(tech.units.indriya.unit.Units.MONTH, "mo");
			formatter.label(NonSI.DEGREE_ANGLE, "degree_angle");
			unit = (javax.measure.Unit<?>) SimpleUnitFormat.getInstance(SimpleUnitFormat.Flavor.ASCII).parse(string);
		} catch (Throwable e) {
			// KLAB-156: Error getting the default unit
			// catched in org.integratedmodelling.klab.model.Model.java:488
			throw new KlabValidationException("Invalid unit: " + string);
		}
		if (factor != 1.0) {
			unit = unit.multiply(factor);
		}

		return new Unit(unit, string);
	}

	public static Unit unitless() {
		return new Unit(AbstractUnit.ONE);
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
	public Unit(javax.measure.Unit<?> unit, String statement) {
		this._unit = unit;
		this.statement = statement;
	}

	/**
	 * Instantiates a new unit.
	 *
	 * @param unit the unit
	 */
	public Unit(javax.measure.Unit<?> unit) {
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
		System.out.println(convert(120, "mg/L", "g/dm^3"));
		Unit dio = create("g/l");
		System.out.println("DIO era " + dio);
		System.out.println("DIO ora " + dio.standardize());
	}

	/** {@inheritDoc} */
	@Override
	public Number convert(Number value, IValueMediator unit) {

		if (Observations.INSTANCE.isNodata(value)) {
			return value;
		}

		if (!(unit instanceof Unit)) {
			throw new KlabIllegalArgumentException("can't convert into a unit from " + unit);
		}

		if (this.scale != null) {
			if (this.contextualization == null) {
				this.contextualization = new HashMap<>();
			}
			if (!this.contextualization.containsKey(unit)) {
				Pair<Unit, Double> ctx = this.getContextualizationFactor(this.observable, unit, this.scale);
				this.contextualization.put((Unit) unit, new Triple<>(this, ctx.getFirst(), ctx.getSecond()));
			}

			Triple<Unit, Unit, Double> data = this.contextualization.get(unit);
			@SuppressWarnings("unchecked")
			UnitConverter converter = data.getSecond()._unit.getConverterTo(data.getFirst()._unit);
			return converter.convert(value.doubleValue()) * data.getThird();

		}

		UnitConverter converter = ((Unit) unit).getUnit().getConverterTo(_unit);
		return converter.convert(value.doubleValue());
	}

	@Override
	public Number backConvert(Number value, IValueMediator unit) {

		if (Observations.INSTANCE.isNodata(value)) {
			return value;
		}

		if (!(unit instanceof Unit)) {
			throw new KlabIllegalArgumentException("can't convert into a unit from " + unit);
		}

		if (this.scale != null) {
			if (this.contextualization == null) {
				this.contextualization = new HashMap<>();
			}
			if (!this.contextualization.containsKey(unit)) {
				Pair<Unit, Double> ctx = this.getContextualizationFactor(this.observable, unit, this.scale);
				this.contextualization.put((Unit) unit, new Triple<>(this, ctx.getFirst(), ctx.getSecond()));
			}

			Triple<Unit, Unit, Double> data = this.contextualization.get(unit);
			@SuppressWarnings("unchecked")
			UnitConverter converter = data.getFirst()._unit.getConverterTo(data.getSecond()._unit);
			return converter.convert(value.doubleValue()) / data.getThird();

		}

		UnitConverter converter = _unit.getConverterTo(((Unit) unit).getUnit());
		return converter.convert(value.doubleValue());
	}

	/**
	 * Gets the unit.
	 *
	 * @return the unit
	 */
	public javax.measure.Unit<?> getUnit() {
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
		return new Unit(_unit.multiply(((Unit) unit)._unit));
	}

	/** {@inheritDoc} */
	@Override
	public IUnit divide(IUnit unit) {
		return new Unit(_unit.divide(((Unit) unit)._unit));
	}

	/** {@inheritDoc} */
	@Override
	public IUnit scale(double scale) {
		return new Unit(_unit.multiply(scale));
	}

	/**
	 * Assuming this is a simple, monodimensional unit, return the power its
	 * dimension is at. Otherwise return -1.
	 * 
	 * @return
	 */
	public int getPower() {
		Map<javax.measure.Unit, Integer> bunits = _unit.getBaseUnits();
		return bunits.size() == 1 ? bunits.get(bunits.keySet().iterator().next()) : -1;
	}

	@Override
	public Map<ExtentDimension, ExtentDistribution> getAggregatedDimensions() {
		return aggregatedDimensions;
	}

	public Set<ExtentDimension> getDimensions() {
		Set<ExtentDimension> ret = new HashSet<>();
		switch (Units.INSTANCE.getSpatialDimensionality(this)) {
		case 1:
			ret.add(ExtentDimension.LINEAL);
			break;
		case 2:
			ret.add(ExtentDimension.AREAL);
			break;
		case 3:
			ret.add(ExtentDimension.VOLUMETRIC);
			break;
		}
		if (Units.INSTANCE.getTemporalDimensionality(this) == 1) {
			ret.add(ExtentDimension.TEMPORAL);
		}
		return ret;
	}

	private Dimension getUnitDimension(ExtentDimension dimension) {
		switch (dimension) {
		case AREAL:
		case LINEAL:
		case VOLUMETRIC:
			return UnitDimension.LENGTH;
		case TEMPORAL:
			return UnitDimension.TIME;
		case CONCEPTUAL:
		case PUNTAL:
		default:
			break;

		}
		return UnitDimension.NONE;
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

	private javax.measure.Unit<?> standardize(javax.measure.Unit<?> unit) {
		String alternate = translations.get(unit.toString());
		if (alternate != null) {
			javax.measure.Unit parsed = SimpleUnitFormat.getInstance(SimpleUnitFormat.Flavor.ASCII).parse(alternate);
			if (parsed == null) {
				throw new KlabInternalErrorException(new ParseException(unit.toString(), 0));
			} else {
				return parsed;
			}
		}
		if (unit instanceof ProductUnit<?>) {
			List<Triple<javax.measure.Unit<?>, Integer, Integer>> elements = new ArrayList<>();
			for (int i = 0; i < ((ProductUnit<?>) unit).getUnitCount(); i++) {
				elements.add(new Triple<>(standardize(((ProductUnit<?>) unit).getUnit(i)),
						((ProductUnit<?>) unit).getUnitPow(i), ((ProductUnit<?>) unit).getUnitRoot(i)));
			}

			javax.measure.Unit<?> ret = null;
			for (int i = 0; i < elements.size(); i++) {
				javax.measure.Unit<?> u = elements.get(i).getFirst().root(elements.get(i).getThird())
						.pow(elements.get(i).getSecond());
				ret = ret == null ? u : ret.multiply(u);
			}
			return ret;

		}
		return unit;
	}

	@Override
	public Pair<IUnit, IUnit> splitExtent(ExtentDimension dimension) {

		Dimension dim = getUnitDimension(dimension);

		if (dim == UnitDimension.NONE) {
			return new Pair<>(this, null);
		}

		List<javax.measure.Unit<?>> components = new ArrayList<>();
		List<Integer> powers = new ArrayList<>();
		javax.measure.Unit<?> extentual = null;
		int dimensionality = dimension.dimensionality;
		Dimension powered = dim.pow(dimensionality);
		boolean raiseExtentual = false;

		/*
		 * split into components, keeping the extentual component separated
		 */
		int n = _unit instanceof ProductUnit ? ((ProductUnit<?>) _unit).getUnitCount() : 1;
		for (int i = 0; i < n; i++) {

			javax.measure.Unit<?> component = _unit instanceof ProductUnit ? ((ProductUnit<?>) _unit).getUnit(i)
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
		javax.measure.Unit<?> decontextualized = components.get(0).pow(powers.get(0));
		for (int i = 1; i < components.size(); i++) {
			decontextualized = decontextualized.multiply(components.get(i).pow(powers.get(i)));
		}

		return new Pair<>(new Unit(decontextualized),
				new Unit(raiseExtentual ? extentual.pow(dimensionality) : extentual));
	}

	// @Override
	// public IValueMediator getContextualizingUnit(IObservable observable, IScale
	// scale, ILocator
	// locator) {
	//
	// if (observable.getUnit() == null ||
	// !Units.INSTANCE.needsUnitScaling(observable)
	// || this.isCompatible(observable.getUnit())) {
	// return this;
	// }
	//
	// /*
	// * Contextualize the passed unit and find the base unit that matches it
	// */
	// UnitContextualization contextualization =
	// Units.INSTANCE.getContextualization(observable,
	// scale, null);
	//
	// IUnit matching = null;
	// for (IUnit unit : contextualization.getCandidateUnits()) {
	// if (unit.isCompatible(observable.getUnit())) {
	// matching = unit;
	// break;
	// }
	// }
	//
	// if (matching == null) {
	// // shouldn't happen
	// throw new IllegalStateException("trying to recontextualize a unit in an
	// incompatible scale");
	// }
	//
	// boolean regular = true;
	// Unit recontextualizer = this;
	// double contextualConversion = 1.0;
	//
	// /**
	// * FIXME revise!
	// */
	// for (ExtentDimension ed : matching.getAggregatedDimensions().keySet()) {
	//
	// IExtent dim = ((Scale) scale).getDimension(ed.spatial ? Type.SPACE :
	// Type.TIME);
	// Pair<IUnit, IUnit> split = recontextualizer.splitExtent(ed);
	// if (split != null && split.getSecond() != null) {
	//
	// recontextualizer = (Unit) split.getFirst();
	// Pair<Double, IUnit> dimsize = dim.getStandardizedDimension(locator);
	// contextualConversion *= split.getSecond().convert(dimsize.getFirst(),
	// dimsize.getSecond()).doubleValue();
	//
	// if (dim.size() > 0 || dim.isRegular()) {
	// if (!dim.isRegular()) {
	// regular = false;
	// break;
	// }
	// }
	//
	// }
	// }
	//
	// return new RecontextualizingUnit((Unit) observable.getUnit(),
	// recontextualizer,
	// contextualConversion, !regular);
	// }

	public Unit withAggregatedDimensions(Map<ExtentDimension, ExtentDistribution> set) {
		this.aggregatedDimensions.putAll(set);
		wasContextualized = true;
		return this;
	}

	/**
	 * Perform a context analysis w.r.t. the passed scale and observable and
	 * populate the aggregatedDimension map in the result to reflect how the scale's
	 * dimensions are considered when the unit is used in that scale. Will only
	 * properly work with conformant scales in the geometry, i.e. won't allow areal
	 * to mix with volumetric; any hybrid dimensionality will return a null unit.
	 * 
	 * @param scale
	 * @return
	 */
	private Unit contextualizeExtents(IObservable observable, IScale scale) {

		UnitContextualization contextualization = Units.INSTANCE.getContextualization(observable, scale, null);
		Unit ret = new Unit(_unit);
		Unit matching = null;

		if (this.isCompatible(contextualization.getChosenUnit())) {
			matching = (Unit) contextualization.getChosenUnit();
			/*
			 * if the chosen unit matches, all dimensions of the scale are represented.
			 */
			for (IExtent dimension : scale.getExtents()) {
				ret.aggregatedDimensions.put(dimension.getExtentDimension(), ExtentDistribution.INTENSIVE);
			}
		}

		if (matching == null) {
			for (IUnit unit : contextualization.getCandidateUnits()) {
				if (this.isCompatible(unit)) {
					matching = (Unit) unit;
					break;
				}
			}
		}

		if (matching == null) {
			return null;
		}

		ret.aggregatedDimensions.putAll(matching.getAggregatedDimensions());

		return ret;
	}

	public Unit decontextualize() {
		return (Unit) Units.INSTANCE.removeExtents(this, getDimensions());
	}

	public Unit decontextualize(IScale scale) {
		return (Unit) Units.INSTANCE.removeExtents(this,
				scale.getDimensions().stream().map(dim -> dim.getExtentDimension()).collect(Collectors.toList()));
	}

	/**
	 * Return a multiplicative factor to adapt a value in the "from" unit to the
	 * passed scale, considering the extension or intension over the context
	 * embodied in the units. If there is no conformant match between either units
	 * or geometries, the return value should be Double.NaN.
	 * 
	 * @param from
	 * @param scale
	 * @return the decontextualized target unit (this unit w/o the contexts outside
	 *         of the multiplicative factor), the decontextualized source unit
	 *         (compatible with this unit) and the multiplicative factor that will
	 *         mediate the different contexts.
	 */
	private Pair<Unit, Double> getContextualizationFactor(IObservable observable, IValueMediator from, IScale scale) {

		// e.g. for mm in <S,T> this must say: intensive in area, extensive in time
		Unit contextualizedTarget = contextualizeExtents(observable, scale);
		// e.g. for mm/day in <S,T> this must say: intensive in area, intensive in time
		Unit contextualizedSource = ((Unit) from).contextualizeExtents(observable, scale);

		// must have same dimensions as keys or result is NaN
		if (contextualizedSource == null || contextualizedTarget == null || !contextualizedTarget
				.getAggregatedDimensions().keySet().equals(contextualizedSource.getAggregatedDimensions().keySet())) {
			return null;
		}

		// initialize to the conversion factor between the units after factoring out
		// either
		// dimension
		double ret = 1.0;
		if (!Observables.INSTANCE.isExtensive(observable) && contextualizedTarget.getAggregatedDimensions()
				.equals(contextualizedSource.getAggregatedDimensions())) {
			return new Pair<>((Unit) from, 1.0);
		}

		Unit sourceUnitDecontextualized = (Unit) from;

		// factor must disaggregate if extensive -> intensive, aggregate if
		// intensive->extensive
		for (ExtentDimension dim : contextualizedTarget.getAggregatedDimensions().keySet()) {
			ExtentDistribution agrTrg = contextualizedTarget.getAggregatedDimensions().get(dim);
			ExtentDistribution agrSrc = contextualizedSource.getAggregatedDimensions().get(dim);
			if (agrTrg == ExtentDistribution.EXTENSIVE && agrSrc == ExtentDistribution.INTENSIVE) {
				/*
				 * intensive -> extensive: take the scale factor from the scale and multiply the
				 * value to remove it. Scale factor units of the source dimension are
				 * represented in the scale extent.
				 */
				IExtent extent = (IExtent) scale.getDimension(dim.type);
				IUnit dimUnit = Units.INSTANCE.getDimensionUnit((IUnit) from, dim);
				sourceUnitDecontextualized = (Unit) sourceUnitDecontextualized.multiply(dimUnit);
				ret *= extent.getDimensionSize(dimUnit);

			} else if (agrTrg == ExtentDistribution.INTENSIVE && agrSrc == ExtentDistribution.EXTENSIVE) {

				IExtent extent = (IExtent) scale.getDimension(dim.type);
				IUnit dimUnit = Units.INSTANCE.getDimensionUnit(this, dim);
				ret /= extent.getDimensionSize(dimUnit);
				sourceUnitDecontextualized = (Unit) sourceUnitDecontextualized.divide(dimUnit);
			}
		}

		return new Pair<>(sourceUnitDecontextualized, ret);
	}

	@Override
	public IUnit contextualize(IObservable observable, IScale scale) {
		Unit ret = new Unit(_unit);
		ret.scale = scale;
		ret.observable = observable;
		return ret;
	}
}
