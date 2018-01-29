package org.integratedmodelling.klab.resolution;

import java.util.Collection;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IProvenance.Artifact;
import org.integratedmodelling.klab.api.resolution.ICoverage;
import org.integratedmodelling.klab.api.resolution.IPrioritizer;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;

public class ResolutionScope implements IResolutionScope {

  public ResolutionScope() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public IScale getScale() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Collection<String> getScenarios() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IModel getModel() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IPrioritizer<IModel> getPrioritizer() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public INamespace getResolutionNamespace() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IDirectObservation getSubject() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ICoverage getCoverage() {
    // TODO Auto-generated method stub
    return null;
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
  public boolean isForInstantiation() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isOptional() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isInteractive() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isGeneric() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public IObservable getObservable() {
    // TODO Auto-generated method stub
    return null;
  }

}
