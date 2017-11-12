package org.integratedmodelling.klab.api.runtime.dataflow;

public interface IPort {

	/**
	 * Port name, corresponding to the ID that this input or output is known as in
	 * the corresponding model.
	 * 
	 * @return
	 */
	String getName();
	
	int getActuatorCount();
	
	/**
	 * The actuator this is part of.
	 * 
	 * @return the actuator this is part of. Never null.
	 */
	IActuator getActuator();
}
