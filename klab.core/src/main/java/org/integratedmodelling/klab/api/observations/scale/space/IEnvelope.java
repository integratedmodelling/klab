package org.integratedmodelling.klab.api.observations.scale.space;

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
    IEnvelope transform(IProjection projection, boolean b);

    /**
     * 
     * @return
     */
    double[] getCenterCoordinates();
}