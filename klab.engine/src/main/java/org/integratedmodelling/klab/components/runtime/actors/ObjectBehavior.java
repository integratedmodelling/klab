package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.actors.Behavior;
import org.integratedmodelling.klab.api.extensions.actors.Message;

@Behavior(id="object", version=Version.CURRENT)
public class ObjectBehavior {

	@Message(id= {"die"})
	public static class MoveAway extends AbstractKlabMessage {
		
	}
}
