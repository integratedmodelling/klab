package org.integratedmodelling.klab.resolution;

import org.integratedmodelling.klab.Models;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.api.services.IModelService.RankedModel;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.observation.DirectObservation;
import org.integratedmodelling.klab.owl.Observable;

/**
 * The pattern of use for the different resolvers is:
 * 
 * <ul>
 * <li>Call the function with the scope of the outer layer</li>
 * <li>Within the function, create a child scope for the resolved object and use it for any further
 * resolution</li>
 * <li>Merge any successful resolution within the child scope</li>
 * <li>Return the child scope, or scope.empty() in case of failure</li>
 * </ul>
 * 
 * @author ferdinando.villa
 *
 */
public enum Resolver {

  INSTANCE;

  public ResolutionScope resolve(DirectObservation observation, ResolutionScope scope)
      throws KlabException {

//    ResolutionScope ret = scope.get(observation, Mode.RESOLUTION);
//    if (resolve(observation.getObservable(), ret).isRelevant()) {
//      ret = scope.merge(ret);
//    }
    return scope;
  }

  public ResolutionScope resolve(Observable observable, ResolutionScope scope) {

//    for (RankedModel model : Models.INSTANCE.resolve(observable, scope)) {
//      if (scope.or(resolve(model, scope)).isComplete()) {
//        break;
//      }
//    }
    return scope;
  }

  public ResolutionScope resolve(IModel model, ResolutionScope scope) {

//    for (IObservable observable : model.getDependencies()) {
//      if (scope.and(resolve(model, ret)).isEmpty()) {
//        if (!observable.isOptional()) {
//          break;
//        }
//      }
//    }
    return scope;
  }
}
