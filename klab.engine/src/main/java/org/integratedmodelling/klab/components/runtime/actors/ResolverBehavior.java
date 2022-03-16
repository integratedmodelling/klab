package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.auth.IActorIdentity;
import org.integratedmodelling.klab.api.auth.IActorIdentity.KlabMessage;
import org.integratedmodelling.klab.api.extensions.actors.Action;
import org.integratedmodelling.klab.api.extensions.actors.Behavior;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.Scope;

import akka.actor.typed.ActorRef;

@Behavior(id = "resolver", version = Version.CURRENT)
public class ResolverBehavior {

	@Action(id = "whitelist", fires = {})
	public static class Constrain extends KlabActionExecutor {

		public Constrain(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		void run(Scope scope) {
			// constraint should be scoped to the actor, which means the root test scope
		}

	}
	
	@Action(id = "blacklist", fires = {})
	public static class Exclude extends KlabActionExecutor {

		public Exclude(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		void run(Scope scope) {
			// constraint should be scoped to the actor, which means the root test scope
		}

	}
}
