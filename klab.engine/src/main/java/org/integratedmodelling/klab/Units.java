package org.integratedmodelling.klab;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.measure.Dimension;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.klab.api.data.Aggregation;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.ExtentDimension;
import org.integratedmodelling.klab.api.observations.scale.ExtentDistribution;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Resolution;
import org.integratedmodelling.klab.api.services.IUnitService;
import org.integratedmodelling.klab.common.mediation.AbstractMediator.ExtentSize;
import org.integratedmodelling.klab.common.mediation.AbstractMediator.Mediation;
import org.integratedmodelling.klab.common.mediation.AbstractMediator.Operation;
import org.integratedmodelling.klab.common.mediation.Currency;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.engine.resources.CoreOntology.NS;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Range;

import com.google.common.collect.Sets;

import tech.units.indriya.unit.ProductUnit;
import tech.units.indriya.unit.UnitDimension;

public enum Units implements IUnitService {

    INSTANCE;

    /**
     * Used internally to support the {@link IUnit#contextualize(IObservable, IGeometry)} operation.
     * Iterates all the possible units for an extensive observable in a specified scale, including
     * the "chosen" unit which is completed with its intensive extension. Must be used appropriately
     * - the results for non-extensive observables or incomplete scales are not reliable.
     * 
     * @author Ferd
     *
     */
    public interface UnitContextualization extends Iterable<Unit> {

        /**
         * All the admissible units corresponding to the contextualization of another to a geometry,
         * each one reporting the extents that have been aggregated in it and including the
         * "original" admissible unit with no aggregations. This one will not contain aggregation
         * data, which are supposed to be all-intensive for the scale of observation.
         * 
         * @return
         */
        Collection<Unit> getCandidateUnits();

        /**
         * The correct unit for contextualization to the geometry, taking into account the geometry
         * and any constraints passed to the method that produced this descriptor. Does not include
         * the "chosen" unit.
         * 
         * @return
         */
        Unit getChosenUnit();
    }

    public IUnit METERS = getUnit("m");
    public IUnit SQUARE_METERS = getUnit("m^2");
    public IUnit SQUARE_KILOMETERS = getUnit("km^2");
    public IUnit CUBIC_METERS = getUnit("m^3");
    public IUnit SECONDS = getUnit("s");
    public IUnit DAYS = getUnit("d");
    public IUnit WEEKS = getUnit("wk");
    public IUnit YEARS = getUnit("year");
    // TODO enable when indriya is updated
    // public IUnit MONTHS = getUnit("mo");
    public IUnit MINUTES = getUnit("min");
    public IUnit HOURS = getUnit("h");
    public IUnit MILLISECONDS = getUnit("ms");
    public IUnit COUNTER = Unit.unitless();

    private Map<String, Unit> defaultUnitCache = Collections.synchronizedMap(new HashMap<>());

    @Override
    public Unit getUnit(String string) {
        try {
            return Unit.create(string);
        } catch (Throwable t) {
            Logging.INSTANCE.error("Can't predefine unit '" + string + "': set as null, expect problems");
        }
        return null;
    }

    private Units() {

        // this is some steve bullshit to make the formatter happy, again no idea what
        // he is doing

        Services.INSTANCE.registerService(this, IUnitService.class);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.integratedmodelling.thinklab.modelling.units.IUnit#isRate()
     */
    @Override
    public boolean isRate(IUnit unit) {

        boolean ret = false;
        if (((Unit) unit).getUnit() instanceof ProductUnit<?>) {
            ProductUnit<?> pu = (ProductUnit<?>) ((Unit) unit).getUnit();
            for (int i = 0; i < pu.getUnitCount(); i++) {
                javax.measure.Unit<?> su = pu.getUnit(i);
                int power = pu.getUnitPow(i);
                if (su.getDimension().equals(UnitDimension.TIME) && power == -1) {
                    ret = true;
                    break;
                }
            }
        }
        return ret;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.integratedmodelling.thinklab.modelling.units.IUnit#getTimeExtentUnit()
     */
    @Override
    public IUnit getTimeExtentUnit(IUnit unit) {

        if (((Unit) unit).getUnit() instanceof ProductUnit<?>) {
            ProductUnit<?> pu = (ProductUnit<?>) ((Unit) unit).getUnit();
            for (int i = 0; i < pu.getUnitCount(); i++) {
                javax.measure.Unit<?> su = pu.getUnit(i);
                int power = pu.getUnitPow(i);
                if (su.getDimension().equals(UnitDimension.TIME) && power == -1) {
                    return new Unit(su);
                }
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.integratedmodelling.thinklab.modelling.units.IUnit#isLengthDensity()
     */
    @Override
    public boolean isLengthDensity(IUnit unit) {
        boolean ret = false;
        if (((Unit) unit).getUnit() instanceof ProductUnit<?>) {
            ProductUnit<?> pu = (ProductUnit<?>) ((Unit) unit).getUnit();
            for (int i = 0; i < pu.getUnitCount(); i++) {
                javax.measure.Unit<?> su = pu.getUnit(i);
                int power = pu.getUnitPow(i);
                if (su.getDimension().equals(UnitDimension.LENGTH) && power == -1) {
                    ret = true;
                    break;
                }
            }
        }
        return ret;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.integratedmodelling.thinklab.modelling.units.IUnit#getLengthExtentUnit()
     */
    @Override
    public IUnit getLengthExtentUnit(IUnit unit) {

        if (((Unit) unit).getUnit() instanceof ProductUnit<?>) {
            ProductUnit<?> pu = (ProductUnit<?>) ((Unit) unit).getUnit();
            for (int i = 0; i < pu.getUnitCount(); i++) {
                javax.measure.Unit<?> su = pu.getUnit(i);
                int power = pu.getUnitPow(i);
                if (su.getDimension().equals(UnitDimension.LENGTH) && power == -1) {
                    return new Unit(su);
                }
            }
        }
        return null;
    }

    private javax.measure.Unit<?> getPrimaryUnit(javax.measure.Unit<?> uu) {

        if (uu instanceof ProductUnit<?>) {
            ProductUnit<?> pu = (ProductUnit<?>) uu;
            return pu.getUnit(0);
        }
        return uu;
    }

    private Pair<javax.measure.Unit<?>, Integer> getPrimaryUnitPower(javax.measure.Unit<?> uu) {

        if (uu instanceof ProductUnit<?>) {
            ProductUnit<?> pu = (ProductUnit<?>) uu;
            return new Pair<>(pu.getUnit(0), pu.getUnitPow(0));
        }
        return new Pair<>(uu, 1);
    }

    public javax.measure.Unit<?> getPrimaryUnit(IUnit unit) {
        return getPrimaryUnit(((Unit) unit).getUnit());
    }

    public boolean isArea(IUnit unit) {
        boolean ret = false;
        if (((Unit) unit).getUnit() instanceof ProductUnit<?>) {
            ProductUnit<?> pu = (ProductUnit<?>) ((Unit) unit).getUnit();
            for (int i = 0; i < pu.getUnitCount(); i++) {
                javax.measure.Unit<?> su = pu.getUnit(i);
                int power = pu.getUnitPow(i);
                if ((su.getDimension().equals(UnitDimension.LENGTH) && power == 2)) {
                    ret = true;
                    break;
                }
            }
        }
        return ret;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.integratedmodelling.thinklab.modelling.units.IUnit#isArealDensity()
     */
    @Override
    public boolean isArealDensity(IUnit unit) {
        boolean ret = false;
        if (((Unit) unit).getUnit() instanceof ProductUnit<?>) {
            ProductUnit<?> pu = (ProductUnit<?>) ((Unit) unit).getUnit();
            for (int i = 0; i < pu.getUnitCount(); i++) {
                javax.measure.Unit<?> su = pu.getUnit(i);
                int power = pu.getUnitPow(i);
                if ((su.getDimension().equals(UnitDimension.LENGTH.pow(2)) && power == -1)
                        || (su.getDimension().equals(UnitDimension.LENGTH) && power == -2)) {
                    ret = true;
                    break;
                }
            }
        }
        return ret;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.integratedmodelling.thinklab.modelling.units.IUnit#getArealExtentUnit()
     */
    @Override
    public IUnit getArealExtentUnit(IUnit unit) {

        if (((Unit) unit).getUnit() instanceof ProductUnit<?>) {
            ProductUnit<?> pu = (ProductUnit<?>) ((Unit) unit).getUnit();
            for (int i = 0; i < pu.getUnitCount(); i++) {
                javax.measure.Unit<?> su = pu.getUnit(i);
                int power = pu.getUnitPow(i);
                if (su.getDimension().equals(UnitDimension.LENGTH.pow(2)) && power == -1) {
                    return new Unit(su);
                } else if (su.getDimension().equals(UnitDimension.LENGTH) && power == -2) {
                    return new Unit(su.pow(2));
                }
            }
        }
        return null;
    }

    public IUnit getLinealExtentUnit(IUnit unit) {

        if (((Unit) unit).getUnit() instanceof ProductUnit<?>) {
            ProductUnit<?> pu = (ProductUnit<?>) ((Unit) unit).getUnit();
            for (int i = 0; i < pu.getUnitCount(); i++) {
                javax.measure.Unit<?> su = pu.getUnit(i);
                int power = pu.getUnitPow(i);
                if (su.getDimension().equals(UnitDimension.LENGTH.pow(1)) && power == -1) {
                    return new Unit(su);
                } else if (su.getDimension().equals(UnitDimension.LENGTH) && power == -1) {
                    return new Unit(su.pow(1));
                }
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.integratedmodelling.thinklab.modelling.units.IUnit#isVolumeDensity()
     */
    @Override
    public boolean isVolumeDensity(IUnit unit) {
        boolean ret = false;
        if (((Unit) unit).getUnit() instanceof ProductUnit<?>) {
            ProductUnit<?> pu = (ProductUnit<?>) ((Unit) unit).getUnit();
            for (int i = 0; i < pu.getUnitCount(); i++) {
                javax.measure.Unit<?> su = pu.getUnit(i);
                int power = pu.getUnitPow(i);
                if (su.getDimension().equals(UnitDimension.LENGTH.pow(3)) && power == -1
                        || (su.getDimension().equals(UnitDimension.LENGTH) && power == -3)) {
                    ret = true;
                    break;
                }
            }
        }
        return ret;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.integratedmodelling.thinklab.modelling.units.IUnit#getVolumeExtentUnit()
     */
    @Override
    public IUnit getVolumeExtentUnit(IUnit unit) {

        if (((Unit) unit).getUnit() instanceof ProductUnit<?>) {
            ProductUnit<?> pu = (ProductUnit<?>) ((Unit) unit).getUnit();
            for (int i = 0; i < pu.getUnitCount(); i++) {
                javax.measure.Unit<?> su = pu.getUnit(i);
                int power = pu.getUnitPow(i);
                if (su.getDimension().equals(UnitDimension.LENGTH.pow(3)) && power == -1
                        || (su.getDimension().equals(UnitDimension.LENGTH) && power == -3)) {
                    return new Unit(su);
                }
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.integratedmodelling.thinklab.modelling.units.IUnit#isUnitless()
     */
    @Override
    public boolean isUnitless(IUnit unit) {

        boolean ret = false;

        if (((Unit) unit).getUnit() instanceof ProductUnit<?>) {

            // assume no unitless unit without a distribution
            ret = true;
            ProductUnit<?> pu = (ProductUnit<?>) ((Unit) unit).getUnit();
            for (int i = 0; i < pu.getUnitCount(); i++) {
                int power = pu.getUnitPow(i);
                if (power > 0) {
                    ret = false;
                    break;
                }
            }
        }
        return ret;
    }

    public boolean isSpatialDensity(IUnit unit, IGeometry.Dimension space) {
        switch(space.getDimensionality()) {
        case 0:
            return false;
        case 1:
            return isLengthDensity(unit);
        case 2:
            return isArealDensity(unit);
        case 3:
            return isVolumeDensity(unit);
        }
        return false;
    }

    @Override
    public boolean isSpatialDensity(IUnit unit, IExtent space) {
        if (space instanceof Space) {
            switch(((Space) space).getDimensionSizes().length) {
            case 0:
                return false;
            case 1:
                return isLengthDensity(unit);
            case 2:
                return isArealDensity(unit);
            case 3:
                return isVolumeDensity(unit);
            }
        }
        return false;
    }

    public int getSpatialDimensionality(IUnit unit) {
        if (isLengthDensity(unit)) {
            return 1;
        }
        if (isArealDensity(unit)) {
            return 2;
        }
        if (isVolumeDensity(unit)) {
            return 3;
        }
        return 0;
    }

    public int getTemporalDimensionality(IUnit unit) {
        if (isRate(unit)) {
            return 1;
        }
        return 0;
    }

    @Override
    public boolean isDensity(IUnit unit, IConcept extent) {

        if (extent.is(Concepts.c(NS.SPACE_DOMAIN))) {
            return isArealDensity(unit) || isLengthDensity(unit) || isVolumeDensity(unit);
        }
        if (extent.is(Concepts.c(NS.TIME_DOMAIN))) {
            return isRate(unit);
        }
        return false;
    }

    @Override
    public IUnit addExtents(IUnit refUnit, Collection<ExtentDimension> extentDimensions) {

        Unit unit = (Unit) refUnit;

        for (ExtentDimension dim : extentDimensions) {
            switch(dim) {
            case AREAL:
                unit = new Unit(((Unit) unit).getUnit().divide(((Unit) getUnit("m^2")).getUnit()));
                break;
            case CONCEPTUAL:
                break;
            case LINEAL:
                unit = new Unit(((Unit) unit).getUnit().divide(((Unit) getUnit("m")).getUnit()));
                break;
            case PUNTAL:
                break;
            case TEMPORAL:
                unit = new Unit(((Unit) unit).getUnit().divide(((Unit) getUnit("s")).getUnit()));
                break;
            case VOLUMETRIC:
                unit = new Unit(((Unit) unit).getUnit().divide(((Unit) getUnit("m^3")).getUnit()));
                break;
            default:
                break;

            }
        }

        return unit;
    }

    public IUnit addExtents(IUnit refUnit, Collection<ExtentDimension> extentDimensions, IUnit extentUnit) {

        Unit unit = (Unit) refUnit;

        for (ExtentDimension dim : extentDimensions) {
            switch(dim) {
            case AREAL:
                unit = new Unit(((Unit) unit).getUnit().divide(((Unit) extentUnit).getUnit()));
                break;
            case CONCEPTUAL:
                break;
            case LINEAL:
                unit = new Unit(((Unit) unit).getUnit().divide(((Unit) extentUnit).getUnit()));
                break;
            case PUNTAL:
                break;
            case TEMPORAL:
                unit = new Unit(((Unit) unit).getUnit().divide(((Unit) extentUnit).getUnit()));
                break;
            case VOLUMETRIC:
                unit = new Unit(((Unit) unit).getUnit().divide(((Unit) extentUnit).getUnit()));
                break;
            default:
                break;

            }
        }

        return unit;
    }

    @Override
    public IUnit removeExtents(IUnit refUnit, Collection<ExtentDimension> extentDimensions) {

        Unit unit = (Unit) refUnit;

        for (ExtentDimension dim : extentDimensions) {

            int spatial = getSpatialDimensionality(unit);
            int temporal = getTemporalDimensionality(unit);

            switch(dim) {
            case AREAL:
                if (spatial >= 2) {
                    unit = new Unit(((Unit) unit).getUnit().multiply(((Unit) getArealExtentUnit(unit)).getUnit()));
                }
                break;
            case CONCEPTUAL:
                break;
            case LINEAL:
                if (spatial >= 1) {
                    unit = new Unit(((Unit) unit).getUnit().multiply(((Unit) getLinealExtentUnit(unit)).getUnit()));
                }
                break;
            case PUNTAL:
                break;
            case TEMPORAL:
                if (temporal >= 1) {
                    unit = new Unit(((Unit) unit).getUnit().multiply(((Unit) getTimeExtentUnit(unit)).getUnit()));
                }
                break;
            case VOLUMETRIC:
                if (spatial >= 3) {
                    unit = new Unit(((Unit) unit).getUnit().multiply(((Unit) getVolumeExtentUnit(unit)).getUnit()));
                }
                break;
            default:
                break;
            }
        }

        return unit;
    }

    /**
     * Ensure that the passed unit is distributed in the passed dimensions and return the result.
     * Only add a dimension if it's not there already. If the unit already has an incompatible
     * dimension, return null.
     * 
     * @param unit
     * @param aggregatable
     */
    @Override
    public Unit contextualize(IUnit refUnit, Set<ExtentDimension> aggregatable) {

        Unit unit = (Unit) refUnit;

        for (ExtentDimension dim : aggregatable) {

            switch(dim) {
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

    public void dump(IUnit unit, PrintStream out) {

        javax.measure.Unit<?> iunit = ((Unit) unit).getUnit();

        out.println("unit " + ((Unit) unit).getUnit());

        // if (_modifier != null)
        // out.println("modifier: " + _modifier);

        out.println("is" + (isUnitless(unit) ? " " : " not ") + "unitless");
        out.println("is" + (isRate(unit) ? " " : " not ") + "a rate");
        out.println("is" + (isLengthDensity(unit) ? " " : " not ") + "a lenght density");
        out.println("is" + (isArealDensity(unit) ? " " : " not ") + "an areal density");
        out.println("is" + (isVolumeDensity(unit) ? " " : " not ") + "a volumetric density");

        if (iunit instanceof ProductUnit<?>) {
            out.println("Product of:");
            ProductUnit<?> pu = (ProductUnit<?>) ((Unit) unit).getUnit();
            for (int i = 0; i < pu.getUnitCount(); i++) {
                javax.measure.Unit<?> su = pu.getUnit(i);
                int power = pu.getUnitPow(i);
                out.println("   " + su + " [" + su.getDimension() + "^" + power + "]");
            }
        }
    }

    /**
     * Get the default unit for the passed concept. Only returns a unit if the concept is a physical
     * property or a ratio thereof.
     * 
     * @param concept
     * @return the default SI unit or null
     */
    @Override
    public Unit getDefaultUnitFor(IObservable observable) {

        if (observable.is(Type.MONEY) || observable.is(Type.MONETARY_VALUE) || observable.is(Type.NUMEROSITY)) {
            return Unit.unitless();
        }

        if (observable.is(Type.RATIO)) {
            IConcept numerator = Observables.INSTANCE.getDescribedType(observable.getType());
            IConcept denominator = Observables.INSTANCE.getComparisonType(observable.getType());
            if (numerator != null && denominator != null
                    && (numerator.is(Type.INTENSIVE_PROPERTY) || numerator.is(Type.EXTENSIVE_PROPERTY))
                    && (denominator.is(Type.INTENSIVE_PROPERTY) || denominator.is(Type.EXTENSIVE_PROPERTY))) {
                Unit utop = getDefaultUnitFor(numerator);
                Unit ubot = getDefaultUnitFor(denominator);
                if (utop != null && ubot != null) {
                    return new Unit(utop.getUnit().divide(ubot.getUnit()));
                }
            }
        }

        if (defaultUnitCache.containsKey(observable.getType().getDefinition())) {
            // return defaultUnitCache.get(observable.getType().getDefinition());
        }

        Unit ret = null;

        boolean assignUnits = observable.is(Type.EXTENSIVE_PROPERTY) || observable.is(Type.INTENSIVE_PROPERTY);

        if (assignUnits) {
            /*
             * OK only if not transformed
             */
            Boolean rescaled = observable.getType().getMetadata().get(IMetadata.IM_IS_RESCALED, Boolean.class);
            if (rescaled == null) {
                for (IConcept trait : Traits.INSTANCE.getTraits(observable.getType())) {
                    if (trait.is(Type.RESCALING)) {
                        assignUnits = false;
                        observable.getType().getMetadata().put(IMetadata.IM_IS_RESCALED, Boolean.TRUE);
                        break;
                    }
                }
                if (/* still */ assignUnits) {
                    observable.getType().getMetadata().put(IMetadata.IM_IS_RESCALED, Boolean.FALSE);
                }
            } else {
                assignUnits = !rescaled;
            }
        }

        if (/* still */ assignUnits) {
            Object unit = Concepts.INSTANCE.getMetadata(Observables.INSTANCE.getBaseObservable(observable.getType()),
                    NS.SI_UNIT_PROPERTY);
            ret = unit == null ? null : getUnit(unit.toString());

        }

        // also cache nulls
        defaultUnitCache.put(observable.getType().getDefinition(), ret);

        return ret;
    }

    public Unit getDefaultUnitFor(IConcept concept) {
        if (concept.is(Type.RATIO)) {
            IConcept numerator = Observables.INSTANCE.getDescribedType(concept);
            IConcept denominator = Observables.INSTANCE.getComparisonType(concept);
            if (numerator != null && denominator != null
                    && (numerator.is(Type.INTENSIVE_PROPERTY) || numerator.is(Type.EXTENSIVE_PROPERTY))
                    && (denominator.is(Type.INTENSIVE_PROPERTY) || denominator.is(Type.EXTENSIVE_PROPERTY))) {
                Unit utop = getDefaultUnitFor(numerator);
                Unit ubot = getDefaultUnitFor(denominator);
                if (utop != null && ubot != null) {
                    return new Unit(utop.getUnit().divide(ubot.getUnit()));
                }
            }
        }
        Object unit = Concepts.INSTANCE.getMetadata(Observables.INSTANCE.getBaseObservable(concept), NS.SI_UNIT_PROPERTY);
        return unit == null ? null : getUnit(unit.toString());
    }

    public Collection<ExtentDimension> getExtentDimensions(IScale scale) {
        Set<ExtentDimension> ret = new HashSet<>();
        if (scale.getSpace() != null) {
            ret.add(getExtentDimension(scale.getSpace()));
        }
        if (scale.getTime() != null) {
            ret.add(ExtentDimension.TEMPORAL);
        }
        return ret;
    }

    public ExtentDimension getExtentDimension(ISpace space) {
        switch(space.getDimensionality()) {
        case 0:
            return ExtentDimension.PUNTAL;
        case 1:
            return ExtentDimension.LINEAL;
        case 2:
            return ExtentDimension.AREAL;
        case 3:
            return ExtentDimension.VOLUMETRIC;
        }
        throw new IllegalArgumentException("cannot attribute dimensional extent to spatial representation " + space);
    }

    /**
     * Analyze an observable in a scale and return a contextualized unit and the needed
     * transformation to aggregate the values. If the transformation isn't stable, this will need to
     * be repeated for each locator, otherwise the result can be reused within the same
     * contextualization.
     * 
     * @return
     */
    @Override
    public Pair<IValueMediator, Aggregation> getAggregationStrategy(IObservable observable, IScale locator) {

        IUnit unit = null;
        Aggregation aggregation = null;

        switch(observable.getDescriptionType()) {

        case CATEGORIZATION:
        case VERIFICATION:
            aggregation = Aggregation.MAJORITY;
        case QUANTIFICATION:

            // } else if (aggregates && observable.getUnit() != null) {

            aggregation = Aggregation.MEAN;
            if (needsUnits(observable)) {
                unit = observable.getUnit();
                if (unit == null) {
                    unit = getDefaultUnitFor(observable);
                }
                if (needsUnitScaling(observable)) {
                    unit = removeExtents(unit, getExtentDimensions(locator)).contextualize(observable, locator);
                    aggregation = Aggregation.SUM;
                }
            }
        default:
            break;
        }

        return new Pair<>(unit, aggregation);
    }

    @Override
    public boolean needsUnits(IObservable observable) {

        if (observable.is(Type.RATIO)) {
            // needs units if both concepts do
            IConcept numerator = Observables.INSTANCE.getDescribedType(observable.getType());
            IConcept denominator = Observables.INSTANCE.getComparisonType(observable.getType());
            if (numerator != null && denominator != null
                    && (numerator.is(Type.INTENSIVE_PROPERTY) || numerator.is(Type.EXTENSIVE_PROPERTY))
                    && (denominator.is(Type.INTENSIVE_PROPERTY) || denominator.is(Type.EXTENSIVE_PROPERTY))) {
                Unit unit = getDefaultUnitFor(observable.getType());
                return unit != null && !unit.isUnitless();
            } else {
                return false;
            }
        }

        boolean checkMetadata = false;
        if (observable.is(Type.MONEY) || observable.is(Type.MONETARY_VALUE) || observable.is(Type.EXTENSIVE_PROPERTY)
                || observable.is(Type.INTENSIVE_PROPERTY) || observable.is(Type.NUMEROSITY)) {
            boolean assignUnits = true;
            Boolean rescaled = observable.getType().getMetadata().get(IMetadata.IM_IS_RESCALED, Boolean.class);
            if (rescaled == null) {
                // move on with further checks later
                checkMetadata = true;
                for (IConcept trait : Traits.INSTANCE.getTraits(observable.getType())) {
                    if (trait.is(Type.RESCALING)) {
                        assignUnits = false;
                        observable.getType().getMetadata().put(IMetadata.IM_IS_RESCALED, Boolean.TRUE);
                        break;
                    }
                }
                if (/* still */ assignUnits) {
                    observable.getType().getMetadata().put(IMetadata.IM_IS_RESCALED, Boolean.FALSE);
                }
            } else {
                assignUnits = !rescaled;
            }

            /**
             * This part is for the benefit of checking if this describes an extensive value OF some
             * countable, done by needsUnitScaling, which calls this first, so we keep all the logic
             * in one place. If this is a property inherent to something else, this is intensive,
             * not extensive.
             * 
             * FIXME the numerosity check is because at the moment we use the inherent type for the
             * numerosity 'of', but this makes it impossible to have "numerosity of X of Y" - which
             * is a limitation of the language but also a stumbling block for fully general
             * statements.
             * 
             * FIXME re-evaluate the above after switching from inherency to the describedType in
             * qualities with operators.
             */
            if (checkMetadata && !observable.is(Type.NUMEROSITY) && !observable.is(Type.INTENSIVE_PROPERTY)) {
                Boolean rescalesInherent = observable.getType().getMetadata().get(IMetadata.IM_RESCALES_INHERENT, Boolean.class);
                if (rescalesInherent == null) {
                    if (Observables.INSTANCE.getDirectInherentType(observable.getType()) != null
                            || Observables.INSTANCE.getDescribedType(observable.getType()) != null) {
                        rescalesInherent = true;
                    } else {
                        rescalesInherent = false;
                    }
                    observable.getType().getMetadata().put(IMetadata.IM_RESCALES_INHERENT, rescalesInherent);
                }
            }

            return assignUnits;
        }
        return false;
    }

    @Override
    public boolean needsUnitScaling(IObservable observable) {

        if (!needsUnits(observable)) {
            return false;
        }

        boolean aggregates = observable.getType().is(Type.EXTENSIVE_PROPERTY) || observable.getType().is(Type.NUMEROSITY)
                || observable.getType().is(Type.MONEY) || observable.getType().is(Type.MONETARY_VALUE);

        return aggregates && (Observables.INSTANCE.getDirectInherentType(observable.getType()) == null);
    }

    /**
     * Contextualize this observable (with units) to the passed geometry, returning a descriptor
     * that contains all the acceptable <b>base</b> units paired with the set of extents that are
     * aggregated in them. The descriptor also contains a chosen unit that corresponds to an
     * optional set of constraints, pairing a dimension to a choice of extensive (aggregated) or
     * intensive (distributed). If the constraints are null, the chosen unit is the one that is
     * distributed over all the extents in the geometry.
     * 
     * @param geometry a scale or geometry to contextualize to
     * @param constraints a map of requested constraints on the chosen unit (may be null)
     * @return
     */
    public UnitContextualization getContextualization(IObservable observable, IGeometry geometry,
            Map<ExtentDimension, ExtentDistribution> constraints) {

        if (geometry instanceof Scale) {
            geometry = ((Scale) geometry).asGeometry();
        }

        IUnit unit = getDefaultUnitFor(observable);

        return getContextualization(unit, geometry, constraints);
    }

    public UnitContextualization getContextualization(IUnit baseUnit, IGeometry geometry,
            Map<ExtentDimension, ExtentDistribution> constraints) {

        /*
         * produce all possible base units: gather the extents in the geometry
         */
        Set<ExtentDimension> aggregatable = new HashSet<>();
        for (IGeometry.Dimension dimension : geometry.getDimensions()) {
            /*
             * ACHTUNG - this prevents a temporal period from mediating from a grid. Must promote
             * the time or change the logics.
             */
//            if (dimension.isDistributed()) {
                aggregatable.add(dimension.getExtentDimension());
//            }
        }

        Set<ExtentDimension> implied = new HashSet<>(aggregatable);
        if (constraints != null) {
            for (ExtentDimension ed : constraints.keySet()) {
                aggregatable.add(ed);
                if (constraints.get(ed) == ExtentDistribution.EXTENSIVE) {
                    implied.remove(ed);
                } else {
                    implied.add(ed);
                }
            }
        }

        /**
         * "Correct" unit given the geometry and the constraints. This must be intensive for all
         * dimensions if no constraints are passed.
         */
        Unit chosen = contextualize(baseUnit, implied);

        /**
         * all possible other transformations of the base unit vs. the stated dimensions
         */
        Map<ExtentDimension, ExtentDistribution> context = new HashMap<>();

        // all intensive
        Unit fullyContextualized = (Unit) contextualize(baseUnit, aggregatable);
        Set<Unit> potentialUnits = new LinkedHashSet<>();
        if (fullyContextualized != null && !chosen.equals(fullyContextualized)) {
            for (ExtentDimension ed : aggregatable) {
                context.put(ed, ExtentDistribution.INTENSIVE);
            }
            potentialUnits.add(fullyContextualized.withAggregatedDimensions(new HashMap<>(context)));
        }
        // all extensive
        Unit fullyExtensive = Unit.create(baseUnit); // (Unit)Units.INSTANCE.removeExtents(fullyContextualized,
                                                     // aggregatable);
        if (chosen != null && !chosen.equals(fullyExtensive)) {
            for (ExtentDimension ed : aggregatable) {
                context.put(ed, ExtentDistribution.EXTENSIVE);
            }
            potentialUnits.add(fullyExtensive.withAggregatedDimensions(new HashMap<>(context)));
        }

        // all other non-trivial variations
        for (Set<ExtentDimension> set : Sets.powerSet(aggregatable)) {
            if (set.isEmpty()) {
                continue;
            }
            // reset
            for (ExtentDimension ed : context.keySet()) {
                context.put(ed, ExtentDistribution.EXTENSIVE);
            }
            Unit aggregated = (Unit) Units.INSTANCE.contextualize(baseUnit, set);
            if (aggregated != null && !aggregated.equals(chosen)) {
                for (ExtentDimension ed : set) {
                    context.put(ed, ExtentDistribution.INTENSIVE);
                }
                potentialUnits.add(aggregated.withAggregatedDimensions(new HashMap<>(context)));
            }
        }

        return new UnitContextualization(){

            @Override
            public Unit getChosenUnit() {
                return chosen;
            }

            @Override
            public Collection<Unit> getCandidateUnits() {
                return potentialUnits;
            }

            @Override
            public Iterator<Unit> iterator() {

                List<Unit> ret = new ArrayList<>();
                // add all-intensive if possible and needed
                if (chosen.getAggregatedDimensions().isEmpty() && !potentialUnits.isEmpty()) {
                    for (ExtentDimension dim : potentialUnits.iterator().next().getAggregatedDimensions().keySet()) {
                        chosen.getAggregatedDimensions().put(dim, ExtentDistribution.INTENSIVE);
                    }
                }

                ret.add(chosen);
                ret.addAll(potentialUnits);

                return ret.iterator();
            }
        };
    }

    /**
     * Return the unit that describes the passed dimension. Handles implicit contextualization
     * resulting from simplification (e.g. mm == mm^3/mm^2), assuming the passed dimension comes
     * from a correct analysis (as there is no way to validate that here).
     * 
     * @param type
     * @return
     */
    public IUnit getDimensionUnit(IUnit unit, ExtentDimension dimension) {

        if (dimension.type == IGeometry.Dimension.Type.SPACE) {
            switch(getSpatialDimensionality(unit)) {
            case 1:
                return getLinealExtentUnit(unit);
            case 2:
                return getArealExtentUnit(unit);
            case 3:
                return getVolumeExtentUnit(unit);
            default:
                /*
                 * TODO this is the case when the dimension has been simplified. Check that the
                 * original unit is a length, then multiply by the needed power to match the
                 * dimensionality.
                 */
                javax.measure.Unit<?> length = findUnitFor(((Unit) unit).getUnit(), UnitDimension.LENGTH);
                if (length == null) {
                    throw new KlabIllegalArgumentException("cannot find length dimension in unit " + unit + " being scanned for "
                            + dimension + " dimensionality");
                }
                return new Unit(length.pow(1).pow(dimension.dimensionality));
            }
        } else if (dimension.type == IGeometry.Dimension.Type.TIME) {
            return getTimeExtentUnit(unit);
        }

        return null;
    }

    javax.measure.Unit<?> findUnitFor(javax.measure.Unit<?> unit, Dimension dim) {
        if (unit instanceof ProductUnit<?>) {
            ProductUnit<?> pu = (ProductUnit<?>) unit;
            for (int i = 0; i < pu.getUnitCount(); i++) {
                javax.measure.Unit<?> su = pu.getUnit(i);
                if (su.getDimension().equals(dim)) {
                    return su;
                }
            }
        } else if (unit.getDimension().equals(dim)) {
            return unit;
        }
        return null;
    }

    /**
     * Return the unit that describes the passed dimension.
     * 
     * @param type
     * @return
     */
    public IUnit getDimensionUnit(IUnit unit, IGeometry.Dimension.Type type) {

        if (type == IGeometry.Dimension.Type.SPACE) {
            switch(getSpatialDimensionality(unit)) {
            case 1:
                return getLinealExtentUnit(unit);
            case 2:
                return getArealExtentUnit(unit);
            case 3:
                return getVolumeExtentUnit(unit);
            }
        } else if (type == IGeometry.Dimension.Type.TIME) {
            return getTimeExtentUnit(unit);
        }

        return null;
    }

    /**
     * Return the temporal resolution correspondent to the temporal unit passed (can be s, m, hr,
     * day, wk, yr) or null if the unit isn't temporal.
     * 
     * @param unit
     * @return
     */
    public ITime.Resolution asTemporalResolution(IUnit unit) {
        if (DAYS.equals(unit)) {
            return Time.resolution(1, Resolution.Type.DAY);
        } else if (YEARS.equals(unit)) {
            return Time.resolution(1, Resolution.Type.YEAR);
        } /*
           * TODO enable when possible (needs latest indriya) else if (MONTHS.equals(unit)) { return
           * Time.resolution(1, Resolution.Type.MONTH); }
           */else if (WEEKS.equals(unit)) {
            return Time.resolution(1, Resolution.Type.WEEK);
        } else if (SECONDS.equals(unit)) {
            return Time.resolution(1, Resolution.Type.SECOND);
        } else if (HOURS.equals(unit)) {
            return Time.resolution(1, Resolution.Type.HOUR);
        } else if (MINUTES.equals(unit)) {
            return Time.resolution(1, Resolution.Type.MINUTE);
        } else if (MILLISECONDS.equals(unit)) {
            return Time.resolution(1, Resolution.Type.MILLISECOND);
        }
        return null;
    }

    /**
     * Obtain a mediator that will convert quantities from the mediator of the observable (unit or
     * currency) into what we represent, using the scale portion over which the observation of the
     * value is made to account for any different distribution through the context.
     * <p>
     * The resulting mediator will only accept the {@link #convert(Number, ILocator)} call and throw
     * an exception in any other situation. If the observable passed has no mediator, the conversion
     * will be standard and non-contextual (using a simple conversion factor for speed). Otherwise,
     * the fastest set of transformations will be encoded in the returned mediator.
     * <p>
     * The resulting mediator will perform correcly <em>only</em> when used with locators coming
     * from the same scale that was used to produce it. It will contain transformations in
     * parametric form, so that the possible irregularity of the extents in the locators is
     * accounted for.
     * <p>
     * The strategy to create the necessary transformations, consisting in parametric
     * multiplications or divisions by an appropriately transformed extent in space and/or time, is
     * as follows:
     * <ol>
     * <li>if observable is intensive, just check compatibility and return self if compatible, throw
     * exception if not. Otherwise:
     * <li>obtain contextualized candidate forms of both the observable's base unit and self. Both
     * should have one compatible form in the candidates. If not, throw exception. If the compatible
     * form is the same, proceed as in (1). Otherwise:
     * <li>devise two strategies to mediate 1) incoming form to base form and 2) base form to this.
     * Each strategy consists of a list of parametric operations on S/T contexts with a conversion
     * factor for the basic representation (m^x for space, ms for time).
     * <li>simplify the two strategies into a single set of operations to add to the contextualized
     * unit returned, which also carries the definition of the contextual nature re: S/T and a
     * string explaining the transformations made and why.
     * </ol>
     */
    public IUnit contextualize(IObservable observable, IUnit target, IGeometry scale) {

        if (observable.getUnit() == null) {
            return target;
        }

        if (!needsUnitScaling(observable)) {
            if (!target.isCompatible(observable.getUnit())) {
                throw new KlabIllegalStateException(
                        "Cannot mediate " + observable.getUnit() + " to " + target + " in an non-extensive observation");
            }
        }

        IUnit source = observable.getUnit();

        Unit sourceModel = null;
        Unit targetModel = null;

        /*
         * contextualization uses the base unit. Retrieve the contextual extension of both units,
         * which must be compatible with exactly one of the possible candidates.
         */
        for (Unit candidate : getContextualization(observable, scale, null)) {
            if (candidate.isCompatible(source)) {
                sourceModel = candidate;
            }
            if (candidate.isCompatible(target)) {
                targetModel = candidate;
            }
        }

        if (sourceModel == null || targetModel == null) {
            // sorry
            throw new KlabIllegalStateException(
                    "Cannot mediate unit " + observable.getUnit() + " to " + target + " in the chosen scale");
        }

        /*
         * this will be the output unit, to which we add the scaling operators and the precompiled
         * converters so that standard conversion is skipped
         */
        Unit ret = new Unit(((Unit) target).getUnit());

        if (sourceModel == targetModel) {

            /*
             * scaling is compatible, so just use straight mediation. We do it this way because it's
             * faster to store a precomputed converter in repeated calls.
             */
            Mediation mediation = new Mediation();
            mediation.converter = ((Unit) source).getUnit().getConverterTo((javax.measure.Unit) ((Unit) target).getUnit());
            mediation.description = "CONVERT the current value from " + source + " to " + target;
            ret.setMediation(source, Collections.singletonList(mediation));
            return ret;
        }

        List<Mediation> mediations = new ArrayList<>();

        /*
         * first mediation requires no recontextualization: source has no extension and that's fine
         */
        mediations.addAll(mediate((Unit) source, sourceModel));

        /*
         * mediate from the current unit and extension to the target model
         */
        mediations.addAll(mediate(sourceModel, targetModel));

        /*
         * finally mediate from the target model to the target, no extension needed
         */
        mediations.addAll(mediate(targetModel, (Unit) target));

        /*
         * TODO simplify the strategy if necessary. Unsure how much efficiency this would gain, but
         * these do get executed potentially millions of times. Should make some test.
         */

        ret.setMediation(source, mediations);

        return ret;
    }

    private List<Mediation> mediate(Unit source, Unit destination) {

        List<Mediation> ret = new ArrayList<>();

        if (!source.getAggregatedDimensions().isEmpty() && !destination.getAggregatedDimensions().isEmpty()) {

            Unit baseUnit = source;

            for (ExtentDimension dim : source.getAggregatedDimensions().keySet()) {
                ExtentDistribution sourceExtension = source.getAggregatedDimensions().get(dim);
                ExtentDistribution targetExtension = destination.getAggregatedDimensions().get(dim);
                if (sourceExtension != targetExtension) {

                    Mediation mediation = new Mediation();
                    if (targetExtension == ExtentDistribution.EXTENSIVE) {
                        // intensive -> extensive: remove the dimension from target
                        IUnit dimensionalUnit = getDimensionUnit(source, dim);
                        mediation.extentSize = getExtentSize(dim);
                        mediation.factor = getExtentMultiplier(dimensionalUnit, mediation.extentSize);
                        mediation.operation = Operation.MULTIPLY;
                        baseUnit = (Unit) removeExtents(baseUnit, Collections.singleton(dim));
                    } else {
                        // extensive -> add the same dimension to target
                        IUnit dimensionalUnit = getDimensionUnit(destination, dim);
                        mediation.extentSize = getExtentSize(dim);
                        mediation.factor = getExtentMultiplier(dimensionalUnit, mediation.extentSize);
                        mediation.operation = Operation.DIVIDE;
                        baseUnit = (Unit) addExtents(baseUnit, Collections.singleton(dim), dimensionalUnit);
                    }

                    mediation.description = mediation.operation + " the current value by " + mediation.extentSize.getDescription()
                            + (mediation.factor == 1 ? "" : " multiplied by " + mediation.factor) + " to obtain " + destination;

                    ret.add(mediation);
                }
            }

        } else if (!source.equals(destination)) {
            Mediation mediation = new Mediation();
            mediation.converter = source.getUnit().getConverterTo((javax.measure.Unit) destination.getUnit());
            mediation.description = "CONVERT the current value from " + source + " to " + destination;
            ret.add(mediation);
        }

        return ret;
    }

    double getExtentMultiplier(IUnit dimensionalUnit, ExtentSize extentSize) {
        switch(extentSize) {
        case SPACE_M:
            return 1.0 / METERS.convert(1, dimensionalUnit).doubleValue();
        case SPACE_M2:
            return 1.0 / SQUARE_METERS.convert(1, dimensionalUnit).doubleValue();
        case SPACE_M3:
            return 1.0 / CUBIC_METERS.convert(1, dimensionalUnit).doubleValue();
        case TIME_MS:
            return 1.0 / MILLISECONDS.convert(1, dimensionalUnit).doubleValue();
        default:
            break;

        }
        return 1;
    }

    private ExtentSize getExtentSize(ExtentDimension dim) {
        switch(dim) {
        case AREAL:
            return ExtentSize.SPACE_M2;
        case LINEAL:
            return ExtentSize.SPACE_M;
        case TEMPORAL:
            return ExtentSize.TIME_MS;
        case VOLUMETRIC:
            return ExtentSize.SPACE_M3;
        default:
            break;
        }
        return null;
    }

    public IValueMediator getMediator(String unit) {
        if (unit.contains("@")) {
            return Currency.create(unit);
        } else if (unit.trim().contains(" ")) {
            return Range.create(unit);
        }
        return Unit.create(unit);
    }

}
