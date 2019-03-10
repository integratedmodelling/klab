package org.integratedmodelling.klab.ogc.vector.wfs;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geotools.data.wfs.WFSDataStore;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceImporter;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.ogc.WfsAdapter;
import org.integratedmodelling.klab.utils.Parameters;

public class WfsImporter implements IResourceImporter {

	WfsValidator validator = new WfsValidator();

	@Override
	public Collection<Builder> importResources(String importLocation, IParameters<String> userData, IMonitor monitor) {

		List<Builder> ret = new ArrayList<>();

		// TODO parse from parameter string - for now just force it. USE 1.0.0 OR THE WFS
		// AXIS SWAP WILL TAKE OVER ANY EFFORT TO CIRCUMVENT IT.
		String wfsVersion = "1.0.0";
		try {
			int index = importLocation.indexOf('?');
			importLocation = importLocation.substring(0, index);
			WFSDataStore dataStore = WfsAdapter.getDatastore(importLocation, Version.create(wfsVersion));

			/*
			 * capabilities will come with EPSG:4326 lat/lon in all services except 1.0.0. But fixing the mess
			 * entails lots worse than the following line. For now just force 1.0.0 and screw it.
			 */
			// validator.swapLatlonAxes(!wfsVersion.equals("1.0.0"));

			for (String layer : dataStore.getTypeNames()) {

				try {

					Parameters<String> parameters = new Parameters<>();
					parameters.putAll(userData);
					parameters.put("serviceUrl", importLocation);
					parameters.put("wfsVersion", wfsVersion);
					parameters.put("wfsIdentifier", layer);

					Builder builder = validator.validate(new URL(importLocation), parameters, monitor);

					if (builder != null) {
						String layerId = layer.toLowerCase().replaceAll("__", ".").replaceAll("\\:", "_");
						builder.withLocalName(layerId).setResourceId(layerId);
						ret.add(builder.withParameters(parameters));
					}

					Logging.INSTANCE.info("importing WFS resource " + layer + " from service " + importLocation);

				} catch (KlabResourceNotFoundException e) {

					Logging.INSTANCE.warn(
							"skipping WFS resource " + layer + " from service " + wfsVersion + ": " + e.getMessage());
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
				&& lowurl.contains("service=wfs") && lowurl.contains("getcapabilities");
	}


    @Override
    public Map<String, String> getExportCapabilities(IObservation observation) {
        Map<String, String> ret = new HashMap<>();
        return ret;
    }

    @Override
    public File exportObservation(File file, IObservation observation, ILocator locator, String format) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, String> getExportCapabilities(IResource resource) {
        Map<String, String> ret = new HashMap<>();
        return ret;
    }

    @Override
    public boolean exportResource(File file, IResource resource, String format) {
        // TODO Auto-generated method stub
        return false;
    }

}
