package org.integratedmodelling.authorities.wrb.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ReferenceSoilGroup {

	private String code;
	private String name;
	private String uri;
	private String description;
	private List<Set<String>> principalQualifiers = new ArrayList<>();
	private List<Set<String>> supplementaryQualifiers = new ArrayList<>();

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public List<Set<String>> getPrincipalQualifiers() {
		return principalQualifiers;
	}

	public void setPrincipalQualifiers(List<Set<String>> principalQualifiers) {
		this.principalQualifiers = principalQualifiers;
	}

	public List<Set<String>> getSupplementaryQualifiers() {
		return supplementaryQualifiers;
	}

	public void setSupplementaryQualifiers(List<Set<String>> supplementaryQualifiers) {
		this.supplementaryQualifiers = supplementaryQualifiers;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public String toString() {
		return "<RSG " + name + ">";
	}

}
