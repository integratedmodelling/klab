package org.integratedmodelling.klab.components.runtime.actors;

import java.util.concurrent.Future;

import org.integratedmodelling.kactors.api.IKActorsValue.Type;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Actors;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.actors.Action;
import org.integratedmodelling.klab.api.extensions.actors.Behavior;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.ISession.ObservationListener;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;
import org.integratedmodelling.klab.engine.runtime.api.IActorIdentity;
import org.integratedmodelling.klab.utils.Pair;

import akka.actor.typed.ActorRef;

@Behavior(id = "object", version = Version.CURRENT)
public class ObjectBehavior {

	@Action(id = "observe", fires = Type.OBSERVATION)
	public static class Observe extends KlabAction {

		public Observe(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		void run(KlabActor.Scope scope) {

			if (this.identity instanceof ISubject) {
				Object arg = evaluateArgument(0, scope);
				if (arg instanceof IObservable) {
					try {
						Future<IObservation> future = ((ISubject) identity).observe(((IObservable) arg).getDefinition());
						fire(future.get(), true);
					} catch (Throwable e) {
						fail(e);
					}
				}
			} else {
				fail(this.identity + ": observations can only be made within subjects");
			}

		}
	}

	@Action(id = "stop")
	public static class MoveAway extends KlabAction {

		public MoveAway(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		void run(KlabActor.Scope scope) {
			// TODO Auto-generated method stub

		}

	}

	@Action(id = "bind")
	public static class Bind extends KlabAction {

		public Bind(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		void run(KlabActor.Scope scope) {
			IObservable what = Actors.INSTANCE.getArgument(arguments, IObservable.class);
			String behavior = Actors.INSTANCE.getArgument(arguments, String.class);
			IKimExpression filter = Actors.INSTANCE.getArgument(arguments, IKimExpression.class);
			if (what == null || behavior == null || Actors.INSTANCE.getBehavior(behavior) == null) {
				// TODO improve message
				error("error in bind action: behavior or observable not specified or recognized");
			} else {
				scope.runtimeScope.getBehaviorBindings().put(what.getType(), new Pair<>(behavior, filter));
			}
		}
	}

	/**
	 * Install a listener in a context that will fire an object to the sender
	 * whenever it is resolved, optionally matching a type.
	 * 
	 * @author Ferd
	 *
	 */
	@Action(id = "when")
	public static class When extends KlabAction {

		String listener;

		public When(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
			// TODO filters
		}

		@Override
		void run(KlabActor.Scope scope) {
			this.listener = scope.getMonitor().getIdentity().getParentIdentity(ISession.class)
					.addObservationListener(new ISession.ObservationListener() {
				@Override
				public void newObservation(IObservation observation, ISubject context) {
					// TODO filter if a filter was configured
					fire(observation, false);
				}

				@Override
				public void newContext(ISubject context) {
				}
			});
		}

		@Override
		public void dispose() {
			scope.getMonitor().getIdentity().getParentIdentity(ISession.class)
			.removeObservationListener(this.listener);
		}
	}

	/**
	 * Fire the list of all siblings of an object, optionally filtered by type
	 * 
	 * @author Ferd
	 *
	 */
	@Action(id = "siblings")
	public static class Siblings extends KlabAction {

		public Siblings(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		void run(KlabActor.Scope scope) {
			// TODO Auto-generated method stub

		}

	}

	@Action(id = "connect")
	public static class Connect extends KlabAction {

		public Connect(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		void run(KlabActor.Scope scope) {
			// TODO Auto-generated method stub

		}

	}

}
