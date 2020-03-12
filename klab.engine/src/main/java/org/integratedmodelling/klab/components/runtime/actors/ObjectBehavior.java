package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.actors.Action;
import org.integratedmodelling.klab.api.extensions.actors.Behavior;

@Behavior(id="object", version=Version.CURRENT)
public class ObjectBehavior {

	@Action(id="stop")
	public static class MoveAway extends AbstractKlabMessage {
		
	}
}
