package org.integratedmodelling.klab.stac;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.geotools.api.data.FeatureSource;
import org.geotools.api.feature.simple.SimpleFeature;
import org.geotools.api.feature.simple.SimpleFeatureType;
import org.geotools.api.referencing.crs.CoordinateReferenceSystem;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.processing.Operations;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.referencing.CRS;
import org.hortonmachine.gears.io.stac.HMStacAsset;
import org.hortonmachine.gears.io.stac.HMStacCollection;
import org.hortonmachine.gears.io.stac.HMStacItem;
import org.hortonmachine.gears.io.stac.HMStacManager;
import org.hortonmachine.gears.libs.modules.HMRaster;
import org.hortonmachine.gears.libs.modules.HMRaster.HMRasterWritableBuilder;
import org.hortonmachine.gears.libs.modules.HMRaster.MergeMode;
import org.hortonmachine.gears.libs.monitor.LogProgressMonitor;
import org.hortonmachine.gears.utils.RegionMap;
import org.hortonmachine.gears.utils.crs.CrsUtilities;
import org.hortonmachine.gears.utils.geometry.GeometryUtilities;
import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension.Type;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.knowledge.ICodelist;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeInstant;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.components.time.extents.TimeInstant;
import org.integratedmodelling.klab.exceptions.KlabContextualizationException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.exceptions.KlabResourceAccessException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.ogc.STACAdapter;
import org.integratedmodelling.klab.ogc.vector.files.VectorEncoder;
import org.integratedmodelling.klab.raster.files.RasterEncoder;
import org.integratedmodelling.klab.rest.ExternalAuthenticationCredentials;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.stac.extensions.COGAssetExtension;
import org.integratedmodelling.klab.stac.extensions.STACFeatureExtension;
import org.integratedmodelling.klab.stac.extensions.STACIIASAExtension;
import org.integratedmodelling.klab.utils.s3.S3URLUtils;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygon;
import org.hortonmachine.gears.utils.crs.HMCrsRegistry;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.github.davidmoten.aws.lw.client.Client;
import com.github.davidmoten.aws.lw.client.Credentials;

import kong.unirest.json.JSONObject;

public class STACEncoder implements IResourceEncoder {

    /**
     * The raster or vector encoder that does the actual work after we get our coverage from the service.
     */
    IResourceEncoder encoder;

    @Override
    public boolean isOnline(IResource resource, IMonitor monitor) {
        String collectionUrl = resource.getParameters().get("collection", String.class);
        if (collectionUrl == null) {
            monitor.error("Resource is lacking a proper schema. Try to reimport the STAC collection.");
            return false;
        }

        STACService service = STACAdapter.getService(collectionUrl);
        if (service == null) {
            monitor.error("Connection with collection " + collectionUrl
                    + " cannot be opened: likely the service URL is wrong or offline");
            return false;
        }
        return true;
    }

    @Override
    public IResource contextualize(IResource resource, IScale scale, IArtifact targetObservation,
            Map<String, String> urnParameters, IContextualizationScope scope) {
        // TODO Auto-generated method stub
        return resource;
    }

    @Override
    public ICodelist categorize(IResource resource, String attribute, IMonitor monitor) {
        // TODO Auto-generated method stub
        return null;
    }

    private Time refitTime(Time contextTime, Time resourceTime) {
        if (resourceTime.getCoveredExtent() < contextTime.getCoveredExtent()) {
            throw new KlabContextualizationException(
                    "Current observation is outside the bounds of the STAC resource and cannot be reffitted.");
        }
        if (contextTime.getStart().isBefore(resourceTime.getStart())) {
            ITimeInstant newEnd = TimeInstant.create(resourceTime.getStart().getMilliseconds() + contextTime.getLength());
            return Time.create(resourceTime.getStart().getMilliseconds(), newEnd.getMilliseconds());
        }
        if (contextTime.getEnd().isAfter(resourceTime.getEnd())) {
            ITimeInstant newStart = TimeInstant.create(resourceTime.getEnd().getMilliseconds() - contextTime.getLength());
            return Time.create(newStart.getMilliseconds(), resourceTime.getEnd().getMilliseconds());
        }
        throw new KlabContextualizationException(
                "Current observation is outside the bounds of the STAC resource and cannot be reffitted.");
    }

    /**
     * Validates that the temporal dimension of the context can be supported by the resource.
     * Due to the nature of the STAC search query, time can be refitted if needed.
     * @param contextTime
     * @param resourceTime
     * @return validated time for the request
     */
    private Time validateTemporalDimension(Time contextTime, Time resourceTime) {
        if (!resourceTime.contains(contextTime)) {
            return refitTime(contextTime, resourceTime);
        }
        return contextTime;
    }

    private HMRaster.MergeMode chooseMergeMode(IObservable targetSemantics, IMonitor monitor) {
        if (targetSemantics == null) {
            monitor.debug("Using average as merge mode");
            return HMRaster.MergeMode.AVG;
        }
        switch(targetSemantics.getArtifactType()) {
        case CONCEPT:
        case BOOLEAN:
            monitor.debug("Using substitute as merge mode");
            return HMRaster.MergeMode.SUBSTITUTE;
        case NUMBER:
            if (Observables.INSTANCE.isExtensive(targetSemantics)) {
                monitor.debug("Using sum as merge mode");
                return HMRaster.MergeMode.SUM;
            }
            monitor.debug("Using substitute as merge mode");
            return HMRaster.MergeMode.SUBSTITUTE;
        default:
            monitor.debug("Defaulting to average as merge mode");
            return HMRaster.MergeMode.AVG;
        }
    }

    /*
        Helper to Sort Items (of type HMStacItem) based on their timestamp
     */
    private void sortByDate(List<HMStacItem> items, IMonitor monitor) {
        if (items.stream().anyMatch(i -> i.getTimestamp() == null)) {
            throw new KlabIllegalStateException("STAC items are lacking a timestamp and could not be sorted by date.");
        }
        items.sort((i1, i2) -> i1.getTimestamp().compareTo(i2.getTimestamp()));
        monitor.debug("Ordered STAC items. First: [" + items.get(0).getTimestamp() + "]; Last ["
                + items.get(items.size() - 1).getTimestamp() + "]");
    }

    private Client buildS3Client(String endpointURL) throws IOException {
        ExternalAuthenticationCredentials awsCredentials = Authentication.INSTANCE.getCredentials(endpointURL);
        Credentials credentials = null;
        String defaultS3Region = "us-east-1";
        try {
            credentials = Credentials.of(awsCredentials.getCredentials().get(0), awsCredentials.getCredentials().get(1));
        } catch (Exception e) {
            throw new KlabIOException("Error defining S3 credenetials. " + e.getMessage());
        }
        return  Client.s3()
                .region(defaultS3Region)
                .credentials(credentials)
                .baseUrlFactory((service, region) -> endpointURL)
                .build();
    }

    private boolean isDateWithinRange(Time rangeTime, Date date) {
        Date start = new Date(rangeTime.getStart().getMilliseconds());
        Date end = new Date(rangeTime.getEnd().getMilliseconds());
        return date.after(start) && date.before(end);
    }

    @Override
    public void getEncodedData(IResource resource, Map<String, String> urnParameters, IGeometry geometry, Builder builder,
            IContextualizationScope scope) {

        String COGURL = null;
        Space space = (Space) geometry.getDimensions().stream().filter(d -> d instanceof Space).findFirst().orElseThrow();
        IEnvelope envelope = space.getEnvelope();
        List<Double> bbox = List.of(envelope.getMinX(), envelope.getMaxX(), envelope.getMinY(), envelope.getMaxY());

        // Only for Backward Compatiability
        // A new COG Adapter would be added
        if (resource.getParameters().get("cog") != null) {
            COGURL = resource.getParameters().get("cog", String.class);
            scope.getMonitor().info("Getting requested extent from the COG Asset from url" + COGURL);
            GridCoverage2D coverage = COGAssetExtension.getCOGWindowCoverage(bbox, COGURL);

            String rcrs = geometry.getDimension(IGeometry.Dimension.Type.SPACE).getParameters()
                    .get(org.integratedmodelling.klab.common.Geometry.PARAMETER_SPACE_PROJECTION, String.class);

            Projection crs = Projection.create(rcrs);
            org.locationtech.jts.geom.Envelope requestedExtend = new org.locationtech.jts.geom.Envelope(bbox.get(0), bbox.get(1),
                    bbox.get(2), bbox.get(3));

            HMRaster raster = HMRaster.fromGridCoverage(coverage);
            HMRaster outRaster = new HMRasterWritableBuilder()
                    .setRegion(RegionMap.fromEnvelopeAndGrid(requestedExtend, (int) space.shape()[0], (int) space.shape()[1]))
                    .setCrs(crs.getCoordinateReferenceSystem()).setNoValue(raster.getNovalue()).build();

            GridCoverage2D adjCoverage = null;
            try {
                outRaster.mapRaster(null, raster, null);
                adjCoverage = outRaster.buildCoverage();
            } catch (Exception e) {
                throw new KlabResourceAccessException("Cannot build COG Output " + e.getMessage());
            }

            encoder = new RasterEncoder();
            ((RasterEncoder) encoder).encodeFromCoverage(resource, urnParameters, adjCoverage, geometry, builder, scope);
            return;
        }

        String collectionUrl = resource.getParameters().get("collection", String.class);
        JSONObject collectionData = STACUtils.requestMetadata(collectionUrl, "collection");
        String collectionId = collectionData.getString("id");
        String catalogUrl = STACUtils.getCatalogUrl(collectionUrl, collectionId, collectionData);
        JSONObject catalogData = STACUtils.requestMetadata(catalogUrl, "catalog");
        Integer bandIndex = resource.getParameters().get("band", Integer.class);
        String assetId = resource.getParameters().get("asset", String.class);
        boolean hasSearchOption = STACUtils.containsLinkTo(catalogData, "search");
        final boolean allowTransform = true;
        Time ctxTime = (Time) geometry.getDimensions().stream().filter(d -> d instanceof Time).findFirst().orElseThrow();
        Time resourceTime = (Time) Scale.create(resource.getGeometry()).getDimension(Type.TIME);

        Time effectiveTime = ctxTime;
        if (resourceTime != null && resourceTime.getStart() != null && resourceTime.getEnd() != null
                && resourceTime.getCoveredExtent() > 0) {

            effectiveTime = validateTemporalDimension(ctxTime, resourceTime);
        }

        // This is part of a WIP that will be removed in the future
        if (catalogUrl.contains("iiasa.blob")) {
            FeatureSource<SimpleFeatureType, SimpleFeature> source;
            try {
                source = STACIIASAExtension.getFeatures(collectionData, bbox);
            } catch (IOException e) {
                throw new KlabResourceAccessException("Cannot extract features from IIASA catalog - " + e.getMessage());
            }
            encoder = new VectorEncoder();
            ((VectorEncoder) encoder).encodeFromFeatures(source, resource, urnParameters, geometry, builder, scope);
            return;
        }

        /*
        Select the Predicate based on the assetId, JSONSelector Query, and the JSONValue
         */

        Predicate<HMStacAsset> assetPredicate = null;
        if (assetId != null) {
            assetPredicate = new Predicate<HMStacAsset>(){
                @Override
                public boolean test(HMStacAsset asset) { // Assuming for now that "eo:bands" would
                                                         // be there, adding support for customised
                                                         // predicates
                    var bands = asset.getAssetNode().get("eo:bands");
                    if (bands != null && bands.isArray()) {
                        var bandsArray = (ArrayNode) bands;
                        for(var bandNode : bandsArray) {
                            String bandName = bandNode.get("name").asText();
                            if (bandName.equals(assetId)) { // under eo:band it's one of the band
                                return true;
                            }
                        }
                    } else { // meaning eo:bands is not present like Microsoft Planetary, in this
                             // case this would be like the asset key i.e. Id
                        return asset.getId().equals(assetId);
                    }
                    return false;
                }
            };
        } else if (resource.getParameters().get("jsonSelector", String.class) != null) {
            // based on the JSON Expression on JSONSelector and JSONValue
            try {
                assetPredicate = getAssetPredicate(resource);
            } catch (Exception e) {
                throw new KlabResourceAccessException("Couldn't form a predicate with the JSON Expressions");
            }

        }

        // These are the static STAC catalogue
        if (!hasSearchOption) {
            List<SimpleFeature> features = getFeaturesFromStaticCollection(collectionUrl, collectionData, collectionId);
            features = features.stream().filter(f -> {
                Geometry fGeometry = (Geometry) f.getDefaultGeometry();
                return fGeometry.intersects(space.getShape().getJTSGeometry());
            }).toList();
            CoordinateReferenceSystem crs = features.get(0).getFeatureType().getCoordinateReferenceSystem();
            if (crs == null) {
                crs = CrsUtilities.getCrsFromSrid(4326); // We go to the standard
            }
            var time2 = effectiveTime;
            // TODO merge with similar code from below
            IGrid grid = space.getGrid();
            RegionMap region = RegionMap.fromBoundsAndGrid(space.getEnvelope().getMinX(), space.getEnvelope().getMaxX(),
                    space.getEnvelope().getMinY(), space.getEnvelope().getMaxY(), (int) grid.getXCells(), (int) grid.getYCells());

            ReferencedEnvelope regionEnvelope = new ReferencedEnvelope(region.toEnvelope(),
                    space.getProjection().getCoordinateReferenceSystem());
            RegionMap regionTransformed = RegionMap.fromEnvelopeAndGrid(regionEnvelope, (int) grid.getXCells(),
                    (int) grid.getYCells());
            // end //TODO
            List<HMStacItem> items = features.stream().map(f -> {
                try {
                    return HMStacItem.fromSimpleFeature(f);
                } catch (Exception e) {
                    scope.getMonitor().warn("Cannot parse feature " + f.getID() + ". Ignored.");
                    return null;
                }
            }).filter(Objects::nonNull)
                    .filter(item -> isWithinRange(item, time2.getStart().getMilliseconds(), time2.getEnd().getMilliseconds()))
                    .toList();

            GridCoverage2D coverage = null;

            try {
                // TODO see if we can access to the same readRasterBandOnRegion without using a
                // collection
                LogProgressMonitor lpm = new LogProgressMonitor();
                try (HMStacManager manager = new HMStacManager(catalogUrl, lpm)) {
                    HMStacCollection collection = null;
                    try {
                        manager.open();
                        collection = manager.getCollectionById(resource.getParameters().get("collectionId", String.class));
                    } catch (Exception e1) {
                        throw new KlabResourceAccessException("Cannot access to STAC collection " + collectionUrl);
                    }

                    if (collection == null) {
                        scope.getMonitor().error(
                                "Collection " + resource.getParameters().get("collection", String.class) + " cannot be found.");
                    }
                    Predicate<HMStacAsset> predicate;
                    try {
                        predicate = getAssetPredicate(resource);
                    } catch (KlabIllegalArgumentException e) {
                        manager.close();
                        throw e;
                    }
                    HMRaster outRaster = collection.readRasterBandOnRegion(regionTransformed, predicate, items, true,
                            MergeMode.SUBSTITUTE, lpm);
                    coverage = outRaster.buildCoverage();
                }
                CoordinateReferenceSystem targetCRS = HMCrsRegistry.INSTANCE.getCrs("4326");
                if (bandIndex != null) { // Which means theat it's a Multi Band COG
                    coverage = (GridCoverage2D) Operations.DEFAULT.selectSampleDimension(coverage, new int[]{bandIndex});
                }
                if (!CRS.equalsIgnoreMetadata(
                        coverage.getCoordinateReferenceSystem(),
                        targetCRS)) {

                    coverage = (GridCoverage2D) Operations.DEFAULT.resample(
                            coverage,
                            targetCRS);
                }
            } catch (Exception e) {
                throw new KlabResourceAccessException(
                        "Cannot build output for static collection " + collectionId + ". Reason: " + e.getLocalizedMessage());
            }
                
            encoder = new RasterEncoder();
            ((RasterEncoder) encoder).encodeFromCoverage(resource, urnParameters, coverage, geometry, builder, scope);
            return;
        }

        LogProgressMonitor lpm = new LogProgressMonitor();
        HMStacManager manager = new HMStacManager(catalogUrl, lpm);
        HMStacCollection collection = null;
        try {
            manager.open();
            collection = manager.getCollectionById(resource.getParameters().get("collectionId", String.class));

            if (collection == null) {
                scope.getMonitor()
                        .error("Collection " + resource.getParameters().get("collection", String.class) + " cannot be found.");
                manager.close();
                throw new KlabResourceAccessException("Cannot access to STAC collection " + collectionUrl); // Fail
                                                                                                            // fast
            }
            
            IObservable targetSemantics = scope.getTargetArtifact() instanceof Observation
                    ? ((Observation) scope.getTargetArtifact()).getObservable()
                    : null;
            HMRaster.MergeMode mergeMode = chooseMergeMode(targetSemantics, scope.getMonitor());
            Envelope env = new Envelope(envelope.getMinX(), envelope.getMaxX(), envelope.getMinY(), envelope.getMaxY());
            Polygon poly = GeometryUtilities.createPolygonFromEnvelope(env);
            collection.setGeometryFilter(poly);
            // collection.setTimestampFilter(new Date(start.getMilliseconds()), new
            // Date(end.getMilliseconds())); --> Filter later :)

            GridCoverage2D coverage = null;

            // Allow transform ensures the process to finish, but I would not bet on the resulting
            // data
            if (assetPredicate == null) {
                // NO JSONSelector and JSONValue found, NO assetID was passed as well
                scope.getMonitor().debug("Query STAC " + collectionUrl + "to get the features");
                // Only get the features from STAC Collection, no need to interact with Rasters
                FeatureSource<SimpleFeatureType, SimpleFeature> source;
                try {
                    source = STACFeatureExtension.getFeatures(catalogData, collectionId, bbox, effectiveTime.getStart(),
                            effectiveTime.getEnd());
                } catch (Exception e) {
                    manager.close();
                    throw new KlabResourceAccessException("Cannot extract features from STAC Collection - " + e.getMessage());
                }
                encoder = new VectorEncoder();
                ((VectorEncoder) encoder).encodeFromFeatures(source, resource, urnParameters, geometry, builder, scope);
                manager.close();
                return;
            }

            List<HMStacItem> items = collection.searchItems();
            if (items.isEmpty()) {
                manager.close();
                throw new KlabIllegalStateException("No STAC items found for this context.");
            }

            if (mergeMode == HMRaster.MergeMode.SUBSTITUTE) {
                sortByDate(items, scope.getMonitor());
            }

            IGrid grid = space.getGrid();

            RegionMap region = RegionMap.fromBoundsAndGrid(space.getEnvelope().getMinX(), space.getEnvelope().getMaxX(),
                    space.getEnvelope().getMinY(), space.getEnvelope().getMaxY(), (int) grid.getXCells(), (int) grid.getYCells());

            ReferencedEnvelope regionEnvelope = new ReferencedEnvelope(region.toEnvelope(),
                    space.getProjection().getCoordinateReferenceSystem());
            RegionMap regionTransformed = RegionMap.fromEnvelopeAndGrid(regionEnvelope, (int) grid.getXCells(),
                    (int) grid.getYCells());

            if (resource.getParameters().contains("s3EndpointUrl")) {
                String s3EndpointURL = resource.getParameters().get("s3EndpointUrl", String.class);
                Client s3Client = buildS3Client(s3EndpointURL);
                collection.setS3Client(s3Client);
            }
            var time = effectiveTime;
            // Filter here based on time, since in some STAC collections they don't yet support
            // temporal filtering :( like ECDC
            items = items.stream()
                    .filter(item -> isWithinRange(item, time.getStart().getMilliseconds(), time.getEnd().getMilliseconds()))
                    .collect(Collectors.toList());

            if (items.size() == 0) {
                manager.close();
                throw new KlabIllegalStateException(
                        "No STAC items found covering the entire time duration of the context requested");
            } else {
                scope.getMonitor().debug("Found " + items.size() + " STAC items satisfying the temporal constraint.");
            }

            // Once the support for customized predicate is added, we can apply for features as well

            var pred = assetPredicate;
            Set<Integer> EPSGAtAssets = items.stream()
                    .flatMap(item -> item.getAssets().stream().filter(pred).findFirst()
                            .map(asset -> asset.getEpsg() != null ? asset.getEpsg() : item.getEpsg()).stream())
                    .collect(Collectors.toUnmodifiableSet());

            if (EPSGAtAssets.size() > 1) {
                scope.getMonitor().warn("Multiple EPSGs found on the assets in items " + EPSGAtAssets.toString() + "."
                        + "The transformation process could affect the data.");
            }

            HMRaster outRaster = collection.readRasterBandOnRegion(regionTransformed, assetPredicate, items, allowTransform,
                    MergeMode.SUBSTITUTE, lpm);
            if (outRaster == null) {
                scope.getMonitor().error("No STAC assets were found. Please check the spatial/temporal coverage of the resource");
                throw new KlabIllegalStateException("No STAC assets were found. Please check the spatial/temporal coverage of the resource");
            }
            coverage = outRaster.buildCoverage();
            if (bandIndex != null) { // Which means theat it's a Multi Band COG
                coverage = (GridCoverage2D) Operations.DEFAULT.selectSampleDimension(coverage, new int[]{bandIndex});
            }
            CoordinateReferenceSystem targetCRS = HMCrsRegistry.INSTANCE.getCrs("4326");
            manager.close();
            encoder = new RasterEncoder();
            if (!CRS.equalsIgnoreMetadata(
                    coverage.getCoordinateReferenceSystem(),
                    targetCRS)) {
            	
            	System.out.println("Resampling! Found " +  coverage.getCoordinateReferenceSystem() 
            	+ " Resampling to " + targetCRS);

                coverage = (GridCoverage2D) Operations.DEFAULT.resample(
                        coverage,
                        targetCRS);
            }
            ((RasterEncoder) encoder).encodeFromCoverage(resource, urnParameters, coverage, geometry, builder, scope);
        } catch (Exception e) {
            e.printStackTrace();
            throw new KlabInternalErrorException("Cannot build STAC raster output. Reason " + e.getMessage());
        }
    }

    private Predicate<HMStacAsset> getAssetPredicate(IResource resource) {
        String assetId = resource.getParameters().get("asset", String.class);
        if (assetId != null) {
            return STACPathExpression.STACAssetPredicate.fromHMStacAssetId(assetId);
        }
        String jsonSelector = resource.getParameters().get("jsonSelector", String.class);
        String jsonValue = resource.getParameters().get("jsonValue", String.class);
        if (jsonSelector != null && !jsonSelector.isBlank() && jsonValue != null) {
            try {
                return STACPathExpression.STACAssetPredicate.fromHMStacAsset(jsonSelector, jsonValue);
            } catch (IllegalArgumentException e) {
                throw new KlabIllegalArgumentException("Invalid STAC asset JSON selector: " + jsonSelector);
            }
        } else {
            throw new KlabIllegalArgumentException("Either asset or both jsonSelector and jsonValue must be provided");
        }
    }

    /*
        To check if an Item (of type HMStacItem) is within a time range
     */
    private boolean isWithinRange(HMStacItem item, long startMillis, long endMillis) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startTimestamp = item.getStartTimestamp();
        String endTimestamp = item.getEndTimestamp();

        if (startTimestamp == null || endTimestamp == null) {
            return true; // Assume the time part is ok
        }

        try {

            long itemStart = LocalDateTime.parse(item.getStartTimestamp(), formatter).atZone(ZoneOffset.UTC).toInstant()
                    .toEpochMilli();

            long itemEnd = LocalDateTime.parse(item.getEndTimestamp(), formatter).atZone(ZoneOffset.UTC).toInstant()
                    .toEpochMilli();

            return startMillis >= itemStart && endMillis <= itemEnd;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private List<SimpleFeature> getFeaturesFromStaticCollection(String collectionUrl, JSONObject collectionData,
            String collectionId) {
        List<JSONObject> links = collectionData.getJSONArray("links").toList().stream()
                .filter(link -> ((JSONObject) link).getString("rel").equalsIgnoreCase("item")).toList();
        List<String> urlOfLinks = links.stream()
                .map(link -> STACUtils.getUrlOfItem(collectionUrl, collectionId, link.getString("href"))).toList();
        return urlOfLinks.stream().map(i -> {
            try {
                return STACUtils.getItemAsFeature(i);
            } catch (Exception e) {
                throw new KlabValidationException("Item at " + i + " cannot be parsed.");
            }
        }).toList();
    }

    @Override
    public void listDetail(IResource resource, OutputStream stream, boolean verbose, IMonitor monitor) {
        // TODO Auto-generated method stub
    }

}
