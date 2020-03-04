package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;
import org.integratedmodelling.klab.engine.runtime.api.IActorIdentity;

import akka.actor.typed.ActorRef;

public class KlabMessages {

	public static class Load implements KlabMessage {

		IBehavior behavior;
		
		public Load(IBehavior behavior) {
			this.behavior = behavior;
		}
	}
	
	public static class Spawn implements KlabMessage {
		
		IActorIdentity<KlabMessage> identity;
		ActorRef<KlabMessage> ref;
		ActorRef<KlabMessage> replyTo; 
		
		public Spawn(IActorIdentity<KlabMessage> identity, ActorRef<KlabMessage> replyTo) {
			this.identity = identity;
			this.replyTo = replyTo;
		}
		
	}
}
