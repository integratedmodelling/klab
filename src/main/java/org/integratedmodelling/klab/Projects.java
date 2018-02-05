package org.integratedmodelling.klab;

import java.util.HashMap;
import java.util.Map;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.services.IProjectService;
import org.integratedmodelling.klab.common.project.Project;

public enum Projects implements IProjectService {
    INSTANCE;
    
    Map<String, Project> projects = new HashMap<>();
  
    @Override
    public Project getProject(String projectId) {
        return projects.get(projectId);
    }

    public IProject retrieveOrCreate(IKimProject project) {
      if (projects.containsKey(project.getName())) {
        return projects.get(project.getName());
      }
      
      Project ret = new Project(project);
      projects.put(ret.getName(), ret);
      return ret;
    }
    
}
