package org.integratedmodelling.klab.components.runtime.actors;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kactors.model.KActorsValue;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.actors.Action;
import org.integratedmodelling.klab.api.extensions.actors.Behavior;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;
import org.integratedmodelling.klab.engine.runtime.api.IActorIdentity;

import akka.actor.typed.ActorRef;

@Behavior(id = "test", version = Version.CURRENT)
public class TestBehavior {

	@Action(id = "test", fires = {}, description = "Run all the test included in one or more projects, naming the project ID, "
			+ "a URL or a Git URL (git:// or http....*.git")
	public static class Test extends KlabActionExecutor {

		public Test(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		void run(KlabActor.Scope scope) {
			List<Object> args = new ArrayList<>();
			for (Object arg : arguments.values()) {
			}
		}
	}

}
