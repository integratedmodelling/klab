package org.integratedmodelling.klab.api.data.adapters;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;

/**
 * A UrnAdapter that uses local configuration through a resource json. This works the same way as a
 * normal {@link IResourceAdapter} but is served by a simpler IUrnAdapter that has the option of
 * reading configuration through the contents of the resource.json file.
 * 
 * @author Ferd
 *
 */
public interface IConfigurableUrnAdapter extends IUrnAdapter {
    
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
    void encodeData(IResource resource, Builder builder, IGeometry geometry, IContextualizationScope scope);
}
