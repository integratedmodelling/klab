package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

public class ProjectActionResponse {

    private List<String> namespaces = new ArrayList<>();

    public List<String> getNamespaces() {
        return namespaces;
    }

    public void setNamespaces(List<String> namespaces) {
        this.namespaces = namespaces;
    }
    
}
