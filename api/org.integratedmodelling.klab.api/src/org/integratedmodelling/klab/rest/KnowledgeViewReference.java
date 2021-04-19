package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.documentation.IReport;
import org.integratedmodelling.klab.rest.ObservationReference.ExportFormat;

public class KnowledgeViewReference {

    private String title;
    private String body;
    private IReport.View viewClass;
    private String contextId;
    private String viewId;
    private String label;
    private List<ExportFormat> exportFormats = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getContextId() {
        return contextId;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    public String getViewId() {
        return this.viewId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<ExportFormat> getExportFormats() {
        return exportFormats;
    }

    public void setExportFormats(List<ExportFormat> exportFormats) {
        this.exportFormats = exportFormats;
    }

    public IReport.View getViewClass() {
        return viewClass;
    }

    public void setViewClass(IReport.View viewClass) {
        this.viewClass = viewClass;
    }

}
