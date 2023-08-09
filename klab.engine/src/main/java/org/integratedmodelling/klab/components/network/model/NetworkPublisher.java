package org.integratedmodelling.klab.components.network.model;

import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResourceCatalog;
import org.integratedmodelling.klab.api.data.adapters.IResourcePublisher;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

public class NetworkPublisher implements IResourcePublisher {

	@Override
	public IResource publish(IResource localResource, IResourceCatalog catalog, IMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean unpublish(IResource resource, IMonitor monitor) {
		// TODO Auto-generated method stub
		return false;
	}

}
