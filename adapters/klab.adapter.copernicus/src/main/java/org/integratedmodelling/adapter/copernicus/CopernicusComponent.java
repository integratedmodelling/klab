package org.integratedmodelling.adapter.copernicus;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.adapter.copernicus.repositories.AgERA5Repository;
import org.integratedmodelling.adapter.datacube.ChunkedDatacubeRepository;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.extensions.component.GetStatus;
import org.integratedmodelling.klab.api.extensions.component.Initialize;
import org.integratedmodelling.klab.api.extensions.component.Maintain;
import org.integratedmodelling.klab.api.extensions.component.Setup;
import org.integratedmodelling.klab.api.knowledge.IMetadata;

@Component(id = CopernicusComponent.ID, version = Version.CURRENT)
public class CopernicusComponent {

    public static final String ID = "org.integratedmodelling.copernicus";

    private Map<String, ChunkedDatacubeRepository> repositories = Collections.synchronizedMap(new HashMap<>());

    public CopernicusComponent() {

        /*
         * the repositories. TODO this should go in initialization but it doesn't get called.
         */
        ChunkedDatacubeRepository agera5 = new AgERA5Repository();

        /*
         * add them all with the names they advertise
         */
        repositories.put(agera5.getName(), agera5);

    }

    /*
     * keep a catalog of repositories, which will be shared among different adapters (in different
     * configurations for static and dynamic).
     */
    @Initialize
    public boolean initialize() {
        // TODO this doesn't get called
        return true;
    }

    @Setup(asynchronous = true)
    public boolean setupEvents() {
        return true;
    }

    public ChunkedDatacubeRepository getRepository(String repository) {
        return repositories.get(repository);
    }

    @GetStatus
    public void getStatus(IMetadata metadata) {
        for (String repository : repositories.keySet()) {
            ChunkedDatacubeRepository datacube = repositories.get(repository);
            metadata.put("datacube." + repository + ".status",
                    (datacube.isOnline() ? "ONLINE" : "OFFLINE") + " (" + datacube.getStatusMessage() + ")");
        }
    }

    /**
     * Default maintenance of GHCND stations is every 3 days. Should also eventually schedule a
     * storm detection step.
     */
    @Maintain(intervalMinutes = 60 * 24 * 3)
    public void updateStations() {
    }

}
