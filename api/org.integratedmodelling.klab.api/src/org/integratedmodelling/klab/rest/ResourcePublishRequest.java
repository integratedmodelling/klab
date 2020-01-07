package org.integratedmodelling.klab.rest;

public class ResourcePublishRequest {

	private String urn;
	private NodeReference node;
	private String suggestedName;

	public String getUrn() {
		return urn;
	}

	public void setUrn(String urn) {
		this.urn = urn;
	}

	public NodeReference getNode() {
		return node;
	}

	public void setNode(NodeReference node) {
		this.node = node;
	}

	public String getSuggestedName() {
		return suggestedName;
	}

	public void setSuggestedName(String suggestedName) {
		this.suggestedName = suggestedName;
	}

	@Override
	public String toString() {
		return "ResourcePublishRequest [urn=" + urn + ", node=" + node + ", suggestedName=" + suggestedName + "]";
	}
	
}
