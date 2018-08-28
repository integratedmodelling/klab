package org.integratedmodelling.klab.documentation;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.rest.AttributeReference;

public class ModelDocumentation {

	private String trigger;
	private String section;
	private String documentedId;
	private String extended;
	private String template;
	private Map<String, AttributeReference> variables = new HashMap<>();

	public String getExtended() {
		return extended;
	}

	public void setExtended(String extended) {
		this.extended = extended;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public Map<String, AttributeReference> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, AttributeReference> variables) {
		this.variables = variables;
	}

	public String getTrigger() {
		return trigger;
	}

	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getDocumentedId() {
		return documentedId;
	}

	public void setDocumentedId(String documentedId) {
		this.documentedId = documentedId;
	}

}
