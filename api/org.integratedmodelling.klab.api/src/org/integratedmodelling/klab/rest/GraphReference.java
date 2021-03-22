package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Graph encoded as a list of objects and links between them. For easier navigation we keep a list
 * of node IDs that have no explicit parent, in whatever natural order implied. Multiple "root" IDs
 * are possible (graph, not tree) as are cyclic references, according to implementation. Links are
 * ordered and typed, so that multiple link roles (e.g. child, sibling etc.) are possible.
 * 
 * @author Ferd
 *
 * @param <T>
 */
public class GraphReference<T> {

    public static class Link {
        String type;
        String source;
        String destination;
    }

    private Map<String, T> objects = new HashMap<>();
    private List<Link> links = new ArrayList<>();
    private List<String> rootObjectIds = new ArrayList<>();

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
    public List<String> getRootObjectIds() {
        return rootObjectIds;
    }
    public void setRootObjectIds(List<String> rootObjectIds) {
        this.rootObjectIds = rootObjectIds;
    }

    public void link(String source, String destination) {
        Link link = new Link();
        link.source = source;
        link.destination = destination;
        links.add(link);
    }

    public void link(String source, String destination, String type) {
        Link link = new Link();
        link.source = source;
        link.destination = destination;
        link.type = type;
        links.add(link);
    }
    
    public List<String> incoming(String id) {
        List<String> ret = new ArrayList<>();
        for (Link link : links) {
            if (id.equals(link.destination)) {
                ret.add(link.source);
            }
        }
        return ret;
    }

    public List<String> outgoing(String id) {
        List<String> ret = new ArrayList<>();
        for (Link link : links) {
            if (id.equals(link.source)) {
                ret.add(link.destination);
            }
        }
        return ret;
    }

}
