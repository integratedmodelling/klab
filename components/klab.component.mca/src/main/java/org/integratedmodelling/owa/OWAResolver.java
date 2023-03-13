package org.integratedmodelling.owa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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
	private List<Number> ordinalWeights;
	
	// Used only in the case that  relevance weights are specified with annotations and 
	// ordinal weights are not passed explicitly. 
	private Double riskProfileParameter;
	
	private List<Number> buildOrdinalWeights(Integer nObservations, Double riskProfile){
		List<Number> ow = new ArrayList<>();
		for(int i=0;i<nObservations;i++){
			// TODO: here a proper function that calculates ordinal weights from risk profile.
			ow.add(riskProfile); 
		}
		return ow;
	} 
	
	private void setOrdinalWeights(Integer nObservations, Double riskProfile) {
		this.ordinalWeights = buildOrdinalWeights(nObservations,riskProfile);
	}
		
	private Double calculateOWA(Map<String,Number> relevanceWeights, List<Number> ordinalWeights, Map<String,Double> values) {
		
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
            Double ow = ordinalWeights.get(i).doubleValue();
            acc += sv.doubleValue() * rw * ow.doubleValue();
	        i++;
		}
		acc /= values.size();
		
		return acc;
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

            relevanceWeights = new HashMap<>();
            IParameters<String> annotatedInputs = getAnnotatedInputs("criterion");
            Map<String, IAnnotation> annotations = getAnnotations("criterion");

            // Create map of relevance weights iterating over annotated inputs and extracting the
            // annotation weight value.
            // TODO: design a better way to handle multiple parameters of the annotation.
            for(String observable : annotatedInputs.keySet()) {

                Boolean containsWeight = annotations.get(observable).contains("weight");
                if (containsWeight) {
                    relevanceWeights.put(observable, annotations.get(observable).get("weight", Double.class));
                } else {
                    // If no parameter name is supplied with the annotation, the value is assumed to
                    // be the weight.
                    relevanceWeights.put(observable, annotations.get(observable).get("value", Double.class));
                }
            }
            
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
		resolver.relevanceWeights = rw;
				
		// Import the ordinal weights.
		Object rp = parameters.get("risk_profile");
		
		if (rp instanceof List) {
			resolver.ordinalWeights = (List<Number>) rp;
		} else if(rp instanceof Number) {
			resolver.riskProfileParameter = (Double) rp;
			if (rw != null) {
				resolver.setOrdinalWeights(rw.size(), (Double) rp);
			} else {
				resolver.ordinalWeights = null;
			}
		}

		return resolver;
	}
		
}
