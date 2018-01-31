package org.integratedmodelling.klab.components.geospace;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.extensions.component.Initialize;
import org.integratedmodelling.klab.components.geospace.extents.Projection;

import com.vividsolutions.jts.geom.GeometryFactory;

@Component(id = "geospace", version = Version.CURRENT)
public class Geospace {

    public static final String    DEFAULT_PROJECTION_CODE = "EPSG:4326";
    public static GeometryFactory gFactory                = new GeometryFactory();
    public static Projection defaultProjection;

    public Geospace() {
        // TODO Auto-generated constructor stub
    }

    @Initialize
    public void initialize() {
        // TODO create the desired geometry factory
        // TODO set up defaults for projections etc.
    }

    /**
     * The haversine formula calculates great-circle distance between two points on a
     * sphere from their longitudes and latitudes.
     * 
     * From http://rosettacode.org/wiki/Haversine_formula#Java
     * 
     * @param lat1 PointOne latitude
     * @param lon1 PointOne longitude
     * @param lat2 PointTwo latitude
     * @param lon2 PointTwo longitude
     * @return distance in meters
     */
    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
        double R = 6372800; // in m
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }

    public static Projection getDefaultProjection() {
        if (defaultProjection == null) {
            defaultProjection = Projection.create(DEFAULT_PROJECTION_CODE);
        }
        return defaultProjection;
    }

}
