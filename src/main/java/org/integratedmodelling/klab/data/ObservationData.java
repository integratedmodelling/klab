package org.integratedmodelling.klab.data;

import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.raw.IObjectData;
import org.integratedmodelling.klab.api.data.raw.IObservationData;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;

/**
 * Simple abstract observation data class for storage components. Just implements the basic iterator
 * functions and access to simple final fields.
 * 
 * @author Ferd
 *
 */
public abstract class ObservationData implements IObservationData {

  private IGeometry       geometry;
  private IRuntimeContext runtimeContext;
  IMetadata               metadata = new Metadata();
  private IObjectData     parent;

  protected ObservationData(IGeometry geometry, IRuntimeContext context) {
    this.geometry = geometry;
    this.runtimeContext = context;
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

  public IObjectData getParent() {
    return parent;
  }

  public void chain(IObservationData data) {
  }

}
