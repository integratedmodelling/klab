package org.integratedmodelling.klab.documentation;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.rest.DocumentationNode;
import org.integratedmodelling.klab.rest.DocumentationNode.Figure;
import org.integratedmodelling.klab.rest.DocumentationNode.Table;
import org.integratedmodelling.klab.rest.DocumentationNode.Type;
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
    }

    public ReportElement(Type type, Object content) {
        this.type = type;
        this.node = encode(content);
        this.id = this.node.getId();
    }

    public ReportElement append(ReportElement element) {
        this.children.add(element);
        return element;
    }

    public DocumentationNode encode(Object element) {
        if (element instanceof DocumentationNode) {
            return (DocumentationNode) element;
        }
        DocumentationNode node = new DocumentationNode();

        if (element instanceof Figure) {
            node.setId(((Figure) element).getId());
            node.setFigure((Figure) element);
            notify(node);
        } else if (element instanceof Table) {
            node.setTable((Table) element);
            // nodes.put(node.getId(), node);
            notify(node);
        }

        return node;
    }

    public DocumentationNode.Type getType() {
        return type;
    }

    public String getId() {
        return id;
    }

}
