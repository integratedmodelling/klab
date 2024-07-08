package org.integratedmodelling.klab.stac;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.hortonmachine.gears.io.stac.HMStacCollection;
import org.hortonmachine.gears.io.stac.HMStacItem;
import org.hortonmachine.gears.libs.modules.HMRaster;
import org.hortonmachine.gears.libs.monitor.LogProgressMonitor;
import org.hortonmachine.gears.utils.RegionMap;
import org.hortonmachine.gears.utils.geometry.GeometryUtilities;
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
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.ogc.STACAdapter;
import org.integratedmodelling.klab.raster.files.RasterEncoder;
import org.integratedmodelling.klab.scale.Scale;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Polygon;

public class STACEncoder implements IResourceEncoder {

    /**
     * The raster encoder that does the actual work after we get our coverage from
     * the service.
     */
    RasterEncoder encoder = new RasterEncoder();

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
     * For generic resources, time can be refitted.
     * @param contextTime
     * @param resourceTime
     * @return validated time for the request
     */
    private Time validateTemporalDimension(Time contextTime, Time resourceTime) {
        if (!resourceTime.contains(contextTime)) {
            if (resourceTime.isGeneric()) {
                return refitTime(contextTime, resourceTime);
            } else {
                throw new KlabContextualizationException("Current observation is outside the bounds of the STAC resource and cannot be reffitted.");
            }
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

    @Override
    public void getEncodedData(IResource resource, Map<String, String> urnParameters, IGeometry geometry, Builder builder,
            IContextualizationScope scope) {
        IObservable targetSemantics = scope.getTargetArtifact() instanceof Observation
                ? ((Observation) scope.getTargetArtifact()).getObservable()
                : null;
        HMRaster.MergeMode mergeMode = chooseMergeMode(targetSemantics, scope.getMonitor());

        String collectionUrl = resource.getParameters().get("collection", String.class);

        STACService service = STACAdapter.getService(collectionUrl);
        if (service.isStatic()) {
            // TODO implement how to read static collections
            throw new KlabUnimplementedException("Cannot read a static collection.");
        }
        HMStacCollection collection = service.getCollection();
        if (collection == null) {
            scope.getMonitor().error("Collection " + resource.getParameters().get("collection", String.class) + " cannot be find.");
        }

        Space space = (Space) geometry.getDimensions().stream().filter(d -> d instanceof Space)
                .findFirst().orElseThrow();
        IEnvelope envelope = space.getEnvelope();
        Envelope env = new Envelope(envelope.getMinX(), envelope.getMaxX(), envelope.getMinY(), envelope.getMaxY());
        Polygon poly = GeometryUtilities.createPolygonFromEnvelope(env);
        collection.setGeometryFilter(poly);

        Time time = (Time) geometry.getDimensions().stream().filter(d -> d instanceof Time)
                .findFirst().orElseThrow();
        Time resourceTime = (Time) Scale.create(resource.getGeometry()).getDimension(Type.TIME);
        if (resourceTime.getCoveredExtent() > 0) {
            time = validateTemporalDimension(time, resourceTime);
            ITimeInstant start = time.getStart();
            ITimeInstant end = time.getEnd();
            collection.setTimestampFilter(new Date(start.getMilliseconds()), new Date(end.getMilliseconds()));
        }

        GridCoverage2D coverage = null;
        try {
            List<HMStacItem> items = collection.searchItems();

            if (items.isEmpty()) {
                throw new KlabIllegalStateException("No STAC items found for this context.");
            }
            scope.getMonitor().debug("Found " + items.size() + " STAC items.");

            if (mergeMode == HMRaster.MergeMode.SUBSTITUTE) {
                sortByDate(items, scope.getMonitor());
            }

            LogProgressMonitor lpm = new LogProgressMonitor();
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

            // Allow transform ensures the process to finish, but I would not bet on the resulting
            // data.
            final boolean allowTransform = true;
            String assetId = resource.getParameters().get("asset", String.class);
            HMRaster outRaster = collection.readRasterBandOnRegion(regionTransformed, assetId, items, allowTransform, mergeMode, lpm);
            coverage = outRaster.buildCoverage();
        } catch (Exception e) {
            throw new KlabInternalErrorException("Cannot build STAC raster output. Reason " + e.getMessage());
        }

        encoder.encodeFromCoverage(resource, urnParameters, coverage, geometry, builder, scope);
    }

    @Override
    public void listDetail(IResource resource, OutputStream stream, boolean verbose, IMonitor monitor) {
        // TODO Auto-generated method stub
    }

}
