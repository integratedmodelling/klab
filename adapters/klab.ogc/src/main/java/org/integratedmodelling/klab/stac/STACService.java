package org.integratedmodelling.klab.stac;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
import kong.unirest.JsonNode;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class STACService {

    public static final String LOWER_CORNER = "ows:LowerCorner";
    public static final String UPPER_CORNER = "ows:UpperCorner";

    private HMStacManager catalog;
    private List<HMStacCollection> collections = Collections.synchronizedList(new ArrayList<>());

    private String resourceUrl;
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

        return Envelope.create(lowerCorner[0], upperCorner[0], lowerCorner[1], upperCorner[1], Projection.getLatLon());
    }

    public IGeometry getGeometry(IParameters<String> parameters) {
        String catalogUrl = parameters.get("catalogUrl", String.class);
        String collectionId = parameters.get("collectionId", String.class);
        String item = parameters.get("asset", String.class);
        GeometryBuilder gBuilder = Geometry.builder();

        JsonNode collectionMetadata = STACUtils.requestCollectionMetadata(catalogUrl, collectionId);
        JsonNode itemMetadata = STACUtils.requestItemMetadata(catalogUrl, collectionId, item);

        JSONObject itemInfo = itemMetadata.getObject();
        // We should prioritize the data from the item. However, it is sometimes provided as a GeoJSON instead of a bbox.
        // In those cases, we can still work with the bbox of the collection.
        if (itemInfo.has("bbox") && !itemInfo.isNull("bbox")) {
            JSONArray bbox = itemInfo.getJSONArray("bbox");
            gBuilder.space().boundingBox(bbox.getDouble(0), bbox.getDouble(1), bbox.getDouble(2), bbox.getDouble(3));
        } else {
            JSONArray bbox = collectionMetadata.getObject().getJSONArray("bbox");
            gBuilder.space().boundingBox(bbox.getDouble(0), bbox.getDouble(1), bbox.getDouble(2), bbox.getDouble(3));
        }

        JSONArray timeInterval = collectionMetadata.getObject().getJSONArray("interval");
        // For now, we will assume that there is a single interval
        // From the STAC documentation: "The first time interval always describes the overall
        // temporal extent of the data. All subsequent time intervals can be used to provide a more
        // precise description of the extent and identify clusters of data."
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
