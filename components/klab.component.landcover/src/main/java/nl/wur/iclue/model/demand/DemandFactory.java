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

import java.util.Map;
import java.util.Map.Entry;
import nl.wur.iclue.parameter.EaseOfChange;
import nl.wur.iclue.parameter.LanduseDistributions;
import nl.wur.iclue.parameter.Landuses.Landuse;
import nl.wur.iclue.parameter.Parameters;
import nl.alterra.shared.datakind.Clazz;

/**
 *
 * @author Peter Verweij
 */

public class DemandFactory {
    private static final String ERROR_UNKNOWN_DEMAND_DEVIATION_TYPE = "Cannot create demand validators. Unknown demand validator type: '%s' for landuse: '%s' ";
    
    public enum DemandValidationType {
        PERCENTAGE_DEVIATION ("PercentageDeviation"),
        ABSOLUTE_DEVIATION ("AbsoluteDeviation"),
        UNKNOWN ("Unknown");
        
        private final String caption;

        private DemandValidationType(String caption) {
            this.caption = caption;
        }

        public String getCaption() {
            return caption;
        }
        
         
        public static final DemandValidationType findByCaption(String caption) {
            if (caption != null) {
                for (DemandValidationType type: values())
                    if (caption.equalsIgnoreCase(type.getCaption()))
                        return type;
            }
            return UNKNOWN;
        }
    }
    
    public static DemandValidators create(Parameters params, Clazz administrativeUnit, int year) {
        DemandValidators validators = new DemandValidators();
        
        // OPTIMIZATION 
        // Is it necessary to determine amounts for baseline landuse distribution?
        LanduseDistributions lud;
        int firstDemandYear = params.getDemands().getSortedYears(administrativeUnit).get(0);
        if (year >= firstDemandYear)
            lud = params.getDemands();
        else
            lud = params.getLanduseDistributions();
        
        Map<Landuse, Integer> areaAmounts = lud.getAreaAmounts(administrativeUnit, year);
        
        for (Entry<Landuse, Integer> demand: areaAmounts.entrySet()) {
            Landuse landuse = demand.getKey();
            
            // no need to do this for landuse that cannot change
            if (landuse.getEaseOfChange().equals(EaseOfChange.CANNOT_CHANGE) )
                continue;
                
            DemandValidator validator = null;
            switch (params.getDemandDeviationType(landuse)) {
                case PERCENTAGE_DEVIATION: {
                    validator = new PercentageDeviationValidator(demand.getValue(), params.getDemandDeviation(landuse));
                    break;
                } 
                case ABSOLUTE_DEVIATION: {
                    validator = new AbsoluteDeviationValidator(demand.getValue(), params.getDemandDeviation(landuse));
                    break;
                } 
                default:
                    throw new RuntimeException(String.format(ERROR_UNKNOWN_DEMAND_DEVIATION_TYPE, params.getDemandDeviationType(landuse).getCaption(), landuse.getCaption()));
            }
             
            validators.add(demand.getKey().getCategory(), validator);
        }
        
        return validators;
    }
    
}

