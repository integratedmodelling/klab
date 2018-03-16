package org.integratedmodelling.klab.data.storage;

import java.util.Objects;
import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.kim.model.Geometry;
import org.integratedmodelling.klab.api.data.raw.IObservationData;
import org.integratedmodelling.klab.api.data.raw.IStorage;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.ILocator;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.observation.Scale;

public abstract class AbstractSingletonStorage<T> implements IStorage<T> {

  IObservable semantics;
  // we only store the scale to enable promotion to regular if needed
  Scale       scale;
  T           value;
  Metadata    metadata    = new Metadata();
  IStorage<T> delegate    = null;
  // value was set
  boolean     initialized = false;

  protected AbstractSingletonStorage(IObservable observable, Scale scale) {
    this.semantics = observable;
    this.scale = scale;
  }
  
  @Override
  public IObservable getSemantics() {
    return semantics;
  }

  @Override
  public IGeometry getGeometry() {
    return Geometry.scalar();
  }

  @Override
  public IMetadata getMetadata() {
    return metadata;
  }

  @Override
  public boolean hasNext() {
    return false;
  }

  @Override
  public IObservationData next() {
    return null;
  }

  @Override
  public T get(ILocator index) {
    return value;
  }

  @Override
  public void set(ILocator index, Object value) {
    if (this.initialized && !Objects.equals(this.value, value)) {
      // TODO the first different value should trigger promotion. Hard because we don't know what was set so far.
//      this.delegate = Klab.INSTANCE.getStorageProvider().createStorage(observable, scale);
//      this.delegate.set(index, value);
    }
    this.value = setValue(value);
//    this.initialized = true;
  }

  protected abstract T setValue(Object value);

  @Override
  public long size() {
    return scale.size();
  }


}
