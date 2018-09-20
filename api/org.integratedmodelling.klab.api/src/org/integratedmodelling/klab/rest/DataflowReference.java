package org.integratedmodelling.klab.rest;

import org.integratedmodelling.klab.api.runtime.rest.IDataflowReference;

public class DataflowReference implements IDataflowReference {

	private String taskId;
	private String kdlCode;
	private String jsonElkLayout;

	public DataflowReference() {
	}

	public DataflowReference(String taskId, String kdlCode, String jsonElkLayout) {
		this.taskId = taskId;
		this.kdlCode = kdlCode;
		this.jsonElkLayout = jsonElkLayout;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.klab.rest.IDataflowReference#getTaskId()
	 */
	@Override
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.klab.rest.IDataflowReference#getKdlCode()
	 */
	@Override
	public String getKdlCode() {
		return kdlCode;
	}

	public void setKdlCode(String kdlCode) {
		this.kdlCode = kdlCode;
	}

	/**
	 * JSON specifications, directly parseable into the ElkGraphJson format. Easier
	 * this way than incorporating a JSON object directly due to the way the API is
	 * laid out.
	 * 
	 * @return
	 */
	public String getJsonElkLayout() {
		return jsonElkLayout;
	}

	public void setJsonElkLayout(String jsonElkLayout) {
		this.jsonElkLayout = jsonElkLayout;
	}

}
