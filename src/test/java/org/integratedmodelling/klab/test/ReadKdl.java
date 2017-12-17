/**
 * 
 */
package org.integratedmodelling.klab.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import org.integratedmodelling.kdl.api.IKdlDataflow;
import org.integratedmodelling.klab.Dataflows;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author ferdinando.villa
 *
 */
public class ReadKdl {

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
    public void parseKdl() throws MalformedURLException, KlabException {
        try (InputStream stream = ReadKdl.class.getClassLoader().getResourceAsStream("test.kdl")) {
            IKdlDataflow kdl = Dataflows.INSTANCE.declare(stream);
            System.out.println("result: " + kdl);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
