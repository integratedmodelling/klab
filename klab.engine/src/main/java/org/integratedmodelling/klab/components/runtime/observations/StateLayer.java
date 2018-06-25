package org.integratedmodelling.klab.components.runtime.observations;

import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.components.runtime.RuntimeContext;

public class StateLayer extends State implements IState {

	State delegate;
	
	public StateLayer(State state, IDataArtifact layer) {
		super(state.getObservable(), state.getScale(), (RuntimeContext)state.getRuntimeContext(), layer);
		this.delegate = state;
		// share the layers map with the original layer
		this.layers = state.layers;
	}
	
	public IState as(org.integratedmodelling.klab.api.provenance.IArtifact.Type type) {
		if (delegate.getType() == type) {
			return delegate;
		}
		return delegate.as(type);
	}

}
