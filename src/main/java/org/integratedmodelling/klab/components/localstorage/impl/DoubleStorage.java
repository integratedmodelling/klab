package org.integratedmodelling.klab.components.localstorage.impl;

import org.integratedmodelling.klab.api.data.raw.IStorage;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.ILocator;
import org.integratedmodelling.klab.data.AbstractObservationData;
import org.integratedmodelling.klab.observation.Scale;

public class DoubleStorage extends AbstractObservationData implements IStorage<Double> {

  public DoubleStorage(IObservable observable, Scale scale) {
      super(observable);
  }

  @Override
  public long size() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public Double get(ILocator index) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void set(ILocator index, Object value) {
    // TODO Auto-generated method stub
    
  }
  
}
