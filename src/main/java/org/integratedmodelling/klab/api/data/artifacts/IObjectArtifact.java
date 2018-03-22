package org.integratedmodelling.klab.api.data.artifacts;

import java.util.Collection;
import org.integratedmodelling.klab.api.provenance.IArtifact;

/**
 * A {@code IArtifact} representing a first-class object, which may own other artifacts and has a
 * name. Additional API for a direct observation.
 * 
 * @author Ferd
 *
 */
public interface IObjectArtifact extends IArtifact {

  /**
   * Objects have names.
   * 
   * @return the object's name
   */
  String getName();

  /**
   * Objects may own other objects. The ownership is one of the three structures in a context: 1)
   * artifact grouping (provenance); 2) ownership structure (always a tree), and 3)
   * relationship/network structure.
   * 
   * @return all the directly owned objects
   */
  Collection<IArtifact> getChildren();

}
