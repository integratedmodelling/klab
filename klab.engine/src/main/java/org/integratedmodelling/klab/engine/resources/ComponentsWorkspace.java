package org.integratedmodelling.klab.engine.resources;

import java.io.File;
import java.util.Collection;

public class ComponentsWorkspace extends MonitorableGitWorkspace {

    public ComponentsWorkspace(String name, File root, Collection<String> gitUrls, File... overridingProjects) {
        super(root, "components", gitUrls);
        this.setSkipSync(System.getProperty("skipWorldviewSync") != null);
    }
}
