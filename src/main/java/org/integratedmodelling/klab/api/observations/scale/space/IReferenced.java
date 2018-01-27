package org.integratedmodelling.klab.api.observations.scale.space;

/**
 * Anything that has coordinates is referenced.
 * 
 * @author ferdinando.villa
 *
 */
public abstract interface IReferenced {

    /**
     * 
     * @return
     */
    IProjection getProjection();
}