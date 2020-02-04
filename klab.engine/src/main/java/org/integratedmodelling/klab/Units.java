package org.integratedmodelling.klab;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.measure.unit.Dimension;
import javax.measure.unit.ProductUnit;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.data.mediation.IUnit.Contextualization;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.ExtentDimension;
import org.integratedmodelling.klab.api.observations.scale.ExtentDistribution;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.services.IUnitService;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.engine.resources.CoreOntology.NS;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.scale.Scale;

import com.google.common.collect.Sets;

public enum Units implements IUnitService {

	INSTANCE;

	public IUnit METERS = getUnit("m");
	public IUnit SQUARE_METERS = getUnit("m^2");
	public IUnit SQUARE_KILOMETERS = getUnit("km^2");
	public IUnit CUBIC_METERS = getUnit("m^3");
	public IUnit SECONDS = getUnit("s");
	public IUnit YEARS = getUnit("year");
	public IUnit HOURS = getUnit("h");

	private Map<String, Unit> defaultUnitCache = Collections.synchronizedMap(new HashMap<>());

	@Override
	public Unit getUnit(String string) {
		return Unit.create(string);
	}

	private Units() {
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
				javax.measure.unit.Unit<?> su = pu.getUnit(i);
				int power = pu.getUnitPow(i);
				if (su.getDimension().equals(Dimension.TIME) && power == -1) {
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
	 * @see
	 * org.integratedmodelling.thinklab.modelling.units.IUnit#getTimeExtentUnit()
	 */
	@Override
	public IUnit getTimeExtentUnit(IUnit unit) {

		if (((Unit) unit).getUnit() instanceof ProductUnit<?>) {
			ProductUnit<?> pu = (ProductUnit<?>) ((Unit) unit).getUnit();
			for (int i = 0; i < pu.getUnitCount(); i++) {
				javax.measure.unit.Unit<?> su = pu.getUnit(i);
				int power = pu.getUnitPow(i);
				if (su.getDimension().equals(Dimension.TIME) && power == -1) {
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
				javax.measure.unit.Unit<?> su = pu.getUnit(i);
				int power = pu.getUnitPow(i);
				if (su.getDimension().equals(Dimension.LENGTH) && power == -1) {
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
	 * @see
	 * org.integratedmodelling.thinklab.modelling.units.IUnit#getLengthExtentUnit()
	 */
	@Override
	public IUnit getLengthExtentUnit(IUnit unit) {

		if (((Unit) unit).getUnit() instanceof ProductUnit<?>) {
			ProductUnit<?> pu = (ProductUnit<?>) ((Unit) unit).getUnit();
			for (int i = 0; i < pu.getUnitCount(); i++) {
				javax.measure.unit.Unit<?> su = pu.getUnit(i);
				int power = pu.getUnitPow(i);
				if (su.getDimension().equals(Dimension.LENGTH) && power == -1) {
					return new Unit(su);
				}
			}
		}
		return null;
	}

	private javax.measure.unit.Unit<?> getPrimaryUnit(javax.measure.unit.Unit<?> uu) {

		if (uu instanceof ProductUnit<?>) {
			ProductUnit<?> pu = (ProductUnit<?>) uu;
			return pu.getUnit(0);
		}
		return uu;
	}

	public javax.measure.unit.Unit<?> getPrimaryUnit(IUnit unit) {
		return getPrimaryUnit(((Unit) unit).getUnit());
	}

	public boolean isArea(IUnit unit) {
		boolean ret = false;
		if (((Unit) unit).getUnit() instanceof ProductUnit<?>) {
			ProductUnit<?> pu = (ProductUnit<?>) ((Unit) unit).getUnit();
			for (int i = 0; i < pu.getUnitCount(); i++) {
				javax.measure.unit.Unit<?> su = pu.getUnit(i);
				int power = pu.getUnitPow(i);
				if ((su.getDimension().equals(Dimension.LENGTH) && power == 2)) {
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
				javax.measure.unit.Unit<?> su = pu.getUnit(i);
				int power = pu.getUnitPow(i);
				if ((su.getDimension().equals(Dimension.LENGTH.pow(2)) && power == -1)
						|| (su.getDimension().equals(Dimension.LENGTH) && power == -2)) {
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
	 * @see
	 * org.integratedmodelling.thinklab.modelling.units.IUnit#getArealExtentUnit()
	 */
	@Override
	public IUnit getArealExtentUnit(IUnit unit) {

		if (((Unit) unit).getUnit() instanceof ProductUnit<?>) {
			ProductUnit<?> pu = (ProductUnit<?>) ((Unit) unit).getUnit();
			for (int i = 0; i < pu.getUnitCount(); i++) {
				javax.measure.unit.Unit<?> su = pu.getUnit(i);
				int power = pu.getUnitPow(i);
				if (su.getDimension().equals(Dimension.LENGTH.pow(2)) && power == -1) {
					return new Unit(su);
				} else if (su.getDimension().equals(Dimension.LENGTH) && power == -2) {
					return new Unit(su.pow(2));
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
				javax.measure.unit.Unit<?> su = pu.getUnit(i);
				int power = pu.getUnitPow(i);
				if (su.getDimension().equals(Dimension.LENGTH.pow(3)) && power == -1
						|| (su.getDimension().equals(Dimension.LENGTH) && power == -3)) {
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
	 * @see
	 * org.integratedmodelling.thinklab.modelling.units.IUnit#getVolumeExtentUnit()
	 */
	@Override
	public IUnit getVolumeExtentUnit(IUnit unit) {

		if (((Unit) unit).getUnit() instanceof ProductUnit<?>) {
			ProductUnit<?> pu = (ProductUnit<?>) ((Unit) unit).getUnit();
			for (int i = 0; i < pu.getUnitCount(); i++) {
				javax.measure.unit.Unit<?> su = pu.getUnit(i);
				int power = pu.getUnitPow(i);
				if (su.getDimension().equals(Dimension.LENGTH.pow(3)) && power == -1
						|| (su.getDimension().equals(Dimension.LENGTH) && power == -3)) {
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
		switch (space.getDimensionality()) {
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
			switch (((Space) space).getDimensionSizes().length) {
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
			switch (dim) {
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

	@Override
	public IUnit removeExtents(IUnit refUnit, Collection<ExtentDimension> extentDimensions) {

		Unit unit = (Unit) refUnit;

		for (ExtentDimension dim : extentDimensions) {
			switch (dim) {
			case AREAL:
				unit = new Unit(((Unit) unit).getUnit().times(((Unit) getUnit("m^2")).getUnit()));
				break;
			case CONCEPTUAL:
				break;
			case LINEAL:
				unit = new Unit(((Unit) unit).getUnit().times(((Unit) getUnit("m")).getUnit()));
				break;
			case PUNTAL:
				break;
			case TEMPORAL:
				unit = new Unit(((Unit) unit).getUnit().times(((Unit) getUnit("s")).getUnit()));
				break;
			case VOLUMETRIC:
				unit = new Unit(((Unit) unit).getUnit().times(((Unit) getUnit("m^3")).getUnit()));
				break;
			default:
				break;
			}
		}

		return unit;
	}

	/**
	 * Ensure that the passed unit is distributed in the passed dimensions and
	 * return the result. Only add a dimension if it's not there already. If the
	 * unit already has an incompatible dimension, return null.
	 * 
	 * @param unit
	 * @param aggregatable
	 */
	@Override
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

	public void dump(IUnit unit, PrintStream out) {

		javax.measure.unit.Unit<?> iunit = ((Unit) unit).getUnit();

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
				javax.measure.unit.Unit<?> su = pu.getUnit(i);
				int power = pu.getUnitPow(i);
				out.println("   " + su + " [" + su.getDimension() + "^" + power + "]");
			}
		}
	}

	/**
	 * Get the default unit for the passed concept. Only returns a unit if the
	 * concept is a physical property.
	 * 
	 * @param concept
	 * @return the default SI unit or null
	 */
	@Override
	public Unit getDefaultUnitFor(IObservable observable) {

		if (observable.is(Type.MONEY) || observable.is(Type.MONETARY) || observable.is(Type.NUMEROSITY)) {
			return Unit.unitless();
		}

		if (defaultUnitCache.containsKey(observable.getType().getDefinition())) {
//			return defaultUnitCache.get(observable.getType().getDefinition());
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

	public ExtentDimension getExtentDimension(ISpace space) {
		switch (space.getDimensionality()) {
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

	@Override
	public boolean needsUnits(IObservable observable) {

		boolean checkMetadata = false;
		if (observable.is(Type.MONEY) || observable.is(Type.MONETARY) || observable.is(Type.EXTENSIVE_PROPERTY)
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
			 * This part is for the benefit of checking if this describes an extensive value
			 * OF some countable, done by needsUnitScaling, which calls this first, so we
			 * keep all the logic in one place. If this is a property inherent to something
			 * else, this is intensive, not extensive.
			 * 
			 * FIXME the numerosity check is because at the moment we use the inherent type
			 * for the numerosity 'of', but this makes it impossible to have "numerosity of
			 * X of Y" - which is a limitation of the language but also a stumbling block
			 * for fully general statements.
			 */
			if (checkMetadata && !observable.is(Type.NUMEROSITY) && !observable.is(Type.INTENSIVE_PROPERTY)) {
				Boolean rescalesInherent = observable.getType().getMetadata().get(IMetadata.IM_RESCALES_INHERENT,
						Boolean.class);
				if (rescalesInherent == null) {
					if (Observables.INSTANCE.getDirectInherentType(observable.getType()) != null) {
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
		return needsUnits(observable) && !observable.is(Type.INTENSIVE_PROPERTY)
				&& !observable.getType().getMetadata().get(IMetadata.IM_RESCALES_INHERENT, Boolean.FALSE);
	}

	/**
	 * Contextualize this observable (with units) to the passed geometry, returning
	 * a descriptor that contains all the acceptable <b>base</b> units paired with
	 * the set of extents that are aggregated in them. The descriptor also contains
	 * a chosen unit that corresponds to an optional set of constraints, pairing a
	 * dimension to a choice of extensive (aggregated) or intensive (distributed).
	 * If the constraints are null, the chosen unit is the one that is distributed
	 * over all the extents in the geometry.
	 * 
	 * @param geometry
	 *            a scale or geometry to contextualize to
	 * @param constraints
	 *            a map of requested constraints on the chosen unit (may be null)
	 * @return
	 */
	public Contextualization getContextualization(IObservable observable, IGeometry geometry,
			Map<ExtentDimension, ExtentDistribution> constraints) {

		if (geometry instanceof Scale) {
			geometry = ((Scale) geometry).asGeometry();
		}

		IUnit unit = getDefaultUnitFor(observable);

		return getContextualization(unit, geometry, constraints);
	}
	
	public Contextualization getContextualization(IUnit baseUnit, IGeometry geometry,
			Map<ExtentDimension, ExtentDistribution> constraints) {
		
		/*
		 * produce all possible base units: gather the extents in the geometry
		 */
		Set<ExtentDimension> aggregatable = new HashSet<>();
		for (IGeometry.Dimension dimension : geometry.getDimensions()) {
			if (dimension.size() > 1 || dimension.isRegular()) {
				aggregatable.add(dimension.getExtentDimension());
			}
		}

		IUnit fullyContextualized = contextualize(baseUnit, aggregatable);

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

}
