package org.integratedmodelling.klab.test;

import java.util.function.Consumer;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class UnitTests {

    static Engine engine;

    @BeforeClass
    public static void setUp() throws Exception {
        engine = Engine.start();
        // ensure errors cause exc
        Logging.INSTANCE.setErrorWriter(new Consumer<String>() {
            
            @Override
            public void accept(String t) {
                throw new KlabException(t);
            }
        });
    }

    @AfterClass
    public static void tearDown() throws Exception {
        if (engine != null) {
            engine.stop();
        }
    }
    
    @Test
    public void test() {

        IGeometry geometry = Geometry.create("T1(23)S2(200,100)");
        IUnit unit = Unit.create("mm");
        IUnit contextualized = unit.contextualize(Observables.INSTANCE.declare("earth:PrecipitationVolume in mm/day"), geometry);
        
        /*
         * convert 
         */
        
        System.out.println("Stop here");
    }

}
