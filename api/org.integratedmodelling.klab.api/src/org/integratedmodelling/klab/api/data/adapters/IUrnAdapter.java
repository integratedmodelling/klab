package org.integratedmodelling.klab.api.data.adapters;

import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;

/**
 * Base interface for a URN adapter, which can produce non-semantic data from
 * simple URNs with the universal protocol ([klab/nodename]:adapter:....).
 * 
 * @author Ferd
 *
 */
public interface IUrnAdapter {

	/**
	 * The adapter name. Must be lowercase and simple.
	 * 
	 * @return the adapter name.
	 */
	String getName();

	/**
	 * Check if the resource can be accessed. This should ensure the ability of
	 * calling
	 * {@link #getEncodedData(IResource, IGeometry, org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder, IContextualizationScope)}
	 * without spending too much time.
	 * 
	 * @param resource
	 * @return true if resource can be used at the moment of this call.
	 */
	boolean isOnline(Urn urn);

	/**
	 * Build the resource data corresponding to the passed resource in the passed
	 * geometry. The data are created using a builder passed by the runtime.
	 *
	 * @param resource a {@link org.integratedmodelling.klab.api.data.IResource}. It
	 *                 should have been recently inspected with
	 *                 {@link #isOnline(IResource)} so it can be assumed that it is
	 *                 correct and active.
	 * @param builder  a suitable builder to use to build the result.
	 * @param geometry the {@link org.integratedmodelling.klab.api.data.IGeometry}
	 *                 of reference for the query, if any. Null when URN specifies
	 *                 the root observation.
	 * @param context  the context of computation, if any. Null when URN specifies
	 *                 the root observation.
	 */
	void getEncodedData(Urn urn, IKlabData.Builder builder, IGeometry geometry, IContextualizationScope context);
	
	/**
	 * Get the resource type.
	 * 
	 * @param urn
	 * @return
	 */
	IArtifact.Type getType(Urn urn);

	/**
	 * Get the URN's geometry.
	 * 
	 * @param urn
	 * @return
	 */
	IGeometry getGeometry(Urn urn);

}
