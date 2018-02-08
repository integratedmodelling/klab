package org.integratedmodelling.klab.resolution;

import java.util.ArrayList;
import java.util.Collection;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.resolution.ICoverage;
import org.integratedmodelling.klab.api.resolution.IPrioritizer;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.model.Namespace;
import org.integratedmodelling.klab.observation.Scale;
import org.integratedmodelling.klab.observation.Subject;
import org.integratedmodelling.klab.owl.Observable;

public class ResolutionScope extends Coverage implements IResolutionScope {

  private Subject            subject;
  private Observable         observable;
  private Scale              scale;
  private Collection<String> scenarios = new ArrayList<>();
  private Model              model;
  private Namespace          resolutionNamespace;
  private Prioritizer        prioritizer;
  private Mode               mode      = Mode.RESOLUTION;
  private boolean            generic;
  private boolean            interactive;
  private IMonitor           monitor;
  private ResolutionScope    parent;

  private ResolutionScope(ResolutionScope other) {
    super(other.getScale());
    this.subject = other.subject;
    this.observable = other.observable;
    this.scale = other.scale;
    this.scenarios.addAll(other.scenarios);
    this.model = other.model;
    this.resolutionNamespace = other.resolutionNamespace;
    this.mode = other.mode;
    this.generic = other.generic;
    this.interactive = other.interactive;
    this.prioritizer = other.prioritizer;
    this.monitor = other.monitor;
    this.parent = other;
  }

  public static final ResolutionScope empty() {
    return new ResolutionScope();
  }

  public static ICoverage full(IScale scale) {
    return new Coverage(scale, 1.0);
  }

  /**
   * Create the resolution scope for an existing subject, where finding a resolver is optional.
   * Coverage is initially 100% and resolution mode is set at
   * {@link org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode#RESOLUTION}.
   * 
   * @param subject
   * @param scenarios
   */
  public ResolutionScope(Subject subject, String... scenarios) {
    super(subject.getScale());
    this.subject = subject;
    this.scale = subject.getScale();
  }

  public ResolutionScope() {
    super(null, 0.0);
  }

  public ResolutionScope get(Subject subject) {
    ResolutionScope ret = new ResolutionScope(this);
    ret.subject = subject;
    ret.scale = subject.getScale();
    ret.observable = subject.getObservable();
    return ret;
  }

  /**
   * Create a child coverage for a passed observable with the same scale but initial coverage set at
   * 0.
   * 
   * @param observable
   * @param mode
   * @return
   */
  public ResolutionScope get(Observable observable, Mode mode) {
    ResolutionScope ret = new ResolutionScope(this);
    ret.observable = observable;
    ret.mode = mode;
    this.setCoverage(0);
    return ret;
  }

  public ResolutionScope get(Model model) {
    ResolutionScope ret = new ResolutionScope(this);
    /*
     * TODO if the model has its own coverage, set the scale of the returned scope to the
     * intersection of the scales
     * 
     * TODO coverages should be separated for the various scale dimensions, and which ones get
     * intersected should depend on the geometry that the model handles.
     */
    return this;
  }


  @Override
  public Scale getScale() {
    return scale;
  }

  @Override
  public Collection<String> getScenarios() {
    return scenarios;
  }

  @Override
  public Model getModel() {
    return model;
  }

  @Override
  public Namespace getResolutionNamespace() {
    return resolutionNamespace;
  }

  // @Override
  // public DirectObservation getSubject() {
  // return subject;
  // }
  //
  // @Override
  // public Artifact getProvenanceArtifact() {
  // // TODO Auto-generated method stub
  // return null;
  // }
  //
  // @Override
  // public boolean isUsed(IObservable observable) {
  // // TODO Auto-generated method stub
  // return false;
  // }
  //
  // @Override
  // public boolean isRequired(IObservable observable) {
  // // TODO Auto-generated method stub
  // return false;
  // }

  @Override
  public Mode getMode() {
    return mode;
  }

  // @Override
  // public boolean isOptional() {
  // return optional;
  // }

  @Override
  public boolean isInteractive() {
    return interactive;
  }

  @Override
  public Observable getObservable() {
    return observable;
  }

  @Override
  public IMonitor getMonitor() {
    return monitor;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> IPrioritizer<T> getPrioritizer(Class<T> cls) {
    if (Model.class.isAssignableFrom(cls)) {
      return (IPrioritizer<T>) prioritizer;
    }
    return null;
  }

  ResolutionScope merge(ResolutionScope scope) {
    // TODO
    return this;
  }

  @Override
  public Subject getSubject() {
    // TODO Auto-generated method stub
    return subject;
  }

  @Override
  public IResolutionScope getRoot() {
    return parent == null ? this : parent.getRoot();
  }

  /**
   * If passed coverage is relevant, OR the coverage with ours and update our state.
   */
  @Override
  public ResolutionScope or(ICoverage child) throws KlabException {
    if (child.isRelevant()) {
      super.or(child);
      // TODO
    }
    return this;
  }

  /**
   * If passed coverage is relevant, OR the coverage with ours and update our state.
   */
  @Override
  public ResolutionScope and(ICoverage child) throws KlabException {
    if (child.isRelevant()) {
      super.and(child);
      // TODO
    }

    return this;
  }

}
