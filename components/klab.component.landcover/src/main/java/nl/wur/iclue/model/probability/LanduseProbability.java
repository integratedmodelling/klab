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
package nl.wur.iclue.model.probability;

import nl.wur.iclue.parameter.Landuses;

/**
 * class to store all probabilities for a landuse
 * 
 * @author Peter Verweij
 */
public class LanduseProbability {
	
    public static final double MAX_SUITABILITY_VALUE = 1.0;
    
    private static final String ERROR_NEIGHBOURHOOD_OUT_OF_RANGE = "Cannot set neighbourhood value to %f. Should be between [0..1]";
    private static final String ERROR_SUITABILITY_OUT_OF_RANGE = "Cannot set suitability value to %f. Should be between [0..1]";
    private static final String ERROR_DEMANDWEIGHT_OUT_OF_RANGE = "Cannot set demandweight value to %f. Should be between [-1..1]";
    
    private final Landuses.Landuse landuse;
    private double neighbourhood;
    private double suitability;
    private double demandWeight;

    public LanduseProbability(Landuses.Landuse landuse) {
        super();
        this.landuse = landuse;
        setAllButEaseOfChangeToZero();
    }

    public static LanduseProbability copy(LanduseProbability lup) {
        LanduseProbability result = new LanduseProbability(lup.landuse);
        result.neighbourhood = lup.neighbourhood;
        result.suitability = lup.suitability;
        result.demandWeight = lup.demandWeight;
        return result;
    }

    public Landuses.Landuse getLanduse() {
        return landuse;
    }

    public final void setAllButEaseOfChangeToZero() {
        neighbourhood = .0;
        suitability = .0;
        demandWeight = .0;
    }

    public double getEaseOfChange() {
        return landuse.getEaseOfChange().getWeight();
    }

    public double getNeighbourhood() {
        return neighbourhood;
    }

    public LanduseProbability setNeighbourhood(double neighbourhood) {
        if ((neighbourhood<0.0) || (neighbourhood>1.0))
            throw new IllegalArgumentException(String.format(ERROR_NEIGHBOURHOOD_OUT_OF_RANGE, neighbourhood));
        this.neighbourhood = neighbourhood;
        return this;
    }

    public double getSuitability() {
        return suitability;
    }

    public LanduseProbability setSuitability(double suitability) {
        if ((suitability<0.0) || (suitability>1.0))
            throw new IllegalArgumentException(String.format(ERROR_SUITABILITY_OUT_OF_RANGE, suitability));
        this.suitability = suitability;
        return this;
    }

    public double getDemandWeight() {
        return demandWeight;
    }

    public LanduseProbability setDemandWeight(double demandWeight) {
        if ((demandWeight<-1.0) || (demandWeight>1.0))
            throw new IllegalArgumentException(String.format(ERROR_DEMANDWEIGHT_OUT_OF_RANGE, demandWeight));
        this.demandWeight = demandWeight;
        return this;
    }
}
