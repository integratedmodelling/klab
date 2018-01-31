package org.integratedmodelling.klab.components.geospace.extents;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.observations.scale.IScale.Locator;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.space.Orientation;
import org.integratedmodelling.klab.exceptions.KlabException;
import com.vividsolutions.jts.geom.GeometryFactory;

public class Grid extends Area implements IGrid {

  /**
   * Positions a cell may be on that differ in terms of the allowed neigborhoods
   */
  public static enum CellPositionClass {
    SW_CORNER, SE_CORNER, NW_CORNER, NE_CORNER, S_EDGE, E_EDGE, N_EDGE, W_EDGE, INTERNAL
  }

  /**
   * Directions accessible from each corner
   */
  static Orientation[] swCorner          =
      new Orientation[] {Orientation.N, Orientation.NE, Orientation.E};
  static Orientation[] seCorner          =
      new Orientation[] {Orientation.W, Orientation.NW, Orientation.N};
  static Orientation[] nwCorner          =
      new Orientation[] {Orientation.S, Orientation.SE, Orientation.W};
  static Orientation[] neCorner          =
      new Orientation[] {Orientation.W, Orientation.SW, Orientation.S};

  /**
   * Directions accessible from each edge
   */
  static Orientation[] sEdge             = new Orientation[] {Orientation.W, Orientation.NW,
      Orientation.N, Orientation.NE, Orientation.E};
  static Orientation[] eEdge             = new Orientation[] {Orientation.S, Orientation.SW,
      Orientation.W, Orientation.NW, Orientation.N};
  static Orientation[] nEdge             = new Orientation[] {Orientation.W, Orientation.SW,
      Orientation.S, Orientation.SE, Orientation.E};
  static Orientation[] wEdge             = new Orientation[] {Orientation.S, Orientation.SE,
      Orientation.E, Orientation.NE, Orientation.N};

  static Orientation[] mooreNeighborhood = new Orientation[] {Orientation.W, Orientation.NW,
      Orientation.N, Orientation.NE, Orientation.E, Orientation.SE, Orientation.S, Orientation.SW};


  static int connectionCount(int n, int m) {
    return ((n - 2) * (m - 2) * 8) + // connections in internal cells
        (2 * (n - 2) + 2 * (m - 2)) * 5 + // connections in edge cells
        (4 * 3); // connections in corner cells
  }
  
  /**
   * Create a grid from a shape in the CRS of the shape and using the given resolution
   * for the larger extent.
   * 
   * @param shape
   * @param resolutionInMeters
   * @throws KlabException
   */
  private Grid(Shape shape, String resolutionInMeters) throws KlabException {
      super(shape);
//      Shape bb = shape.getBoundingBox().convertToMeters();
//      double meters = parseResolution(resolutionInMeters);
//      int linearResolution = (int) (bb.getEnvelope().getWidth() / meters);
//      setAdjustedEnvelope(shape, linearResolution);
  }
  
  /**
   * Create a grid extent with a rasterized shape in the given grid. Will not clip the
   * shape or anything: use ONLY when you already know the precise aspect factor for the
   * extent resulting from the shape.
   * 
   * @param shape
   * @param x
   * @param y
   * @throws KlabException
   */
  private Grid(Shape shape, int x, int y) throws KlabException {
      super(shape);
      this.setResolution(x, y);
//      activationLayer = ThinklabRasterizer.createMask(shape, this);
  }

  public class CellImpl implements Cell {

    int   x;
    int   y;

    Shape shape;

    CellImpl(int x, int y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public int getX() {
      return x;
    }

    @Override
    public int getY() {
      return y;
    }

    @Override
    public Cell N() {
      int trow = getYCells() - 1;
      if (y < trow) {
        return new CellImpl(x, y + 1);
      }
      return null;
    }

    @Override
    public Cell S() {
      if (y > 0) {
        return new CellImpl(x, y - 1);
      }
      return null;
    }

    @Override
    public Cell E() {
      if (x > 0) {
        return new CellImpl(x - 1, y);
      }
      return null;
    }

    @Override
    public Cell W() {
      int tcol = getXCells() - 1;
      if (x < tcol) {
        return new CellImpl(x + 1, y);
      }
      return null;
    }

    @Override
    public Cell NW() {
      int trow = getYCells() - 1, tcol = getXCells() - 1;
      if (y < trow && x < tcol) {
        return new CellImpl(x + 1, y + 1);
      }
      return null;
    }

    @Override
    public Cell NE() {
      int trow = getYCells() - 1;
      if (y < trow && x > 0) {
        return new CellImpl(x - 1, y + 1);
      }
      return null;
    }

    @Override
    public Cell SE() {
      if (y > 0 && x > 0) {
        return new CellImpl(x - 1, y - 1);
      }
      return null;
    }

    @Override
    public Cell SW() {
      int tcol = getXCells() - 1;
      if (y > 0 && x < tcol) {
        return new CellImpl(x + 1, y - 1);
      }
      return null;
    }

    @Override
    public String toString() {
      return "<cell " + getEast() + "," + getWest() + " - " + getSouth() + "," + getNorth() + ">";
    }

    /*
     * return all the neighboring cells that actually exist in the grid.
     */
    @Override
    public Collection<Cell> getNeighbors() {
      ArrayList<Cell> ret = new ArrayList<>();
      Cell c = N();
      if (c != null)
        ret.add(c);
      c = S();
      if (c != null)
        ret.add(c);
      c = W();
      if (c != null)
        ret.add(c);
      c = E();
      if (c != null)
        ret.add(c);
      c = NW();
      if (c != null)
        ret.add(c);
      c = NE();
      if (c != null)
        ret.add(c);
      c = SW();
      if (c != null)
        ret.add(c);
      c = SE();
      if (c != null)
        ret.add(c);
      return ret;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + x;
      result = prime * result + y;
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (!(obj instanceof CellImpl))
        return false;
      CellImpl other = (CellImpl) obj;
      if (x != other.x)
        return false;
      if (y != other.y)
        return false;
      return true;
    }

    @Override
    public Cell move(int xOfs, int yOfs) {
      int newX = x + xOfs;
      int newY = y + yOfs;
      if (newX >= 0 && newX < xCells) {
        if (newY >= 0 && newY < yCells) {
          // if (isActive(newX, newY)) {
          return new CellImpl(newX, newY);
          // }
        }
      }
      return null;
    }

    @Override
    public double getEast() {
      return Grid.this.getEast() + (x * getCellWidth());
    }

    @Override
    public double getWest() {
      return this.getEast() + getCellWidth();
    }

    @Override
    public double getSouth() {
      return Grid.this.getSouth() + ((yCells - y - 1) * getCellHeight());
    }

    @Override
    public double getNorth() {
      return this.getSouth() + getCellHeight();
    }

    @Override
    public Integer getOffsetInGrid() {
      return getOffset(x, y);
    }

    @Override
    public double[] getCenter() {
      return getCoordinates(getOffsetInGrid());
    }

    public Grid getGrid() {
      return Grid.this;
    }

    @Override
    public boolean isAdjacent(Cell cell) {
      int xo = Math.abs(cell.getX() - x);
      int yo = Math.abs(cell.getY() - y);
      return (xo + yo) > 0 && (xo <= 1) && (yo <= 1);
    }

    @Override
    public Cell getNeighbor(Orientation orientation) {
      switch (orientation) {
        case E:
          return E();
        case N:
          return N();
        case NE:
          return NE();
        case NW:
          return NW();
        case S:
          return S();
        case SE:
          return SE();
        case SW:
          return SW();
        case W:
          return W();
      }
      return null;
    }

    @Override
    public IShape getShape() {
      return Shape.create(getEast(), getSouth(), getWest(), getNorth(), projection);
    }
  }

  GeometryFactory gFactory          = null;
  int             xCells            = 0;
  int             yCells            = 0;
  double          cellWidth         = 0.0;
  double          cellHeight        = 0.0;
  double          xOrigin           = 0.0;
  double          yOrigin           = 0.0;
  Mask            activationLayer   = null;

  /*
   * only set in a subgrid of the grid having the parent ID.
   */
  int[]           offsetInSupergrid = null;
  String          superGridId       = null;

  public Grid() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public Iterator<Cell> iterator() {

    return new Iterator<Cell>() {

      int n = 0;

      @Override
      public boolean hasNext() {
        return n < getCellCount();
      }

      @Override
      public Cell next() {
        return getCell(n++);
      }

      @Override
      public void remove() {}

    };
  }

  @Override
  public int getYCells() {
    return yCells;
  }

  @Override
  public int getXCells() {
    return xCells;
  }

  @Override
  public int getCellCount() {
    return xCells * yCells;
  }

  public Cell getCell(int index) {
    int[] xy = getXYOffsets(index);
    return new CellImpl(xy[0], xy[1]);
  }

  public Cell getCell(int x, int y) {
    return new CellImpl(x, y);
  }


  @Override
  public double getCellArea(IUnit unit) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public boolean isActive(int x, int y) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public int[] getGridCoordinatesAt(double x, double y) {
    int ofs = getOffsetFromWorldCoordinates(x, y);
    return getXYOffsets(ofs);
  }

  @Override
  public int getOffsetFromWorldCoordinates(double x, double y) {
    if (x < getEnvelope().getMinX() || x > getEnvelope().getMaxX() || y < getEnvelope().getMinY()
        || y > getEnvelope().getMaxY())
      return -1;
    int xx =
        (int) (((x - getEnvelope().getMinX()) / (getEnvelope().getMaxX() - getEnvelope().getMinX()))
            * xCells);
    int yy =
        (int) (((y - getEnvelope().getMinY()) / (getEnvelope().getMaxY() - getEnvelope().getMinY()))
            * yCells);
    return getIndex(xx, yy);
  }

  @Override
  public double[] getWorldCoordinatesAt(int x, int y) {
    double x1 = getEnvelope().getMinX() + (cellWidth * x);
    double y1 = getEnvelope().getMinY() + (cellHeight * (getYCells() - y - 1));
    return new double[] {x1 + (cellWidth / 2), y1 + (cellHeight / 2)};
  }

  public int getIndex(int x, int y) {
    if (xCells != 0) {
      return (y * xCells) + x;
    }
    return 0;
  }

  @Override
  public double[] getCoordinates(int index) {
    int[] xy = getXYOffsets(index);
    return getWorldCoordinatesAt(xy[0], xy[1]);
  }

  @Override
  public Locator getLocator(int x, int y) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public double getCellWidth() {
    return cellWidth;
  }

  @Override
  public double getCellHeight() {
    return cellHeight;
  }

  @Override
  public Shape getShape() {
    return Shape.create(getEast(), getSouth(), getWest(), getNorth(), projection);
  }


  /**
   * Return a new grid with compatible geometry to this but limited to the bounding box of the
   * passed shape, which must be included in our envelope. Also return an array with the x and y
   * offsets of the grid within the original.
   * 
   * @param shape
   * @return
   * @throws KlabException
   */
  public IGrid cutToShape(Shape shape) throws KlabException {

    com.vividsolutions.jts.geom.Envelope genv =
        Envelope.create(getEast(), getWest(), getSouth(), getNorth(), projection).envelope;
    com.vividsolutions.jts.geom.Envelope senv = shape.geometry.getEnvelope().getEnvelopeInternal();

    if (!genv.covers(senv)) {
      return null;
    }

    /*
     * adjusts envelope boundaries to cover original cells exactly
     */
    double gxmin = senv.getMinX();
    double gxmax = senv.getMaxX();
    double dx = gxmax - gxmin;
    double gymin = senv.getMinY();
    double gymax = senv.getMaxY();
    double dy = gymax - gymin;

    int nx = (int) (dx / getCellWidth());
    int ny = (int) (dy / getCellHeight());

    if ((nx * getCellWidth()) < dx) {
      nx++;
      gxmin -= (getCellWidth() / 2);
      gxmax += (getCellWidth() / 2);
    }
    if ((ny * getCellHeight()) < dy) {
      ny++;
      gymin -= (getCellHeight() / 2);
      gymax += (getCellHeight() / 2);
    }

    int xofs = (int) ((gxmin - getEast()) / getCellWidth());
    int yofs = (int) ((gymin - getSouth()) / getCellHeight());

    Grid ret = new Grid(shape, nx, ny);
    ret.offsetInSupergrid = new int[] {xofs, yofs};
    ret.superGridId = getSignature();

    ret.createActivationLayer(shape);

    return ret;
  }

  private void setResolution(int xCells, int yCells) {
    this.xCells = xCells;
    this.yCells = yCells;
    this.cellWidth = getEnvelope().getWidth() / xCells;
    this.cellHeight = getEnvelope().getHeight() / yCells;
  }

  private void createActivationLayer(Shape shape) {
    // TODO Auto-generated method stub

  }

  public Collection<Integer> getNeumannNeighbors(int xcell, int ycell) {
    ArrayList<Integer> ret = new ArrayList<>();

    if (inRange(xcell - 1, ycell))
      ret.add(getOffset(xcell - 1, ycell));
    if (inRange(xcell + 1, ycell))
      ret.add(getOffset(xcell + 1, ycell));
    if (inRange(xcell, ycell - 1))
      ret.add(getOffset(xcell, ycell - 1));
    if (inRange(xcell, ycell + 1))
      ret.add(getOffset(xcell, ycell + 1));

    return ret;
  }

  public Collection<Integer> getMooreNeighbors(int xcell, int ycell) {

    ArrayList<Integer> ret = (ArrayList<Integer>) getNeumannNeighbors(xcell, ycell);

    if (inRange(xcell - 1, ycell - 1))
      ret.add(getOffset(xcell - 1, ycell - 1));
    if (inRange(xcell + 1, ycell))
      ret.add(getOffset(xcell + 1, ycell + 1));
    if (inRange(xcell, ycell - 1))
      ret.add(getOffset(xcell + 1, ycell - 1));
    if (inRange(xcell, ycell + 1))
      ret.add(getOffset(xcell - 1, ycell + 1));

    return ret;
  }

  public boolean inRange(int x, int y) {
    return x >= 0 && x < getXCells() && y >= 0 && y < getYCells();
  }

  public boolean isCovered(int granule) {
    if (activationLayer == null) {
      return true;
    }
    return activationLayer.isActive(granule);
  }

  @Override
  public int[] getXYOffsets(int index) {
    int xx = index % getXCells();
    int yy = getYCells() - (index / getXCells()) - 1;
    return new int[] {xx, yy};
  }

  public static int[] getXYCoordinates(int index, int width, int height) {
    int xx = index % width;
    int yy = /* height - ( */index / width/* ) - 1 */;
    return new int[] {xx, yy};
  }

  @Override
  public int getOffset(int x, int y) {
    return ((yCells - y - 1) * xCells) + x;
  }

  public String getSignature() {
    return "grid," + getWest() + "," + getSouth() + "," + getEast() + "," + getNorth() + ","
        + getXCells() + "," + getYCells() + projection.getCode();
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof Grid && ((Grid) obj).getSignature().equals(getSignature());
  }

  @Override
  public int hashCode() {
    return getSignature().hashCode();
  }

  public String getParentGridId() {
    return superGridId;
  }

  public int[] getOffsetInParentGrid() {
    return offsetInSupergrid;
  }

  @Override
  public double snapX(double xCoordinate, int direction) {
    int steps = (int) Math.floor((xCoordinate - getEast()) / getCellWidth());
    if (direction == RIGHT && steps < getXCells() - 1) {
      steps++;
    }
    return getEast() + (getCellWidth() * steps);
  }

  @Override
  public double snapY(double yCoordinate, int direction) {
    int steps = (int) Math.floor((yCoordinate - getSouth()) / getCellHeight());
    if (direction == TOP && steps < getYCells() - 1) {
      steps++;
    }
    return getSouth() + (getCellHeight() * steps);
  }

  public int getConnectionCount() {
    return connectionCount(xCells, yCells);
  }

  public CellPositionClass classifyCellPosition(int x, int y) {

    if (x == 0 || y == 0) {
      if (x == 0) {
        return y == 0 ? CellPositionClass.SW_CORNER
            : (y == (yCells - 1) ? CellPositionClass.SE_CORNER : CellPositionClass.S_EDGE);
      }
      if (y == 0) {
        /*
         * we know x != 0
         */
        return x == (xCells - 1) ? CellPositionClass.NW_CORNER : CellPositionClass.W_EDGE;
      }
    } else if (x == (xCells - 1) || y == (yCells - 1)) {
      if (x == (xCells - 1)) {
        return y == (yCells - 1) ? CellPositionClass.NE_CORNER : CellPositionClass.E_EDGE;
      } else {
        return CellPositionClass.N_EDGE;
      }
    }
    return CellPositionClass.INTERNAL;

  }

  public Orientation[] getPossibleConnections(int x, int y) {

    switch (classifyCellPosition(x, y)) {
      case E_EDGE:
        return eEdge;
      case INTERNAL:
        return mooreNeighborhood;
      case NE_CORNER:
        return neCorner;
      case NW_CORNER:
        return nwCorner;
      case N_EDGE:
        return nEdge;
      case SE_CORNER:
        return seCorner;
      case SW_CORNER:
        return swCorner;
      case S_EDGE:
        return sEdge;
      case W_EDGE:
        return wEdge;
      default:
        break;
    }
    return null;
  }


}
