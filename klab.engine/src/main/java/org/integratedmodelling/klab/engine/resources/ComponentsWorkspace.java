package org.integratedmodelling.klab.engine.resources;

import java.io.File;
import java.util.Map;
import java.util.Set;

public class ComponentsWorkspace extends MonitorableGitWorkspace {

    public ComponentsWorkspace(String name, File root, Map<String, Set<String>> gitUrls, File... overridingProjects) {
        super(root, "components", gitUrls);
        this.setSkipSync(System.getProperty("skipWorldviewSync") != null);
    }
}
