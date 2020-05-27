package org.integratedmodelling.klab.engine.resources;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.kim.model.KimWorkspace;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.utils.GitUtils;
import org.integratedmodelling.klab.utils.MiscUtilities;

public class MonitorableGitWorkspace extends MonitorableFileWorkspace {

    Collection<String> gitUrls;
    boolean synced;
    boolean skipSync = false;
    // to handle deletion
    Map<String, String> gitUrlByName = new HashMap<>();
    
    public MonitorableGitWorkspace(File root, String name, Collection<String> gitUrls, File... overridingProjects) {
        
        delegate = new KimWorkspace(root, name) {

            @Override
            public void readProjects() {
                
                if (!synced && (!skipSync || !root.exists())) {
                    synced = true;
                    for (String url : gitUrls) {
                    	
//                    	// TODO FIXME REMOVE
//                    	if (url.contains("im.data.usa")) {
//                    		continue;
//                    	}
//                    	
                    	
                        try {
                            GitUtils.requireUpdatedRepository(url, getRoot());
                            addProjectPath(new File(root + File.separator + MiscUtilities.getURLBaseName(url)));
                            gitUrlByName.put(MiscUtilities.getURLBaseName(url), url);
                        } catch (KlabException e) {
                            if (new File(root + File.separator + MiscUtilities.getURLBaseName(url) + File.separator + ".git").exists()) {
                                Logging.INSTANCE.error("cannot sync existing repository "  + url + ": error is " + e.getMessage());
                            } else {
                                throw new KlabIOException(e);
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
    
	public void deleteProject(IProject project) {
		gitUrlByName.remove(project.getName());
		super.deleteProject(project);
	}
    
    
}
