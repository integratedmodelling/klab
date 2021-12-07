package org.integratedmodelling.klab.api.observations.scale;

import org.integratedmodelling.klab.api.knowledge.IAuthority;
import org.integratedmodelling.klab.api.knowledge.IConcept;

/**
 * Enumerated extent, which details a coverage over a conceptual space, either
 * the semantic closure of a base identity or an authority.
 * 
 * @author Ferd
 *
 */
public interface IEnumeratedExtent extends IExtent {

	/**
	 * 
	 * @return
	 */
	IAuthority getAuthority();
	
	/**
	 * 
	 * @return
	 */
	IConcept getBaseIdentity();
}
