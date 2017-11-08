package org.integratedmodelling.klab.engine.resources;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.utils.GitUtils;

public class MonitorableGitWorkspace extends MonitorableFileWorkspace {

    Collection<String> gitUrls;
    boolean synced;
    
    public MonitorableGitWorkspace(File root, Collection<String> gitUrls, File... overridingProjects) {
        super(root, overridingProjects);
        this.gitUrls = gitUrls;
    }
    
    protected void readProjects() throws IOException {
        
        if (!synced) {
            synced = true;
            for (String url : gitUrls) {
                try {
                    GitUtils.clone(url, getRoot(), false);
                } catch (KlabException e) {
                    throw new IOException(e);
                }
            }
        }
        
        super.readProjects();
    }
}
