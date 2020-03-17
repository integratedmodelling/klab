package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.actors.Action;
import org.integratedmodelling.klab.api.extensions.actors.Behavior;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;
import org.integratedmodelling.klab.engine.runtime.api.IActorIdentity;

@Behavior(id="object", version=Version.CURRENT)
public class ObjectBehavior {

	@Action(id="stop")
	public static class MoveAway extends KlabAction {

		public MoveAway(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope) {
			super(identity, arguments, scope);
		}

		@Override
		void run() {
			// TODO Auto-generated method stub
			
		}
		
	}
}
