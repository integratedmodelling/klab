package org.integratedmodelling.klab.documentation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IKimTable;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.classification.IClassification;
import org.integratedmodelling.klab.api.data.general.IStructuredTable;
import org.integratedmodelling.klab.api.documentation.IReport;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.rest.IObservationReference;
import org.integratedmodelling.klab.api.services.IModelService.IRankedModel;
import org.integratedmodelling.klab.dataflow.ObservedConcept;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.utils.Path;

/**
 * The structured version of the report, to substitute the simpler report based on a document view.
 * Uses the beans from the rest package directly. Each report holds a doctree with info on
 * everything that has gone through it. The tree is serializable to JSON. Eventually this may
 * substitute the Report object in its entirety.
 * 
 * @author Ferd
 *
 */
public class DocumentationTree {

    Map<String, DocumentationItem> items = new HashMap<>();
    ISession session;
    IRuntimeScope context;
    Report report;

    public DocumentationTree(Report report) {
        //
    }

    public DocumentationTree(Report report, IRuntimeScope context, ISession identity) {
        // TODO Auto-generated constructor stub
    }

    public void addResolution(ObservedConcept observable, List<IRankedModel> resolved) {

    }

    /**
     * Add a first-class object
     * 
     * @param o
     */
    public void add(Object o) {

        if (o instanceof IResource) {

        } else if (o instanceof IPrototype) {

        } else if (o instanceof ReportSection) {

        } else if (o instanceof IObservationReference) {

        } else if (o instanceof IKimTable) {

        } else if (o instanceof IClassification) {

        } else {
            System.out.println("OHIBÓ un cianfero non visto prima");
        }
    }

    /**
     * Add a child section
     */
    public void add(ReportSection section, ReportSection parent) {
        // TODO insert in tree; if figure 
        System.out.println("SUBSECTION " + section);
    }

    /**
     * Child figure (will split paragraphs)
     * @param reportSection
     * @param ref
     */
    public void addFigure(ReportSection reportSection, IObservationReference ref) {
        // TODO Auto-generated method stub
        System.out.println("FIGURE " + ref);
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
    }

    /**
     * Child citation
     * @param reportSection
     * @param reference
     */
    public void addCitation(ReportSection reportSection, Reference reference) {
        // TODO Auto-generated method stub
        System.out.println("CITATION " + reference);

    }

}
