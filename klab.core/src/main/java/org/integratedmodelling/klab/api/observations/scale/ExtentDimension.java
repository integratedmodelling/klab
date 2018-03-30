package org.integratedmodelling.klab.api.observations.scale;

/**
 * All extent dimensions understood in this implementation, for both space and time. Used to
 * decompose units to validate distribution over time and space.
 * 
 * @author ferdinando.villa
 *
 */
public enum ExtentDimension {

    PUNTAL,
    LINEAL,
    AREAL,
    VOLUMETRIC,
    TEMPORAL,
    CONCEPTUAL
}
