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
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.space.ISpatialIndex;
import org.integratedmodelling.klab.api.observations.scale.space.ITessellation;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.observation.Extent;

public class Space extends Extent implements ISpace {

  private IShape      shape;
  private Grid        grid;
  private IEnvelope   envelope;
  private IProjection projection;
  private int         scaleRank;

  public static Space create(IShape shape) {
    
    return null;
  }

  public static Space create(IShape shape, Grid grid) {
    return null;
  }

  public static Space create(IShape shape, double resolutionInMeters) {
    return null;
  }

  private Space() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public int getScaleRank() {
    return scaleRank;
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
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isConsistent() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isEmpty() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public int[] getDimensionSizes() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int[] getDimensionOffsets(int linearOffset, boolean rowFirst) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int locate(Locator locator) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public Mediator getMediator(IExtent extent, IObservable observable, IConcept trait) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public long getMultiplicity() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public IExtent intersection(IExtent other) throws KlabException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IExtent union(IExtent other) throws KlabException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean contains(IExtent o) throws KlabException {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean overlaps(IExtent o) throws KlabException {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean intersects(IExtent o) throws KlabException {
    // TODO Auto-generated method stub
    return false;
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
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ISpace getExtent(int stateIndex) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IEnvelope getEnvelope() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IProjection getProjection() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Optional<IGrid> getGrid() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Optional<ITessellation> getTessellation() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IShape getShape() {
    // TODO Auto-generated method stub
    return null;
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
