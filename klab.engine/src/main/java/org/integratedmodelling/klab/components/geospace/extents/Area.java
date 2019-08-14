/*******************************************************************************
 * Copyright (C) 2007, 2014:
 * 
 * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any other
 * authors listed in @author annotations
 *
 * All rights reserved. This file is part of the k.LAB software suite, meant to enable modular,
 * collaborative, integrated development of interoperable data and model components. For details,
 * see http://integratedmodelling.org.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * Affero General Public License Version 3 or any later version.
 *
 * This program is distributed in the hope that it will be useful, but without any warranty; without
 * even the implied warranty of merchantability or fitness for a particular purpose. See the Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the Affero General Public License along with this program; if
 * not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA. The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.components.geospace.extents;


import org.integratedmodelling.klab.exceptions.KlabException;
import com.vividsolutions.jts.geom.Coordinate;

public abstract class Area {

  Envelope   envelope = null;
  protected Projection projection;

  public abstract Shape getShape();

  protected Area() {}

  public Area(Projection crs, double minx, double miny, double maxx, double maxy) {
    this.projection = crs;
    this.envelope = Envelope.create(minx, maxx, miny, maxy, crs);
  }

  public Area(Shape shape) {
    this.envelope = shape.getEnvelope();
    this.projection = shape.getProjection();
  }

  /**
   * Get an envelope where the X axis is always east-west.
   * 
   * @return the straightened envelope
   */
  public Envelope getEnvelope() {
    return envelope;
  }

  public Shape getBoundingBox() {
    return envelope.asShape();
  }

  public Shape getCentroid() {
    return getBoundingBox().getCentroid();
  }

  public boolean contains(Coordinate c) {
    return envelope.envelope.contains(c);
  }

  public double getNorth() {
    return envelope.getMaxY();
  }

  public double getWest() {
    return envelope.getMinX();
  }

  public double getSouth() {
    return envelope.getMinY();
  }

  public double getEast() {
    return envelope.getMaxX();
  }

  public double getEWExtent() {
    return envelope.getWidth();
  }

  public double getNSExtent() {
    return envelope.getHeight();
  }

//  public Area and(IExtent extent) throws KlabException {
//
//    Object[] cc = computeCommonExtent(extent);
//
//    Area orextent = (Area) cc[0];
//    Area otextent = (Area) cc[1];
//    CoordinateReferenceSystem ccr = (CoordinateReferenceSystem) cc[2];
//    Envelope common = (Envelope) cc[3];
//    Envelope orenvnorm = (Envelope) cc[4];
//    Envelope otenvnorm = (Envelope) cc[5];
//
//    /*
//     * TODO intersection may be empty - this should be checked in createMergedExtent instead of
//     * cursing here.
//     */
//    if (common.isNull()) {
//      return null;
//    }
//
//    /**
//     * send out to a virtual to create the appropriate areal extent with this envelope and CRS,
//     * adding whatever else we need to use it.
//     */
//    return createMergedExtent(orextent, otextent, ccr, common, orenvnorm, otenvnorm);
//  }
//
//  public Area constrain(IExtent extent) throws KlabException {
//
//    Object[] cc = computeCommonExtent(extent);
//
//    Area orextent = (Area) cc[0];
//    Area otextent = (Area) cc[1];
//    CoordinateReferenceSystem ccr = (CoordinateReferenceSystem) cc[2];
//    Envelope common = (Envelope) cc[3];
//    Envelope orenvnorm = (Envelope) cc[4];
//    Envelope otenvnorm = (Envelope) cc[5];
//
//    /*
//     * TODO intersection may be empty - this should be checked in createMergedExtent instead of
//     * cursing here.
//     */
//    if (common.isNull()) {
//      return null;
//    }
//
//    /**
//     * send out to a virtual to create the appropriate areal extent with this envelope and CRS,
//     * adding whatever else we need to use it.
//     */
//    return createConstrainedExtent(orextent, otextent, ccr, common, orenvnorm, otenvnorm);
//  }

//  /**
//   * Does the actual work of merging with the extent, after merge() has ensured that the extents are
//   * spatial and have a common intersection and crs.
//   * 
//   * @param orextent
//   * @param otextent
//   * @param ccr
//   * @param common
//   * @param otenvnorm
//   * @param orenvnorm
//   * @return
//   * @throws KlabException
//   */
//  protected abstract Area createMergedExtent(Area orextent, Area otextent,
//      CoordinateReferenceSystem ccr, Envelope common, Envelope orenvnorm, Envelope otenvnorm)
//      throws KlabException;
//
//  /**
//   * Does the actual work of merging with the extent, ensuring that the grain of the extent is the
//   * same as that of the constraining extent. Called after merge() has ensured that the extents are
//   * spatial and have a common intersection and crs.
//   * 
//   * @param orextent
//   * @param otextent
//   * @param ccr
//   * @param common
//   * @param otenvnorm
//   * @param orenvnorm
//   * @return
//   * @throws KlabException
//   */
//  protected abstract Area createConstrainedExtent(Area orextent, Area otextent,
//      CoordinateReferenceSystem ccr, Envelope common, Envelope orenvnorm, Envelope otenvnorm)
//      throws KlabException;

  public boolean contains(Area e) throws KlabException {
    return getBoundingBox().shapeGeometry.contains(e.getBoundingBox().shapeGeometry);
  }

  public boolean overlaps(Area e) throws KlabException {
    return getBoundingBox().shapeGeometry.overlaps(e.getBoundingBox().shapeGeometry);
  }

  public boolean intersects(Area e) throws KlabException {
    return getBoundingBox().shapeGeometry.intersects(e.getBoundingBox().shapeGeometry);
  }

}
