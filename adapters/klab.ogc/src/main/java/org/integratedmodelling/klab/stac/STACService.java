package org.integratedmodelling.klab.stac;

import java.time.Instant;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.hortonmachine.gears.io.stac.HMStacCollection;
import org.hortonmachine.gears.io.stac.HMStacManager;
import org.hortonmachine.gears.libs.monitor.LogProgressMonitor;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.GeometryBuilder;
import org.integratedmodelling.klab.components.geospace.extents.Envelope;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class STACService {
    private HMStacManager catalog;
    private HMStacCollection collection;

    public STACService(String catalogUrl, String collectionId) {
        LogProgressMonitor lpm = new LogProgressMonitor();
        this.catalog = new HMStacManager(catalogUrl, lpm);
        try {
            this.catalog.open();
            this.collection = catalog.getCollectionById(collectionId);
        } catch (Exception e) {
            throw new KlabInternalErrorException("Error trying to create a STAC Service. " + e.getMessage());
        }
    }

    public HMStacCollection getCollection() {
        return collection;
    }

    public IEnvelope getEnvelope() {
        ReferencedEnvelope envelope = collection.getSpatialBounds();
        double[] upperCorner = {envelope.getMaxX(), envelope.getMaxY()};
        double[] lowerCorner = {envelope.getMinX(), envelope.getMinY()};

        return Envelope.create(lowerCorner[0], upperCorner[0], lowerCorner[1], upperCorner[1], Projection.getLatLon());
    }

    public IGeometry getGeometry(IParameters<String> parameters) {
        String catalogUrl = parameters.get("catalogUrl", String.class);
        String collectionId = parameters.get("collectionId", String.class);

        GeometryBuilder gBuilder = Geometry.builder();
        gBuilder.time().generic();

        JSONObject collectionMetadata = STACUtils.requestCollectionMetadata(catalogUrl, collectionId);
        JSONArray bbox = collectionMetadata.getJSONObject("extent").getJSONObject("spatial").getJSONArray("bbox").getJSONArray(0);
        gBuilder.space().boundingBox(bbox.getDouble(0), bbox.getDouble(1), bbox.getDouble(2), bbox.getDouble(3));

        // For now, we will assume that there is a single interval
        // From the STAC documentation: "The first time interval always describes the overall
        // temporal extent of the data. All subsequent time intervals can be used to provide a more
        // precise description of the extent and identify clusters of data."
        JSONArray timeInterval = collectionMetadata.getJSONObject("extent").getJSONObject("temporal").getJSONArray("interval").getJSONArray(0);
        if (!timeInterval.isNull(0)) {
            Instant start = Instant.parse(timeInterval.getString(0));
            gBuilder.time().start(start.toEpochMilli());
        }
        if (!timeInterval.isNull(1)) {
            Instant end = Instant.parse(timeInterval.getString(1));
            gBuilder.time().end(end.toEpochMilli());
        }

        Geometry ret = gBuilder.build().withProjection(Projection.DEFAULT_PROJECTION_CODE);
        return ret;
    }

}
