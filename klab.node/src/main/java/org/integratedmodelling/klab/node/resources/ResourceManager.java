package org.integratedmodelling.klab.node.resources;

import java.io.File;
import java.util.Set;

import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.adapters.IResourcePublisher;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.data.storage.ResourceCatalog;
import org.integratedmodelling.klab.exceptions.KlabUnsupportedFeatureException;
import org.integratedmodelling.klab.node.auth.EngineAuthorization;
import org.integratedmodelling.klab.rest.Group;
import org.integratedmodelling.klab.rest.ResourceReference;
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
	
    public IResource publishResource(ResourceReference resourceReference, File uploadFolder, EngineAuthorization user, IMonitor monitor) {

        IResourceAdapter adapter = Resources.INSTANCE.getResourceAdapter(resourceReference.getAdapterType());
        if (adapter == null) {
            throw new KlabUnsupportedFeatureException("resource adapter " + resourceReference.getAdapterType() + " not installed: cannot publish resource");
        }

        IResourcePublisher publisher = adapter.getPublisher();
        if (publisher == null) {
            throw new KlabUnsupportedFeatureException("resource publisher " + resourceReference.getAdapterType() + " not implemented: cannot publish resource");
        }

        IResource ret = publisher.publish(new Resource(resourceReference).in(uploadFolder), monitor);
        
        // TODO establish public URN
        
        if (ret != null && !ret.hasErrors()) {
            this.catalog.put(ret.getUrn(), ret);
        }
        
        return ret;
    }
    
}
