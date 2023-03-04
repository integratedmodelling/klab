package org.integratedmodelling.klab.tests.services.resources;

import org.integratedmodelling.klab.api.lang.kim.KKimObservable;
import org.integratedmodelling.klab.api.lang.kim.KKimStatement;
import org.integratedmodelling.klab.api.lang.kim.impl.KimStatement;
import org.integratedmodelling.klab.services.resources.ResourcesService;
import org.integratedmodelling.klab.utils.Utils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ResourceIngestion {

    private static ResourcesService service = null;

    /*
     * TODO fill in with as many observable use cases as possible
     */
    private static String[] testObservables = {
            "geography:Elevation in m",
            "geography:Elevation optional",
            "geography:Elevation in m optional",
            "any geography:Elevation in m",
            "geography:Elevation in m > 100",
            "geography:Elevation in m by landcover:LandCoverType"
    };

    @BeforeAll
    static void setUp() throws Exception {
        service = new ResourcesService();
        service.addProjectToLocalWorkspace("worldview", "https://bitbucket.org/integratedmodelling/im.git#develop", false);
    }

    @AfterAll
    static void tearDown() throws Exception {
        service.shutdown();
    }

    @Test
    void parseObservables() {

        for (String observable : testObservables) {
            KKimObservable obs = service.resolveObservable(observable);
            assert(obs != null);
            String serialized = Utils.Json.asString(obs);
            System.out.println(serialized);
            KKimStatement object = Utils.Json.parseObject(serialized, KimStatement.class);
            System.out.println(object);
        }
    }

//    @Test
//    void serializeAndDeserializeObservables() {
//
//        for (String observable : testObservables) {
//            KKimObservable obs = service.resolveObservable(observable);
//            String serialized = Utils.Json.asString(obs);
//            Object kimObject = Utils.Json.parseObject(serialized, KKimStatement.class);
//            assert (kimObject instanceof KKimObservable);
//        }
//    }

    
}
