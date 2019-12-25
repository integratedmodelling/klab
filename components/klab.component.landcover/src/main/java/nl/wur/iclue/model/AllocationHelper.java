/*
 * Copyright 2014 Alterra, Wageningen UR
 * 
 * Licensed under the EUPL, Version 1.1 or – as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the
 * Licence.
 * You may obtain a copy of the Licence at:
 * 
 * http://ec.europa.eu/idabc/eupl5
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 */

package nl.wur.iclue.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.landcover.clue.KlabCLUEParameters;

import nl.alterra.shared.datakind.Clazz;
import nl.alterra.shared.rasterdata.CellStack;
import nl.alterra.shared.rasterdata.RasterData;
import nl.alterra.shared.rasterdata.RasterDataFactory;
import nl.alterra.shared.rasterdata.RasterDataStack;
import nl.wur.iclue.model.demand.DemandValidator.DeviationStatus;
import nl.wur.iclue.parameter.Landuses;
import nl.wur.iclue.parameter.Landuses.Landuse;
import nl.wur.iclue.parameter.Parameters;
import nl.wur.iclue.parameter.SpatialDataset;
import nl.wur.iclue.parameter.conversion.Always;
import nl.wur.iclue.parameter.conversion.Conversion;
import nl.wur.iclue.parameter.conversion.RestrictedAreas;
import nl.wur.iclue.suitability.SuitabilityCalculator;

/**
 *
 * @author Peter Verweij
 */
public class AllocationHelper extends IterationHelper {
	private final Map<Integer, Map<Landuse, Conversion>> conversions; // <fromLanduseCode, <toLanduse, conversion>>

	public AllocationHelper(Parameters parameters, SuitabilityCalculator suitabilityCalculator) {
		this(parameters.getLanduses(), parameters.getDrivers(), suitabilityCalculator, parameters.getConversions());
		
		if (parameters instanceof KlabCLUEParameters) {
			// TODO recover context
			// not TODO nah
		}
	}

	public AllocationHelper(Landuses landuses, List<SpatialDataset> drivers,
			SuitabilityCalculator suitabilityCalculator, List<Conversion> conversions) {
		super(landuses, drivers, suitabilityCalculator);

		this.conversions = new HashMap<>();
		for (Landuse from : landuses) {
			Map<Landuse, Conversion> toMap = new HashMap<>();
			this.conversions.put(from.getCode(), toMap);
			for (Landuse to : landuses)
				toMap.put(to, findConversionByLanduse(from, to, conversions));
		}
	}

	private Conversion findConversionByLanduse(Landuse from, Landuse to, List<Conversion> conversions) {
		for (Conversion c : conversions)
			if ((c.getFromLanduse().equals(from)) && (c.getToLanduse().equals(to)))
				return c;
		return createDefaultConversion(from, to);
	}

	private Conversion createDefaultConversion(Landuse from, Landuse to) {
		Conversion result = new Conversion();
		result.setRule(new Always());
		result.setFromLanduse(from);
		result.setToLanduse(to);
		return result;
	}

	public RasterDataStack createAllocationMaps(CLUEModel model) {
		return createAllocationMapsWithStochasticShock(null, model);
	}

	public RasterDataStack createAllocationMapsWithStochasticShock(
			Map<Clazz, DeviationStatus> landusesRequiringStochasticShock, CLUEModel model) {
		RasterDataStack result = RasterDataFactory.createStack(model);
		result.addInput(landuseRasterData.getLanduseMap()); // index = 0
		result.addInput(landuseRasterData.getAgeMap()); // index = 1

		Map<Landuses.Landuse, RasterData> probabilityMaps = super.createProbabilityMaps(
				landusesRequiringStochasticShock, model);
		for (Landuse lu : allocatableLanduses)
			result.addInput(probabilityMaps.get(lu));

		return result;
	}

	public Landuse getLanduse(CellStack allocationCellValues) {
		return landuses.findByValue(extractLanduseCellValue(allocationCellValues.inputValues));
	}

	public Landuse getLanduseByProbabilties(CellStack allocationCellValues) {
		List<Landuse> possibleTargetLanduses = createPossibleTargetLanduses(allocationCellValues);
		CellStack probabilityCellValues = removeLanduseCellValue(allocationCellValues,
				CellValueRemoval.LANDUSE_AND_AGE);
		return super.getLanduseByProbabilties(probabilityCellValues, possibleTargetLanduses);
	}

	private List<Landuse> createPossibleTargetLanduses(CellStack allocationCellValues) {
		List<Landuse> result = new ArrayList<>();

		int landuseCode = extractLanduseCellValue(allocationCellValues.inputValues);
		Map<Landuse, Conversion> conversionsToOtherLanduses = conversions.get(landuseCode);
		int age = extractAgeCellValue(allocationCellValues.inputValues);

		for (Landuse landuse : allocatableLanduses) {
			Conversion conversion = conversionsToOtherLanduses.get(landuse);
			if (conversion.getRule() instanceof RestrictedAreas)
				((RestrictedAreas) conversion.getRule()).setLocation(allocationCellValues);
			if (conversion.canConvert(age))
				result.add(landuse);
		}
		return result;
	}
}
