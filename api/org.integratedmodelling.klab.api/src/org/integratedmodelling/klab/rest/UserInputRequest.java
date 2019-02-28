package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.api.provenance.IArtifact;

public class UserInputRequest {

	public static class Field {

		private String description;
		private IArtifact.Type type;
		private String initialValue;
		private Set<String> values;
		// validation
		private List<Double> range;
		private int numericPrecision;
		private String regexp;
		// range, regexp & numeric precision

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public IArtifact.Type getType() {
			return type;
		}

		public void setType(IArtifact.Type type) {
			this.type = type;
		}

		public String getInitialValue() {
			return initialValue;
		}

		public void setInitialValue(String initialValue) {
			this.initialValue = initialValue;
		}

		public Set<String> getValues() {
			return values;
		}

		public void setValues(Set<String> values) {
			this.values = values;
		}

		public List<Double> getRange() {
			return range;
		}

		public void setRange(List<Double> range) {
			this.range = range;
		}

		public int getNumericPrecision() {
			return numericPrecision;
		}

		public void setNumericPrecision(int numericPrecision) {
			this.numericPrecision = numericPrecision;
		}

		public String getRegexp() {
			return regexp;
		}

		public void setRegexp(String regexp) {
			this.regexp = regexp;
		}
	}

	private String requestId;
	private String sectionTitle;
	private String description;

	/*
	 * we have either this....
	 */
	private List<Field> fields = new ArrayList<>();

	/*
	 * ...or the next two
	 */
	private String formId;
	private Map<String, String> formData;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public String getSectionTitle() {
		return sectionTitle;
	}

	public void setSectionTitle(String sectionTitle) {
		this.sectionTitle = sectionTitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public Map<String, String> getFormData() {
		return formData;
	}

	public void setFormData(Map<String, String> formData) {
		this.formData = formData;
	}

}
