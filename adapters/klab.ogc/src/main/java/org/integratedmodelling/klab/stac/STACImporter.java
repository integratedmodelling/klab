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
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Triple;
import org.integratedmodelling.klab.utils.s3.S3RegionResolver;
import org.integratedmodelling.klab.utils.s3.S3URLUtils;

import kong.unirest.json.JSONObject;
import software.amazon.awssdk.regions.Region;

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
        String collectionUrl = parameters.get("collection", String.class);
        JSONObject collectionData = STACUtils.requestMetadata(collectionUrl, "collection");
        String collectionId = STACCollectionParser.readCollectionId(collectionData);
        parameters.put("collectionId", collectionId);

        String regex = null;
        if (parameters.contains("regex")) {
            regex = parameters.get(Resources.REGEX_ENTRY, String.class);
            parameters.remove(Resources.REGEX_ENTRY);
        }

        boolean isBulkImport = parameters.contains("bulkImport");
        parameters.remove("bulkImport");
        
        if (!parameters.contains("asset") && !isBulkImport) {
        	System.out.println("Couldn't find asset in Resource Params...");
            Builder builder = buildResource(parameters, project, monitor, collectionId);
            if (builder != null) {
                ret.add(builder);
            } else {
                monitor.warn("STAC collection " + collectionId + " is invalid and cannot be imported");
            }
            return;
        }
        
        JSONObject assets = STACCollectionParser.readAssetsFromCollection(collectionUrl, collectionData);
        Set<String> assetIds = STACAssetMapParser.readAssetNames(assets);
        
        System.out.println(assets);
        System.out.println(assetIds);
        
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
            String href = assetData.getString("href");
            if (S3URLUtils.isS3Endpoint(href)) {
                String[] bucketAndObject = href.split("://")[1].split("/", 2);
                Region s3Region = S3RegionResolver.resolveBucketRegion(bucketAndObject[0], bucketAndObject[1], monitor);
                parameters.put("awsRegion", s3Region.id());
            }

            Builder builder = buildResource(parameters, project, monitor, resourceUrn);
            if (builder != null) {
                ret.add(builder);
            } else {
                monitor.warn("STAC resource with asset " + resourceUrn + " is invalid and cannot be imported");
            }
        }
    }

    private Builder buildResource(IParameters<String> parameters, IProject project, IMonitor monitor, String resourceUrn) throws MalformedURLException {
        Builder builder = validator.validate(
                Resources.INSTANCE.createLocalResourceUrn(resourceUrn, project), new URL(parameters.get("collection", String.class)),
                parameters, monitor);

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
        try {
            monitor.info("Beginning STAC collection import from " + collectionUrl);

            Parameters<String> parameters = new Parameters<>();
            parameters.putAll(userData);
            parameters.put("collection", collectionUrl);
            parameters.put("bulkImport", true);

            importCollection(ret, parameters, project, monitor);
        } catch (Exception e) {
            monitor.error("STAC collection import failed: " + e.getMessage());
            throw new KlabIOException(e);
        }

        monitor.info("STAC: imported collection " + collectionUrl);
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
