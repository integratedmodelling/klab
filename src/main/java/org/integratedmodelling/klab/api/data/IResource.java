package org.integratedmodelling.klab.api.data;

import java.util.List;

import org.integratedmodelling.klab.api.data.raw.IRawObject;
import org.integratedmodelling.klab.data.Urn;

/**
 * Resolution of a URN returns a IResource, which is an immutable list of IRawObjects that the
 * semantic model converts into IObservations using the worldview.
 * 
 * @author Ferd
 *
 */
public interface IResource extends List<IRawObject> {
    
    Urn getUrn();
    
}
