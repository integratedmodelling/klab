package org.integratedmodelling.klab.rest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProjectLoadRequest {

    private List<File> projectLocations = new ArrayList<>();

    public ProjectLoadRequest() {}
    
    public ProjectLoadRequest(List<File> projectFiles) {
        this.projectLocations.addAll(projectFiles);
    }

    public List<File> getProjectLocations() {
        return projectLocations;
    }

    public void setProjectLocations(List<File> projectLocations) {
        this.projectLocations = projectLocations;
    }

    @Override
    public String toString() {
        return "ProjectLoadRequest [projectLocations=" + projectLocations + "]";
    }

}
