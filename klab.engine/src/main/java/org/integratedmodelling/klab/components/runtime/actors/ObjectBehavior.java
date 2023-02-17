package org.integratedmodelling.klab.components.runtime.actors;

import java.util.concurrent.Future;

import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kactors.api.IKActorsValue.Type;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Actors;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.auth.IActorIdentity;
import org.integratedmodelling.klab.api.auth.IActorIdentity.KlabMessage;
import org.integratedmodelling.klab.api.extensions.actors.Action;
import org.integratedmodelling.klab.api.extensions.actors.Behavior;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.components.runtime.observations.Subject;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.utils.Pair;

import akka.actor.typed.ActorRef;

@Behavior(id = "object", version = Version.CURRENT)
public class ObjectBehavior {

	@Action(id = "observe", fires = Type.OBSERVATION)
	public static class Observe extends KlabActionExecutor {

		public Observe(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, IKActorsBehavior.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		public void run(IKActorsBehavior.Scope scope) {

			if (this.identity instanceof ISubject) {
				Object arg = evaluateArgument(0, scope);
				if (arg instanceof IObservable) {
					try {
						Future<IObservation> future = ((Subject) identity)
								.observe(((IObservable) arg).getDefinition());
						fire(future.get(), scope);
					} catch (Throwable e) {
						fail(scope, e);
					}
				}
			} else if (this.identity instanceof Session) {

				try {
					Object arg = evaluateArgument(0, scope);
					if (arg instanceof IObservable) {
						Future<IArtifact> future = ((Session) this.identity).getState()
								.submit(((IObservable) arg).getDefinition());
						fire(future.get(), scope);
					}
				} catch (Throwable e) {
					fail(scope, e);
				}

			} else {
				fail(scope, this.identity + ": observations can only be made within subjects or sessions");
			}

		}
	}

	@Action(id = "stop", fires = {})
	public static class MoveAway extends KlabActionExecutor {

		public MoveAway(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, IKActorsBehavior.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		public void run(IKActorsBehavior.Scope scope) {
			// TODO Auto-generated method stub

		}

	}

	@Action(id = "bind", fires = {})
	public static class Bind extends KlabActionExecutor {

		public Bind(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, IKActorsBehavior.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		public void run(IKActorsBehavior.Scope scope) {
			IObservable what = Actors.INSTANCE.getArgument(arguments, scope, identity, IObservable.class);
			String behavior = Actors.INSTANCE.getArgument(arguments, scope, identity, String.class);
			IKimExpression filter = Actors.INSTANCE.getArgument(arguments, scope, identity, IKimExpression.class);
			if (what == null || behavior == null || Actors.INSTANCE.getBehavior(behavior) == null) {
				// TODO improve message
				error("error in bind action: behavior or observable not specified or recognized");
			} else {
				((IRuntimeScope)scope.getRuntimeScope()).getBehaviorBindings().put(what.getType(), new Pair<>(behavior, filter));
			}
		}
	}

	/**
	 * Fire the list of all siblings of an object, optionally filtered by type
	 * 
	 * @author Ferd
	 *
	 */
	@Action(id = "siblings", fires = Type.LIST)
	public static class Siblings extends KlabActionExecutor {

		public Siblings(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, IKActorsBehavior.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		public void run(IKActorsBehavior.Scope scope) {
			// TODO Auto-generated method stub

		}

	}

	@Action(id = "connect", fires = Type.OBSERVATION)
	public static class Connect extends KlabActionExecutor {

		public Connect(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, IKActorsBehavior.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		public void run(IKActorsBehavior.Scope scope) {
			// TODO Auto-generated method stub

		}

	}

}
