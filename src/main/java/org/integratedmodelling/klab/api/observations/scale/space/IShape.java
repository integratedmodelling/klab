package org.integratedmodelling.klab.api.observations.scale.space;

import org.integratedmodelling.klab.api.data.mediation.IUnit;

/**
 * Opaque, minimal interface for a 2D geometry.
 * 
 * @author ferdinando.villa
 *
 */
public interface IShape extends IReferenced {

    public enum Type {
        EMPTY,
        POINT,
        LINESTRING,
        POLYGON,
        MULTIPOINT,
        MULTILINESTRING,
        MULTIPOLYGON
    }

    /**
     * Geometry type
     * 
     * @return the type
     */
    IShape.Type getType();

    /**
     * Return a suitable measure of area. Unit must be areal.
     * @param unit 
     * 
     * @return area in passed unit
     */
    double getArea(IUnit unit);

    /**
     * Shapes may be empty or inconsistent.
     * 
     * @return true if not really a shape
     */
    boolean isEmpty();

    /**
     * Return the shape transformed to the passed projection.
     * 
     * @param projection
     * @return
     */
    IShape transform(IProjection projection);

    /**
     * 
     * @return
     */
    IEnvelope getEnvelope();

    /**
     * 
     * @param other
     * @return
     */
    IShape intersection(IShape other);

    /**
     * 
     * @param other
     * @return
     */
    IShape union(IShape other);

}