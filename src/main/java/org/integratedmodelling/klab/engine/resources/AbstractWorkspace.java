package org.integratedmodelling.klab.engine.resources;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.integratedmodelling.kim.model.KimWorkspace;
import org.integratedmodelling.klab.api.knowledge.IWorkspace;

public abstract class AbstractWorkspace implements IWorkspace {

    KimWorkspace delegate;

    public AbstractWorkspace(File root, File[] overridingProjects) {
        delegate = new KimWorkspace(root, overridingProjects);
    }

    @Override
    public Collection<File> getProjectLocations() {
        return delegate.getProjectLocations();
    }

    @Override
    public Collection<String> getProjectNames() {
        return delegate.getProjectNames();
    }
    
    protected void readProjects() throws IOException {
        delegate.readProjects();
    }

    
}
