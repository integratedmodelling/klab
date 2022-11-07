package org.integratedmodelling.klab.components.runtime.observations;

import org.integratedmodelling.klab.api.observations.INetwork;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.scale.Scale;

public class Process extends DirectObservation implements IProcess {

	public Process(String name, Observable observable, Scale scale, IRuntimeScope context) {
        super(name, observable, scale, context);
        // TODO Auto-generated constructor stub
    }
    
    protected Process(Process other) {
    	super(other);
    }
    
	@Override
	public IArtifact.Type getType() {
		return IArtifact.Type.PROCESS;
	}
	

	@Override
	public INetwork getOriginatingPattern() {
		return (INetwork)super.getOriginatingPattern();
	}
}
