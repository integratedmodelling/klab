package org.integratedmodelling.klab;

import java.io.File;
import java.io.IOException;

import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.knowledge.IWorkspace;
import org.integratedmodelling.klab.api.knowledge.IWorldview;
import org.integratedmodelling.klab.api.services.IWorkspaceService;
import org.integratedmodelling.klab.engine.resources.MonitorableFileWorkspace;
import org.integratedmodelling.klab.engine.resources.OWLCore;

public enum Workspaces implements IWorkspaceService {

    INSTANCE;

    /**
     * The core workspace, only containing the OWL knowledge distributed with the software, and no projects.
     */
    private OWLCore    coreKnowledge;

    /**
     * The worldview, synchronized at startup from Git repositories specified in or through the k.LAB
     * certificate.
     */
    private IWorldview worldview;

    /**
     * The workspace containing components from the network (or local components if so configured), loaded on
     * demand.
     */
    private IWorkspace components;

    /**
     * Workspace containing the k.LAB assets installed on the running instance. The files in this workspace
     * are monitored and reloaded incrementally at each change.
     */
    private IWorkspace local;

    @Override
    public IWorkspace getLocal() {
        return local;
    }
    
    @Override
    public IWorkspace getCoreKnowledge() {
        return coreKnowledge;
    }
    
    @Override
    public IWorldview getWorldview() {
        return worldview;
    }
    
    @Override
    public IWorkspace getComponents() {
        return components;
    }
    /*
     * Extract and load the OWL core knowledge workspace.
     */
    public void loadCoreKnowledge() throws IOException {
        coreKnowledge = new OWLCore(Configuration.INSTANCE.getDataPath("knowledge"));
        coreKnowledge.load(false);
    }

    /*
     * Create and load the components workspace. TODO needs the network to obtain components, then add/override any
     * local ones.
     */
    public void loadComponents(File[] localComponentPaths) throws IOException {
        components = new MonitorableFileWorkspace(Configuration.INSTANCE
                .getDataPath("components"), localComponentPaths);
        components.load(false);
    }

    /*
     * Create and load the worldview specified by the Git repositories pointed to
     * by the certificate.
     */
    public void loadWorldview(ICertificate certificate) throws IOException {
        worldview = certificate.getWorldview();
        worldview.load(false);
    }

    /*
     * Initialize (index but do not load) the local workspace from the passed path.
     */
    public void initializeLocalWorkspace(File workspaceRoot) {
    }

}
