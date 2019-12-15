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

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.landcover.clue.KlabCLUEParameters;

import nl.alterra.shared.datakind.Category;
import nl.alterra.shared.datakind.Clazz;
import nl.alterra.shared.rasterdata.CellStack;
import nl.alterra.shared.rasterdata.RasterData;
import nl.alterra.shared.rasterdata.RasterDataFactory;
import nl.alterra.shared.rasterdata.RasterDataStack;
import nl.alterra.shared.utils.log.Level;
import nl.alterra.shared.utils.log.Log;
import nl.wur.iclue.model.IterationResultEvaluator.IterationResult;
import nl.wur.iclue.model.demand.DemandFactory;
import nl.wur.iclue.model.demand.DemandValidators;
import nl.wur.iclue.model.ui.LanduseAllocationProgress;
import nl.wur.iclue.parameter.EaseOfChange;
import nl.wur.iclue.parameter.Landuses;
import nl.wur.iclue.parameter.Landuses.Landuse;
import nl.wur.iclue.parameter.ParameterStatus;
import nl.wur.iclue.parameter.Parameters;
import nl.wur.iclue.parameter.SpatialDataset;
import nl.wur.iclue.suitability.SuitabilityCalculator;
import nl.wur.iclue.suitability.SuitabilityFactory;

/**
 * probabilities affecting land use allocation: 1. ease of change - static over
 * time (specific per land use) 2. neighbourhood - static over time (specific
 * per land use) 3. suitability - might change over time (based on parameter
 * maps. specific per land use) 4. demandWeight - changes in iterations and over
 * time (difference available and demand. specific per land use)
 * 
 * conversion restrictions may occur (e.g. forest -> agri only after 5 years)
 * 
 * @author Johnny te Roller, Peter Verweij
 */
public class CLUEModel {

	private IMonitor monitor;
	private LanduseAllocationProgress allocationProgressPanel = null;

	private static final String ERROR_PARAMETERS = "Cannot run model. Parameters are incomplete: "
			+ ParameterStatus.NEW_LINE + "%s";
	private static final String ERROR_CANNOT_ALLOCATE = "Model execution halted in year %d. Cannot allocate demands.";
	private static final String ERROR_SUITABILITIES_UNAVAILABLE = "Cannot run model. Initialize land use suitabilities first";
	private static final String INFO_TOO_FEW_CHANGES = "Too few changes detected. apply stochasticity to suitabilities.";
	private static final String INFO_TOO_MANY_ITERATIONS = "Could not allocate demands. Maximum number of iterations reached.";

	public static final String LOG_TOKEN = "MODEL";
	private static final String LOG_ADMIN_UNIT = "Allocation for administrative unit '%s' started...";
	private static final String LOG_ALLOCATION_STARTED = "Allocation for year %d started...";
	private static final String LOG_DEMAND = "Demands [#cells change]: ";
	private static final String LOG_ITERATION = "Iteration %d '%s': ";

	private SuitabilityCalculator suitabilityCalculator = null;

	protected Parameters params;

	public CLUEModel(Parameters params, IMonitor monitor) {

		if (!params.getCompletenessStatus().isComplete()) {
			String errorMessage = String.format(ERROR_PARAMETERS, params.getCompletenessStatus().getErrorsMessage());
			System.out.println(errorMessage);
			throw new RuntimeException(errorMessage);
		}
		this.params = params;
	}

	/**
	 * land use suitabilities can be determined using a variety of methods, such as:
	 * -stepwise regression (might take some time to determine regression functions)
	 * -bayesian statistics (might take some time to train the probabilities) -...
	 */
	public void initializeSuitabilities() {
		this.suitabilityCalculator = SuitabilityFactory.create(params);
	}

	/**
	 * FV main driver
	 * 
	 * @return
	 */
	public Projections createLanduseProjections() {
		if (suitabilityCalculator == null)
			throw new RuntimeException(ERROR_SUITABILITIES_UNAVAILABLE);

		Map<Clazz, SpatialDataset> landuseProjectionsForAllAdminUnits = new HashMap<>();
		Map<Clazz, SpatialDataset> ageProjectionsForAllAdminUnits = new HashMap<>();

		// bah
//		JFrame frm = new JFrame();
		try {
			allocationProgressPanel = new LanduseAllocationProgress(params.getLanduses());
//			frm.getContentPane().add(allocationProgressPanel);
//			frm.pack();
//			frm.setVisible(true);

			for (Clazz administrativeUnit : params.getAdministrativeUnits().getDatakind().getClasses()) {

				Log.log(Level.INFO, String.format(LOG_ADMIN_UNIT, administrativeUnit.getCaption()), null);

//				LanduseAndAgeDataset projections = LanduseAndAgeDataset.createByAdministrativeBoundaryCut(
//						params.getBaseline(), params.getLanduses(), params.getAdministrativeUnits(),
//						(Category) administrativeUnit);
				LanduseAndAgeDataset projections = LanduseAndAgeDataset.create(params, (Category) administrativeUnit);

				suitabilityCalculator.updateFromBaseline(projections.getLanduseData(), administrativeUnit);

				for (int year = params.getBaseline().getYear() + 1; year <= params.getTargetTime(); year++) {

					Log.log(Level.INFO, String.format(LOG_ALLOCATION_STARTED, year), null);

					// allocate NEW land uses based on land uses in PREVIOUS year
					LanduseRasterData previousLanduseAndAge = projections.createRasterData(year - 1);
					DemandValidators validators = DemandFactory.create(params, administrativeUnit, year);
					logDemands(previousLanduseAndAge.getLanduseMap(), validators);

					LanduseRasterData newLanduseRasterData = allocate(year, previousLanduseAndAge, validators);
					if (newLanduseRasterData == null) {// no new land use map could be allocated
						Log.log(Level.WARNING, String.format(ERROR_CANNOT_ALLOCATE, year), null);
						break;
						// throw new RuntimeException(String.format(ERROR_CANNOT_ALLOCATE, year));
					} else {
						projections.addRasterData(newLanduseRasterData, year);
					}
				}
				landuseProjectionsForAllAdminUnits.put(administrativeUnit, projections.getLanduseData());
				ageProjectionsForAllAdminUnits.put(administrativeUnit, projections.getAgeData());
			}

		} finally {
//			frm.dispose();
		}

		SpatialDataset allLanduseProjections = mergeProjectionsFromAllAdminUnits(landuseProjectionsForAllAdminUnits);
		SpatialDataset allAgeProjections = mergeProjectionsFromAllAdminUnits(ageProjectionsForAllAdminUnits);
		Projections result = new Projections(allLanduseProjections, allAgeProjections);
		return result;
	}

	private LanduseRasterData allocate(int year, LanduseRasterData previousLanduseRasterData,
			DemandValidators demands) {
		RasterData previousLanduseMap = previousLanduseRasterData.getLanduseMap();
		RasterData previousAgeMap = previousLanduseRasterData.getAgeMap();

		// initialise
		IterationResultEvaluator evaluator = new IterationResultEvaluator(demands);
		AllocationHelper helper = new AllocationHelper(params.getLanduses(), params.getDrivers(), suitabilityCalculator,
				params.getConversions());
		helper.prepareTimeStep(year, previousLanduseRasterData, demands);

		//
		RasterData resultingLanduseMap = previousLanduseMap;
		IterationResultEvaluator.IterationResult iterationResult = evaluator.getStatus(resultingLanduseMap);
		RasterDataStack allocationMaps = null;
		if (!IterationResultEvaluator.IterationResult.ALLOCATION_ACCEPTABLE.equals(iterationResult))
			allocationMaps = helper.createAllocationMaps();

		// iterate
		while ((!IterationResultEvaluator.IterationResult.ALLOCATION_ACCEPTABLE.equals(iterationResult))
				&& (!IterationResultEvaluator.IterationResult.MAX_NUMBER_OF_ITERATIONS_REACHED
						.equals(iterationResult))) {
			// create new landcover output
			allocationMaps.clearOutputs();
			resultingLanduseMap = allocationMaps.addOutput(true);
			int LANDUSE_RESULT_INDEX = 0;

			// loop over cells
			Number[] outputCellValues = new Number[allocationMaps.getOutputCount()];
			for (CellStack inputCellStack : allocationMaps.getCellCursor()) {
				if (allocationMaps.containsInputWithNodata(inputCellStack)) {
					outputCellValues = allocationMaps.getOutputNodataValues();
				} else {
					Landuse currentLanduse = helper.getLanduse(inputCellStack);
					int age = IterationHelper.extractAgeCellValue(inputCellStack.inputValues);
					if (EaseOfChange.CANNOT_CHANGE.equals(currentLanduse.getEaseOfChange()))
						outputCellValues[LANDUSE_RESULT_INDEX] = currentLanduse.getCode();
					else {
						Landuse projectedLanduse = helper.getLanduseByProbabilties(inputCellStack);
						if (projectedLanduse == null) // non found, maintain current landuse
							outputCellValues[LANDUSE_RESULT_INDEX] = currentLanduse.getCode();
						else
							outputCellValues[LANDUSE_RESULT_INDEX] = projectedLanduse.getCode();
					}
				}
				allocationMaps.setOutputValues(inputCellStack, outputCellValues);
			}

			evaluator.incrementIterationCounter();
			iterationResult = evaluator.getStatus(resultingLanduseMap);
			logIterationResult(iterationResult, year, resultingLanduseMap, demands, evaluator);

			switch (iterationResult) {
			case ALLOCATION_ACCEPTABLE: {
				break;
			}
			case REQUEST_NEW_ITERATION: {
				helper.prepareForNextIteration(resultingLanduseMap);
				break;
			}
			case DIFFERENCE_TOO_SMALL_IN_MOVING_AVERAGE: {
				Log.log(Level.INFO, INFO_TOO_FEW_CHANGES, null);
				// build new allocation maps
				allocationMaps = helper.createAllocationMapsWithStochasticShock(
						evaluator.getDeviationStatusPerLanduse(resultingLanduseMap)); // apply stochasticity to
																						// suitabilities
				evaluator.resetMovingAverage();
				break;
			}
			case MAX_NUMBER_OF_ITERATIONS_REACHED: {
				Log.log(Level.INFO, INFO_TOO_MANY_ITERATIONS, null);
				resultingLanduseMap = null;
				break;
			}
			}
		}

		RasterData resultingAgeMap = deriveAgeMap(previousAgeMap, previousLanduseMap, resultingLanduseMap);
		return new LanduseRasterData(resultingLanduseMap, resultingAgeMap);
	}

	private RasterData deriveAgeMap(RasterData previousAgeMap, RasterData previousLanduseMap,
			RasterData resultingLanduseMap) {
		RasterDataStack rasterStack = RasterDataFactory.createStack(params);
		rasterStack.addInput(previousAgeMap);
		int INDEX_PREVIOUS_AGE = 0;
		rasterStack.addInput(previousLanduseMap);
		int INDEX_PREVIOUS_LANDUSE = 1;
		rasterStack.addInput(resultingLanduseMap);
		int INDEX_RESULTING_LANDUSE = 2;
		RasterData result = rasterStack.addOutput(true);
		for (CellStack cellStack : rasterStack.getCellCursor()) {
			if (rasterStack.containsInputWithNodata(cellStack))
				rasterStack.setOutputValues(cellStack, rasterStack.getOutputNodataValues());
			else if (cellStack.inputValues[INDEX_PREVIOUS_LANDUSE]
					.intValue() != cellStack.inputValues[INDEX_RESULTING_LANDUSE].intValue())
				rasterStack.setOutputValues(cellStack, new Number[] { 1 });
			else
				rasterStack.setOutputValues(cellStack,
						new Number[] { cellStack.inputValues[INDEX_PREVIOUS_AGE].intValue() + 1 });
		}
		return result;
	}

	private void logDemands(RasterData previousLanduseMap, DemandValidators validators) {
		String msg = LOG_DEMAND;
		Map<Integer, Integer> vat = previousLanduseMap.createValueCountTable();
		for (Clazz lu : validators.getLanduses()) {
			msg += String.format("%s(%d) ", lu.getCaption(), validators.getDemand(lu));
		}
		Log.log(Level.INFO, msg, null);
	}

	private void logIterationResult(IterationResult iterationResult, long year, RasterData landuseMapFromIteration,
			DemandValidators validators, IterationResultEvaluator evaluator) {
		boolean iterationIsSuccessfull = IterationResult.ALLOCATION_ACCEPTABLE.equals(iterationResult);
		if (iterationIsSuccessfull) {
			allocationProgressPanel.clearSeries(year);

			int iterationCount = evaluator.getIterationCount();
			int shockCount = evaluator.getMovingAverageResetCount();
			if (shockCount > 0)
				Log.log(Level.INFO, String.format("year %d, #iterations %3d (%d)", year, iterationCount, shockCount),
						null, LOG_TOKEN);
			else
				Log.log(Level.INFO, String.format("year %d, #iterations %3d", year, iterationCount), null, LOG_TOKEN);
		} else {
			allocationProgressPanel.addIteration(year, evaluator.getIterationCount(), landuseMapFromIteration,
					validators);
		}
	}

	private SpatialDataset mergeProjectionsFromAllAdminUnits(Map<Clazz, SpatialDataset> projectionsForAllAdminUnits) {
		if (projectionsForAllAdminUnits.size() != 1)
			throw new UnsupportedOperationException("Cannot merge projections for all admin units. Not supported yet");
		return projectionsForAllAdminUnits.values().iterator().next();
	}

	private static class LanduseAndAgeDataset {

		private SpatialDataset landuseData = null;
		private SpatialDataset ageData = null;

		LanduseAndAgeDataset() {
		}

		public LanduseAndAgeDataset(SpatialDataset landuseBaseline, Landuses landuses) {
			this.landuseData = landuseBaseline;
			int year = landuseBaseline.getYear();

			RasterData ageMap = LanduseRasterData.createInitialAgeMap(landuseBaseline.getRasterData(), landuses);
			this.ageData = new SpatialDataset();
			this.ageData.setCaption("Age");
			this.ageData.add(ageMap, year);
		}

		/*
		 * discontinued in favor of more generic next. Does the same if parameters are
		 * standard CLUE.
		 */
		public static LanduseAndAgeDataset createByAdministrativeBoundaryCut(SpatialDataset landuseBaseline,
				Landuses landuses, SpatialDataset administrativeUnits, Category administrativeUnitOfInterest) {
			return new LanduseAndAgeDataset(landuseBaseline.cut(administrativeUnits, administrativeUnitOfInterest),
					landuses);
		}

		public static LanduseAndAgeDataset create(Parameters parameters, Category administrativeUnitOfInterest) {

			if (parameters instanceof KlabCLUEParameters) {

				/*
				 * create outputs - for the age layer, use the current state from the context
				 * (or create if not there) and ensure the baseline points at the previous. Age
				 * should remain stored.
				 */
				LanduseAndAgeDataset ret = new LanduseAndAgeDataset();
				ret.landuseData = parameters.getBaseline().cut(parameters.getAdministrativeUnits(),
						administrativeUnitOfInterest);
				ret.ageData = ((KlabCLUEParameters) parameters).getAgeDataset();

				return ret;
			}

			return new LanduseAndAgeDataset(
					parameters.getBaseline().cut(parameters.getAdministrativeUnits(), administrativeUnitOfInterest),
					parameters.getLanduses());
		}

		public LanduseRasterData createRasterData(int year) {
			RasterData landuse = getLanduseData().getRasterData(year);
			RasterData age = getAgeData().getRasterData(year);
			return new LanduseRasterData(landuse, age);
		}

		public void addRasterData(LanduseRasterData landuseAndAge, int year) {
			getLanduseData().add(landuseAndAge.getLanduseMap(), year);
			getAgeData().add(landuseAndAge.getAgeMap(), year);
		}

		private SpatialDataset getLanduseData() {
			return landuseData;
		}

		private SpatialDataset getAgeData() {
			return ageData;
		}

	}

	public class Projections {
		private final SpatialDataset landuseProjections;
		private final SpatialDataset ageProjections;

		public Projections(SpatialDataset landuseProjections, SpatialDataset ageProjections) {
			this.landuseProjections = landuseProjections;
			this.ageProjections = ageProjections;
		}

		public RasterData getLanduseProjection(int year) {
			return landuseProjections.getRasterData(year);
		}

		public RasterData getAgeProjection(int year) {
			return ageProjections.getRasterData(year);
		}

		public long getFirstYear() {
			return landuseProjections.getFirstYear();
		}

		public long getLastYear() {
			return landuseProjections.getLastYear();
		}
	}
}
