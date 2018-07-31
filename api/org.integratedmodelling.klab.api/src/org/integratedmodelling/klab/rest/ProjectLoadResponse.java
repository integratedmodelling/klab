package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProjectLoadResponse {

    private List<ProjectReference> projects = new ArrayList<>();

    public ProjectLoadResponse() {
    }

    public ProjectLoadResponse(Collection<ProjectReference> projects) {
        this.projects.addAll(projects);
    }

    public List<ProjectReference> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectReference> projects) {
        this.projects = projects;
    }

}
