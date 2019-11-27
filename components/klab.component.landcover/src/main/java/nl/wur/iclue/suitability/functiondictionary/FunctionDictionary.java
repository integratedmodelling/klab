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
package nl.wur.iclue.suitability.functiondictionary;

import java.util.List;
import java.util.Map;
import nl.wur.iclue.parameter.Landuses;
import nl.wur.iclue.parameter.Landuses.Landuse;
import nl.wur.iclue.parameter.SpatialDataset;
import nl.wur.iclue.suitability.SuitabilityCalculator;
import nl.wur.iclue.suitability.function.SuitabilityFunction;
import nl.alterra.shared.datakind.Category;
import nl.alterra.shared.datakind.Clazz;
import nl.alterra.shared.datakind.DataKind;
import nl.alterra.shared.rasterdata.CellStack;
import nl.alterra.shared.utils.log.Log;

/**
 *
 * @author Johnny te Roller
 */
public class FunctionDictionary extends SuitabilityCalculator{
    
    private final static String ERROR_ADMINUNIT_NOTFOUND = "No suitability functions available for administrative unit %s";
    private final static String ERROR_LANDUSE_NOTFOUND = "No suitability function available for landusetype %s";
    private static final String ERROR_LOG = "Error logging suitability function";
    
    private final Map<String, Map<Category,SuitabilityFunction>> adminUnitLanduseSuitabilityFunctions;
    private Clazz currentAdminUnit;
    
    public FunctionDictionary(List<DataKind> driverDatakinds, Landuses landuses, Map<String, Map<Category,SuitabilityFunction>> adminUnitLanduseSuitabilityFunctions) {
        super(driverDatakinds, landuses);
        this.adminUnitLanduseSuitabilityFunctions = adminUnitLanduseSuitabilityFunctions;
    }

    @Override
    public double getProbability(Landuses.Landuse landuseOfInterest, CellStack driverValues) {
        if (!adminUnitLanduseSuitabilityFunctions.containsKey(currentAdminUnit.getCaption()))
            throw new RuntimeException(String.format(ERROR_ADMINUNIT_NOTFOUND, currentAdminUnit.getCaption()));
            
        Map<Category, SuitabilityFunction> landuseSuitabilityFunctions = adminUnitLanduseSuitabilityFunctions.get(currentAdminUnit.getCaption());
            
        if (!landuseSuitabilityFunctions.containsKey(landuseOfInterest.getCategory()))
            throw new RuntimeException(String.format(ERROR_LANDUSE_NOTFOUND, landuseOfInterest.getCaption()));
        
        SuitabilityFunction function = landuseSuitabilityFunctions.get(landuseOfInterest.getCategory());
        return function.getProbability(getQuantitativeDriverValues(driverValues.inputValues), getQualitativeDriverValues(driverValues.inputValues));
    }

    @Override
    public void updateFromBaseline(SpatialDataset baseline, Clazz adminstrativeUnit) {
        if (!adminUnitLanduseSuitabilityFunctions.containsKey(adminstrativeUnit.getCaption()) )
            throw new RuntimeException(String.format(ERROR_ADMINUNIT_NOTFOUND, adminstrativeUnit.getCaption()));
            
        currentAdminUnit = adminstrativeUnit;
        Map<Category, SuitabilityFunction> landuseSuitabilityFunctions = adminUnitLanduseSuitabilityFunctions.get(currentAdminUnit.getCaption());
        
        for (Landuse landuse: getLanduses()) {
            if (landuseSuitabilityFunctions.containsKey(landuse.getCategory()))
                logRegressionFunction(landuse.getCategory(), landuseSuitabilityFunctions.get(landuse.getCategory()), getDriverDatakinds());   
        }
    }

    private void log(String msg) {
        Log.log(null, msg, null, LOG_TOKEN);
    }
    
    private void logRegressionFunction(Category landuse, SuitabilityFunction function, List<DataKind> dataKinds) {
        log(String.format("Regressionfunction %s (%s):", landuse.getCaption(), currentAdminUnit.getCaption()));
        log(String.format("    %11.8f Constant", function.getConstantFactor()));
        
        int indexOfQuantitativeDriver = 0;
        for (DataKind dk: dataKinds) {
            switch (dk.getType()) {
                case QUANTITATIVE: {
                    Double coef = function.getQuantitativeDriverCoefficient(indexOfQuantitativeDriver);
                    if ((coef != null) && (!coef.isNaN())) 
                        log(String.format("    %11.8f %s", coef, dk.getCaption()));
                     else 
                        log(String.format("    %s %s", "       n.a.", dk.getCaption()));

                    indexOfQuantitativeDriver++;
                    break;
                }
                case QUALITATIVE: {
                    log(String.format("    %11s %s", "", dk.getCaption()));
                    for (Clazz clz: dk.getClasses()) {
                        if (function.getQualitativeDriverCategories().containsKey((Category)clz)) 
                            log(String.format("    %11.8f -> %s", function.getQualitativeDriverCategories().get((Category)clz), clz.getCaption()));
                        else 
                            log(String.format("    %11s -> %s", "    n.a.", clz.getCaption()));    
                    }
                    break;
                }
                default: throw new RuntimeException(ERROR_LOG);
            }
        }
        log(""); // ad empty line for log readability
    }
    
}
