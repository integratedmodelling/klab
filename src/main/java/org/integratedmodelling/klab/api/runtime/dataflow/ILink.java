package org.integratedmodelling.klab.api.runtime.dataflow;

import java.util.Optional;

public interface ILink {
    
	IPort getSource();
	
	IPort getDestination();
	
	Optional<ICondition> getCondition();
}
