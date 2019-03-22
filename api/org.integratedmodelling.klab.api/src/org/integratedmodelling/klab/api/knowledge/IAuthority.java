package org.integratedmodelling.klab.api.knowledge;

import java.util.List;

public interface IAuthority {

	/**
	 * Name of the authority. May be a uppercase string or uppercase path.
	 * 
	 * @return
	 */
	String getName();
	
	/**
	 * A full description of the authority.
	 * 
	 * @return
	 */
	String getDescription();

	/**
	 * Create the concept corresponding to the identity. It must be an identity
	 * semantically, and may or may not have structure to locate it in a hierarchy
	 * if appropriate.
	 * 
	 * @param identityId
	 * @return
	 */
	IConcept getIdentity(String identityId);

	/**
	 * If this returns true, search can be enabled in UIs and the results used to
	 * instantiate concepts.
	 * 
	 * @return
	 */
	boolean isSearchable();

	/**
	 * Called only if {@link #isSearchable()} returns true. Implementations must set
	 * the {@link IMetadata#IM_KEY} to the unique identity ID that will produce the
	 * concept when called in {@link #getIdentity(String)}. Remaining fields should be
	 * set so as to support the user in choosing an identity.
	 * 
	 * @param query
	 * @return
	 */
	List<IMetadata> search(String query);
}
