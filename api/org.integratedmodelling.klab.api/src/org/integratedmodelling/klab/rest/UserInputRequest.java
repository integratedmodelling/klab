package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IComputableResource.InteractiveParameter;

public class UserInputRequest {

	private String requestId;
	private String sectionTitle;
	private String description;

	/*
	 * we have either this....
	 */
	private List<InteractiveParameter> fields = new ArrayList<>();

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

	public List<InteractiveParameter> getFields() {
		return fields;
	}

	public void setFields(List<InteractiveParameter> fields) {
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
