package org.integratedmodelling.klab.tests.services.resources;

import org.integratedmodelling.klab.services.resources.ResourcesService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ResourceIngestion {

    private static ResourcesService service = null;

    @BeforeAll
    static void setUp() throws Exception {
        service = new ResourcesService();
    }

    @AfterAll
    static void tearDown() throws Exception {
        service.shutdown();
    }

    @Test
    void importIm() {
        service.addProjectToLocalWorkspace("worldview", "https://bitbucket.org/integratedmodelling/im.git#develop");
    }

}
