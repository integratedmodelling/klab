package org.integratedmodelling.tables.adapter;

import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResourceCatalog;
import org.integratedmodelling.klab.api.data.adapters.IResourcePublisher;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;

public class TablePublisher implements IResourcePublisher {

    public TablePublisher() {
		// TODO Auto-generated constructor stub
	}

	@Override
    public IResource publish(IResource localResource, IResourceCatalog catalog, IMonitor monitor) throws KlabException {
        // TODO Auto-generated method stub
        return null;
    }

	@Override
	public boolean unpublish(IResource resource, IMonitor monitor) {
		// TODO Auto-generated method stub
		return false;
	}

}
