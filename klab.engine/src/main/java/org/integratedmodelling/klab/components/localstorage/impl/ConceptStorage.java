package org.integratedmodelling.klab.components.localstorage.impl;

import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.kim.api.data.ILocator;
import org.integratedmodelling.kim.utils.Utils;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.knowledge.IConcept;

public class ConceptStorage extends Storage implements IDataArtifact {

  public ConceptStorage(IGeometry scale) {
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
  public long set(ILocator index, Object value) {
    // TODO Auto-generated method stub
    return 0;
  }
  
  @Override
  public <T> T get(ILocator index, Class<T> cls) {
    return Utils.asType(get(index), cls);
  }
  

}
