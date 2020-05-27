package org.integratedmodelling.klab.rest;

import org.integratedmodelling.klab.api.runtime.rest.ITaskReference;

public class TaskReference implements ITaskReference {

	private String id;
	private String parentId;
	private String contextId;
	private String rootContextId;
	private String urn;
	private String description;
	private String error;
	
	private Status status = Status.Started;

	public TaskReference() {
	}
	
//	public TaskReference(TaskReference other) {
//		this.id = other.id;
//		this.parentId = other.parentId;
//		this.urn = other.urn;
//		this.description = other.description;
//		this.error = other.error;
//		this.rootContextId = other.getRootContextId();
//	}
	
	// TODO provenance info - agent, cause etc
	/* (non-Javadoc)
	 * @see org.integratedmodelling.klab.rest.ITaskReference#getId()
	 */
	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see org.integratedmodelling.klab.rest.ITaskReference#getParentId()
	 */
	@Override
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/* (non-Javadoc)
	 * @see org.integratedmodelling.klab.rest.ITaskReference#getUrn()
	 */
	@Override
	public String getUrn() {
		return urn;
	}

	public void setUrn(String urn) {
		this.urn = urn;
	}

	/* (non-Javadoc)
	 * @see org.integratedmodelling.klab.rest.ITaskReference#getDescription()
	 */
	@Override
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/* (non-Javadoc)
	 * @see org.integratedmodelling.klab.rest.ITaskReference#getError()
	 */
	@Override
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

    @Override
    public String toString() {
        return "TaskReference [id=" + id + ", description=" + description + "]";
    }

    @Override
	public String getContextId() {
		return contextId;
	}

	public void setContextId(String contextId) {
		this.contextId = contextId;
	}

	public String getRootContextId() {
		return rootContextId;
	}

	public void setRootContextId(String rootContextId) {
		this.rootContextId = rootContextId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
