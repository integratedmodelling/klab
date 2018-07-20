package org.integratedmodelling.klab.organizer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TypedContainer implements Serializable {

	private static final long serialVersionUID = -3548186902816100372L;

	private String name;
	private String description;
	private String id;
	private String urn;
	private Map<String,String> properties = new HashMap<>();
	
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrn() {
		return urn;
	}
	public void setUrn(String urn) {
		this.urn = urn;
	}
	public Map<String, String> getProperties() {
		return properties;
	}
	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}
	
	
}
