package org.integratedmodelling.landcover.clue;

import java.util.List;

import org.integratedmodelling.klab.api.data.IResourceCalculator;

import nl.alterra.shared.datakind.Clazz;
import nl.alterra.shared.datakind.DataKind;
import nl.alterra.shared.rasterdata.CellStack;
import nl.wur.iclue.parameter.Landuses;
import nl.wur.iclue.parameter.Landuses.Landuse;
import nl.wur.iclue.suitability.SuitabilityCalculator;
import nl.wur.iclue.parameter.SpatialDataset;

public class KLABSuitabilityCalculator extends SuitabilityCalculator {

	private IResourceCalculator calculator;

	public KLABSuitabilityCalculator(List<DataKind> driverDataKinds, Landuses landuses,
			IResourceCalculator parameters) {
		super(driverDataKinds, landuses);
		this.calculator = parameters;
	}

	@Override
	public double getProbability(Landuse landuseOfInterest, CellStack driverValues) {
		// TODO Auto-generated method stub
		/*
		 * If the landuse suitability comes from a dependency, locate that
		 * otherwise use the calculator at x,y
		 */
		return 0;
	}

	@Override
	public void updateFromBaseline(SpatialDataset baseline, Clazz adminstrativeUnit) {
		// TODO Auto-generated method stub
	}

}
