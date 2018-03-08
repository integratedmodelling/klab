package org.integratedmodelling.klab.components.localstorage.impl;

import org.integratedmodelling.klab.api.data.raw.IStorage;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.ILocator;
import org.integratedmodelling.klab.data.AbstractObservationData;
import org.integratedmodelling.klab.observation.Scale;

public class BooleanStorage extends AbstractObservationData implements IStorage<Boolean> {

  public BooleanStorage(IObservable observable, Scale scale) {
      super(observable);
  }

  @Override
  public long size() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public Boolean get(ILocator index) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void set(ILocator index, Object value) {
    // TODO Auto-generated method stub
    
  }


}
