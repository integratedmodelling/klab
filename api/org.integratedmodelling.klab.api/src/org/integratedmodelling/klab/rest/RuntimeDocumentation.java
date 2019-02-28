package org.integratedmodelling.klab.rest;

import org.integratedmodelling.klab.api.documentation.IDocumentation;

public class RuntimeDocumentation {

	/**
	 * ID of context or actuator to which the content refers to.
	 */
	private String contextId;
	/**
	 * HTML content to display
	 */
	private String htmlContent;

	/**
	 * Trigger for documentation for additional info. Null in dataflow docs.
	 */
	private IDocumentation.Trigger trigger;

	public String getContextId() {
		return contextId;
	}

	public void setContextId(String contextId) {
		this.contextId = contextId;
	}

	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}

	public IDocumentation.Trigger getTrigger() {
		return trigger;
	}

	public void setTrigger(IDocumentation.Trigger trigger) {
		this.trigger = trigger;
	}

}
