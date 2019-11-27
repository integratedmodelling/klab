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

import nl.wur.iclue.model.probability.LanduseProbabilities;
import nl.wur.iclue.model.probability.Probabilities;
import nl.wur.iclue.model.demand.DemandValidators;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.wur.iclue.model.demand.DemandValidator.DeviationStatus;
import nl.wur.iclue.model.probability.LanduseProbability;
import nl.wur.iclue.model.probability.ProbabilityCompositeCalculator;
import nl.wur.iclue.parameter.Landuses;
import nl.wur.iclue.parameter.Landuses.Landuse;
import nl.wur.iclue.parameter.SpatialDataset;
import nl.wur.iclue.suitability.SuitabilityCalculator;
import nl.alterra.shared.datakind.Category;
import nl.alterra.shared.datakind.Clazz;
import nl.alterra.shared.rasterdata.CellStack;
import nl.alterra.shared.rasterdata.RasterData;

/**
 *
 * @author Peter Verweij
 */
public class IterationHelper extends Probabilities{
    private static final String ERROR_CANNOT_SHIFT_DUETO_UNKNOWN_LANDUSE = "Cannot shift probabilities for unknown landuse: %s. Known are: {%s}";
    private static final double DEFAULT_NO_SHIFT = 0.0;
    private static final double SHIFT_STEP_SIZE = 0.001;
    public static Map<Landuses.Landuse, Double> probabilityShifts = null;
    
    private int year;
    protected LanduseRasterData landuseRasterData;
    private DemandValidators demands;
    private Map<Landuses.Landuse, Double> demandRatioTable;
    private Map<Clazz, DeviationStatus> landusesRequiringStochasticShock;
    
    protected List<Landuse> allocatableLanduses;
    private Map<Landuses.Landuse, RasterData> probabilityMaps;
    

    public IterationHelper(Landuses landuses, List<SpatialDataset> drivers, SuitabilityCalculator suitabilityCalculator) {
        super(landuses, drivers, suitabilityCalculator);
    }
    
    public void prepareTimeStep(int year, LanduseRasterData landuseRasterData, DemandValidators demands) {
        this.year = year;
        this.landuseRasterData = landuseRasterData;
        this.demands = demands;
        this.demandRatioTable = createRatioTable(demands);
    }

    protected Map<Landuses.Landuse, RasterData> createProbabilityMaps(Map<Clazz, DeviationStatus> landusesRequiringStochasticShock) {
        this.landusesRequiringStochasticShock = landusesRequiringStochasticShock;
        
        allocatableLanduses = new ArrayList<>();
        for (Landuse lu: landuses)
            if (lu.canChange())
                allocatableLanduses.add(lu);

        // initialize only the first time, afterwards keep values used for previous year.
        if (probabilityShifts == null) {
            probabilityShifts = new HashMap<>();
            for (Landuses.Landuse lu: allocatableLanduses) {
                probabilityShifts.put(lu, DEFAULT_NO_SHIFT);
            }     
        }
        
        probabilityMaps = super.createProbabilityMaps(year, landuseRasterData.getLanduseMap(), getDemands(demands)); 
        
        return probabilityMaps;
    }
    
    /**
     * extract demand for each land use (in cell count) from the validators
     * @param validators
     * @return 
     */
    private Map<Landuses.Landuse, Integer> getDemands(DemandValidators validators) {
        Map<Landuses.Landuse, Integer> result = new HashMap<>();
        for (Landuses.Landuse landuse: landuses) {
            Category landuseCategory = landuse.getCategory();
            if (validators.getLanduses().contains(landuseCategory)) {
                result.put(landuse, validators.getDemand(landuseCategory));
            }
        }
        return result;
    }
     
    
    
    private Map<Landuses.Landuse, Double> createRatioTable(DemandValidators validators) {
        Map<Landuses.Landuse, Double> result = new HashMap<>();
        int totalDemand = validators.getTotalDemand();
        
        for (Landuses.Landuse lu: landuses)
            if (validators.getLanduses().contains(lu.getCategory())) {
                result.put(lu, (double)validators.getDemand(lu.getCategory()) / (double)totalDemand);
            }
            
        return result;
    }
    
    private Map<Landuses.Landuse, Double> createRatioTable(RasterData landuseMap) {
        Map<Landuses.Landuse, Double> result = new HashMap<>();
        
        Map<Integer, Integer> vat = landuseMap.createValueCountTable();
        int cellCount = RasterData.getCellCount(vat);
        
        for (Landuses.Landuse lu: landuses) {
            if (vat.containsKey(lu.getCode()))
                result.put(lu, (double)vat.get(lu.getCode()) / (double)cellCount);
            else
                result.put(lu, 0.0); // landuse is not available in map
        }
        
        return result;
    }
    
    /**
     * prepare doing a next iteration by client calling 'getLanduse()' on the cellStack returned via a call to 'createAllocationMaps'
     * @param landuseMap 
     */
    public void prepareForNextIteration(RasterData landuseMap) {
        shiftProbabilities(landuseMap);
    }
    
    private void shiftProbabilities(RasterData landuseMap) {
        Map<Landuses.Landuse, Double> landuseRatioTable = createRatioTable(landuseMap);
        for (Map.Entry<Landuses.Landuse, Double> entry : probabilityShifts.entrySet()) {
            Landuses.Landuse landuse = entry.getKey();
            
            if (!demandRatioTable.containsKey(landuse)) 
                continue;
                
            Double shiftValue = entry.getValue();
            Double allocatedDemandRatio = 0.0;
            if (demandRatioTable.get(landuse) > 0.0) {
                allocatedDemandRatio = (demandRatioTable.get(landuse) - landuseRatioTable.get(landuse)) / demandRatioTable.get(landuse);
            }
            
            // shift max 0.05
            double stepSize = Math.min(allocatedDemandRatio * 100 * SHIFT_STEP_SIZE, 0.05);
            probabilityShifts.put(landuse, shiftValue + stepSize);
        }
    }
    
    protected Landuses.Landuse getLanduseByProbabilties(CellStack probabilityCellValues, List<Landuse> possibleTargetLanduses) {
        return getHighestFromShiftedProbability(probabilityCellValues, possibleTargetLanduses);
    }
    
    private Landuses.Landuse getHighestFromShiftedProbability(CellStack probabilityCellValues, List<Landuse> possibleTargetLanduses) {
        Landuses.Landuse result = null;
        double maxProbability = -1;
        for (int i=0; i< allocatableLanduses.size(); i++) {
            Landuses.Landuse lu = allocatableLanduses.get(i);
            if (possibleTargetLanduses.contains(lu)) {
                double luProbability = probabilityCellValues.inputValues[i].doubleValue();
                if (!probabilityShifts.containsKey(lu)) {
                    String availableLanduses = "";
                    for (Landuse landuse: probabilityShifts.keySet())
                        availableLanduses += landuse.getCaption() +", ";
                    throw new RuntimeException(String.format(ERROR_CANNOT_SHIFT_DUETO_UNKNOWN_LANDUSE, lu.getCaption(), availableLanduses));
                }
                luProbability += probabilityShifts.get(lu); 
                // add random factor of max 5% to give each cell a bit of a different probability and break potential flip-flopping
                luProbability +=  (nextRandom() - 0.5) * 0.05 * luProbability; 

                if (luProbability > maxProbability) {
                    result = lu;
                    maxProbability = luProbability;
                }
            }
        }
        
        return result;
    }

    /**
     * This probability calculator can apply a stochastic shock
     * @return 
     */
    @Override
    protected ProbabilityCompositeCalculator getProbabilityCalculator() {
        ProbabilityCompositeCalculator inheritedCalculator = super.getProbabilityCalculator();
        
        return (Landuse current, LanduseProbability lup) -> {
            LanduseProbability clonedLanduseProbability = LanduseProbability.copy(lup);
            // apply stochastic shock on suitability, if necessary
            if ((landusesRequiringStochasticShock != null) && (landusesRequiringStochasticShock.get(current.getCategory()) != null)) {
                double randomFactor = 0.02 + (nextRandom() * 0.05); // max 7% correction
                double correctionFactor = 1.0;
                switch (landusesRequiringStochasticShock.get(current.getCategory())) {
                    case TOO_FEW:{
                        correctionFactor += randomFactor;
                        break;
                    }
                    case TOO_MANY:{
                        correctionFactor -= randomFactor;
                    }
                    default: {
                        // void, assume no shock is required as demand=allocated
                    }
                }
                
                clonedLanduseProbability.setSuitability(Math.min(LanduseProbability.MAX_SUITABILITY_VALUE, lup.getSuitability() * correctionFactor));
            }
            
            // use super composite calculator with adapted suitability value
            return inheritedCalculator.getCompositeValue(current, clonedLanduseProbability);
        };
    }
    
    /**
     * 
     * @return random function between 0.0 and 1.0
     */
    private double nextRandom() {
//        org.apache.commons.math3.random.MersenneTwister gen = new org.apache.commons.math3.random.MersenneTwister();
//        return gen.nextDouble();  // REALLY SLOW!
        return Math.random(); // TODO: APACHE RANDOM!!!!  NOT MATH!!!!
    }
    
//    @Override
//    protected double adaptProbability(double probability, Landuse target) {
//        // shift probabilities
//        if (!probabilityShifts.containsKey(target))
//            throw new RuntimeException(String.format(ERROR_CANNOT_SHIFT_DUETO_UNKNOWN_LANDUSE, target.getCaption()));
//        double result = probability + probabilityShifts.get(target); 
//        
//        // apply stochastic shock, if necessary
//        if ((landusesRequiringStochasticShock != null) && (landusesRequiringStochasticShock.get(target.getCategory()) != null)) {
//            switch (landusesRequiringStochasticShock.get(target.getCategory())) {
//                case TOO_FEW:{
//                    
//                }
//                case TOO_MANY:{
//                    
//                }
//                default: {
//                    // void
//                }
//            }
//        }
//                
//        
//        return result;
//    }
    
    /**
     * TODO: FUNCTION HAS NOT BEEN USED FOR A LONG TIME; CHECK CORRECTNESS BEFORE CALLING
     * @param probabilityCellValues
     * @param canConvertToLanduseInfo
     * @return 
     */
    private Landuses.Landuse getSampleFromShiftedProbability(CellStack probabilityCellValues, List<Landuse> possibleTargetLanduses) {
        Map<Landuse, Double> landuseProbabilities = new HashMap<>();
        for (int i=0; i< allocatableLanduses.size(); i++) {
            Landuses.Landuse lu = allocatableLanduses.get(i);
            if (possibleTargetLanduses.contains(lu)) {
                double probability = probabilityCellValues.inputValues[i].doubleValue();
                probability += probabilityShifts.get(lu); 
                if (probability < 0)
                    probability = 0;
                if (probability > 1)
                    probability = 1;
                landuseProbabilities.put(lu, probability);
            }
        }
        return LanduseProbabilities.getSampleOfProbabilityDistribution(landuseProbabilities);
    }

    /**
     * ASSUMPTION: age is the second element in the cell stack
     * extract second element 
     * @param cellStackValues
     * @return 
     */
    public static Integer extractAgeCellValue(Number[] cellStackValues) {
        int INDEX_LANDUSE_AGE = 1;
        return cellStackValues[INDEX_LANDUSE_AGE].intValue();
    }
}
