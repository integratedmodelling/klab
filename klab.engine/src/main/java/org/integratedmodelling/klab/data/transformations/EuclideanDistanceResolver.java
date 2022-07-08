package org.integratedmodelling.klab.data.transformations;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservedConcept;
import org.integratedmodelling.klab.api.model.contextualization.IProcessor;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.runtime.RuntimeScope;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.dataflow.ObservedConcept;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.utils.Parameters;

/**
 * Compute a variety of euclidean distance-based indicators. TODO figure out if
 * this is actually doing this, as opposed to implementing our modelers'
 * excrement.
 * 
 * @author Ferd
 *
 */
public class EuclideanDistanceResolver extends AbstractContextualizer implements IResolver<IState>, IExpression, IProcessor {

	private IContextualizationScope scope;
	private IParameters<String> parameters;

	public EuclideanDistanceResolver() {
	}

	public EuclideanDistanceResolver(IParameters<String> parameters, IContextualizationScope context) {
		this.parameters = parameters;
		this.scope = context;
	}

	@Override
	public Type getType() {
		return Type.NUMBER;
	}

	@Override
	public Object eval(IContextualizationScope context, Object...parameters) throws KlabException {
		return new EuclideanDistanceResolver(Parameters.create(parameters), context);
	}

	@Override
	public IState resolve(IState ret, IContextualizationScope context) throws KlabException {

		Map<IObservedConcept, IObservation> catalog = ((IRuntimeScope) context).getCatalog();

		Set<IState> targets = extractStates("target", catalog, context);
		Set<IState> sources = extractStates("source", catalog, context);
		Object weight = parameters.get("weights");

		for (ILocator locator : context.getScale()) {
		    double sum = Double.NaN;
            for (IState state : sources) {
                Object value = state.get(locator);
                if (value instanceof Number) {
                    double w = 1.0/(double)sources.size();
                    if (weight instanceof Number) {
                        w = ((Number) weight).doubleValue();
                    } else if (weight instanceof Map) {
                        // TODO look up the observable in the map and get the weight; cache the search
                        // based on observable name for later reference
                    }
                    double factor = w * (((Number) value).doubleValue() * ((Number) value).doubleValue());
                    sum = Double.isNaN(sum) ? factor : (sum + factor);
                }

            }
			switch (parameters.get("indicator", "distance")) {
			case "distance":
			    ret.set(locator, Double.isNaN(sum) ? null : Math.sqrt(sum));

				break;
			case "similarity":
				ret.set(locator, Double.isNaN(sum) ? null : (1.0 / (1.0 + Math.sqrt(sum))));

				break;
			}
		}

		return ret;

	}

	private Set<IState> extractStates(String string, Map<IObservedConcept, IObservation> catalog,
			IContextualizationScope scope) {
		Set<IState> ret = new HashSet<>();
		Object o = parameters.get(string);
		if (o != null) {
			ret.addAll(extractState(o, catalog, scope));
		} else if (o instanceof Collection) {
			for (Object oo : ((Collection<?>) o)) {
				ret.addAll(extractState(oo, catalog, scope));
			}
		}
		return ret;
	}

	private Set<IState> extractState(Object o, Map<IObservedConcept, IObservation> catalog,
			IContextualizationScope scope) {
		Set<IState> ret = new HashSet<>();
		if (o instanceof IKimConcept) {
			IConcept concept = Concepts.INSTANCE.declare((IKimConcept) o);
			if (concept.is(IKimConcept.Type.ROLE)) {
				for (IObservedConcept key : catalog.keySet()) {
					for (IConcept role : key.getObservable().getContextualRoles()) {
						if (((RuntimeScope) scope).cached_is(role, concept) && catalog.get(key) instanceof IState) {
							ret.add((IState) catalog.get(key));
						}
					}
				}
			} else if (concept.is(IKimConcept.Type.QUALITY)) {
				IObservation obs = catalog.get(new ObservedConcept(concept));
				if (obs instanceof IState) {
					ret.add((IState) obs);
				}
			}
		} else if (o instanceof String) {
			IArtifact art = this.scope.getArtifact((String) o);
			if (art instanceof IState) {
				ret.add((IState) art);
			}
		}
		return ret;
	}

}
