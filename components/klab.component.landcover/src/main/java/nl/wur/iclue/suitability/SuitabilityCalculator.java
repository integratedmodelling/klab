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

package nl.wur.iclue.suitability;

import java.util.ArrayList;
import java.util.List;

import nl.alterra.shared.datakind.Category;
import nl.alterra.shared.datakind.Clazz;
import nl.alterra.shared.datakind.DataKind;
import nl.alterra.shared.datakind.IDataKind;
import nl.alterra.shared.rasterdata.CellStack;
import nl.wur.iclue.parameter.Landuses;
import nl.wur.iclue.parameter.Landuses.Landuse;
import nl.wur.iclue.parameter.SpatialDataset;

/**
 *
 * @author Peter Verweij
 */
public abstract class SuitabilityCalculator {
	
    private static final String ERROR_UNDEFINED_DRIVERVALUE = "Cannot map code to category for datakind: '%s' and value '%d'";
    public static final String LOG_TOKEN = "SUITABILITY";
    
    private final List<DataKind> driverDatakinds;

    private final Landuses landuses;
    
    public SuitabilityCalculator(List<DataKind> driverDatakinds, Landuses landuses) {
        this.driverDatakinds = driverDatakinds;
        this.landuses = landuses;
    }

    /**
     * ASSUMES Ordering of drivers corresponds in both driverValues and driverDataKinds
     * @param landuseOfInterest
     * @param driverValues
     * @return 
     */
    public abstract double getProbability(Landuse landuseOfInterest, CellStack driverValues); // INCLUDE year? following Melanie's example

    /**
     * call when probabilities need to be (re)calculated for a baseline. 
     * When administrative unit changes
     * @param baseline 
     * @param adminstrativeUnit 
     */
    public abstract void updateFromBaseline(SpatialDataset baseline, Clazz adminstrativeUnit);
    
    protected Landuses getLanduses() {
        return landuses;
    }
    
    protected List<DataKind> getDriverDatakinds() {
        return driverDatakinds;
    }

    protected boolean isQualitative(int driverIndex) {
        assert((driverIndex >= 0) && (driverIndex < driverDatakinds.size()));
        
        return IDataKind.Type.QUALITATIVE.equals(driverDatakinds.get(driverIndex).getType());
    }
    
    protected Clazz getClazz(int driverIndex, Number[] driverValues) {
        assert(driverValues.length == driverDatakinds.size());
        assert((driverIndex >= 0) && (driverIndex < driverValues.length));
        
        return driverDatakinds.get(driverIndex).findByValue(driverValues[driverIndex]);
    }
    
    protected List<Number> getQuantitativeDriverValues(Number[] driverValues) {
        assert(driverValues.length == driverDatakinds.size());

        List<Number> result = new ArrayList<>();
        for (int i=0; i<driverValues.length; i++)
            if (!isQualitative(i))
                result.add(driverValues[i]);
        return result;
    }
    
    protected List<Category>  getQualitativeDriverValues(Number[] driverValues) {
        assert(driverValues.length == driverDatakinds.size());

        List<Category> result = new ArrayList<>();
        for (int i=0; i<driverValues.length; i++)
            if (isQualitative(i)) {
                Clazz clz = driverDatakinds.get(i).findByValue(driverValues[i]);
                if (clz instanceof Clazz.UndefinedClazz)
                    throw new RuntimeException(String.format(ERROR_UNDEFINED_DRIVERVALUE, driverDatakinds.get(i).getCaption(), driverValues[i].intValue()));
                result.add((Category)clz);
            }
        return result;
    }
}
