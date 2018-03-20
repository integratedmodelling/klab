package org.integratedmodelling.klab.provenance;

import org.integratedmodelling.klab.api.provenance.IArtifact;

public abstract class Artifact implements IArtifact {

  protected IArtifact next;

  public void chain(IArtifact artifact) {
    Artifact end = this;
    while (end.next != null) {
      end = (Artifact)end.next;
    }
    end.next = artifact;
  }
  
  public boolean hasNext() {
    return next != null;
  }
  
  public IArtifact getNext() {
    return next;
  }
  
}
