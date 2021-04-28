package org.integratedmodelling.klab.documentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimTable;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.classification.IClassification;
import org.integratedmodelling.klab.api.data.general.IStructuredTable;
import org.integratedmodelling.klab.api.documentation.IReport;
import org.integratedmodelling.klab.api.documentation.IReport.SectionRole;
import org.integratedmodelling.klab.api.documentation.IReport.View;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.observations.IKnowledgeView;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.rest.IObservationReference;
import org.integratedmodelling.klab.api.services.IModelService.IRankedModel;
import org.integratedmodelling.klab.components.localstorage.impl.TimesliceLocator;
import org.integratedmodelling.klab.components.runtime.observations.State;
import org.integratedmodelling.klab.dataflow.ObservedConcept;
import org.integratedmodelling.klab.documentation.ReportSection.Element;
import org.integratedmodelling.klab.engine.resources.MergedResource;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.monitoring.Message;
import org.integratedmodelling.klab.rest.DocumentationEvent;
import org.integratedmodelling.klab.rest.DocumentationNode;
import org.integratedmodelling.klab.rest.DocumentationNode.Figure;
import org.integratedmodelling.klab.rest.DocumentationNode.Table;
import org.integratedmodelling.klab.rest.DocumentationNode.Type;
import org.integratedmodelling.klab.rest.KnowledgeViewReference;
import org.integratedmodelling.klab.utils.MarkdownUtils;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.StringUtil;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.attributes.AttributesExtension;
import com.vladsch.flexmark.ext.definition.DefinitionExtension;
import com.vladsch.flexmark.ext.enumerated.reference.EnumeratedReferenceExtension;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.ext.media.tags.MediaTagsExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataSet;

/**
 * The structured version of the report, to substitute the simpler report based on a document view.
 * Uses the beans from the rest package directly. Each report holds a doctree with info on
 * everything that has gone through it. The tree is serializable to JSON. Eventually this may
 * substitute the Report object in its entirety.
 * <p>
 * From a client perspective:
 * <ul>
 * <li>Every individual documentation item is notified with a message and an ID, with the ID of the
 * context it belongs to. Nothing else is notified automatically.</li>
 * <li>Since the first notification, the view can ask for any of the view types, which will return a
 * graph of documentation types only referencing the IDs, possibly empty. The graph is produced
 * directly in JSON-compatible bean form.</li>
 * </ul>
 * 
 * @author Ferd
 *
 */
public class DocumentationTree {

    /**
     * Anchors in HTML code are sent using this pattern, to be reinterpreted by the client.
     */
    public static final String ANCHOR_PATTERN = "LINK/{type}/{id}/";

    private Map<String, DocumentationNode> nodes = new LinkedHashMap<>();
    private ISession session;
    private IRuntimeScope context;
    private Report report;
    private List<ReportSection> mainSections = new ArrayList<>();

    /*
     * this keeps track of which resources are used from temporally merged resourcesets
     */
    private Map<String, Map<String, IResource>> contextualizedResources = new HashMap<>();

    // models in order of usage
    private Set<IModel> models = new LinkedHashSet<>();
    // and the resolution for each observable
    private Map<ObservedConcept, List<IRankedModel>> resolutions = new HashMap<>();
    private Parser parser_;
    private HtmlRenderer renderer_;

    private int referencesCount;

    public DocumentationTree(Report report) {
        this.report = report;
    }

    public DocumentationTree(Report report, IRuntimeScope context, ISession identity) {
        this(report);
        this.context = context;
        this.session = identity;
    }

    private String md2html(String markdown) {
        if (this.renderer_ == null) {
            MutableDataSet options = new MutableDataSet().set(Parser.EXTENSIONS,
                    Arrays.asList(FootnoteExtension.create(), AttributesExtension.create(), EnumeratedReferenceExtension.create(),
                            MediaTagsExtension.create(), DefinitionExtension.create(), TablesExtension.create()));

            this.parser_ = Parser.builder(options).build();
            this.renderer_ = HtmlRenderer.builder(options).build();
        }
        Node document = parser_.parse(markdown);
        return renderer_.render(document);
    }

    public List<DocumentationNode> getView(View view, String format) {
        switch(view) {
        case FIGURES:
            return getFiguresView(format);
        case MODELS:
            return getModelsView(format);
        case REPORT:
            return getReportView(format);
        case RESOURCES:
            return getResourcesView(format);
        case TABLES:
            return getTablesView(format);
        case PROVENANCE:
            return getProvenanceView(format);
        }
        return null;
    }

    public static String getLinkText(DocumentationNode node) {
        return ANCHOR_PATTERN.replace("{type}", node.getType().name()).replace("{id}", node.getId());
    }

    // /**
    // * Add the item and notify the views so they can put that away.
    // *
    // * @param item
    // */
    // public void addNode(DocumentationNode item) {
    // nodes.put(item.getId(), item);
    // // TODO notify view
    // }

    public void addResolution(ObservedConcept observable, List<IRankedModel> resolved) {
        resolutions.put(observable, resolved);
    }

    /**
     * Add a first-class object TODO add the model that uses it, if any
     * 
     * @param o
     */
    public void addComputable(Object o) {

        DocumentationNode item = null;

        if (o instanceof IResource) {

            IResource resource = (IResource) o;
            if (nodes.get(resource.getUrn()) == null) {
                DocumentationNode node = getResourceNode(resource);
                if (node != null) {
                    nodes.put(node.getId(), node);
                    notify(node);
                }
            }

        } else if (o instanceof IPrototype) {

        } else if (o instanceof ReportSection) {

            this.mainSections.add((ReportSection) o);
            // This only to send a placeholder and say that there is a report
            notify(getReferencesNode());

        } else if (o instanceof IObservationReference) {

        } else if (o instanceof IKimTable) {

        } else if (o instanceof IClassification) {

        } else {
            System.out.println("OHIBÓ un cianfero non visto prima: " + o.getClass().getCanonicalName());
        }
    }

    private DocumentationNode getResourceNode(IResource resource) {

        if (resource instanceof MergedResource) {
            // we only document the individual (used) ones here.
            return null;
        }

        Urn urn = new Urn(resource.getUrn());

        DocumentationNode ret = new DocumentationNode();
        ret.setId(resource.getUrn());
        ret.setType(DocumentationNode.Type.Resource);
        ret.setTitle(resource.getMetadata().containsKey(IMetadata.DC_TITLE)
                ? resource.getMetadata().get(IMetadata.DC_TITLE).toString()
                : urn.getResourceId());
        
        
        DocumentationNode.Resource res = new DocumentationNode.Resource();
        

        if (resource.getMetadata().get(IMetadata.DC_URL) != null) {
            String content = resource.getMetadata().get(IMetadata.DC_URL).toString();
            for (String c : content.split("\\s*(;|,|\\s)\\s*")) {
                res.getUrls().add(c);
            }
        }
        if (resource.getMetadata().get(IMetadata.DC_COMMENT) != null) {
            String content = resource.getMetadata().get(IMetadata.DC_COMMENT).toString();
            res.setResourceDescription(MarkdownUtils.INSTANCE.format(content));
        }
        if (resource.getMetadata().get(IMetadata.DC_CREATOR) != null) {
            String content = resource.getMetadata().get(IMetadata.DC_CREATOR).toString();
            for (String c : content.split(";")) {
                res.getAuthors().add(c.trim());
            }
        }
        if (resource.getMetadata().get(IMetadata.IM_THEMATIC_AREA) != null) {
            String content = resource.getMetadata().get(IMetadata.IM_THEMATIC_AREA).toString();
            res.getKeywords().add(content);
        }
        if (resource.getMetadata().get(IMetadata.IM_GEOGRAPHIC_AREA) != null) {
            String content = resource.getMetadata().get(IMetadata.IM_GEOGRAPHIC_AREA).toString();
            res.getKeywords().add(content);
        }
        if (resource.getMetadata().get(IMetadata.DC_SOURCE) != null) {
            String content = resource.getMetadata().get(IMetadata.DC_SOURCE).toString();
            res.setBibliographicReference(content);
        }
        if (resource.getMetadata().get(IMetadata.IM_NOTES) != null) {
            String content = resource.getMetadata().get(IMetadata.IM_NOTES).toString();
            res.setAccessDescription(MarkdownUtils.INSTANCE.format(content));
        }
        if (resource.getMetadata().get(IMetadata.DC_ORIGINATOR) != null) {
            String content = resource.getMetadata().get(IMetadata.DC_ORIGINATOR).toString();
            res.setOriginatorDescription(content);
        }
        if (resource.getMetadata().get(IMetadata.IM_KEYWORDS) != null) {
            String content = resource.getMetadata().get(IMetadata.IM_KEYWORDS).toString();
            for (String c : content.split("\\s*(;|,|\\s)\\s*")) {
                res.getKeywords().add(c);
            }
        }
        res.setOriginatorDescription(resource.getMetadata().containsKey(IMetadata.DC_ORIGINATOR)
                ? resource.getMetadata().get(IMetadata.DC_ORIGINATOR).toString()
                : "Unknown originator");
        
        if (resource.getGeometry().getDimension(Dimension.Type.SPACE) != null) {
            res.setSpaceDescriptionUrl(API.ENGINE.RESOURCE.GET_RESOURCE_SPATIAL_IMAGE.replace("{urn}", resource.getUrn()));
        }
        if (resource.getGeometry().getDimension(Dimension.Type.TIME) != null) {
        }
        
        ret.setResource(res);
        return ret;
    }

    public void addView(IKnowledgeView view, KnowledgeViewReference descriptor) {

        DocumentationNode node = new DocumentationNode();
        if (!nodes.containsKey(view.getId())) {
            node.setId(view.getId());
            node.setTitle(view.getTitle());
            node.setBodyText(view.getLabel());
            if ("table".equals(view.getViewClass())) {
                node.setTable(view.getBean(Table.class));
                node.setType(DocumentationNode.Type.Table);
            }
            this.nodes.put(node.getId(), node);
            notify(node);
        }
    }

    // TODO add the contextualization
    public void addModel(IModel model) {
        if (!this.nodes.containsKey(model.getName()) && model.getStatement() != null) {
            DocumentationNode node = new DocumentationNode();
            node.setId(model.getName());
            node.setTitle(StringUtil.capitalize(model.getId().replace("_", " ")));
            node.setBodyText(model.getStatement().getSourceCode());
            node.setModel(((Model) model).getBean());
            node.setType(DocumentationNode.Type.Model);
            this.nodes.put(node.getId(), node);
            models.add(model);
            notify(node);
        }
    }

    // TODO add the model
    public void addObservation(IObservation observation) {

    }

    /**
     * Child citation
     * 
     * @param reportSection
     * @param reference
     */
    public DocumentationNode addCitation(Reference reference) {

        DocumentationNode.Reference ref = null;
        if (reference.get(BibTexFields.EXAMPLE_CITATION) == null) {
            // the key is the DOI
            String doi = reference.get("key").toString();
            if (!nodes.containsKey(doi)) {
                ref = Crossref.INSTANCE.resolve(doi);
            }
        } else {

            if (!nodes.containsKey(reference.get("key").toString())) {
                ref = new DocumentationNode.Reference();
                ref.getCitations().put("default", reference.get(BibTexFields.EXAMPLE_CITATION).toString());
                ref.setDoi(reference.get("key").toString());
            }
        }

        // add reference if not there already
        if (ref != null) {
            DocumentationNode node = new DocumentationNode();
            node.setBodyText(ref.getCitations().get("default"));
            node.setType(DocumentationNode.Type.Reference);
            node.setId(ref.getDoi());
            nodes.put(node.getId(), node);
            this.referencesCount++;
            notify(node);
        }

        return ref == null ? null : nodes.get(ref.getDoi());
    }

    private void notify(DocumentationNode node) {

        DocumentationEvent message = new DocumentationEvent();

        message.setNodeId(node.getId());
        message.setNodeType(node.getType());

        switch(node.getType()) {
        case Anchor:
            break;
        case Chart:
            message.getViewsAffected().add(IReport.View.FIGURES);
            break;
        case Citation:
            break;
        case Figure:
            message.getViewsAffected().add(IReport.View.FIGURES);
            break;
        case Link:
            break;
        case Model:
            message.getViewsAffected().add(IReport.View.MODELS);
            break;
        case Paragraph:
            message.getViewsAffected().add(IReport.View.REPORT);
            break;
        case Reference:
            message.getViewsAffected().add(IReport.View.REPORT);
            message.getViewsAffected().add(IReport.View.REFERENCES);
            break;
        case Report:
            message.getViewsAffected().add(IReport.View.REPORT);
            break;
        case Resource:
            message.getViewsAffected().add(IReport.View.RESOURCES);
            break;
        case Section:
            message.getViewsAffected().add(IReport.View.REPORT);
            break;
        case Table:
            message.getViewsAffected().add(IReport.View.REPORT);
            message.getViewsAffected().add(IReport.View.TABLES);
            break;
        case View:
            message.getViewsAffected().add(IReport.View.TABLES);
            break;
        default:
            break;

        }

        session.getMonitor().send(Message.create(session.getId(), IMessage.MessageClass.UserInterface,
                IMessage.Type.DocumentationChanged, message));
    }

    private List<DocumentationNode> getProvenanceView(String format) {
        List<DocumentationNode> ret = new ArrayList<>();
        return ret;
    }

    private List<DocumentationNode> getTablesView(String format) {
        List<DocumentationNode> ret = new ArrayList<>();
        for (DocumentationNode node : nodes.values()) {
            if (node.getType() == Type.Table) {
                ret.add(node);
            }
        }
        return ret;
    }

    private List<DocumentationNode> getResourcesView(String format) {
        List<DocumentationNode> ret = new ArrayList<>();
        for (DocumentationNode node : nodes.values()) {
            if (node.getType() == Type.Resource) {
                ret.add(node);
            }
        }
        return ret;
    }

    private List<DocumentationNode> getReportView(String format) {
        List<DocumentationNode> ret = new ArrayList<>();
        for (SectionRole order : SectionRole.values()) {
            boolean done = false;
            for (ReportSection section : mainSections) {
                if (order == section.getRole()) {
                    ret.add(compileSection(section, format));
                    done = true;
                }
            }
            if (order == SectionRole.REFERENCES && !done && this.referencesCount > 0) {
                ret.add(getReferencesNode());
            }
        }

        return ret;
    }

    private DocumentationNode getReferencesNode() {

        DocumentationNode ret = new DocumentationNode();
        ret.setId("References");
        ret.setType(Type.Section);
        ret.setTitle("References cited");
        for (DocumentationNode node : nodes.values()) {
            if (node.getType() == Type.Reference) {
                ret.getChildren().add(node);
            }
        }
        return ret;
    }

    private DocumentationNode compileSection(ReportSection section, String format) {

        DocumentationNode ret = new DocumentationNode();
        ret.setId(section.getId());
        ret.setType(Type.Section);
        ret.setTitle(section.getName() == null
                ? (section.getRole() == null ? null : StringUtil.capitalize(section.getRole().name().toLowerCase()))
                : section.getName());

        String body = section.body.toString();
        int offset = 0;
        for (Element element : section.elements) {
            if (element.startOffset > offset) {
                offset = compileParagraph(body, offset, element.startOffset, element.endOffset, ret, format);
            }
            DocumentationNode child = compileElement(element, format);
            if (child != null) {
                ret.getChildren().add(child);
            }
        }

        if (body.length() > offset) {
            compileParagraph(body, offset, body.length(), 0, ret, format);
        }

        if (section.role == SectionRole.REFERENCES) {
            for (DocumentationNode node : nodes.values()) {
                if (node.getType() == Type.Reference) {
                    ret.getChildren().add(node);
                }
            }
        }

        return ret;
    }

    private int compileParagraph(String body, int offset, int start, int end, DocumentationNode section, String format) {
        String paragraph = body.substring(offset, start);
        if ("html".equals(format)) {
            paragraph = md2html(paragraph);
        }
        DocumentationNode node = new DocumentationNode();
        node.setType(Type.Paragraph);
        node.setId("p_" + NameGenerator.shortUUID());
        node.setBodyText(paragraph);
        section.getChildren().add(node);
        return end;
    }

    private DocumentationNode compileElement(Element element, String format) {

        if (element.type == Type.Section) {
            return compileSection((ReportSection) element.element, format);
        } else if (element.type == Type.Anchor || element.type == Type.Citation || element.type == Type.Link) {
            return null;
        }

        DocumentationNode node = new DocumentationNode();
        node.setId(NameGenerator.shortUUID());
        node.setType(element.type);

        switch(element.type) {
        // case Anchor:
        // case Citation:
        // case Link:
        // node.setBodyText(element.element.toString());
        // break;
        case Chart:
            break;
        case Figure:
            node.setFigure((Figure) element.element);
            break;
        case Model:
            break;
        case Reference:
            break;
        case Resource:
            break;
        case Table:
            node.setTable((Table) element.element);
            break;
        case View:
            break;
        default:
            break;
        }

        return node;
    }

    private List<DocumentationNode> getModelsView(String format) {
        List<DocumentationNode> ret = new ArrayList<>();
        for (DocumentationNode node : nodes.values()) {
            if (node.getType() == Type.Model) {
                ret.add(node);
            }
        }
        return ret;
    }

    private List<DocumentationNode> getFiguresView(String format) {
        List<DocumentationNode> ret = new ArrayList<>();
        for (DocumentationNode node : nodes.values()) {
            if (node.getType() == Type.Figure) {
                ret.add(node);
            }
        }
        return ret;
    }

    /**
     * Notify that a resource out of a merged resource set has been used in this scope.
     * 
     * @param urn
     * @param first
     */
    public void addContextualizedResource(String urn, IResource resource) {
        Map<String, IResource> ret = this.contextualizedResources.get(urn);
        if (ret == null) {
            ret = new LinkedHashMap<>();
            this.contextualizedResources.put(urn, ret);
            if (!nodes.containsKey(resource.getUrn())) {
                DocumentationNode node = getResourceNode(resource);
                if (node != null) {
                    nodes.put(node.getId(), node);
                    notify(node);
                }
            }
        }
        ret.put(resource.getUrn(), resource);
    }

    public List<IResource> getContextualizedResources(String urn) {
        List<IResource> ret = new ArrayList<>();
        Map<String, IResource> ress = this.contextualizedResources.get(urn);
        if (ress != null) {
            for (IResource res : ress.values()) {
                ret.add(res);
            }
        }
        return ret;
    }

    public static Table getTableDescriptor(IStructuredTable<?> table, Object[] args) {
        Table ret = new Table();
        return ret;
    }

    public static Figure getFigureDescriptor(IArtifact artifact, IObservationReference ref, Object[] args) {
        
        Figure ret = new Figure();
        
        String id = args.length > 1 ? args[1].toString() : ("fig" + NameGenerator.shortUUID()); 
        String caption = "";
        if (args.length > 2) {
            StringBuffer c = new StringBuffer(512);
            for (int n = 2; n < args.length; n++) {
                c.append(n == 2 ? args[n].toString() : (" " + args[n]));
            }
            caption = c.toString();
        }
        
        ret.setId(id);
        ret.setCaption(caption);    
        ret.setObservationId(ret.getId());
        ret.setLabel(ref.getLabel());
        
        /**
         * Must add output type, locator and viewport
         */
        String baseUrl = API.ENGINE.OBSERVATION.VIEW.GET_DATA_OBSERVATION.replace("{observation}", artifact.getId());
        ret.setObservationType(ref.getObservationType());
        ret.getGeometryTypes().addAll(ref.getGeometryTypes());
        ret.setObservableType(ref.getObservableType());
        
        if (artifact instanceof State) {
            for (ILocator locator : ((State)artifact).getSliceLocators()) {
                TimesliceLocator sl = (TimesliceLocator)locator;
                ret.getTimeSlices().add(sl.getLocatorCode() + "," + sl.getLabel());
            }
        }

        ret.setDataSummary(ref.getDataSummary());
        ret.setBaseUrl(baseUrl);
        
        return ret;
    }

}
