package org.integratedmodelling.klab;

import java.io.PrintStream;
import java.util.Collection;

import javax.measure.unit.Dimension;
import javax.measure.unit.ProductUnit;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.scale.ExtentDimension;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.services.IUnitService;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.engine.resources.CoreOntology.NS;

public enum Units implements IUnitService {

	INSTANCE;

	public IUnit METERS = getUnit("m");
	public IUnit SQUARE_METERS = getUnit("m^2");
	public IUnit SQUARE_KILOMETERS = getUnit("km^2");
	public IUnit CUBIC_METERS = getUnit("m^3");

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

	public void dump(IUnit unit, PrintStream out) {

		out.println("unit " + ((Unit) unit).getUnit());

		// if (_modifier != null)
		// out.println("modifier: " + _modifier);

		out.println("is" + (isUnitless(unit) ? " " : " not ") + "unitless");
		out.println("is" + (isRate(unit) ? " " : " not ") + "a rate");
		out.println("is" + (isLengthDensity(unit) ? " " : " not ") + "a lenght density");
		out.println("is" + (isArealDensity(unit) ? " " : " not ") + "an areal density");
		out.println("is" + (isVolumeDensity(unit) ? " " : " not ") + "a volumetric density");
	}

	/**
	 * Get the default unit for the passed concept. Only returns a unit if the
	 * concept is a physical property.
	 * 
	 * @param concept
	 * @return the default SI unit or null
	 */
	@Override
	public Unit getDefaultUnitFor(IConcept concept) {
		if (!concept.is(Type.MONEY) && (concept.is(Type.EXTENSIVE_PROPERTY) || concept.is(Type.INTENSIVE_PROPERTY))) {
			Object unit = Concepts.INSTANCE.getMetadata(concept, NS.SI_UNIT_PROPERTY);
			return unit == null ? null : getUnit(unit.toString());
		}
		return null;
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

}
