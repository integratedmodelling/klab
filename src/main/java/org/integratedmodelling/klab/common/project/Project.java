package org.integratedmodelling.klab.common.project;

import java.io.File;
import java.util.List;

import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.model.INamespace;

public class Project implements IProject {
    
    IKimProject delegate;
   
    public Project(String name) {
        
    }
    
    public Project(IKimProject project) {
        this.delegate = project;
    }

    @Override
    public String getName() {
        return delegate.getName();
    }

    @Override
    public File getRoot() {
        return delegate.getRoot();
    }

    @Override
    public List<INamespace> getNamespaces() {
        return null;
    }

    @Override
    public List<IProject> getPrerequisites() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Version getVersion() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public INamespace getUserKnowledge() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isCanonical() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isRemote() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getOriginatingNodeId() {
        // TODO Auto-generated method stub
        return null;
    }

}
