package org.integratedmodelling.klab.components.geospace.geocoding;

import java.util.Map;

import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.communication.client.Client;
import org.integratedmodelling.klab.components.geospace.extents.Envelope;
import org.integratedmodelling.klab.components.geospace.extents.Shape;

public class OSMNamesGeocodingService extends GeocodingService {

	/*
	 * Fast client is used for nominating service, we don't want to wait too much
	 * for it. TODO implement an asynchronous way to update context information
	 */
	Client fastClient = Client.createCustomTimeoutClient(2000);

	protected OSMNamesGeocodingService(double maxCallsPerSecond) {
		super(maxCallsPerSecond);
	}

	@Override
	public IShape getAnnotatedRegion(IEnvelope envelope, IMonitor monitor) {

		String url = "https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat="
				+ envelope.getCenterCoordinates()[1] + "&lon=" + envelope.getCenterCoordinates()[0] + "&zoom="
				+ closest(envelope.getScaleRank());

		/*
		 * Let exceptions be thrown, don't return null without one.
		 */
		IShape ret = null;
		String name = null;
		System.out.println(url);
		try {
			Map<?, ?> res = fastClient.get(url, Map.class);
			if (res != null && res.containsKey("display_name")) {
				name = res.get("display_name").toString();
			} else if (res != null && res.containsKey("name")) {
				name = res.get("name").toString();
			}
			ret = Shape.create((Envelope) envelope);
			ret.getMetadata().put(IMetadata.DC_DESCRIPTION, name);
			// this tells the view to not paint the shape
			ret.getMetadata().put(IMetadata.IM_GEOGRAPHIC_AREA, Boolean.FALSE);
		} catch (Throwable t) {

		}
		return ret;
	}

	private static int[] levels = new int[] { 3, 5, 8, 10, 14, 16, 17, 18 };

	protected int closest(int scaleRank) {

		int n = 0;
		for (int i : levels) {
			if (scaleRank == i) {
				return i;
			} else if (i > scaleRank) {
				return n > 0 ? levels[n - 1] : 3;
			}
			n++;
		}
		return 18;
	}

}
