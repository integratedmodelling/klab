package org.integratedmodelling.klab.api.runtime.dataflow;

import java.util.List;

public interface IActuator {

	List<IPort> getInputs();
	
	List<IPort> getOutputs();
	
}
