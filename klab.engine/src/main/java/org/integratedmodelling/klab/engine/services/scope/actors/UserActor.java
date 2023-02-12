package org.integratedmodelling.klab.engine.services.scope.actors;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.auth.IActorIdentity.KlabMessage;
import org.integratedmodelling.klab.auth.EngineUser;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor;
import org.integratedmodelling.klab.components.runtime.actors.UserBehavior;
import org.integratedmodelling.klab.components.runtime.actors.UserBehavior.UnknownMessage;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.ReceiveBuilder;

public class UserActor extends KlabActor {

	public static Behavior<KlabMessage> create(EngineUser session) {
		return Behaviors.setup(ctx -> new UserActor(ctx, session, null));
	}

	public UserActor(ActorContext<KlabMessage> context, EngineUser identity, String appId) {
		super(context, identity, appId);
	}

	@Override
	protected ReceiveBuilder<KlabMessage> configure() {
		// TODO add all view messages and runtime messages
		return super.configure().onMessage(UnknownMessage.class, this::onUnknownMessage);
	}

	private Behavior<KlabMessage> onUnknownMessage(UnknownMessage message) {
	    if (Configuration.INSTANCE.isEchoEnabled()) {
	        System.out.println("UNKNOWN MESSAGE " + message);
	    }
		return Behaviors.same();
	}
	
	@Override
	protected UserActor onPostStop() {
		// TODO do something
		return this;
	}

}
