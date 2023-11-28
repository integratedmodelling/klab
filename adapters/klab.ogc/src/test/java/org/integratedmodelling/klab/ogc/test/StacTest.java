package org.integratedmodelling.klab.ogc.test;

import java.util.Date;
import java.util.List;
import org.hortonmachine.gears.io.stac.EarthAwsElement84Sentinel2Bands;
import org.hortonmachine.gears.io.stac.HMStacCollection;
import org.hortonmachine.gears.io.stac.HMStacItem;
import org.hortonmachine.gears.io.stac.HMStacManager;
import org.hortonmachine.gears.libs.modules.HMRaster;
import org.hortonmachine.gears.libs.monitor.LogProgressMonitor;
import org.hortonmachine.gears.utils.RegionMap;
import org.hortonmachine.gears.utils.geometry.GeometryUtilities;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.junit.Ignore;
import org.junit.Test;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygon;

import com.ibm.icu.util.Calendar;

public class StacTest {

    String catalogUrl = "https://earth-search.aws.element84.com/v1";
    String collectionId = "sentinel-2-l2a";

    private Date getDates(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }

    @Test
    @Ignore
    public void stacTestProgress() throws Exception {
        LogProgressMonitor lpm = new LogProgressMonitor();
        HMStacManager manager = new HMStacManager(catalogUrl, lpm);
        manager.open();
        HMStacCollection collection = manager.getCollectionById(collectionId);

        Envelope env = new Envelope(100.0, 99.0, 50.0, 49.0);
        Polygon poly = GeometryUtilities.createPolygonFromEnvelope(env);
        Date first = getDates(2015, 02, 20);
        Date second = getDates(2017, 05, 20);
        List<HMStacItem> items = collection.setGeometryFilter(poly)
                .setTimestampFilter(first, second)
                .searchItems();
        manager.close();
    }

    
    @Test
    @Ignore
    public void stacTestOld() throws Exception {
        LogProgressMonitor lpm = new LogProgressMonitor();
        try (HMStacManager manager = new HMStacManager(catalogUrl, lpm)) {
            manager.open();
            HMStacCollection collection = manager.getCollectionById(collectionId);

            Envelope env = new Envelope(100.0, 99.0, 50.0, 49.0);
            Polygon poly = GeometryUtilities.createPolygonFromEnvelope(env);
            Date first = getDates(2015, 02, 20);
            Date second = getDates(2017, 05, 20);
            List<HMStacItem> items = collection.setGeometryFilter(poly)
                    .setTimestampFilter(first, second)
                    .searchItems();

            for( HMStacItem hmStacItem : items ) {
                System.out.println(hmStacItem.getId());
            }
        }
    }

    private String getStacBand() {
        String band = EarthAwsElement84Sentinel2Bands.green.name();
        for(EarthAwsElement84Sentinel2Bands sentinelBand: EarthAwsElement84Sentinel2Bands.values()) {
            if(sentinelBand.name().equals(band)) {
                return sentinelBand.getRealName();
            }
        }
        return band;
    }

    // No value present
    static String sShape = "EPSG:4326 POLYGON ((-1.6875986268981849 48.34801777460501, -2.346778314398184 48.34801777460501, -2.346778314398184 48.803906413093586, -1.6875986268981849 48.803906413093586, -1.6875986268981849 48.34801777460501))";
    // Working
//    static String sShape = "EPSG:4326 POLYGON ((-4.1937599108927825 42.13077291881794, -4.3585548327677825 42.13077291881794, -4.3585548327677825 42.25839640850498, -4.1937599108927825 42.25839640850498, -4.1937599108927825 42.13077291881794))";
    // org.geotools.referencing.operation.projection.ProjectionException: The transform result may be 0.419 meters away from the expected position. Are you sure that the input coordinates are inside this map projection area of validity? The point is located 14°45.0'E away from the central meridian and 0°00.0'N away from the latitude of origin. The projection is "Transverse_Mercator".
//    static String sShape = "EPSG:4326 POLYGON ((-16.69241145675222 21.11909506216014, -17.02200130050222 21.11909506216014, -17.02200130050222 21.44012937254722, -16.69241145675222 21.44012937254722, -16.69241145675222 21.11909506216014))";
    // Epsgs are different
//    static String sShape = "EPSG:4326 POLYGON ((-0.1343547631315612 38.948261918530505, -0.21631353998703 38.948261918530505, -0.21631353998703 39.01485932167856, -0.1343547631315612 39.01485932167856, -0.1343547631315612 38.948261918530505))";

    @Test
    public void stacTest() throws Exception {
        LogProgressMonitor lpm = new LogProgressMonitor();
        HMStacManager manager = new HMStacManager(catalogUrl, lpm);
        manager.open();
        HMStacCollection collection = manager.getCollectionById(collectionId);
        System.out.println(collection);

        Shape shape = Shape.create(sShape);
        Envelope env = new Envelope(shape.getEnvelope().getMinX(), shape.getEnvelope().getMaxX(),
                shape.getEnvelope().getMinY(), shape.getEnvelope().getMaxY());
        Polygon poly = GeometryUtilities.createPolygonFromEnvelope(env);
        Date first = getDates(2020, 02, 20);
        Date second = getDates(2020, 02, 27);
        List<HMStacItem> items = collection.setGeometryFilter(poly)
                .setTimestampFilter(first, second)
                .searchItems();

        lpm.message("Found a total of " + items.size() + " items");
        items = items.stream().filter(i -> !i.getAssets().isEmpty()).toList();
        lpm.message("Found a total of " + items.size() + " items");

        Geometry coveredAreas = HMStacCollection.getCoveredArea(items);
        Geometry commonArea = coveredAreas.intersection(poly);
        double coveredArea = commonArea.getArea();
        double roiArea = poly.getArea();
        int percentage = (int) Math.round(coveredArea * 100 / roiArea);
        lpm.message("Region of interest is covered by data in amout of " + percentage + "%");

        String stacBand = getStacBand();
        lpm.message("Stac Band: " + stacBand);

        int cols = 979, rows = 676;
        RegionMap regionMap = RegionMap.fromEnvelopeAndGrid(env, cols, rows);
        HMRaster raster = HMStacCollection.readRasterBandOnRegion(regionMap, stacBand, items, true, HMRaster.MergeMode.AVG, lpm);
        lpm.message("Raster: " + raster + "\n-------\n");

        manager.close();
    }
}
