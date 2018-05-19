package org.integratedmodelling.klab.raster.wcs;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.jxpath.JXPathContext;
import org.geotools.wcs.WCSConfiguration;
import org.geotools.xml.Parser;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.components.geospace.extents.Envelope;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.utils.NumberUtils;

public class WCSService {

	private List<Throwable> errors = new ArrayList<>();
	Map<String, WCSLayer> layers = new HashMap<>();
	private String serviceUrl;
	private Version version;

	public class WCSLayer {

		String name;
		IEnvelope wgs84envelope;

		// set to true when a getCoverage has been sent
		boolean finished = false;

		@Override
		public String toString() {
			return name + " " + wgs84envelope;
		}
	}

	@SuppressWarnings("unchecked")
	public WCSService(String serviceUrl, Version version) {

		this.serviceUrl = serviceUrl;
		this.version = version;
		
		try {
			Parser parser = new Parser(new WCSConfiguration());
			URL url = new URL(serviceUrl + "?service=WCS&request=getCapabilities&version=" + version);
			Map<?, ?> capabilitiesType = (Map<?, ?>) parser.parse(url.openStream());

			JXPathContext context = JXPathContext.newContext(capabilitiesType);
			for (Iterator<Object> it = context.iterate("Contents/CoverageSummary"); it.hasNext();) {

				Map<String, Object> item = (Map<String, Object>) it.next();
				Object name = item.get(version.getMajor() >= 2 ? "CoverageId" : "Identifier");
				Object bbox = item.get("WGS84BoundingBox");

				if (name instanceof String && bbox instanceof Map) {
					WCSLayer layer = new WCSLayer();
					layer.name = name.toString();
					double[] upperCorner = NumberUtils
							.doubleArrayFromString(((Map<?, ?>) bbox).get("UpperCorner").toString(), "\\s+");
					double[] lowerCorner = NumberUtils
							.doubleArrayFromString(((Map<?, ?>) bbox).get("LowerCorner").toString(), "\\s+");
					layer.wgs84envelope = Envelope.create(lowerCorner[0], upperCorner[0], lowerCorner[1],
							upperCorner[1], Projection.getLatLon());
					layers.put(layer.name, layer);
				}
			}
		} catch (Throwable e) {
			errors.add(e);
		}
	}

	public boolean hasErrors() {
		return errors.size() > 0;
	}

	public Collection<WCSLayer> getLayers() {
		return layers.values();
	}
}