package org.integratedmodelling.authorities.wrb.model;

public class Qualifier {

	private String name;
	private String code;
	private String description;
	private String parentQualifier = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParentQualifier() {
		return parentQualifier;
	}

	public void setParentQualifier(String parentQualifier) {
		this.parentQualifier = parentQualifier;
	}

}
