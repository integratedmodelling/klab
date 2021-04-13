package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Parent class for a member of the documentation tree. This will substitute the report and the
 * resource/model metadata through the specific subclasses. The DocumentationTree exposes graphs of
 * these for all the different views.
 * 
 * @author Ferd
 *
 */
public class DocumentationNode {

    public static enum Type {
        Report, Section, Paragraph, Table, Chart, Figure, Resource, Model, Reference, Citation, View, Link, Anchor
    }

    public static class Model {

    }

    public static class Section {
        private String text;
        private int level;
        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }
    }

    public static class Resource {

    }

    public static class Table {

        public static class Column {

            private String title;
            private String id;
            private boolean headerVertical;
            private String sorter;
            private String hozAlign;
            private String formatter;
            
            public String getTitle() {
                return title;
            }
            public void setTitle(String title) {
                this.title = title;
            }
            public String getId() {
                return id;
            }
            public void setId(String id) {
                this.id = id;
            }
            public boolean isHeaderVertical() {
                return headerVertical;
            }
            public void setHeaderVertical(boolean headerVertical) {
                this.headerVertical = headerVertical;
            }
            public String getSorter() {
                return sorter;
            }
            public void setSorter(String sorter) {
                this.sorter = sorter;
            }
            public String getHozAlign() {
                return hozAlign;
            }
            public void setHozAlign(String hozAlign) {
                this.hozAlign = hozAlign;
            }
            public String getFormatter() {
                return formatter;
            }
            public void setFormatter(String formatter) {
                this.formatter = formatter;
            }
            
            
        }

        private List<Column> columns = new ArrayList<>();

        /**
         * Data come as strings to avoid having to enable polymorphism in JSON; the type is in the
         * column definition. For types other than POD, the type may also select a formatter based
         * on configuration.
         */
        private List<Map<String, String>> rows = new ArrayList<>();

        public List<Column> getColumns() {
            return columns;
        }

        public void setColumns(List<Column> columns) {
            this.columns = columns;
        }

        public List<Map<String, String>> getRows() {
            return rows;
        }

        public void setRows(List<Map<String, String>> rows) {
            this.rows = rows;
        }

        
        
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
    private String bodyText; // only filled in paragraphs, anchors, links and citations
    private List<DocumentationNode> children = new ArrayList<>();

    /*
     * Only one of these below gets filled, according to the type. For the simplest types all are
     * null and bodyText carries the content.
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

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    public String getBodyText() {
        return this.bodyText;
    }
    public List<DocumentationNode> getChildren() {
        return children;
    }
    public void setChildren(List<DocumentationNode> children) {
        this.children = children;
    }

}
