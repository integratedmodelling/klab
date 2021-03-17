package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphReference<T> {

    public static class Link {
        String source;
        String destination;
    }

    private Map<String, T> objects = new HashMap<>();
    private List<Link> links = new ArrayList<>();
    public Map<String, T> getObjects() {
        return objects;
    }
    public void setObjects(Map<String, T> objects) {
        this.objects = objects;
    }
    public List<Link> getLinks() {
        return links;
    }
    public void setLinks(List<Link> links) {
        this.links = links;
    }

}
