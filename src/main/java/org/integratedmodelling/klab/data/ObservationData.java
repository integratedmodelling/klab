package org.integratedmodelling.klab.data;

import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.raw.IObservationData;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.ILocator;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.observation.Scale;

/**
 * Simple abstract observation data class for storage components. Just implements the basic iterator
 * functions and access to simple final fields.
 * 
 * @author Ferd
 *
 */
public abstract class ObservationData implements IObservationData {

  private IObservable semantics;
  private IGeometry geometry;
  private IRuntimeContext runtimeContext;
  IMetadata metadata = new Metadata();

  protected ObservationData(IObservable semantics, IRuntimeContext context) {
    this.semantics = semantics;
    this.runtimeContext = context;
  }

  @Override
  public boolean hasNext() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public IObservationData next() {
    // TODO Auto-generated method stub
    return null;
  }

  public void chain(IObservationData data) {
    // TODO
  }

  @Override
  public IObservable getSemantics() {
    return semantics;
  }

  @Override
  public IGeometry getGeometry() {
    return geometry;
  }

  @Override
  public IMetadata getMetadata() {
    return metadata;
  }

  public IRuntimeContext getRuntimeContext() {
    return this.runtimeContext;
  }

  public void setRuntimeContext(IRuntimeContext runtimeContext) {
    this.runtimeContext = runtimeContext;
  }

}
