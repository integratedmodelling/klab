package org.integratedmodelling.kdl.model;

import org.integratedmodelling.kdl.api.IKdlContextualizer;
import org.integratedmodelling.kdl.kdl.Function;
import org.integratedmodelling.kim.api.IServiceCall;

public class KdlContextualizer extends KdlStatement implements IKdlContextualizer {

    private static final long serialVersionUID = 692420005135910303L;
    
    KdlContextualizer(Function o) {
        super(o);
    }

	@Override
	public IServiceCall getServiceCall() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMediationTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getVariable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTarget() {
		// TODO Auto-generated method stub
		return null;
	}
}
