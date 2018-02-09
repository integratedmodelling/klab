package org.integratedmodelling.klab.components.geospace.extents;

import java.util.Iterator;
import java.util.Optional;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IState.Mediator;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScale.Locator;
import org.integratedmodelling.klab.api.observations.scale.ITopologicallyComparable;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid;
import org.integratedmodelling.klab.api.observations.scale.space.IProjection;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.space.ISpatialIndex;
import org.integratedmodelling.klab.api.observations.scale.space.ITessellation;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabRuntimeException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.observation.Extent;

public class Space extends Extent implements ISpace {

  private Shape         shape;
  private Grid          grid;
  private Envelope      envelope;
  private Projection    projection;
  private ITessellation features;
  private boolean       consistent  = false;

  private static Space  EMPTY_SPACE = new Space(Shape.empty());

  public static Space create(Shape shape) {
    return new Space(shape);
  }

  public static Space create(Shape shape, double resolutionInMeters) throws KlabException {
    Grid grid = Grid.create(shape, resolutionInMeters);
    return new Space(shape, grid);
  }

  private Space() {
    //
  }

  private Space(Space extent) {
    this.shape = extent.shape;
    this.grid = extent.grid;
    this.envelope = extent.envelope;
    this.projection = extent.projection;
  }

  private Space(Shape shape, Grid grid) {

    this.projection = shape.getProjection();
    this.shape = shape;
    if (grid != null) {
      try {
        this.grid = Grid.create(this.shape, grid.getXCells(), grid.getYCells());
      } catch (KlabException e) {
        throw new KlabRuntimeException(e);
      }
      if (grid instanceof Grid) {
        this.grid.offsetInSupergrid = ((Grid) grid).offsetInSupergrid;
        this.grid.superGridId = ((Grid) grid).superGridId;
      }
      this.envelope = ((Grid) grid).getEnvelope();
    } else {
      this.envelope = this.shape.getEnvelope();
    }
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
  public IConcept getDomainConcept() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IExtent collapse() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isCovered(int stateIndex) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public IExtent merge(IExtent extent, boolean force) throws KlabException {

    if (!(extent instanceof Space)) {
      throw new KlabValidationException("space extent cannot merge non-space extent");
    }

    Space ret = new Space(this);
    Space oth = (Space) extent;

    /*
     * TODO figure out mandatory vs. not. These are all false, which probably shouldn't be - either
     * pass to merge or be smarter.
     */
//    if (oth.grid != null) {
//      ret.set(oth.grid, force);
//    } else if (oth.features != null) {
//      ret.set(oth.features, oth.shape, force);
//    } else if (oth.shape != null) {
//      ret.set(oth.shape, force);
//    } else if (oth.gridResolution > 0.0) {
//      ret.setGridResolution(oth.gridResolution, force);
//    }
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
  public int[] getDimensionSizes() {
    if (features != null) {
      return new int[] {features.size()};
    } else if (grid != null) {
      return new int[] {grid.getXCells(), grid.getYCells()};
    }
    return new int[] {1};
  }

  @Override
  public int[] getDimensionOffsets(int linearOffset, boolean rowFirst) {

    if (features != null) {
      return new int[] {linearOffset};
    } else if (grid != null) {
      if (rowFirst) {
        int[] xy = grid.getXYOffsets(linearOffset);
        return new int[] {xy[1], xy[0]};
      }
      return grid.getXYOffsets(linearOffset);
    }
    return new int[] {0};
  }

  @Override
  public int locate(Locator locator) {

    if (locator instanceof SpaceLocator) {
      if (locator.isAll())
        return GENERIC_LOCATOR;
      if (grid != null) {
        if (((SpaceLocator) locator).isLatLon()) {
          return grid.getOffsetFromWorldCoordinates(((SpaceLocator) locator).lon,
              ((SpaceLocator) locator).lat);
        } else {
          return grid.getOffset(((SpaceLocator) locator).x, ((SpaceLocator) locator).y);
        }
      }
    }
    return INAPPROPRIATE_LOCATOR;
  }

  @Override
  public Mediator getMediator(IExtent extent, IObservable observable, IConcept trait) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public long getMultiplicity() {
    if (grid != null) {
      return grid.getCellCount();
    }
    if (features != null) {
      return features.size();
    }
    return 1;
  }

  @Override
  public IExtent intersection(ITopologicallyComparable<?> other) throws KlabException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IExtent union(ITopologicallyComparable<?> other) throws KlabException {
    if (!(other instanceof Space)) {
      throw new IllegalArgumentException(
          "cannot union a space extent with a " + other.getClass().getCanonicalName());
    }
    Space e = (Space) other;
    // TODO
    return shape == null || e.shape == null ? EMPTY_SPACE : null;
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
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public Iterator<IExtent> iterator() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ISpace getExtent() {
    return create(getShape());
  }

  @Override
  public ISpace getExtent(int stateIndex) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IEnvelope getEnvelope() {
    return envelope;
  }

  @Override
  public IProjection getProjection() {
    return projection;
  }

  @Override
  public Optional<IGrid> getGrid() {
    // TODO Auto-generated method stub
    return grid == null ? Optional.empty() : Optional.of(grid);
  }

  @Override
  public Optional<ITessellation> getTessellation() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Shape getShape() {
    return shape;
  }

  @Override
  public ISpatialIndex getIndex(boolean makeNew) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Space copy() {
    // TODO Auto-generated method stub
    return null;
  }

}
