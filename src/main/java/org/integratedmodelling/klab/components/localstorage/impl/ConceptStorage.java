package org.integratedmodelling.klab.components.localstorage.impl;

import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.ILocator;
import org.integratedmodelling.klab.api.observations.scale.IScale;

public class ConceptStorage extends Storage implements IDataArtifact {

  public ConceptStorage(IObservable observable, IScale scale) {
      super(scale);
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
