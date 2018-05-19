package org.integratedmodelling.klab.data.encoding;

import org.integratedmodelling.kim.api.INotification;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;

/**
 * A builder that encodes the data into an existing (or creates a new) local
 * artifact. The build() step merely moves the finished artifact to the data
 * object for retrieval, which is even unnecessary if the target artifact is
 * pre-built by the runtime.
 * 
 * @author ferdinando.villa
 *
 */
public class LocalDataBuilder implements IKlabData.Builder {

	IState state = null;
	IDirectObservation observation = null;
	IRuntimeContext context = null;
	long offset = 0;

	public LocalDataBuilder(IRuntimeContext context) {
		this.context = context;
		if (context.getTargetArtifact() instanceof IState) {
			this.state = (IState) context.getTargetArtifact();
		}
	}

	@Override
	public Builder startState(String name) {
		// TODO see how we want to deal with this - should push on stack and get or
		// create previous, in object if we have an object
		offset = 0;
		return this;
	}

	@Override
	public void add(double doubleValue) {
		if (state != null) {
			state.set(state.getGeometry().getLocator(offset++), doubleValue);
		}
	}

	@Override
	public void add(float floatValue) {
		if (state != null) {
			state.set(state.getGeometry().getLocator(offset++), floatValue);
		}
	}

	@Override
	public void add(int intValue) {
		if (state != null) {
			state.set(state.getGeometry().getLocator(offset++), intValue);
		}
	}

	@Override
	public void add(long longValue) {
		if (state != null) {
			state.set(state.getGeometry().getLocator(offset++), longValue);
		}
	}

	@Override
	public Builder finishState() {
		// TODO pop context
		return null;
	}

	@Override
	public Builder startObject(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Builder finishObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Builder setProperty(String property, Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Builder addNotification(INotification notification) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IKlabData build() {
		return new LocalData(state == null ? observation : state);
	}

}
