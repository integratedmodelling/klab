package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.actors.Behavior;
import org.integratedmodelling.klab.api.extensions.actors.Message;
import org.integratedmodelling.klab.utils.Parameters;

@Behavior(id="session", version=Version.CURRENT)
public class RuntimeBehavior {

	@Message(id="observe")
	public static class Observe extends AbstractKlabMessage {

		@Override
		public void initialize(Parameters<String> arguments) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
