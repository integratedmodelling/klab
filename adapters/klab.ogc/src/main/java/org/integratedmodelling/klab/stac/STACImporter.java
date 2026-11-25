package org.integratedmodelling.klab.stac;

import java.io.File;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hortonmachine.gears.io.stac.HMStacAsset;
import org.hortonmachine.gears.io.stac.HMStacCollection;
import org.hortonmachine.gears.io.stac.HMStacItem;
import org.hortonmachine.gears.io.stac.HMStacManager;
import org.hortonmachine.gears.libs.monitor.DummyProgressMonitor;
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
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Triple;
import org.integratedmodelling.klab.utils.s3.S3URLUtils;

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

    private boolean hasAssetSelector(IParameters<String> parameters) {
        return (parameters.contains("asset") || (parameters.contains("jsonSelector") && parameters.contains("jsonValue")));
    }

    private void importCollection(List<Builder> ret, IParameters<String> parameters, IProject project, IMonitor monitor)
            throws Exception {
        importCollection(ret, parameters, project, monitor, null, null);
    }

    /**
     * @param manager the open manager of the catalog of the collection, if known, else null.
     * @param hmCollection the collection as found by the manager, if known, else null.
     */
    private void importCollection(List<Builder> ret, IParameters<String> parameters, IProject project, IMonitor monitor,
            HMStacManager manager, HMStacCollection hmCollection) throws Exception {
        String collectionUrl = parameters.get("collection", String.class);
        JSONObject collectionData = STACUtils.requestMetadata(collectionUrl, "collection");
        String collectionId = STACCollectionParser.readCollectionId(collectionData);
        parameters.put("collectionId", collectionId);

        boolean isBulkImport = parameters.contains("bulkImport");
        parameters.remove("bulkImport");
        if (!hasAssetSelector(parameters) && !isBulkImport) {
            Builder builder = buildResource(parameters, project, monitor, collectionId);
            if (builder != null) {
                ret.add(builder);
            } else {
                monitor.warn("STAC collection " + collectionId + " is invalid and cannot be imported");
            }
            return;
        }

        if (isBulkImport && !hasAssetSelector(parameters)) {
            // bulk import: one resource per asset of the collection
            importAssets(ret, parameters, collectionUrl, collectionData, collectionId, manager, hmCollection, project,
                    monitor);
            return;
        }

        String assetId = parameters.get("asset", String.class);
        String resourceUrn = collectionId;
        JSONObject assetData = null;
        /*
        This is only for the part, to check the Asset is under an S3 Bucket
         */
        if (assetId != null) {
            JSONObject assetNode = STACCollectionParser.readAssetInformationFromCollection(collectionUrl, collectionData, assetId);
            //assetData = assetNode.getJSONObject(assetId);
            assetData = (JSONObject) assetNode.toMap()
            	    .values()
            	    .iterator()
            	    .next();
            /*
            * If the particular asset Id wasn't found, then 
            * still proceed to create the resource since it can happen
            * for a number of reasons. Pagination, STAC Backend etc.
            * In that case however, a better handling of S3 URL needs to figured out
            */
            if (assetData != null) {
                if (!STACAssetParser.isSupportedMediaType(assetData)) {
                    throw new Exception("Unsupported media type for the asset");
                }
            }
            resourceUrn = collectionId + "-" + assetId;
        } 
        
        //TODO: Check if assetId is null, and instead a jsonSelector and Value is passed!
        //else if (parameters.get("jsonSelector", String.class) != null) {
        //    String selector = parameters.get("jsonSelector", String.class);
        //    String val = parameters.get("jsonValue", String.class);
        //	assetData = STACCollectionParser.readAssetInformationFromCollection(collectionUrl, collectionData, null, STACPathExpression.STACAssetPredicate.fromKongJsonObject(selector, val));
        //}
        
        // the s3 assets are read with the authentications of the user (see STACAccess), the region and
        // endpoint come from them or from the storage extension of the items

        Builder builder = buildResource(parameters, project, monitor, resourceUrn);
        if (builder != null) {
            ret.add(builder);
        } else {
            monitor.warn("STAC resource with asset " + resourceUrn + " is invalid and cannot be imported");
        }

        // Think of a way to do the REGEX matching. Possibly not worth it to support
        // this for something as diverse as STAC

        // String regex = null;
        if (parameters.contains("regex")) {
            // regex = parameters.get(Resources.REGEX_ENTRY, String.class);
            parameters.remove(Resources.REGEX_ENTRY);
        }

        /*
        Set<String> assetIds = STACAssetMapParser.readAssetNames(assets);
        for(String assetId : assetIds) {
            if (regex != null && !assetId.matches(regex)) {
                Logging.INSTANCE.info("Asset " + assetId + " doesn't match REGEX, skipped");
                continue;
            }
        
            JSONObject assetData = STACAssetMapParser.getAsset(assets, assetId);
            if (assetData != null) {
            	if (!STACAssetParser.isSupportedMediaType(assetData)) {
                    Logging.INSTANCE.info("Asset " + assetId + " doesn't have a supported media type, skipped");
                    continue;
                }
            }
           
            parameters.put("asset", assetId);
            String resourceUrn = collectionId + "-" + assetId;
        
            Builder builder = buildResource(parameters, project, monitor, resourceUrn);
            if (builder != null) {
                ret.add(builder);
            } else {
                monitor.warn("STAC resource with asset " + resourceUrn + " is invalid and cannot be imported");
            }
        }
        
        */
    }

    /**
     * Import each asset of a collection as a resource (collection + asset key, urn collectionId-assetId), the
     * encoder then reads the asset of that key from all the items of the collection.
     *
     * <p>The asset keys are those of the collection, for static catalogs that have them, else those of the first
     * item, which are assumed to be the same in all the items. Assets are filtered by the regex, if given, and by
     * the supported media types. The assets that fail are reported and skipped.</p>
     */
    private void importAssets(List<Builder> ret, IParameters<String> parameters, String collectionUrl,
            JSONObject collectionData, String collectionId, HMStacManager manager, HMStacCollection hmCollection,
            IProject project, IMonitor monitor) throws Exception {
        String regex = parameters.get(Resources.REGEX_ENTRY, String.class);
        parameters.remove(Resources.REGEX_ENTRY);
        if (regex != null && regex.isBlank()) {
            regex = null;
        }

        Map<String, JSONObject> assets;
        if (manager != null && hmCollection != null) {
            assets = readAssets(collectionData, manager, hmCollection);
        } else {
            String catalogUrl = STACUtils.getCatalogUrl(collectionUrl, collectionId, collectionData);
            try (HMStacManager catalogManager = new HMStacManager(catalogUrl, new DummyProgressMonitor())) {
                catalogManager.setAccess(STACAccess.get());
                catalogManager.open();
                assets = readAssets(collectionData, catalogManager, catalogManager.getCollectionByURL(collectionUrl));
            }
        }
        if (assets.isEmpty()) {
            monitor.warn("No assets found in STAC collection " + collectionId);
            return;
        }

        for (Map.Entry<String, JSONObject> entry : assets.entrySet()) {
            String assetId = entry.getKey();
            if (regex != null && !assetId.matches(regex)) {
                Logging.INSTANCE.info("Asset " + assetId + " doesn't match REGEX, skipped");
                continue;
            }
            boolean supported;
            try {
                supported = STACAssetParser.isSupportedMediaType(entry.getValue());
            } catch (Exception e) {
                supported = false;
            }
            if (!supported) {
                Logging.INSTANCE.info("Asset " + assetId + " doesn't have a supported media type, skipped");
                continue;
            }

            Parameters<String> assetParameters = new Parameters<>();
            assetParameters.putAll(parameters);
            assetParameters.put("asset", assetId);
            String resourceUrn = collectionId + "-" + assetId;
            try {
                Builder builder = buildResource(assetParameters, project, monitor, resourceUrn);
                if (builder != null) {
                    ret.add(builder);
                } else {
                    monitor.warn("STAC resource with asset " + resourceUrn + " is invalid and cannot be imported");
                }
            } catch (Exception e) {
                monitor.warn("STAC resource with asset " + resourceUrn + " cannot be imported: " + e.getMessage());
            }
        }
    }

    /**
     * @return the assets of the collection by key: those of the collection itself for static catalogs that have
     *         them, else those of its first item.
     */
    private static Map<String, JSONObject> readAssets(JSONObject collectionData, HMStacManager manager,
            HMStacCollection collection) throws Exception {
        Map<String, JSONObject> ret = new LinkedHashMap<>();
        if (!manager.isItemSearchAvailable()) {
            // static catalogs can have their assets on the collection
            JSONObject collectionAssets = collectionData.optJSONObject("assets");
            if (collectionAssets != null && collectionAssets.length() > 0) {
                for (String key : collectionAssets.keySet()) {
                    JSONObject asset = collectionAssets.optJSONObject(key);
                    if (asset != null) {
                        ret.put(key, asset);
                    }
                }
                return ret;
            }
        }
        // the assets of the first item, through the search API or the links of static catalogs
        List<HMStacItem> items = collection.searchItems(1);
        if (!items.isEmpty()) {
            for (HMStacAsset asset : items.get(0).getAllAssets()) {
                ret.put(asset.getId(), new JSONObject(asset.getAssetNode().toString()));
            }
        }
        return ret;
    }

    private Builder buildResource(IParameters<String> parameters, IProject project, IMonitor monitor, String resourceUrn)
            throws MalformedURLException {
        Builder builder = validator.validate(Resources.INSTANCE.createLocalResourceUrn(resourceUrn, project),
                new URL(parameters.get("collection", String.class)), parameters, monitor);

        if (builder == null) {
            return null;
        }
        builder.withLocalName(resourceUrn).setResourceId(resourceUrn);
        monitor.info("STAC collection " + resourceUrn + " added");
        return builder;
    }

    @Override
    public Collection<Builder> importResources(String collectionUrl, IProject project, IParameters<String> userData,
            IMonitor monitor) {
        List<Builder> ret = new ArrayList<>();
        String location = collectionUrl;
        try {
            monitor.info("Beginning STAC import from " + location);

            // s3:// addresses (static catalogs on buckets) are used through their https url
            String httpUrl = S3URLUtils.isS3Endpoint(location) ? STACAccess.toHttpUrl(location) : location;

            if (isCollectionDocument(httpUrl)) {
                importCollection(ret, bulkParameters(userData, httpUrl), project, monitor);
                monitor.info("STAC: imported collection " + httpUrl);
            } else {
                // a catalog (API landing page, static catalog or bucket folder holding a catalog.json):
                // the collections are found by the HortonMachine STAC client
                importCatalog(ret, location, userData, project, monitor);
            }
        } catch (Exception e) {
            monitor.error("STAC import failed: " + e.getMessage());
            throw new KlabIOException(e);
        }

        return ret;
    }

    /**
     * Import the assets of all the collections of a catalog, see
     * {@link #importAssets(List, IParameters, String, JSONObject, String, HMStacManager, HMStacCollection, IProject, IMonitor)}.
     * The collections that fail are reported and skipped.
     */
    private void importCatalog(List<Builder> ret, String catalogUrl, IParameters<String> userData, IProject project,
            IMonitor monitor) throws Exception {
        try (HMStacManager manager = new HMStacManager(catalogUrl, new DummyProgressMonitor())) {
            manager.setAccess(STACAccess.get());
            manager.open();
            List<HMStacCollection> collections = manager.getCollections();
            if (collections.isEmpty()) {
                throw new KlabIOException("No STAC collections found at " + catalogUrl);
            }
            monitor.info("STAC: found " + collections.size() + " collections in " + catalogUrl);
            int before = ret.size();
            for (HMStacCollection collection : collections) {
                String url = collection.getUrl();
                if (url == null) {
                    monitor.warn("STAC collection " + collection.getId()
                            + " skipped: the address of its document is not known");
                    continue;
                }
                try {
                    importCollection(ret, bulkParameters(userData, url), project, monitor, manager, collection);
                } catch (Exception e) {
                    monitor.warn("STAC collection " + collection.getId() + " at " + url + " skipped: "
                            + e.getMessage());
                }
            }
            monitor.info("STAC: imported " + (ret.size() - before) + " resources from " + collections.size()
                    + " collections of " + catalogUrl);
        }
    }

    private static Parameters<String> bulkParameters(IParameters<String> userData, String collectionUrl) {
        Parameters<String> parameters = new Parameters<>();
        if (userData != null) {
            parameters.putAll(userData);
        }
        parameters.put("collection", collectionUrl);
        parameters.put("bulkImport", true);
        return parameters;
    }

    /**
     * @return true if the address is the json document of a single STAC collection. Anything else (catalogs,
     *         bucket listings, unreachable addresses) is left to the catalog discovery, which reports its own
     *         errors.
     */
    private static boolean isCollectionDocument(String httpUrl) {
        try {
            JSONObject data = STACAccess.getJson(httpUrl);
            return data.has("type") && "collection".equalsIgnoreCase(data.getString("type"));
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean importIntoResource(URL importLocation, IResource target, IMonitor monitor) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean canHandle(String importLocation, IParameters<String> userData) {
        if (importLocation != null && S3URLUtils.isS3Endpoint(importLocation)) {
            // static catalogs on buckets
            return true;
        }
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
    public File exportObservation(File file, IObservation observation, ILocator locator, String format, IMonitor monitor) {
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
