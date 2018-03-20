package org.integratedmodelling.klab.components.runtime.observations;

import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.raw.IObjectData;
import org.integratedmodelling.klab.api.data.raw.IStorage;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.ILocator;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.observation.Scale;
import org.integratedmodelling.klab.owl.Observable;

public class State<T> extends Observation implements IState<T> {

  IStorage<T> storage;

  private State(Observable observable, Scale scale, IStorage<T> data, Subject context, IMonitor monitor) {
    super(observable, scale, monitor);
    setContextObservation(context);
    this.storage = data;
  }

  private static final long serialVersionUID = -7075415960868285693L;

  public static State create(Observable observable, Scale scale, IStorage<?> data, Subject context,
      IMonitor monitor) {
    return new State(observable, scale, data, context, monitor);
  }

  @Override
  public IStorage<?> getData() {
      return storage;
  }

  @Override
  public boolean isConstant() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isDynamic() {
    return false;
  }

  @Override
  public State<T> as(IObservable observable) {
    // TODO Auto-generated method stub
    return null;
  }
  
  public void setValue(Object value, long offset) {
    // TODO create storage lazily if not there; if observable is numeric and value is a
    // distribution, set up for
    // that or promote to probabilistic.
  }

  @Override
  public State<?> next() {
    return (State<?>)super.next();
  }

  public T get(ILocator index) {
    return storage.get(index);
  }

  public void set(ILocator index, Object value) {
    storage.set(index, value);
  }

  public IGeometry getGeometry() {
    return storage.getGeometry();
  }

  public IMetadata getMetadata() {
    return storage.getMetadata();
  }

  public IObjectData getParent() {
    return storage.getParent();
  }

  public long size() {
    return storage.size();
  }
}
