package org.integratedmodelling.klab.rest;

/**
 * Parent class for a member of the documentation tree. This will substitute the report and the
 * resource/model metadata through the specific subclasses.
 * 
 * @author Ferd
 *
 */
public class DocumentationItem {

    public static enum Type {
        Report, Section, Table, Chart, Resource, Model, BibliographicReference
    }

    private Type type;
    private String id;
    private String parentId;
    private String previousId;
    private String nextId;
    private String title;
    private String subtitle;

    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getParentId() {
        return parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public String getPreviousId() {
        return previousId;
    }
    public void setPreviousId(String previousId) {
        this.previousId = previousId;
    }
    public String getNextId() {
        return nextId;
    }
    public void setNextId(String nextId) {
        this.nextId = nextId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getSubtitle() {
        return subtitle;
    }
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

}
