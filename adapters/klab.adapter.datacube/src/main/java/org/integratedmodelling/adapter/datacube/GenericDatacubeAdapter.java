package org.integratedmodelling.adapter.datacube;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
    protected Datacube datacube;

    protected GenericDatacubeAdapter(String name, Datacube datacube) {
        this.name = name;
        this.datacube = datacube;
    }

    @Override
    public boolean isOnline(Urn urn) {
        return datacube.isOnline();
    }

    @Override
    public void encodeData(Urn urn, Builder builder, IGeometry geometry, IContextualizationScope scope) {

        AvailabilityReference availability = datacube.availability.checkAvailability(geometry,
                datacube.urnTranslation.getVariable(urn, geometry), datacube.ingestion);
        if (availability.getAvailability() == Availability.DELAYED) {
            scope.getMonitor().addWait(availability.getRetryTimeSeconds());
        } else if (availability.getAvailability() == Availability.NONE) {
            scope.getMonitor().error(name + " adapter cannot fulfill request for " + urn + ": resource unavailable");
        } else {
            if (availability.getAvailability() == Availability.PARTIAL) {
                scope.getMonitor().warn(name + " adapter can only partially fulfill '" + urn + "' request");
            }
            datacube.encoding.encodeData(urn, builder, geometry, scope);
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
        AvailabilityReference availability = datacube.availability.checkAvailability(overallScale,
                datacube.urnTranslation.getVariable(new Urn(resource.getUrn()), overallScale), datacube.ingestion);
        if (availability.getAvailability() != Availability.COMPLETE) {
            ResourceReference ref = ((Resource)resource).getReference();
            ref.setAvailability(availability);
            return new Resource(ref);
        }
        return resource;
    }

}
