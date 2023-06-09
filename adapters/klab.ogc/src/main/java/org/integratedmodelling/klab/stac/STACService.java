package org.integratedmodelling.klab.stac;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.geotools.geometry.jts.ReferencedEnvelope;
import org.hortonmachine.gears.io.stac.HMStacCollection;
import org.hortonmachine.gears.io.stac.HMStacManager;
import org.hortonmachine.gears.libs.monitor.LogProgressMonitor;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IProjection;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.geospace.extents.Envelope;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.rest.SpatialExtent;

public class STACService {

    public static final String LOWER_CORNER = "ows:LowerCorner";
    public static final String UPPER_CORNER = "ows:UpperCorner";

    private HMStacManager catalog;
    private List<HMStacCollection> collections = Collections.synchronizedList(new ArrayList<>());

    // envelope in WGS84 from capabilities
    private IEnvelope wgs84envelope;

    private String resourceUrl;
    private IEnvelope originalEnvelope;
    private IProjection originalProjection;


    public STACService(String resourceUrl) {
        this.resourceUrl = resourceUrl;
        LogProgressMonitor lpm = new LogProgressMonitor();
        this.catalog = new HMStacManager(resourceUrl, lpm);
        try {
            this.catalog.open();
            this.collections.addAll(catalog.getCollections());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getServiceUrl() {
        return resourceUrl;
    }

    public Optional<HMStacCollection> getCollectionById(String collectionId) throws Exception {
        HMStacCollection collection = catalog.getCollectionById(collectionId);
        if (collection == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(collection);
    }

    public List<HMStacCollection> getCollections() {
        return collections;
    }

    public IEnvelope getEnvelope(String collectionId) {
        HMStacCollection collection = collections.stream().filter(c -> c.getId().equals(collectionId)).findFirst().get();
        ReferencedEnvelope envelope = collection.getSpatialBounds();
        double[] upperCorner = {envelope.getMaxX(), envelope.getMaxY()};
        double[] lowerCorner = {envelope.getMinX(), envelope.getMinY()};

        return Envelope.create(lowerCorner[0], upperCorner[0], lowerCorner[1], upperCorner[1],
                Projection.getLatLon());
    }

    public IGeometry getGeometry(String collectionId) {
        Geometry ret = Geometry.create("S2");

        HMStacCollection collection = collections.stream().filter(c -> c.getId().equals(collectionId)).findFirst().get();
        ReferencedEnvelope envelope = collection.getSpatialBounds();
        double[] upperCorner = {envelope.getMaxX(), envelope.getMaxY()};
        double[] lowerCorner = {envelope.getMinX(), envelope.getMinY()};

        wgs84envelope = Envelope.create(lowerCorner[0], upperCorner[0], lowerCorner[1], upperCorner[1],
                Projection.getLatLon());

        if (originalProjection != null && originalEnvelope != null) {
            if (originalProjection.flipsCoordinates()) {
                // use the WGS84
                ret = ret.withBoundingBox(wgs84envelope.getMinX(), wgs84envelope.getMaxX(), wgs84envelope.getMinY(),
                        wgs84envelope.getMaxY()).withProjection(Projection.DEFAULT_PROJECTION_CODE);
            } else {
                ret = ret.withBoundingBox(originalEnvelope.getMinX(), originalEnvelope.getMaxX(), originalEnvelope.getMinY(),
                        originalEnvelope.getMaxY()).withProjection(originalProjection.getSimpleSRS());
            }
        } else if (wgs84envelope != null) {
            ret = ret.withBoundingBox(wgs84envelope.getMinX(), wgs84envelope.getMaxX(), wgs84envelope.getMinY(),
                    wgs84envelope.getMaxY()).withProjection(Projection.DEFAULT_PROJECTION_CODE);
        }

        return ret;
    }

    public SpatialExtent getSpatialExtent() {

        if (wgs84envelope == null) {
            return null;
        }
        SpatialExtent ret = new SpatialExtent();
        ret.setWest(wgs84envelope.getMinX());
        ret.setEast(wgs84envelope.getMaxX());
        ret.setSouth(wgs84envelope.getMinY());
        ret.setNorth(wgs84envelope.getMaxY());
        return ret;
    }

}
