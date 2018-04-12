package org.integratedmodelling.kim.api;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.List;

public interface IKimWorkspace {

    List<IKimNamespace> load(boolean incremental) throws IOException;
    
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
     * @return all project names
     */
    Collection<String> getProjectNames();

    URL getURL();

    File getRoot();
}
