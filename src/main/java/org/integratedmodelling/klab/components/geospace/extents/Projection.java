package org.integratedmodelling.klab.components.geospace.extents;

import org.geotools.referencing.CRS;
import org.integratedmodelling.klab.api.observations.scale.space.IProjection;
import org.integratedmodelling.klab.exceptions.KlabRuntimeException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

public class Projection implements IProjection {

  private String                code;
  CoordinateReferenceSystem     crs;

  public static final String    DEFAULT_METERS_PROJECTION = "EPSG:3005";
  public static final String    DEFAULT_PROJECTION_CODE   = "EPSG:4326";
  public static final String    LATLON_PROJECTION_CODE    = "EPSG:4326";
  private static Projection     defaultProjection;
  private static Projection     latlonProjection;
  private static Projection[][] utmProjections            = new Projection[20][60];

  /**
   * The haversine formula calculates great-circle distance between two points on a sphere from
   * their longitudes and latitudes.
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

  public static Projection getDefault() {
    if (defaultProjection == null) {
      defaultProjection = Projection.create(DEFAULT_PROJECTION_CODE);
    }
    return defaultProjection;
  }

  public static Projection getLatLon() {
    if (latlonProjection == null) {
      latlonProjection = Projection.create(LATLON_PROJECTION_CODE);
    }
    return latlonProjection;
  }

  public static Projection getUTM(Envelope envelope) {
    return null;
  }

  private Projection(String code) {
    this.code = code;
    try {
      this.crs = CRS.decode(this.code, true);
    } catch (FactoryException e) {
      throw new KlabRuntimeException(e);
    }
  }

  private Projection(String code, CoordinateReferenceSystem crs) {
    this.code = code;
    this.crs = crs;
  }

  @Override
  public String getCode() {
    return code;
  }

  /**
   * Obtain the projection corresponding to the passed EPSG (or other supported authority) code, in
   * the format "EPSG:nnnn".
   * 
   * @param code
   * @return the projection, which may be invalid.
   */
  public static Projection create(String code) {
    return new Projection(code);
  }

  public static Projection create(String authority, int code) {
    return new Projection(authority + ":" + code);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((code == null) ? 0 : code.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Projection other = (Projection) obj;
    if (code == null) {
      if (other.code != null)
        return false;
    } else if (!code.equals(other.code))
      return false;
    return true;
  }

  public CoordinateReferenceSystem getCoordinateReferenceSystem() {
    return crs;
  }

  static Projection create(CoordinateReferenceSystem coordinateReferenceSystem) {
    try {
      String code = CRS.lookupIdentifier(coordinateReferenceSystem, true);
      return new Projection(code, coordinateReferenceSystem);
    } catch (FactoryException e) {
      throw new KlabRuntimeException(e);
    }
  }

}
