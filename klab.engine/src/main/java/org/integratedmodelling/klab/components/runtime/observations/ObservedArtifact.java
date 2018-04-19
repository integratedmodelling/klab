package org.integratedmodelling.klab.components.runtime.observations;

import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.kim.utils.NameGenerator;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.provenance.Artifact;

/**
 * Simple abstract observation data class for storage components. Just implements the basic iterator
 * functions and access to simple final fields.
 * 
 * @author Ferd
 *
 */
public class ObservedArtifact extends Artifact implements IArtifact {

  private IGeometry      geometry;
  private IRuntimeContext runtimeContext;
  private IMetadata              metadata = new Metadata();
  private String         token    = "o" + NameGenerator.shortUUID();

  public ObservedArtifact(IGeometry geometry, IRuntimeContext context) {
    this.geometry = geometry;
    this.runtimeContext = context;
  }

  public String getId() {
    return token;
  }

  @Override
  public IGeometry getGeometry() {
    return geometry;
  }

  @Override
  public IMetadata getMetadata() {
    return metadata;
  }

  public IRuntimeContext getRuntimeContext() {
    return this.runtimeContext;
  }

  public ObservedArtifact getParent() {
    // TODO Auto-generated method stub USE THE GRAPH IN THE CONTEXT
    return null;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((token == null) ? 0 : token.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ObservedArtifact other = (ObservedArtifact) obj;
    if (token == null) {
      if (other.token != null)
        return false;
    } else if (!token.equals(other.token))
      return false;
    return true;
  }

  @Override
  public IProvenance getProvenance() {
    return getRuntimeContext().getProvenance();
  }
  
}
