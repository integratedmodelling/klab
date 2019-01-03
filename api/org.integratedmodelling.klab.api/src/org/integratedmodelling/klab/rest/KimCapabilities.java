package org.integratedmodelling.klab.rest;

import java.util.LinkedHashSet;
import java.util.Set;

public class KimCapabilities {

	private String version;
	private String build;
	private Set<String> keywords = new LinkedHashSet<>();
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getBuild() {
		return build;
	}
	public void setBuild(String build) {
		this.build = build;
	}
	public Set<String> getKeywords() {
		return keywords;
	}
	public void setKeywords(Set<String> keywords) {
		this.keywords = keywords;
	}
	
	
}
