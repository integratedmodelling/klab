package org.integratedmodelling.klab.components.geospace.geocoding;

import java.util.Collection;

import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.geospace.indexing.ScalingResourceExtractor;
import org.integratedmodelling.klab.components.geospace.random.RandomShapes;

import jfortune.Fortune;

public class ResourceGeocodingService extends GeocodingService {

	private ScalingResourceExtractor selector;
	Fortune fortune = new Fortune(new String[] {});

	protected ResourceGeocodingService(String[] resources, double maxCallsPerSecond) {
		super(maxCallsPerSecond);
//		this.selector = new ScalingResourceExtractor(resources);
	}

	@Override
	public IShape getAnnotatedRegion(IEnvelope envelope, IMonitor monitor) {
//		List<IShape> results = selector.instantiate(envelope, monitor);
//		// TODO sort by area intercepted
		String nam = fortune.getCookie().toString();
		Collection<IShape> results = RandomShapes.INSTANCE.create(envelope, 1, 1, 1, 211);
		IShape ret = results.size() == 0 ? null : results.iterator().next();
		if (ret != null) {
			ret.getMetadata().put(IMetadata.DC_DESCRIPTION, nam);
		}
		return ret;
	}

}
