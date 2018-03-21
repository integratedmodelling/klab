package org.integratedmodelling.klab.components.localstorage;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IStorageProvider;
import org.integratedmodelling.klab.api.data.raw.IStorage;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.components.localstorage.impl.BooleanStorage;
import org.integratedmodelling.klab.components.localstorage.impl.ConceptStorage;
import org.integratedmodelling.klab.components.localstorage.impl.DoubleStorage;
import org.integratedmodelling.klab.observation.Scale;

@Component(id = "local.storage", version = Version.CURRENT)
public class LocalStorageComponent implements IStorageProvider {

  public LocalStorageComponent() {
    // TODO Auto-generated constructor stub
    // TODO install reaper for any leftover storage
  }

  @Override
  public IStorage<?> createStorage(IObservable observable, IScale scale, IComputationContext context) {
    switch (observable.getObservationType()) {
      case CLASSIFICATION:
        return new ConceptStorage(observable, scale);
      case QUANTIFICATION:
        return new DoubleStorage(observable, scale);
      case VERIFICATION:
        return new BooleanStorage(observable, scale);
      case INSTANTIATION:
      case SIMULATION:
      case DETECTION:
      default:
        throw new IllegalArgumentException("illegal observable for state storage: " + observable);
    }
  }

}
