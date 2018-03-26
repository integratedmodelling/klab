package org.integratedmodelling.klab.components.localstorage.impl;

import java.util.Collection;
import java.util.Iterator;
import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.provenance.IAgent;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.provenance.Artifact;

public class Storage implements IArtifact {

  IMetadata metadata = new Metadata();
  IGeometry geometry;
  Artifact  artifact;
  
  public Storage(IGeometry geometry) {
    this.geometry = geometry;
  }

  public IGeometry getGeometry() {
    return this.geometry;
  }
  
  public IMetadata getMetadata() {
    return this.metadata;
  }
  

  /*
   * --------------------------------------------------------------------------------
   * artifact delegate methods
   * --------------------------------------------------------------------------------
   */
  
  @Override
  public long getTimestamp() {
    return artifact.getTimestamp();
  }

  @Override
  public IProvenance getProvenance() {
    return artifact.getProvenance();
  }

  @Override
  public boolean isEmpty() {
    return artifact.isEmpty();
  }

  @Override
  public String getUrn() {
    return artifact.getUrn();
  }

  @Override
  public IAgent getConsumer() {
    return artifact.getConsumer();
  }

  @Override
  public IAgent getOwner() {
    return artifact.getOwner();
  }

  @Override
  public Collection<IArtifact> getAntecedents() {
    return artifact.getAntecedents();
  }

  @Override
  public Collection<IArtifact> getConsequents() {
    return artifact.getConsequents();
  }

  @Override
  public IArtifact trace(IConcept concept) {
    return artifact.trace(concept);
  }

  @Override
  public Collection<IArtifact> collect(IConcept concept) {
    return artifact.collect(concept);
  }

  @Override
  public IArtifact trace(IConcept role, IDirectObservation roleContext) {
    return artifact.trace(role, roleContext);
  }

  @Override
  public Collection<IArtifact> collect(IConcept role, IDirectObservation roleContext) {
    return artifact.collect(role, roleContext);
  }

  @Override
  public Iterator<IArtifact> iterator() {
    return artifact.iterator();
  }

}
