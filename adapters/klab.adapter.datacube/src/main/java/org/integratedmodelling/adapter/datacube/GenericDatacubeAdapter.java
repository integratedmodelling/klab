package org.integratedmodelling.adapter.datacube;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.adapter.datacube.api.IDatacube.ObservationStrategy;
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

	public abstract ChunkedDatacubeRepository getDatacube(Urn urn);

	public GenericDatacubeAdapter(String name) {
		this.name = name;
	}

	@Override
	public boolean isOnline(Urn urn) {
		return getDatacube(urn).isOnline();
	}

	@Override
	public IResource getResource(String urn) {
		Urn kurn = new Urn(urn);
		return getDatacube(kurn).getResource(urn);
	}

	@Override
	public void encodeData(Urn urn, Builder builder, IGeometry geometry, IContextualizationScope scope) {

		// just in case
		if (!getDatacube(urn).isOnline()) {
			scope.getMonitor().error("datacube is offline"
					+ (getDatacube(urn).getStatusMessage() == null ? "" : (": " + getDatacube(urn).getStatusMessage())));
		}

		ObservationStrategy strategy = getDatacube(urn).getStrategy(urn.getResourceId(), geometry);
		if (strategy.getTimeToAvailabilitySeconds() < 0) {
			scope.getMonitor().error(name + " adapter cannot fulfill request for " + urn + ": resource unavailable");
		} else if (strategy.getTimeToAvailabilitySeconds() > 0) {
			AvailabilityReference availability = strategy.buildCache();
			if (availability.getAvailability() == Availability.DELAYED) {
				scope.getMonitor().addWait(availability.getRetryTimeSeconds());
			} else if (availability.getAvailability() == Availability.NONE) {
				scope.getMonitor()
						.error(name + " adapter cannot fulfill request for " + urn + ": resource unavailable");
			}
		} else {
			strategy.execute(geometry, builder, scope);
		}
	}

	@Override
	public Type getType(Urn urn) {
		return getDatacube(urn).getResourceType(urn);
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
		 * Logic should be: the first time that the availability for the current
		 * geometry isn't immediate, enqueue the sync strategy for the overall scale.
		 * This may return delayed a second time but guarantees that the sync is
		 * complete after the first wait, and the strategy we get at encode() should be
		 * quick.
		 */
		Urn urn = new Urn(resource.getUrn());
		ObservationStrategy strategy = getDatacube(urn).getStrategy(urn.getResourceId(), scale);
		AvailabilityReference availability = AvailabilityReference.immediate();
		if (strategy.getTimeToAvailabilitySeconds() < 0) {
			availability.setAvailability(Availability.NONE);
		} else if (strategy.getTimeToAvailabilitySeconds() > 0) {
			ObservationStrategy overallStrategy = getDatacube(urn).getStrategy(urn.getResourceId(), overallScale);
			availability = overallStrategy.buildCache();
		}
		ResourceReference ref = ((Resource) resource).getReference();
		ref.setAvailability(availability);
		return new Resource(ref);
	}

}
