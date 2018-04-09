package org.integratedmodelling.klab.raster;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.data.adapters.IResourcePublisher;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.extensions.ResourceAdapter;
import org.integratedmodelling.klab.raster.encoder.RasterEncoder;
import org.integratedmodelling.klab.raster.publisher.RasterPublisher;
import org.integratedmodelling.klab.raster.validator.RasterValidator;

@ResourceAdapter(type = "raster", version = Version.CURRENT)
public class RasterAdapter implements IResourceAdapter {

	@Override
	public IResourceValidator getValidator() {
		return new RasterValidator();
	}

	@Override
	public IResourcePublisher getPublisher() {
		return new RasterPublisher();
	}

	@Override
	public IResourceEncoder getEncoder() {
		return new RasterEncoder();
	}

}
