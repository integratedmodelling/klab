package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.actors.Behavior;
import org.integratedmodelling.klab.api.extensions.actors.Message;
import org.integratedmodelling.klab.utils.Parameters;

@Behavior(id="object", version=Version.CURRENT)
public class ObjectBehavior {

	@Message(id="stop")
	public static class MoveAway extends AbstractKlabMessage {

		@Override
		public void initialize(Parameters<String> arguments) {
		}

		
	}
}
