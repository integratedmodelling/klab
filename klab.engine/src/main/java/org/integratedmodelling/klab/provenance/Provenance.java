package org.integratedmodelling.klab.provenance;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.klab.api.provenance.IActivity;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.jgrapht.graph.DefaultDirectedGraph;

public class Provenance extends DefaultDirectedGraph<IProvenance.Node, ProvenanceEdge> implements IProvenance {

  private static final long serialVersionUID = -699663910228938188L;
    
  /**
   * TODO this should also take the agent and activity that created the initial subject.
   */
  public Provenance() {
    super(ProvenanceEdge.class);
  }

  @Override
  public boolean isEmpty() {
    return vertexSet().isEmpty();
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
	  Set<IArtifact> ret = new HashSet<>();
	  for (IProvenance.Node node : vertexSet()) {
		  if (node instanceof IArtifact) {
			  ret.add((IArtifact) node);
		  }
	  }
	  return ret;
  }

  public void addArtifact(IArtifact ret) {
    addVertex(ret);
  }

}
