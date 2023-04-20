package org.integratedmodelling.klab.engine.resources;

import java.io.File;
import java.util.Map;
import java.util.Set;

public class ComponentsWorkspace extends MonitorableGitWorkspace {

    public ComponentsWorkspace(String name, File root, String worldview, Map<String, Set<String>> gitUrls, File... overridingProjects) {
        super(root, "components", worldview, gitUrls);
        this.setSkipSync(System.getProperty("skipWorldviewSync") != null);
    }
}
