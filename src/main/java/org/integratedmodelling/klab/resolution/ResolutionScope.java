package org.integratedmodelling.klab.resolution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.resolution.ICoverage;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.resolution.IResolvable;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.components.runtime.observations.DirectObservation;
import org.integratedmodelling.klab.components.runtime.observations.Subject;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.model.Namespace;
import org.integratedmodelling.klab.model.Observer;
import org.integratedmodelling.klab.observation.Scale;
import org.integratedmodelling.klab.owl.Observable;

/**
 * Each resolution scope acts as the context for successive ones. Scopes may be:
 * <ul>
 * <li>the root scope, created as the context for an entire resolution, either empty or in the
 * context of a subject;</li>
 * <li>an observer scope, context for resolving an observation;</li>
 * <li>an observable scope, or
 * <li>
 * <li>a model scope</li>
 * </ul>
 * 
 * @author Ferd
 *
 */
public class ResolutionScope extends Coverage implements IResolutionScope {

  /**
   * Each successful merge creates a link that is saved in the scope. At the end of the resolution,
   * all link are used to build the final dependency graph.
   * 
   * Class is used by the dataflow compiler, so it's package private.
   */
  public class Link {

    ResolutionScope target;

    Link(ResolutionScope target) {
      this.target = target;
    }

    public ResolutionScope getSource() {
      return ResolutionScope.this;
    }

    public ResolutionScope getTarget() {
      return target;
    }

    @Override
    public String toString() {
      return getSource() + " <- " + target;
    }

    @Override
    public boolean equals(Object object) {
      return object == this
          || (object instanceof Link && ((Link) object).getSource().equals(getSource())
              && target.equals(((Link) object).target));
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + getSource().hashCode();
      result = prime * result + target.hashCode();
      return result;
    }

  }

  /*
   * The three main pieces of info in each scope: only the root node has none of these, other nodes
   * have one and only one of these.
   */
  private Observable         observable;
  private Model              model;
  private Observer           observer;

  /*
   * These two are built at merge() and thrown away if a resolution ends up empty.
   */
  Set<Link>                  links               = new HashSet<>();
  Set<ResolutionScope>       resolvedObservables = new HashSet<>();

  /*
   * These change during resolution and influence the choice of models
   */
  private Mode               mode                = Mode.RESOLUTION;
  private Namespace          resolutionNamespace;

  /*
   * these do not change during an individual resolution.
   */
  private DirectObservation  context;
  private Collection<String> scenarios           = new ArrayList<>();
  private boolean            interactive;
  private IMonitor           monitor;

  /*
   * The parent is only used during model ranking, to establish project and namespace nesting and
   * distance
   */
  private ResolutionScope    parent;

  /*
   * this is only used to correctly merge in dependencies.
   */
  int                        mergedObservables   = 0;

  /*
   * this controls whether indirect resolution can happen at all. Set to true and not modified; may
   * want to configure it at some point.
   */
  private boolean            resolveIndirectly   = true;

  /**
   * Get a root scope based on the definition of an observation.
   * 
   * @param observer
   * @param monitor
   * @param scenarios
   * @return a root scope
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
   * @return a root scope
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

  /**
   * Copy the passed scope without copying the results of resolution.
   * 
   * @param other
   */
  private ResolutionScope(ResolutionScope other) {
    this(other, false);
  }

  private ResolutionScope(ResolutionScope other, boolean copyResolution) {
    super(other);
    this.scale = other.scale;
    this.scenarios.addAll(other.scenarios);
    this.resolutionNamespace = other.resolutionNamespace;
    this.mode = other.mode;
    this.interactive = other.interactive;
    this.monitor = other.monitor;
    this.parent = other;
    this.context = other.context;
    if (copyResolution) {
      this.observable = other.observable;
      this.model = other.model;
      this.observer = other.observer;
      this.links.addAll(other.links);
      this.resolvedObservables.addAll(other.resolvedObservables);
    }
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

  public Collection<Link> getLinks() {
    return links;
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

    /*
     * check if we already can resolve this (directly or indirectly), and if so, set coverage so
     * that it can be accepted as is. This should be a model; we should make the link, increment the
     * use count for the observable, and return coverage.
     */
    ResolutionScope previous = getObservable(observable, mode, resolveIndirectly);
    if (previous != null) {
      ret.setTo(previous);
    }

    return ret;
  }

  /**
   * Create a child coverage for a passed observable with a new scale and initial coverage set at 0.
   * Used to resolve new direct observables within an existing context.
   * 
   * @param observable
   * @param scale
   * @param mode
   * @return a new scope for the passed observable
   */
  public ResolutionScope getChildScope(Observable observable, Scale scale, Mode mode) {

    ResolutionScope ret = new ResolutionScope(this);
    ret.observable = observable;
    ret.setTo(new Coverage(scale, 1.0));
    ret.mode = mode;

    /*
     * check if we already can resolve this (directly or indirectly), and if so, set coverage so
     * that it can be accepted as is. This should be a model; we should make the link, increment the
     * use count for the observable, and return coverage.
     */
    ResolutionScope previous = getObservable(observable, mode, resolveIndirectly);
    if (previous != null) {
      ret.setTo(previous);
    }

    return ret;
  }

  public ResolutionScope getChildScope(Observable observable, Scale scale) {

    ResolutionScope ret = new ResolutionScope(this);
    ret.observable = observable;
    ret.setTo(new Coverage(scale, 1.0));

    /*
     * check if we already can resolve this (directly or indirectly), and if so, set coverage so
     * that it can be accepted as is. This should be a model; we should make the link, increment the
     * use count for the observable, and return coverage.
     */
    ResolutionScope previous = getObservable(observable, mode, resolveIndirectly);
    if (previous != null) {
      ret.setTo(previous);
    }

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
     * resolved models start with full coverage...
     */
    if (model.isResolved()) {
      ret.setCoverage(1.0);
    }

    /*
     * ...and redefine it based on their own coverage if they have any.
     * 
     * TODO coverages should be separated for the various scale dimensions, and which ones get
     * intersected should depend on the geometry that the model handles.
     */
    if (model.getBehavior().hasScale()) {
      Coverage rcov =
          super.and(new Coverage(Scale.create(model.getBehavior().getExtents(this.monitor))));
      ret.setTo(rcov);
    }

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

    ret.setCoverage(1.0);
    if (observer.getBehavior().hasScale()) {
      ret.setTo(new Coverage(Scale.create(observer.getBehavior().getExtents(monitor)), 1.0));
    }

    return ret;
  }

  // @Override
  // public boolean resolves(Observable observable) {
  // // TODO use the dependency graph
  // return false;
  // }

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

  // for the dataflow compiler
  public IResolvable getResolvable() {
    return observable != null ? observable
        : (model != null ? model : (observer != null ? observer : null));
  }

  @Override
  public IMonitor getMonitor() {
    return monitor;
  }

  public String toString() {
    String ret = "<" + (observable == null ? "" : ("OBS " + observable))
        + (model == null ? "" : ("MOD " + model.getName()))
        + (observer == null ? "" : ("SUB " + observer.getName()));
    return ret + (ret.length() == 1 ? "ROOT " : " ") + coverage + ">";
  }

  /**
   * Merge an accepted child scope (which has, in turn, been merged before this is called). The
   * scope must represent the entire result of a downstream resolution, not just partially - if not,
   * use the other version ({@link #merge(ResolutionScope, LogicalConnector)}).
   * 
   * @param childScope
   * @return true if the merge did anything significant
   */
  boolean merge(ResolutionScope childScope) {

    boolean successful = childScope.isRelevant();

    /*
     * Accept the observation and merge in the model if any
     */

    /*
     * my coverage becomes the child's
     */
    this.coverage = childScope.coverage;

    if (successful) {

      // when I am OBS and the child is MOD, make links
      if (this.observable != null && childScope.getModel() != null) {

        // TODO merge in new scopes for the other observables provided and link them to the child
        // scope
        for (IObservable o : childScope.getResolvedObservables(this.observable)) {
          resolvedObservables.add(childScope.getAdditionalScope(o));
        }
      }

      // when the child is OBS, update all resolution records with the new observable
      if (childScope.getObservable() != null) {

        // usage count goes up every time an observable is explicitly merged.
        resolvedObservables.add(childScope);
      }

      links.addAll(childScope.links);
      links.add(new Link(childScope));

      /*
       * Record any observables already resolved in the dependency graph so far. Must have the
       * model's scale if not existing already.
       */
      resolvedObservables.addAll(childScope.resolvedObservables);
    }

    return successful;
  }

  /**
   * Return a new scope for the passed observable, with same scale and coverage as ours. Used for
   * yet unused observables provided by models besides the focal one.
   * 
   * @param o
   * @return
   */
  private ResolutionScope getAdditionalScope(IObservable o) {
    ResolutionScope ret = new ResolutionScope(this);
    ret.observable = (Observable) o;
    ret.mode = o.getType().is(Type.COUNTABLE) ? Mode.INSTANTIATION : Mode.RESOLUTION;
    return ret;
  }

  /**
   * Merge in a child scope that contributes to the coverage according to the passed connector.
   * 
   * If the connector is OR, we start at zero coverage and each merge adds to it, proportionally to
   * the amount of NEW coverage it adds. This happens when observables merge in models that
   * contributed partially to coverage.
   * 
   * If the connector is AND, we start at zero coverage; the first child merged sets the current
   * coverage, and all others intersect it, finishing with a coverage that is the intersection of
   * all merged children. This happens when models merge in observables that satisfy their
   * dependencies.
   * 
   * @param childScope
   * @param connector
   * @return true if the merge did anything significant
   * @throws KlabException
   */
  boolean merge(ResolutionScope childScope, LogicalConnector connector) throws KlabException {

    boolean successful = false;
    if (connector.equals(LogicalConnector.INTERSECTION) && mergedObservables == 0) {
      successful = merge(childScope);
    } else {
      Coverage c = connector.equals(LogicalConnector.INTERSECTION) ? super.and(childScope)
          : super.or(childScope);
      if ((successful = c.isMergeSignificant())) {
        if (childScope.getObservable() != null) {
          resolvedObservables.add(childScope);
        }
        links.addAll(childScope.links);
        links.add(new Link(childScope));
        resolvedObservables.addAll(childScope.resolvedObservables);
        setTo(c);
      }
    }

    if (successful) {
      mergedObservables++;
    }
    return successful;
  }

  /*
   * observables are actually resolved only if this is used within merge()
   */
  private Collection<IObservable> getResolvedObservables(IObservable toSkip) {
    if (this.model != null) {
      List<IObservable> ret = new ArrayList<>();
      for (IObservable obs : this.model.getObservables()) {
        if (!obs.equals(toSkip)) {
          ret.add(obs);
        }
      }
      return ret;
    } else if (this.observable != null && !this.observable.equals(toSkip)) {
      return Collections.singleton(this.observable);
    }
    return Collections.emptyList();
  }

  public Observer getObserver() {
    return observer;
  }

  /**
   * OR the coverages, everything else remains the same.
   */
  @Override
  public ResolutionScope or(ICoverage child) throws KlabException {
    if (child.isRelevant()) {
      Coverage c = super.or(child);
      ResolutionScope rs = new ResolutionScope(this, true);
      rs.setTo(c);
      return rs;
    }
    return empty();
  }

  /**
   * If passed coverage is relevant, AND the coverage with ours and merge the dependencies. If not,
   * return this coverage unaltered.
   */
  @Override
  public ResolutionScope and(ICoverage child) throws KlabException {
    if (child.isRelevant()) {
      Coverage c = super.and(child);
      ResolutionScope rs = new ResolutionScope(this, true);
      rs.setTo(c);
      return rs;
    }
    return this;
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

  /**
   * Get the existing dependency for a passed observable and mode, or null if none exists.
   * 
   * @param observable
   * @param mode
   * @param allowIndirectObservation
   * @return
   */
  public ResolutionScope getObservable(Observable observable, Mode mode,
      boolean allowIndirectObservation) {
    for (ResolutionScope o : resolvedObservables) {
      if (o.observable.equals(observable) && o.mode == mode) {
        return o;
      }
    }
    if (allowIndirectObservation) {
      ObservableReasoner reasoner = new ObservableReasoner(observable, mode);
      for (Observable indirect : reasoner.getAlternatives()) {
        ResolutionScope alternative = getObservable(indirect, mode, true);
        if (alternative != null) {
          return alternative;
        }
      }
    }
    return null;
  }

  /**
   * Report the coverage of the passed observable in the passed mode. Return empty if the observable
   * isn't part of the graph.
   * 
   * Shorthand for getDependency(observable, mode)?.getCoverage()
   * 
   * @param observable
   * @param mode
   * @return
   */
  public Coverage getCoverage(Observable observable, Mode mode) {
    ResolutionScope dependency = getObservable(observable, mode, false);
    if (dependency != null) {
      return dependency;
    }
    return empty();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((mode == null) ? 0 : mode.hashCode());
    result = prime * result + ((model == null) ? 0 : model.hashCode());
    result = prime * result + ((observable == null) ? 0 : observable.hashCode());
    result = prime * result + ((observer == null) ? 0 : observer.hashCode());
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    ResolutionScope other = (ResolutionScope) obj;
    if (mode != other.mode) {
      return false;
    }
    if (model == null) {
      if (other.model != null) {
        return false;
      }
    } else if (!model.equals(other.model)) {
      return false;
    }
    if (observable == null) {
      if (other.observable != null) {
        return false;
      }
    } else if (!observable.equals(other.observable)) {
      return false;
    }
    if (observer == null) {
      if (other.observer != null) {
        return false;
      }
    } else if (!observer.equals(other.observer)) {
      return false;
    }
    return true;
  }

  public void setContext(IDirectObservation target) {
    this.context = (DirectObservation) target;
  }

  /**
   * Called on an empty coverage to accept anyway, setting coverage to 1.0 and removing any remnants
   * from a possibly failed resolution.
   */
  public void acceptEmpty() {
    setCoverage(1.0);
    this.model = null;
    this.links.clear();
    this.resolvedObservables.clear();
  }

}
