package org.integratedmodelling.klab.components.geospace.extents;

import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid.Cell;
import org.integratedmodelling.klab.api.observations.scale.space.IProjection;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.components.geospace.Geospace;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.prep.PreparedGeometry;
import com.vividsolutions.jts.geom.prep.PreparedGeometryFactory;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;
import com.vividsolutions.jts.io.WKTReader;

public class Shape implements IShape {

  Geometry                 geometry;
  Envelope                 envelope;
  Type                     type = null;
  Projection               projection;

  // these are used to speed up repeated point-in-polygon operations like
  // those that RasterActivationLayer does.
  private PreparedGeometry preparedShape;
  private boolean          preparationAttempted;
  
  // the shape in an appropriate meter projection. If metersComputed is true and metered == null, this is in meters already.
  boolean metersComputed = false;
  private Shape            metered;

  public static Shape empty() {
    return new Shape();
  }

  public static Shape create(String wkt) throws KlabValidationException {
    Shape ret = new Shape();
    ret.parseWkt(wkt);
    if (ret.geometry != null) {
      ret.envelope = Envelope.create(ret.geometry.getEnvelopeInternal(), ret.projection);
    }
    return ret;
  }

  public static Shape create(double x1, double y1, double x2, double y2, Projection projection) {
    Shape ret = new Shape();
    ret.geometry = makeCell(x1, y1, x2, y2);
    ret.projection = projection;
    ret.envelope = Envelope.create(ret.geometry.getEnvelopeInternal(), ret.projection);
    return ret;
  }

  private static Shape create(Geometry geometry, Projection projection) {
    Shape ret = new Shape();
    ret.geometry = geometry;
    ret.projection = projection;
    ret.envelope = Envelope.create(ret.geometry.getEnvelopeInternal(), ret.projection);
    return ret;
  }

  public static Geometry makeCell(double x1, double y1, double x2, double y2) {

    Coordinate[] pts = {new Coordinate(x1, y1), new Coordinate(x2, y1), new Coordinate(x2, y2),
        new Coordinate(x1, y2), new Coordinate(x1, y1)};

    return Geospace.gFactory.createPolygon(Geospace.gFactory.createLinearRing(pts), null);
  }

  public static Geometry makePoint(double x1, double y1) {
    Coordinate coordinate = new Coordinate(x1, y1);
    return Geospace.gFactory.createPoint(coordinate);
  }

  public static Geometry makePoint(IGrid.Cell cell) {
    double[] xy = cell.getCenter();
    return Geospace.gFactory.createPoint(new Coordinate(xy[0], xy[1]));
  }

  private Shape() {}

  @Override
  public Projection getProjection() {
    return projection;
  }

  @Override
  public Type getType() {
    if (type == null) {
      if (geometry instanceof Polygon) {
        type = Type.POLYGON;
      } else if (geometry instanceof MultiPolygon) {
        type = Type.MULTIPOLYGON;
      } else if (geometry instanceof Point) {
        type = Type.POINT;
      } else if (geometry instanceof MultiLineString) {
        type = Type.MULTILINESTRING;
      } else if (geometry instanceof LineString) {
        type = Type.LINESTRING;
      } else if (geometry instanceof MultiPoint) {
        type = Type.MULTIPOINT;
      }
    }
    return type;
  }

  public double getNativeArea() {
    return geometry.getArea();
  }
  
  @Override
  public double getArea(IUnit unit) {
    // TODO Auto-generated method stub
    return 0;
  }

  public Shape getCentroid() {
    return create(geometry.getCentroid(), projection);
  }

  @Override
  public boolean isEmpty() {
    return geometry == null || geometry.isEmpty();
  }

  @Override
  public Shape transform(IProjection otherProjection) throws KlabValidationException {

    if (this.projection.equals(projection)) {
      return this;
    }
    Geometry g = null;

    try {
      g = JTS.transform(geometry,
          CRS.findMathTransform(projection.crs, ((Projection) otherProjection).crs));
    } catch (Exception e) {
      throw new KlabValidationException(e);
    }

    return Shape.create(g, (Projection) otherProjection);
  }

  @Override
  public Envelope getEnvelope() {
    return envelope;
  }

  @Override
  public Shape intersection(
      org.integratedmodelling.klab.api.observations.scale.space.IShape other) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Shape union(org.integratedmodelling.klab.api.observations.scale.space.IShape other) {
    // TODO Auto-generated method stub
    return null;
  }

  public boolean containsCoordinates(double x, double y) {
    checkPreparedShape();
    return preparedShape == null
        ? geometry.contains(Geospace.gFactory.createPoint(new Coordinate(x, y)))
        : preparedShape.contains(Geospace.gFactory.createPoint(new Coordinate(x, y)));
  }

  private void checkPreparedShape() {
    if (this.preparedShape == null && !preparationAttempted) {
      preparationAttempted = true;
      try {
        this.preparedShape = PreparedGeometryFactory.prepare(geometry);
      } catch (Throwable t) {
        //
      }
    }
  }

  public PreparedGeometry getPreparedGeometry() {
    checkPreparedShape();
    return preparedShape;
  }

  public double getCoverage(Cell cell, boolean simpleIntersection) {

    checkPreparedShape();
    if (preparedShape == null) {
      if (simpleIntersection) {
        Geometry gm = makePoint(cell);
        return gm.intersects(geometry) ? 1.0 : 0.0;
      }
      Geometry gm = makeCell(cell.getEast(), cell.getSouth(), cell.getWest(), cell.getNorth());
      return gm.covers(geometry) ? 1.0 : (gm.intersection(geometry).getArea() / gm.getArea());
    }
    if (simpleIntersection) {
      return preparedShape.covers(makePoint(cell)) ? 1 : 0;
    }
    Geometry gm = makeCell(cell.getEast(), cell.getSouth(), cell.getWest(), cell.getNorth());
    return preparedShape.covers(gm) ? 1.0 : (gm.intersection(geometry).getArea() / gm.getArea());
  }


  private void parseWkt(String s) throws KlabValidationException {

    String pcode = Projection.DEFAULT_PROJECTION_CODE;
    Geometry geometry = null;
    boolean wkt = false;
    /*
     * first see if we start with a token that matches "EPSG:[0-9]*". If so, set the CRS from it;
     * otherwise it is null (not the plugin default).
     */
    if (s.startsWith("EPSG:") || s.startsWith("urn:")) {
      int n = s.indexOf(' ');
      pcode = s.substring(0, n);
      s = s.substring(n + 1);
    }
    try {
      if (s.contains("(")) {
        wkt = true;
        geometry = new WKTReader().read(s);
      } else {
        geometry = new WKBReader().read(WKBReader.hexToBytes(s));
      }
    } catch (ParseException e) {
      throw new KlabValidationException(
          "error parsing " + (wkt ? "WKT" : "WBT") + ": " + e.getMessage());
    }

    this.projection = Projection.create(pcode);
    this.geometry = geometry;
  }

}
