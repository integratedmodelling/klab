package org.integratedmodelling.klab.components.localstorage.impl;

import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.raw.IStorage;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.IScale.Locator;

public class BooleanStorage implements IStorage<Boolean> {

  public BooleanStorage() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public IGeometry getGeometry() {
    // TODO Auto-generated method stub
    return null;
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

  @Override
  public IObservable getSemantics() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IMetadata getMetadata() {
    // TODO Auto-generated method stub
    return null;
  }

}
