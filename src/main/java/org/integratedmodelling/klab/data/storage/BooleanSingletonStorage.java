package org.integratedmodelling.klab.data.storage;

import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.observation.Scale;

public class BooleanSingletonStorage extends AbstractSingletonStorage {

  public BooleanSingletonStorage(IObservable observable, Scale scale) {
    super(observable, scale);
  }

  @Override
  protected Boolean setValue(Object value) {
    return (Boolean)value;
  }

}
