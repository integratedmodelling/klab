package org.integratedmodelling.klab.components.runtime.observations;

import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.utils.Delegating;

/**
 * Used for cleanliness when collecting provenance. Using this instead of
 * Delegating<IArtifact> because Java sucks and type gaming is awkward.
 * 
 * @author Ferd
 *
 */
public interface DelegatingArtifact extends Delegating<IArtifact> {
}
