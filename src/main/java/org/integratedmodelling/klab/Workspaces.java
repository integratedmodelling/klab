package org.integratedmodelling.klab;

import java.io.IOException;

import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.knowledge.IWorkspace;
import org.integratedmodelling.klab.api.services.IWorkspaceService;
import org.integratedmodelling.klab.engine.resources.OWLCore;

public enum Workspaces implements IWorkspaceService {
    
    INSTANCE;
    
    /**
     * The core workspace, only containing the OWL knowledge distributed with the software, and
     * no projects.
     */
    public OWLCore CORE;
    
    /**
     * The worldview, synchronized at startup from Git repositories specified in or through the 
     * k.LAB certificate.
     */
    public IWorkspace WORLDVIEW;

    /**
     * The workspace containing components from the network (or local components if so configured),
     * loaded on demand.
     */
    public IWorkspace COMPONENTS;

    /**
     * Workspace containing the k.LAB assets installed on the running instance. The files in this
     * workspace are monitored and reloaded incrementally at each change.
     */
    public IWorkspace LOCAL;

    public void initializeCoreKnowledge() throws IOException {
        CORE = new OWLCore(Configuration.INSTANCE.getDataPath("knowledge"));
        CORE.load(false);
    }
    
    public void initializeWorldview(ICertificate certificate) {
        // TODO
    }
    
}
