package org.integratedmodelling.klab.engine.resources;

import java.io.File;
import java.util.Collection;

public class ComponentsWorkspace extends MonitorableGitWorkspace {

    public ComponentsWorkspace(String name, File root, Collection<String> gitUrls, File... overridingProjects) {
        super(root, gitUrls, overridingProjects);
        this.setName(name);
        this.setSkipSync(System.getProperty("skipWorldviewSync") != null);
    }
}
