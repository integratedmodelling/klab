package org.integratedmodelling.owa;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IStateResolver;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Parameters;

public class OWAResolver extends AbstractContextualizer implements IStateResolver, IExpression {

	private HashMap<Integer,Double> relevanceWeights;
	private HashMap<Integer,Double> ordinalWeights;
	
	private HashMap<Integer,Double> buildOrdinalWeights(Integer nObs, Double riskProfile){
		HashMap<Integer,Double> w = new HashMap<Integer,Double>();
		for(int i=0;i<nObs;i++){
			w.put(i,riskProfile); // TODO: here a proper function that calculates ordinal weights from risk profile.
		}
		return w;
	} 
	
	private void setOrdinalWeights(Integer nObs, Double riskProfile) {
		this.ordinalWeights = buildOrdinalWeights(nObs,riskProfile);
	}
		
	private Double calculateOWA(HashMap<Integer,Double> relevanceWeights, HashMap<Integer,Double> ordinalWeights, HashMap<Integer,Double> values) {
		
		// Sort the values' map by ascending order.
		LinkedHashMap<Integer, Double> sortedValues = values.entrySet()
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
		for (Integer key : sortedValues.keySet()) {
	        acc += sortedValues.get(key) * relevanceWeights.get(key) * ordinalWeights.get(i);
	        i++;
		}
		acc /= Double.valueOf(values.size());
		
		return acc;
	}

	@Override	
	public Object resolve(IObservable observable, IContextualizationScope scope, ILocator locator) throws KlabValidationException{
		
		// OWA is a quantitative metric thus we force values to be double, an exception should be thrown if the observable cannot be forced to a double.
		Double value1 = scope.get("obs1", Number.class).doubleValue();
		Double value2 = scope.get("obs2", Number.class).doubleValue();
		Double value3 = scope.get("obs3", Number.class).doubleValue();
		Double value4 = scope.get("obs4", Number.class).doubleValue();
		Double value5 = scope.get("obs5", Number.class).doubleValue();
		
		HashMap<Integer,Double> valMap = new HashMap<Integer,Double>();
		valMap.put(0, value1);
		valMap.put(1, value2);
		valMap.put(2, value3);
		valMap.put(3, value4);
		valMap.put(4, value5);
		
		Double owa = calculateOWA(relevanceWeights,ordinalWeights,valMap);
		
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
		
		Double relevance1 = parameters.get("relevance1", Double.class);
		Double relevance2 = parameters.get("relevance2", Double.class);
		Double relevance3 = parameters.get("relevance3", Double.class);
		Double relevance4 = parameters.get("relevance4", Double.class);
		Double relevance5 = parameters.get("relevance5", Double.class);
		
		HashMap<Integer,Double> rw = new HashMap<>();
		rw.put(0, relevance1);rw.put(1, relevance2);rw.put(2, relevance3);rw.put(3, relevance4);rw.put(4, relevance5);
		
		resolver.relevanceWeights = rw;
		
		Double riskProfile = parameters.get("risk_profile", Double.class);
//		resolver.setOrdinalWeights(relevanceWeights.size(),riskProfile);
		resolver.setOrdinalWeights(rw.size(),riskProfile);

		
		return resolver;
	}
	
	
}

