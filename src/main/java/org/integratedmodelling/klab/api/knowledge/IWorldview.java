package org.integratedmodelling.klab.api.knowledge;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.observations.IScale;

/**
 * Intended to be specialized for specific worldviews, to replace the cumbersome
 * 'export' mechanism.
 * 
 * @author Ferd
 *
 */
public interface IWorldview extends IWorkspace {

    /**
     * Translate the geometry from a URN-specified datasource to the corresponding
     * IScale for the worldview.
     * 
     * @param geometry
     * @return
     */
    IScale getScale(IGeometry geometry);
    
}
