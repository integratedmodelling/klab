package org.integratedmodelling.authorities.wrb.model;

import java.util.ArrayList;
import java.util.List;

public class WrbIdentity {

	public static class SpecifiedQualifier {
		private Qualifier qualifier;
		private Specifier specifier = null;

		public Qualifier getQualifier() {
			return qualifier;
		}

		public void setQualifier(Qualifier qualifier) {
			this.qualifier = qualifier;
		}

		public Specifier getSpecifier() {
			return specifier;
		}

		public void setSpecifier(Specifier specifier) {
			this.specifier = specifier;
		}
	}

	private ReferenceSoilGroup soilGroup;
	private List<SpecifiedQualifier> principalQualifiers = new ArrayList<>();
	private List<SpecifiedQualifier> supplementaryQualifiers = new ArrayList<>();
	private String documentation;
	private String normalizedId;

	public ReferenceSoilGroup getSoilGroup() {
		return soilGroup;
	}

	public void setSoilGroup(ReferenceSoilGroup soilGroup) {
		this.soilGroup = soilGroup;
	}

	public List<SpecifiedQualifier> getPrincipalQualifiers() {
		return principalQualifiers;
	}

	public void setPrincipalQualifiers(List<SpecifiedQualifier> principalQualifiers) {
		this.principalQualifiers = principalQualifiers;
	}

	public List<SpecifiedQualifier> getSupplementaryQualifiers() {
		return supplementaryQualifiers;
	}

	public void setSupplementaryQualifiers(List<SpecifiedQualifier> supplementaryQualifiers) {
		this.supplementaryQualifiers = supplementaryQualifiers;
	}

	public String getDocumentation() {
		return documentation;
	}

	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}

	public String getNormalizedId() {
		return normalizedId;
	}

	public void setNormalizedId(String normalizedId) {
		this.normalizedId = normalizedId;
	}

}
