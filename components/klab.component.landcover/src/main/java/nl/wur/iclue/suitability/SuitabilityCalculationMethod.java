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

/**
 *
 * @author Peter Verweij
 */
public enum SuitabilityCalculationMethod {
    CONSTANT("constant"),
    MAP("Map"),
    STEPWISE_REGRESSION("StepwiseRegression"),
    BAYESIAN_STATISTICS("BayesianStatistics"),
    FUNCTION_DICTIONARY("FunctionDictionary");
    
    private final String caption;

    private SuitabilityCalculationMethod(String caption) {
        this.caption = caption;
    }
    
    public String getCaption() {
        return this.caption;
    }
    
    public static SuitabilityCalculationMethod findByCaption(String caption) {
        for (SuitabilityCalculationMethod method: SuitabilityCalculationMethod.values())
            if (method.getCaption().equalsIgnoreCase(caption))
                return method;
        return null;
    }
}
