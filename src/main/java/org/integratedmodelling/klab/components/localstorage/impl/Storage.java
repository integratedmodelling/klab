package org.integratedmodelling.klab.components.localstorage.impl;

import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.data.Metadata;

public class Storage {

  IMetadata metadata = new Metadata();
  IGeometry geometry;
  
  public Storage(IGeometry geometry) {
    this.geometry = geometry;
  }

  public IGeometry getGeometry() {
    return this.geometry;
  }
  
  public IMetadata getMetadata() {
    return this.metadata;
  }
}
