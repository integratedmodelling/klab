package org.integratedmodelling.klab.ogc.vector.wfs;

import java.util.Collection;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceImporter;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

public class WfsImporter implements IResourceImporter {

	@Override
	public Collection<Builder> importResources(String importLocation, IParameters<String> userData, IMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canHandle(String importLocation, IParameters<String> userData) {
		// TODO Auto-generated method stub
		return false;
	}

}
