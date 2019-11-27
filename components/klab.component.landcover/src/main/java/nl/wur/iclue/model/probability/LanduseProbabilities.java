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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import nl.wur.iclue.parameter.EaseOfChange;
import nl.wur.iclue.parameter.Landuses;
import nl.wur.iclue.parameter.Landuses.Landuse;
import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.util.Pair;

/**
 * List of probabilities per land use
 * 
 * @author Peter Verweij
 */
public class LanduseProbabilities {
	
    private List<LanduseProbability> probs;

    public LanduseProbabilities() {
        this(null, true);
    }

    public LanduseProbabilities(Landuses landuses, boolean includeLandusesThatCannotChange) {
        super();
        probs = new ArrayList<>();
        if (landuses != null) {
            for (Landuse lu: landuses) { 
                if (!EaseOfChange.CANNOT_CHANGE.equals(lu.getEaseOfChange()))
                    add(lu);
                else if (includeLandusesThatCannotChange)
                    add(lu);
            }
        }
    }
    
    public final LanduseProbability add(Landuse landuse) {
        LanduseProbability lp = new LanduseProbability(landuse);
        probs.add(lp);
        return lp;
    }
    
    public LanduseProbability get(Landuse landuse) {
        for (LanduseProbability prob: probs)
            if (prob.getLanduse().equals(landuse))
                return prob;
        return null;
    }

    public List<Landuse> getLanduses() {
        List<Landuse> result = new ArrayList<>();
        for (LanduseProbability prob: probs) 
            result.add(prob.getLanduse());
        return result;
    }
    
    
    
    public void setAllProbabilitiesToZero() {
        for (LanduseProbability prob: probs)
            prob.setAllButEaseOfChangeToZero();
    }
    
    public double getProbability(Landuse current, Landuse target, ProbabilityCompositeCalculator calculator) {
        LanduseProbability lup = get(target);
        return calculator.getCompositeValue(current, lup);
    }
    
    public static Landuse getHighestProbability(Map<Landuse, Double> landuseProbabilities) {
        if (landuseProbabilities.isEmpty())
            return null;
        if (landuseProbabilities.size() == 1)
            return landuseProbabilities.keySet().iterator().next();
        
        Double maxProbability = Double.MIN_VALUE;
        Landuse maxLanduse = null;
        for (Entry<Landuse, Double> entry: landuseProbabilities.entrySet()) 
            if (entry.getValue() > maxProbability) {
                maxProbability = entry.getValue();
                maxLanduse = entry.getKey();
            }
        return maxLanduse;
    }

    public static Landuse getSampleOfProbabilityDistribution(Map<Landuse, Double> landuseProbabilities) {
        if (landuseProbabilities.isEmpty())
            return null;
        if (landuseProbabilities.size() == 1)
            return landuseProbabilities.keySet().iterator().next();
        
        List<Pair<Landuse, Double>> pairs = new ArrayList<>();
        for (Entry<Landuse, Double> entry: landuseProbabilities.entrySet()) 
            pairs.add(new Pair(entry.getKey(), entry.getValue()));
        return (new EnumeratedDistribution<>(pairs)).sample();
    }
    
    private static Map<Landuse, Double> createDistributionMap(LanduseProbabilities landuseProbabilities, ProbabilityCompositeCalculator calculator) {
        Map<Landuse, Double> result = new HashMap<>();
        for (Landuse lu: landuseProbabilities.getLanduses())
            result.put(lu, calculator.getCompositeValue(lu, landuseProbabilities.get(lu)));
        return result;
    } 

    public static Landuse getHighestProbability(LanduseProbabilities landuseProbabilities, ProbabilityCompositeCalculator calculator) {
        return getHighestProbability(createDistributionMap(landuseProbabilities, calculator));
    }
    
    public static Landuse getSampleOfProbabilityDistribution(LanduseProbabilities landuseProbabilities, ProbabilityCompositeCalculator calculator) {
        return getSampleOfProbabilityDistribution(createDistributionMap(landuseProbabilities, calculator));
    }
    
    
    
    
}
