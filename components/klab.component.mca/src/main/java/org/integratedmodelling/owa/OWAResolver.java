package org.integratedmodelling.owa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	// Used only in the case that  relevance weights are specified with annotations and 
	// ordinal weights are not passed explicitly. 
	private Double riskProfileParameter;
	
	
	
	private List<Double> buildOrdinalWeights(Integer nObservations, Double riskProfile){
		List<Number> ow = new ArrayList<>();
		for(int i=0;i<nObservations;i++){
			// TODO: here a proper function that calculates ordinal weights from risk profile.
			ow.add(riskProfile); 
		}
		List<Double> ordinalWeights = normalizeOrdinalWeights(ow);
		return ordinalWeights;
	} 
	
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
	
	private void setOrdinalWeights(Integer nObservations, Double riskProfile) {
		this.ordinalWeights = buildOrdinalWeights(nObservations,riskProfile);
	}
		
	private Double calculateOWA(Map<String,Double> relevanceWeights, List<Double> ordinalWeights, Map<String,Double> values) {
		
		// Weight the values according to their relevance.
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

	@Override	
    public Object resolve(IObservable observable, IContextualizationScope scope, ILocator locator)
            throws KlabValidationException {

        Map<String, Double> values = new HashMap<String, Double>();

        // OWA is a quantitative metric thus we force values to be double, an exception should be
        // thrown if the observable cannot be forced to a double.
        for(String key : relevanceWeights.keySet()) {
            values.put(key, scope.get(key, IState.class).get(locator, Double.class));
        }

        Double owa = calculateOWA(relevanceWeights, ordinalWeights, values);

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
            
            if (ordinalWeights == null) {
            	setOrdinalWeights(relevanceWeights.size(), riskProfileParameter);
            }	

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
		
		if (rp instanceof List) {
			
			resolver.ordinalWeights = normalizeOrdinalWeights( (List<Number>) rp);
			
			if (rw != null) {
				
				resolver.relevanceWeights = normalizeRelevanceWeights(rw);
			
			} else {
				
				resolver.relevanceWeights = null;
			
			}
		
		} else if(rp instanceof Number) {
			
			resolver.riskProfileParameter = (Double) rp;
			
			if (rw != null) { // Weights provided as parameter. 	
			
				resolver.relevanceWeights = normalizeRelevanceWeights(rw);
				resolver.setOrdinalWeights(rw.size(), (Double) rp);
			
			} else { // Weights provided by annotations: this is dealt with in initialization method.
				
				resolver.ordinalWeights = null;
				resolver.relevanceWeights = null;
				
			}
		}

		return resolver;
	}
		
}
