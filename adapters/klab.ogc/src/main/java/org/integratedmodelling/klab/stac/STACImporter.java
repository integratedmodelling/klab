package org.integratedmodelling.klab.stac;

import java.io.File;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.hortonmachine.gears.io.stac.HMStacCollection;
import org.hortonmachine.gears.io.stac.HMStacManager;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceImporter;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.ogc.STACAdapter;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Triple;

public class STACImporter implements IResourceImporter {

	STACValidator validator = new STACValidator();

	@Override
	public IResourceImporter withOption(String option, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean acceptsMultiple() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<Builder> importResources(String importLocation, IProject project, IParameters<String> userData,
			IMonitor monitor) {
		List<Builder> ret = new ArrayList<>();

		monitor.info("Beginning STAC catalog import from " + importLocation);

		String regex = null;
		if (userData.contains("regex")) {
			regex = (String) userData.get(Resources.REGEX_ENTRY);
			userData.remove(Resources.REGEX_ENTRY);
		}
		int nc = 0;
		try {
			STACService service = STACAdapter.getService(importLocation);

			HMStacManager catalog = new HMStacManager(importLocation, null);
			catalog.open();

			List<HMStacCollection> collections = catalog.getCollections();
			for (HMStacCollection collection : collections) {
				// Check the regex
				if (regex != null && !collection.getId().matches(regex)) {
					Logging.INSTANCE.info("Collection " + collection.getId() + " doesn't match REGEX, skipped");
					continue;
				}

				try {
					Parameters<String> parameters = new Parameters<>();
					parameters.putAll(userData);
					parameters.put("catalogUrl", removeLastSlash(importLocation));

					String collectionId = collection.getId();
					parameters.put("collectionId", collectionId);

					/**
					 * TODO should explore assets (for 1+ items) and make a new builder per each COG
					 * asset (or GeoJSON), storing them as separate resources. Assets of different
					 * type should end up in resource as data keys, codelists, provenance or other
					 * info.
					 */

					Builder builder = validator.validate(
							Resources.INSTANCE.createLocalResourceUrn(collectionId, project), new URL(importLocation),
							parameters, monitor);

					if (builder != null) {
						builder.withLocalName(collectionId).setResourceId(collectionId);
						ret.add(builder);
						nc++;
						monitor.info("STAC collection " + collectionId + " added");
					} else {
						monitor.warn("STAC collection " + collectionId + " is invalid");
					}
				} catch (KlabResourceNotFoundException e) {
					monitor.warn("skipping STAC resource " + collection.getId() + " from service "
							+ service.getServiceUrl() + ": " + e.getMessage());
					continue;
				}
			}
			catalog.close();

		} catch (Exception e) {
			monitor.error("STAC catalog import failed: " + e.getMessage());
			throw new KlabIOException(e);
		}

		monitor.info("STAC: imported " + nc + " collections");

		return ret;
	}

	// Removing the last slash makes the URL easier to manage on later steps
	private String removeLastSlash(String importLocation) {
		return importLocation.endsWith("/") ? importLocation.substring(0, importLocation.length() - 1) : importLocation;
	}

	@Override
	public boolean importIntoResource(URL importLocation, IResource target, IMonitor monitor) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canHandle(String importLocation, IParameters<String> userData) {
		URL url = null;
		try {
			url = new URL(importLocation);
		} catch (MalformedURLException e) {
			return false;
		}
		// TODO make more checks to know if it is a proper STAC endpoint
		return url != null && url.getProtocol().startsWith("http");
	}

	@Override
	public boolean resourceCanHandle(IResource resource, String importLocation) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Triple<String, String, String>> getExportCapabilities(IObservation observation) {
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}

	@Override
	public File exportObservation(File file, IObservation observation, ILocator locator, String format,
			IMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getExportCapabilities(IResource resource) {
		return Collections.emptyMap();
	}

	@Override
	public boolean exportResource(File file, IResource resource, String format) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean write(Writer writer, IObservation observation, ILocator locator, IMonitor monitor) {
		// TODO Auto-generated method stub
		return false;
	}

}
