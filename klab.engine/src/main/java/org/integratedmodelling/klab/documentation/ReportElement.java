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
    protected List<ReportElement> children = new ArrayList<>();

    public ReportElement(Type type) {
        this.type = type;
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
        this.id = this.node.getId();
    }

    public ReportElement append(ReportElement element) {
        this.children.add(element);
        return element;
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
            report.notify(node);
        } else if (element instanceof Table) {
            node.setTable((Table) element);
            node.setType(Type.Table);
            // nodes.put(node.getId(), node);
            report.notify(node);
        } else {
            System.out.println("DIOCANE");
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
