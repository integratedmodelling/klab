/**
 * 
 */
package org.integratedmodelling.klab.test;

import org.integratedmodelling.klab.Workspaces;
import org.integratedmodelling.klab.api.engine.IEngine;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.engine.Engine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author ferdinando.villa
 *
 */
public class EngineStartStop {

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void startEngine() {
       IEngine engine = Engine.start();
       for (IProject project : Workspaces.INSTANCE.getLocal().getProjects()) {
           
       }
    }

}
