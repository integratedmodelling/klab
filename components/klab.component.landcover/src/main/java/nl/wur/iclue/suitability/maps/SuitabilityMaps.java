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

package nl.wur.iclue.suitability.maps;

import java.util.List;
import java.util.Map;
import nl.wur.iclue.parameter.Landuses;
import nl.wur.iclue.parameter.Landuses.Landuse;
import nl.wur.iclue.parameter.SpatialDataset;
import nl.wur.iclue.suitability.SuitabilityCalculator;
import nl.alterra.shared.datakind.Clazz;
import nl.alterra.shared.datakind.DataKind;
import nl.alterra.shared.rasterdata.CellStack;
import nl.alterra.shared.rasterdata.RasterData;

/**
 *
 * @author Peter Verweij
 */
public class SuitabilityMaps extends SuitabilityCalculator {
    private final Map<Landuse, RasterData> maps;
    
    public SuitabilityMaps(List<DataKind> driverDatakinds, Landuses landuses, Map<Landuse, RasterData> maps) {
        super(driverDatakinds, landuses);
        this.maps = maps;
    }

    @Override
    public double getProbability(Landuses.Landuse landuseOfInterest, CellStack driverValues) {
        RasterData map = maps.get(landuseOfInterest); 
        Number cellValue = map.getCellValue(driverValues.getRowIndex(), driverValues.getColumnIndex());
        return cellValue.doubleValue();
    }

    @Override
    public void updateFromBaseline(SpatialDataset baseline, Clazz administrativeUnit) {
        // void
    }
    
}
