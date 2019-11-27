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

import nl.wur.iclue.model.demand.DemandValidators;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import nl.wur.iclue.model.demand.DemandValidator.DeviationStatus;
import nl.alterra.shared.datakind.Clazz;
import nl.alterra.shared.rasterdata.RasterData;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

/**
 *
 * @author Peter Verweij, Johnny te Roller
 */
public class IterationResultEvaluator {
    private static final int MAX_NUMBER_OF_ITERATIONS = 2000;
    private int iterationCount = 0;
    private int movingAverageResetCount = 0;
    private final DemandValidators validators;
    private final MovingAverage movingAverage;

    public IterationResultEvaluator(DemandValidators validators) {
        this.validators = validators;
        
        int[] landuseCodes = new int[validators.getLanduses().size()];
        int landuseIndex = 0;
        for (Clazz landuse: validators.getLanduses()) 
            landuseCodes[landuseIndex++] = Integer.parseInt(landuse.getValueAsString());
        movingAverage = new MovingAverage(landuseCodes);
        
    }
    
    public void resetMovingAverage() {
        movingAverage.reset();
        movingAverageResetCount++;
    }
    
    public Map<Clazz, DeviationStatus> getDeviationStatusPerLanduse(RasterData landuseMapCreatedDuringIteration) {
        Map<Integer, Integer> areaPerLanduse = landuseMapCreatedDuringIteration.createValueCountTable();
        
        Map<Clazz, DeviationStatus> result = new HashMap<>();
        
        for (Clazz landuse: validators.getLanduses()) {
            int allocatedAmount = 0;
            int landuseCode = Integer.parseInt(landuse.getValueAsString());
            if (areaPerLanduse.containsKey(landuseCode))
                allocatedAmount = areaPerLanduse.get(landuseCode);
            
            result.put(landuse, validators.getDeviationStatus(landuse, allocatedAmount));
        }
        
        return result;
    }
    
    
    public IterationResult getStatus(RasterData landuseMapCreatedDuringIteration) {
        Map<Integer, Integer> newLanduseDistribution = landuseMapCreatedDuringIteration.createValueCountTable();
        
        if (isAllocatedLandUseAcceptable(newLanduseDistribution, validators))
            return IterationResult.ALLOCATION_ACCEPTABLE;
        else if (iterationCount > MAX_NUMBER_OF_ITERATIONS)
            return IterationResult.MAX_NUMBER_OF_ITERATIONS_REACHED;
        else if (movingAverage.tooFewChanges(newLanduseDistribution))
            return IterationResult.DIFFERENCE_TOO_SMALL_IN_MOVING_AVERAGE;
        else
            return IterationResult.REQUEST_NEW_ITERATION;
    }

    public int getIterationCount() {
        return iterationCount;
    }

    public int getMovingAverageResetCount() {
        return movingAverageResetCount;
    }
    
    
    private boolean isAllocatedLandUseAcceptable(Map<Integer, Integer> areaPerLanduse, DemandValidators validators) {
        for (Clazz landuse: validators.getLanduses()) {
            int allocatedAmount = 0;
            int landuseCode = Integer.parseInt(landuse.getValueAsString());
            if (areaPerLanduse.containsKey(landuseCode))
                allocatedAmount = areaPerLanduse.get(landuseCode);
            if (!validators.isValid(landuse, allocatedAmount))
                return false;
        }
        return true;
    }

    public void incrementIterationCounter() {
        iterationCount++;
    }
    
    private class MovingAverage {
        private final int REQUIRED_NUMBER_OF_VALUES = 10;
        private final double MIN_MEAN_DEVIATION = .05;
        private final Map<Integer, DescriptiveStatistics> landuseStats;

        public MovingAverage(int[] landusesOfInterest) {
            landuseStats = new HashMap<>();
            for (int landuseCode: landusesOfInterest)
                landuseStats.put(landuseCode, new DescriptiveStatistics(REQUIRED_NUMBER_OF_VALUES));
        }
        
        public void reset() {
            for (Integer landuseCode: landuseStats.keySet())
                landuseStats.put(landuseCode, new DescriptiveStatistics(REQUIRED_NUMBER_OF_VALUES));
        }

        /**
         * compares average changes of land use over several iterations
         * @param landuseDistribution <landuse code, cell count>
         * @return true, if found to be stable (or changing too little)
         */
        public boolean tooFewChanges(Map<Integer, Integer> landuseDistribution) {
            boolean tooFew = true;
            
            for (Entry<Integer, DescriptiveStatistics> entry: landuseStats.entrySet()) {
                int landuseOfInterest = entry.getKey();
                DescriptiveStatistics stats = entry.getValue();
                
                Integer actualDistribution = landuseDistribution.get(landuseOfInterest);
                if (actualDistribution == null)
                    actualDistribution = 0; // assume it 0 if is not present

                double previousMean = stats.getMean();
                stats.addValue(actualDistribution);
                double newMean = stats.getMean();
                if ((newMean > 0) && (Math.abs((previousMean - newMean)/newMean) > MIN_MEAN_DEVIATION)) 
                    tooFew = false;
            }
            
            Iterator<DescriptiveStatistics> iter = landuseStats.values().iterator();
            if ((!iter.hasNext()) || (iter.next().getN() < REQUIRED_NUMBER_OF_VALUES))
                tooFew = false;
            
            return tooFew;
        }
    }
    
    
    
    public static enum IterationResult {
        ALLOCATION_ACCEPTABLE,
        REQUEST_NEW_ITERATION,
        MAX_NUMBER_OF_ITERATIONS_REACHED,
        DIFFERENCE_TOO_SMALL_IN_MOVING_AVERAGE,
    }
}

