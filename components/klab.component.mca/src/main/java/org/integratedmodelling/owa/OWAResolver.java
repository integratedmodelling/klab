package org.integratedmodelling.owa;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IStateResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Parameters;

public class OWAResolver extends AbstractContextualizer implements IStateResolver, IExpression {

	private HashMap<String,Double> relevanceWeights;
	private HashMap<Integer,Double> ordinalWeights;
	
	private HashMap<Integer,Double> buildOrdinalWeights(Integer nObservations, Double riskProfile){
		HashMap<Integer,Double> w = new HashMap<Integer,Double>();
		for(int i=0;i<nObservations;i++){
			// TODO: here a proper function that calculates ordinal weights from risk profile.
			w.put(i,riskProfile); 
		}
		return w;
	} 
	
	private void setOrdinalWeights(Integer nObservations, Double riskProfile) {
		this.ordinalWeights = buildOrdinalWeights(nObservations,riskProfile);
	}
		
	private Double calculateOWA(HashMap<String,Double> relevanceWeights, HashMap<Integer,Double> ordinalWeights, HashMap<String,Double> values) {
		
		// Sort the values' map by ascending order.
		LinkedHashMap<String, Double> sortedValues = values.entrySet()
			    .stream()
			    .sorted(Map.Entry.comparingByValue())
			    .collect(Collectors.toMap(
			        Map.Entry::getKey,
			        Map.Entry::getValue,
			        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
		
		// Calculate the weighted average: 
		// Keys on the ordinal weights map correspond to the passage order while iterating over the values' map. 
		// Keys on the relevance weights map correspond to the values' keys, as they are associated with the observables irrespective of their values.
		int i=0;
		Double acc = 0.0;
		for (String key : sortedValues.keySet()) {
	        acc += sortedValues.get(key) * relevanceWeights.get(key) * ordinalWeights.get(i);
	        i++;
		}
		acc /= Double.valueOf(values.size());
		
		return acc;
	}

	@Override	
	public Object resolve(IObservable observable, IContextualizationScope scope, ILocator locator) throws KlabValidationException{
		
		HashMap<String,Double> values = new HashMap<String,Double>();
		
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
		
		// Get OWA parameters: relevance weights and risk profile.
		@SuppressWarnings("unchecked")
		HashMap<String,Double> rw = parameters.get("weights", HashMap.class);
		Double riskProfile = parameters.get("risk_profile", Double.class);
		
		resolver.relevanceWeights = rw;		
		resolver.setOrdinalWeights(rw.size(),riskProfile);

		
		return resolver;
	}
	
	
}

