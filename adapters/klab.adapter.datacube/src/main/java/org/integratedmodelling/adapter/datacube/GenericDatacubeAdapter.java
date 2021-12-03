package org.integratedmodelling.adapter.datacube;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.adapter.datacube.api.IDatacube.ObservationStrategy;
import org.integratedmodelling.klab.Logging;
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
    private boolean dynamic;
    
    public abstract ChunkedDatacubeRepository getDatacube(Urn urn);

    public GenericDatacubeAdapter(String name, boolean dynamic) {
        this.name = name;
        this.dynamic = dynamic;
    }

    @Override
    public boolean isOnline(Urn urn) {
        return getDatacube(urn).isOnline();
    }

    @Override
    public IResource getResource(String urn) {
        Urn kurn = new Urn(urn);
        return getDatacube(kurn).getResource(urn, this.dynamic);
    }

    @Override
    public void encodeData(Urn urn, Builder builder, IGeometry geometry, IContextualizationScope scope) {

    	// FIXME remove
    	Logging.INSTANCE.info("entering encodeData from " + urn);
    	
        // just in case
        if (!getDatacube(urn).isOnline()) {
            scope.getMonitor().error("datacube is offline"
                    + (getDatacube(urn).getStatusMessage() == null ? "" : (": " + getDatacube(urn).getStatusMessage())));
        }

        for (String variableName : getDatacube(urn).getVariableNames(urn)) {

        	// FIXME remove
        	Logging.INSTANCE.info("   building strategy for " + variableName);
        	
            ObservationStrategy strategy = getDatacube(urn).getStrategy(variableName, geometry);

        	// FIXME remove
        	Logging.INSTANCE.info("strategy is " + strategy);

            
            if (strategy.getTimeToAvailabilitySeconds() < 0) {
                scope.getMonitor().error(name + " adapter cannot fulfill request for " + urn + ": resource unavailable");
            } else if (strategy.getTimeToAvailabilitySeconds() > 0) {
                AvailabilityReference availability = strategy.buildCache();
                if (availability.getAvailability() == Availability.DELAYED) {
                    scope.getMonitor().addWait(availability.getRetryTimeSeconds());
                } else if (availability.getAvailability() == Availability.NONE) {
                    scope.getMonitor().error(name + " adapter cannot fulfill request for " + urn + ": resource unavailable");
                }
            } else {
                strategy.execute(urn, geometry, builder, scope);
            }
        }
    }

    @Override
    public Type getType(Urn urn) {
        return getDatacube(urn).getResourceType(urn, this.dynamic);
    }

    @Override
    public IGeometry getGeometry(Urn urn) {
        return getDatacube(urn).getResourceGeometry(urn);
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
        AvailabilityReference availability = AvailabilityReference.immediate();

        for (String variableName : getDatacube(urn).getVariableNames(urn)) {
            ObservationStrategy strategy = getDatacube(urn).getStrategy(variableName, scale);
            if (strategy.getTimeToAvailabilitySeconds() < 0) {
                availability.setAvailability(Availability.NONE);
            } else if (strategy.getTimeToAvailabilitySeconds() > 0) {
                ObservationStrategy overallStrategy = getDatacube(urn).getStrategy(variableName, overallScale);
                AvailabilityReference newav = overallStrategy.buildCache();
                mergeAvailability(newav, availability);
            }
        }
        ResourceReference ref = ((Resource) resource).getReference();
        ref.setAvailability(availability);
        return new Resource(ref);
    }

    protected void mergeAvailability(AvailabilityReference source, AvailabilityReference destination) {

        if (destination.getAvailability() == Availability.NONE) {
            return;
        }

        switch(source.getAvailability()) {
        case COMPLETE:
            // leave as is
            break;
        case DELAYED:
            if (destination.getAvailability() != Availability.PARTIAL) {
                destination.setAvailability(Availability.DELAYED);
            }
            destination.setRetryTimeSeconds(destination.getRetryTimeSeconds() + source.getRetryTimeSeconds());
            break;
        case NONE:
            destination.setAvailability(Availability.NONE);
            break;
        case PARTIAL:
            if (destination.getAvailability() == Availability.COMPLETE) {
                destination.setAvailability(Availability.PARTIAL);
            }
            break;
        default:
            break;

        }
    }

}
