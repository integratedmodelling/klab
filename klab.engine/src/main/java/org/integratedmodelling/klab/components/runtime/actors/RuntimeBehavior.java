package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.actors.Behavior;
import org.integratedmodelling.klab.api.extensions.actors.Message;

@Behavior(id="session", version=Version.CURRENT)
public class RuntimeBehavior {

	@Message(id="observe")
	public static class Observe extends AbstractKlabMessage {
		
	}
	
}
