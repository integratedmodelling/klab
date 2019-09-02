package org.integratedmodelling.klab.components.localstorage;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IStorage;
import org.integratedmodelling.klab.api.data.IStorageProvider;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.localstorage.debug.DebugStorage;
import org.integratedmodelling.klab.components.localstorage.debug.KeyedDebugStorage;
import org.integratedmodelling.klab.components.localstorage.impl.KeyedStorage;
import org.integratedmodelling.klab.data.storage.FileMappedStorage;

@Component(id = "org.integratedmodelling.storage.local", version = Version.CURRENT)
public class LocalStorageComponent implements IStorageProvider {
	
	private boolean debugging = false;

	public LocalStorageComponent() {
		// TODO Auto-generated constructor stub
		// TODO install reaper for any temporary/leftover storage
		// TODO link debugging flag to configuration
	}

	@Override
	public IStorage<?> createStorage(IArtifact.Type type, IScale scale, IContextualizationScope context) {
		
		if (debugging) {
			switch (type) {
			case CONCEPT:
				return new KeyedDebugStorage<IConcept>(scale, IConcept.class);
			case NUMBER:
				return new DebugStorage<Double>(scale, Double.class);
			case BOOLEAN:
				return new DebugStorage<Boolean>(scale, Boolean.class);
			case TEXT:
				return new DebugStorage<String>(scale, String.class);
			default:
				throw new IllegalArgumentException("illegal type for state storage: " + type);
			}
		}
		
		switch (type) {
		case CONCEPT:
			return new KeyedStorage<IConcept>(scale, IConcept.class);
		case NUMBER:
			return new FileMappedStorage<Double>(scale, Double.class);
		case BOOLEAN:
			return new FileMappedStorage<Boolean>(scale, Boolean.class);
		case TEXT:
			return new KeyedStorage<String>(scale, String.class);
		default:
			throw new IllegalArgumentException("illegal type for state storage: " + type);
		}
	}

}
