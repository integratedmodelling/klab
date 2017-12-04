package org.integratedmodelling.klab;

import java.io.File;

import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.knowledge.IWorkspace;
import org.integratedmodelling.klab.api.knowledge.IWorldview;
import org.integratedmodelling.klab.api.services.IWorkspaceService;
import org.integratedmodelling.klab.engine.resources.CoreOntology;
import org.integratedmodelling.klab.engine.resources.MonitorableFileWorkspace;

public enum Workspaces implements IWorkspaceService {

    INSTANCE;

    /**
     * The core workspace, only containing the OWL knowledge distributed with the software, and no projects.
     */
    private CoreOntology coreKnowledge;

    /**
     * The worldview, synchronized at startup from Git repositories specified in or through the k.LAB
     * certificate.
     */
    private IWorldview   worldview;

    /**
     * The workspace containing components from the network (or local components if so configured), loaded on
     * demand.
     */
    private IWorkspace   components;

    /**
     * Workspace containing the k.LAB assets installed on the running instance. The files in this workspace
     * are monitored and reloaded incrementally at each change.
     */
    private IWorkspace   local;

    @Override
    public IWorkspace getLocal() {
        return local;
    }

    @Override
    public CoreOntology getUpperOntology() {
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
    public boolean loadCoreKnowledge() {
        try {
            coreKnowledge = new CoreOntology(Configuration.INSTANCE.getDataPath("knowledge"));
            coreKnowledge.load(false);
            return true;
        } catch (Throwable e) {
            Klab.INSTANCE.error(e.getLocalizedMessage());
        }
        return false;
    }

    /*
     * Create and load the components workspace. TODO needs the network to obtain components, then add/override any
     * local ones.
     */
    public boolean loadComponents(File[] localComponentPaths) {
        try {
            components = new MonitorableFileWorkspace(Configuration.INSTANCE
                    .getDataPath("components"), localComponentPaths);
            components.load(false);
            return true;
        } catch (Throwable e) {
            Klab.INSTANCE.error(e.getLocalizedMessage());
        }
        return false;
    }

    /*
     * Create and load the worldview specified by the Git repositories pointed to
     * by the certificate.
     */
    public boolean loadWorldview(ICertificate certificate) {
        try {
            worldview = certificate.getWorldview();
            worldview.load(false);
            return true;
        } catch (Throwable e) {
            Klab.INSTANCE.error(e.getLocalizedMessage());
        }
        return false;
    }

    /*
     * Initialize (index but do not load) the local workspace from the passed path.
     */
    public void initializeLocalWorkspace(File workspaceRoot) {
        if (local == null) {
            local = new MonitorableFileWorkspace(workspaceRoot);
        }
    }
    
    /*
     * Create and load the local workspace.
     */
    public boolean loadLocalWorkspace() {
        try {
            getLocal().load(false);
            return true;
        } catch (Throwable e) {
            Klab.INSTANCE.error(e.getLocalizedMessage());
        }
        return false;
    }

}
