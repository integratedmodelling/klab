package org.integratedmodelling.klab.resolution;

import java.util.Collection;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.Models;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.resolution.IPrioritizer;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.api.resolution.IResolvable;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IModelService.IRankedModel;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.model.Observer;
import org.integratedmodelling.klab.owl.Observable;

/**
 * The resolver provides methods to find the observation strategy for any {@link IResolvable}
 * object. All state during resolution is held in the associate {@link IResolutionScope}.
 * 
 * @author ferdinando.villa
 *
 */
public enum Resolver {

  INSTANCE;

  /**
   * Resolve the passed object in the passed scope, using the resolution strategy appropriate to the
   * type.
   * 
   * @param resolvable
   * @param scope
   * @return the resolved scope
   * @throws KlabException
   */
  public ResolutionScope resolve(IResolvable resolvable, ResolutionScope scope)
      throws KlabException {

    if (resolvable instanceof Observable) {
      return resolve((Observable) resolvable, scope,
          ((Observable) resolvable).is(Type.COUNTABLE) ? Mode.INSTANTIATION : Mode.RESOLUTION);
    } else if (resolvable instanceof Model) {
      return resolve((Model) resolvable, scope);
    } else if (resolvable instanceof Observer) {
      return resolve((Observer) resolvable, scope);
    }

    return ResolutionScope.empty();
  }

  /**
   * Resolve the root observer to an acknowledged observation tree. This being an acknowledgement,
   * coverage will always be 100% unless errors happen.
   * 
   * @param observer
   * @param monitor
   * @param scenarios
   * @return the scope, with the new subject in it.
   * @throws KlabException
   */
  public ResolutionScope resolve(Observer observer, IMonitor monitor, Collection<String> scenarios)
      throws KlabException {

    ResolutionScope ret = ResolutionScope.create(observer, monitor, scenarios);
    if (resolve(observer.getObservable(), ret, Mode.RESOLUTION).isRelevant()) {
      return ret;
    }
    return ResolutionScope.empty();
  }

  /**
   * Resolve an observer in a previously existing context.
   * 
   * @param observer
   * @param parentScope
   * @return the merged scope
   * @throws KlabException
   */
  public ResolutionScope resolve(Observer observer, ResolutionScope parentScope) throws KlabException {

    // Subject subject;
    // try {
    // subject = Observations.INSTANCE.createSubject(observer, scope.getMonitor());
    // } catch (KlabException e) {
    // scope.getMonitor().error("error creating subject for " + observer + ": " + e.getMessage());
    // return ResolutionScope.empty();
    // }
    //// subject.setContextObservation(scope.getSubject());
    ResolutionScope ret =
        resolve(observer.getObservable(), parentScope.getChildScope(observer), Mode.RESOLUTION);
    if (ret.isRelevant()) {
      parentScope.merge(ret);
    }
    return ret;
  }

  /**
   * Resolve an observable in a context by accepting as many models as necessary to resolve its
   * observation or instantiate the target observations. Final coverage is the OR of the coverage of
   * all models found; lookup of models stops when coverage is complete.
   * 
   * @param observable
   * @param parentScope
   * @param mode
   * @return the scope with any child scopes for the models and the coverage of the resolved
   *         observable. If resolution is unsuccessful, return a scope with no children, with empty
   *         coverage if the observable is mandatory, or the passed scope's coverage if it's
   *         optional.
   */
  public ResolutionScope resolve(Observable observable, ResolutionScope parentScope, Mode mode) {

    ResolutionScope ret = parentScope.getChildScope(observable, mode);
    ResolutionScope modelScope = null;
    try {
      for (IRankedModel model : Models.INSTANCE.resolve(observable, parentScope)) {
        ResolutionScope mret = resolve((RankedModel) model, parentScope);
        modelScope = modelScope == null ? mret : modelScope.or(mret);
        if (modelScope.isComplete()) {
          break;
        }
      }
    } catch (KlabException e) {
      parentScope.getMonitor().error("error during resolution of " + observable + ": " + e.getMessage());
      return ResolutionScope.empty();
    }

    if (modelScope == null) {
      ret = ResolutionScope.empty();
    } else if (modelScope.isRelevant()) {
      ret.merge(modelScope);
    }

    return ret.isEmpty() && observable.isOptional() ? parentScope : ret;
  }

  /**
   * Resolve a model's dependencies. Final coverage is the AND of the resolved dependencies.
   * 
   * @param model
   * @param parentScope
   * @return the merged scope, or an empty one.
   * @throws KlabException
   */
  public ResolutionScope resolve(Model model, ResolutionScope parentScope) throws KlabException {

    ResolutionScope ret = parentScope.getChildScope(model);
    for (IObservable observable : model.getDependencies()) {
      ResolutionScope dep = resolve((Observable) observable, ret,
          observable.is(Type.COUNTABLE) ? Mode.INSTANTIATION : Mode.RESOLUTION);
      try {
        ret = ret.and(dep);
      } catch (KlabException e) {
        parentScope.getMonitor()
            .error("error during resolution of " + observable + ": " + e.getMessage());
        return ResolutionScope.empty();
      }
    }

    if (ret.isRelevant()) {
      parentScope.merge(ret);
    }

    return ret;
  }

  /**
   * Retrieve an appropriately configured model prioritizer for the passed scope.
   * 
   * @param context
   * @return
   */
  public IPrioritizer<org.integratedmodelling.klab.data.rest.resources.Model> getPrioritizer(
      ResolutionScope context) {
    return new Prioritizer(context);
  }
}
