package org.integratedmodelling.klab.rest;

/**
 * Parent class for a member of the documentation tree. This will substitute the report and the
 * resource/model metadata through the specific subclasses. The DocumentationTree exposes graphs of
 * these for all the different views.
 * 
 * @author Ferd
 *
 */
public class DocumentationItem {

    public static enum Type {
        Report, Section, Table, Chart, Resource, Model, Reference
    }

    public static class Model {

    }

    public static class Section {

    }

    public static class Resource {

    }

    public static class Table {

    }

    public static class Figure {

    }

    public static class Chart {

    }

    public static class Reference {

    }

    private Type type;
    private String id;
    private String parentId;
    private String previousId;
    private String nextId;
    private String title;
    private String subtitle;

    /*
     * Only one of these below gets filled, according to the type.
     */

    private Model model;
    private Section section;
    private Resource resource;
    private Table table;
    private Figure figure;
    private Reference reference;

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
    public Model getModel() {
        return model;
    }
    public void setModel(Model model) {
        this.model = model;
    }
    public Section getSection() {
        return section;
    }
    public void setSection(Section section) {
        this.section = section;
    }
    public Resource getResource() {
        return resource;
    }
    public void setResource(Resource resource) {
        this.resource = resource;
    }
    public Table getTable() {
        return table;
    }
    public void setTable(Table table) {
        this.table = table;
    }
    public Figure getFigure() {
        return figure;
    }
    public void setFigure(Figure figure) {
        this.figure = figure;
    }
    public Reference getReference() {
        return reference;
    }
    public void setReference(Reference reference) {
        this.reference = reference;
    }

}
