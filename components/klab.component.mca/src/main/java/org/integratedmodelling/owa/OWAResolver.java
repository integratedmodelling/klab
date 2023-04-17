package org.integratedmodelling.owa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.awt.geom.Point2D;
import java.lang.Math;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.model.contextualization.IStateResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Parameters;


/*
 *  Resolver for Ordered Weighted Averages (OWA) of multiple observations. OWAs are used in multi-criteria analysis
 *  to guide decision making in spatially distributed contexts by associating a degree of importance, risk or 
 *  vulnerability to each location in a certain scope based on the relative relevance of a set of observables and the
 *  risk profile of the decision-maker. Thus an OWA is commonly parameterized by a set of relevance weights, that 
 *  represent the relative importance of each observable, and a set of ordinal weights, that represents the risk profile
 *  of the decision.maker. OWAs can be calculated using slightly different methods, this resolver supports three: 
 *  
 *  1- Classic OWA: The calculation requires a set of ordinal weights and relevance weights. The latter are 
 *  		associated with each observation. In each location of the scope, observables are ordered based on the
 *  		value of the observation multiplied by the corresponding relevance weight, and in decreasing order. 
 *  		The result of that multiplication for each observable is again multiplied by the ordinal weights in an
 *  		ordered manner. The resulting number is the final weight and the sum of those weights in each location 
 *  		of the scope is the OWA in that location.   
 *  
 *  2- WOWA with linguistic quantifier: WOWA stands for Weighted Ordered Weighted Average. The calculation requires
 *  		a set of relevance weights and a parameter Alpha (real number) representing the risk profile. Ordinal 
 *  		weights are represented by the function F(x) = x ^ Alpha. The final weights are determined by evaluating 
 *  		F(x) at {x_i} i in [1, nObservables] where x_i is the value of the cumulative sum of the relevance weights
 *  		ordered with respect to the value of the corresponding observables at the pixel. The final weights'
 *  		expression is W_i = x_i ^ Alpha - x_(i-1) ^ Alpha for i in [1, nObservables]. The method has the practical 
 *  		advantage of being more user-friendly by describing the risk profile with a single parameter. 
 *  
 *  3- WOWA with interpolated weights: The calculation requires a set of ordinal weights and relevance weights. Final
 *  		weights are determined like in method 2. However instead of evaluating a predefined and simple "risk" function,
 *  		a more complex and expressive function is determined from the full set of ordinal weights. The function is built
 *  		by interpolating the cumulative sum of the ordinal weights using a piece-wise second-order Bernstein polynomial.
 *  		This method gives most control and robustness than method 2 at the expense of being slightly harder to 
 *  		parameterize.    
 * 
 * Notes: 
 * 
 * 	All methods use normalized weights and weight normalization is done within the resolver, leaving the user the freedom to 
 * 	define the weights as preferred. 
 * 
 *  All methods require normalized and non-dimensional observations as inputs in order to produce meaningful results. 
 *  DISCLAIMER: NORMALIZATION OF THE OBSERVATIONS IS NOT DONE WITHIN THE RESOLVER. AS A CONSEQUENCE OBSERVABLES' DATA MUST BE
 *  NORMALIZED BEFORE CONTEXTUALIZATION.
 *  
 *  		   
 * 
 * 
 * */

public class OWAResolver extends AbstractContextualizer implements IStateResolver, IExpression {

	private Map<String,Double> relevanceWeights;
	// An ordered list is used for ordinal weights because weights are applied in order and not by key.
	// Thus the input list must be ordered.
	private List<Double> ordinalWeights;
	// Risk profile parameter for WOWA with linguistic quantifier.
	private Double alpha;
	// Only used for WOWA with interpolated weights.
	private Boolean interpolateWeights;
	private BernsteinInterpolator interpolator;
		
	
	/*
	 *
	 * Methods for weights' normalization.
	 * 
	 * */
	
	private List<Double> normalizeOrdinalWeights(List<Number> weights){
		Double sum = 0.0; 
		for(Number val : weights){
			 sum += val.doubleValue();
		} 
		List<Double> normalizedWeights = new ArrayList<>();
		for(Number val : weights){
			 normalizedWeights.add(val.doubleValue()/sum);
		} 
		return normalizedWeights;
	}
	
	
	private Map<String,Double> normalizeRelevanceWeights(Map<String,Number> weights){
		Double sum = 0.0; 
		for(Number val : weights.values()){
			 sum += val.doubleValue();
		} 
		Map<String, Double> normalizedWeights = new HashMap<>();
		for(String key : weights.keySet()){
			 normalizedWeights.put(key, weights.get(key).doubleValue()/sum);
		} 
		return normalizedWeights;
	}	
	
	
	
	/*
	 * 
	 * Calculation of cumulative weights for WOWA.
	 * 
	 * */
	
	private List<Point2D> cumulativeOrdinalWeights(List<Double> normalizedOrdinalWeights) {
		List<Point2D> cumulativeOW = new ArrayList<Point2D>();
		
		cumulativeOW.add(new Point2D.Double(0.0,0.0));
		
		Double xStep = 1.0/(double)normalizedOrdinalWeights.size(); 
		
		Double acc = 0.0;
		Double x;
		for(Integer i=0; i<normalizedOrdinalWeights.size(); i++) {
			acc += normalizedOrdinalWeights.get(i);
			x = (double) (i+1)*xStep;
			cumulativeOW.add(new Point2D.Double(x,acc));
		}
		
		return cumulativeOW;
	}
	
	private LinkedHashMap<String,Double> cumulativeRelevanceWeights(Map<String,Double> sortedRelevanceWeights){
		
		LinkedHashMap<String,Double> cumulativeRW = new LinkedHashMap<>();
		
		Iterator<Entry<String,Double>> it = sortedRelevanceWeights.entrySet().iterator();
		String key;
		Double sum = 0.0;
		while(it.hasNext()) {
			key = it.next().getKey();
        	sum += sortedRelevanceWeights.get(key);
        	cumulativeRW.put(key, sum);
		}
		
		return cumulativeRW;
	}
	
	
	
	/*
	 * 
	 * Relevance weights' sorting by observable value in descending order.
	 * 
	 * */
	
	private LinkedHashMap<String,Double> sortWeights(Map<String,Double> relevanceWeights, Map<String,Double> values){
		
		// Sort the values' map by descending order.
		LinkedHashMap<String, Double> sortedValues = values.entrySet()
			    .stream()
			    .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
			    .collect(Collectors.toMap(
			        Map.Entry::getKey,
			        Map.Entry::getValue,
			        (oldValue, newValue) -> oldValue, LinkedHashMap<String, Double>::new));
		
		// Fill the sorted RW by reading the keys of sortedValues.
		LinkedHashMap<String,Double> sortedRW = new LinkedHashMap<>();
		
        // Iterator for the sorted values.
        Iterator<Entry<String,Double>> it = sortedValues.entrySet().iterator();
  
        String key;
        Double w;
        while (it.hasNext()) {
        	key = it.next().getKey();
        	w = relevanceWeights.get(key);
        	sortedRW.put(key, w);
        }
        
        return sortedRW;
	}
	
	
	
	/*
	 * 
	 * Final weights for interpolated WOWA. The map of sorted relevance weights is linked, thus elements are traversed in order.
	 * The interpolator is evaluated at each cumulative relevance weight.
	 * 
	 * */
	
	private Map<String,Double> finalWeightsWOWA(BernsteinInterpolator interpolator, Map<String,Double> sortedRelevanceWeights){
		
		LinkedHashMap<String,Double> cumulativeRW = cumulativeRelevanceWeights(sortedRelevanceWeights);
		
		Map<String,Double> finalWeights = new HashMap<>();
		
		// Iterator for the cumulative and sorted weights.
        Iterator<Entry<String,Double>> it = cumulativeRW.entrySet().iterator();
		
        String key;
		Double val;
		Double previous = 0.0;
		while(it.hasNext()){
			key = it.next().getKey();
			val = interpolator.getInterpolatedValue(cumulativeRW.get(key));
			finalWeights.put(key,val - previous);
			previous = val;
		}
		
		return finalWeights;
	}
	
	
	/*
	 * 
	 * Final weights for linguistic quantifier WOWA. The map of sorted relevance weights is linked.
	 * F(x) = x ^ alpha is evaluated at each cumulative relevance weight. 
	 * 
	 * */
	private Map<String,Double> exponentialOrdinalWeights(Map<String,Double> sortedRelevanceWeights){
		
		Map<String,Double> cumulativeRW = cumulativeRelevanceWeights(sortedRelevanceWeights); 

		Map<String,Double> finalWeights = new HashMap<>();
		Double exp_par;
		Double eps = 0.00000001;
		Double val;
		Double previous = 0.0;
		for(String key : cumulativeRW.keySet()){
			if (alpha == 0) {exp_par = (double) (1.0 - alpha)/alpha;} else {exp_par = (double) (1.0 - eps)/eps;}
			val = Math.pow(cumulativeRW.get(key),alpha);
			finalWeights.put(key,val - previous);
			previous = val; 
		}
		return finalWeights;
	} 
	
	
	
	/*
	 * 
	 * Calculation of OWA and WOWAs.
	 * 
	 * */
	
	
	private Double calculateOWA(Map<String,Double> relevanceWeights, List<Double> ordinalWeights, Map<String,Double> values) {
		
		// Weight the values according to their relevance before sorting.
		List<Double> weightedValues = new ArrayList<>();
		for (String key : values.keySet()) {
            Double weightedValue = relevanceWeights.get(key) * values.get(key);
            weightedValues.add(weightedValue);
		}
		
		// Sort the weighted values' map by descending order.
		Collections.sort(weightedValues, Collections.reverseOrder());
		
		// Calculate the OWA: by summing the element-wise product of ordinal weights with the sorted weighted values. 
		double owa = 0.0;
		for (Integer i=0; i<weightedValues.size(); i++) {
            owa += ordinalWeights.get(i) * weightedValues.get(i);
		}
		
		return owa;
	}

	
	private Double calculateLinguisticQuantifierWOWA(Map<String,Double> values) {
		LinkedHashMap<String,Double> sortedWeights = sortWeights(relevanceWeights,values);
		Map<String,Double> finalWeights = exponentialOrdinalWeights(sortedWeights);
		Double owa = 0.0;
		for (String key : values.keySet()) {
			owa += finalWeights.get(key)*values.get(key);
		}
		return owa;
	}
	
	
	private Double calculateWOWA(BernsteinInterpolator interpolator, Map<String,Double> values) {
		LinkedHashMap<String,Double> sortedWeights = sortWeights(relevanceWeights,values);
		Map<String,Double> finalWeights = finalWeightsWOWA(interpolator, sortedWeights);
		Double wowa = 0.0;
		for (String key : values.keySet()) {
			wowa += finalWeights.get(key)*values.get(key);
		}
		
		return wowa;
	}
	

	/*
	 * 
	 * Resolver definition.
	 * 
	 * */
	
	@Override	
    public Object resolve(IObservable observable, IContextualizationScope scope, ILocator locator)
            throws KlabValidationException {
		
		Double owa = 0.0;
			
		Map<String, Double> values = new HashMap<String, Double>();

        // OWA is a quantitative metric thus we force values to be double, an exception should be
        // thrown if the observable cannot be forced to a double.
        for(String key : relevanceWeights.keySet()) {
            values.put(key, scope.get(key, IState.class).get(locator, Double.class));
        }

        if (!interpolateWeights) {
        	if (ordinalWeights!=null) {
	        	owa = calculateOWA(relevanceWeights, ordinalWeights, values);
	        } else {
	        	owa = calculateLinguisticQuantifierWOWA(values);
	        }
        } else {
        	
        	owa = calculateWOWA(interpolator, values);
        }
	        
        return owa;
    }

	@Override
	public Type getType() {
		return Type.NUMBER;
	}

    /**
     * Override this to provide initial configuration after prototype and scope have been assigned.
     */
    public void initializeContextualizer() {
        if (relevanceWeights == null) {

            // If weights were not explicitly specified as parameters try to get them from
            // annotations.

            Map<String,Number> rw = new HashMap<>();
            IParameters<String> annotatedInputs = getAnnotatedInputs("criterion");
            Map<String, IAnnotation> annotations = getAnnotations("criterion");

            // Create map of relevance weights iterating over annotated inputs and extracting the
            // annotation weight value.
            // TODO: design a better way to handle multiple parameters of the annotation.
            for(String observable : annotatedInputs.keySet()) {

                Boolean containsWeight = annotations.get(observable).contains("weight");
                if (containsWeight) {
                	rw.put(observable, annotations.get(observable).get("weight", Double.class));
                } else {
                    // If no parameter name is supplied with the annotation, the value is assumed to
                    // be the weight.
                	rw.put(observable, annotations.get(observable).get("value", Double.class));
                }
            }
            
            relevanceWeights = normalizeRelevanceWeights(rw);
        }
    }
	
	@SuppressWarnings("unchecked")
	@Override
	public Object eval(IContextualizationScope scope, Object...params) throws KlabException{
		Parameters<String> parameters = Parameters.create(params);		
		
		OWAResolver resolver = new OWAResolver();		
		
		// First try to import the weights from the resolver's parameters.
		Map<String,Number> rw = parameters.get("weights", Map.class);
				
		// Import the ordinal weights.
		Object rp = parameters.get("risk_profile");
	
		Boolean interpolate = parameters.get("interpolate_weights", Boolean.class);
		
		resolver.interpolateWeights = interpolate;
		
		if (rp instanceof List) { // Ordinal weights explicitly specified. 
			
			List<Double>  normalizedOW = normalizeOrdinalWeights( (List<Number>) rp);
			resolver.ordinalWeights = normalizedOW;
			
			if (rw != null) { // Relevance weights provided as parameter.
				
				resolver.relevanceWeights = normalizeRelevanceWeights(rw);
			
			} else { // Provided with annotations.
				
				resolver.relevanceWeights = null;
			
			}
			
			if (interpolate) {
				resolver.interpolator = new BernsteinInterpolator( cumulativeOrdinalWeights(normalizedOW));
				
			} else {
				resolver.interpolator = null;
			}
		
		} else if(rp instanceof Number) { // Ordinal weights built with risk profile parameter alpha.
			
			resolver.interpolator = null;
			resolver.alpha = (Double) rp;
			
			if (rw != null) { // Weights provided as parameter. 	
			
				resolver.relevanceWeights = normalizeRelevanceWeights(rw);
				resolver.ordinalWeights = null;
			
			} else { // Weights provided by annotations: this is dealt with in initialization method.
				
				resolver.ordinalWeights = null;
				resolver.relevanceWeights = null;
				
			}
		}
		

		return resolver;
	}
		
}
