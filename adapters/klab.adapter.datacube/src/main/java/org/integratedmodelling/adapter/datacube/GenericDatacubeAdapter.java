package org.integratedmodelling.adapter.datacube;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.adapter.datacube.api.IDatacube;
import org.integratedmodelling.adapter.datacube.api.IDatacube.SyncStrategy;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Availability;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IUrnAdapter;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.rest.ResourceReference.AvailabilityReference;

public abstract class GenericDatacubeAdapter implements IUrnAdapter {

    private String name;
    protected ChunkedDatacubeRepository datacube;

    /**
     * Maintains the datacube.
     */
    public interface MaintenanceService {

        /**
         * One-time setup if never done, or on demand
         */
        void setup(IDatacube datacube);

        /**
         * Initialization at each construction of the datacube service.
         */
        void initialize(IDatacube datacube);

        /**
         * At regular intervals, set through properties
         */
        void maintain(IDatacube datacube);

        /**
         * Check for the availability of the datacube service. Quickly please.
         * 
         * @return
         */
        boolean checkService(IDatacube datacube);

        /**
         * Before each request
         */
        void cleanupBefore(IDatacube datacube);

        /**
         * After each request
         */
        void cleanupAfter(IDatacube datacube);
    }

    protected GenericDatacubeAdapter(String name, ChunkedDatacubeRepository datacube) {
        this.name = name;
        this.datacube = datacube;
    }

    @Override
    public boolean isOnline(Urn urn) {
        return datacube.isOnline();
    }

    @Override
    public void encodeData(Urn urn, Builder builder, IGeometry geometry, IContextualizationScope scope) {

        // just in case
        if (!datacube.isOnline()) {
            scope.getMonitor().error(
                    "datacube is offline" + (datacube.getStatusMessage() == null ? "" : (": " + datacube.getStatusMessage())));
        }

        SyncStrategy strategy = datacube.getStrategy(urn.getResourceId(), geometry);
        if (strategy.getTimeToAvailabilitySeconds() < 0) {
            scope.getMonitor().error(name + " adapter cannot fulfill request for " + urn + ": resource unavailable");
        } else if (strategy.getTimeToAvailabilitySeconds() > 0) {
            AvailabilityReference availability = strategy.execute();
            if (availability.getAvailability() == Availability.DELAYED) {
                scope.getMonitor().addWait(availability.getRetryTimeSeconds());
            } else if (availability.getAvailability() == Availability.NONE) {
                scope.getMonitor().error(name + " adapter cannot fulfill request for " + urn + ": resource unavailable");
            }
        } else {
            /*
             * Execute hostia!
             */

        }
    }

    @Override
    public Type getType(Urn urn) {
        return datacube.getResourceType(urn);
    }

    @Override
    public IGeometry getGeometry(Urn urn) {
        return datacube.getResourceGeometry(urn);
    }

    @Override
    public String getDescription() {
        return datacube.getDescription();
    }

    @Override
    public Collection<String> getResourceUrns() {
        Set<String> ret = new HashSet<>();
        // TODO (really? should it be patterns?)
        return ret;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IResource contextualize(IResource resource, IGeometry scale, IGeometry overallScale, IObservable semantics) {

        /*
         * Logic should be: the first time that the availability for the current geometry isn't
         * immediate, enqueue the sync strategy for the overall scale. This may return delayed a
         * second time but guarantees that the sync is complete after the first wait, and the
         * strategy we get at encode() should be quick.
         */
        Urn urn = new Urn(resource.getUrn());
        SyncStrategy strategy = datacube.getStrategy(urn.getResourceId(), scale);
        AvailabilityReference availability = AvailabilityReference.immediate();
        if (strategy.getTimeToAvailabilitySeconds() < 0) {
            availability.setAvailability(Availability.NONE);
        } else if (strategy.getTimeToAvailabilitySeconds() > 0) {
            SyncStrategy overallStrategy = datacube.getStrategy(urn.getResourceId(), overallScale);
            availability = overallStrategy.execute();
        }
        ResourceReference ref = ((Resource) resource).getReference();
        ref.setAvailability(availability);
        return new Resource(ref);
    }

}
