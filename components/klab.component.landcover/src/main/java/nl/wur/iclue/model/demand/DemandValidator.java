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

/**
 *
 * @author Peter Verweij, Johnny te Roller
 */
public abstract class DemandValidator {
    private final int demand;

    /**
     * 
     * @param demand in cells
     */
    public DemandValidator(int demand) {
        super();
        this.demand = demand;
    }

    public Integer getDemand() {
        return demand;
    }
    
    protected abstract double getAllocatedMinAmountToBeValid();
    protected abstract double getAllocatedMaxAmountToBeValid();
        
    /**
     * 
     * @param allocatedAmount in cells
     * @return 
     */
    public boolean isValid(int allocatedAmount) {
        return (allocatedAmount >= getAllocatedMinAmountToBeValid()) && (allocatedAmount <= getAllocatedMaxAmountToBeValid());
    }
    
    /**
     * 
     * @param allocatedAmount in cells
     * @return: DeviationStatus
     */
    public DeviationStatus getDeviationStatus(int allocatedAmount) {
        if (allocatedAmount < getAllocatedMinAmountToBeValid())
            return DeviationStatus.TOO_FEW;
        else if (allocatedAmount > getAllocatedMaxAmountToBeValid())
            return DeviationStatus.TOO_MANY;
        else
            return DeviationStatus.VALID;

    }
    
    /**
     * 
     * @param allocatedAmount in cells
     * @return deviation in cells. Positive when too many cells are allocated, negative when too little
     */
    public int getDeviation(int allocatedAmount) {
        return allocatedAmount - getDemand();
    }
    
    @Override
    public String toString() {
        return String.format("%d",demand);
    }
    
    
    public enum DeviationStatus {
        TOO_FEW,
        VALID,
        TOO_MANY;
    }
}
