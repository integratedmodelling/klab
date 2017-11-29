package org.integratedmodelling.klab.api.knowledge;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.exceptions.KlabException;

public interface IWorkspace {

    String getName();

    File getRoot();
    
    List<INamespace> load(boolean incremental) throws KlabException;
    
    Collection<IProject> getProjects();
    
}
