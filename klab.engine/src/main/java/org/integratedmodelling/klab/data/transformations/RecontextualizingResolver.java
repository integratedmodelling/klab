package org.integratedmodelling.klab.data.transformations;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.components.time.extents.TemporalExtension;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.dataflow.ObservedConcept;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Parameters;

public class RecontextualizingResolver extends AbstractContextualizer implements IResolver<IState>, IExpression {

	IObservable target = null;

	RecontextualizingResolver(Object target) {
		this.target = Observables.INSTANCE.asObservable(target);
	}

	public RecontextualizingResolver() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Type getType() {
		return Type.NUMBER;
	}

	@Override
	public Object eval(IContextualizationScope scope, Object... additionalParameters) {
		IParameters<String> parameters = Parameters.create(additionalParameters);
		return new RecontextualizingResolver(parameters.get("target"));
	}

	@Override
	public IState resolve(IState ret, IContextualizationScope scope) {

		// This is the hackiest of all
		IObservable stobs = ret.getObservable().getBuilder(scope.getMonitor()).withoutAny(IKimConcept.Type.TRAIT).buildObservable();
		IObservation obs = ((IRuntimeScope) scope).getCatalog().get(new ObservedConcept(target, Mode.INSTANTIATION));
		List<IState> states = new ArrayList<>();
		for (IArtifact sub : obs) {
			IObservation subject = (IObservation)sub;
			IState state = subject.getScope().getState(stobs.getType(), ret.getObservable().getUnit());
			if (state != null) {
				states.add(state);
			}
		}

		for (ILocator locator : scope.getScale()) {
			ISpace space = null;
			double result = 0;
			for (IState state : states) {
				space = (ISpace)state.getSpace().at(((IScale)locator).getSpace());
				if (space != null) {
					TemporalExtension extension = ((Time)state.getScale().getTime()).getExtension();
					for (int i = 0; i < extension.size(); i++) {
						Scale scale = Scale.create(space,  ((Time)state.getScale().getTime()).focus(extension.getExtension(i)));
						double value = state.get(scale, Double.class);
						if (!Double.isNaN(value)) {
							result += value;
						}
					}
					ret.set(locator, result);
				}
			}
		}

		return ret;
	}

}
