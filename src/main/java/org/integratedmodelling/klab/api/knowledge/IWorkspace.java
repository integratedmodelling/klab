package org.integratedmodelling.klab.api.knowledge;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * 
 * @author ferdinando.villa
 *
 */
public interface IWorkspace {

    /**
     * All workspaces in k.LAB have a unique name.
     *
     * @return the unique workspace name
     */
    String getName();

    /**
     * All workspaces in k.LAB are filesystem based for now.
     * 
     * @return the file location of the root of the workspace on the filesystem.
     */
    File getRoot();
    
    /**
     * Load all the knowledge in the namespace, optionally limited to anything that has
     * changed since last read.
     * 
     * @param incremental if true, only read resources that have changed.
     * @return the list of namespaces read, excluding those that did not need update.
     *         Workspaces that keep track of dependencies should return the namespaces
     *         in dependency order.
     * 
     * @throws KlabException in case of I/O or other errors. Syntax errors won't cause
     *         exceptions.
     */
    List<INamespace> load(boolean incremental) throws KlabException;
    
    /**
     * 
     * @return
     */
    Collection<IProject> getProjects();
    
}
