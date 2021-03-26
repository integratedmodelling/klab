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
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.rest.IObservationReference;
import org.integratedmodelling.klab.api.services.IModelService.IRankedModel;
import org.integratedmodelling.klab.dataflow.ObservedConcept;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.rest.DocumentationNode;
import org.integratedmodelling.klab.rest.DocumentationNode.Type;
import org.integratedmodelling.klab.rest.GraphReference;
import org.integratedmodelling.klab.utils.NameGenerator;
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

    private final static String ROOT_NODE = "__root__";

    // all items, which for now can be DocumentationNode, ReportSection, or Reference
    Map<String, Object> nodes = new HashMap<>();
    ISession session;
    IRuntimeScope context;
    Report report;
    // mutual dependencies by ID (key in nodes). Root node is ROOT_NODE.
    Graph<String, DefaultEdge> structure = new DefaultDirectedGraph<>(DefaultEdge.class);
    private String refSectionId;

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
        this.structure.addVertex(ROOT_NODE);
    }

    public DocumentationTree(Report report, IRuntimeScope context, ISession identity) {
        // TODO Auto-generated constructor stub
        this(report);
        this.context = context;
        this.session = identity;
    }

    public GraphReference<DocumentationNode> getView(View view) {
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

            item = getItem(Type.Resource);

        } else if (o instanceof IPrototype) {

        } else if (o instanceof ReportSection) {

            nodes.put(((ReportSection) o).getId(), o);
            structure.addVertex(((ReportSection) o).getId());
            structure.addEdge(((ReportSection) o).getId(), ROOT_NODE);

            // save the reference section to append refs to.
            if (((ReportSection) o).getRole() == SectionRole.REFERENCES) {
                this.refSectionId = ((ReportSection) o).getId();
            }

        } else if (o instanceof IObservationReference) {

        } else if (o instanceof IKimTable) {

        } else if (o instanceof IClassification) {

        } else {
            System.out.println("OHIBÓ un cianfero non visto prima");
        }

        if (item != null) {
            addNode(item);
        }
    }

    private DocumentationNode getItem(Type type) {
        DocumentationNode ret = new DocumentationNode();
        ret.setType(type);
        ret.setId(NameGenerator.shortUUID());
        return ret;
    }

    private DocumentationNode getItem(Type type, ReportSection parent) {
        DocumentationNode ret = getItem(type);
        ret.setRelativePosition(parent.body.length());
        return ret;
    }

    public void addModel(IModel model) {
        models.add(model);
    }

    public void addObservation(IObservation observation) {

    }

    /**
     * Add a child section
     */
    public void add(ReportSection section, ReportSection parent) {
        // TODO insert in tree; if figure
        System.out.println("SUBSECTION " + section);
        nodes.put(section.getId(), section);
        structure.addVertex(section.getId());
        structure.addEdge(section.getId(), parent.getId());
    }

    /**
     * Child figure (will split paragraphs)
     * 
     * @param reportSection
     * @param ref
     */
    public void addFigure(ReportSection reportSection, IObservationReference ref) {
        // TODO Auto-generated method stub
        System.out.println("FIGURE " + ref);
        DocumentationNode item = getItem(Type.Figure, reportSection);
        addNode(item);
        structure.addVertex(item.getId());
        structure.addEdge(item.getId(), reportSection.getId());
    }

    /**
     * Child table (split paragraph)
     * 
     * @param reportSection
     * @param table
     */
    public void addTable(ReportSection reportSection, IStructuredTable<?> table) {
        // TODO Auto-generated method stub
        System.out.println("TABLE " + table);
        DocumentationNode item = getItem(Type.Table, reportSection);
        addNode(item);
        structure.addVertex(item.getId());
        structure.addEdge(item.getId(), reportSection.getId());
    }

    /**
     * Child citation
     * 
     * @param reportSection
     * @param reference
     */
    public void addCitation(ReportSection reportSection, Reference reference) {
        // TODO Auto-generated method stub
        System.out.println("CITATION " + reference);
        nodes.put(reference.get("key"), reference);
        DocumentationNode item = getItem(Type.Citation, reportSection);
        addNode(item);
        structure.addVertex(item.getId());
        structure.addEdge(item.getId(), reportSection.getId());
        if (refSectionId != null) {
            structure.addVertex(reference.get("key"));
            structure.addEdge(reference.get("key"), refSectionId);
        } else {
            // shouldn't happen, but just in case, curse
            System.out.println("ZIOCAN NO REF SECTION");
        }
    }

    private GraphReference<DocumentationNode> getProvenanceView() {
        GraphReference<DocumentationNode> ret = new GraphReference<>();
        return ret;
    }

    private GraphReference<DocumentationNode> getTablesView() {
        GraphReference<DocumentationNode> ret = new GraphReference<>();
        return ret;
    }

    private GraphReference<DocumentationNode> getResourcesView() {
        GraphReference<DocumentationNode> ret = new GraphReference<>();
        return ret;
    }

    private GraphReference<DocumentationNode> getReportView() {
        Document document = new Document();
        for (SectionRole order : SectionRole.values()) {
            addReportSection(document, order);
        }
        return document.getGraph();
    }

    private void addReportSection(Document document, SectionRole role) {
        for (DefaultEdge edge : structure.incomingEdgesOf(ROOT_NODE)) {
            String id = structure.getEdgeSource(edge);
            if (nodes.get(id) instanceof ReportSection && (((ReportSection) nodes.get(id)).getRole() == role)) {
                makeSectionNode(document, (ReportSection) nodes.get(id), role);
            }
        }
    }

    private DocumentationNode makeSectionNode(Document document, ReportSection reportSection, SectionRole role) {

        DocumentationNode ret = new DocumentationNode();
        ret.setId(NameGenerator.shortUUID());
        ret.setTitle(reportSection.getName());
        List<DocumentationNode> children = new ArrayList<>();
        for (DefaultEdge edge : structure.incomingEdgesOf(reportSection.getId())) {
            Object child = nodes.get(structure.getEdgeSource(edge));
            if (child instanceof DocumentationNode) {
                children.add((DocumentationNode) child);
            }
        }

        document.add(role, reportSection, children);

        return ret;
    }

    private GraphReference<DocumentationNode> getModelsView() {
        GraphReference<DocumentationNode> ret = new GraphReference<>();
        return ret;
    }

    private GraphReference<DocumentationNode> getFiguresView() {
        GraphReference<DocumentationNode> ret = new GraphReference<>();
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

}
