package org.integratedmodelling.klab.components.localstorage.impl;

import org.integratedmodelling.klab.api.data.raw.IStorage;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.ILocator;
import org.integratedmodelling.klab.data.ObservationData;
import org.integratedmodelling.klab.observation.Scale;

public class ConceptStorage extends ObservationData implements IStorage<IConcept> {

  public ConceptStorage(IObservable observable, Scale scale) {
      super(observable, null);
  }

  @Override
  public long size() {
    // TODO Auto-generated method stub
    return 0;
  }


  @Override
  public IConcept get(ILocator index) {
    // TODO Auto-generated method stub
    return null;
  }


  @Override
  public void set(ILocator index, Object value) {
    // TODO Auto-generated method stub
    
  }

}
