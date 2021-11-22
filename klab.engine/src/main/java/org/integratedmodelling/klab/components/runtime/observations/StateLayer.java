package org.integratedmodelling.klab.components.runtime.observations;

import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.components.runtime.RuntimeScope;
import org.integratedmodelling.klab.engine.runtime.api.IDataStorage;

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
	IArtifact.Type layerType;

	/**
	 * Same ID as the original, just in case this is used as a proxy to retrieve
	 * the original content.
	 */
	@Override
	public String getId() {
		return delegate.getId();
	}
	
	public StateLayer(State state, IDataStorage<?> layer) {
		super(state.getObservable(), state.getScale(), (RuntimeScope) state.getScope(), layer);
		this.delegate = state;
		this.timeCoverage = state.timeCoverage;
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
        return (DirectObservation) getScope().getParentOf(delegate);
    }
	
	@Override
	public void finalizeTransition(IScale scale) {
		setContextualized(true);
		if (scale.getTime() != null && scale.getTime().getTimeType() != ITime.Type.INITIALIZATION) {
			setDynamic(true);
		}
	}

    public void setLayerType(IArtifact.Type type) {
        this.layerType = type;
    }

	@Override
	public void setValuePresentation(ValuePresentation vp) {
		delegate.setValuePresentation(vp);
		super.setValuePresentation(vp);
	}

    
    
}
