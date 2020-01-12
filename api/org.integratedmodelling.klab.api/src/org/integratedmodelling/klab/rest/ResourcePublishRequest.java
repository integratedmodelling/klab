package org.integratedmodelling.klab.rest;

public class ResourcePublishRequest {

	private String urn;
	private NodeReference node;
	private String suggestedName;
	private String suggestedNamespace;
	private String suggestedCatalog;

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

	public String getSuggestedCatalog() {
		return suggestedCatalog;
	}

	public void setSuggestedCatalog(String suggestedCatalog) {
		this.suggestedCatalog = suggestedCatalog;
	}

	public String getSuggestedNamespace() {
		return suggestedNamespace;
	}

	public void setSuggestedNamespace(String suggestedNamespace) {
		this.suggestedNamespace = suggestedNamespace;
	}
	
}
