package org.integratedmodelling.klab.documentation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimTable;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.classification.IClassification;
import org.integratedmodelling.klab.api.data.general.IStructuredTable;
import org.integratedmodelling.klab.api.documentation.IReport.SectionRole;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.observations.IKnowledgeView;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.rest.IObservationReference;
import org.integratedmodelling.klab.api.services.IModelService.IRankedModel;
import org.integratedmodelling.klab.dataflow.ObservedConcept;
import org.integratedmodelling.klab.documentation.ReportSection.Element;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.rest.DocumentationNode;
import org.integratedmodelling.klab.rest.DocumentationNode.Figure;
import org.integratedmodelling.klab.rest.DocumentationNode.Table;
import org.integratedmodelling.klab.rest.DocumentationNode.Type;
import org.integratedmodelling.klab.rest.KnowledgeViewReference;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.StringUtil;
import org.jgraph.graph.DefaultEdge;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;

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

//    private final static String ROOT_NODE = "__root__";

    // all items, which for now can be DocumentationNode, ReportSection, or Reference
    Map<String, DocumentationNode> nodes = new HashMap<>();
    ISession session;
    IRuntimeScope context;
    Report report;
//    // mutual dependencies by ID (key in nodes). Root node is ROOT_NODE.
//    @Deprecated
//    Graph<String, DefaultEdge> structure = new DefaultDirectedGraph<>(DefaultEdge.class);
//    private String refSectionId;

    private List<ReportSection> mainSections = new ArrayList<>();

    /*
     * this keeps track of which resources are used from temporally merged resourcesets
     */
    private Map<String, Map<String, IResource>> contextualizedResources = new HashMap<>();

    // models in order of usage
    private Set<IModel> models = new LinkedHashSet<>();
    // and the resolution for each observable
    private Map<ObservedConcept, List<IRankedModel>> resolutions = new HashMap<>();

    public enum View {
        REPORT, FIGURES, TABLES, RESOURCES, MODELS, PROVENANCE
    }

    public DocumentationTree(Report report) {
        this.report = report;
//        this.structure.addVertex(ROOT_NODE);
    }

    public DocumentationTree(Report report, IRuntimeScope context, ISession identity) {
        // TODO Auto-generated constructor stub
        this(report);
        this.context = context;
        this.session = identity;
    }

    public List<DocumentationNode> getView(View view) {
        switch(view) {
        case FIGURES:
            return getFiguresView();
        case MODELS:
            return getModelsView();
        case REPORT:
            return getReportView();
        case RESOURCES:
            return getResourcesView();
        case TABLES:
            return getTablesView();
        case PROVENANCE:
            return getProvenanceView();
        }
        return null;
    }

    /**
     * Add the item and notify the views so they can put that away.
     * 
     * @param item
     */
    public void addNode(DocumentationNode item) {
        nodes.put(item.getId(), item);
        // TODO notify view
    }

    public void addResolution(ObservedConcept observable, List<IRankedModel> resolved) {
        resolutions.put(observable, resolved);
    }

    /**
     * Add a first-class object
     * 
     * @param o
     */
    public void add(Object o) {

        DocumentationNode item = null;

        if (o instanceof IResource) {

//            item = getItem(Type.Resource);

        } else if (o instanceof IPrototype) {

        } else if (o instanceof ReportSection) {

            this.mainSections.add((ReportSection) o);

            // nodes.put(((ReportSection) o).getId(), o);
            // structure.addVertex(((ReportSection) o).getId());
            // structure.addEdge(((ReportSection) o).getId(), ROOT_NODE);
            //
            // // save the reference section to append refs to.
            // if (((ReportSection) o).getRole() == SectionRole.REFERENCES) {
            // this.refSectionId = ((ReportSection) o).getId();
            // }

        } else if (o instanceof IObservationReference) {

        } else if (o instanceof IKimTable) {

        } else if (o instanceof IClassification) {

        } else {
            System.out.println("OHIBÓ un cianfero non visto prima");
        }

//        if (item != null) {
//            addNode(item);
//        }
    }

//    private DocumentationNode getItem(Type type) {
//        DocumentationNode ret = new DocumentationNode();
//        ret.setType(type);
//        ret.setId(NameGenerator.shortUUID());
//        return ret;
//    }

//    private DocumentationNode getItem(Type type, ReportSection parent) {
//        DocumentationNode ret = getItem(type);
////        ret.setRelativePosition(parent.body.length());
//        return ret;
//    }

    public void addModel(IModel model) {
        models.add(model);
    }

    public void addObservation(IObservation observation) {

    }

//    /**
//     * Add a child section
//     */
//    public void add(ReportSection section, ReportSection parent) {
//        // TODO insert in tree; if figure
//        System.out.println("SUBSECTION " + section);
//        nodes.put(section.getId(), section);
////        structure.addVertex(section.getId());
////        structure.addEdge(section.getId(), parent.getId());
//    }

//    /**
//     * Child figure (will split paragraphs)
//     * 
//     * @param reportSection
//     * @param ref
//     */
//    public void addFigure(ReportSection reportSection, IObservationReference ref) {
//        // TODO Auto-generated method stub
//        System.out.println("FIGURE " + ref);
//        DocumentationNode item = getItem(Type.Figure, reportSection);
//        addNode(item);
////        structure.addVertex(item.getId());
////        structure.addEdge(item.getId(), reportSection.getId());
//    }

//    /**
//     * Child table (split paragraph)
//     * 
//     * @param reportSection
//     * @param table
//     */
//    public void addTable(ReportSection reportSection, IStructuredTable<?> table) {
//        // TODO Auto-generated method stub
//        System.out.println("TABLE " + table);
//        DocumentationNode item = getItem(Type.Table, reportSection);
//        addNode(item);
////        structure.addVertex(item.getId());
////        structure.addEdge(item.getId(), reportSection.getId());
//    }

    /**
     * Child citation
     * 
     * @param reportSection
     * @param reference
     */
    public void addCitation(Reference reference) {

        System.out.println("CITATION " + reference);

        // TODO add reference if not there already
        
        
        //        nodes.put(reference.get("key"), reference);
//        DocumentationNode item = getItem(Type.Citation, reportSection);

        
        //        addNode(item);
//        structure.addVertex(item.getId());
//        structure.addEdge(item.getId(), reportSection.getId());
//        if (refSectionId != null) {
//            structure.addVertex(reference.get("key"));
//            structure.addEdge(reference.get("key"), refSectionId);
//        } else {
//            // shouldn't happen, but in case, say something nice
//            System.out.println("ZIOCAN NO REF SECTION");
//        }
    }

    private List<DocumentationNode> getProvenanceView() {
        List<DocumentationNode> ret = new ArrayList<>();
        return ret;
    }

    private List<DocumentationNode> getTablesView() {
        List<DocumentationNode> ret = new ArrayList<>();
        return ret;
    }

    private List<DocumentationNode> getResourcesView() {
        List<DocumentationNode> ret = new ArrayList<>();
        return ret;
    }

    private List<DocumentationNode> getReportView() {
        List<DocumentationNode> ret = new ArrayList<>();
        for (SectionRole order : SectionRole.values()) {
            for (ReportSection section : mainSections) {
                if (order == section.getRole()) {
                    ret.add(compileSection(section));
                }
            }
        }

        return ret;
    }

    private DocumentationNode compileSection(ReportSection section) {

        DocumentationNode ret = new DocumentationNode();
        ret.setId(section.getId());
        ret.setType(Type.Section);
        ret.setTitle(section.getName() == null
                ? (section.getRole() == null ? null : StringUtil.capitalize(section.getRole().name().toLowerCase()))
                : section.getName());
        
//        graph.getObjects().put(ret.getId(), ret);
        
        String body = section.body.toString();
        int offset = 0;
        for (Element element : section.elements) {
            if (element.startOffset > offset) {
                offset = compileParagraph(body, offset, element.startOffset, element.endOffset, ret);
            }
            DocumentationNode child = compileElement(element);
            if (child != null) {
                ret.getChildren().add(child);
            }
       }
        
        if (body.length() > offset) {
            compileParagraph(body, offset, body.length(), 0, ret);
        }
        
        return ret;
    }

    private int compileParagraph(String body, int offset, int start, int end, DocumentationNode section) {
        String paragraph = body.substring(offset, start);
        DocumentationNode node = new DocumentationNode();
        node.setType(Type.Paragraph);
        node.setId("p_" + NameGenerator.shortUUID());
        node.setBodyText(paragraph);
        section.getChildren().add(node);
        return end;
    }

    private DocumentationNode compileElement(Element element) {

        if (element.type == Type.Section) {
            return compileSection((ReportSection)element.element);
        }
        
        DocumentationNode node = new DocumentationNode();
        node.setId(NameGenerator.shortUUID());
        node.setType(element.type);
        
        switch (element.type) {
        case Anchor:
        case Citation:
        case Link:
            node.setBodyText(element.element.toString());
            break;
        case Chart:
            break;
        case Figure:
            node.setFigure((Figure)element.element);
            break;
        case Model:
            break;
        case Reference:
            break;
        case Resource:
            break;
        case Table:
            node.setTable((Table)element.element);
            break;
        case View:
            break;
        default:
            break;
        }
        
        return node;
    }

//    @Deprecated
//    private List<DocumentationNode> getReportViewOld() {
//        Document document = new Document();
//        for (SectionRole order : SectionRole.values()) {
//            addReportSection(document, order);
//        }
//        return document.getGraph();
//    }
//
//    @Deprecated
//    private void addReportSection(Document document, SectionRole role) {
//        for (DefaultEdge edge : structure.incomingEdgesOf(ROOT_NODE)) {
//            String id = structure.getEdgeSource(edge);
//            if (nodes.get(id) instanceof ReportSection && (((ReportSection) nodes.get(id)).getRole() == role)) {
//                makeSectionNode(document, (ReportSection) nodes.get(id), role);
//            }
//        }
//    }
//
//    @Deprecated
//    private DocumentationNode makeSectionNode(Document document, ReportSection reportSection, SectionRole role) {
//
//        DocumentationNode ret = new DocumentationNode();
//        ret.setId(NameGenerator.shortUUID());
//        ret.setTitle(reportSection.getName());
//        List<DocumentationNode> children = new ArrayList<>();
//        for (DefaultEdge edge : structure.incomingEdgesOf(reportSection.getId())) {
//            Object child = nodes.get(structure.getEdgeSource(edge));
//            if (child instanceof DocumentationNode) {
//                children.add((DocumentationNode) child);
//            }
//        }
//
//        document.add(role, reportSection, children);
//
//        return ret;
//    }

    private List<DocumentationNode> getModelsView() {
        List<DocumentationNode> ret = new ArrayList<>();
        return ret;
    }

    private List<DocumentationNode> getFiguresView() {
        List<DocumentationNode> ret = new ArrayList<>();
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

    public static Object getTableDescriptor(IStructuredTable<?> table, Object[] args) {
        Table ret = new Table();
        return ret;
    }

    public static Object getFigureDescriptor(IArtifact artifact, IObservationReference ref, Object[] args) {
        Figure ret = new Figure();
        return ret;
    }

    public void addView(IKnowledgeView view, KnowledgeViewReference descriptor) {
        // TODO Auto-generated method stub

    }

}
