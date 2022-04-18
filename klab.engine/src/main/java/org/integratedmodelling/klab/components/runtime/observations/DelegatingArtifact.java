package org.integratedmodelling.klab.components.runtime.observations;

import org.integratedmodelling.klab.api.provenance.IArtifact;

/**
 * Used for cleanliness when collecting provenance.
 * 
 * @author Ferd
 *
 */
public interface DelegatingArtifact {

	IArtifact getDelegate();
}
