package org.integratedmodelling.klab.documentation;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.general.IStructuredTable;
import org.integratedmodelling.klab.api.documentation.IDocumentation;
import org.integratedmodelling.klab.api.documentation.IDocumentationProvider.Item;
import org.integratedmodelling.klab.api.documentation.IReport;
import org.integratedmodelling.klab.api.documentation.IReport.Section;
import org.integratedmodelling.klab.api.documentation.IReport.SectionRole;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.rest.IObservationReference;
import org.integratedmodelling.klab.data.classification.Classifier;
import org.integratedmodelling.klab.documentation.Documentation.SectionImpl;
import org.integratedmodelling.klab.documentation.Report.RefType;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.StringUtil;

public class ReportSection extends Parameters<String> implements Section {

    IReport.SectionRole role;
    String id = "rsec" + NameGenerator.shortUUID();
    String name = null;
    List<ReportSection> children = new ArrayList<>();
    StringBuffer body = new StringBuffer(512);
    Report report;
    String sectionRole;

    ReportSection(Report report, SectionRole role) {
        this.role = role;
        this.report = report;
    }

    ReportSection(ReportSection parent) {
        parent.children.add(this);
        this.report = parent.report;
    }

    public ReportSection(Report report, Reference reference, String tag) {
        this.report = report;
        this.body.append("[#" + Report.RefType.REF.name().toLowerCase() + ":" + tag + "]. ");
        this.body.append(reference.get(BibTexFields.EXAMPLE_CITATION));
        this.body.append(" {#" + Report.RefType.REF.name().toLowerCase() + ":" + tag + "}");
        this.body.append("\n\n");
        ReportSection parent = report.getMainSection(SectionRole.REFERENCES);
        parent.children.add(this);
    }

    public String getName() {
        return name == null ? StringUtil.capitalize(role.name()) : name;
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

        for (ReportSection child : children) {
            if (child.name.equals(string)) {
                return child;
            }
        }
        ReportSection ret = new ReportSection(this);
        ret.name = string;
        ret.sectionRole = role;
        report.docTree.add(ret, this);
        return ret;
    }

    /*
     * ---------------------------------------------------------------------------- API callable
     * from Groovy code
     */
    public void write(Object... objects) {
        for (Object o : objects) {
            body.append(o == null ? "" : o.toString());
        }
    }

    public void separator() {
        body.append("\n---\n");
    }

    public void paragraph(Object... objects) {
        body.append("\n---\n");
        write(objects);
        body.append("\n---\n");
    }

    /*
     * ---------------------------------------------------------------------------- API receiving
     * the report calls through template instructions
     */

    /**
     * Insert a descriptive span for the description of the passed argument, optionally with tag and
     * caption. For not do nothing and just use figure and text.
     * 
     * @param args tag
     * @param context
     */
    public void describe(Object[] args, IDocumentation documentation, IContextualizationScope context) {
        // TODO Auto-generated method stub
        // System.out.println("FOC");
    }

    /**
     * Create the {#reference:tag} attribute to refer back to the containing span.
     * 
     * @param args tag
     * @param context
     */
    public void tag(Object[] args, IDocumentation documentation, IContextualizationScope context) {
        body.append("{#user:" + args[0] + "}");
    }

    /**
     * Create a [@type:tag] link to the passed tag, resolving the type appropriately, optionally
     * around the passed text.
     * 
     * @param processArguments tag, text
     * @param context
     */
    public void link(Object[] args, IDocumentation documentation, IContextualizationScope context) {
        RefType type = report.getReferenceType(args[0].toString());
        if (type != null) {
            body.append("[@" + type.name().toLowerCase() + ":" + args[0] + "]");
        } else {
            body.append("[@user:" + args[0] + "]");
        }
    }

    /**
     * Produce a table from the first argument, which must identify a k.IM table in the context, and
     * format it with an optional tag and caption.
     * 
     * @param processArguments
     * @param context
     */
    public void table(Object[] args, IDocumentation documentation, IContextualizationScope context) {

        IStructuredTable<?> table = getTable(args[0].toString());
        if (table != null) {
            report.setReferenceType(args[1].toString(), RefType.TABLE);
            body.append("\n\n");
            if (!table.getColumnHeaders().get(0).startsWith("$")) {
                String separator = "";
                for (String h : table.getColumnHeaders()) {
                    body.append((separator.isEmpty() ? "" : "|") + h);
                    separator += ((separator.isEmpty() ? "" : "|") + ":---");
                }
                body.append("\n");
                body.append(separator + "\n");
            }
            for (int i = 0; i < table.getRowCount(); i++) {
                Object[] row = table.getRow(i);
                boolean first = true;
                for (Object item : row) {
                    body.append((first ? "" : "|") + formatTableElement(item));
                    first = false;
                }
                body.append("\n");
            }
            body.append("[[#" + RefType.TABLE.name().toLowerCase() + ":" + args[1] + "] "
                    + (args.length > 2 ? (" " + args[2].toString()) : "") + "]");
            body.append("{#" + RefType.TABLE.name().toLowerCase() + ":" + args[1] + " text-align: center}\n\n");
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
     */
    public void cite(Object[] args, IDocumentation documentation, IContextualizationScope context) {

        if (!report.referencesCited.containsKey(args[0])) {
            Reference reference = ((Documentation) documentation).getReference(args[0].toString());
            if (reference != null) {
                report.referencesCited.put(args[0].toString(), new ReportSection(this.report, reference, args[0].toString()));
            }
        }
        body.append((args.length > 1 ? args[1] : "") + "[@" + Report.RefType.REF.name().toLowerCase() + ":" + args[0] + "]");
    }

    /**
     * Add a footnote to the document using flexmark's extension; assign and insert the tag.
     * 
     * @param processArguments
     * @param context
     */
    public void footnote(Object[] processArguments, IDocumentation documentation, IContextualizationScope context) {
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
     */
    public void figure(Object[] args, IDocumentation documentation, IContextualizationScope context) {

        IArtifact artifact = "self".equals(args[0]) ? context.getTargetArtifact() : context.getArtifact(args[0].toString());
        if (artifact instanceof IObservation) {
            IObservationReference ref = report.getObservation(((IObservation) artifact).getId());
            if (ref != null) {
                // TODO check if we need an exception
                if (args.length > 1) {
                    report.setReferenceType(args[1].toString(), RefType.FIG);
                }
                // this solution work if k.EXPLORER is the hosting one
                // and doesn't work if it run in 'no engine' URL (as in front end development)
                body.append("\n\n![" + ref.getLabel() + "](/modeler/engine/session/view/displaydata/" + report.getSessionId()
                        + "/" + ref.getId() + "?format=RASTER&viewport=800)");
                body.append("\n[[#" + RefType.FIG.name().toLowerCase() + ":" + args[1] + "] "
                        + (args.length > 2 ? (" " + args[2].toString()) : "") + "]");
                body.append("{#" + RefType.FIG.name().toLowerCase() + ":" + args[1] + " text-align: center}\n\n");
            }
        }
    }

    /**
     * Insert the contents of a passed tag into the section at the current insertion point.
     * 
     * @param processArguments
     * @param context
     */
    public void insert(Object[] processArguments, IDocumentation documentation, IContextualizationScope context) {
        if (processArguments.length > 0) {
            Item item = report.taggedText.get(processArguments[0].toString());
            if (item != null) {
                body.append(item.getMarkdownContents());
                report.usedTags.add(processArguments[0].toString());
            }
        }
    }

    @Override
    public String render() {
        return render(0, null);
    }

    public String render(int level, String numbering) {

        String ret = "";

        if (name != null) {
            ret += "\n" + StringUtil.repeat('#', level + 1) + (numbering == null ? " " : (" " + numbering + " ")) + name + "\n";
        }

        ret += body.toString();

        int n = 0;
        for (ReportSection child : children) {
            String numb = null;
            if (child.name != null && numbering != null) {
                numb = numbering + "." + (++n);
            }
            ret += child.render(level + 1, numb);
        }

        return ret;
    }

    public Report getReport() {
        return report;
    }

}
