package org.integratedmodelling.klab.documentation;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.documentation.IReport;
import org.integratedmodelling.klab.api.documentation.IReport.Section;
import org.integratedmodelling.klab.api.documentation.IReport.SectionRole;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.StringUtils;

public class ReportSection extends Parameters<String> implements Section {

    IReport.SectionRole role;
    String              id       = "rsec" + NameGenerator.shortUUID();
    String              name     = null;
    List<ReportSection> children = new ArrayList<>();
    StringBuffer        body     = new StringBuffer(512);
    Report              report;

    ReportSection(Report report, SectionRole role) {
        this.role = role;
        this.report = report;
    }

    ReportSection(ReportSection parent) {
        parent.children.add(this);
        this.report = parent.report;
    }

    public String getName() {
        return name == null ? StringUtils.capitalize(role.name()) : name;
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

    public ReportSection getChild(ReportSection parent, String titlePath) {
        ReportSection ret = parent;
        while (titlePath.startsWith("/")) {
            titlePath = titlePath.substring(1);
        }
        String[] path = titlePath.split("\\/");
        for (int i = 0; i < path.length; i++) {
            ret = ret.getOrCreateChildNamed(path[i]);
        }
        return ret;
    }

    private ReportSection getOrCreateChildNamed(String string) {

        for (ReportSection child : children) {
            if (child.name.equals(string)) {
                return child;
            }
        }
        ReportSection ret = new ReportSection(this);
        ret.name = string;
        return ret;
    }

    /*
     * ----------------------------------------------------------------------------
     * API callable from Groovy code
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
     * ----------------------------------------------------------------------------
     * API receiving the report calls through template instructions
     */

    /**
     * Insert a span for the description of the passed argument, optionally with
     * tag and caption.
     * 
     * @param args
     *            tag
     * @param context
     */
    public void describe(Object[] args, IComputationContext context) {
        // TODO Auto-generated method stub
        System.out.println("FOC");
    }
    
    /**
     * Create the {#reference:tag} attribute to refer back to the containing span.
     * 
     * @param args
     *            tag
     * @param context
     */
    public void tag(Object[] args, IComputationContext context) {
        // TODO Auto-generated method stub
        System.out.println("FOC");
    }

    /**
     * Create a [@type:tag] link to the passed tag, resolving the type
     * appropriately, optionally around the passed text.
     * 
     * @param processArguments
     *            tag, text
     * @param context
     */
    public void link(Object[] processArguments, IComputationContext context) {
        // TODO Auto-generated method stub
        System.out.println("FOC");
    }

    /**
     * Produce a table from the first argument, which must identify a k.IM table in
     * the context, and format it with an optional tag and caption.
     * 
     * @param processArguments
     * @param context
     */
    public void table(Object[] processArguments, IComputationContext context) {
        // TODO Auto-generated method stub
        System.out.println("FOC");
    }

    /**
     * Produce a citation for the passed bibliographic tag and insert the
     * correspondent section in the bibliography.
     * 
     * @param processArguments
     * @param context
     */
    public void cite(Object[] processArguments, IComputationContext context) {
        // TODO Auto-generated method stub
        System.out.println("FOC");
    }

    /**
     * Add a footnote to the document using flexmark's extension; assign and insert
     * the tag.
     * 
     * @param processArguments
     * @param context
     */
    public void footnote(Object[] processArguments, IComputationContext context) {
        // TODO Auto-generated method stub
        System.out.println("FOC");
    }

    /**
     * Produce a figure appropriate to the first argument: figure if raster map or
     * shape, inline code if model, etc.; format it with an optional tag and
     * caption.
     * 
     * @param processArguments
     * @param context
     */
    public void figure(Object[] processArguments, IComputationContext context) {
        // TODO Auto-generated method stub
        System.out.println("FOC");
    }

    /**
     * Insert the contents of a passed tag into the section at the current insertion
     * point.
     * 
     * @param processArguments
     * @param context
     */
    public void insert(Object[] processArguments, IComputationContext context) {
        // TODO Auto-generated method stub
        System.out.println("FOC");
    }

    @Override
    public String render() {
        return render(0);
    }

    private String render(int level) {

        String ret = "";

        if (name != null) {
            ret += "\n" + StringUtils.repeat('#', level + 1) + " " + name + "\n";
        }

        ret += body.toString();

        for (ReportSection child : children) {
            ret += child.render(level + 1);
        }

        return ret;
    }

    public Report getReport() {
        return report;
    }

}
