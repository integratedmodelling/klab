package org.integratedmodelling.klab.provenance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.provenance.IAgent;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.api.provenance.IArtifact;

/**
 * All the provenance-related functions of IArtifact. Can be used as delegate for those
 * within any runtime provider that produces artifacts.
 * 
 * @author ferdinando.villa
 *
 */
public class Artifact implements IArtifact {

  // all observation data in a group share the same list and contain their index in it; established
  // at chain()
  List<IArtifact> group    = null;
  // first observation in a group has idx = -1; the others have their own index
  int                    idx      = -1;

  public void chain(IArtifact data) {
    if (group == null) {
      group = new ArrayList<>();
    }
    group.add(data);
    ((Artifact) data).group = group;
    ((Artifact) data).idx = group.size() - 1;
  }

  public boolean hasNext() {
    return group != null && group.size() > idx;
  }

  public Artifact next() {
    if (!hasNext()) {
      throw new NoSuchElementException(
          "ObservationData.next() called when hasNext() returns false");
    }
    return (Artifact) group.get(idx + 1);
  }
  
  @Override
  public long getTimestamp() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public IProvenance getProvenance() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isEmpty() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public String getUrn() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IAgent getConsumer() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IAgent getOwner() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Collection<IArtifact> getAntecedents() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Collection<IArtifact> getConsequents() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IArtifact trace(IConcept concept) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Collection<IArtifact> collect(IConcept concept) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IArtifact trace(IConcept role, IDirectObservation roleContext) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Collection<IArtifact> collect(IConcept role, IDirectObservation roleContext) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IGeometry getGeometry() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IMetadata getMetadata() {
    // TODO Auto-generated method stub
    return null;
  }
  
}
