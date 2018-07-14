package org.integratedmodelling.klab.node.resources;

import java.util.Set;

import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.data.storage.ResourceCatalog;
import org.integratedmodelling.klab.rest.Group;
import org.springframework.stereotype.Service;

@Service
public class ResourceManager {

	ResourceCatalog catalog;
	
	public ResourceManager() {
		this.catalog = new ResourceCatalog("publicresources");
	}
	
    public IResource getResource(String urn, Set<Group> groups) {
        // TODO Auto-generated method stub
        return null;
    }
	
}
