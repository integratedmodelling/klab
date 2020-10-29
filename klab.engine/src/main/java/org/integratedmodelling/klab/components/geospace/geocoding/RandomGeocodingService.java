package org.integratedmodelling.klab.components.geospace.geocoding;

import java.util.Collection;
import java.util.Random;

import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.geospace.random.RandomShapes;

import jfortune.Fortune;

public class RandomGeocodingService extends GeocodingService {

	Fortune fortune = new Fortune(new String[] {});
	Random random = new Random();

	protected RandomGeocodingService(double maxCallsPerSecond) {
		super(maxCallsPerSecond);
	}

	@Override
	public IShape getAnnotatedRegion(IEnvelope envelope, IMonitor monitor) {
		String nam = fortune.getCookie().toString();
		Collection<IShape> results = RandomShapes.INSTANCE.create(envelope, 1, 1, 1, 32 + (int)(random.nextDouble() * 200));
		IShape ret = results.size() == 0 ? null : results.iterator().next();
		if (ret != null) {
			ret.getMetadata().put(IMetadata.DC_DESCRIPTION, nam);
		}
		return ret;
	}

}
