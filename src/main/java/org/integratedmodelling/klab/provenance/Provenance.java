package org.integratedmodelling.klab.provenance;

import org.jgrapht.graph.DefaultDirectedGraph;

public class Provenance extends DefaultDirectedGraph<ObservationalArtifact<?>, Activity> {

  private static final long serialVersionUID = -699663910228938188L;

  public Provenance() {
    super(Activity.class);
  }

}
