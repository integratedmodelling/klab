package org.integratedmodelling.klab;

import org.integratedmodelling.kim.api.IWorkspace;
import org.integratedmodelling.klab.api.services.IWorkspaceService;

public enum Workspaces implements IWorkspaceService {
    INSTANCE;
    
    public IWorkspace WORLDVIEW;
    public IWorkspace COMPONENTS;
    public IWorkspace LOCAL;
    
    
}
