package org.integratedmodelling.tests.services.reasoner;

import org.integratedmodelling.klab.api.knowledge.KConcept;
import org.integratedmodelling.klab.api.knowledge.KObservable;
import org.integratedmodelling.klab.services.reasoner.ReasonerService;
import org.integratedmodelling.klab.services.resources.ResourcesService;
import org.junit.Before;
import org.junit.jupiter.api.Test;

class ConceptIngestion {

    /*
     * TODO substitute with online service filled in with as many observable use cases as possible
     */
    private static String[] testObservables = {"geography:Elevation in m", "geography:Elevation optional",
            "geography:Elevation in m optional", "any geography:Elevation in m", "geography:Elevation in m > 100",
            "geography:Elevation in m by landcover:LandCoverType"};

    private static String[] testConcepts = {"geography:Elevation in m", "geography:Elevation optional",
            "geography:Elevation in m optional", "any geography:Elevation in m", "geography:Elevation in m > 100",
            "geography:Elevation in m by landcover:LandCoverType"};

    private ResourcesService resourceService = new ResourcesService();
    private ReasonerService reasonerService;

    @Before
    public void prepare() {
        this.reasonerService = new ReasonerService(resourceService, null);
    }

    @Test
    void concepts() {
        for (String c : testConcepts) {
            KConcept concept = reasonerService.resolveConcept(c);
            System.out.println(concept);
        }
    }

    @Test
    void observables() {
        for (String concept : testObservables) {
            KObservable observable = reasonerService.resolveObservable(concept);
            System.out.println(observable);
        }
    }

}
