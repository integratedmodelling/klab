package org.integratedmodelling.klab;

import org.integratedmodelling.klab.api.services.IModelService;

public enum Models implements IModelService {

    INSTANCE;

    @Override
    public void releaseNamespace(String name) {
        // TODO remove all artifacts from local kbox
    }


}
