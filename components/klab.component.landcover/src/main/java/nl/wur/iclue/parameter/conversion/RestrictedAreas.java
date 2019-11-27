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
package nl.wur.iclue.parameter.conversion;

import java.util.Arrays;
import nl.wur.iclue.parameter.SpatialDataset;
import nl.alterra.shared.rasterdata.CellStack;
import nl.alterra.shared.rasterdata.RasterData;

/**
 * Areas without data (=no-data) are allowed to be converted. Areas with data cannot be converted
 * 
 * @author Johnny te Roller, Peter Verweij
 */
public class RestrictedAreas extends Rule {
    private final String ERROR_INVALID_RESTRICTED_AREAS = "Cannot determine whether conversion is possible. There is no spatial dataset for restriction set";
    private final String ERROR_INVALID_SPATIAL_DATASET_DEFINITION = "Cannot set spatialdataset. String definition is incorrect: '%s'";
    private SpatialDataset restrictedAreas;
    private int rowIndex = 0;
    private int columnIndex = 0;

    public RestrictedAreas() {
        this(null);
    }

    public RestrictedAreas(SpatialDataset restrictedAreas) {
        super();
        this.restrictedAreas = restrictedAreas;
    }

    /**
     * create a spatial dataset from a string array
     * @param def may be formed by one of the below:
     *  1. [filename]
     *  2. [filename1, year1, filename2, years2, etc.]
     */
    public void setAreasFromDefinition(String[] def) {
        try {
            restrictedAreas = new SpatialDataset();
            if (def.length == 1)
                restrictedAreas.setFilename(def[0]);
            else {
                for (int i=1; i<def.length; i+=2) {
                    String filename = def[i];
                    int year = Integer.parseInt(def[i+1]);
                    restrictedAreas.add(filename, year);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format(ERROR_INVALID_SPATIAL_DATASET_DEFINITION, Arrays.toString(def)));
        }
    }
    
    
    @Override
    public boolean canConvert(int currentAge) {
        if (restrictedAreas == null)
            throw new RuntimeException(ERROR_INVALID_RESTRICTED_AREAS);
        RasterData rasterData = restrictedAreas.getMostRecentRasterData(currentAge);
        return rasterData.isNodata(rasterData.getCellValue(rowIndex, columnIndex));
    }
    
    
    public void setLocation(CellStack location) {
        rowIndex = location.getRowIndex();
        columnIndex = location.getColumnIndex();
        
        
    }
}
