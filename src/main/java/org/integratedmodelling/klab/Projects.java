package org.integratedmodelling.klab;

import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.klab.api.services.IProjectService;

public enum Projects implements IProjectService {
    INSTANCE;
    
    public IKimProject get(String projectId) {
        return null;
    }
    
}
