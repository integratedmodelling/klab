package org.integratedmodelling.klab.resolution;

import java.util.Collection;
import java.util.Iterator;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.Models;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.resolution.IPrioritizer;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.api.resolution.IResolvable;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IModelService.IRankedModel;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.model.Observer;
import org.integratedmodelling.klab.observation.Scale;
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
   * Resolve the passed object in the passed parent scope, using the resolution strategy appropriate
   * to the type.
   * 
   * TODO expose the version that returns a dataflow, make this private
   * 
   * @param resolvable
   * @param parentScope
   * @return the resolved scope
   * @throws KlabException
   */
  public ResolutionScope resolve(IResolvable resolvable, ResolutionScope parentScope)
      throws KlabException {

    if (resolvable instanceof Observable) {
      return resolve((Observable) resolvable, parentScope,
          ((Observable) resolvable).is(Type.COUNTABLE) ? Mode.INSTANTIATION : Mode.RESOLUTION);
    } else if (resolvable instanceof Model) {
      return resolve((Model) resolvable, parentScope);
    } else if (resolvable instanceof Observer) {
      return resolve((Observer) resolvable, parentScope);
    }

    return parentScope.empty();
  }

  /**
   * Resolve a root observer to an acknowledged observation tree. This being an acknowledgement,
   * coverage will always be 100% unless errors happen.
   * 
   * TODO expose the version that returns a dataflow, make this private
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
    if (resolve(observer.getObservable(), ret, Mode.RESOLUTION).getCoverage().isRelevant()) {
      return ret;
    }
    return ret.empty();
  }

  /**
   * Resolve an observer in a previously existing context using passed mode and scale.
   * 
   * TODO expose the version that returns a dataflow, make this private
   * 
   * @param observable
   * @param parentScope
   * @param mode
   * @param scale
   * @return the merged scope
   * @throws KlabException
   */
  public ResolutionScope resolve(Observable observable, ResolutionScope parentScope, Mode mode,
      IScale scale) throws KlabException {

    ResolutionScope ret =
        resolve(observable, parentScope.getChildScope(observable, (Scale) scale), mode);
    if (ret.getCoverage().isRelevant()) {
      parentScope.merge(ret);
    }
    return ret;
  }

  /**
   * Resolve an observer in a previously existing context.
   * 
   * @param observer
   * @param parentScope
   * @return the merged scope
   * @throws KlabException
   */
  private ResolutionScope resolve(Observer observer, ResolutionScope parentScope)
      throws KlabException {

    ResolutionScope ret =
        resolve(observer.getObservable(), parentScope.getChildScope(observer), Mode.RESOLUTION);
    if (ret.getCoverage().isRelevant()) {
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
  private ResolutionScope resolve(Observable observable, ResolutionScope parentScope, Mode mode) {

    ResolutionScope ret = parentScope.getChildScope(observable, mode);

    // will be non-empty if this observable was resolved before, empty otherwise
    if (ret.getCoverage().isEmpty()) {

      ObservableReasoner reasoner = new ObservableReasoner(observable, ret);
      for (Iterator<Observable> it = reasoner.iterator(); it.hasNext();) {
        Observable candidate = it.next();
        try {
          for (IRankedModel model : Models.INSTANCE.resolve(candidate, ret)) {
            ResolutionScope mscope = resolve((RankedModel) model, ret);
            if (mscope.getCoverage().isRelevant() && ret.or(mscope)) {
              ret.link(mscope);
            }
            if (ret.getCoverage().isComplete()) {
              break;
            }
          }
        } catch (KlabException e) {
          parentScope.getMonitor()
              .error("error during resolution of " + candidate + ": " + e.getMessage());
          return parentScope.empty();
        }
      }
    }

    if (ret.getCoverage().isRelevant()) {
      parentScope.merge(ret);
    } else if (observable.isOptional()
        || (observable.is(Type.SUBJECT) && mode == Mode.RESOLUTION)) {
      /*
       * empty strategy is OK for optional dependencies and resolved subjects. The latter are never
       * resolved unless there has been an implicit instantiation from an instantiator, so a
       * dataflow that creates them is generated.
       */
      ret.acceptEmpty();
    }

    return ret;
  }

  /**
   * Resolve a model's dependencies. Final coverage is the AND of the resolved dependencies.
   * 
   * @param model
   * @param parentScope
   * @return the merged scope, or an empty one.
   * @throws KlabException
   */
  private ResolutionScope resolve(Model model, ResolutionScope parentScope) throws KlabException {

    ResolutionScope ret = parentScope.getChildScope(model);
    // use the reasoner to infer any missing dependency from the semantics
    ObservableReasoner reasoner = new ObservableReasoner(model, parentScope.getObservable());
    for (Observable observable : reasoner.getObservables()) {
      ret.and(
          resolve(observable, ret,
              observable.is(Type.COUNTABLE) ? Mode.INSTANTIATION : Mode.RESOLUTION));
      if (ret.getCoverage().isEmpty()) {
        break;
      }
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
