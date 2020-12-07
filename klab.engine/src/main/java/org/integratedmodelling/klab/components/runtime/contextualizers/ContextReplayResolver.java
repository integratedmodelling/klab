package org.integratedmodelling.klab.components.runtime.contextualizers;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.components.runtime.observations.State;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public class ContextReplayResolver implements IResolver<IArtifact>, IExpression {

	static final public String FUNCTION_ID = "klab.runtime.replay";

	private IObservation observation;

	// don't remove - only used as expression
	public ContextReplayResolver() {
	}

	public ContextReplayResolver(IObservation observation) {
		this.observation = observation;
		if (!(observation instanceof State)) {
			throw new KlabUnimplementedException(
					"Replaying of temporal observation is unimplemented for observations other than states");
		}
	}

	public static IServiceCall getServiceCall(String artifactName) throws KlabValidationException {
		return KimServiceCall.create(FUNCTION_ID, "artifact", artifactName);
	}

	@Override
	public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
		return new ContextReplayResolver(
				context.getArtifact(parameters.get("artifact", String.class), IObservation.class));
	}

	@Override
	public IArtifact.Type getType() {
		return this.observation.getType();
	}

	@Override
	public IArtifact resolve(IArtifact ret, IContextualizationScope context) throws KlabException {
		((Observation) this.observation).setReplayingTime(context.getScale().getTime());
		return this.observation;
	}
}
