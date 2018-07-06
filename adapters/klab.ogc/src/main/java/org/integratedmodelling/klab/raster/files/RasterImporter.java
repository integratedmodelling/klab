package org.integratedmodelling.klab.raster.files;

import java.io.File;
import java.net.MalformedURLException;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.adapters.AbstractFilesetImporter;
import org.integratedmodelling.klab.ogc.RasterAdapter;

public class RasterImporter extends AbstractFilesetImporter {

	RasterValidator validator = new RasterValidator();
	
	public RasterImporter() {
		super(RasterAdapter.fileExtensions.toArray(new String[RasterAdapter.fileExtensions.size()]));
	}

	@Override
	protected Builder importFile(File file, IParameters<String> userData, IMonitor monitor) {
		try {
			return validator.validate(file.toURI().toURL(), userData, monitor);
		} catch (MalformedURLException e) {
			Logging.INSTANCE.error(e);
			return null;
		}
	}

}
