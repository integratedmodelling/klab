package org.integratedmodelling.klab.components.geospace.extents;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.observations.scale.ExtentDimension;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScaleMediator;
import org.integratedmodelling.klab.api.observations.scale.ITopologicallyComparable;
import org.integratedmodelling.klab.api.observations.scale.space.Direction;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IProjection;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.space.ISpaceLocator;
import org.integratedmodelling.klab.api.observations.scale.space.Orientation;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.components.geospace.api.IGrid;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.rest.SpatialExtent;
import org.integratedmodelling.klab.scale.AbstractExtent;
import org.integratedmodelling.klab.utils.Pair;

/**
 * 
 * @author Ferd
 * @author Ioannis
 */
public class Grid extends Area implements IGrid {

	/**
	 * Positions a cell may be on that differ in terms of the allowed neigborhoods
	 */
	public static enum CellPositionClass {
		SW_CORNER, SE_CORNER, NW_CORNER, NE_CORNER, S_EDGE, E_EDGE, N_EDGE, W_EDGE, INTERNAL
	}

	/**
	 * A trivial grid mask with almost no memory footpring that automatically
	 * graduates to a proper one when any operation sets a cell inactive.
	 * 
	 * @author ferdinando.villa
	 *
	 */
	class FullMask implements Mask {

		Mask delegate = null;
		IShape shape;

		FullMask(IShape shape) {
			this.shape = shape;
		}

		Mask getDelegate() {
			this.delegate = new GridMask(Grid.this, shape);
			return this.delegate;
		}

		@Override
		public void merge(Mask other, LogicalConnector connector) {
			getDelegate().merge(other, connector);
		}

		@Override
		public boolean isActive(long x, long y) {
			return delegate == null ? true : delegate.isActive(x, y);
		}

		@Override
		public void activate(long x, long y) {
			if (delegate != null) {
				delegate.activate(x, y);
			}
		}

		@Override
		public void deactivate(long x, long y) {
			getDelegate().deactivate(x, y);
		}

		@Override
		public long totalActiveCells() {
			return delegate == null ? Grid.this.getCellCount() : delegate.totalActiveCells();
		}

		@Override
		public long nextActiveOffset(long fromOffset) {
			return delegate == null ? (fromOffset >= Grid.this.getCellCount() ? -1 : fromOffset)
					: delegate.nextActiveOffset(fromOffset);
		}

		@Override
		public void invert() {
			getDelegate().invert();
		}

		@Override
		public void deactivate() {
			getDelegate().deactivate();
		}

		@Override
		public void activate() {
			if (delegate != null) {
				delegate.activate();
			}
		}

	}

	/**
	 * Directions accessible from each corner
	 */
	static Orientation[] swCorner = new Orientation[] { Orientation.N, Orientation.NE, Orientation.E };
	static Orientation[] seCorner = new Orientation[] { Orientation.W, Orientation.NW, Orientation.N };
	static Orientation[] nwCorner = new Orientation[] { Orientation.S, Orientation.SE, Orientation.E };
	static Orientation[] neCorner = new Orientation[] { Orientation.W, Orientation.SW, Orientation.S };

	/**
	 * Directions accessible from each edge
	 */
	static Orientation[] sEdge = new Orientation[] { Orientation.W, Orientation.NW, Orientation.N, Orientation.NE,
			Orientation.E };
	static Orientation[] eEdge = new Orientation[] { Orientation.S, Orientation.SW, Orientation.W, Orientation.NW,
			Orientation.N };
	static Orientation[] nEdge = new Orientation[] { Orientation.W, Orientation.SW, Orientation.S, Orientation.SE,
			Orientation.E };
	static Orientation[] wEdge = new Orientation[] { Orientation.S, Orientation.SE, Orientation.E, Orientation.NE,
			Orientation.N };

	static Orientation[] mooreNeighborhood = new Orientation[] { Orientation.W, Orientation.NW, Orientation.N,
			Orientation.NE, Orientation.E, Orientation.SE, Orientation.S, Orientation.SW };

	static int connectionCount(long n, long m) {
		return (int) (((n - 2) * (m - 2) * 8) + // connections in internal cells
				(2 * (n - 2) + 2 * (m - 2)) * 5 + // connections in edge cells
				(4 * 3)); // connections in corner cells
	}

	public static Grid create(Shape shape, double resolutionInMeters) throws KlabException {
		return new Grid(shape, resolutionInMeters);
	}

	public static Grid create(Shape shape, long x, long y) throws KlabException {
		return new Grid(shape, x, y);
	}

	/**
	 * @param gxmin
	 * @param gymin
	 * @param gxmax
	 * @param gymax
	 * @param nx
	 * @param ny
	 * @param projection
	 * @return grid
	 */
	public static Grid create(double gxmin, double gymin, double gxmax, double gymax, long nx, long ny,
			Projection projection) {
		return new Grid(gxmin, gymin, gxmax, gymax, nx, ny, projection);
	}

	/**
	 * Create a grid from a shape in the CRS of the shape and using the given
	 * resolution for the larger extent.
	 * 
	 * @param shape
	 * @param resolutionInMeters
	 * @throws KlabException
	 */
	private Grid(Shape shape, double resolutionInMeters) throws KlabException {
		super(shape);
		setAdjustedEnvelope(shape, resolutionInMeters);
		mask = createMask(shape);
	}

	/*
	 * Make a trivial mask unless the shape differs from its envelope
	 */
	public Mask createMask(Shape shape) {
		return this.mask = getCellCount() < 36 || shape.equals(shape.getEnvelope().asShape()) ? new FullMask(shape)
				: new GridMask(this, shape);
	}

	/**
	 * Create a grid extent with a rasterized shape in the given grid. Will not clip
	 * the shape or anything: use ONLY when you already know the precise aspect
	 * factor for the extent resulting from the shape.
	 * 
	 * @param shape
	 * @param x
	 * @param y
	 * @throws KlabException
	 */
	private Grid(Shape shape, long x, long y) throws KlabException {
		super(shape);
		this.setResolution(x, y);
		mask = createMask(shape);
	}

	private Grid(double x1, // lonLowerBound
			double y1, // latLowerBound
			double x2, // lonUpperBound
			double y2, // latUpperBound
			long xDivs, long yDivs, Projection projection) {

		this.xOrigin = x1;
		this.yOrigin = y1;
		this.envelope = Envelope.create(x1, x2, y1, y2, projection);
		this.shape = envelope.asShape();
		this.projection = projection;
		setResolution(xDivs, yDivs);
	}

	public String toString() {
		return "<GRID [" + xCells + "," + yCells + "] " + envelope + ">";
	}

	public Grid copy() {
		return create(getShape().copy(), getXCells(), getYCells());
	}

	public class CellImpl extends AbstractExtent implements Cell {

		long x;
		long y;

		Shape shape;

		CellImpl(long x, long y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public long getX() {
			return x;
		}

		@Override
		public long getY() {
			return y;
		}

		@Override
		public Cell N() {
			if (y > 0) {
				return new CellImpl(x, y - 1);
			}
			return null;
		}

		@Override
		public Cell S() {
			long trow = getYCells() - 1;
			if (y < trow) {
				return new CellImpl(x, y + 1);
			}
			return null;
		}

		@Override
		public Cell E() {
			long tcol = getXCells() - 1;
			if (x < tcol) {
				return new CellImpl(x + 1, y);
			}
			return null;
		}

		@Override
		public Cell W() {
			if (x > 0) {
				return new CellImpl(x - 1, y);
			}
			return null;
		}

		@Override
		public Cell NW() {
			if (y > 0 && x > 0) {
				return new CellImpl(x - 1, y - 1);
			}
			return null;
		}

		@Override
		public Cell NE() {
			long tcol = getXCells() - 1;
			if (y > 0 && x < tcol) {
				return new CellImpl(x + 1, y - 1);
			}
			return null;
		}

		@Override
		public Cell SE() {
			long tcol = getXCells() - 1;
			long trow = getYCells() - 1;
			if (y < trow && x < tcol) {
				return new CellImpl(x + 1, y + 1);
			}
			return null;
		}

		@Override
		public Cell SW() {
			long trow = getYCells() - 1;
			if (y < trow && x > 0) {
				return new CellImpl(x - 1, y + 1);
			}
			return null;
		}

		@Override
		public Cell getNeighbor(long xOfs, long yOfs) {
			long tx = x + xOfs;
			long ty = y + yOfs;
			if (tx >= 0 && tx < getXCells() && ty >= 0 && ty < getYCells()) {
				return new CellImpl(tx, ty);
			}
			return null;
		}

		@Override
		public String toString() {
			return "<cell " + getWest() + "," + getEast() + " - " + getSouth() + "," + getNorth() + ">";
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
			final long prime = 31;
			long result = 1;
			result = prime * result + x;
			result = prime * result + y;
			return (int) result;
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
		public Cell move(long xOfs, long yOfs) {
			long newX = x + xOfs;
			long newY = y + yOfs;
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
		public long[] getDimensionOffsets(long linearOffset) {
			if (linearOffset != 0) {
				throw new IllegalArgumentException("0-dimensional extents don't use offset addressing");
			}
			return new long[] { 0 };
		}

		@Override
		public long getOffset(long[] dimOffsets) {
			if (dimOffsets.length != 1 && dimOffsets[0] != 0) {
				throw new IllegalArgumentException("0-dimensional extents don't use offset addressing");
			}
			return 0;
		}

		@Override
		public double getWest() {
			return Grid.this.getWest() + (x * getCellWidth());
		}

		@Override
		public double getEast() {
			return this.getWest() + getCellWidth();
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
		public Long getOffsetInGrid() {
			return Grid.this.getOffset(x, y);
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
			long xo = Math.abs(cell.getX() - x);
			long yo = Math.abs(cell.getY() - y);
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
		public Shape getShape() {
			if (this.shape == null) {
				this.shape = Shape.create(getWest(), getSouth(), getEast(), getNorth(), projection);
			}
			return this.shape;
		}

		@Override
		public IEnvelope getEnvelope() {
			return getShape().getEnvelope();
		}

		@Override
		public IProjection getProjection() {
			return projection;
		}

		@Override
		public ISpace at(ILocator locator) {
			return getShape().at(locator);
		}

		@Override
		public int getScaleRank() {
			return getShape().getScaleRank();
		}

		@Override
		public IExtent collapse() {
			return getShape().collapse();
		}

		@Override
		public IExtent merge(IExtent extent) throws KlabException {
			return getShape().merge(extent);
		}

		@Override
		public double getCoverage() {
			return 1;
		}

		@Override
		public long size() {
			return 1;
		}

		@Override
		public boolean contains(IExtent o) throws KlabException {
			return getShape().contains(o);
		}

		@Override
		public boolean overlaps(IExtent o) throws KlabException {
			return getShape().overlaps(o);
		}

		@Override
		public boolean intersects(IExtent o) throws KlabException {
			return getShape().intersects(o);
		}

		@Override
		public double getCoveredExtent() {
			return getShape().getStandardizedGeometry().getArea();
		}

		@Override
		public Iterator<IExtent> iterator() {
			return Collections.singleton((IExtent) this).iterator();
		}

		@Override
		public Type getType() {
			return Type.SPACE;
		}

		@Override
		public boolean isRegular() {
			return true;
		}

		@Override
		public int getDimensionality() {
			return 2;
		}

		@Override
		public long[] shape() {
			return new long[] { 1, 1 };
		}

		@Override
		public long getOffset(ILocator index) {
			if (index instanceof CellImpl && this.equals(index)) {
				return 0;
			}
			throw new IllegalArgumentException("cannot use " + index + " as a cell locator");
		}

		@Override
		public IExtent getExtent() {
			return this;
		}

		@Override
		public IExtent merge(ITopologicallyComparable<?> other, LogicalConnector how) {
			return getShape().merge(other, how);
		}

		@Override
		public IParameters<String> getParameters() {
			return baseDimension.getParameters();
		}

		@Override
		public String encode() {
			return "S2(1,1){shape=" + ((Shape) getShape()).getWKB() + ",proj=" + getProjection().getSimpleSRS() + "}";
		}

		@Override
		public IScaleMediator getMediator(IExtent extent) {
			return getShape().getMediator(extent);
		}

		@Override
		public IExtent mergeCoverage(IExtent other, LogicalConnector connector) {
			return getShape().merge(other, connector);
		}

		@Override
		public AbstractExtent copy() {
			return getShape().copy();
		}

		@Override
		public IServiceCall getKimSpecification() {
			return getShape().getKimSpecification();
		}

		@Override
		public SpatialExtent getExtentDescriptor() {
			return getShape().getExtentDescriptor();
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> T as(Class<T> cls) {
			if (ISpaceLocator.class.isAssignableFrom(cls)) {
				SpaceLocator ret = new SpaceLocator(getX(), getY(), getOffsetInGrid());
				ret.setWorldCoordinates(getEast() + (getEast() - getWest()) / 2.,
						getSouth() + (getNorth() - getSouth()) / 2.);
				return (T) ret;
			}
			return null;
		}

		@Override
		public double getStandardizedVolume() {
			return getFirstCell().getStandardizedVolume();
		}

		@Override
		public double getStandardizedArea() {
			return getFirstCell().getStandardizedArea();
		}

		@Override
		public double getStandardizedWidth() {
			return getFirstCell().getStandardizedWidth();
		}

		@Override
		public double getStandardizedHeight() {
			return getFirstCell().getStandardizedHeight();
		}

		@Override
		public double getStandardizedDepth() {
			return getFirstCell().getStandardizedDepth();
		}

		@Override
		public double getStandardizedLength() {
			return getFirstCell().getStandardizedLength();
		}

		@Override
		public double getStandardizedDistance(ISpace space) {
			return getShape().getStandardizedDistance(space.getShape());
		}

		@Override
		public boolean isGeneric() {
			return false;
		}

		@Override
		public boolean isEmpty() {
			// a cell is never an empty extent
			return false;
		}

        @Override
        public IExtent getBoundingExtent() {
            return getShape();
        }

        @Override
        public ExtentDimension getExtentDimension() {
            return ExtentDimension.AREAL;
        }

		@Override
		public Pair<Double, IUnit> getStandardizedDimension(ILocator locator) {
			return new Pair<>(getStandardizedArea(), Units.INSTANCE.SQUARE_METERS);
		}
	}

	Shape shape;
	long xCells = 0;
	long yCells = 0;
	double cellWidth = 0.0;
	double cellHeight = 0.0;
	double xOrigin = 0.0;
	double yOrigin = 0.0;
	protected Mask mask = null;

	/*
	 * only set in a subgrid of the grid having the parent ID.
	 */
	long[] offsetInSupergrid = null;
	String superGridId = null;

	// computed once on demand and used to efficiently return shape data in meters
	private IShape firstCell;

	public Grid() {
		// TODO Auto-generated constructor stub
	}

	private IShape getFirstCell() {
		if (firstCell == null) {
			firstCell = getCell(0).getShape();
		}
		return firstCell;
	}

	@Override
	public Iterator<Cell> iterator() {

		return new Iterator<Cell>() {

			long n = mask == null ? 0 : mask.nextActiveOffset(0);

			@Override
			public boolean hasNext() {
				if (mask == null) {
					return n < (getCellCount() - 1);
				}
				return n >= 0;
			}

			@Override
			public Cell next() {
				if (mask == null) {
					return getCell(n++);
				}
				Cell ret = getCell(n);
				n = mask.nextActiveOffset(n + 1);
				return ret;
			}

			@Override
			public void remove() {
			}

		};
	}

	@Override
	public long getYCells() {
		return yCells;
	}

	@Override
	public long getXCells() {
		return xCells;
	}

	@Override
	public long getCellCount() {
		return xCells * yCells;
	}

	public Cell getCell(long index) {
		long[] xy = getXYOffsets(index);
		return new CellImpl(xy[0], xy[1]);
	}

	public Cell getCell(long x, long y) {
		return new CellImpl(x, y);
	}

	@Override
	public boolean isActive(long x, long y) {
		return isCovered(getOffset(x, y));
	}

	@Override
	public long[] getGridCoordinatesAt(double x, double y) {
		long ofs = getOffsetFromWorldCoordinates(x, y);
		return getXYOffsets(ofs);
	}

	@Override
	public long getOffsetFromWorldCoordinates(double x, double y) {
		if (x < getEnvelope().getMinX() || x > getEnvelope().getMaxX() || y < getEnvelope().getMinY()
				|| y > getEnvelope().getMaxY())
			return -1;
		long xx = (long) (((x - getEnvelope().getMinX()) / (getEnvelope().getMaxX() - getEnvelope().getMinX()))
				* xCells);
		long yy = (long) (((y - getEnvelope().getMinY()) / (getEnvelope().getMaxY() - getEnvelope().getMinY()))
				* yCells);
		return getIndex(xx, yy);
	}

	@Override
	public double[] getWorldCoordinatesAt(long x, long y) {
		double x1 = getEnvelope().getMinX() + (cellWidth * x);
		double y1 = getEnvelope().getMinY() + (cellHeight * (getYCells() - y - 1));
		return new double[] { x1 + (cellWidth / 2), y1 + (cellHeight / 2) };
	}

	public long getIndex(long x, long y) {
		if (xCells != 0) {
			return (y * xCells) + x;
		}
		return 0;
	}

	@Override
	public double[] getCoordinates(long index) {
		long[] xy = getXYOffsets(index);
		return getWorldCoordinatesAt(xy[0], xy[1]);
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
		if (shape == null) {
			shape = Shape.create(getWest(), getSouth(), getEast(), getNorth(), projection);
		}
		return shape;
	}

	/**
	 * Return a new grid with compatible geometry to this but limited to the
	 * bounding box of the passed shape, which must be included in our envelope.
	 * Also return an array with the x and y offsets of the grid within the
	 * original.
	 * 
	 * @param shape
	 * @return new grid
	 * @throws KlabException
	 */
	public IGrid cutToShape(Shape shape) throws KlabException {

		com.vividsolutions.jts.geom.Envelope genv = Envelope.create(getEast(), getWest(), getSouth(), getNorth(),
				projection).envelope;
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

		long nx = (long) (dx / getCellWidth());
		long ny = (long) (dy / getCellHeight());

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

		long xofs = (long) ((gxmin - getEast()) / getCellWidth());
		long yofs = (long) ((gymin - getSouth()) / getCellHeight());

		Grid ret = new Grid(shape, nx, ny);
		ret.offsetInSupergrid = new long[] { xofs, yofs };
		ret.superGridId = getSignature();

		ret.createActivationLayer(shape);

		return ret;
	}

	private void setResolution(long xCells, long yCells) {
		this.xCells = xCells;
		this.yCells = yCells;
		this.cellWidth = getEnvelope().getWidth() / xCells;
		this.cellHeight = getEnvelope().getHeight() / yCells;
	}

	private void createActivationLayer(Shape shape) {
		// TODO Auto-generated method stub

	}

	public Collection<Long> getNeumannNeighbors(long xcell, long ycell) {
		ArrayList<Long> ret = new ArrayList<>();

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

	public Collection<Long> getMooreNeighbors(long xcell, long ycell) {

		ArrayList<Long> ret = (ArrayList<Long>) getNeumannNeighbors(xcell, ycell);

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

	public boolean inRange(long x, long y) {
		return x >= 0 && x < getXCells() && y >= 0 && y < getYCells();
	}

	public boolean isCovered(long granule) {
		long[] xy = getXYOffsets(granule);
		return mask == null ? true : mask.isActive(xy[0], xy[1]);
	}

	@Override
	public long[] getXYOffsets(long index) {
		long xx = index % getXCells();
		long yy = getYCells() - (index / getXCells()) - 1;
		return new long[] { xx, yy };
	}

	public static long[] getXYCoordinates(long index, long xCells, long yCells) {
		long xx = index % xCells;
		long yy = yCells - (index / xCells) - 1;
		return new long[] { xx, yy };
	}

	@Override
	public long getOffset(long x, long y) {
		return ((yCells - y - 1) * xCells) + x;
	}

	public String getSignature() {
		return "grid," + getWest() + "," + getSouth() + "," + getEast() + "," + getNorth() + "," + getXCells() + ","
				+ getYCells() + getProjection().getCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj == this || (obj instanceof Grid && ((Grid) obj).getSignature().equals(getSignature()));
	}

	@Override
	public int hashCode() {
		return getSignature().hashCode();
	}

	public String getParentGridId() {
		return superGridId;
	}

	public long[] getOffsetInParentGrid() {
		return offsetInSupergrid;
	}

	@Override
	public double snapX(double xCoordinate, Direction direction) {
		long steps = (long) Math.floor((xCoordinate - getWest()) / getCellWidth());
		if (direction == Direction.RIGHT && steps > 0 && steps < getXCells() - 1) {
			steps++;
		}
		return getWest() + (getCellWidth() * steps);
	}

	@Override
	public double snapY(double yCoordinate, Direction direction) {
		long steps = (long) Math.floor((yCoordinate - getSouth()) / getCellHeight());
		if (direction == Direction.TOP && steps > 0 && steps < getYCells() - 1) {
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

	/**
	 * Set the envelope from shape.
	 * 
	 * @param shape
	 *            could be in any CRS.
	 * @param squareSize
	 *            should be in meters
	 * @throws KlabException
	 */
	private void setAdjustedEnvelope(Shape shape, double squareSize) throws KlabException {
		long x = 0, y = 0;
		// double dx = 0, dy = 0;
		Envelope env = shape.getEnvelope();

		// Case one: both CRS and square size are in meters.
		if (shape.getProjection().isMeters()) {

			double height = env.getHeight();
			double width = env.getWidth();

			x = (long) Math.ceil(width / squareSize);
			y = (long) Math.ceil(height / squareSize);

			// dx = (x * squareSize) - width;
			// dy = (y * squareSize) - height;

			// ReferencedEnvelope envelop_ = new ReferencedEnvelope(env.getMinX() - dx,
			// env.getMaxX() + dx,
			// env.getMinY() - dy, env.getMaxY() + dy,
			// shape.getProjection().getCoordinateReferenceSystem());

			// After doing the calculations in the original CRS,
			// shape and envelope is transformed to default.

			// this.shape = shape.transform(Projection.getDefault());
			// try {
			// this.envelope = Envelope
			// .create(envelop_.transform(Projection.getDefault().getCoordinateReferenceSystem(),
			// true));
			// } catch (Exception e) {
			// // shouldn't happen
			// throw new KlabValidationException(e);
			// }
			// this.projection = Projection.getDefault();

		}
		// Case 2: CRS uses degrees
		else {
			// get height and width in meters
			double height = Projection.distance(env.getMinY(), env.getMinX(), env.getMaxY(), env.getMinX());
			double width = Projection.distance(env.getMinY(), env.getMinX(), env.getMinY(), env.getMaxX());
			x = (long) Math.ceil(width / squareSize);
			y = (long) Math.ceil(height / squareSize);

			// Here I tried to adjust further dx and dy based on the size of the
			// grid cell
			// at the center of the Grid. Possibly not right either, so I left
			// it out.
			// double centralX = (env.getMaxX() + env.getMinX())/2;
			// double stepX = (env.getMaxX() - env.getMinX())/x;
			//
			// double centralY = (env.getMaxY() + env.getMinY())/2;
			// double stepY = (env.getMaxY() - env.getMinY())/x;
			//
			// double actualStepWidth = Grid.haversine(centralY,centralX,
			// centralY,centralX+stepX);
			//
			// double actualStepHeight = Grid.haversine(centralY, centralX,
			// centralY+stepY,centralX);
			// dx = stepX * (squareSize/actualStepWidth) / 2;
			// dy = stepY * (squareSize/actualStepHeight) / 2;

		}

		// if (!this.projection.equals(Projection.getDefault())) {
		// this.shape = this.shape.transform(Projection.getDefault());
		// try {
		// this.envelope = this.envelope.transform(Projection.getDefault(), true);
		// } catch (Exception e) {
		// // TODO: Shouldn't happen
		// e.printStackTrace();
		// }
		// this.projection = Projection.getDefault();
		// }

		this.setResolution(x, y);

		// activationLayer = Rasterizer.createMask(shape, this);
	}

	@Override
	public Projection getProjection() {
		return getShape().getProjection();
	}

	/**
	 * Return offset of cell if cell is mine, otherwise throw an illegal arg
	 * exception. Only handles the non-mediation case.
	 * 
	 * @param index
	 * @return the offset of the cell
	 */
	public long getOffset(Cell index) {
		if (index instanceof CellImpl && ((CellImpl) index).getGrid().equals(this)) {
			return ((CellImpl) index).getOffsetInGrid();
		} else {
			double[] wxy = ((CellImpl)index).getGrid().getWorldCoordinatesAt(index.getX(), index.getY());
			return this.getOffsetFromWorldCoordinates(wxy[0], wxy[1]);
		}
//		throw new IllegalArgumentException("grid: cannot use a cell from a different grid as a locator");
	}

	public Spliterator<Cell> spliterator(final IMonitor monitor) {
		return new SplIt(0, getCellCount(), monitor);
	}

	class SplIt implements Spliterator<Cell> {

		Grid splitGrid = Grid.this;
		long beginSplit;
		long endSplit = getCellCount();
		long counter;
		IMonitor monitor;

		public SplIt(long begin, long end, IMonitor monitor) {
			this.beginSplit = this.counter = begin;
			this.endSplit = end;
			this.monitor = monitor;
		}

		@Override
		public int characteristics() {
			// CHECK not SIZED | SUBSIZED because of nodata cells
			return NONNULL | CONCURRENT | IMMUTABLE;
		}

		@Override
		public long estimateSize() {
			return endSplit - beginSplit;
		}

		@Override
		public boolean tryAdvance(Consumer<? super Cell> arg0) {
			if (monitor.isInterrupted()) {
				return false;
			}
			if (counter < (endSplit - 1)) {
				arg0.accept(splitGrid.getCell(counter++));
				return true;
			}
			return false;
		}

		@Override
		public Spliterator<Cell> trySplit() {

			if (monitor.isInterrupted()) {
				return null;
			}
			if (estimateSize() > 16) {
				long midOfs = (endSplit - beginSplit) / 2;
				long end = this.endSplit;
				this.endSplit = beginSplit + midOfs;
				return new SplIt(beginSplit + midOfs, end, monitor);
			}
			return null;
		}
	};
}
