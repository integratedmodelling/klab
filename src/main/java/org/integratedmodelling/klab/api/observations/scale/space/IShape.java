package org.integratedmodelling.klab.api.observations.scale.space;

import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

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
    Type getType();

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
     * @return the transformed shape
     * @throws KlabValidationException 
     */
    IShape transform(IProjection projection) throws KlabValidationException;

    /**
     * The shape's bounding box
     * @return the referenced envelope
     */
    IEnvelope getEnvelope();

    /**
     * Create a new shape by intersecting this with the passed other.
     * @param other
     * @return the intersection
     */
    IShape intersection(IShape other);

    /**
     * Create a new shape by uniting this with the passed other.
     * @param other
     * @return the union
     */
    IShape union(IShape other);

}