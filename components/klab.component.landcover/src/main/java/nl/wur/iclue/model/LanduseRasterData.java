/*
 * Copyright 2015 Alterra, Wageningen UR
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

import nl.wur.iclue.parameter.Landuses;
import nl.alterra.shared.rasterdata.CellStack;
import nl.alterra.shared.rasterdata.RasterData;
import nl.alterra.shared.rasterdata.RasterDataFactory;
import nl.alterra.shared.rasterdata.RasterDataStack;

/**
 *
 * @author Peter Verweij
 */
public class LanduseRasterData {
    private final RasterData landuseMap;
    private final RasterData ageMap;

    public LanduseRasterData(RasterData landuseMap, RasterData ageMap) {
        this.landuseMap = landuseMap;
        this.ageMap = ageMap;
    }
    
    public LanduseRasterData(RasterData baselineLanduseMap, Landuses landuses) {
        this.landuseMap = baselineLanduseMap;
        this.ageMap = createIntialAgeMap(baselineLanduseMap, landuses);
    }

    public RasterData getLanduseMap() {
        return landuseMap;
    }

    public RasterData getAgeMap() {
        return ageMap;
    }
    
    
    
    /**
     * create age map 
     * @param baselineLanduseMap
     * @param landuses, to retrieve initial ages 
     * @return 
     */
    public static RasterData createIntialAgeMap(RasterData baselineLanduseMap, Landuses landuses) {
        RasterDataStack mapStack = RasterDataFactory.createStack();
        mapStack.addInput(baselineLanduseMap); int LANDUSE_INDEX = 0;
        RasterData initialAgeMap = mapStack.addOutput(true);
        
        Number[] outputCellValues;
        for (CellStack cellStack: mapStack.getCellCursor()) {
            if (mapStack.containsInputWithNodata(cellStack))
                outputCellValues = mapStack.getOutputNodataValues();
            else {
                Number landuseCode = cellStack.inputValues[LANDUSE_INDEX];
                Landuses.Landuse landuse = landuses.findByValue(landuseCode);
                outputCellValues = new Number[] {landuse.getInitialAge()};
            }
            mapStack.setOutputValues(cellStack, outputCellValues);
        }
        
        return initialAgeMap;
    }
    
    
}
