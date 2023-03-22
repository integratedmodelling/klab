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

public class OWAResolver extends AbstractContextualizer implements IStateResolver, IExpression {

	private Map<String,Double> relevanceWeights;
	private List<Double> ordinalWeights;
	private Double alpha;
	private Boolean interpolateWeights;
	
	private Boolean combinationsComputed;
	
	private BernsteinInterpolator interpolator;
		
	/*
	 * Weights normalization.
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
	 * Cumulative weights for WOWA.
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
	
	private Map<String,Double> cumulativeRelevanceWeights(Map<String,Double> sortedRelevanceWeights){
		Map<String,Double> cumulativeRW = new LinkedHashMap<>();
		
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
	 * Sort weights by observable value.
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
	 * Final weights for interpolated WOWA. TODO: weight map should be linked as order matters.
	 * */
	private Map<String,Double> finalWeightsWOWA(BernsteinInterpolator interpolator, Map<String,Double> sortedRelevanceWeights){
		
		Map<String,Double> cumulativeRW = cumulativeRelevanceWeights(sortedRelevanceWeights);
		
		Map<String,Double> finalWeights = new HashMap<>();
		
		Double val;
		Double previous = 0.0;
		for(String key : cumulativeRW.keySet()){
			val = interpolator.getInterpolatedValue(cumulativeRW.get(key));
			finalWeights.put(key,val - previous);
			previous = val;
		}
		return finalWeights;
	}
	
	
	
	
	/*
	 * Ordinal weights for linguistic quantifier OWA. TODO: weight map should be linked as order matters.
	 * */
	private Map<String,Double> exponentialOrdinalWeights(Map<String,Double> sortedRelevanceWeights){
		
		Map<String,Double> cumulativeRW = cumulativeRelevanceWeights(sortedRelevanceWeights); 

		Map<String,Double> finalWeights = new HashMap<>();
		Double val;
		Double previous = 0.0;
		for(String key : cumulativeRW.keySet()){
			val = Math.pow(cumulativeRW.get(key),alpha);
			finalWeights.put(key,val - previous);
			previous = val; 
		}
		return finalWeights;
	} 
	
	
	/*
	 * Calculation of aggregate indices.
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

	private Double calculateLinguisticQuantifierOWA(Map<String,Double> values) {
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
	 * Resolver.
	 * */
	@Override	
    public Object resolve(IObservable observable, IContextualizationScope scope, ILocator locator)
            throws KlabValidationException {
		
		Double owa = 0.0;
		
		if (!combinationsComputed){
					
			combinationsComputed = true;
		} else {
			
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
		        	owa = calculateLinguisticQuantifierOWA(values);
		        }
	        } else {
	        	
	        	owa = calculateWOWA(interpolator, values);
	        	
	        }
	        
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
		
		interpolateWeights = parameters.get("interpolate_weights", Boolean.class);
		
		if (rp instanceof List) { // Ordinal weights explicitly specified. 
			
			resolver.ordinalWeights = normalizeOrdinalWeights( (List<Number>) rp);
			
			if (rw != null) { // Relevance weights provided as parameter.
				
				resolver.relevanceWeights = normalizeRelevanceWeights(rw);
			
			} else { // Provided with annotations.
				
				resolver.relevanceWeights = null;
			
			}
		
		} else if(rp instanceof Number) { // Ordinal weights built with risk profile parameter alpha.
			
			resolver.alpha = (Double) rp;
			
			if (rw != null) { // Weights provided as parameter. 	
			
				resolver.relevanceWeights = normalizeRelevanceWeights(rw);
				resolver.ordinalWeights = null;
			
			} else { // Weights provided by annotations: this is dealt with in initialization method.
				
				resolver.ordinalWeights = null;
				resolver.relevanceWeights = null;
				
			}
		}
		if (interpolateWeights) {
			combinationsComputed = false;
			
			if (ordinalWeights!=null) {
				interpolator = new BernsteinInterpolator( cumulativeOrdinalWeights(ordinalWeights) );
			}	else {
				interpolator = null;
			}
			
		} else {
			interpolator = null;
			combinationsComputed = true;
		}

		return resolver;
	}
		
}
