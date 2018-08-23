package org.integratedmodelling.klab.rest;

import org.integratedmodelling.klab.api.runtime.rest.IDataflowReference;

public class DataflowReference implements IDataflowReference {
	
	private String taskId;
	private String kdlCode;
	
	public DataflowReference() {}
	
	public DataflowReference(String taskId, String kdlCode) {
		this.taskId = taskId;
		this.kdlCode = kdlCode;
	}

	/* (non-Javadoc)
	 * @see org.integratedmodelling.klab.rest.IDataflowReference#getTaskId()
	 */
	@Override
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	/* (non-Javadoc)
	 * @see org.integratedmodelling.klab.rest.IDataflowReference#getKdlCode()
	 */
	@Override
	public String getKdlCode() {
		return kdlCode;
	}
	public void setKdlCode(String kdlCode) {
		this.kdlCode = kdlCode;
	}

	
}
