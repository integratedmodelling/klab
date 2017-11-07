package org.integratedmodelling.klab.api.data;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.raw.IRawObject;
import org.integratedmodelling.klab.api.services.IUrnService;

/**
 * Resolution of a URN (operated by the {@link IUrnService} returns a IResource, which represents an immutable,
 * non-empty list of IRawObjects that the semantic model converts into IObservations using the worldview.
 * 
 * @author Ferd
 *
 */
public interface IResource {

    /**
     * The URN for this resource.
     * 
     * @return
     */
    String getUrn();

    /**
     * Get the geometry associated with the resource, without fetching the entire data
     * content.
     * 
     * @return
     */
    IGeometry getGeometry();

    /**
     * Get the version associated with the resource.
     * 
     * @return
     */
    Version getVersion();
    
    /**
     * Fetch (if necessary) and return the root raw object represented by this resource.
     * 
     * @return
     */
    IRawObject get();
}
