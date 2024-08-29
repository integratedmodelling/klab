package org.integratedmodelling.klab.stac;

import java.util.Date;
import java.util.List;

import org.geotools.geometry.jts.ReferencedEnvelope;
import org.hortonmachine.gears.io.stac.HMStacCollection;
import org.hortonmachine.gears.io.stac.HMStacManager;
import org.hortonmachine.gears.libs.monitor.LogProgressMonitor;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.GeometryBuilder;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;

public class STACService {
    private HMStacManager catalog;
    private HMStacCollection collection;

    public STACService(String catalogUrl, String collectionId) {
        LogProgressMonitor lpm = new LogProgressMonitor();
        this.catalog = new HMStacManager(catalogUrl, lpm);
        try {
            this.catalog.open();
        } catch (Exception e) {
            throw new KlabInternalErrorException("Error at STAC service. Cannot read catalog at '" + catalogUrl + "'.");
        }
        try {
            this.collection = catalog.getCollectionById(collectionId);
        } catch (Exception e) {
            throw new KlabInternalErrorException("Error at STAC service. Cannot read collection at '" + catalogUrl + "/collections/" + collectionId + "'.");
        }
        if (collection == null) {
            throw new KlabInternalErrorException("Error at STAC service. Endpoint '" + catalogUrl + "' has no collection '" + collectionId + "'.");
        }
    }

    public HMStacCollection getCollection() {
        return collection;
    }

    public IGeometry getGeometry(IParameters<String> parameters) {
        GeometryBuilder gBuilder = Geometry.builder();
        ReferencedEnvelope envelope = collection.getSpatialBounds();
        double[] upperCorner = {envelope.getMaxX(), envelope.getMaxY()};
        double[] lowerCorner = {envelope.getMinX(), envelope.getMinY()};

        gBuilder.space().boundingBox(lowerCorner[0], upperCorner[0], lowerCorner[1], upperCorner[1]);

        setTemporalInterval(gBuilder);

        Geometry ret = gBuilder.build().withProjection(Projection.DEFAULT_PROJECTION_CODE)
                .withTimeType("grid");
        return ret;
    }

    private void setTemporalInterval(GeometryBuilder gBuilder) {
        List<Date> interval = collection.getTemporalBounds();
        if (interval.isEmpty()) {
            return;
        }
        if (interval.get(0) != null) {
            gBuilder.time().start(interval.get(0).getTime());
        }
        if (interval.size() > 1 && interval.get(1) != null) {
            gBuilder.time().end(interval.get(1).getTime());
        }
    }

}
