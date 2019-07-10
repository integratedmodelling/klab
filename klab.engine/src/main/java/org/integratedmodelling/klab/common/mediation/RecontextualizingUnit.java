package org.integratedmodelling.klab.common.mediation;

import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.IScale;

/**
 * Result of a
 * {@link IUnit#getContextualizingUnit(IObservable, IScale, ILocator)}
 * operation. All the actual work is done there, this is a special-purpose unit
 * meant to be called repeatedly.
 * 
 * @author Ferd
 *
 */
public class RecontextualizingUnit implements IValueMediator {

	/**
	 * The original unit to convert, stripped of all the contexts we're aggregating
	 */
	IUnit nonContextualUnit;

	/**
	 * The unit we represent, which has aggregated context compared to
	 * nonContextualUnit;
	 */
	IUnit unitTo;

	private boolean variesByLocation;

	/**
	 * The conversion factor that accounts for the rescaling
	 */
	double scaleConversionFactor;

	RecontextualizingUnit(Unit unit, IUnit nonContextual, double contextualConversion, boolean variesByLocation) {
		this.unitTo = unit;
		this.nonContextualUnit = nonContextual;
		this.scaleConversionFactor = contextualConversion;
		this.variesByLocation = variesByLocation;
	}

	@Override
	public boolean isCompatible(IValueMediator other) {
		// this is a special-purpose mediator that is only compatible with itself
		return false;
	}

	@Override
	public Number convert(Number d, IValueMediator scale) {
		return unitTo.convert(d, nonContextualUnit).doubleValue() * scaleConversionFactor;
	}

	/**
	 * Return whether this unit varies at each scale subdivision. If true, we must
	 * recompute it, otherwise it can be stored and reused in a scale sweep.
	 */
	public boolean variesByLocation() {
		return variesByLocation;
	}

}
