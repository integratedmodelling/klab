package org.integratedmodelling.klab.rest;

import java.net.URL;

public class ResourceImportRequest {

	private URL importUrl;
	private String adapter;
	private String projectName;

	public ResourceImportRequest() {
	}

	public ResourceImportRequest(URL url) {
		this.importUrl = url;
	}
	
	public URL getImportUrl() {
		return importUrl;
	}

	public void setImportUrl(URL importUrl) {
		this.importUrl = importUrl;
	}

	public String getAdapter() {
		return adapter;
	}

	public void setAdapter(String adapter) {
		this.adapter = adapter;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

}
