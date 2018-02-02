package org.integratedmodelling.klab.components.geospace.extents;

import org.geotools.geometry.jts.ReferencedEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IProjection;
import org.integratedmodelling.klab.exceptions.KlabRuntimeException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.TransformException;

public class Envelope implements IEnvelope {

  ReferencedEnvelope envelope;
  IProjection        projection;
  Integer            scaleRank = null;

  public static Envelope create(com.vividsolutions.jts.geom.Envelope envelope,
      Projection projection) {
    Envelope ret = new Envelope();
    ret.envelope = new ReferencedEnvelope(envelope, projection.getCoordinateReferenceSystem());
    ret.projection = projection;
    return ret;
  }

  public static Envelope create(ReferencedEnvelope envelope) {
    Envelope ret = new Envelope();
    ret.envelope = envelope;
    ret.projection = Projection.create(envelope.getCoordinateReferenceSystem());
    return ret;
  }


  private Envelope() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public IProjection getProjection() {
    return projection;
  }

  @Override
  public double getMinX() {
    return envelope.getMinX();
  }

  @Override
  public double getMaxX() {
    return envelope.getMaxX();
  }

  @Override
  public double getMinY() {
    return envelope.getMinY();
  }

  @Override
  public double getMaxY() {
    return envelope.getMaxY();
  }

  @Override
  public Shape asShape() {
    // TODO Auto-generated method stub
    return null;
  }

  public static Envelope create(double minx, double maxx, double miny, double maxy,
      Projection crs) {
    // TODO Auto-generated method stub
    return null;
  }

  public double getWidth() {
    return envelope.getWidth();
  }

  public double getHeight() {
    return envelope.getHeight();
  }

  @Override
  public Envelope transform(Projection projection, boolean b) {

    if (projection.equals(this.projection)) {
      return this;
    }
    
    Envelope ret = new Envelope();
    try {
      ret.envelope = this.envelope.transform(projection.crs, true);
    } catch (TransformException | FactoryException e) {
      throw new KlabRuntimeException(e);
    }
    ret.projection = projection;
    return ret;
  }

  /**
   * Compute the zoom level a'la Google/OSM.
   * <p>
   * 
   * <pre>
   * Level  Degrees  Area            m/pixel     ~Scale          # Tiles
   * 0      360      whole world     156,412     1:500 million   1
   * 1      180                      78,206      1:250 million   4
   * 2      90                       39,103      1:150 million   16
   * 3      45                       19,551      1:70 million    64
   * 4      22.5                     9,776       1:35 million    256
   * 5      11.25                    4,888       1:15 million    1,024
   * 6      5.625                    2,444       1:10 million    4,096
   * 7      2.813                    1,222       1:4 million     16,384
   * 8      1.406                    610.984     1:2 million     65,536
   * 9      0.703    wide area       305.492     1:1 million     262,144
   * 10     0.352                    152.746     1:500,000       1,048,576
   * 11     0.176    area            76.373      1:250,000       4,194,304
   * 12     0.088                    38.187      1:150,000       16,777,216
   * 13     0.044    village or town 19.093      1:70,000        67,108,864
   * 14     0.022                    9.547       1:35,000        268,435,456
   * 15     0.011                    4.773       1:15,000        1,073,741,824
   * 16     0.005    small road      2.387       1:8,000         4,294,967,296
   * 17     0.003                    1.193       1:4,000         17,179,869,184
   * 18     0.001                    0.596       1:2,000         68,719,476,736
   * 19     0.0005                   0.298       1:1,000         274,877,906,944
   * </pre>
   * 
   * @return the scale rank (zoom level) for the envelope
   * 
   */
  public int getScaleRank() {

    if (this.scaleRank == null) {
      Envelope envelope = transform(Projection.getLatLon(), true);

      int zoomLevel;
      double latDiff = envelope.getHeight();
      double lngDiff = envelope.getWidth();

      double maxDiff = (lngDiff > latDiff) ? lngDiff : latDiff;
      if (maxDiff < 360 / Math.pow(2, 20)) {
        zoomLevel = 21;
      } else {
        zoomLevel =
            (int) (-1 * ((Math.log(maxDiff) / Math.log(2)) - (Math.log(360) / Math.log(2))));
        if (zoomLevel < 1) {
          zoomLevel = 1;
        }
      }
      this.scaleRank = zoomLevel;
    }
    
    return this.scaleRank;
  }

  public com.vividsolutions.jts.geom.Envelope getJTSEnvelope() {
    return envelope;
  }

  @Override
  public double[] getCenterCoordinates() {
    return new double[] {envelope.getMedian(0), envelope.getMedian(1)};
  }

}
