package org.integratedmodelling.klab.engine.resources;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.model.KimWorkspace;
import org.integratedmodelling.klab.Namespaces;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.knowledge.IWorkspace;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;

public abstract class AbstractWorkspace implements IWorkspace {

    KimWorkspace delegate;
    List<IProject> projects = new ArrayList<>();
    
    
    AbstractWorkspace() {
    }
    
    public AbstractWorkspace(File root, File... overridingProjects) {
        delegate = new KimWorkspace(root, overridingProjects);
    }

    @Override
    public String getName() {
        return delegate.getName();
    }
    
    public Collection<File> getProjectLocations() {
        return delegate.getProjectLocations();
    }

    public Collection<String> getProjectNames() {
        return delegate.getProjectNames();
    }

    protected void readProjects() throws IOException {
        delegate.readProjects();
    }

    @Override
    public List<INamespace> load(boolean incremental) throws KlabException {

        List<INamespace> ret = new ArrayList<>();
        try {
            for (IKimNamespace ns : delegate.load(incremental)) {
                // the validator callback inserts the namespace into the index, all we do is retrieve it
                INamespace namespace = Namespaces.INSTANCE.getNamespace(ns.getName());
                if (namespace != null) {
                    ret.add(namespace);
                }
            }
        } catch (IOException e) {
            throw new KlabIOException(e);
        }
        return ret;
    }

    @Override
    public File getRoot() {
        return delegate.getRoot();
    }
    
    public void setName(String s) {
        delegate.setName(s);
    }
    
    public Collection<IProject> getProjects() {
        return projects;
    }
}
