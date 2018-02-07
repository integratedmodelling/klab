package org.integratedmodelling.klab.resolution;

import org.integratedmodelling.klab.Models;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.services.IModelService.RankedModel;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.observation.DirectObservation;
import org.integratedmodelling.klab.owl.Observable;

public enum Resolver {

  INSTANCE;

  public Coverage resolve(DirectObservation observation, ResolutionScope scope) throws KlabException {

    Coverage ret = Coverage.empty();
    
    for (RankedModel model : Models.INSTANCE.resolve(observation.getObservable(), scope)) {
      if ((ret = ret.or(resolve(model, scope))).isComplete()) {
        break;
      }
    }
    
    return ret;
  }

  public Coverage resolve(Observable observable, ResolutionScope scope) {
    return Coverage.empty();
  }

  public Coverage resolve(IModel observable, ResolutionScope scope) {
    return Coverage.empty();
  }
}
