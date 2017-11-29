package org.integratedmodelling.klab.api.knowledge;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.exceptions.KlabException;

public interface IWorkspace {

    String getName();

    File getRoot();
    
    List<INamespace> load(boolean incremental) throws KlabException;
    
    /**
     * Directories on the local filesystem where each project managed under this workspace. Each project
     * has its own individual location - there is no requirement for a "root" workspace directory.
     * 
     * @return all registered project locations
     */
    Collection<File> getProjectLocations();
    
    /**
     * Names of all projects managed under this workspace.
     * 
     * @return
     */
    Collection<String> getProjectNames();

}
