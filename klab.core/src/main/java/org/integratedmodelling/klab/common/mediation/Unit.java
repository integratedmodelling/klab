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
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.measure.converter.UnitConverter;
import javax.measure.unit.UnitFormat;

import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.observations.scale.ExtentDimension;
import org.integratedmodelling.klab.api.observations.scale.ExtentDistribution;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.MapUtils;
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

    public IUnit contextualize(IUnit refUnit, Set<ExtentDimension> aggregatable) {

        Unit unit = (Unit) refUnit;

        for (ExtentDimension dim : aggregatable) {

            switch (dim) {
            case AREAL:
                int sdim = getSpatialDimensionality(unit);
                if (sdim == 0) {
                    unit = new Unit(((Unit) unit).getUnit().divide(((Unit) getUnit("m^2")).getUnit()));
                } else if (sdim != 2) {
                    return null;
                }
                break;
            case CONCEPTUAL:
                throw new KlabUnimplementedException("can't handle non-spatio/temporal extents yet");
            case LINEAL:
                sdim = getSpatialDimensionality(unit);
                if (sdim == 0) {
                    unit = new Unit(((Unit) unit).getUnit().divide(((Unit) getUnit("m")).getUnit()));
                } else if (sdim != 1) {
                    return null;
                }
                break;
            case PUNTAL:
                break;
            case TEMPORAL:
                sdim = getTemporalDimensionality(unit);
                if (sdim == 0) {
                    unit = new Unit(((Unit) unit).getUnit().divide(((Unit) getUnit("s")).getUnit()));
                } else if (sdim != 1) {
                    return null;
                }
                break;
            case VOLUMETRIC:
                sdim = getSpatialDimensionality(unit);
                if (sdim == 0) {
                    unit = new Unit(((Unit) unit).getUnit().divide(((Unit) getUnit("m^3")).getUnit()));
                } else if (sdim != 3) {
                    return null;
                }
                break;
            default:
                break;
            }
        }

        return unit;

    }
    
    @Override
    public Contextualization contextualize(IGeometry geometry, Map<ExtentDimension, ExtentDistribution> constraints) {

        /*
         * produce all possible base units: gather the extents in the geometry
         */
        Set<ExtentDimension> aggregatable = new HashSet<>();
        for (IGeometry.Dimension dimension : geometry.getDimensions()) {
            aggregatable.add(dimension.getExtentDimension());
        }

        IUnit fullyContextualized = Units.INSTANCE.contextualize(this, aggregatable);
        ret += "\n   The fully contextualized unit: " + fullyContextualized;

        List<Pair<Set<ExtentDimension>, IUnit>> potentialUnits = new ArrayList<>();
        for (Set<ExtentDimension> set : Sets.powerSet(aggregatable)) {
            IUnit aggregated = Units.INSTANCE.removeExtents(fullyContextualized, set);
            potentialUnits.add(new Pair<>(set, aggregated));
            ret += ("\n   After aggregating in " + set + " -> " + aggregated);
        }

        if (constraints == null || constraints.isEmpty()) {
            ret += "\n Chosen unit for no forcings:\n*  " + fullyContextualized;
        } else {

            Set<ExtentDimension> whitelist = new HashSet<>();
            Set<ExtentDimension> blacklist = new HashSet<>();
            for (ExtentDimension d : forcings.keySet()) {
                if (forcings.get(d) == ExtentDistribution.EXTENSIVE) {
                    whitelist.add(d);
                } else {
                    blacklist.add(d);
                }
            }

            for (Pair<Set<ExtentDimension>, IUnit> punit : potentialUnits) {
                if (Sets.intersection(punit.getFirst(), whitelist).size() == whitelist.size()
                        && Sets.intersection(punit.getFirst(), blacklist).size() == 0) {
                    ret += "\n Chosen unit for " + MapUtils.toString(forcings) + " forcings:\n*  " + punit.getSecond();
                    break;
                }
            }
        }
        return null;
    }
}
