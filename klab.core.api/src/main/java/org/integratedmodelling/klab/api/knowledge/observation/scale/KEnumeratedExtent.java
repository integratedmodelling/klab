package org.integratedmodelling.klab.api.knowledge.observation.scale;

import java.util.Collection;

import org.integratedmodelling.klab.api.knowledge.KAuthority;
import org.integratedmodelling.klab.api.knowledge.KConcept;

/**
 * Enumerated extent, which details a coverage over a conceptual space, either the semantic closure
 * of a base identity or an authority.
 * 
 * @author Ferd
 *
 */
public interface KEnumeratedExtent extends KExtent {

    /**
     * 
     * @return
     */
    KAuthority getAuthority();

    /**
     * 
     * @return
     */
    KConcept getBaseIdentity();

    /**
     * Return all the concepts that make up the extent of this domain.
     * 
     * @return
     */
    Collection<KConcept> getExtension();
}
