package org.integratedmodelling.klab;

import org.integratedmodelling.kim.api.IProject;
import org.integratedmodelling.klab.api.services.IProjectService;

public enum Projects implements IProjectService {
    INSTANCE;
    
    public IProject get(String projectId) {
        return null;
    }
    
}
