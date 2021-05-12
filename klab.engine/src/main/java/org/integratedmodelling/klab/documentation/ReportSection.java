package org.integratedmodelling.klab.documentation;

import java.util.Map;

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
    // StringBuffer body = new StringBuffer(512);
    Report report;
    String sectionRole;

    // used to hold the structure when pieces are added. Offsets locate the piece of interest in the
    // body. Anything outside of an element is a paragraph. Child sections also get added to this
    // list as well as the children list.
//    List<Element> elements = new ArrayList<>();
//
//    class Element {
//
//        // int startOffset;
//        // int endOffset;
//        Object element;
//        DocumentationNode.Type type;
//
//        // public void finalize() {
//        // this.endOffset = startOffset + body.length();
//        // }
//    }

    ReportSection(Report report, SectionRole role) {
        super(DocumentationNode.Type.Section);
        this.role = role;
        this.report = report;
    }

    ReportSection(ReportSection parent) {
        super(DocumentationNode.Type.Section);
        parent.children.add(this);
        this.report = parent.report;
    }

    @Override
    public String toString() {
        return "# " + getName()  /*+ ": (" + body.length() + ")"*/;
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

//    private Element addElement(Object object, DocumentationNode.Type type) {
//        Element ret = new Element();
//        ret.element = object;
//        ret.type = type;
////        ret.startOffset = this.body.length();
//        elements.add(ret);
//        return ret;
//    }

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
//        addElement(ret, DocumentationNode.Type.Section);
        return ret;
    }

    /*
     * --- API callable from Groovy code
     */
//    public void write(Object... objects) {
//        for (Object o : objects) {
//            appendContent(o == null ? "" : o.toString());
//        }
//    }
//
//    public void separator() {
//        appendContent("\n---\n");
//    }
//
//    public void paragraph(Object... objects) {
//        appendContent("\n---\n");
//        write(objects);
//        appendContent("\n---\n");
//    }

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
//        Element element = addElement(args[0], DocumentationNode.Type.Link);
        appendContent("{#user:" + args.getUnnamedArguments().get(0) + "}");
//        element.finalize();
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

            report.setReferenceType(args[1].toString(), RefType.TABLE);
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
            appendContent("[[#" + RefType.TABLE.name().toLowerCase() + ":" + args[1] + "] "
                    + (args.length > 2 ? (" " + args[2].toString()) : "") + "]");
            appendContent("{#" + RefType.TABLE.name().toLowerCase() + ":" + args[1] + " text-align: center}\n\n");

//            element.finalize();

        } else {

            for (IKnowledgeView view : ((IRuntimeScope) context).getViews()) {
                if (view.getIdentifier().equals(args[0])) {
                    /*
                     * insert table component
                     */
                    for (DocumentationNode vnode : report.getExistingViewNode(view.getIdentifier())) {
//                        Element element = addElement(vnode, DocumentationNode.Type.Figure);
                        if (args.length > 1) {
                            report.setReferenceType(args[1].toString(), RefType.TABLE);
                        }
//                        element.finalize();
                    }
                }
            }

        }
    }

    private String formatTableElement(Object item) {

        // TODO Auto-generated method stub
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
    public void cite(Object[] args, IDocumentation documentation, IContextualizationScope context, Scope scope) {

        // Element element = null;
        DocumentationNode node = null;
        if (!report.referencesCited.containsKey(args[0])) {
            Reference reference = ((Documentation) documentation).getReference(args[0].toString());
            if (reference != null) {
                // element = addElement(args[0], DocumentationNode.Type.Citation);
                // report.referencesCited.put(args[0].toString(), new ReportSection(this.report,
                // reference, args[0].toString()));
                // add to section in doc tree (this will split the section at the current place,
                // then resume if more text arrives
                node = report.addCitation(reference);
            }
        }

        if (node != null) {
            appendContent(Report.getLinkText(node));
        }

        // appendContent((args.length > 1 ? args[1] : "") + "[@" +
        // Report.RefType.REF.name().toLowerCase() + ":" + args[0] + "]");
        // if (element != null) {
        // element.finalize();
        // }
    }

    /**
     * Add a footnote to the document using flexmark's extension; assign and insert the tag.
     * 
     * @param processArguments
     * @param context
     * @param scope
     */
    public void footnote(Object[] processArguments, IDocumentation documentation, IContextualizationScope context, Scope scope) {
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
//                if (figure != null) {
////                    Element element = addElement(figure, DocumentationNode.Type.Figure);
//
////                    element.finalize();
//                }
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
    public void insert(Object[] processArguments, IDocumentation documentation, IContextualizationScope context, Scope scope) {
        if (processArguments.length > 0) {
            Item item = report.taggedText.get(processArguments[0].toString());
            if (item != null) {
//                Element element = addElement(item.getMarkdownContents(), DocumentationNode.Type.Paragraph);
                appendContent(item.getMarkdownContents());
                report.usedTags.add(processArguments[0].toString());
//                element.finalize();
            }
        }
    }

    @Override
    public String render(Map<String, Object> templateVariables) {
        return render(0, null, templateVariables);
    }

    public String render(int level, String numbering, Map<String, Object> templateVariables) {

        String ret = "";

        if (name != null) {
            ret += "\n" + StringUtil.repeat('#', level + 1) + (numbering == null ? " " : (" " + numbering + " ")) + name + "\n";
        }

//        ret += body.toString();

        int n = 0;
        for (ReportElement child : children) {
            String numb = null;
//            if (child.name != null && numbering != null) {
//                numb = numbering + "." + (++n);
//            }
//            ret += child.render(level + 1, numb, templateVariables);
        }

        return ret;
    }

    public Report getReport() {
        return report;
    }

    public void appendContent(String content) {
        // TODO add to current content if our last piece is a paragraph, otherwise create a new one
        // and add to it.
        System.out.println("HAOHAO");
    }

}
