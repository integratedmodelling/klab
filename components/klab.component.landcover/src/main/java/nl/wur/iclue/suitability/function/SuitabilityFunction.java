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
package nl.wur.iclue.suitability.function;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.alterra.shared.datakind.Category;
import nl.alterra.shared.utils.log.Level;
import nl.alterra.shared.utils.log.Log;

/**
 * The Suitability function contains coefficients of drivers and a constant factor.
 * With actual driver values the probability can be calculated for each land use type.
 * 
 * @author Peter Verweij, Johnny te Roller 
 */
public class SuitabilityFunction {
	
    private static final String ERROR_PROBABILITY_OUT_OF_RANGE = "Probability is out of range [0..1]: %.3f";
    private static final String LOG_PROBABILITY_ERROR = "Potential suitability value from regression function failure";
    
    private final Map<Category, Double> qualitativeDriverCategories;
    private Map<Category, String> qualitativeDriverCategoriesMetaInfo;
    private final double constantFactor;
    private final Double[] quantitativeDriverCoefficients; // contains all quantitative driver values, but some might be null -> don't use in regression function
    private String[] quantitativeDriverMetaInfo;     // contains meta information for all quantitative driver values

    public SuitabilityFunction(double constantFactor, Double[] quantitativeDriverCoefficients, Map<Category, Double> qualitativeDriverCategories) {
        this.qualitativeDriverCategories = qualitativeDriverCategories;
        this.constantFactor = constantFactor;
        this.quantitativeDriverCoefficients = quantitativeDriverCoefficients;
        quantitativeDriverMetaInfo = new String[quantitativeDriverCoefficients.length];
        qualitativeDriverCategoriesMetaInfo = new HashMap<>();
    }
        
    public void setQuantitativeDriverMetaInfo(String[] quantitativeDriverMetaInfo) {
        this.quantitativeDriverMetaInfo = quantitativeDriverMetaInfo;
    } 
    
    public void setQualitativeDriverCategoriesMetaInfo(Map<Category, String> qualitativeDriverCategoriesMetaInfo) {
        this.qualitativeDriverCategoriesMetaInfo = qualitativeDriverCategoriesMetaInfo;
    }
    
    public double getProbability(List<Number> quantitativeDriverValues, List<Category> qualitativeDriverValues) {
        assert(quantitativeDriverValues.size() == quantitativeDriverCoefficients.length);

        double klarabella = constantFactor;
        for (int i=0; i< quantitativeDriverValues.size(); i++)
            if (quantitativeDriverCoefficients[i] != null)
                    klarabella += quantitativeDriverCoefficients[i] * quantitativeDriverValues.get(i).doubleValue();

        for (Category category: qualitativeDriverValues)
            if (qualitativeDriverCategories.containsKey(category))
                klarabella += klarabella * qualitativeDriverCategories.get(category);
        
        Double betsie = Math.exp(klarabella);
        Double result = betsie/(1.0 + betsie);
        if (betsie.isInfinite())
           result = 1.0;
              
        if ((result>=0.0) && (result<=1.0)) 
            return result;
        else {
            RuntimeException e = new RuntimeException(String.format(ERROR_PROBABILITY_OUT_OF_RANGE, result));
            Log.log(Level.ERROR, LOG_PROBABILITY_ERROR, e);
            throw (e);
        }
    }

    public Map<Category, Double> getQualitativeDriverCategories() {
        return qualitativeDriverCategories;
    }
    
    public Map<Category, String> getQualitativeDriverCategoriesMetaInfo() {
        return qualitativeDriverCategoriesMetaInfo;
    }
    public double getConstantFactor() {
        return constantFactor;
    }

    public Double getQuantitativeDriverCoefficient(int driverIndex) {
        return quantitativeDriverCoefficients[driverIndex];
    }
    
    public String getQuantitativeDriverMetaInfo(int driverIndex) {
        return quantitativeDriverMetaInfo[driverIndex];
    }
    
}
