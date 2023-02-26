package org.integratedmodelling.klab.api.knowledge.organization;

import java.io.Serializable;
import java.net.URL;
import java.util.Collection;

import org.integratedmodelling.klab.api.lang.kim.KKimNamespace;

public interface KWorkspace extends Serializable {
    
    /**
     * Name of the workspace. May or may not be linked to the name of the root directory.
     * 
     * @return workspace name.
     */
    String getName();

    /**
     * Names of all projects managed under this workspace.
     * 
     * @return all project names
     */
    Collection<KProject> getProjects();

    /**
     * The URL for the workspace. With content type JSON and proper authorization it should return
     * the parsed projects.
     * 
     * @return the workspace URL.
     */
    URL getURL();

    /**
     * Find the named namespace in all the projects this workspace manages.
     * 
     * @param id
     * @return the namespace or null
     */
    KKimNamespace findNamespace(String id);
}
