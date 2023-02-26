package org.integratedmodelling.klab.api.lang.kim;

/**
 * An active statement encodes an object that can have a runtime behavior specified
 * through contextualization actions.
 * 
 * @author ferdinando.villa
 *
 */
public interface KKimActiveStatement extends KKimStatement {

    /**
     * Return the behavior specified in k.IM, possibly empty.
     * 
     * @return a behavior, never null.
     */
    KKimBehavior getBehavior();
    
}
