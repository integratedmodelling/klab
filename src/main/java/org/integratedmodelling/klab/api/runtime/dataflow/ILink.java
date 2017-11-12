package org.integratedmodelling.klab.api.runtime.dataflow;

public interface ILink {

	IPort getSource();
	
	IPort getDestination();
}
