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

package nl.wur.iclue.parameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nl.alterra.shared.datakind.Category;
import nl.alterra.shared.datakind.Clazz;
import nl.alterra.shared.datakind.IDataKind;
import nl.alterra.shared.rasterdata.RasterData;
import nl.wur.iclue.model.demand.DemandFactory.DemandValidationType;
import nl.wur.iclue.parameter.Landuses.Landuse;
import nl.wur.iclue.parameter.conversion.Conversion;
import nl.wur.iclue.suitability.SuitabilityCalculationMethod;
import nl.wur.iclue.suitability.function.SuitabilityFunction;

/**
 *
 * @author Peter Verweij
 */
public class ParameterStatus {
    public static final String NEW_LINE = System.getProperty("line.separator");
    
    private final Parameters params;
    private final String ERROR_BASELINE_NULL = "NULL reference for baseline";
    private final String ERROR_BASELINE_FILE_DOES_NOT_EXIST = "Baseline file does not exist: '%s'";
    private final String ERROR_BASLINE_YEAR_NULL = "NULL reference for the baseline year";
    private final String ERROR_BASELINE_NO_LANDUSE_DEFINED = "No landuse classes are defined for the baseline";
    private final String ERROR_LANDUSE_NULL = "NULL reference for landuse classes";
    private final String ERROR_LANDUSE_EMPTY = "The are 0 landuse classes defined";
    private final String ERROR_ADMIN_FILE_DOES_NOT_EXIST = "Administrative units file does not exist: '%s'";
    private final String ERROR_ADMIN_NO_UNITS_DEFINED = "There are no administrative units defined";
    private final String ERROR_TARGET_TIME = "The target time must be later then the baseline time";
    private final String ERROR_DEMANDS_NONE = "NULL reference for the demands";
    private final String ERROR_DEMANDS_MISSING_LANDUSE = "Missing demand for '%s'. In Administrative unit: '%s', year: %d";
    private final String ERROR_DEMANDS_LANDUSE_COUNT = "Wrong number of landuse demands for '%s' - %d. Expected %d landuse types. Got %d.";
    private final String ERROR_DRIVER_NONE = "NULL reference for the drivers";
    private final String ERROR_DRIVER_UNSPECIFIED_DATAKIND = "There is no valid DataType for driver: '%s'. Please choose between: QUALITATIVE or QUANTITATIVE";
    private final String ERROR_DRIVER_0_YEARS = "There is no year information for the driver: '%s'";
    private final String ERROR_DRIVER_YEAR_BEFORE_BASELINE = "The driver '%s' contains the year '%d', which is before the baseline";
    private final String ERROR_DRIVER_YEAR_AFTER_TARGET_TIME = "The driver '%s' contains the year '%d', which is after the target time";
    private final String ERROR_DRIVER_FILE_DOES_NOT_EXIST = "Driver file does not exist: '%s'";
    private final String ERROR_CONVERSION_NONE = "NULL reference for conversions";
    private final String ERROR_CONVERSION_NUMBERS = "Wrong number of conversion. Expected a maximum of %d, got %d";
    private final String ERROR_CONVERSION_WITH_INCOMPLETE_LANDUSE = "Conversion with incomplete landuse (FROM and/or TO) encountered";
    private final String ERROR_CONVERSION_UNKNOWN_RULE = "Conversion with unknown rule for '%s' to '%s'";
    private final String ERROR_SUITABILITY_CALCULATION_METHOD_NULL = "NULL reference for suitability calculation method";
    private final String ERROR_SUITABILITY_CALCULATION_PARAMETERS_MAPS = "Suitabilty for landuses is based on maps. The parameters are either not present, or incomplete";
    private final String ERROR_SUITABILITY_CALCULATION_PARAMETERS_MISSING = "Suitability calculation parameters for Stepwise Regression are missing";
    private final String ERROR_SUITABILITY_CALCULATION_PARAMETERS_FUNCTION_DICTIONARY_MISSING = "Suitability parameters for Function Dictionary are missing";
    private final String ERROR_SUITABILITY_CALCULATION_PARAMETERS_UNCHECKED = "Unchecked parameters for suitability calculation: %s";
    private final String ERROR_DEMAND_DEVIATION_TYPE_UNKNOWN = "Demand deviation type not set for '%s'";
    private final String ERROR_DEMAND_DEVIATION_AMOUNT_UNKNOWN = "Demand deviation amount not set for '%s'";

    ParameterStatus(Parameters params) {
        this.params = params;
    }

    public boolean isComplete() {
        return getErrors().isEmpty();
    }

    public List<String> getErrors() {
        List<String> errors = new ArrayList<>();

        getLanduseError(errors);
        getBaselineError(errors);
        getAdministrativeUnitsError(errors);
        getTargetTimeError(errors);
        getDemandsError(errors);
        getDriversError(errors);
        getDemandDeviationError(errors);
        getConversionError(errors);
        getSuitabilityCalculationMethod(errors);

        return errors;
    }
    
    public String getErrorsMessage() {
        String result = "";
        for (String error: getErrors())
            result += error + NEW_LINE;
        return result;
    }

    private void getBaselineError(List<String> errorLog) {
        if (params.getBaseline() == null) {
            errorLog.add(ERROR_BASELINE_NULL);
            return;
        }
        if (!(params.getBaseline().getRasterData().isDataDefinitionValid()))
            errorLog.add(String.format(ERROR_BASELINE_FILE_DOES_NOT_EXIST, params.getBaseline().getRasterData().getDataDefinition()));
        if (params.getBaseline().getYear() == null)
            errorLog.add(ERROR_BASLINE_YEAR_NULL);
        if (params.getBaseline().getDatakind().getClassCount() < 1)
            errorLog.add(ERROR_BASELINE_NO_LANDUSE_DEFINED);
    }

    private void getLanduseError(List<String> errorLog) {
        if (params.getLanduses() == null) {
            errorLog.add(ERROR_LANDUSE_NULL);
            return;
        }
        if (params.getLanduses().size() < 1)
            errorLog.add(ERROR_LANDUSE_EMPTY);
    }

    private void getAdministrativeUnitsError(List<String> errorLog) {
        // can be null. In that case there are no administrative units
        if (params.getAdministrativeUnits() == null)
            return;
        if (!(params.getAdministrativeUnits().getRasterData().isDataDefinitionValid()))
            errorLog.add(String.format(ERROR_ADMIN_FILE_DOES_NOT_EXIST, params.getAdministrativeUnits().getRasterData().getDataDefinition()));
        if (params.getAdministrativeUnits().getDatakind().getClassCount() < 1)
            errorLog.add(ERROR_ADMIN_NO_UNITS_DEFINED);
    }

    private void getTargetTimeError(List<String> errorLog) {
        if (!params.isValidTargetTime())
            errorLog.add(ERROR_TARGET_TIME);
    }

    private void getDemandsError(List<String> errorLog) {
        if ((params.getDemands()== null) || (params.getDemands().isEmpty())) {
            errorLog.add(ERROR_DEMANDS_NONE);
            return;
        }
        LanduseDistributions demands = params.getDemands();
        for (Clazz adminUnit: params.getAdministrativeUnits().getDatakind().getClasses()) {
            for (long year: demands.getSortedYears(adminUnit)) {
                Map<Landuse, Integer> areaAmounts = demands.getAreaAmounts(adminUnit, year);
                for (Landuse lu: params.getLanduses()) {
                    if (!areaAmounts.containsKey(lu))
                        errorLog.add(String.format(ERROR_DEMANDS_MISSING_LANDUSE, lu.getCaption(), adminUnit.getCaption(), year));
                }
                if (params.getLanduses().size() != areaAmounts.size())
                    errorLog.add(String.format(ERROR_DEMANDS_LANDUSE_COUNT, adminUnit.getCaption(), year, params.getLanduses().size(), areaAmounts.size()));
            }
        }
    }

    private void getDriversError(List<String> errorLog) {
    	/*
    	 * FV - empty drivers is OK as we externalize the suitability model completely. Even in the 
    	 * general CLUE logic this should be OK, as we could have constant suitability.
    	 */
        if ((params.getDrivers() == null/*) || (params.getDrivers().isEmpty()*/)) {
            errorLog.add(ERROR_DRIVER_NONE);
            return;
        }
        for (SpatialDataset ds: params.getDrivers()) {
            if (IDataKind.Type.UNSPECIFIED.equals(ds.getDatakind().getType()))
                errorLog.add(String.format(ERROR_DRIVER_UNSPECIFIED_DATAKIND, ds.getCaption()));
                
            if (ds.getYears().isEmpty())
                errorLog.add(String.format(ERROR_DRIVER_0_YEARS, ds.getCaption()));
            else if (!ds.isYearKnown()) {
                if (!(ds.getRasterData().isDataDefinitionValid()))
                    errorLog.add(String.format(ERROR_DRIVER_FILE_DOES_NOT_EXIST, ds.getRasterData().getDataDefinition(), -1));
            } else {
                for (int year: ds.getYears()) {
                    if (year<params.getBaseline().getYear())
                        errorLog.add(String.format(ERROR_DRIVER_YEAR_BEFORE_BASELINE, ds.getCaption(), year));
                    if (year>params.getTargetTime())
                        errorLog.add(String.format(ERROR_DRIVER_YEAR_AFTER_TARGET_TIME, ds.getCaption(), year));
                    if (!(ds.getRasterData(year).isDataDefinitionValid()))
                        errorLog.add(String.format(ERROR_DRIVER_FILE_DOES_NOT_EXIST, ds.getRasterData(year).getDataDefinition(), year));
                }
            }
        }
    }

    private void getDemandDeviationError(List<String> errorLog) {
        for (Landuse lu: params.getLanduses()) {
            DemandValidationType deviationType = params.getDemandDeviationType(lu);
            if ((deviationType == null) || (DemandValidationType.UNKNOWN.equals(deviationType)))
                errorLog.add(String.format(ERROR_DEMAND_DEVIATION_TYPE_UNKNOWN, lu.getCaption()));
            Integer deviationAmount = params.getDemandDeviation(lu);
            if ((deviationAmount == null) || (deviationAmount == Parameters.UNDEFINED))
                errorLog.add(String.format(ERROR_DEMAND_DEVIATION_AMOUNT_UNKNOWN, lu.getCaption()));
        }
    }
    
    private void getConversionError(List<String> errorLog) {
    	
        List<Conversion> conversions = params.getConversions();
        if (conversions == null) {
            errorLog.add(ERROR_CONVERSION_NONE);
            return;
        }
        if (params.getLanduses() != null) {
            int numberOfExpectedConversions = (int)Math.pow(params.getLanduses().size(),2);
            if (conversions.size() > numberOfExpectedConversions)
                errorLog.add(String.format(ERROR_CONVERSION_NUMBERS, numberOfExpectedConversions, conversions.size()));
        }
        for (Conversion c: conversions) {
            if ((c.getFromLanduse() == null) || (c.getToLanduse()== null))
                errorLog.add(ERROR_CONVERSION_WITH_INCOMPLETE_LANDUSE);
            else if (c.getRule() == null)
                errorLog.add(String.format(ERROR_CONVERSION_UNKNOWN_RULE, c.getFromLanduse().getCaption(), c.getToLanduse().getCaption()));
        }
    }

    private void getSuitabilityCalculationMethod(List<String> errorLog) {
        SuitabilityParameters suitabilityParameters = params.getSuitabilityParameters();
        if ((suitabilityParameters == null) || (suitabilityParameters.getMethod() == null)) {
            errorLog.add(ERROR_SUITABILITY_CALCULATION_METHOD_NULL);
            return;
        }

        SuitabilityCalculationMethod method = suitabilityParameters.getMethod();
        if (SuitabilityCalculationMethod.CONSTANT.equals(method))
            return;
        
        Object methodParams = suitabilityParameters.getParameters();
        if (methodParams == null)
            errorLog.add(ERROR_SUITABILITY_CALCULATION_PARAMETERS_MISSING);
        switch (method) {
            case STEPWISE_REGRESSION: 
            case BAYESIAN_STATISTICS: 
                break;
            case MAP: 
                Map<Landuse, RasterData> maps;
                try {
                    maps = (Map<Landuse, RasterData>)methodParams;
                    for (Landuse lu: params.getLanduses()) {
                        RasterData map = maps.get(lu);
                        if (map == null)
                            throw new RuntimeException();
                    }
                } catch (RuntimeException e) {
                    errorLog.add(ERROR_SUITABILITY_CALCULATION_PARAMETERS_MAPS);
                }
                break;
            case FUNCTION_DICTIONARY: {
                Map<String, Map<Category,SuitabilityFunction>> functions;
                try {
                    functions = (Map<String, Map<Category,SuitabilityFunction>>)methodParams;
                    if (functions.size() < 1)
                        throw new RuntimeException();
                } catch (RuntimeException e) {
                    errorLog.add(ERROR_SUITABILITY_CALCULATION_PARAMETERS_FUNCTION_DICTIONARY_MISSING);
                }
                break;
            }
            default:
                errorLog.add(String.format(ERROR_SUITABILITY_CALCULATION_PARAMETERS_UNCHECKED, method.getCaption()));
        }
    }
        
}
