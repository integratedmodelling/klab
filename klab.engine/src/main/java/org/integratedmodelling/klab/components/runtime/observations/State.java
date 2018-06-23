package org.integratedmodelling.klab.components.runtime.observations;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.components.runtime.RuntimeContext;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.scale.Scale;

public class State extends Observation implements IState {

	IDataArtifact storage;

	public State(Observable observable, Scale scale, RuntimeContext context, IDataArtifact data) {
		super(observable, scale, context);
		this.storage = data;
	}

	@Override
	public boolean isConstant() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDynamic() {
		return false;
	}

	@Override
	public State as(IObservable observable) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setValue(Object value, long offset) {
		// TODO create storage lazily if not there; if observable is numeric and value
		// is a
		// distribution, set up for
		// that or promote to probabilistic.
	}

	public Object get(ILocator index) {
		return storage.get(index);
	}

	public long set(ILocator index, Object value) {
		return storage.set(index, value);
	}

	public IGeometry getGeometry() {
		return storage.getGeometry();
	}

	public IMetadata getMetadata() {
		return storage.getMetadata();
	}

	public long size() {
		return storage.size();
	}

	@Override
	public <T> T get(ILocator index, Class<T> cls) {
		return storage.get(index, cls);
	}

	@Override
	public org.integratedmodelling.kim.api.IPrototype.Type getType() {
		return storage.getType();
	}
}
