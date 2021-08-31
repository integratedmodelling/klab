package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.actors.IBehavior.Action;

/**
 * Additional scope for actions in test scripts.
 * 
 * @author Ferd
 *
 */
public class TestScope {
    /*
     * match for the expected fire, if any
     */
    public Object expect = null;

    public TestScope(IBehavior behavior) {
        // setup logging
    }

    public void onException(Throwable t) {
        // TODO Auto-generated method stub
        System.out.println("HAHAHA");
    }

    public void finalizeTest(Action action) {
        // TODO Auto-generated method stub
        System.out.println("HOHOHO");
    }
}
