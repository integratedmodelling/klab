package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.documentation.IReport;

/**
 * Sent after any new documentation node has been created, with a list of the views that are
 * affected as well as the type and ID of the affecting element.
 * 
 * @author Ferd
 *
 */
public class DocumentationEvent {

    private List<IReport.View> viewsAffected = new ArrayList<>();
    private DocumentationNode.Type nodeType;
    private String nodeId;

    public List<IReport.View> getViewsAffected() {
        return viewsAffected;
    }
    public void setViewsAffected(List<IReport.View> viewsAffected) {
        this.viewsAffected = viewsAffected;
    }
    public DocumentationNode.Type getNodeType() {
        return nodeType;
    }
    public void setNodeType(DocumentationNode.Type nodeType) {
        this.nodeType = nodeType;
    }
    public String getNodeId() {
        return nodeId;
    }
    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

}
