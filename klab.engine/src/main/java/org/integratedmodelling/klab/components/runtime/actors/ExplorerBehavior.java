package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.kactors.api.IKActorsValue.Type;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.actors.Action;
import org.integratedmodelling.klab.api.extensions.actors.Behavior;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.observations.IKnowledgeView;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;
import org.integratedmodelling.klab.engine.runtime.api.IActorIdentity;
import org.integratedmodelling.klab.rest.ViewSetting;
import org.integratedmodelling.klab.rest.ViewSetting.Operation;
import org.integratedmodelling.klab.rest.ViewSetting.Target;

import akka.actor.typed.ActorRef;

/**
 * Messages dedicated to talking to the k.LAB explorer view.
 * 
 * <ul>
 * <li></li>
 * </ul>
 * 
 * @author Ferd
 *
 */
@Behavior(id = "explorer", version = Version.CURRENT)
public class ExplorerBehavior {

	@Action(id = "show", fires = IKActorsValue.Type.OBSERVATION, description = "Show an artifact in the explorer,identified by name, semantics, or the artifact itself."
			+ " When selected, fire the artifact if all OK, an error, or empty if it was shown already.")
	public static class Show extends KlabActionExecutor {

		String listenerId = null;

		public Show(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		void run(KlabActor.Scope scope) {

			if (!arguments.getUnnamedKeys().isEmpty()) {

				Object arg = arguments.get(arguments.getUnnamedKeys().get(0));

				ViewSetting message = new ViewSetting();
				message.setOperation(Operation.Show);
				Object what = KlabActor.evaluate(arg, scope);
				if (what instanceof IObservation) {
					message.setTarget(Target.Observation);
					message.setTargetId(((IObservation) what).getId());
				} else if (what instanceof IKnowledgeView) {
					message.setTarget(Target.View);
					message.setTargetId(((IKnowledgeView) what).getId());
				} else if (what instanceof IObservable && scope.identity instanceof ISession) {
					IObservation observation = ((ISession) scope.identity).getState()
							.getObservation((IObservable) what);
					if (observation != null) {
						message.setTarget(Target.Observation);
						message.setTargetId(observation.getId());
					}
				} else {
					if (arg instanceof IKActorsValue && ((IKActorsValue) arg).getType() == Type.CONSTANT) {
						switch (what.toString()) {
						case "TREE":
							message.setTarget(Target.Tree);
							break;
						case "REPORT":
							message.setTarget(Target.Report);
							break;
						case "DATAFLOW":
							message.setTarget(Target.Dataflow);
							break;
						}
					} else if (what != null) {
						IArtifact artifact = ((ISession) scope.identity).getState().getArtifact(what.toString());
						if (artifact instanceof IObservation) {
							message.setTarget(Target.Observation);
							message.setTargetId(((IObservation) artifact).getId());
						} else if (artifact instanceof IKnowledgeView) {
							message.setTarget(Target.View);
							message.setTargetId(((IKnowledgeView) artifact).getId());
						}
					}
				}

				if (message.getTarget() != null) {
					identity.getParentIdentity(ISession.class).getMonitor().send(IMessage.MessageClass.UserInterface,
							IMessage.Type.ViewSetting, message);
				}

			}
		}
	}

	@Action(id = "hide", fires = IKActorsValue.Type.OBSERVATION, description = "Hide an artifact in the explorer, identified by name, semantics, or the artifact itself."
			+ " When selected, fire the artifact if all OK, an error, or empty if it wasn't shown.")
	public static class Hide extends KlabActionExecutor {

		String listenerId = null;

		public Hide(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		void run(KlabActor.Scope scope) {

			if (!arguments.getUnnamedKeys().isEmpty()) {
				Object arg = arguments.get(arguments.getUnnamedKeys().get(0));

				ViewSetting message = new ViewSetting();
				message.setOperation(Operation.Hide);
				Object what = KlabActor.evaluate(arg, scope);
				if (what instanceof IObservation) {
					message.setTarget(Target.Observation);
					message.setTargetId(((IObservation) what).getId());
				} else if (what instanceof IKnowledgeView) {
					message.setTarget(Target.View);
					message.setTargetId(((IKnowledgeView) what).getId());
				} else if (what instanceof IObservable && scope.identity instanceof ISession) {
					IObservation observation = ((ISession) scope.identity).getState()
							.getObservation((IObservable) what);
					if (observation != null) {
						message.setTarget(Target.Observation);
						message.setTargetId(observation.getId());
					}
				} else {
					if (arg instanceof IKActorsValue && ((IKActorsValue) arg).getType() == Type.CONSTANT) {
						switch (what.toString()) {
						case "TREE":
							message.setTarget(Target.Tree);
							break;
						case "REPORT":
							message.setTarget(Target.Report);
							break;
						case "DATAFLOW":
							message.setTarget(Target.Dataflow);
							break;
						}
					} else if (arg != null) {
						IArtifact artifact = ((ISession) scope.identity).getState().getArtifact(arg.toString());
						if (artifact instanceof IObservation) {
							message.setTarget(Target.Observation);
							message.setTargetId(((IObservation) artifact).getId());
						} else if (artifact instanceof IKnowledgeView) {
							message.setTarget(Target.View);
							message.setTargetId(((IKnowledgeView) artifact).getId());
						}
					}
				}

				if (message.getTarget() != null) {
					identity.getParentIdentity(ISession.class).getMonitor().send(IMessage.MessageClass.UserInterface,
							IMessage.Type.ViewSetting, message);
				}
			}
		}
	}

}