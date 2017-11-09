package org.integratedmodelling.klab;

import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.services.IProjectService;

public enum Projects implements IProjectService {
    INSTANCE;
    
    public IProject getProject(String projectId) {
        return null;
    }
    
}
