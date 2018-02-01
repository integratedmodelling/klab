package org.integratedmodelling.klab.api.observations.scale.space;

import org.integratedmodelling.klab.components.geospace.extents.Envelope;
import org.integratedmodelling.klab.components.geospace.extents.Projection;

/**
 * Opaque interface for a referenced envelope.
 * 
 * @author ferdinando.villa
 *
 */
public interface IEnvelope extends IReferenced {

    /**
     * 
     * @return
     */
    double getMinX();

    /**
     * 
     * @return
     */
    double getMaxX();

    /**
     * 
     * @return
     */
    double getMinY();

    /**
     * 
     * @return
     */
    double getMaxY();

    /**
     * 
     * @return
     */
    IShape asShape();

    
    /**
     * 
     * @param projection
     * @param b
     * @return
     */
    Envelope transform(Projection projection, boolean b);
}