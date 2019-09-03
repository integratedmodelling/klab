package org.integratedmodelling.klab.engine.debugger;

import org.integratedmodelling.klab.components.runtime.observations.State;
import org.integratedmodelling.klab.data.storage.RescalingState;

public enum Debug {

	INSTANCE;
	
	public void summarize(Object object) {
		if (object instanceof RescalingState) {
			((RescalingState)object).summarize();
		} else if (object instanceof State) {
			
		}
	}
	
}
