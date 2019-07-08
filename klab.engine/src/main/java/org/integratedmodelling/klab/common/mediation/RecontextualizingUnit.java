package org.integratedmodelling.klab.common.mediation;

import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.klab.api.data.mediation.IUnit;

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
	
	/**
	 * The conversion factor that accounts for the rescaling
	 */
	double scaleConversionFactor;
	
	RecontextualizingUnit(Unit unit, IUnit nonContextual, double contextualConversion) {
		this.unitTo = unit;
		this.nonContextualUnit = nonContextual;
		this.scaleConversionFactor = contextualConversion;
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


}
