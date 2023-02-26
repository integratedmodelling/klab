package org.integratedmodelling.klab.api.lang.kim;

import java.util.Collection;

import org.integratedmodelling.klab.api.lang.KServiceCall;

/**
 * A IKimBehavior is the statement of the contextualization strategy
 * for a model or an observation, consisting of a list of action and
 * a set of general methods for convenience.
 * 
 * @author fvilla
 *
 */
public interface KKimBehavior extends KKimStatement, Iterable<KKimAction> {

    /**
     * Quick check for no-op behaviors.
     * 
     * @return true if nothing is here.
     */
    boolean isEmpty();

    /**
     * Quick check for the existence of temporal actions.
     * 
     * @return true if temporal actions are there.
     */
    boolean isDynamic();

    /**
     * Any extent functions mentioned in the behavior are collected here. They
     * may have partial or no parameters, to be harmonized with the overall
     * context's scale.
     * 
     * @return the extent functions
     */
    Collection<KServiceCall> getExtentFunctions();
    
}
