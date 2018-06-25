package org.integratedmodelling.klab.components.runtime.observations;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.components.runtime.RuntimeContext;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.scale.Scale;

public class State extends Observation implements IState {

	IDataArtifact storage;
	Map<IArtifact.Type, IDataArtifact> layers = new HashMap<>();

	public State(Observable observable, Scale scale, RuntimeContext context, IDataArtifact data) {
		super(observable, scale, context);
		this.storage = data;
		this.layers.put(data.getType(), data);
	}
	
	@Override
	public boolean isConstant() {
		return false;
	}

	@Override
	public IState as(IArtifact.Type type) {

		if (type == storage.getType() || type == IArtifact.Type.VALUE) {
			return this;
		}
		
		IDataArtifact layer = layers.get(type);
		if (layer == null) {
			layers.put(type, layer = Klab.INSTANCE.getStorageProvider().createStorage(type, getScale(), getRuntimeContext()));
		}

		return new StateLayer(this, layer);
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
	public IArtifact.Type getType() {
		return storage.getType();
	}

}
