package org.integratedmodelling.owa;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

	private Map<String,Number> relevanceWeights;
	private Map<Integer,Double> ordinalWeights;
	
	private Map<Integer,Double> buildOrdinalWeights(Integer nObservations, Double riskProfile){
		Map<Integer,Double> w = new HashMap<Integer,Double>();
		for(int i=0;i<nObservations;i++){
			// TODO: here a proper function that calculates ordinal weights from risk profile.
			w.put(i,riskProfile); 
		}
		return w;
	} 
	
	private void setOrdinalWeights(Integer nObservations, Double riskProfile) {
		this.ordinalWeights = buildOrdinalWeights(nObservations,riskProfile);
	}
		
	private Double calculateOWA(Map<String,Number> relevanceWeights, Map<Integer,Double> ordinalWeights, Map<String,Double> values) {
		
		// Sort the values' map by ascending order.
		LinkedHashMap<String, Double> sortedValues = values.entrySet()
			    .stream()
			    .sorted(Map.Entry.comparingByValue())
			    .collect(Collectors.toMap(
			        Map.Entry::getKey,
			        Map.Entry::getValue,
			        (oldValue, newValue) -> oldValue, LinkedHashMap<String, Double>::new));
		
		// Calculate the weighted average: 
		// Keys on the ordinal weights map correspond to the passage order while iterating over the values' map. 
		// Keys on the relevance weights map correspond to the values' keys, as they are associated with the observables irrespective of their values.
		int i=0;
		double acc = 0.0;
		for (String key : sortedValues.keySet()) {
	        Double sv = sortedValues.get(key);
            double rw = relevanceWeights.get(key).doubleValue();
            Double ow = ordinalWeights.get(i);
            acc += sv.doubleValue() * rw * ow.doubleValue();
	        i++;
		}
		acc /= values.size();
		
		return acc;
	}

	@Override	
	public Object resolve(IObservable observable, IContextualizationScope scope, ILocator locator) throws KlabValidationException{
		
		Map<String,Double> values = new HashMap<String,Double>();
				
		// OWA is a quantitative metric thus we force values to be double, an exception should be 
		// thrown if the observable cannot be forced to a double.
		for (String key : relevanceWeights.keySet()) {
			values.put(key, scope.get(key, IState.class).get(locator, Double.class)); 
		} 
		
		Double owa = calculateOWA(relevanceWeights,ordinalWeights,values);
		
		return owa;
	}

	@Override
	public Type getType() {
		return Type.NUMBER;
	}

	
	@Override
	public Object eval(IContextualizationScope scope, Object...params) throws KlabException{
		Parameters<String> parameters = Parameters.create(params);		
		
		OWAResolver resolver = new OWAResolver();		
		
		// First try to import the weights from the resolver's parameters.
		@SuppressWarnings("unchecked")
		Map<String,Number> rw = parameters.get("weights", Map.class);
		
		// If weights were not explicitly specified as parameters try to get them from annotations.
		if (rw == null) {
			
			rw = new HashMap<>(); 
			IParameters<String> annotatedInputs = getAnnotatedInputs("criterion");
			Map<String, IAnnotation> annotations = getAnnotations("criterion");
			
			// Create map of relevance weights iterating over annotated inputs and extracting the
			// annotation weight value.
			// TODO: design a better way to handle multiple parameters of the annotation.
			for(String observable: annotatedInputs.keySet()) {
				
				Boolean containsWeight = annotations.get(observable).contains("weight");
				if (containsWeight) {
					rw.put(observable, annotations.get(observable).get("weight", Double.class));
				}	
				else { 
					// If no parameter name is supplied with the annotation, the value is assumed to be the weight.
					rw.put(observable, annotations.get(observable).get("value", Double.class));
				}	
			}  
		}
		
		resolver.relevanceWeights = rw;
		Double riskProfile = parameters.get("risk_profile", Double.class);
		resolver.setOrdinalWeights(rw.size(),riskProfile);

		return resolver;
	}
		
}
