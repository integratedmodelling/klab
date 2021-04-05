package org.integratedmodelling.klab.ogc.fscan;

import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResourceCatalog;
import org.integratedmodelling.klab.api.data.adapters.IResourcePublisher;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.ogc.FSCANAdapter;
import org.integratedmodelling.klab.utils.Parameters;

public class FSCANPublisher implements IResourcePublisher {

	@Override
	public IResource publish(IResource localResource, IResourceCatalog catalog, IMonitor monitor) {
		return new FSCANValidator().performOperation(localResource, "index", Parameters.create(), catalog,
				Klab.INSTANCE.getRootMonitor());
	}

	@Override
	public boolean unpublish(IResource resource, IMonitor monitor) {
	    try {
	        FSCANAdapter.getPostgis().removeFeatures(new Urn(resource.getUrn()));
	    } catch (Throwable t) {
	        return false;
	    }
		return true;
	}

}
