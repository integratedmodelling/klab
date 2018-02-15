package org.integratedmodelling.klab.resolution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
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
  private Subject            context;
  private ResolutionScope    parent;

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
    this.context = observer;
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
    this.parent = other;
    this.context = other.context;
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
   * @return a new scope for the passed observable
   */
  public ResolutionScope getChildScope(Observable observable, Mode mode) {
    ResolutionScope ret = new ResolutionScope(this);
    ret.observable = observable;
    ret.mode = mode;
    ret.setCoverage(0);
    return ret;
  }

  /**
   * 
   * @param model
   * @return a scope to resolve the passed model
   * @throws KlabException
   */
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

    return ret;
  }

  /**
   * 
   * @param observer
   * @return a scope to resolve the passed observer
   * @throws KlabException
   */
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

    return ret;
  }

  @Override
  public boolean resolves(Observable observable) {
    // TODO use the dependency graph
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
     * my coverage becomes the child's
     */
    this.coverage = childScope.coverage;
    
    /*
     * Record any resolved observables in the dependency diagram
     */
    for (IObservable o : getResolvedObservables()) {
      // 
    }

    return this;
  }

  /*
   * observables are actually resolved only if this is used within merge()
   */
  private Collection<IObservable> getResolvedObservables() {
    if (this.model != null) {
      return this.model.getObservables();
    } else if (this.observable != null) {
      return Collections.singleton(this.observable);
    }
    return Collections.emptyList();
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
    } else if (context != null) {
      return context.getObservable();
    }
    return null;
  }

  /**
   * Return how far away in the resolution is the passed namespace; -1 if not found.
   * 
   * @param ns
   * @return namespace distance
   */
  public int getNamespaceDistance(INamespace ns) {

    if (ns == null) {
      return -1;
    }

    int ret = 0;
    if (!this.resolutionNamespace.getId().equals(ns.getId())) {
      ResolutionScope rc = parent;
      while (rc != null) {
        ret++;
        if (rc.resolutionNamespace != null && rc.resolutionNamespace.getId().equals(ns.getId())) {
          return ret;
        }
        rc = rc.parent;
      }
    }

    return -1;
  }

  public int getProjectDistance(IProject ns) {

    if (ns == null) {
      return -1;
    }

    if (this.resolutionNamespace.getProject() != null
        && this.resolutionNamespace.getProject().getName().equals(ns.getName())) {
      return 0;
    }

    int ret = 0;
    if (this.resolutionNamespace.getProject() == null
        || !this.resolutionNamespace.getProject().getName().equals(ns.getName())) {
      ResolutionScope rc = parent;
      while (rc != null) {
        ret++;
        if (rc.resolutionNamespace != null && rc.resolutionNamespace.getProject() != null
            && rc.resolutionNamespace.getProject().getName().equals(ns.getName())) {
          return ret;
        }
        rc = rc.parent;
      }
    }

    return -1;
  }

  @Override
  public IDirectObservation getContext() {
    return context;
  }
}
