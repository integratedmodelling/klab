package org.integratedmodelling.klab.engine.resources;

import java.io.File;

import org.integratedmodelling.kim.model.KimWorkspace;
import org.integratedmodelling.klab.api.knowledge.IWorkspace;
import org.integratedmodelling.klab.utils.DirectoryWatcher;

public class MonitorableFileWorkspace extends KimWorkspace implements IWorkspace {

    DirectoryWatcher watcher = null;
    
    public MonitorableFileWorkspace(File root, File[] overridingProjects) {
        super(root, overridingProjects);
    }
    
}
