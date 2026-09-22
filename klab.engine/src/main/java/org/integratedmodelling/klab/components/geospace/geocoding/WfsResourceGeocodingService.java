package org.integratedmodelling.klab.components.geospace.geocoding;

import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.components.geospace.extents.Envelope;
import org.integratedmodelling.klab.scale.Scale;

public class WfsResourceGeocodingService extends ResourceGeocodingService {

	protected WfsResourceGeocodingService(String urn, double maxCallsPerSecond) {
		super(urn + "#intersect=false", maxCallsPerSecond);
	}

	@Override
	protected IScale getLookupScale(IEnvelope envelope) {

		/*
		 * Construct a very small envelope centered on the incoming envelope.
		 */
		double[] center = envelope.getCenterCoordinates();

	    double cx = center[0];
	    double cy = center[1];

	    double delta = envelope.metersToDistance(10.0);

	    IEnvelope lookup = Envelope.create(
	        cx + delta,
	        cx - delta,
	        cy - delta,
	        cy + delta,
	        envelope.getProjection()
	    );
	    return Scale.create(lookup.asShape());
	}
}