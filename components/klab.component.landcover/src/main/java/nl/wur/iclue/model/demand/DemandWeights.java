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

import java.util.HashMap;
import java.util.Map;
import nl.wur.iclue.parameter.Landuses;
import nl.alterra.shared.rasterdata.RasterData;

/**
 * Algorithms for determining demand weight for each land use
 * 
 * @author Peter Verweij
 */
public class DemandWeights {
    
    /**
     * create demand weights for each land use class based on available area of land use and demands
     * @param landuseMap
     * @param demands
     * @return weights ranging between [-1;1]. -1 indicating strong decrease, 1 indicating strong increase required
     */
    public static Map<Landuses.Landuse, Double> create(RasterData landuseMap, Map<Landuses.Landuse, Integer> demands) {
        Map<Landuses.Landuse, Double> result = new HashMap<>();
        
        Map<Integer, Integer> valueCountTable = landuseMap.createValueCountTable();
                
        int totalStudyArea = RasterData.getCellCount(valueCountTable);
        for (Landuses.Landuse landuse: demands.keySet()) {
            Integer allocatedArea = valueCountTable.get(landuse.getCode());
            allocatedArea = (allocatedArea != null) ? allocatedArea: 0;
            int demand = demands.get(landuse);
            result.put(landuse, calculateDemandWeight(allocatedArea, demand, totalStudyArea));
        }
        return result;
    }
    
    /**
     * 
     * @param allocatedArea
     * @param demand
     * @param totalArea
     * @return number between [-1;1]. negative for decrease, positive for increase (0 for no no change)
     */
    private static double calculateDemandWeight(int allocatedArea, int demand, int totalArea) {
        // sigmoid function
        double steepnessConstant = 0.1; // the smaller -> the steeper the sigmoid
            
        double result;

        if (allocatedArea < demand) {
            double hulp1 = Math.log(steepnessConstant/(1+steepnessConstant))/(double)demand;
            result = (1+steepnessConstant)*(1-Math.exp(hulp1*(demand-allocatedArea)));
        } 
        else if (allocatedArea == demand) { 
            result = 0.0;
        }
        else {
            double hulp2 = Math.log(steepnessConstant/(1+steepnessConstant))/(double)(totalArea-demand);
            result = -1 * (1+steepnessConstant)*(1-Math.exp(hulp2*(allocatedArea-demand)));
        }
        
        return result; 
    }
    
}
