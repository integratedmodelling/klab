package org.integratedmodelling.klab.provenance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.provenance.IAgent;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IProvenance;

/**
 * All the provenance-related functions of IArtifact. Can be used as delegate for those within any
 * runtime provider that produces artifacts.
 * <p>
 * Artifacts in provenance graphs can also represent externally available resources, expressing the
 * lineage of the information retrieved through URNs. The history is reconstructed based on the
 * resource's metadata.
 * 
 * @author ferdinando.villa
 *
 */
public class Artifact implements IArtifact {

  // all observation data in a group share the same list and contain their index in it; established
  // at chain()
  List<IArtifact> group = null;
  // first observation in a group has idx = -1; the others have their own index
  int             idx   = -1;

  public void chain(IArtifact data) {
    if (group == null) {
      group = new ArrayList<>();
    }
    group.add(data);
    ((Artifact) data).group = group;
    ((Artifact) data).idx = group.size() - 1;
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

  @Override
  public Iterator<IArtifact> iterator() {
    List<IArtifact> list =
        new ArrayList<>(1 + (group == null ? 0 : (group.size() - (idx < 0 ? 0 : idx))));
    list.add(this);
    if (group != null) {
      for (int i = (idx < 0 ? 0 : idx); i < group.size(); i++) {
        list.add(group.get(i));
      }
    }
    return list.iterator();
  }

  @Override
  public int groupSize() {
    return 1 + (group == null ? 0 : group.size());
  }

}
