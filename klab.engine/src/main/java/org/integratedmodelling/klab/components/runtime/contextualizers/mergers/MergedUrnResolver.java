package org.integratedmodelling.klab.components.runtime.contextualizers.mergers;

import java.util.List;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.model.contextualization.IContextualizer;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.model.contextualization.IStateResolver;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.utils.Pair;

public class MergedUrnResolver implements IExpression, IResolver<IArtifact> {

	public final static String FUNCTION_ID = "klab.runtime.timeseries";

	private List<String> urns;
	private Type type;
	private ComputableSchedule schedule;

	private IState targetState;

	// don't remove - only used as expression
	public MergedUrnResolver() {
	}

	public MergedUrnResolver(List<String> urns, IContextualizationScope context) {
		this.urns = urns;
		this.type = context.getTargetSemantics().getArtifactType();
	}

	public static IServiceCall getServiceCall(List<String> mergedUrns) {
		return KimServiceCall.create(FUNCTION_ID, "urns", mergedUrns);
	}

	@SuppressWarnings("unchecked")
	@Override
	public IArtifact resolve(IArtifact observation, IContextualizationScope context) {

		if (this.schedule == null) {

			IConcept inherent = Observables.INSTANCE
					.getInherentType(((IObservation) observation).getObservable().getType());
			Pair<String, IArtifact> target = ((IRuntimeScope) context).findArtifact(Observable.promote(inherent));

			if (target == null || !(target.getSecond() instanceof IState)) {
				throw new IllegalArgumentException("Cannot run a merging resolver on a non-state");
			}

			this.targetState = (IState) target.getSecond();
			this.schedule = new ComputableSchedule(context.getTargetSemantics(), (IRuntimeScope) context);
			schedule.add(urns);
		}

		IState target = this.targetState;

		for (IContextualizer contextualizer : this.schedule.getComputation(context.getScale().getTime(), context)) {

			/*
			 * Establish the container for the output: switch the storage in the state to
			 * the type needed in the compute chain, creating a layer if necessary. This is
			 * the layer to WRITE INTO.
			 */
			target = this.targetState.as(contextualizer.getType());

			if (contextualizer instanceof IStateResolver) {

				/*
				 * pass the distributed computation to the runtime provider for possible
				 * parallelization instead of hard-coding a loop here.
				 */
				target = (IState) Klab.INSTANCE.getRuntimeProvider()
						.distributeComputation((IStateResolver) contextualizer, target, context, context.getScale());

			} else if (contextualizer instanceof IResolver) {
				target = (IState) ((IResolver<IArtifact>) contextualizer).resolve(target, context);
			}
		}

		return this.targetState;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
		return new MergedUrnResolver(parameters.get("urns", List.class), context);
	}

	@Override
	public Type getType() {
		return type;
	}

}
