package org.integratedmodelling.klab.engine.resources;

import java.io.File;
import java.io.IOException;

import org.integratedmodelling.kim.model.KimWorkspace;

public class MonitorableGitWorkspace extends KimWorkspace {

    public MonitorableGitWorkspace(File root, File... overridingProjects) {
        super(root, overridingProjects);
    }
    
    protected void readProjects() throws IOException {
        super.readProjects();
    }
}
