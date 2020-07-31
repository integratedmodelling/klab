package org.integratedmodelling.authorities.wrb.model;

import java.util.HashMap;
import java.util.Map;

/**
 * An entire WRB vocabulary. Reading methods vary but it can be serialized as-is for
 * consistency.
 * 
 * @author Ferd
 *
 */
public class Vocabulary {

	private String name;
	private Map<String, ReferenceSoilGroup> groups = new HashMap<>();
	private Map<String, Qualifier> qualifiers = new HashMap<>();
	private Map<String, Specifier> specifiers = new HashMap<>();

	// for the deserializer
	public Vocabulary() {}
	
	public Vocabulary(String name) {
		this.name = name;
	}

	public Map<String, ReferenceSoilGroup> getGroups() {
		return groups;
	}

	public Map<String, Qualifier> getQualifiers() {
		return qualifiers;
	}

	public Map<String, Specifier> getSpecifiers() {
		return specifiers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setGroups(Map<String, ReferenceSoilGroup> groups) {
		this.groups = groups;
	}

	public void setQualifiers(Map<String, Qualifier> qualifiers) {
		this.qualifiers = qualifiers;
	}

	public void setSpecifiers(Map<String, Specifier> specifiers) {
		this.specifiers = specifiers;
	}

}
