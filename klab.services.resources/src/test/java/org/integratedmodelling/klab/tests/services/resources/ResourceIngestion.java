package org.integratedmodelling.klab.tests.services.resources;

import org.integratedmodelling.klab.api.lang.kim.KKimObservable;
import org.integratedmodelling.klab.api.lang.kim.KKimStatement;
import org.integratedmodelling.klab.services.resources.ResourcesService;
import org.integratedmodelling.klab.utils.Utils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ResourceIngestion {

    private static ResourcesService service = null;

    /*
     * TODO substitute with online service filled in with as many observable use cases as possible
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
        service.shutdown(0);
    }

    @Test
    void parseObservables() {

        for (String observable : testObservables) {
            KKimObservable obs = service.resolveObservable(observable);
            System.out.println(obs);
            assert(obs != null);
        }
    }

    @Test
    void serializeAndDeserializeObservables() {

        for (String observable : testObservables) {
            KKimObservable obs = service.resolveObservable(observable);
            String serialized = Utils.Json.asString(obs);
            KKimStatement kimObject = Utils.Json.parseObject(serialized, KKimStatement.class);
            assert (kimObject instanceof KKimObservable);
        }
    }

    
}
