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

package nl.wur.iclue.model.demand;

import nl.wur.iclue.model.demand.DemandValidator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import nl.wur.iclue.model.demand.DemandValidator.DeviationStatus;
import nl.alterra.shared.datakind.Category;
import nl.alterra.shared.datakind.Clazz;

/**
 *
 * @author Johnny te Roller, Peter Verweij
 */
public class DemandValidators {
    private final String ERROR_LANDUSE_NOT_AVAILABLE = "Cannot validate the allocated amount. No validator registered for landuse: %s";
    private final Map<Clazz, DemandValidator> validators;

    public DemandValidators() {
        validators = new HashMap<>();
    }
    
    public Set<Clazz> getLanduses() {
        return validators.keySet();
    }
    
    public void add(Clazz landuse, DemandValidator validator) {
        validators.put(landuse, validator);
    }
    
    public boolean isValid(Clazz landuse, int allocatedAmount) {
        DemandValidator validator = getValidator(landuse);
        if (validator == null)
            throw new RuntimeException(String.format(ERROR_LANDUSE_NOT_AVAILABLE, landuse.getCaption()));
        return validator.isValid(allocatedAmount);
    }
    
    /**
     * @param landuse type
     * @param allocatedAmount in cells
     * @return:DeviationStatus
     */
    public DeviationStatus getDeviationStatus(Clazz landuse, int allocatedAmount) {
        DemandValidator validator = getValidator(landuse);
        if (validator == null)
            throw new RuntimeException(String.format(ERROR_LANDUSE_NOT_AVAILABLE, landuse.getCaption()));
        return validator.getDeviationStatus(allocatedAmount);
    }

    public DemandValidator getValidator(Clazz landuse) {
        return validators.get(landuse);
    }
    
    public Integer getDemand(Clazz landuse) {
        return getValidator(landuse).getDemand();
    }

    public int getTotalDemand() {
        int total = 0;
        for (DemandValidator validator: validators.values())
            total += validator.getDemand();
        return total;
    }
    
    /**
     * 
     * @param landuseDistribution, map of landuse clazz and number of allocated cells
     * @return map of land use clazz and deviation in number of cells. Positive when too. Negative when too little
     */
    public Map<Clazz, Integer> getDemandDeviations(Map<Clazz, Integer> landuseDistribution) {
        Map<Clazz, Integer> result = new HashMap<>();
        for (Clazz landuse: landuseDistribution.keySet()) {
            int deviation = getValidator(landuse).getDeviation(landuseDistribution.get(landuse));
            result.put(landuse, deviation);
        }
        return result;
    }
    
    /**
     * 
     * @param landuse
     * @param allocatedAmount deviation in cells. Positive when too many cells are allocated, negative when too little
     * @return 
     */
    public int getDeviation (Clazz landuse, int allocatedAmount) {
        DemandValidator validator = getValidator(landuse);
        if (validator == null)
            throw new RuntimeException(String.format(ERROR_LANDUSE_NOT_AVAILABLE, landuse.getCaption()));
        return validator.getDeviation(allocatedAmount);
    }
    
    /**
     * 
     * @param landuseDistribution, map of landuse clazz and number of allocated cells
     * @return 
     */
    public Clazz getHighestDemandDeviation(Map<Clazz, Integer> landuseDistribution) {
        int maxDeviationAmount = Integer.MIN_VALUE;
        Clazz maxDeviationLanduse = null;
        for (Clazz landuse: landuseDistribution.keySet()) {
            int deviation = getValidator(landuse).getDeviation(landuseDistribution.get(landuse));
            int absoluteDeviation = Math.abs(deviation);
            if (absoluteDeviation > maxDeviationAmount) {
                maxDeviationAmount = absoluteDeviation;
                maxDeviationLanduse= landuse;
            }
        }
        return maxDeviationLanduse;
    }
    
    /**
     * 
     * @param landuseDistribution, map of landuse clazz and number of allocated cells
     * @return 
     */
    public Clazz getHighestTooManyDemandDeviation(Map<Clazz, Integer> landuseDistribution) {
        int maxDeviationAmount = Integer.MIN_VALUE;
        Clazz maxDeviationLanduse = null;
        for (Clazz landuse: landuseDistribution.keySet()) {
            int deviation = getValidator(landuse).getDeviation(landuseDistribution.get(landuse));
            if (deviation > maxDeviationAmount) {
                maxDeviationAmount = deviation;
                maxDeviationLanduse= landuse;
            }
        }
        return maxDeviationLanduse;
    }
    
    /**
     * 
     * @param landuseDistribution, map of landuse clazz and number of allocated cells
     * @return 
     */
    public Clazz getHighestTooFewDemandDeviation(Map<Clazz, Integer> landuseDistribution) {
        int minDeviationAmount = Integer.MAX_VALUE;
        Clazz minDeviationLanduse = null;
        for (Clazz landuse: landuseDistribution.keySet()) {
            int deviation = getValidator(landuse).getDeviation(landuseDistribution.get(landuse));
            if (deviation < minDeviationAmount) {
                minDeviationAmount = deviation;
                minDeviationLanduse= landuse;
            }
        }
        return minDeviationLanduse;
    }
       
    @Override
    public String toString() {
        String result = "{";
        for (Clazz lu: validators.keySet()) {
            result += String.format("%s(%s)=%s ", lu.getCaption(), lu.getValueAsString(), validators.get(lu).toString());
        }
        result += "}";
        return result;
    }
}
