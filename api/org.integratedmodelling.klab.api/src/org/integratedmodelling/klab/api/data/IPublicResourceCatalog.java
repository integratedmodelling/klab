package org.integratedmodelling.klab.api.data;

import java.util.Collection;

import org.integratedmodelling.klab.api.auth.INodeIdentity;

public interface IPublicResourceCatalog {
	/**
	 * Return the node names that can handle the passed URN.
	 * 
	 * @param urn
	 * @return
	 */
	Collection<String> getNodes(String urn);

	void removeNode(INodeIdentity node);

	void updateNode(INodeIdentity node);

	IResource get(String urn);
}
