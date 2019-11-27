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

package nl.wur.iclue.model.probability;

import nl.wur.iclue.parameter.Landuses.Landuse;

/**
 * enum indicating how the various probabilities are taken together as a composite probability
 * 
 * @author Peter Verweij
 */
    public enum ProbabilityCompositeMethod implements ProbabilityCompositeCalculator {
        MULTIPLY_ALL(new ProbabilityCompositeCalculator() {

            @Override
            public double getCompositeValue(Landuse current, LanduseProbability lup) {
                return lup.getDemandWeight()
                        * lup.getEaseOfChange()
                        * lup.getNeighbourhood()
                        * lup.getSuitability();
            }
        }),
        SUM_ALL(new ProbabilityCompositeCalculator() {

            @Override
            public double getCompositeValue(Landuse current, LanduseProbability lup) {
                return lup.getDemandWeight()
                        + lup.getEaseOfChange()
                        + lup.getNeighbourhood()
                        + lup.getSuitability();
            }
        }),
        INCLUDE_EASEOFCHANGE_ONLY_FOR_CURRENT_LANDUSE(new ProbabilityCompositeCalculator() {

            @Override
            public double getCompositeValue(Landuse current, LanduseProbability lup) {
                double result = (lup.getDemandWeight() + lup.getNeighbourhood() + lup.getSuitability());
                if (lup.getLanduse().equals(current)) 
                    result += current.getEaseOfChange().getWeight();
                return result;
            }
        }),
        DEMAND_WEIGHT_AS_MAIN_DRIVER(new ProbabilityCompositeCalculator() {

            @Override
            public double getCompositeValue(Landuse current, LanduseProbability lup) {
                double rescaledDemandWeight = (lup.getDemandWeight()+1.0)/2.0; // originally from -1;1
                double preProbability = (rescaledDemandWeight + lup.getSuitability())/2.0;
                if (lup.getLanduse().equals(current)) 
                    return preProbability + (1-preProbability)*current.getEaseOfChange().getWeight();
                else
                    return preProbability;
            }
        }),
        SUITABILITY_ONLY(new ProbabilityCompositeCalculator() {

            @Override
            public double getCompositeValue(Landuse current, LanduseProbability lup) {
                return lup.getSuitability();
            }
            
        });
        
        private final ProbabilityCompositeCalculator algo;

        private ProbabilityCompositeMethod(ProbabilityCompositeCalculator algo) {
            this.algo = algo;
        }
        
        @Override
        public double getCompositeValue(Landuse current, LanduseProbability lup) {
            return algo.getCompositeValue(current, lup);
        }
    }
