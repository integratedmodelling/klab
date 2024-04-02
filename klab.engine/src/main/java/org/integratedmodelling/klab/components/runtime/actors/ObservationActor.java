package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.klab.Annotations;
import org.integratedmodelling.klab.api.actors.IBehavior.Action;
import org.integratedmodelling.klab.api.auth.IActorIdentity.KlabMessage;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.Transition;
import org.integratedmodelling.klab.components.runtime.observations.Observation;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.ReceiveBuilder;

public class ObservationActor extends KlabActor {

	boolean transitionActionChecked;
	Action transitionAction;
	static public Behavior<KlabMessage> create(Observation observation, String appId) {
		return Behaviors.setup(ctx -> new ObservationActor(ctx, observation, appId));
	}

	@Override
	protected ReceiveBuilder<KlabMessage> configure() {
		return super.configure().onMessage(Transition.class, this::onTransition);
	}

	/**
	 * Called when a transition involving the observation is sent. The message
	 * contains the runtime scope including the scale of interest.
	 * 
	 * @param message
	 * @return
	 */
	protected Behavior<KlabMessage> onTransition(Transition message) {

		if (!transitionActionChecked) {
			if (behavior != null) {
				for (Action action : behavior.getActions()) {
					if (Annotations.INSTANCE.hasAnnotation(action, "transition")) {
						this.transitionAction = action;
						break;
					}
				}
			}
			transitionActionChecked = true;
		}
		
		if (transitionAction != null) {
			run(transitionAction, message.getScope());
		}
		
		return Behaviors.same();
	}

	public ObservationActor(ActorContext<KlabMessage> context, Observation observation, String appId) {
		super(context, observation, appId);
	}

}
