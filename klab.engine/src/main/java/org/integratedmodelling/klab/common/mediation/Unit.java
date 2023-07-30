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

import javax.measure.Dimension;
import javax.measure.UnitConverter;

import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.Units.UnitContextualization;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension.Type;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.ExtentDimension;
import org.integratedmodelling.klab.api.observations.scale.ExtentDistribution;
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

/**
 * Unit of measurement. Wrapper for the JSR 385 units with extensions to permit cross-scale
 * conversion.
 * 
 * @author Ferd
 *
 */
public class Unit extends AbstractMediator implements IUnit {

    @SuppressWarnings("rawtypes")
    javax.measure.Unit _unit;
    String statement;
    Map<ExtentDimension, ExtentDistribution> aggregatedDimensions = new HashMap<>();

    /**
     * Non-SI units we can't handle directly if we need to preserve comparability with same
     * semantics. E.g. volumes in liters wouldn't be compatible with volumes in m^3 from a unit
     * perspective, so we turn liters into cubic decimeters at the moment of declaration.
     * 
     * TODO hopefully no longer necessary, as liters DO translate to volumes properly. For the time
     * being the code is there in standardize() but is not used.
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
            // FIXME why every time?
            SimpleUnitFormat formatter = SimpleUnitFormat.getInstance(SimpleUnitFormat.Flavor.ASCII);
            formatter.label(tech.units.indriya.unit.Units.LITRE, "L");
            formatter.label(tech.units.indriya.unit.Units.WEEK, "wk");
            formatter.label(NonSI.DEGREE_ANGLE, "degree_angle");
            unit = (javax.measure.Unit<?>) formatter.parse(string);
        } catch (Throwable e) {
            // KLAB-156: Error getting the default unit
            // caught in org.integratedmodelling.klab.model.Model.java:488
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
     * @param value the value
     * @param unitFrom the unit from
     * @param unitTo the unit to
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
     * @param unit the unit
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

        if (mediators != null) {
            throw new KlabInternalErrorException("contextual conversions must be performed using convert(value, scale)");
        }

        if (Observations.INSTANCE.isNodata(value)) {
            return value;
        }

        if (!(unit instanceof Unit)) {
            throw new KlabIllegalArgumentException("can't convert into a unit from " + unit);
        }

        UnitConverter converter = ((Unit) unit).getUnit().getConverterTo(_unit);
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
     * Assuming this is a simple, monodimensional unit, return the power its dimension is at.
     * Otherwise return -1.
     * 
     * @return
     */
    public int getPower() {
        Map<javax.measure.Unit<?>, Integer> bunits = _unit.getBaseUnits();
        return bunits == null ? 1 : (bunits.size() == 1 ? bunits.get(bunits.keySet().iterator().next()) : -1);
    }

    @Override
    public Map<ExtentDimension, ExtentDistribution> getAggregatedDimensions() {
        return aggregatedDimensions;
    }

    public Set<ExtentDimension> getDimensions() {
        Set<ExtentDimension> ret = new HashSet<>();
        switch(Units.INSTANCE.getSpatialDimensionality(this)) {
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
        switch(dimension) {
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
     * Return the unit with any incompatible non-SI units turned into their correspondent SI.
     * 
     * @return
     */
    public Unit standardize() {
        return new Unit(standardize(this._unit));
    }

    private javax.measure.Unit<?> standardize(javax.measure.Unit<?> unit) {
        String alternate = translations.get(unit.toString());
        if (alternate != null) {
            javax.measure.Unit<?> parsed = SimpleUnitFormat.getInstance(SimpleUnitFormat.Flavor.ASCII).parse(alternate);
            if (parsed == null) {
                throw new KlabInternalErrorException(new ParseException(unit.toString(), 0));
            } else {
                return parsed;
            }
        }
        if (unit instanceof ProductUnit<?>) {
            List<Triple<javax.measure.Unit<?>, Integer, Integer>> elements = new ArrayList<>();
            for (int i = 0; i < ((ProductUnit<?>) unit).getUnitCount(); i++) {
                elements.add(new Triple<>(standardize(((ProductUnit<?>) unit).getUnit(i)), ((ProductUnit<?>) unit).getUnitPow(i),
                        ((ProductUnit<?>) unit).getUnitRoot(i)));
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

            javax.measure.Unit<?> component = _unit instanceof ProductUnit ? ((ProductUnit<?>) _unit).getUnit(i) : _unit;
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
         * if the extentual component wasn't found, there must be one of the same dimension that has
         * been yielded by dividing by the same
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

        return new Pair<>(new Unit(decontextualized), new Unit(raiseExtentual ? extentual.pow(dimensionality) : extentual));
    }

    public Unit withAggregatedDimensions(Map<ExtentDimension, ExtentDistribution> set) {
        this.aggregatedDimensions.putAll(set);
        // wasContextualized = true;
        return this;
    }

    // /**
    // * Perform a context analysis w.r.t. the passed scale and observable and populate the
    // * aggregatedDimension map in the result to reflect how the scale's dimensions are considered
    // * when the unit is used in that scale. Will only properly work with conformant scales in the
    // * geometry, i.e. won't allow areal to mix with volumetric; any hybrid dimensionality will
    // * return a null unit.
    // *
    // * @param scale
    // * @return
    // * @deprecated
    // */
    // private Unit contextualizeExtents(IObservable observable, IGeometry scale) {
    //
    // UnitContextualization contextualization = Units.INSTANCE.getContextualization(observable,
    // scale, null);
    // Unit ret = new Unit(_unit);
    // Unit matching = null;
    //
    // if (this.isCompatible(contextualization.getChosenUnit())) {
    // matching = (Unit) contextualization.getChosenUnit();
    // /*
    // * if the chosen unit matches, all dimensions of the scale are represented.
    // */
    // for (IGeometry.Dimension dimension : scale.getDimensions()) {
    // ret.aggregatedDimensions.put(dimension.getExtentDimension(), ExtentDistribution.INTENSIVE);
    // }
    // }
    //
    // if (matching == null) {
    // for (IUnit unit : contextualization.getCandidateUnits()) {
    // if (this.isCompatible(unit)) {
    // matching = (Unit) unit;
    // break;
    // }
    // }
    // }
    //
    // if (matching == null) {
    // return null;
    // }
    //
    // ret.aggregatedDimensions.putAll(matching.getAggregatedDimensions());
    //
    // return ret;
    // }



    @Override
    public IUnit contextualize(IObservable observable, IGeometry scale) {


        return Units.INSTANCE.contextualize(observable, this, scale);

    }

    public String dumpStrategy() {

        if (this.mediators == null) {
            return "Non-contextual conversion";
        }

        String ret = "";
        for (Mediation mediator : mediators) {
            ret += (ret.isEmpty() ? "" : "\n") + mediator.description;
        }

        return ret;
    }

    @Override
    public boolean isContextual() {
        return this.mediators != null;
    }

}
