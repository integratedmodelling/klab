package org.integratedmodelling.klab.api.knowledge;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.collections.impl.Pair;
import org.integratedmodelling.klab.api.data.IMetadata;
import org.integratedmodelling.klab.api.services.runtime.INotification;

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
		 * If not null, this will be the label for the concept that provides a parent
		 * for the identity. The authority must return an identity for it. It will be
		 * declared as the base identity. If null, a base identity will be created from
		 * the ontology ID and shared by all identities in the authority.
		 * 
		 * @return
		 */
		String getBaseIdentity();

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

		/**
		 * The original declaration, including the authority namespace, to set into the
		 * declaration metadata for the concept.
		 * 
		 * @return
		 */
		String getLocator();

		/**
		 * Any notifications from the authority. If any of these has level = error, no
		 * concepts must be created.
		 * 
		 * @return
		 */
		List<INotification> getNotifications();
	}

	interface Capabilities {

		/**
		 * 
		 * @return
		 */
		String getDescription();

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
		 * identifiers or formulas. The main consequence is that if this is true, each
		 * search can have multiple results, otherwise it's either 0 or 1.
		 * 
		 * @return
		 */
		boolean isFuzzy();

		/**
		 * If the authority admits sub-authorities (e.g. GBIF/SPECIES), these should be
		 * listed along with their description. For now the rest of the capabilities
		 * must apply unaltered to each. If the authority also admits use without
		 * subauthorities, the first element should contain an empty string for the
		 * authority ID.
		 */
		List<Pair<String, String>> getSubAuthorities();

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
	 * Unique name of this authority.
	 * 
	 * @return
	 */
	String getName();

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
	 * If the authority is based on a codelist, return it here.
	 * 
	 * @return
	 */
	ICodelist getCodelist();

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

	/**
	 * May be called explicitly through the API for authorities hosted by nodes that
	 * need setup or reset actions.
	 * 
	 * @param options a map of options to specify actions.
	 * @return true if setup was successful. Returning false should invalidate the
	 *         authority.
	 */
	boolean setup(Map<String, String> options);
}
