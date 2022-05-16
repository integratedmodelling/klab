package org.integratedmodelling.klab.documentation;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.rest.DocumentationNode;
import org.integratedmodelling.klab.rest.DocumentationNode.Figure;
import org.integratedmodelling.klab.rest.DocumentationNode.Table;
import org.integratedmodelling.klab.rest.DocumentationNode.Type;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.StringUtil;

/**
 * The contextualized incarnation of a directive into a part of the report tree. Turns directly into
 * a DocumentationNode.
 * 
 * @author Ferd
 *
 */
public class ReportElement {

    private DocumentationNode.Type type;
    private DocumentationNode node = null;
    private String id;
    private Report report;
    protected List<ReportElement> children = new ArrayList<>();

    public ReportElement(Type type, Report report) {
        this.type = type;
        this.report = report;
        if (type == Type.Paragraph) {
            this.node = new DocumentationNode();
            this.node.setType(Type.Paragraph);
            this.node.setId("p" + NameGenerator.shortUUID());
            this.node.setBodyText("");
        }
    }

    public ReportElement(Type type, Object content, Report report) {
        this.type = type;
        this.node = encode(content, report);
        this.report = report;
        this.id = this.node.getId();
    }

    public ReportElement append(ReportElement element) {
        if (element != null) {
            this.children.add(element);
        }
        return element;
    }

    public DocumentationNode render(String encoding) {

        DocumentationNode ret = this.node;

        switch(this.type) {
        case Paragraph:
            if ("html".equals(encoding)) {
                ret = new DocumentationNode();
                ret.setId(this.node.getId());
                ret.setType(Type.Paragraph);
                ret.setBodyText(report.md2html(this.node.getBodyText()));
            }
            break;
        case Section:
            ret = new DocumentationNode();
            ret.setId(this.getId());
            ret.setType(Type.Section);
            ret.setTitle(((ReportSection) this).getName() == null
                    ? (((ReportSection) this).getRole() == null
                            ? null
                            : StringUtil.capitalize(((ReportSection) this).getRole().name().toLowerCase()))
                    : ((ReportSection) this).getName());
            break;
        default:
            break;
        }

        for (ReportElement child : children) {
            ret.getChildren().add(child.render(encoding));
        }

        return ret;
    }

    private DocumentationNode encode(Object element, Report report) {

        if (element instanceof DocumentationNode) {
            return (DocumentationNode) element;
        }

        DocumentationNode node = new DocumentationNode();

        if (element instanceof Figure) {
            node.setId(((Figure) element).getId());
            node.setFigure((Figure) element);
            node.setType(Type.Figure);
            report.notify(report.checkForUpdate(node));
        } else if (element instanceof Table) {
            node.setTable((Table) element);
            node.setType(Type.Table);
            // nodes.put(node.getId(), node);
            report.notify(node);
        }
        
        return node;
    }

    public DocumentationNode.Type getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public DocumentationNode getNode() {
        return this.node;
    }

}
