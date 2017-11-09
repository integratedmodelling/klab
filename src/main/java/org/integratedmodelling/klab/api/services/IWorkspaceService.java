package org.integratedmodelling.klab.api.services;

import org.integratedmodelling.klab.api.knowledge.IWorkspace;
import org.integratedmodelling.klab.api.knowledge.IWorldview;

public interface IWorkspaceService {

    IWorkspace getLocal();

    IWorkspace getCoreKnowledge();

    IWorldview getWorldview();

    IWorkspace getComponents();

}
