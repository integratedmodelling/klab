package org.integratedmodelling.klab.components.runtime.artifacts;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.engine.runtime.api.IDataStorage;
import org.integratedmodelling.klab.provenance.Artifact;
import org.integratedmodelling.klab.utils.Utils;

public class StorageDataArtifact extends Artifact implements IDataArtifact {

	IDataStorage<?> storage;
	
	public StorageDataArtifact(IDataStorage<?> storage) {
		this.storage = storage;
	}
	
	@Override
	public IGeometry getGeometry() {
		return storage.getGeometry();
	}

	@Override
	public Type getType() {
		return storage.getType();
	}

	@Override
	public long getLastUpdate() {
		return 0;
	}

	@Override
	public String getId() {
		return "storage";
	}

	@Override
	public Object get(ILocator index) {
		return storage.getObject(index);
	}

	@Override
	public <T> T get(ILocator index, Class<T> cls) {
		return Utils.asType(storage.getObject(index), cls);
	}

	@Override
	public long set(ILocator index, Object value) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long size() {
		return storage.getGeometry().size();
	}

	@Override
	public IDataKey getDataKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T aggregate(ILocator geometry, Class<? extends T> cls) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IArtifact getGroupMember(int n) {
		// TODO Auto-generated method stub
		return null;
	}

}
