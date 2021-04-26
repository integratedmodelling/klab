package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.klab.api.provenance.IArtifact;

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

        public static class Computation {

            private IContextualizable.Type type;
            private String name;
            private String description;
            private String resourceId;
            private String contents;
            public IContextualizable.Type getType() {
                return type;
            }
            public void setType(IContextualizable.Type type) {
                this.type = type;
            }
            public String getName() {
                return name;
            }
            public void setName(String name) {
                this.name = name;
            }
            public String getDescription() {
                return description;
            }
            public void setDescription(String description) {
                this.description = description;
            }
            public String getResourceId() {
                return resourceId;
            }
            public void setResourceId(String resourceId) {
                this.resourceId = resourceId;
            }
            public String getContents() {
                return contents;
            }
            public void setContents(String contents) {
                this.contents = contents;
            }

        }

        private String description;
        private String title;
        private String namespaceDescription;
        private String accessDescription;
        private String sourceCode;
        private String observableDescription;
        private List<String> observableSemantics = new ArrayList<>();
        private List<String> dependencySemantics = new ArrayList<>();
        private List<Computation> computations = new ArrayList<>();
        private Map<String, Double> ranks = new LinkedHashMap<>();
        private List<Model> otherCandidates = new ArrayList<>();

        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        public String getNamespaceDescription() {
            return namespaceDescription;
        }
        public void setNamespaceDescription(String namespaceDescription) {
            this.namespaceDescription = namespaceDescription;
        }
        public String getAccessDescription() {
            return accessDescription;
        }
        public void setAccessDescription(String accessDescription) {
            this.accessDescription = accessDescription;
        }
        public String getSourceCode() {
            return sourceCode;
        }
        public void setSourceCode(String sourceCode) {
            this.sourceCode = sourceCode;
        }
        public String getObservableDescription() {
            return observableDescription;
        }
        public void setObservableDescription(String observableDescription) {
            this.observableDescription = observableDescription;
        }
        public List<String> getObservableSemantics() {
            return observableSemantics;
        }
        public void setObservableSemantics(List<String> observableSemantics) {
            this.observableSemantics = observableSemantics;
        }
        public List<String> getDependencySemantics() {
            return dependencySemantics;
        }
        public void setDependencySemantics(List<String> dependencySemantics) {
            this.dependencySemantics = dependencySemantics;
        }
        public List<Computation> getComputations() {
            return computations;
        }
        public void setComputations(List<Computation> computations) {
            this.computations = computations;
        }
        public Map<String, Double> getRanks() {
            return ranks;
        }
        public void setRanks(Map<String, Double> ranks) {
            this.ranks = ranks;
        }
        public List<Model> getOtherCandidates() {
            return otherCandidates;
        }
        public void setOtherCandidates(List<Model> otherCandidates) {
            this.otherCandidates = otherCandidates;
        }

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

        private String resourceId;
        private String catalogId;
        private String namespaceId;
        private String nodeId;
        private String resourceDescription;
        private String catalogDescription;
        private String namespaceDescription;
        private String nodeDescription;
        private String spaceDescriptionUrl;
        private String accessDescription;
        private String timeDescription;
        private String originatorDescription;
        private List<String> urls = new ArrayList<>();
        private List<String> authors = new ArrayList<>();
        private Date releaseDate;
        private Date resourceDate;
        private Date startTimeCoverage;
        private Date endTimeCoverage;
        private String sourceVersion;
        private String resourceVersion;
        private String adapterId;
        private String adapterVersion;
        private String adapterDescription;
        private String adapterIconUrl;
        private List<String> keywords = new ArrayList<>();
        private double usageRank;

        public String getResourceId() {
            return resourceId;
        }
        public void setResourceId(String resourceId) {
            this.resourceId = resourceId;
        }
        public String getCatalogId() {
            return catalogId;
        }
        public void setCatalogId(String catalogId) {
            this.catalogId = catalogId;
        }
        public String getNamespaceId() {
            return namespaceId;
        }
        public void setNamespaceId(String namespaceId) {
            this.namespaceId = namespaceId;
        }
        public String getNodeId() {
            return nodeId;
        }
        public void setNodeId(String nodeId) {
            this.nodeId = nodeId;
        }
        public String getResourceDescription() {
            return resourceDescription;
        }
        public void setResourceDescription(String resourceDescription) {
            this.resourceDescription = resourceDescription;
        }
        public String getCatalogDescription() {
            return catalogDescription;
        }
        public void setCatalogDescription(String catalogDescription) {
            this.catalogDescription = catalogDescription;
        }
        public String getNamespaceDescription() {
            return namespaceDescription;
        }
        public void setNamespaceDescription(String namespaceDescription) {
            this.namespaceDescription = namespaceDescription;
        }
        public String getNodeDescription() {
            return nodeDescription;
        }
        public void setNodeDescription(String nodeDescription) {
            this.nodeDescription = nodeDescription;
        }
        public String getSpaceDescriptionUrl() {
            return spaceDescriptionUrl;
        }
        public void setSpaceDescriptionUrl(String spaceDescriptionUrl) {
            this.spaceDescriptionUrl = spaceDescriptionUrl;
        }
        public String getAccessDescription() {
            return accessDescription;
        }
        public void setAccessDescription(String accessDescription) {
            this.accessDescription = accessDescription;
        }
        public String getTimeDescription() {
            return timeDescription;
        }
        public void setTimeDescription(String timeDescription) {
            this.timeDescription = timeDescription;
        }
        public String getOriginatorDescription() {
            return originatorDescription;
        }
        public void setOriginatorDescription(String originatorDescription) {
            this.originatorDescription = originatorDescription;
        }
        public List<String> getAuthors() {
            return authors;
        }
        public void setAuthors(List<String> authors) {
            this.authors = authors;
        }
        public Date getReleaseDate() {
            return releaseDate;
        }
        public void setReleaseDate(Date releaseDate) {
            this.releaseDate = releaseDate;
        }
        public Date getResourceDate() {
            return resourceDate;
        }
        public void setResourceDate(Date resourceDate) {
            this.resourceDate = resourceDate;
        }
        public Date getStartTimeCoverage() {
            return startTimeCoverage;
        }
        public void setStartTimeCoverage(Date startTimeCoverage) {
            this.startTimeCoverage = startTimeCoverage;
        }
        public Date getEndTimeCoverage() {
            return endTimeCoverage;
        }
        public void setEndTimeCoverage(Date endTimeCoverage) {
            this.endTimeCoverage = endTimeCoverage;
        }
        public String getSourceVersion() {
            return sourceVersion;
        }
        public void setSourceVersion(String sourceVersion) {
            this.sourceVersion = sourceVersion;
        }
        public String getResourceVersion() {
            return resourceVersion;
        }
        public void setResourceVersion(String resourceVersion) {
            this.resourceVersion = resourceVersion;
        }
        public String getAdapterId() {
            return adapterId;
        }
        public void setAdapterId(String adapterId) {
            this.adapterId = adapterId;
        }
        public String getAdapterVersion() {
            return adapterVersion;
        }
        public void setAdapterVersion(String adapterVersion) {
            this.adapterVersion = adapterVersion;
        }
        public String getAdapterDescription() {
            return adapterDescription;
        }
        public void setAdapterDescription(String adapterDescription) {
            this.adapterDescription = adapterDescription;
        }
        public String getAdapterIconUrl() {
            return adapterIconUrl;
        }
        public void setAdapterIconUrl(String adapterIconUrl) {
            this.adapterIconUrl = adapterIconUrl;
        }
        public double getUsageRank() {
            return usageRank;
        }
        public void setUsageRank(double usageRank) {
            this.usageRank = usageRank;
        }
        public List<String> getKeywords() {
            return keywords;
        }
        public void setKeywords(List<String> keywords) {
            this.keywords = keywords;
        }
        public List<String> getUrls() {
            return urls;
        }
        public void setUrls(List<String> urls) {
            this.urls = urls;
        }

    }

    public static class Table {

        public static class Column {

            private String title;
            private String id;
            private boolean headerVertical;
            private String sorter;
            private String hozAlign;
            private String formatter;
            private IArtifact.Type type;
            private boolean frozen;
            private String caption;
            private String numberformat;

            private List<Column> columns = new ArrayList<>();

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
            public List<Column> getColumns() {
                return columns;
            }
            public void setColumns(List<Column> columns) {
                this.columns = columns;
            }
            public IArtifact.Type getType() {
                return type;
            }
            public void setType(IArtifact.Type type) {
                this.type = type;
            }
            public boolean isFrozen() {
                return frozen;
            }
            public void setFrozen(boolean frozen) {
                this.frozen = frozen;
            }
            public String getCaption() {
                return caption;
            }
            public void setCaption(String caption) {
                this.caption = caption;
            }
            public String getNumberformat() {
                return numberformat;
            }
            public void setNumberformat(String numberformat) {
                this.numberformat = numberformat;
            }
        }

        private List<Column> columns = new ArrayList<>();

        /**
         * Data come as strings to avoid having to enable polymorphism in JSON; the type is in the
         * column definition. For types other than POD, the type may also select a formatter based
         * on configuration.
         */
        private List<Map<String, String>> rows = new ArrayList<>();

        /**
         * printf-style number format pattern. Comes directly from table specs, if any, so we can
         * make it what we want.
         */
        private String numberFormat;

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

        public String getNumberFormat() {
            return numberFormat;
        }

        public void setNumberFormat(String numberFormat) {
            this.numberFormat = numberFormat;
        }

    }

    public static class Figure {

        private String caption;
        private String label;

        /**
         * Either an (absolute or relative) image URL or the URL to fetch the PNG, passing the
         * locator=LOC:<n> with n = index of the desired slice. Internal endpoints differ if the
         * image is just an image (timeSlices is empty) or a visualized observation (timeslices has
         * at least one value for initialization).
         */
        private String baseUrl;

        /**
         * A label per each of the available timeslices. If empty, image is not an observation but
         * just a URL to display.
         */
        private List<String> timeSlices = new ArrayList<>();

        /**
         * Only non-null if raster map
         */
        private Histogram legend;

        /**
         * Only non-null if raster map
         */
        private Colormap colormap;

        public String getCaption() {
            return caption;
        }
        public void setCaption(String caption) {
            this.caption = caption;
        }
        public String getLabel() {
            return label;
        }
        public void setLabel(String label) {
            this.label = label;
        }
        public String getBaseUrl() {
            return baseUrl;
        }
        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }
        public List<String> getTimeSlices() {
            return timeSlices;
        }
        public void setTimeSlices(List<String> timeSlices) {
            this.timeSlices = timeSlices;
        }
        public Histogram getLegend() {
            return legend;
        }
        public void setLegend(Histogram legend) {
            this.legend = legend;
        }
        public Colormap getColormap() {
            return colormap;
        }
        public void setColormap(Colormap colormap) {
            this.colormap = colormap;
        }

    }

    public static class Chart {

    }

    public static class Reference {

        public static class Author {
            private String name;
            private String affiliation;
            public String getName() {
                return name;
            }
            public void setName(String name) {
                this.name = name;
            }
            public String getAffiliation() {
                return affiliation;
            }
            public void setAffiliation(String affiliation) {
                this.affiliation = affiliation;
            }
        }

        private String doi;
        private String type;
        private String publisher;
        private String container;
        private int volume;
        private int issue;
        private String pageRange;
        private String source;
        private int citedBy;
        private String title;
        private List<Author> authors = new ArrayList<>();
        private String language;
        private String link;
        private String doiUrl;
        private Date date;
        private String issn;
        private String containerShort;

        /**
         * Citation by style, "default" must always be there.
         */
        private Map<String, String> citations = new HashMap<>();

        public String getDoi() {
            return doi;
        }
        public void setDoi(String doi) {
            this.doi = doi;
        }
        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
        public String getPublisher() {
            return publisher;
        }
        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }
        public String getContainer() {
            return container;
        }
        public void setContainer(String container) {
            this.container = container;
        }
        public int getVolume() {
            return volume;
        }
        public void setVolume(int volume) {
            this.volume = volume;
        }
        public String getPageRange() {
            return pageRange;
        }
        public void setPageRange(String pageRange) {
            this.pageRange = pageRange;
        }
        public String getSource() {
            return source;
        }
        public void setSource(String source) {
            this.source = source;
        }
        public int getCitedBy() {
            return citedBy;
        }
        public void setCitedBy(int citedBy) {
            this.citedBy = citedBy;
        }
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        public List<Author> getAuthors() {
            return authors;
        }
        public void setAuthors(List<Author> authors) {
            this.authors = authors;
        }
        public String getLanguage() {
            return language;
        }
        public void setLanguage(String language) {
            this.language = language;
        }
        public String getLink() {
            return link;
        }
        public void setLink(String link) {
            this.link = link;
        }
        public String getDoiUrl() {
            return doiUrl;
        }
        public void setDoiUrl(String doiUrl) {
            this.doiUrl = doiUrl;
        }
        public Date getDate() {
            return date;
        }
        public void setDate(Date date) {
            this.date = date;
        }
        public String getIssn() {
            return issn;
        }
        public void setIssn(String issn) {
            this.issn = issn;
        }
        public Map<String, String> getCitations() {
            return citations;
        }
        public void setCitations(Map<String, String> citations) {
            this.citations = citations;
        }
        public int getIssue() {
            return issue;
        }
        public void setIssue(int issue) {
            this.issue = issue;
        }
        public String getContainerShort() {
            return containerShort;
        }
        public void setContainerShort(String containerShort) {
            this.containerShort = containerShort;
        }

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
