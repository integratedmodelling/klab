package org.integratedmodelling.klab.components.geospace.geocoding;

import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.geospace.indexing.ScalingResourceExtractor;

public class ResourceGeocodingService extends GeocodingService {

	private ScalingResourceExtractor selector;

	protected ResourceGeocodingService(String[] resources, double maxCallsPerSecond) {
		super(maxCallsPerSecond);
//		this.selector = new ScalingResourceExtractor(resources);
	}

	@Override
	public IShape getAnnotatedRegion(IEnvelope envelope, IMonitor monitor) {
//		List<IShape> results = selector.instantiate(envelope, monitor);
//		return results.size() == 0 ? null : results.iterator().next();
		return null;
	}

}
