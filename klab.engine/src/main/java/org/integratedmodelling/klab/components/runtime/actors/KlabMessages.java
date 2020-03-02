package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;

import akka.actor.typed.ActorRef;

public class KlabMessages {

	public static class Load implements KlabMessage {

		IBehavior behavior;
		
		public Load(IBehavior behavior) {
			this.behavior = behavior;
		}
	}
	
	public static class Spawn implements KlabMessage {
		IIdentity identity;
		ActorRef<KlabMessage> ref;
		
		public Spawn(IIdentity identity) {
			this.identity = identity;
		}

		public ActorRef<KlabMessage> getActor() {
			return ref;
		}
	}
}
