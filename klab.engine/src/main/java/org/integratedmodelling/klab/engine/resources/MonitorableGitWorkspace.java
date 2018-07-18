package org.integratedmodelling.klab.engine.resources;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import org.integratedmodelling.kim.model.KimWorkspace;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.utils.GitUtils;
import org.integratedmodelling.klab.utils.MiscUtilities;

public class MonitorableGitWorkspace extends MonitorableFileWorkspace {

    Collection<String> gitUrls;
    boolean synced;
    boolean skipSync = false;
    
    public MonitorableGitWorkspace(File root, Collection<String> gitUrls, File... overridingProjects) {
        
        delegate = new KimWorkspace(root, overridingProjects) {

            @Override
            public void readProjects() throws IOException {
                
                if (!synced && (!skipSync || !root.exists())) {
                    synced = true;
                    for (String url : gitUrls) {
                        try {
                            GitUtils.requireUpdatedRepository(url, getRoot());
                        } catch (KlabException e) {
                            if (new File(root + File.separator + MiscUtilities.getURLBaseName(url) + File.separator + ".git").exists()) {
                                Logging.INSTANCE.error("cannot sync existing repository "  + url + ": skipping");
                            } else {
                                throw new IOException(e);
                            }
                        }
                    }
                }
                
                super.readProjects();
            }
        };
        this.gitUrls = gitUrls;
    }
    
    public void setSkipSync(boolean skipSync) {
      this.skipSync = skipSync;
    }
    
}
