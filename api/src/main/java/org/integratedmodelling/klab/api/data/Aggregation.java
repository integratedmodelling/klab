package org.integratedmodelling.klab.api.data;

/**
 * Aggregation mode. The default is AVERAGE for intensive properties and
 * non-physical properties or SUM for extensive properties, but data reduction
 * traits in the target may modify it (e.g. we may want the MAX if we tag the
 * final observer with im:Maximum). MAJORITY will be the default for qualitative
 * and semi-qualitative observers; at some point we may want to add fuzzy
 * membership and other more sophisticated strategies for probabilistic observers.
 * 
 * @author ferdinando.villa
 *
 */
public enum Aggregation {
    NONE,
    SUM,
    AVERAGE,
    MIN,
    MAX,
    MAJORITY,
    MAXIMUM_LIKELIHOOD
}