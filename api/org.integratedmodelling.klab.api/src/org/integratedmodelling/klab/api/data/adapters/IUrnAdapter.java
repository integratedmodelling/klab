package org.integratedmodelling.klab.api.data.adapters;

import java.util.Collection;

import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;

/**
 * Base interface for a URN adapter, which can produce non-semantic data from simple URNs with the
 * universal protocol ([klab/nodename]:adapter:....).
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
     * The adapter must be capable of producing a resource for a specific URN, containing the type,
     * inputs, outputs, attributes and metadata that describe it.
     * 
     * @return
     */
    IResource getResource(String urn);

    /**
     * Contextualize the resource to the passed scale and semantics. If there is any issue with
     * availability, set the correspondent fields in the result.
     * 
     * @param resource
     * @param scale the scale for this specific contextualization
     * @param overallScale the scale of the entire context
     * @param semantics
     * @return
     */
    IResource contextualize(IResource resource, IGeometry scale, IGeometry overallScale, IObservable semantics);

    /**
     * Check if the resource can be accessed. This should ensure the ability of calling
     * {@link #getEncodedData(IResource, IGeometry, org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder, IContextualizationScope)}
     * without spending too much time.
     * 
     * @param resource
     * @return true if resource can be used at the moment of this call.
     */
    boolean isOnline(Urn urn);

    /**
     * Encode the data for the URN and geometry in the passed builder. The appropriate build()
     * method will then be called by the calling API.
     * 
     * @param resource a {@link org.integratedmodelling.klab.api.data.IResource}. It should have
     *        been recently inspected with {@link #isOnline(IResource)} so it can be assumed that it
     *        is correct and active.
     * @param builder a suitable builder to use to build the result.
     * @param geometry the {@link org.integratedmodelling.klab.api.data.IGeometry} of reference for
     *        the query, if any. Null when URN specifies the root observation.
     * @param context the context of computation, if any. Null when URN specifies the root
     *        observation.
     */
    void encodeData(Urn urn, Builder builder, IGeometry geometry, IContextualizationScope scope);

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

    /**
     * Return a description suitabile for API users.
     * 
     * @return
     */
    String getDescription();

    /**
     * Return all resource URNs that this adapter handles.
     * 
     * @return
     */
    Collection<String> getResourceUrns();

}
