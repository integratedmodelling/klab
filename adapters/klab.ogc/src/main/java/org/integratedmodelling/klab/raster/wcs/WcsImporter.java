package org.integratedmodelling.klab.raster.wcs;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceImporter;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.ogc.WcsAdapter;
import org.integratedmodelling.klab.raster.wcs.WCSService.WCSLayer;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Triple;

public class WcsImporter implements IResourceImporter {

	WcsValidator validator = new WcsValidator();

	@Override
	public IResourceImporter withOption(String option, Object value) {
		return this;
	}

	@Override
	public Collection<Builder> importResources(String importLocation, IProject project, IParameters<String> userData,
			IMonitor monitor) {
		List<Builder> ret = new ArrayList<>();
		// TODO parse from parameter string - for now just force it
		String wcsVersion = "2.0.1";
		String regex = null;
		if (userData.contains("regex")) {
			regex = (String) userData.get(Resources.REGEX_ENTRY);
			userData.remove(Resources.REGEX_ENTRY);
		}
		try {
			int index = importLocation.indexOf('?');
			importLocation = importLocation.substring(0, index);
			WCSService wcs = WcsAdapter.getService(importLocation, Version.create(wcsVersion));
			for (WCSLayer layer : wcs.getLayers()) {

				if (layer.isError()) {
					Logging.INSTANCE.warn("skipping corrupted WCS layer " + layer.getIdentifier());
					continue;
				}
				if (regex != null && !layer.getIdentifier().matches(regex)) {
					Logging.INSTANCE.info("layer " + layer.getIdentifier() + " doesn't match REGEX, skipped");
					continue;
				}
				try {

					Parameters<String> parameters = new Parameters<>();
					parameters.putAll(userData);
					parameters.put("serviceUrl", importLocation);
					parameters.put("wcsVersion", wcsVersion);
					parameters.put("wcsIdentifier", layer.getIdentifier());

					String layerId = layer.getIdentifier().toLowerCase().replaceAll("__", ".");

					Builder builder = validator.validate(Resources.INSTANCE.createLocalResourceUrn(layerId, project),
							new URL(importLocation), parameters, monitor);

					if (builder != null) {
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

	@Override
	public List<Triple<String, String, String>> getExportCapabilities(IObservation observation) {
		List<Triple<String, String, String>> ret = new ArrayList<>();
		return ret;
	}

	@Override
	public File exportObservation(File file, IObservation observation, ILocator locator, String format,
			IMonitor monitor) {
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

	@Override
	public boolean importIntoResource(URL importLocation, IResource target, IMonitor monitor) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean resourceCanHandle(IResource resource, String importLocation) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean acceptsMultiple() {
		// TODO Auto-generated method stub
		return false;
	}

}
