package org.integratedmodelling.kim.api;

/**
 * An active statement encodes an object that can have a runtime behavior specified
 * through contextualization actions.
 * 
 * @author ferdinando.villa
 *
 */
public interface IKimActiveStatement extends IKimStatement {

    /**
     * Return the behavior specified in k.IM, possibly empty.
     * 
     * @return a behavior, never null.
     */
    IKimBehavior getBehavior();
    
}
