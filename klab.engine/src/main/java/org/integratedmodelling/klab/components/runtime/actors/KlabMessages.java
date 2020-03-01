package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;

public class KlabMessages {

	public static class Load implements KlabMessage {

		IBehavior behavior;
		
		public Load(IBehavior behavior) {
			this.behavior = behavior;
		}
	}
	
}
