package org.integratedmodelling.ml.adapters;

import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResourceCatalog;
import org.integratedmodelling.klab.api.data.adapters.IResourcePublisher;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;

public class WekaPublisher implements IResourcePublisher {

	@Override
	public IResource publish(IResource localResource, IResourceCatalog catalog, IMonitor monitor) throws KlabException {
		// no action needed
		return localResource;
	}

	@Override
	public boolean unpublish(IResource resource, IMonitor monitor) {
		// TODO Auto-generated method stub
		return false;
	}

}
