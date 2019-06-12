package org.integratedmodelling.klab.rest;

/**
 * Sent B->F after a request for details on a dataflow element.
 * 
 * @author ferdinando.villa
 *
 */
public class DataflowDetail {

	private String dataflowId;
	private String htmlDescription;

	public String getDataflowId() {
		return dataflowId;
	}

	public void setDataflowId(String dataflowId) {
		this.dataflowId = dataflowId;
	}

	public String getHtmlDescription() {
		return htmlDescription;
	}

	public void setHtmlDescription(String htmlDescription) {
		this.htmlDescription = htmlDescription;
	}

}
