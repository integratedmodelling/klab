package org.integratedmodelling.klab.components.localstorage;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IStorageProvider;
import org.integratedmodelling.klab.api.data.raw.IStorage;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.IScale;

@Component(id="local.storage", version=Version.CURRENT)
public class LocalStorageComponent implements IStorageProvider {

  public LocalStorageComponent() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public IStorage<?> createStorage(IObservable observable, IScale scale) {
    // TODO Auto-generated method stub
    return null;
  }

}
