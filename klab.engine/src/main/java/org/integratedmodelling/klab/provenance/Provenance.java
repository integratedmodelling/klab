package org.integratedmodelling.klab.provenance;

import java.util.Collection;
import java.util.List;
import org.integratedmodelling.kim.api.IKimMetadata;
import org.integratedmodelling.klab.api.provenance.IActivity;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.jgrapht.graph.DefaultDirectedGraph;

public class Provenance extends DefaultDirectedGraph<IArtifact, Activity> implements IProvenance {

  private static final long serialVersionUID = -699663910228938188L;
  
  /**
   * TODO this should also take the agent and activity that created the initial subject.
   * 
   * @param root
   */
  public Provenance() {
    super(Activity.class);
  }

  @Override
  public boolean isEmpty() {
    return vertexSet().isEmpty();
  }

  @Override
  public IKimMetadata collectMetadata(Object node) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<IActivity> getPrimaryActions() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IArtifact getRootArtifact() {
    return null;
  }

  @Override
  public Collection<IArtifact> getArtifacts() {
    return vertexSet();
  }

  public void addArtifact(IArtifact ret) {
    addVertex(ret);
  }

}
