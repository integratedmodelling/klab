package org.integratedmodelling.klab.rest;

public class ResourceAdapterReference {

	private String name;
	private String description;
	private String label;
	private ServicePrototype parameters;
	private boolean fileBased;

	public ServicePrototype getParameters() {
		return parameters;
	}

	public void setParameters(ServicePrototype parameters) {
		this.parameters = parameters;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isFileBased() {
		return fileBased;
	}

	public void setFileBased(boolean fileBased) {
		this.fileBased = fileBased;
	}
}
