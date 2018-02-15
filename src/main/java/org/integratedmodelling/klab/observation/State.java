package org.integratedmodelling.klab.observation;

import org.integratedmodelling.klab.api.data.raw.IStorage;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale.Locator;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.owl.Observable;

public class State extends Observation implements IState {

  IStorage<?> storage;

  private State(Observable observable, Scale scale, Subject context, IMonitor monitor) {
    super(observable, scale, monitor);
    setContextObservation(context);
  }

  private static final long serialVersionUID = -7075415960868285693L;

  public static State create(Observable observable, Scale scale, Subject context,
      IMonitor monitor) {
    return new State(observable, scale, context, monitor);
  }

  @Override
  public IStorage<?> getStorage() {
    if (storage == null) {
      // TODO create it based on 
    }
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public long getValueCount() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public Object getValue(Locator locator) {
    // TODO Auto-generated method stub
    return null;
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
  public void addChangeListener(ChangeListener listener) {
    // TODO Auto-generated method stub

  }

  @Override
  public State as(IObservable observable) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public State next() {
    // TODO Auto-generated method stub
    return null;
  }

  public void setValue(Object value, long offset) {
    // TODO create storage lazily if not there; if observable is numeric and value is a
    // distribution, set up for
    // that or promote to probabilistic.
  }
}
