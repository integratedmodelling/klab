package org.integratedmodelling.klab.api.data.adapters;

import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResourceCatalog;

/**
 * If the publisher implements this interface, it declares that there are
 * further steps after publication that can make the resource more available,
 * powerful or efficient, opening the road for periodic resource enhancements
 * (e.g. storing on high-availability servers) that happen automatically.
 * <p>
 * The publisher can call this during publish() and if not successful, leave it
 * to the node to try again later.
 * 
 * @author Ferd
 *
 */
public interface IResourceEnhancer extends IResourcePublisher {

	/**
	 * If this returns true, no further enhancement is possible and
	 * {@link #enhanceResource(IResource)} won't be called.
	 * 
	 * @param resource
	 * @return
	 */
	boolean isEnhanced(IResource resource);

	/**
	 * Try enhancing the resource and return the result, which may be a different
	 * resource but should keep the previous one in its history. It should never
	 * throw exception but if it fails it should ensure that the resulting resource
	 * will return false when {@link #isEnhanced(IResource)} is called, unless it's
	 * pointless to try again.
	 * 
	 * @param resource
	 * @return
	 */
	IResource enhanceResource(IResource resource, IResourceCatalog catalog);

}
