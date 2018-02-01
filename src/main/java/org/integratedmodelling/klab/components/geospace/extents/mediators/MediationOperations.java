/*******************************************************************************
 * Copyright (C) 2007, 2015:
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
package org.integratedmodelling.klab.components.geospace.extents.mediators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.api.data.Aggregation;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IObservable.ObservationType;
import org.integratedmodelling.klab.api.observations.scale.IScale.Locator;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid.Cell;
import org.integratedmodelling.klab.api.observations.scale.space.IProjection;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.engine.resources.CoreOntology.NS;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.utils.Pair;
import com.vividsolutions.jts.geom.Envelope;

public class MediationOperations {

  public static class Subgrid implements IGrid {

    Grid grid;
    Grid ogrid;
    int  xofs = 0;
    int  yofs = 0;

    private Subgrid(Grid grid, Grid originalGrid, int xofs, int yofs) {
      this.grid = grid;
      this.ogrid = originalGrid;
      this.xofs = xofs;
      this.yofs = yofs;
    }

    @Override
    public String toString() {
      return grid.toString() + "(xofs=" + xofs + ", yofs=" + yofs + ")";
    }

    @Override
    public int getYCells() {
      return grid.getYCells();
    }

    @Override
    public int getXCells() {
      return grid.getXCells();
    }

    @Override
    public int getCellCount() {
      return grid.getCellCount();
    }

    @Override
    public int getOffset(int x, int y) {
      return grid.getOffset(x, y);
    }

    @Override
    public boolean isActive(int x, int y) {
      return grid.isActive(x, y);
    }

    @Override
    public int getOffsetFromWorldCoordinates(double lon, double lat) {
      return grid.getOffsetFromWorldCoordinates(lon, lat);
    }

    @Override
    public int[] getXYOffsets(int index) {
      return grid.getXYOffsets(index);
    }

    @Override
    public double[] getCoordinates(int index) {
      return grid.getCoordinates(index);
    }

    @Override
    public Locator getLocator(int x, int y) {
      return grid.getLocator(x, y);
    }

    @Override
    public double getEast() {
      return grid.getEast();
    }

    @Override
    public double getWest() {
      return grid.getWest();
    }

    @Override
    public double getSouth() {
      return grid.getSouth();
    }

    @Override
    public double getNorth() {
      return grid.getNorth();
    }

    @Override
    public double getCellWidth() {
      return grid.getCellWidth();
    }

    @Override
    public double getCellHeight() {
      return grid.getCellHeight();
    }

    public IShape getShape() {
      return grid.getShape();
    }

    @Override
    public Iterator<Cell> iterator() {
      return grid.iterator();
    }

    public Cell getOriginalCell(Cell cell) {
      return ogrid.getCell(cell.getX() + xofs,
          ogrid.getYCells() - yofs - 1 - (getYCells() - cell.getY() - 1));
    }

    public double geCellArea(IUnit unit) {
      return grid.getCellArea(unit);
    }

    @Override
    public double snapX(double xCoordinate, int direction) {
      return grid.snapX(xCoordinate, direction);
    }

    @Override
    public double snapY(double yCoordinate, int direction) {
      return grid.snapY(yCoordinate, direction);
    }

    @Override
    public double getCellArea(IUnit unit) {
      return grid.getCellArea(unit);
    }

    @Override
    public double[] getWorldCoordinatesAt(int x, int y) {
      return grid.getWorldCoordinatesAt(x, y);
    }

    @Override
    public int[] getGridCoordinatesAt(double x, double y) {
      return grid.getGridCoordinatesAt(x, y);
    }

    @Override
    public IProjection getProjection() {
      return grid.getProjection();
    }
  }

  public static Subgrid getSubgrid(Grid grid, Shape shape) throws KlabException {

    Envelope genv = new Envelope(grid.getEast(), grid.getWest(), grid.getSouth(), grid.getNorth());
    Envelope senv = shape.getEnvelope().getJTSEnvelope();

    if (!genv.covers(senv)) {
      return null;
    }

    /*
     * adjusts envelope boundaries to cover original cells exactly
     */
    double gxmin = grid.snapX(senv.getMinX(), IGrid.LEFT);
    double gxmax = grid.snapX(senv.getMaxX(), IGrid.RIGHT);
    double gymin = grid.snapY(senv.getMinY(), IGrid.BOTTOM);
    double gymax = grid.snapY(senv.getMaxY(), IGrid.TOP);

    double dx = gxmax - gxmin;
    double dy = gymax - gymin;

    int nx = (int) (dx / grid.getCellWidth()) + 1;
    int ny = (int) (dy / grid.getCellHeight()) + 1;

    int xofs = (int) ((gxmin - grid.getEast()) / grid.getCellWidth());
    int yofs = (int) ((gymin - grid.getSouth()) / grid.getCellHeight());

    return new Subgrid(Grid.create(gxmin, gymin, gxmax, gymax, nx, ny, shape.getProjection()), grid,
        xofs, yofs);
  }

  /**
   * Return all cells from the ORIGINAL grid that cover the shape, with the corresponding coverage.
   * 
   * @param grid
   * @param shape
   * @param useSimpleIntersection if true, just check for containment and return full coverage. Much
   *        faster. Use when no weighted aggregation is necessary.
   * @return covered cells
   * @throws KlabException
   */
  public static Iterable<Pair<Cell, Double>> getCoveredCells(Grid grid, Shape shape,
      boolean useSimpleIntersection) throws KlabException {

    Subgrid subgrid = getSubgrid(grid, shape);
    if (subgrid == null) {
      return null;
    }

    // SpatialDisplay sd = new SpatialDisplay(sshape.asExtent());
    // sd.add(subgrid, "subgrid");
    // sd.add(grid, "main grid");
    // sd.add(shape, "sub shape");

    Collection<Pair<Cell, Double>> ret = new ArrayList<>();
    for (Cell cell : subgrid) {

      double d = shape.getCoverage(cell, useSimpleIntersection);

      if (d > 0) {

        Cell orig = subgrid.getOriginalCell(cell);
        ret.add(new Pair<>(orig, d));

        // sd.add(cell, "transposed covering");
        // sd.add(orig, "orig covering");

      }
    }

    // sd.show();

    return ret;
  }

  public static Aggregation getAggregator(IObservable observable) {

    Aggregation ret = Aggregation.MAJORITY;
    if (observable.getObservationType() == ObservationType.QUANTIFICATION) {
      ret = Aggregation.AVERAGE;
      if (observable.isExtensive(Concepts.c(NS.SPACE_DOMAIN))) {
        ret = Aggregation.SUM;
      }
    }
    return ret;
  }

}
