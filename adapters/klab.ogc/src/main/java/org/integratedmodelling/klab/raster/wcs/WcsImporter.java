package org.integratedmodelling.klab.raster.wcs;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceImporter;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.ogc.WcsAdapter;
import org.integratedmodelling.klab.raster.wcs.WCSService.WCSLayer;
import org.integratedmodelling.klab.utils.Parameters;

public class WcsImporter implements IResourceImporter {

	WcsValidator validator = new WcsValidator();

	@Override
	public Collection<Builder> importResources(String importLocation, IParameters<String> userData, IMonitor monitor) {
		List<Builder> ret = new ArrayList<>();
		// TODO parse from parameter string - for now just force it
		String wcsVersion = "2.0.1";
		try {
			int index = importLocation.indexOf('?');
			importLocation = importLocation.substring(0, index);
			WCSService wcs = WcsAdapter.getService(importLocation, Version.create(wcsVersion));
			for (WCSLayer layer : wcs.getLayers()) {

				if (layer.isError()) {
					Logging.INSTANCE.warn("skipping corrupted WCS layer " + layer.getIdentifier());
					continue;
				}
				
				try {
					
					Parameters<String> parameters = new Parameters<>();
					parameters.putAll(userData);
					parameters.put("serviceUrl", importLocation);
					parameters.put("wcsVersion", wcsVersion);
					parameters.put("wcsIdentifier", layer.getIdentifier());
					Builder builder = validator.validate(new URL(importLocation), parameters, monitor);

					if (builder != null) {
						String layerId = layer.getIdentifier().toLowerCase().replaceAll("__", ".");
						builder.withLocalName(layerId).setResourceId(layerId);
						ret.add(builder);
					}

					Logging.INSTANCE.info(
							"importing WCS resource " + layer.getIdentifier() + " from service " + wcs.getServiceUrl());

				} catch (KlabResourceNotFoundException e) {

					Logging.INSTANCE.warn("skipping WCS resource " + layer.getIdentifier() + " from service "
							+ wcs.getServiceUrl() + ": " + e.getMessage());
				}
			}
		} catch (Exception e) {
			throw new KlabIOException(e);
		}
		return ret;
	}

	@Override
	public boolean canHandle(String importLocation, IParameters<String> userData) {

		URL url = null;
		try {
			url = new URL(importLocation);
		} catch (MalformedURLException e) {
			return false;
		}
		String lowurl = importLocation.toLowerCase();
		return url != null && url.getProtocol().startsWith("http") && lowurl.contains("?")
				&& lowurl.contains("service=wcs") && lowurl.contains("getcapabilities");
	}

}
