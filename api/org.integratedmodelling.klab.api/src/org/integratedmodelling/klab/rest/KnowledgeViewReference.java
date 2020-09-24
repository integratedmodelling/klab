package org.integratedmodelling.klab.rest;

public class KnowledgeViewReference {

	private String title;
	private String body;
	private String viewClass;
	private String exportUrl;
	private String contextId;
	private String viewId;
	private String label;

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

	public String getViewClass() {
		return viewClass;
	}

	public void setViewClass(String viewClass) {
		this.viewClass = viewClass;
	}

	public String getExportUrl() {
		return exportUrl;
	}

	public void setExportUrl(String exportUrl) {
		this.exportUrl = exportUrl;
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
	
}
