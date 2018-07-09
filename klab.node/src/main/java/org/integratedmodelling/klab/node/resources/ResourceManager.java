package org.integratedmodelling.klab.node.resources;

import org.integratedmodelling.klab.api.data.IResourceCatalog;
import org.integratedmodelling.klab.data.storage.ResourceCatalog;
import org.springframework.stereotype.Service;

@Service
public class ResourceManager {

	ResourceCatalog catalog;
	
	public ResourceManager() {
		this.catalog = new ResourceCatalog("publicresources");
	}
	
	public IResourceCatalog getCatalog() {
		return catalog;
	}
	
}
