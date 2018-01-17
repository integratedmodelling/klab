package org.integratedmodelling.klab.api.knowledge;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.observations.scale.IScale;

/**
 * Intended to be specialized for specific worldviews, to replace the cumbersome
 * 'export' mechanism.
 * 
 * @author Ferd
 *
 */
public interface IWorldview extends IWorkspace {

    /**
     * Translate the geometry from a {@link IResource} to the corresponding
     * IScale for the worldview.
     * 
     * @param geometry
     * @return the translated geometry
     */
    IScale getScale(IGeometry geometry);
    
}
