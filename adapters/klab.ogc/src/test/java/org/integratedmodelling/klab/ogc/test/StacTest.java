package org.integratedmodelling.klab.ogc.test;

import java.util.List;

import org.hortonmachine.gears.io.stac.HMStacCollection;
import org.hortonmachine.gears.io.stac.HMStacItem;
import org.hortonmachine.gears.io.stac.HMStacManager;
import org.hortonmachine.gears.utils.geometry.GeometryUtilities;
import org.junit.Test;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Polygon;

public class StacTest {

    String catalogUrl = "https://earth-search.aws.element84.com/v1";
    String collectionId = "sentinel-2-l2a";

    @Test
    public void stacTest() throws Exception {
        HMStacManager manager = new HMStacManager(catalogUrl, null);
        manager.open();
        HMStacCollection collection = manager.getCollectionById(collectionId);
        
        Envelope env = new Envelope(100.0, 99.0, 50.0, 49.0);
        Polygon poly = GeometryUtilities.createPolygonFromEnvelope(env);
        List<HMStacItem> items = collection.setGeometryFilter(poly).searchItems();

        manager.close();
    }
}
