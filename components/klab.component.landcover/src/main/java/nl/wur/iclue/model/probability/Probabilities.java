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


package nl.wur.iclue.model.probability;

import nl.wur.iclue.model.demand.DemandWeights;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.wur.iclue.parameter.EaseOfChange;
import nl.wur.iclue.parameter.Landuses;
import nl.wur.iclue.parameter.Landuses.Landuse;
import nl.wur.iclue.parameter.SpatialDataset;
import nl.wur.iclue.suitability.SuitabilityCalculator;
import nl.alterra.shared.rasterdata.CellStack;
import nl.alterra.shared.rasterdata.RasterData;
import nl.alterra.shared.rasterdata.RasterDataFactory;
import nl.alterra.shared.rasterdata.RasterDataStack;
import nl.alterra.shared.utils.log.Level;
import nl.alterra.shared.utils.log.Log;

/**
 * Algorithms for creating probabilities for each land use and drivers
 * 
 * @author Peter Verweij
 */
public class Probabilities {
    private static final String ERROR_UNKNOWN_CELL_REMOVAL_OPTION = "Cannot remove landuse from cell value stack for unknown removal type";
    private static final String LOG_PROBABILITYMAP                = "Probability map created for '%s'. Stored in: %s";

    private static final ProbabilityCompositeCalculator probabilityCompositeCalculator = ProbabilityCompositeMethod.INCLUDE_EASEOFCHANGE_ONLY_FOR_CURRENT_LANDUSE;
    
    protected final Landuses landuses;
    private final List<SpatialDataset> drivers;
    private final SuitabilityCalculator suitabilityCalculator;
    public static final String LOG_TOKEN = "PROBABILITIES";
    private static boolean logHeaderWritten = false;

    public Probabilities(Landuses landuses, List<SpatialDataset> drivers, SuitabilityCalculator suitabilityCalculator) {
        this.landuses = landuses;
        this.drivers = drivers;
        this.suitabilityCalculator = suitabilityCalculator;
        
        if (!logHeaderWritten) {
            String logHeader = "AdministrativeUnit, Year";
            for (int index = 0; index < landuses.size(); index++) {
                if (landuses.get(index).getEaseOfChange() != EaseOfChange.CANNOT_CHANGE) 
                    logHeader += String.format(",%s", landuses.get(index).getCaption());
            }
            Log.log(Level.INFO, logHeader, null, LOG_TOKEN);
            
            logHeaderWritten = true;
        }
    }
    
    /**
     * 
     * @param targetYear
     * @param previousLanduseMap
     * @param demands list of land uses with demands in cell count
     * @return 
     */
    protected Map<Landuses.Landuse, RasterData> createProbabilityMaps(int targetYear, RasterData previousLanduseMap, Map<Landuses.Landuse, Integer> demands) {
        Map<Landuses.Landuse, RasterData> result = new HashMap<>();

        Map<Landuses.Landuse, Double> demandWeights = DemandWeights.create(previousLanduseMap, demands);
        logDemandWeights(demandWeights, targetYear);
                
        LanduseProbabilities landuseProbabilitiesOfCell = new LanduseProbabilities(landuses, false); // 'false' to excludeLandusesThatCannotChange
        List<Landuses.Landuse> orderedLanduseList = landuseProbabilitiesOfCell.getLanduses();

        // prepare map stack
        RasterDataStack mapStack = RasterDataFactory.createStack();
        mapStack.addInput(previousLanduseMap);
        for (SpatialDataset driver: drivers)
            mapStack.addInput(driver.getMostRecentRasterData(targetYear));
        for (Landuses.Landuse lu: orderedLanduseList) {
            RasterData luProbabilityMap = mapStack.addOutput(false);
            result.put(lu, luProbabilityMap); 
        }
        
        // loop over cells;
        Number[] outputCellValues = new Number[orderedLanduseList.size()];
        for (CellStack inputCellStack: mapStack.getCellCursor()) {
            if (mapStack.containsInputWithNodata(inputCellStack)) {
                outputCellValues = mapStack.getOutputNodataValues();
            } else {
                Landuses.Landuse currentLanduse = landuses.findByValue(extractLanduseCellValue(inputCellStack.inputValues));
                determineProbabilitiesForAllLandusesForCell(inputCellStack, landuseProbabilitiesOfCell, demandWeights);
                for (int index=0; index<orderedLanduseList.size();index++) {
                    Landuses.Landuse targetLanduse = orderedLanduseList.get(index);
                    double probability = landuseProbabilitiesOfCell.getProbability(currentLanduse, targetLanduse, getProbabilityCalculator());
                    outputCellValues[index] = probability;
                }
            }
            mapStack.setOutputValues(inputCellStack, outputCellValues);
        }
        
        logProbabilityMaps(result);
        return result;
    }
    
    protected ProbabilityCompositeCalculator getProbabilityCalculator() {
        return probabilityCompositeCalculator;
    }
    
    
    private void determineProbabilitiesForAllLandusesForCell(CellStack cellStack, LanduseProbabilities targetProbabilities, Map<Landuses.Landuse, Double> demandWeights) {
        CellStack driverCellStack = removeLanduseCellValue(cellStack, CellValueRemoval.LANDUSE_ONLY);
        for (Landuses.Landuse landuse: targetProbabilities.getLanduses()) {
            LanduseProbability lup = targetProbabilities.get(landuse);
            // ease of change is derived from landuse which has been set during creation and is static for all cells of a given land use 
            lup.setSuitability(getSuitability(landuse, driverCellStack));
            lup.setNeighbourhood(getNeighbourhoodWeight(landuse));
            lup.setDemandWeight(demandWeights.get(landuse));
        }
    }
    
    
    private double getSuitability(Landuse landuseOfInterest, CellStack parameterCellStack) {
        return suitabilityCalculator.getProbability(landuseOfInterest, parameterCellStack);
    }
  
    private double getNeighbourhoodWeight(Landuse landuse) {
        return 0.0; //throw new UnsupportedOperationException("Not supported yet.");
        // change weight based on surrounding regions (not cells!) which are updated during iterations (landuse changes)
    }
    
    /**
     * ASSUMPTION: land use is the first- and age the second element in the cell stack
     * remove first and second element 
     * @param cellStack
     * @return 
     */
    protected static CellStack removeLanduseCellValue(CellStack cellStack, CellValueRemoval removalOption) {
        int removeNumberOfCellValues;
        switch (removalOption) {
            case LANDUSE_ONLY: {
                removeNumberOfCellValues = 1;
                break;
            }
            case LANDUSE_AND_AGE: {
                removeNumberOfCellValues = 2;
                break;
            }
            default: 
                throw new RuntimeException(ERROR_UNKNOWN_CELL_REMOVAL_OPTION);
        }
        Number[] otherCellValues = new Number[cellStack.inputValues.length-removeNumberOfCellValues];
        System.arraycopy(cellStack.inputValues, removeNumberOfCellValues, otherCellValues, 0, otherCellValues.length);
        
        CellStack result = new CellStack(otherCellValues.length);
        result.setColumnIndex(cellStack.getColumnIndex());
        result.setColumnIndex(cellStack.getRowIndex());
        result.inputValues = otherCellValues;
        return result;
    }
    
    /**
     * ASSUMPTION: land use is the first element in the cell stack
     * extract first element 
     * @param cellStackValues
     * @return 
     */
    protected static Integer extractLanduseCellValue(Number[] cellStackValues) {
        int INDEX_LANDUSE_CODE = 0;
        return cellStackValues[INDEX_LANDUSE_CODE].intValue();
    }
    
    private static void logProbabilityMaps(Map<Landuse, RasterData> probabilityMaps) {
//        for (Landuse lu: probabilityMaps.keySet())
//            Log.log(Level.INFO, String.format(LOG_PROBABILITYMAP, lu.getCaption(), probabilityMaps.get(lu).getDataDefinition()), null);
    }

    private void logDemandWeights(Map<Landuse, Double> demandWeights, long targetYear) {
        String DEMANDWEIGHT_DOES_NOT_EXIST = "none";
        String msg = String.format("[TODO], %d", targetYear);
        for (int index = 0; index < landuses.size(); index++) {
            Landuse lu = landuses.get(index);
            if (lu.getEaseOfChange() != EaseOfChange.CANNOT_CHANGE) {
                if (demandWeights.containsKey(lu))
                    msg += String.format(",%5.4f", demandWeights.get(lu));
                else
                    msg += String.format(",%s", DEMANDWEIGHT_DOES_NOT_EXIST);
            }
        }
        Log.log(Level.INFO, msg, null, LOG_TOKEN);
    }

    
    
    public enum CellValueRemoval {
        LANDUSE_ONLY,
        LANDUSE_AND_AGE;
    }
}
