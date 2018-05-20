package org.integratedmodelling.klab.raster.wcs;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.jxpath.JXPathContext;
import org.geotools.wcs.WCSConfiguration;
import org.geotools.xml.Parser;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IProjection;
import org.integratedmodelling.klab.components.geospace.extents.Envelope;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.utils.NumberUtils;

public class WCSService {

	private List<Throwable> errors = new ArrayList<>();
	Map<String, WCSLayer> layers = new HashMap<>();
	private String serviceUrl;
	private Version version;
	Parser parser;

	public class WCSLayer {

		// identifier from capabilities (simple, no namespace)
		private String name;
		// identifier from describeCoverage, to use for retrieval (includes namespace in
		// Geoserver)
		private String identifier;
		// envelope in WGS84 from capabilities
		private IEnvelope wgs84envelope;
		// if this is empty, we don't know from the server and we should just try,
		// signaling an error
		private Set<IProjection> supportedProjections = new HashSet<>();

		// if for any reason we can't parse these, they will be set to the WSG84 from the capabilities
		private IEnvelope originalEnvelope;
		private IProjection originalProjection;
		
		private Set<Double> nodata = new HashSet<>();

		// set to true when a getCoverage response has been parsed
		private boolean finished = false;
		
		
		private boolean error = false;

		public String getName() {
			return name;
		}

		public String getIdentifier() {
			describeCoverage();
			return identifier;
		}

		public IEnvelope getWgs84envelope() {
			return wgs84envelope;
		}

		public Set<IProjection> getSupportedProjections() {
			describeCoverage();
			return supportedProjections;
		}

		public IEnvelope getOriginalEnvelope() {
			describeCoverage();
			return originalEnvelope;
		}

		public IProjection getOriginalProjection() {
			describeCoverage();
			return originalProjection;
		}

		public Set<Double> getNodata() {
			describeCoverage();
			return nodata;
		}

		public boolean isError() {
			describeCoverage();
			return error;
		}

		// TODO change to private
		private void describeCoverage() {
			if (!finished) {
				finished = true;
				try {
					URL url = new URL(serviceUrl + "?service=WCS&version=" + version + "&request=DescribeCoverage&"
							+ (version.getMajor() >= 2 ? "coverageId=" : "identifiers=") + name);
					try (InputStream input = url.openStream()) {
						Map<?, ?> coverage = (Map<?, ?>) parser.parse(input);
						if (version.getMajor() >= 2) {
							parseV2(coverage);
						} else {
							parseV1(coverage);
						}
//						System.out.println(JsonUtils.printAsJson(coverage));
					} catch (IOException e) {
						error = true;
					}
				} catch (Throwable e) {
					error = true;
				}
			}
		}

		private void parseV1(Map<?, ?> coverage) {

 			this.identifier = coverage.get("Identifier").toString();
			JXPathContext context = JXPathContext.newContext(coverage);
			if (coverage.get("SupportedCRS") instanceof Collection) {
				for (Object crs : ((Collection<?>) coverage.get("SupportedCRS"))) {
					IProjection projection = Projection.create(crs.toString());
					this.supportedProjections.add(projection);
				}
			}

			this.originalEnvelope = wgs84envelope;
			this.originalProjection = Projection.getLatLon();

			for (Iterator<?> it = context.iterate("Domain/BoundingBox"); it.hasNext();) {

				Map<?, ?> bbox = (Map<?, ?>) it.next();

				/*
				 * ignore the EPSG::4326 which has swapped coordinates, and let the other specs
				 * override the defaults
				 */
				if (bbox.get("crs") instanceof String && !bbox.get("crs").equals("urn:ogc:def:crs:EPSG::4326")) {
					this.originalProjection = Projection.create(bbox.get("crs").toString());
					double[] upperCorner = NumberUtils
							.doubleArrayFromString(((Map<?, ?>) bbox).get("UpperCorner").toString(), "\\s+");
					double[] lowerCorner = NumberUtils
							.doubleArrayFromString(((Map<?, ?>) bbox).get("LowerCorner").toString(), "\\s+");
					this.originalEnvelope = Envelope.create(lowerCorner[0], upperCorner[0], lowerCorner[1],
							upperCorner[1], Projection.getLatLon());
				}
			}

			if (coverage.get("Range") instanceof Map) {
				Map<?, ?> range = (Map<?, ?>) coverage.get("Range");
				if (range.containsKey("NullValue") && !range.get("NullValue").toString().contains("Infinity")) {
					this.nodata.add(Double.parseDouble(range.get("NullValue").toString()));
				}
				// TODO interpolation methods and default
				// TODO Axis contains band info
			}

			// TODO the rest: Domain/GridCRS (doesn't seem to add anything useful)
			// SupportedFormat
			// Keywords (for URN metadata)
		}

		private void parseV2(Map<?, ?> coverage) {

			// TODO Auto-generated method stub
			JXPathContext context = JXPathContext.newContext(coverage);
			this.identifier = this.name;
			this.originalEnvelope = this.wgs84envelope;
			this.originalProjection = Projection.getLatLon();

			if (coverage.get("CoverageId") instanceof String) {
				this.identifier = coverage.get("CoverageId").toString();
			}

			// THIS: (from ArcSDE)
//			{
//				  "rangeType" : {
//				    "field" : [ {
//				      "name" : "band_1",
//				      "Quantity" : {
//				        "uom" : "unknown",
//				        "description" : "Band 1",
//				        "constraint" : "0.0 255.0"
//				      }
//				    }, {
//				      "name" : "band_2",
//				      "Quantity" : {
//				        "uom" : "unknown",
//				        "description" : "Band 2",
//				        "constraint" : "0.0 255.0"
//				      }
//				    }, {
//				      "name" : "band_3",
//				      "Quantity" : {
//				        "uom" : "unknown",
//				        "description" : "Band 3",
//				        "constraint" : "0.0 255.0"
//				      }
//				    } ]
//				  },
//				  "boundedBy" : {
//				    "lowerCorner" : "-14497453.9106248 2480608.8817100283",
//				    "srsName" : "http://www.opengis.net/def/crs/EPSG/0/3857",
//				    "srsDimension" : "2",
//				    "axisLabels" : "x y",
//				    "uomLabels" : "",
//				    "upperCorner" : "-7087933.9106248002 6960328.8817100283"
//				  },
//				  "CoverageId" : "Coverage14",
//				  "domainSet" : {
//				    "axisLabels" : "x y",
//				    "origin" : {
//				      "srsName" : "http://www.opengis.net/def/crs/EPSG/0/3857",
//				      "pos" : "-14497438.9106248 6960313.8817100283",
//				      "id" : "grid_origin_Coverage14"
//				    },
//				    "offsetVector" : [ "30 0", "0 -30" ],
//				    "id" : "grid_Coverage14",
//				    "dimension" : "2",
//				    "limits" : {
//				      "high" : "246983 149323",
//				      "low" : "0 0"
//				    }
//				  },
//				  "id" : "Coverage14",
//				  "ServiceParameters" : {
//				    "nativeFormat" : "image/tiff",
//				    "CoverageSubtype" : "RectifiedGridCoverage"
//				  }
//				}
		}

		@Override
		public String toString() {
			return name + " " + wgs84envelope;
		}

		/**
		 * Build and return the geometry for the layer.
		 * 
		 * @return
		 */
		public IGeometry getGeometry() {
			// TODO Auto-generated method stub
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public WCSService(String serviceUrl, Version version) {

		this.serviceUrl = serviceUrl;
		this.version = version;

		try {
			this.parser = new Parser(new WCSConfiguration());
			URL url = new URL(serviceUrl + "?service=WCS&request=getCapabilities&version=" + version);

			try (InputStream input = (url.openStream())) {
				Map<?, ?> capabilitiesType = (Map<?, ?>) parser.parse(input);

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
			} catch (IOException e) {
				errors.add(e);
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
	
	public WCSLayer getLayer(String id) {
		return layers.get(id);
	}
}