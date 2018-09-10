package org.integratedmodelling.klab.documentation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.api.documentation.IDocumentation;
import org.integratedmodelling.klab.rest.AttributeReference;

public class ModelDocumentation {

	private IDocumentation.Trigger trigger;
	private IDocumentation.Template.Section.Type sectionType;
	private String section;
	private String documentedId;
	private String extended;
	private String template;
	private Set<String> documentedUrns = new HashSet<>();

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

	public IDocumentation.Trigger getTrigger() {
		return trigger;
	}

	public void setTrigger(IDocumentation.Trigger trigger) {
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

	public Set<String> getDocumentedUrns() {
		return documentedUrns;
	}

	public void setDocumentedUrns(Set<String> documentedUrns) {
		this.documentedUrns = documentedUrns;
	}

	public IDocumentation.Template.Section.Type getSectionType() {
		return sectionType;
	}

	public void setSectionType(IDocumentation.Template.Section.Type sectionType) {
		this.sectionType = sectionType;
	}

}
