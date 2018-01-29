package org.integratedmodelling.klab.resolution;

import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.resolution.ICoverage;
import org.integratedmodelling.klab.observation.DirectObservation;

public enum Resolver {
  
  INSTANCE;
  
  public ICoverage resolve(DirectObservation observation, ResolutionScope scope) {
      return Coverage.empty();
  }
  
  public ICoverage resolve(IObservable observable, ResolutionScope scope) {
      return Coverage.empty();
  }
  
}
