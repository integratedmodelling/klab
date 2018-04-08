package org.integratedmodelling.klab.raster;

import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.data.adapters.IResourcePublisher;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.extensions.ResourceAdapter;

@ResourceAdapter(type = "wcs", version = Version.CURRENT)
public class WcsAdapter implements IResourceAdapter {

	@Override
	public IResourceValidator getValidator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IResourcePublisher getPublisher() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IResourceEncoder getEncoder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPrototype metadataPrototype() {
		// TODO Auto-generated method stub
		return null;
	}

}
