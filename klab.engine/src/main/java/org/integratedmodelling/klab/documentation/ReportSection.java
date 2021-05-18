package org.integratedmodelling.klab.documentation;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.general.IStructuredTable;
import org.integratedmodelling.klab.api.documentation.IDocumentation;
import org.integratedmodelling.klab.api.documentation.IDocumentationProvider.Item;
import org.integratedmodelling.klab.api.documentation.IReport;
import org.integratedmodelling.klab.api.documentation.IReport.Section;
import org.integratedmodelling.klab.api.documentation.IReport.SectionRole;
import org.integratedmodelling.klab.api.knowledge.ISemantic;
import org.integratedmodelling.klab.api.observations.IKnowledgeView;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.rest.IObservationReference;
import org.integratedmodelling.klab.data.classification.Classifier;
import org.integratedmodelling.klab.documentation.Documentation.Scope;
import org.integratedmodelling.klab.documentation.Report.RefType;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.rest.DocumentationNode;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.StringUtil;

public class ReportSection extends ReportElement implements Section {

    IReport.SectionRole role;
    String id = "rsec" + NameGenerator.shortUUID();
    String name = null;
    Report report;
    String sectionRole;
    private ReportSection parent;

    ReportSection(Report report, SectionRole role) {
        super(DocumentationNode.Type.Section, report);
        this.role = role;
        this.report = report;
    }

    ReportSection(ReportSection parent) {
        super(DocumentationNode.Type.Section, parent.report);
        parent.children.add(this);
        this.parent = parent;
        this.report = parent.report;
    }

    public ReportSection getMainSection() {
        ReportSection ret = this;
        while (ret.parent != null) {
            ret = ret.parent;
        }
        return ret;
    }
    
    @Override
    public String toString() {
        return "# " + getName();
    }

    public String getName() {
        return name == null ? (role == null ? "" : StringUtil.capitalize(role.name())) : name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public SectionRole getRole() {
        return role;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        ReportSection other = (ReportSection) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public ReportSection getChild(ReportSection parent, String titlePath, String role) {
        ReportSection ret = parent;
        while(titlePath.startsWith("/")) {
            titlePath = titlePath.substring(1);
        }
        String[] path = titlePath.split("\\/");
        for (int i = 0; i < path.length; i++) {
            ret = ret.getOrCreateChildNamed(path[i], role);
        }
        return ret;
    }

    private ReportSection getOrCreateChildNamed(String string, String role) {

        for (ReportElement child : children) {
            if (child instanceof ReportSection && ((ReportSection)child).name.equals(string)) {
                return (ReportSection)child;
            }
        }
        ReportSection ret = new ReportSection(this);
        ret.name = string;
        ret.sectionRole = role;
        return ret;
    }

    /*
     * --- API receiving the report calls through template instructions
     */

    /**
     * Insert a descriptive span for the description of the passed argument, optionally with tag and
     * caption. For now do nothing and just use figure and text.
     * 
     * @param args tag
     * @param context
     * @param scope
     */
    public void describe(Object[] args, IDocumentation documentation, IContextualizationScope context, Scope scope) {
        // TODO Auto-generated method stub
        // System.out.println("FOC");
    }

    /**
     * Create the {#reference:tag} attribute to refer back to the containing span.
     * 
     * @param args tag
     * @param context
     * @param scope
     */
    public void tag(IParameters<String> args, IDocumentation documentation, IContextualizationScope context, Scope scope) {
        appendContent("{#user:" + args.getUnnamedArguments().get(0) + "}");
    }

    /**
     * Create a [@type:tag] link to the passed tag, resolving the type appropriately, optionally
     * around the passed text.
     * 
     * @param processArguments tag, text
     * @param context
     * @param scope
     */
    public void link(IParameters<String> args, IDocumentation documentation, IContextualizationScope context, Scope scope) {
        
        String ref = args.getUnnamedArguments().get(0).toString();
        String txt = args.getUnnamedArguments().size() > 1 ? args.getUnnamedArguments().get(1).toString() : null;
        
        if (ref.startsWith("http")) {
            appendContent((txt != null ? ("[" + txt + "](") : "") + ref + (txt != null ? ")" : ""));
        } else {

            ReportElement linked = scope.references.get(ref);
            if (linked != null) {
                appendContent(Report.ANCHOR_PATTERN.replace("{type}", linked.getType().name()).replace("{id}", linked.getId()));
            }
        }
    }

    /**
     * Produce a table from the first argument, which must identify a k.IM table in the context, and
     * format it with an optional tag and caption.
     * 
     * @param processArguments
     * @param context
     * @param scope
     */
    public void table(IParameters<String> args, IDocumentation documentation, IContextualizationScope context, Scope scope) {

        IStructuredTable<?> table = getTable(args.getUnnamedArguments().get(0).toString());

        if (table != null) {

            report.setReferenceType(args.getUnnamedArguments().get(1).toString(), RefType.TABLE);
            appendContent("\n\n");
            if (!table.getColumnHeaders().get(0).startsWith("$")) {
                String separator = "";
                for (String h : table.getColumnHeaders()) {
                    appendContent((separator.isEmpty() ? "" : "|") + h);
                    separator += ((separator.isEmpty() ? "" : "|") + ":---");
                }
                appendContent("\n");
                appendContent(separator + "\n");
            }
            for (int i = 0; i < table.getRowCount(); i++) {
                Object[] row = table.getRow(i);
                boolean first = true;
                for (Object item : row) {
                    appendContent((first ? "" : "|") + formatTableElement(item));
                    first = false;
                }
                appendContent("\n");
            }
            appendContent("[[#" + RefType.TABLE.name().toLowerCase() + ":" + args.getUnnamedArguments().get(1) + "] "
                    + (args.getUnnamedArguments().size() > 2 ? (" " + args.getUnnamedArguments().get(2).toString()) : "") + "]");
            appendContent("{#" + RefType.TABLE.name().toLowerCase() + ":" + args.getUnnamedArguments().get(1) + " text-align: center}\n\n");

        } else {

            for (IKnowledgeView view : ((IRuntimeScope) context).getViews()) {
                if (view.getIdentifier().equals(args.getUnnamedArguments().get(0))) {
                    /*
                     * insert table component
                     */
                    for (DocumentationNode vnode : report.getExistingViewNode(view.getIdentifier())) {
                        ReportElement element = append(new ReportElement(DocumentationNode.Type.Table, vnode, report));
                        if (args.getUnnamedArguments().size() > 1) {
                            scope.link(args.getUnnamedArguments().get(1).toString(), element);
                            report.setReferenceType(scope.disambiguateId(args.getUnnamedArguments().get(1).toString()), RefType.TABLE);
                        }
                    }
                }
            }

        }
    }

    private String formatTableElement(Object item) {

        if (item instanceof Classifier) {
            Classifier i = (Classifier) item;
            if (i.isUniversal()) {
                return "\\*";
            }
            return i.getSourceCode();
        }
        return item.toString();

    }

    private IStructuredTable<?> getTable(String id) {
        Object object = Resources.INSTANCE.getSymbol(id);
        if (object instanceof IStructuredTable) {
            return (IStructuredTable<?>) object;
        }
        return null;
    }

    /**
     * Produce a citation for the passed bibliographic tag and insert the correspondent section in
     * the bibliography.
     * 
     * @param processArguments
     * @param context
     * @param scope
     */
    public void cite(IParameters<String> args, IDocumentation documentation, IContextualizationScope context, Scope scope) {

        DocumentationNode node = null;
        if (!report.referencesCited.containsKey(args.getUnnamedArguments().get(0))) {
            Reference reference = ((Documentation) documentation).getReference(args.getUnnamedArguments().get(0).toString());
            if (reference != null) {
                node = report.addCitation(reference);
            }
        }

        if (node != null) {
            appendContent(Report.getLinkText(node));
        }

    }

    /**
     * Add a footnote to the document using flexmark's extension; assign and insert the tag.
     * 
     * @param processArguments
     * @param context
     * @param scope
     */
    public void footnote(IParameters<String> args, IDocumentation documentation, IContextualizationScope context, Scope scope) {
        // TODO Auto-generated method stub
        // System.out.println("FOC");
    }

    /**
     * Produce a figure appropriate to the first argument: figure if raster map or shape, inline
     * code if model, etc.; format it with an optional tag and caption.
     * 
     * Format will be ![Caption](http://link){#fig:TAG}
     * 
     * @param processArguments
     * @param context
     * @param scope
     */
    public void figure(IParameters<String> args, IDocumentation documentation, IContextualizationScope context, Scope scope) {

        // TODO accommodate insertion of actual figure from doc space

        IArtifact artifact = null;
        if ("self".equals(args.getUnnamedArguments().get(0))) {
            artifact = context.getTargetArtifact();
        } else if (scope.variables.containsKey(args.getUnnamedArguments().get(0).toString())) {

            Object o = scope.variables.get(args.getUnnamedArguments().get(0).toString());
            if (o instanceof IObservation) {
                artifact = (IObservation) o;
            } else if (o instanceof ISemantic) {
                artifact = context.getArtifact(((ISemantic) o).getType(), IObservation.class);
            }

        } else {
            artifact = context.getArtifact(args.getUnnamedArguments().get(0).toString());
        }
        
        if (artifact instanceof IObservation) {

            IObservationReference ref = report.getObservation(((IObservation) artifact).getId());
            if (ref != null) {
                append(report.getFigureDescriptor(artifact, ref, scope, args));
            }
        }
    }
   
    

    /**
     * Insert the contents of a passed tag into the section at the current insertion point.
     * 
     * @param processArguments
     * @param context
     * @param scope
     */
    public void insert(IParameters<String> processArguments, IDocumentation documentation, IContextualizationScope context, Scope scope) {
        if (processArguments.getUnnamedArguments().size() > 0) {
            Item item = report.taggedText.get(processArguments.getUnnamedArguments().get(0).toString());
            if (item != null) {
                appendContent(item.getMarkdownContents());
                report.usedTags.add(processArguments.getUnnamedArguments().get(0).toString());
            }
        }
    }


    public Report getReport() {
        return report;
    }

    public void appendContent(String content) {
        
        ReportElement textElement = null;
        if (this.children.isEmpty() || this.children.get(this.children.size() - 1).getType() != DocumentationNode.Type.Paragraph) {
            textElement = new ReportElement(DocumentationNode.Type.Paragraph, report);
            this.children.add(textElement);
            report.notify(textElement.getNode());
        } else {
            textElement = this.children.get(this.children.size() -1);
        }
        textElement.getNode().setBodyText(textElement.getNode().getBodyText() + content);
        
    }



}
