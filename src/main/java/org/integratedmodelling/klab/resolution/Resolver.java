package org.integratedmodelling.klab.resolution;

import org.integratedmodelling.klab.api.resolution.ICoverage;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.observation.DirectObservation;
import org.integratedmodelling.klab.owl.Observable;

public enum Resolver {
  
  INSTANCE;
  
  public ICoverage resolve(DirectObservation observation, ResolutionScope scope) {
      return Coverage.empty();
  }
  
  public ICoverage resolve(Observable observable, ResolutionScope scope) {
      return Coverage.empty();
  }
  
  public ICoverage resolve(Model observable, ResolutionScope scope) {
    return Coverage.empty();
}
}
