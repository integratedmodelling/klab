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
            this.collection = catalog.getCollectionById(collectionId);
        } catch (Exception e) {
            throw new KlabInternalErrorException("Error trying to create a STAC Service. " + e.getMessage());
        }
    }

    public HMStacCollection getCollection() {
        return collection;
    }

    public IGeometry getGeometry(IParameters<String> parameters) {
        GeometryBuilder gBuilder = Geometry.builder();
        gBuilder.time().generic();

        ReferencedEnvelope envelope = collection.getSpatialBounds();
        double[] upperCorner = {envelope.getMaxX(), envelope.getMaxY()};
        double[] lowerCorner = {envelope.getMinX(), envelope.getMinY()};

        gBuilder.space().boundingBox(lowerCorner[0], upperCorner[0], lowerCorner[1], upperCorner[1]);

        // For now, we will assume that there is a single interval
        // From the STAC documentation: "The first time interval always describes the overall
        // temporal extent of the data. All subsequent time intervals can be used to provide a more
        // precise description of the extent and identify clusters of data."
        List<Date> interval = collection.getTemporalBounds();
        if (!interval.isEmpty()) {
            gBuilder.time().start(interval.get(0).getTime());
            gBuilder.time().end(interval.get(1).getTime());
        }

        Geometry ret = gBuilder.build().withProjection(Projection.DEFAULT_PROJECTION_CODE);
        return ret;
    }

}
