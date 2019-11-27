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

package nl.wur.iclue.suitability.jmsl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.alterra.shared.datakind.Category;
import nl.alterra.shared.utils.log.Level;
import nl.alterra.shared.utils.log.Log;

/**
 * JmslRegressionFunction contains coefficients of drivers and a constant factor.
 * With actual driver values the probability can be calculated for each land use type.
 * 
 * @author Peter Verweij, Johnny te Roller 
 */
@Deprecated
public class JmslRegressionFunction {

    private static final String ERROR_PROBABILITY_OUT_OF_RANGE = "Probability is out of range [0..1]: %.3f";
    private static final String LOG_PROBABILITY_ERROR = "Potential suitability value from regression function failure";
    private static final String LOG_NULL_COEFFICIENT_FOR_CATEGORY = "Regression function configuration encountered null coefficient for '%s'";

    Map<Category, Double> qualitativeDriverCategories = new HashMap<>();
    double constantFactor;
    Double[] quantitativeDriverCoefficients; // contains all quantitative driver values, but some might be null -> don't use in regression function
        
    double getProbability(List<Number> quantitativeDriverValues, List<Category> qualitativeDriverValues) {
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
    
    public void put(Category category, Double coeficient) {
        if (coeficient != null)
            qualitativeDriverCategories.put(category, coeficient);
        else
            Log.log(Level.INFO, String.format(LOG_NULL_COEFFICIENT_FOR_CATEGORY, category.getCaption()), null);
     }

}
