package org.integratedmodelling.klab.components.runtime.observations;

import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.ILocator;
import org.integratedmodelling.klab.components.runtime.RuntimeContext;
import org.integratedmodelling.klab.observation.Scale;
import org.integratedmodelling.klab.owl.Observable;

public class State extends Observation implements IState {

  IDataArtifact storage;

  public State(Observable observable, Scale scale, RuntimeContext context, IDataArtifact data) {
    super(observable, scale, context);
    this.storage = data;
  }

  private static final long serialVersionUID = -7075415960868285693L;
  
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
  public State as(IObservable observable) {
    // TODO Auto-generated method stub
    return null;
  }
  
  public void setValue(Object value, long offset) {
    // TODO create storage lazily if not there; if observable is numeric and value is a
    // distribution, set up for
    // that or promote to probabilistic.
  }

  @Override
  public State next() {
    return (State)super.next();
  }

  public Object get(ILocator index) {
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

  public long size() {
    return storage.size();
  }
}
