package org.integratedmodelling.klab.components.geospace.extents;

import org.geotools.geometry.jts.ReferencedEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IProjection;

public class Envelope implements IEnvelope {

  ReferencedEnvelope envelope;
  IProjection        projection;

  public static Envelope create(com.vividsolutions.jts.geom.Envelope envelope,
      Projection projection) {
    Envelope ret = new Envelope();
    ret.envelope = new ReferencedEnvelope(envelope, projection.getCoordinateReferenceSystem());
    ret.projection = projection;
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

}
