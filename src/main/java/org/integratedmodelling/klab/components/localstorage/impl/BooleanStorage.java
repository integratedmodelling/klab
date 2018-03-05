package org.integratedmodelling.klab.components.localstorage.impl;

import org.integratedmodelling.klab.api.data.raw.IStorage;
import org.integratedmodelling.klab.api.observations.scale.IScale.Locator;
import org.integratedmodelling.klab.data.AbstractObservationData;

public class BooleanStorage extends AbstractObservationData implements IStorage<Boolean> {

  public BooleanStorage() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public Boolean get(long index) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void set(long index, Object value) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void set(Object data, Locator... locators) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public long size() {
    // TODO Auto-generated method stub
    return 0;
  }


}
