package org.integratedmodelling.klab.api.observations.scale.space;

/**
 * Opaque interface for a coordinate reference system.
 * 
 * @author ferdinando.villa
 *
 */
public interface IProjection {

    /**
     * Unique identifier of projection, enough to rebuild it at another
     * endpoint.
     * 
     * @return
     */
    String getCode();
}