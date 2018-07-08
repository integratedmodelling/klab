package org.integratedmodelling.klab.components.localstorage;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IStorageProvider;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.components.localstorage.impl.BooleanStorage;
import org.integratedmodelling.klab.components.localstorage.impl.ConceptStorage;
import org.integratedmodelling.klab.components.localstorage.impl.DoubleStorage;

@Component(id = "local.storage", version = Version.CURRENT)
public class LocalStorageComponent implements IStorageProvider {

    public LocalStorageComponent() {
        // TODO Auto-generated constructor stub
        // TODO install reaper for any leftover storage
    }

    @Override
    public IDataArtifact createStorage(IArtifact.Type type, IScale scale, IComputationContext context) {
        switch (type) {
        case CONCEPT:
            return new ConceptStorage(scale);
        case NUMBER:
            return new DoubleStorage(scale);
        case BOOLEAN:
            return new BooleanStorage(scale);
        default:
            throw new IllegalArgumentException("illegal type for state storage: " + type);
        }
    }

}
