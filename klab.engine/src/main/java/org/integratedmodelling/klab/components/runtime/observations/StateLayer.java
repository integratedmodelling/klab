package org.integratedmodelling.klab.components.runtime.observations;

import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.components.runtime.RuntimeScope;

/**
 * A proxy implementation of a state to support multiple layers, implementing
 * the {@link #as(org.integratedmodelling.klab.api.provenance.IArtifact.Type)}
 * method. Uses the same storage set as another but is "focused" on a different
 * storage layer than the original, and has its own metadata.
 * 
 * @author Ferd
 *
 */
public class StateLayer extends State implements IState {

	State delegate;

	/**
	 * Same ID as the original, just in case this is used as a proxy to retrieve
	 * the original content.
	 */
	@Override
	public String getId() {
		return delegate.getId();
	}
	
	public StateLayer(State state, IDataArtifact layer) {
		super(state.getObservable(), state.getScale(), (RuntimeScope) state.getRuntimeContext(), layer);
		this.delegate = state;
		// share the same layers map of the original layer
		this.layers = state.layers;
	}

	public IState as(org.integratedmodelling.klab.api.provenance.IArtifact.Type type) {
		if (delegate.getType() == type) {
			return delegate;
		}
		return delegate.as(type);
	}
	
	@Override
    public DirectObservation getContext() {
        return (DirectObservation) getRuntimeContext().getParentOf(delegate);
    }

}
