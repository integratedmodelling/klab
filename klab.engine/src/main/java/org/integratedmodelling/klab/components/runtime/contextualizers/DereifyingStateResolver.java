package org.integratedmodelling.klab.components.runtime.contextualizers;

import java.util.Collection;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.Annotations;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.model.contextualization.IProcessor;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.runtime.RuntimeScope;
import org.integratedmodelling.klab.data.storage.MergingState;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public class DereifyingStateResolver extends AbstractContextualizer implements IResolver<IArtifact>, IProcessor, IExpression {

	static final public String FUNCTION_ID = "klab.runtime.dereifiers.merge";

	private Type type;
	private MergingState state;
	private IConcept distributingArtifact;
	private IConcept distributedQuality;

	// don't remove - only used as expression
	public DereifyingStateResolver() {
	}

	public DereifyingStateResolver(IConcept distributing, IConcept inherent, IArtifact.Type type) {
		this.type = type;
		this.distributingArtifact = distributing;
		this.distributedQuality = inherent;
	}

	public static IServiceCall getServiceCall(IConcept distributing, IConcept inherent, IArtifact.Type type)
			throws KlabValidationException {
		return KimServiceCall.create(FUNCTION_ID, "distributing", distributing.getDefinition(), "inherent",
				inherent.getDefinition(), "destination", type.name());
	}

	@Override
	public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
		return new DereifyingStateResolver(
				Observables.INSTANCE.declare(parameters.get("distributing", String.class)).getType(),
				Observables.INSTANCE.declare(parameters.get("inherent", String.class)).getType(),
				IArtifact.Type.valueOf(parameters.get("destination", String.class)));
	}

	@Override
	public IArtifact.Type getType() {
		return type;
	}

	@Override
	public IArtifact resolve(IArtifact ret, IContextualizationScope context) throws KlabException {
		this.state = new MergingState((IState) ret);
		this.state.getAnnotations().addAll(Annotations.INSTANCE.collectAnnotations(ret, ((IState)ret).getObservable()));
		Collection<IObservation> distributing = context.getObservations(distributingArtifact);
		if (distributing.size() > 0) {
			for (IArtifact object : distributing) {
				if (object instanceof IDirectObservation) {
					for (IState state : ((IDirectObservation) object).getStates()) {
						if (((RuntimeScope)context).cached_is(state.getObservable().getType(), distributedQuality)) {
							this.state.add(state);
						}
					}
				}
			}
		}
		return this.state;
	}
}
