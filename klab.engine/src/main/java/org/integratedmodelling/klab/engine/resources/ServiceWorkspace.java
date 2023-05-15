package org.integratedmodelling.klab.engine.resources;

import java.io.File;
import java.io.IOException;

import org.integratedmodelling.kim.model.KimWorkspace;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.utils.FileUtils;

public class ServiceWorkspace extends MonitorableFileWorkspace {

    public static final String INTERNAL_PROJECT_ID = "temp";
    private IProject localProject;
    
    public ServiceWorkspace() {
        // TODO Auto-generated constructor stub
    }

    public ServiceWorkspace(File root) {
        File temproot = new File(root + File.separator + INTERNAL_PROJECT_ID);
        if (temproot.exists()) {
            try {
                FileUtils.deleteDirectory(temproot);
            } catch (IOException e) {
                Logging.INSTANCE.error(e);
            }
        }
        delegate = new KimWorkspace(root, null, "service");
    }
    
    public IProject getServiceProject(IMonitor monitor) {
        this.localProject = getProject(INTERNAL_PROJECT_ID);
        if (this.localProject == null) {
            this.localProject = createProject(INTERNAL_PROJECT_ID, monitor);
        }
        return this.localProject;
    }
    
    public IProject getUserProject(IMonitor monitor) {
        IUserIdentity user = monitor.getIdentity().getParentIdentity(IUserIdentity.class);
        if (user == null) {
            throw new IllegalStateException("internal: cannot establish user identity to retrieve user-specific service project");
        }
        IProject ret = getProject(user.getUsername());
        if (ret == null) {
            ret = createProject(user.getUsername(), monitor);
        }
        return ret;
    }

}
