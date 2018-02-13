package org.integratedmodelling.klab.resolution;

import java.util.ArrayList;
import java.util.Collection;
import org.integratedmodelling.klab.api.resolution.ICoverage;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.model.Namespace;
import org.integratedmodelling.klab.model.Observer;
import org.integratedmodelling.klab.observation.Scale;
import org.integratedmodelling.klab.observation.Subject;
import org.integratedmodelling.klab.owl.Observable;

public class ResolutionScope extends Coverage implements IResolutionScope {

  private Observer           observer;
  private Observable         observable;
  private Collection<String> scenarios       = new ArrayList<>();
  private DependencyGraph    dependencyGraph = new DependencyGraph();
  private Model              model;
  private Namespace          resolutionNamespace;
  private Mode               mode            = Mode.RESOLUTION;
  private boolean            interactive;
  private IMonitor           monitor;

  /**
   * Get a root scope based on the definition of an observation.
   * 
   * @param observer
   * @param monitor
   * @param scenarios
   * @return
   * @throws KlabException
   */
  public static ResolutionScope create(Observer observer, IMonitor monitor,
      Collection<String> scenarios) throws KlabException {
    return new ResolutionScope(observer, monitor, scenarios);
  }

  /**
   * Get a root scope with the scale of an existing subject used as a context for the next
   * observations.
   * 
   * @param observer
   * @param monitor
   * @param scenarios
   * @return
   * @throws KlabException
   */
  public static ResolutionScope create(Subject observer, IMonitor monitor,
      Collection<String> scenarios) throws KlabException {
    return new ResolutionScope(observer, monitor, scenarios);
  }


  private ResolutionScope(Subject observer, IMonitor monitor, Collection<String> scenarios)
      throws KlabException {
    super(observer.getScale());
    this.scenarios.addAll(scenarios);
    this.resolutionNamespace = observer.getNamespace();
    this.monitor = monitor;

    /*
     * TODO must instantiate any pre-existing observables already resolved in the subject if they
     * are to be used to resolve lower-level states.
     */
  }

  private ResolutionScope(Observer observer, IMonitor monitor, Collection<String> scenarios)
      throws KlabException {
    super(Scale.create(observer.getBehavior().getExtents(monitor)));
    this.scenarios.addAll(scenarios);
    this.resolutionNamespace = observer.getNamespace();
    this.observer = observer;
    this.monitor = monitor;
    /*
     * TODO instantiate all pre-existing states mentioned in the observer
     */
  }

  private ResolutionScope(ResolutionScope other) {
    super(other);
    this.observable = other.observable;
    this.scale = other.scale;
    this.scenarios.addAll(other.scenarios);
    this.model = other.model;
    this.resolutionNamespace = other.resolutionNamespace;
    this.mode = other.mode;
    this.interactive = other.interactive;
    this.monitor = other.monitor;
  }

  public static final ResolutionScope empty() {
    return new ResolutionScope();
  }

  public static ICoverage full(Scale scale) {
    return new Coverage(scale, 1.0);
  }

  public ResolutionScope() {
    super(null, 0.0);
  }

  /**
   * Create a child coverage for a passed observable with the same scale but initial coverage set at
   * 0.
   * 
   * @param observable
   * @param mode
   * @return
   */
  public ResolutionScope getChildScope(Observable observable, Mode mode) {
    ResolutionScope ret = new ResolutionScope(this);
    ret.observable = observable;
    ret.mode = mode;
    ret.setCoverage(0);
    return ret;
  }

  public ResolutionScope getChildScope(Model model) throws KlabException {

    ResolutionScope ret = new ResolutionScope(this);
    ret.model = model;
    ret.resolutionNamespace = (Namespace) model.getNamespace();
    /*
     * If the model has its own coverage, set the scale of the child scope to the intersection of
     * the scales
     * 
     * TODO coverages should be separated for the various scale dimensions, and which ones get
     * intersected should depend on the geometry that the model handles.
     */
    if (model.getBehavior().hasScale()) {
      ret = ret.and(new Coverage(Scale.create(model.getBehavior().getExtents(this.monitor))));
    }

    /*
     * TODO add any pre-existing observables with values to the set of observed ones
     */

    return this;
  }

  public ResolutionScope getChildScope(Observer observer) throws KlabException {

    ResolutionScope ret = new ResolutionScope(this);
    ret.observer = observer;
    ret.resolutionNamespace = (Namespace) observer.getNamespace();
    /*
     * If the model has its own coverage, set the scale of the child scope to the intersection of
     * the scales
     * 
     * TODO coverages should be separated for the various scale dimensions, and which ones get
     * intersected should depend on the geometry that the model handles.
     */
    if (observer.getBehavior().hasScale()) {
      ret = ret.and(new Coverage(Scale.create(model.getBehavior().getExtents(this.monitor))));
    }

    /*
     * TODO empty the pre-existing observables and reinstantiate all the states predefined in the
     * observer
     */

    return this;
  }

  @Override
  public boolean resolves(Observable observable) {
    return false;
  }

  @Override
  public Collection<String> getScenarios() {
    return scenarios;
  }

  public Model getModel() {
    return model;
  }

  @Override
  public Namespace getResolutionNamespace() {
    return resolutionNamespace;
  }

  @Override
  public Mode getMode() {
    return mode;
  }

  @Override
  public boolean isInteractive() {
    return interactive;
  }

  public Observable getObservable() {
    return observable;
  }

  @Override
  public IMonitor getMonitor() {
    return monitor;
  }

  /**
   * Merge an accepted child scope (which has, in turn, been merged before this is called). The
   * final result of the merge should be a scope tree where each scope specifies either:
   * 
   * <ul>
   * <li>an observation (observer for acknowledged or observable for non-countable) + a resolver
   * model (optional for acknowledged countables); or</li>
   * <li>a countable observable + an instantiator model</li>
   * </ul>
   * 
   * Each sibling scope can be turned into an independently executable actuator. Actuators for child
   * scopes are executed in depth-first order.
   * 
   * @param childScope
   * @return
   */
  ResolutionScope merge(ResolutionScope childScope) {
    // TODO

    /*
     * Accept the observation and merge in the model if any
     */

    /*
     * Record the observable among the resolved for this scope.
     */

    return this;
  }

  public Observer getObserver() {
    return observer;
  }

  /**
   * If passed coverage is relevant, OR the coverage with a copy of ours and merge the dependencies.
   */
  @Override
  public ResolutionScope or(ICoverage child) throws KlabException {
    if (child.isRelevant()) {
      // FIXME this accomplishes nothing
      super.or(child);
      // TODO
    }
    return this;
  }

  /**
   * If passed coverage is relevant, AND the coverage with ours and merge the dependencies.
   */
  @Override
  public ResolutionScope and(ICoverage child) throws KlabException {
    if (child.isRelevant()) {
      // FIXME this accomplishes nothing
      super.and(child);
      // TODO
    }

    return this;
  }

  public DependencyGraph getDependencyGraph() {
    return dependencyGraph;
  }

  public Observable findObservable() {
    if (observable != null) {
      return observable;
    } else if (observer != null) {
      return observer.getObservable();
    } else if (model != null) {
      return (Observable) model.getObservables().get(0);
    }
    return null;
  }

}
