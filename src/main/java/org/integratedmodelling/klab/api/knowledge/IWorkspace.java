package org.integratedmodelling.klab.api.knowledge;

import java.io.File;
import java.util.Collection;

import org.integratedmodelling.kim.api.IKimWorkspace;

public interface IWorkspace extends IKimWorkspace {
    
    /**
     * Directories on the local filesystem where each project managed under this workspace. Each project
     * has its own individual location - there is no requirement for a "root" workspace directory.
     * 
     * @return all registered project locations
     */
    Collection<File> getProjectLocations();
}
