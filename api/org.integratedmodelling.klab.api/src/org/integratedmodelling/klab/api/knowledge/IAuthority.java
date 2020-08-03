package org.integratedmodelling.klab.api.knowledge;

import java.io.OutputStream;
import java.util.List;

public interface IAuthority {

	interface Identity {

		/**
		 * The official authority ID, which may be different from what the user
		 * provided.
		 * 
		 * @return
		 */
		String getId();

		/**
		 * Stable and consistent ID suitable for naming the correspondent concept.
		 * 
		 * @return
		 */
		String getConceptName();

		/**
		 * The name of the authority, which must be capable of resolving any parents as
		 * well. The first term will be used to name the ontology.
		 * 
		 * @return
		 */
		String getAuthorityName();

		/**
		 * If not null, this will be the type in the ontology to provide the superclass
		 * for the identity. This must have been declared in the capabilities.
		 * 
		 * @return
		 */
		String getIdentityType();

		/**
		 * If the concept is expected to have a broader term from the same vocabulary,
		 * return its ID here. This will be resolved recursively and used to build the
		 * superclass, unless the authority capabilities require a different type.
		 */
		List<String> getParentIds();

		/**
		 * This may be given to define which property should constrain the parents (in
		 * order). If empty and parents are given, they will be superclasses.
		 * 
		 * @return
		 */
		List<String> getParentRelationship();

		/**
		 * Description in text or markdown.
		 * 
		 * @return
		 */
		String getDescription();

		/**
		 * Label to use to build the local concept label and display.
		 * 
		 * @return
		 */
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
		 * If true, the authority is capable of accepting unambiguous but different
		 * identifiers for the same concept, such as water and h2o, which are resolved
		 * through a search. If false, the authority can only deal with correct
		 * identifiers or formulas.
		 * 
		 * @return
		 */
		boolean isFuzzy();

		/**
		 * Return the media type names for any documentation that this authority is
		 * capable of generating given a valid identifier. If not empty, the client will
		 * set up the interface for documenting stated or retrieved identities and send
		 * requests accordingly.
		 * 
		 * @return
		 */
		List<String> getDocumentationFormats();

		/**
		 * If not null, the authority won't be loaded unless the certificate commits the
		 * engine or node to the returned worldview.
		 * 
		 * @return
		 */
		String getWorldview();

	}

	/**
	 * Create the concept corresponding to the identity. It must be an identity
	 * semantically, and may or may not have structure to locate it in a hierarchy
	 * if appropriate. The client may pass a path to the catalog if the authority
	 * has multiple layers.
	 * 
	 * @param identityId
	 * @param catalog    may be null
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
