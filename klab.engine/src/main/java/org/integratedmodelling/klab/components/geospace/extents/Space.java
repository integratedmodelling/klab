package org.integratedmodelling.klab.components.geospace.extents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.api.data.IGeometry.Dimension;
import org.integratedmodelling.kim.api.data.ILocator;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.ITopologicallyComparable;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IProjection;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.components.geospace.api.IGrid;
import org.integratedmodelling.klab.components.geospace.api.IGrid.Cell;
import org.integratedmodelling.klab.components.geospace.api.ISpatialIndex;
import org.integratedmodelling.klab.components.geospace.api.ITessellation;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.scale.Extent;
import org.integratedmodelling.klab.scale.Scale.Mediator;
import org.integratedmodelling.klab.utils.collections.IterableAdapter;
import org.integratedmodelling.klab.utils.collections.IteratorAdapter;

public class Space extends Extent implements ISpace {

  private Shape         shape;
  private Grid          grid;
  private Envelope      envelope;
  private Projection    projection;
  private ITessellation features;
  private boolean       consistent  = false;
  private String        gridSpecs   = null;

  private static Space  EMPTY_SPACE = new Space(Shape.empty());

  public static Space create(Shape shape) {
    return new Space(shape);
  }

  public static final Space empty() {
    return EMPTY_SPACE;
  }

  public static Space create(Shape shape, double resolutionInMeters) throws KlabException {
    Grid grid = Grid.create(shape, resolutionInMeters);
    Space ret = new Space(shape, grid);
    ret.gridSpecs = resolutionInMeters + " m";
    return ret;
  }

  private Space() {
    //
  }

  private Space(Space extent) {
    this.shape = extent.shape;
    this.grid = extent.grid;
    this.envelope = extent.envelope;
    this.projection = extent.projection;
    this.gridSpecs = extent.gridSpecs;
  }

  private Space(Shape shape, Grid grid) {

    this.projection = shape.getProjection();
    this.shape = shape;
    if (grid != null) {
      this.grid = Grid.create(this.shape, grid.getXCells(), grid.getYCells());
      if (grid instanceof Grid) {
        this.grid.offsetInSupergrid = ((Grid) grid).offsetInSupergrid;
        this.grid.superGridId = ((Grid) grid).superGridId;
      }
      this.envelope = ((Grid) grid).getEnvelope();
    } else {
      this.envelope = this.shape.getEnvelope();
    }
  }

  public String toString() {
    return "<SPACE " + (grid != null ? grid.toString() : "")
        + (features != null ? features.toString() : "") + " @" + shape + ">";
  }

  private Space(Shape shape) {
    this.shape = shape;
    this.projection = shape.getProjection();
    this.envelope = shape.getEnvelope();
    this.consistent = true;
  }

  @Override
  public int getScaleRank() {
    return envelope.getScaleRank();
  }

  @Override
  public IExtent collapse() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isCovered(long stateIndex) {
    // TODO Auto-generated method stub
    return true;
  }

  @Override
  public IExtent merge(IExtent extent) throws KlabException {

    if (!(extent instanceof Space)) {
      throw new KlabValidationException("space extent cannot merge non-space extent");
    }

    Space ret = new Space(this);
    Space oth = (Space) extent;

    /*
     * TODO figure out mandatory vs. not. These are all false, which probably shouldn't be - either
     * pass to merge or be smarter.
     */
    // if (oth.grid != null) {
    // ret.set(oth.grid, force);
    // } else if (oth.features != null) {
    // ret.set(oth.features, oth.shape, force);
    // } else if (oth.shape != null) {
    // ret.set(oth.shape, force);
    // } else if (oth.gridResolution > 0.0) {
    // ret.setGridResolution(oth.gridResolution, force);
    // }
    return ret;
  }


  @Override
  public boolean isConsistent() {
    return consistent;
  }

  @Override
  public boolean isEmpty() {
    return shape == null || shape.isEmpty();
  }

  @Override
  public long[] getDimensionSizes() {
    if (features != null) {
      return new long[] {features.size()};
    } else if (grid != null) {
      return new long[] {grid.getXCells(), grid.getYCells()};
    }
    return new long[] {1};
  }

  @Override
  public long getOffset(long[] dimOffsets) {
    if (features != null) {
      if (dimOffsets.length != 1) {
        throw new IllegalArgumentException(
            "can't address offset: tessellation space has one dimension");
      }
      return dimOffsets[0];
    }
    if (grid != null) {
      if (dimOffsets.length != 2) {
        throw new IllegalArgumentException("can't address offset: grid space has two dimensions");
      }
      return grid.getOffset(dimOffsets[0], dimOffsets[1]);
    }
    if (dimOffsets.length != 1 || dimOffsets[0] != 0) {
      throw new IllegalArgumentException(
          "can't address offset: shape space has one dimension and one extent");
    }
    return 0;
  }

  @Override
  public long[] getDimensionOffsets(long linearOffset) {
    // useless but was a a parameter in 0.9.x - see if it still serves any purpose before removing
    boolean rowFirst = true;
    if (features != null) {
      return new long[] {linearOffset};
    } else if (grid != null) {
      if (rowFirst) {
        long[] xy = grid.getXYOffsets(linearOffset);
        return new long[] {xy[1], xy[0]};
      }
      return grid.getXYOffsets(linearOffset);
    }
    return new long[] {0};
  }

  // @Override
  // public long locate(Locator locator) {
  //
  // if (locator instanceof SpaceLocator) {
  // if (locator.isAll())
  // return GENERIC_LOCATOR;
  // if (grid != null) {
  // if (((SpaceLocator) locator).isLatLon()) {
  // return grid.getOffsetFromWorldCoordinates(((SpaceLocator) locator).lon,
  // ((SpaceLocator) locator).lat);
  // } else {
  // return grid.getOffset(((SpaceLocator) locator).x, ((SpaceLocator) locator).y);
  // }
  // }
  // }
  // return INAPPROPRIATE_LOCATOR;
  // }

  @Override
  public Mediator getMediator(IExtent extent, IObservable observable, IConcept trait) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public long size() {
    if (grid != null) {
      return grid.getCellCount();
    }
    if (features != null) {
      return features.size();
    }
    return 1;
  }

  public IExtent intersection(ITopologicallyComparable<?> obj) throws KlabException {


    Space ret = new Space(this);

    if (this.shape == null) {
      return ret;
    }

    Shape other = null;
    if (obj instanceof Space) {
      other = ((Space) obj).getShape();
    } else if (obj instanceof Shape) {
      other = (Shape) obj;
    }

    if (other == null) {
      return new Space();
    }

    Shape common = this.shape.intersection(other);

    // TODO adapt grid, features

    return new Space(common);
  }

  public IExtent union(ITopologicallyComparable<?> obj) throws KlabException {

    Space ret = new Space(this);

    if (this.shape == null) {
      return ret;
    }

    Shape other = null;
    if (obj instanceof Space) {
      other = ((Space) obj).getShape();
    } else if (obj instanceof Shape) {
      other = (Shape) obj;
    }

    if (other == null) {
      return new Space();
    }

    Shape common = this.shape.union(other);

    // TODO adapt grid, features

    return new Space(common);
  }

  @Override
  public boolean contains(IExtent other) throws KlabException {
    if (!(other instanceof Space)) {
      throw new IllegalArgumentException(
          "cannot union a space extent with a " + other.getClass().getCanonicalName());
    }
    Space e = (Space) other;
    return shape == null || e.shape == null ? false : shape.geometry.contains(e.shape.geometry);
  }

  @Override
  public boolean overlaps(IExtent other) throws KlabException {
    if (!(other instanceof Space)) {
      throw new IllegalArgumentException(
          "cannot overla[ a space extent with a " + other.getClass().getCanonicalName());
    }
    Space e = (Space) other;
    return shape == null || e.shape == null ? false : shape.geometry.overlaps(e.shape.geometry);
  }

  @Override
  public boolean intersects(IExtent other) throws KlabException {
    if (!(other instanceof Space)) {
      throw new IllegalArgumentException(
          "cannot intersect a space extent with a " + other.getClass().getCanonicalName());
    }
    Space e = (Space) other;
    return shape == null || e.shape == null ? false : shape.geometry.intersects(e.shape.geometry);
  }

  @Override
  public double getCoveredExtent() {
    return getShape().getStandardizedGeometry().getArea();
  }

  @Override
  public Iterator<IExtent> iterator() {
    if (grid != null) {
      return new IteratorAdapter<IExtent>(grid.iterator());
    }
    if (features != null) {
      return features.iterator();
    }
    return shape.iterator();
  }

  @Override
  public ISpace getExtent() {
    return create(getShape());
  }

  @Override
  public IEnvelope getEnvelope() {
    return envelope;
  }

  @Override
  public IProjection getProjection() {
    return projection;
  }

  // @Override
  public Optional<IGrid> getGrid() {
    // TODO Auto-generated method stub
    return grid == null ? Optional.empty() : Optional.of(grid);
  }

  // @Override
  public Optional<ITessellation> getTessellation() {
    // TODO Auto-generated method stub
    return features == null ? Optional.empty() : Optional.of(features);
  }

  @Override
  public Shape getShape() {
    return shape;
  }

  // @Override
  public ISpatialIndex getIndex(boolean makeNew) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Space copy() {
    // TODO Auto-generated method stub
    // TODO REMEMBER TO ALSO COPY THE SCALEID
    return null;
  }

  @Override
  public ISpace getExtent(long stateIndex) {
    if (this.size() == 1 && stateIndex == 0) {
      return this;
    }
    if (grid != null) {
      return grid.getCell(stateIndex);
    }
    if (features != null) {
      return features.getFeature(stateIndex);
    }
    return null;
  }

  @Override
  public IServiceCall getKimSpecification() {
    List<Object> args = new ArrayList<>(4);
    args.add("shape");
    args.add(shape.toString());
    if (gridSpecs != null) {
      args.add("grid");
      args.add(gridSpecs);
    }
    return new KimServiceCall("space", args.toArray());
  }

  @Override
  public double getCoverage() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public Type getType() {
    return Dimension.Type.SPACE;
  }

  @Override
  public boolean isRegular() {
    return features == null;
  }

  @Override
  public int getDimensionality() {
    return 2;
  }

  @Override
  public ISpace at(ILocator locator) {
    // TODO Auto-generated method stub
    return null;
  }

  // @Override
  public Iterable<ILocator> over(Type dimension) {
    if (dimension != Dimension.Type.SPACE) {
      throw new IllegalArgumentException("cannot iterate a spatial extent over " + dimension);
    }
    return grid != null ? new IterableAdapter<ILocator>(grid)
        : (features != null ? new IterableAdapter<ILocator>(features)
            : Collections.singleton(shape));
  }

  @Override
  public long[] shape() {
    if (this.grid != null) {
      return new long[] {this.grid.getXCells(), this.grid.getYCells()};
    }
    if (this.features != null) {
      return new long[] {this.features.size()};
    }
    return shape.shape();
  }

  @Override
  public long getOffset(ILocator index) {
    // TODO support latlon, shape
    if (this.grid != null) {
      if (index instanceof Cell) {
        return this.grid.getOffset((Cell) index);
      }
    }
    throw new IllegalArgumentException("cannot use " + index + " as a space locator");
  }

  @Override
  public IExtent merge(ITopologicallyComparable<?> other, LogicalConnector how) {
    if (how == LogicalConnector.UNION) {
      return union(other);
    } else if (how == LogicalConnector.INTERSECTION) {
      return intersection(other);
    }
    throw new IllegalArgumentException("cannot merge a spatial extent with " + other);
  }

  @Override
  public IParameters getParameters() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String encode() {
    if (grid != null) {
      return "S2(" + grid.getXCells() + "," + grid.getYCells() + "){bounds=["
          + grid.getEnvelope().getMinX() + " " + grid.getEnvelope().getMaxX() + " "
          + grid.getEnvelope().getMinX() + " " + grid.getEnvelope().getMaxY() + "],shape="
          + getShape().getWKB() + "}";
    } else if (features != null) {
      return "s1(" + features.size() + ")";
    }
    return getShape().encode();
  }

}
