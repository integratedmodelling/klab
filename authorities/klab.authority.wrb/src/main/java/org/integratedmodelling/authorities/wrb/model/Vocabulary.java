package org.integratedmodelling.authorities.wrb.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * An entire WRB vocabulary. Reading methods vary but it can be serialized as-is
 * for consistency.
 * 
 * @author Ferd
 *
 */
public class Vocabulary {

	private String name;
	private Map<String, ReferenceSoilGroup> groups = new HashMap<>();
	private Map<String, Qualifier> qualifiers = new HashMap<>();
	private Map<String, Specifier> specifiers = new HashMap<>();
	private Map<String, Set<String>> qualifierFamilies;

	// for the deserializer
	public Vocabulary() {
	}

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

	public Map<String, Set<String>> getQualifierFamilies() {
		if (this.qualifierFamilies == null) {
			this.qualifierFamilies = new LinkedHashMap<>();
			for (Qualifier q : this.qualifiers.values()) {
				Set<String> siblings = this.qualifierFamilies
						.get(q.getParentQualifier() == null ? q.getName() : q.getParentQualifier());
				if (siblings == null) {
					siblings = new HashSet<>();
					this.qualifierFamilies.put(q.getParentQualifier() == null ? q.getName() : q.getParentQualifier(),
							siblings);
				}
				siblings.add(q.getName());
			}
		}
		return this.qualifierFamilies;

	}

	public void setQualifierFamilies(Map<String, Set<String>> qualifierFamilies) {
		this.qualifierFamilies = qualifierFamilies;
	}

}
