package org.integratedmodelling.klab.stac;

import java.io.IOException;
import java.io.OutputStream;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.data.FeatureSource;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.hortonmachine.gears.io.stac.HMStacAsset;
import org.hortonmachine.gears.io.stac.HMStacCollection;
import org.hortonmachine.gears.io.stac.HMStacItem;
import org.hortonmachine.gears.io.stac.HMStacManager;
import org.hortonmachine.gears.libs.modules.HMRaster;
import org.hortonmachine.gears.libs.monitor.LogProgressMonitor;
import org.hortonmachine.gears.utils.RegionMap;
import org.hortonmachine.gears.utils.geometry.GeometryUtilities;
import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension.Type;
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
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.components.time.extents.TimeInstant;
import org.integratedmodelling.klab.exceptions.KlabContextualizationException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.exceptions.KlabResourceAccessException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.ogc.STACAdapter;
import org.integratedmodelling.klab.ogc.vector.files.VectorEncoder;
import org.integratedmodelling.klab.raster.files.RasterEncoder;
import org.integratedmodelling.klab.rest.ExternalAuthenticationCredentials;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.stac.extensions.STACIIASAExtension;
import org.integratedmodelling.klab.utils.s3.S3URLUtils;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygon;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import kong.unirest.json.JSONObject;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

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
            throw new KlabContextualizationException("Current observation is outside the bounds of the STAC resource and cannot be reffitted.");
        }
        if (contextTime.getStart().isBefore(resourceTime.getStart())) {
            ITimeInstant newEnd = TimeInstant.create(resourceTime.getStart().getMilliseconds() + contextTime.getLength());
            return Time.create(resourceTime.getStart().getMilliseconds(), newEnd.getMilliseconds());
        }
        if (contextTime.getEnd().isAfter(resourceTime.getEnd())) {
            ITimeInstant newStart = TimeInstant.create(resourceTime.getEnd().getMilliseconds() - contextTime.getLength());
            return Time.create(newStart.getMilliseconds(), resourceTime.getEnd().getMilliseconds());
        }
        throw new KlabContextualizationException("Current observation is outside the bounds of the STAC resource and cannot be reffitted.");
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

    private void sortByDate(List<HMStacItem> items, IMonitor monitor) {
        if (items.stream().anyMatch(i -> i.getTimestamp() == null)) {
            throw new KlabIllegalStateException("STAC items are lacking a timestamp and could not be sorted by date.");
        }
        items.sort((i1, i2) -> i1.getTimestamp().compareTo(i2.getTimestamp()));
        monitor.debug(
                "Ordered STAC items. First: [" + items.get(0).getTimestamp() + "]; Last [" + items.get(items.size() - 1).getTimestamp() + "]");
    }

    private S3Client buildS3Client(String bucketRegion) throws IOException {
        ExternalAuthenticationCredentials awsCredentials = Authentication.INSTANCE.getCredentials(S3URLUtils.AWS_ENDPOINT);
        AwsCredentials credentials = null;
        try {
            credentials = AwsBasicCredentials.create(awsCredentials.getCredentials().get(0), awsCredentials.getCredentials().get(1));
        } catch (Exception e) {
            throw new KlabIOException("Error defining AWS credenetials. " + e.getMessage());
        }
        return S3Client.builder()
                .httpClientBuilder(ApacheHttpClient.builder())
                .region(Region.of(bucketRegion))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

    private boolean isDateInsideRange(Time rangeTime, Date date) {
        long rangeStart = rangeTime.getStart().getMilliseconds();
        long rangeEnd = rangeTime.getEnd().getMilliseconds();
        return (rangeStart <= date.getTime() && rangeEnd <= date.getTime());
    }

    @Override
    public void getEncodedData(IResource resource, Map<String, String> urnParameters, IGeometry geometry, Builder builder,
            IContextualizationScope scope) {
        String collectionUrl = resource.getParameters().get("collection", String.class);
        JSONObject collectionData = STACUtils.requestMetadata(collectionUrl, "collection");
        String collectionId = collectionData.getString("id");
        String catalogUrl = STACUtils.getCatalogUrl(collectionUrl, collectionId, collectionData);
        JSONObject catalogData = STACUtils.requestMetadata(catalogUrl, "catalog");
        String assetId = resource.getParameters().get("asset", String.class);

        boolean hasSearchOption = STACUtils.containsLinkTo(catalogData, "search");
        // This is part of a WIP that will be removed in the future
        boolean isIIASA = catalogUrl.contains("iiasa.blob");

        Space space = (Space) geometry.getDimensions().stream().filter(d -> d instanceof Space)
                .findFirst().orElseThrow();
        IEnvelope envelope = space.getEnvelope();
        List<Double> bbox =  List.of(envelope.getMinX(), envelope.getMaxX(), envelope.getMinY(), envelope.getMaxY());
        Time time = (Time) geometry.getDimensions().stream().filter(d -> d instanceof Time)
                .findFirst().orElseThrow();
        Time resourceTime = (Time) Scale.create(resource.getGeometry()).getDimension(Type.TIME);

        if (isIIASA) {
            FeatureSource<SimpleFeatureType, SimpleFeature> source;
            try {
                source = STACIIASAExtension.getFeatures(collectionData, bbox);
            } catch (IOException e) {
               throw new KlabResourceAccessException("Cannot extract features from IIASA catalog - " + e.getMessage());
            }
            encoder = new VectorEncoder();
            ((VectorEncoder)encoder).encodeFromFeatures(source, resource, urnParameters, geometry, builder, scope);
            return;
        }

        if (!hasSearchOption) {
            List<SimpleFeature> features = getFeaturesFromStaticCollection(collectionUrl, collectionData, collectionId);
            Time time2 = time; //TODO make the time and query time different
            features = features.stream().filter(f -> {
                Geometry fGeometry = (Geometry) f.getDefaultGeometry();
                return fGeometry.intersects(space.getShape().getJTSGeometry());
            }).toList();
            features = features.stream().filter(f -> isFeatureInTimeRange(time2, f)).toList();
            if (features.isEmpty()) {
                throw new KlabResourceNotFoundException("There are no items in this context for the collection " + collectionId);
            }
            List<HMStacAsset> assets = features.stream().map(f -> {
                try {
                    return HMStacItem.fromSimpleFeature(f).getAssetForBand(assetId);
                } catch (Exception e) {
                    throw new KlabIOException("Cannot get item from feature. Reason " + e.getMessage());
                }
            }).toList();
            HMStacAsset asset = assets.stream().filter(as -> as.getId().equals(assetId)).findFirst().get();
            HMRaster.fromGridCoverage(null);
            encoder = new RasterEncoder();
            GridCoverage2D coverage = null;
            // TODO get coverage from the raster
            ((RasterEncoder)encoder).encodeFromCoverage(resource, urnParameters, coverage, geometry, builder, scope);
        }

        LogProgressMonitor lpm = new LogProgressMonitor();
        HMStacManager manager = new HMStacManager(catalogUrl, lpm);
        HMStacCollection collection = null;
        try {
            manager.open();
            collection = manager.getCollectionById(resource.getParameters().get("collectionId", String.class));
        } catch (Exception e1) {
            throw new KlabResourceAccessException("Cannot access to STAC collection " + collectionUrl);
        }

        if (collection == null) {
            scope.getMonitor().error("Collection " + resource.getParameters().get("collection", String.class) + " cannot be found.");
        }

        IObservable targetSemantics = scope.getTargetArtifact() instanceof Observation
                ? ((Observation) scope.getTargetArtifact()).getObservable()
                : null;
        HMRaster.MergeMode mergeMode = chooseMergeMode(targetSemantics, scope.getMonitor());

        Envelope env = new Envelope(envelope.getMinX(), envelope.getMaxX(), envelope.getMinY(), envelope.getMaxY());
        Polygon poly = GeometryUtilities.createPolygonFromEnvelope(env);
        collection.setGeometryFilter(poly);

        if (resourceTime != null && resourceTime.getStart() != null && resourceTime.getEnd() != null && resourceTime.getCoveredExtent() > 0) {
            time = validateTemporalDimension(time, resourceTime);
        }
        ITimeInstant start = time.getStart();
        ITimeInstant end = time.getEnd();
        collection.setTimestampFilter(new Date(start.getMilliseconds()), new Date(end.getMilliseconds()));

        GridCoverage2D coverage = null;
        try {
            List<HMStacItem> items = collection.searchItems();

            if (items.isEmpty()) {
                manager.close();
                throw new KlabIllegalStateException("No STAC items found for this context.");
            }
            scope.getMonitor().debug("Found " + items.size() + " STAC items.");

            if (mergeMode == HMRaster.MergeMode.SUBSTITUTE) {
                sortByDate(items, scope.getMonitor());
            }

            IGrid grid = space.getGrid();

            RegionMap region = RegionMap.fromBoundsAndGrid(space.getEnvelope().getMinX(), space.getEnvelope().getMaxX(),
                    space.getEnvelope().getMinY(), space.getEnvelope().getMaxY(), (int) grid.getXCells(),
                    (int) grid.getYCells());

            ReferencedEnvelope regionEnvelope = new ReferencedEnvelope(region.toEnvelope(),
                    space.getProjection().getCoordinateReferenceSystem());
            RegionMap regionTransformed = RegionMap.fromEnvelopeAndGrid(regionEnvelope, (int) grid.getXCells(),
                    (int) grid.getYCells());
            Set<Integer> EPSGsAtItems = items.stream().map(i -> i.getEpsg()).collect(Collectors.toUnmodifiableSet());
            if (EPSGsAtItems.size() > 1) {
                scope.getMonitor().warn("Multiple EPSGs found on the items " + EPSGsAtItems.toString() + ". The transformation process could affect the data.");
            }

            if (resource.getParameters().contains("awsRegion")) {
                String bucketRegion = resource.getParameters().get("awsRegion", String.class);
                S3Client s3Client = buildS3Client(bucketRegion);
                collection.setS3Client(s3Client);
            }

            // Allow transform ensures the process to finish, but I would not bet on the resulting
            // data.
            final boolean allowTransform = true;
            HMRaster outRaster = collection.readRasterBandOnRegion(regionTransformed, assetId, items, allowTransform, mergeMode, lpm);
            coverage = outRaster.buildCoverage();
            manager.close();
        } catch (Exception e) {
            throw new KlabInternalErrorException("Cannot build STAC raster output. Reason " + e.getMessage());
        }
        encoder = new RasterEncoder();
        ((RasterEncoder)encoder).encodeFromCoverage(resource, urnParameters, coverage, geometry, builder, scope);
    }

    private boolean isFeatureInTimeRange(Time time2, SimpleFeature f) {
        Date datetime = (Date) f.getAttribute("datetime");
        if (datetime != null) {
            if (isDateInsideRange(time2, datetime)) {
                return true;
            }
        }

        Date itemStart = (Date) f.getAttribute("start_datetime");
        if (itemStart == null) {
            return false;
        }
        Date itemEnd = (Date) f.getAttribute("end_datetime");
        if (itemEnd == null) {
            return itemStart.toInstant().getEpochSecond() <= time2.getStart().getMilliseconds();
        }
        if (isDateInsideRange(time2, itemStart) || isDateInsideRange(time2, itemEnd)) {
            return false;
        }
        return true;
    }

    private List<SimpleFeature> getFeaturesFromStaticCollection(String collectionUrl, JSONObject collectionData, String collectionId) {
        List<JSONObject> links = collectionData.getJSONArray("links").toList().stream().filter(link -> ((JSONObject)link).getString("rel").equalsIgnoreCase("item")).toList();
        List<String> urlOfLinks = links.stream().map(link -> STACUtils.getUrlOfItem(collectionUrl, collectionId, link.getString("href"))).toList();
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
