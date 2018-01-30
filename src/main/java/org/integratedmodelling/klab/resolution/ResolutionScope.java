package org.integratedmodelling.klab.resolution;

import java.util.ArrayList;
import java.util.Collection;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.provenance.IProvenance.Artifact;
import org.integratedmodelling.klab.api.resolution.IPrioritizer;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.model.Namespace;
import org.integratedmodelling.klab.observation.DirectObservation;
import org.integratedmodelling.klab.observation.Scale;
import org.integratedmodelling.klab.observation.Subject;
import org.integratedmodelling.klab.owl.Observable;

public class ResolutionScope implements IResolutionScope {

  private DirectObservation  subject;
  private Observable         observable;
  private Scale              scale;
  private Collection<String> scenarios = new ArrayList<>();
  private Model              model;
  private Namespace          resolutionNamespace;
  private Coverage           coverage;
  private Prioritizer        prioritizer;
  private Mode               mode = Mode.RESOLUTION;
  private boolean            generic;
  private boolean            interactive;
  private boolean            optional;

  private ResolutionScope(ResolutionScope other) {
    this.subject = other.subject;
    this.observable = other.observable;
    this.scale = other.scale;
    this.scenarios.addAll(other.scenarios);
    this.model = other.model;
    this.resolutionNamespace = other.resolutionNamespace;
    this.mode = other.mode;
    this.optional = other.optional;
    this.generic = other.generic;
    this.interactive = other.interactive;
    this.prioritizer = other.prioritizer;
  }

  /**
   * Create the resolution scope for an existing subject, where finding a resolver is optional.
   * TODO we should probably add dependencies for all mandatory relationships.
   * 
   * @param subject
   * @param scenarios
   */
  public ResolutionScope(Subject subject, String ...scenarios) {
    this.subject = subject;
    this.scale = subject.getScale();
    this.observable = subject.getObservable();
    this.optional = true;
  }
  
  public ResolutionScope get(Subject subject) {
    ResolutionScope ret = new ResolutionScope(this);
    ret.subject = subject;
    ret.scale = subject.getScale();
    ret.observable = subject.getObservable();
    return ret;
  }

  public ResolutionScope get(Observable observable, Mode mode) {
    ResolutionScope ret = new ResolutionScope(this);
    ret.mode = mode;
    return ret;
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
  public IPrioritizer<IModel> getPrioritizer() {
    return prioritizer;
  }

  @Override
  public Namespace getResolutionNamespace() {
    return resolutionNamespace;
  }

  @Override
  public DirectObservation getSubject() {
    return subject;
  }

  @Override
  public Coverage getCoverage() {
    return coverage;
  }

  @Override
  public Artifact getProvenanceArtifact() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isUsed(IObservable observable) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isRequired(IObservable observable) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Mode getMode() {
    return mode;
  }

  @Override
  public boolean isOptional() {
    return optional;
  }

  @Override
  public boolean isInteractive() {
    return interactive;
  }

  @Override
  public boolean isGeneric() {
    return generic;
  }

  @Override
  public Observable getObservable() {
    return observable;
  }

}
