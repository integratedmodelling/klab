package org.integratedmodelling.klab.provenance;

import org.integratedmodelling.klab.api.provenance.IActivity;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.jgrapht.graph.DefaultDirectedGraph;

public class Provenance extends DefaultDirectedGraph<IArtifact<?>, IActivity> {

  private static final long serialVersionUID = -699663910228938188L;

  public Provenance() {
    super(IActivity.class);
  }

}
