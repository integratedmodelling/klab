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
import java.util.Set;

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
import org.integratedmodelling.klab.exceptions.KlabUnsupportedFeatureException;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Triple;

import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

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

    private void importCollection(List<Builder> ret, IParameters<String> parameters, IProject project, IMonitor monitor)
            throws MalformedURLException {
        String catalogUrl = parameters.get("catalogUrl", String.class);
        String collectionId = parameters.get("collectionId", String.class);
        JSONObject collectionData = Unirest.get(catalogUrl + "/collections/" + collectionId)
                .asJson().getBody().getObject();

        // item_assets is a shortcut for obtaining information about the assets
        // https://github.com/stac-extensions/item-assets
        JSONObject assets = null;
        if (collectionData.has("item_assets")) {
            STACCollectionParser.readItemAssets(collectionData);
        } else {
            JSONObject itemsData = Unirest.get(catalogUrl + "/collections/" + collectionId + "/items")
                    .asJson().getBody().getObject();
            STACCollectionParser.readAssets(itemsData);
        }

        String regex = null;
        if (parameters.contains("regex")) {
            regex = parameters.get(Resources.REGEX_ENTRY, String.class);
            parameters.remove(Resources.REGEX_ENTRY);
        }

        Set<String> assetIds = STACAssetMapParser.readAssetNames(assets);
        for(String assetId : assetIds) {
            if (regex != null && !assetId.matches(regex)) {
                Logging.INSTANCE.info("Asset " + assetId + " doesn't match REGEX, skipped");
                continue;
            }

            JSONObject assetData = STACAssetMapParser.getAsset(assets, assetId);
            if (!STACAssetParser.isSupportedMediaType(assetData)) {
                Logging.INSTANCE.info("Asset " + assetId + " doesn't have a supported media type, skipped");
                continue;
            }
            parameters.put("asset", assetId);
            String resourceUrn = collectionId + "-" + assetId;

            Builder builder = validator.validate(
                    Resources.INSTANCE.createLocalResourceUrn(resourceUrn, project), new URL(catalogUrl + "/collections/" + collectionId),
                    parameters, monitor);

            if (builder != null) {
                builder.withLocalName(resourceUrn).setResourceId(resourceUrn);
                ret.add(builder);
                monitor.info("STAC collection " + collectionId + " added");
            } else {
                monitor.warn("STAC collection " + collectionId + " is invalid");
            }
        }
    }

    @Override
    public Collection<Builder> importResources(String importLocation, IProject project, IParameters<String> userData,
            IMonitor monitor) {
        List<Builder> ret = new ArrayList<>();
        String[] locationElements = STACUtils.extractCatalogAndCollection(importLocation);

        if (locationElements.length != 2) {
            throw new KlabUnsupportedFeatureException("Bulk import from a catalog is not supported.");
        }
        try {
            monitor.info("Beginning STAC collection import from " + importLocation);

            Parameters<String> parameters = new Parameters<>();
            parameters.putAll(userData);
            parameters.put("catalogUrl", locationElements[0]);
            parameters.put("collectionId", locationElements[1]);

            importCollection(ret, parameters, project, monitor);
        } catch (Exception e) {
            monitor.error("STAC collection import failed: " + e.getMessage());
            throw new KlabIOException(e);
        }

        monitor.info("STAC: imported collection " + locationElements[1]);
        return ret;
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
