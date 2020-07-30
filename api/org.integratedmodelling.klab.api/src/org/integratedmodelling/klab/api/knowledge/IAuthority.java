package org.integratedmodelling.klab.api.knowledge;

import java.io.OutputStream;
import java.util.List;

public interface IAuthority {

	interface Identity {

		String getId();

		String getConceptName();

		List<String> getParentIds();

		String getDescription();

		String getLabel();

		/**
		 * This should be 1 if returned by getIdentity(), or 0-1 if returned through a
		 * query.
		 * 
		 * @return
		 */
		float getScore();
	}

	interface Capabilities {
		/**
		 * If true, users can use the search API.
		 * 
		 * @return
		 */
		boolean isSearchable();

		/**
		 * Return the media type names for any documentation that this authority is
		 * capable of generating given a valid identifier. If not empty, the client will
		 * set up the interface for documenting stated or retrieved identities and send
		 * requests accordingly.
		 * 
		 * @return
		 */
		List<String> getDocumentationFormats();
	}

	/**
	 * Create the concept corresponding to the identity. It must be an identity
	 * semantically, and may or may not have structure to locate it in a hierarchy
	 * if appropriate. The client may pass a path to the catalog if the authority
	 * has multiple layers.
	 * 
	 * @param identityId
	 * @param catalog may be null
	 * @return
	 */
	Identity getIdentity(String identityId, String catalog);

	/**
	 * Get the authority service's capabilities.
	 * 
	 * @return
	 */
	Capabilities getCapabilities();

	/**
	 * Write the documentation for the passed identity in the passed media type,
	 * which will be one of those returned in the capabilities.
	 * 
	 * @param identityId
	 * @param mediaType
	 * @param destination
	 */
	void document(String identityId, String mediaType, OutputStream destination);

	/**
	 * Called only if {@link #isSearchable()} returns true. Implementations must set
	 * the {@link IMetadata#IM_KEY} to the unique identity ID that will produce the
	 * concept when called in {@link #getIdentity(String)}. Remaining fields should
	 * be set so as to support the user in choosing an identity.
	 * 
	 * @param query
	 * @param catalog may be null
	 * @return
	 */
	List<Identity> search(String query, String catalog);
}
