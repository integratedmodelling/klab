package org.integratedmodelling.klab.api.runtime.dataflow;

import java.util.List;

public interface IActuator {
    
    String getName();

	List<IPort> getInputs();
	
	List<IPort> getOutputs();
	
}
