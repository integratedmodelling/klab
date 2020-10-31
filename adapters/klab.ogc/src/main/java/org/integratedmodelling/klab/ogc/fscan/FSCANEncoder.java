package org.integratedmodelling.klab.ogc.fscan;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.ogc.integration.Postgis;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Path;

public class FSCANEncoder implements IResourceEncoder {

	public static final String FEATURE_ID = "fscan.feature_id";
	public static final String COLLECTION_ID = "fscan.collection_id";

	private Postgis postgis = null;

	private boolean isOnline() {
		if (Postgis.isEnabled()) {
			this.postgis = Postgis.create();
			return this.postgis.isOnline();
		}
		return false;
	}

	@Override
	public boolean isOnline(IResource resource, IMonitor monitor) {
		if (isOnline()) {
			// TODO check for data in resource
			return true;
		}
		return false;
	}

	@Override
	public void getEncodedData(IResource resource, Map<String, String> urnParameters, IGeometry geometry,
			Builder builder, IContextualizationScope context) {

		if (!isOnline()) {
			return;
		}
		
		Urn urn = new Urn(resource.getUrn());

//		if (false && resource.getParameters().containsKey("totalshapes")) {
//			long n = indexShapes(resource);
//			if (n >= 0) {
//				resource.getParameters().put("totalshapes", n);
//				Resources.INSTANCE.getCatalog(resource).update(resource,
//						"spatial indices generated for " + n + " polygons");
//			}
//		}

		/*
		 * default behavior: find the shape that best fits the context
		 */
		IScale scale = geometry instanceof IScale ? (IScale) geometry : Scale.create(geometry);
		IEnvelope envelope = scale.getSpace().getShape().getEnvelope().transform(Projection.getLatLon(), false);
		IShape shape = postgis.getLargestInScale(urn, envelope);
		if (shape != null) {
			builder.startObject("result", shape.getMetadata().get(IMetadata.DC_NAME, String.class), Scale.create(shape))
					.finishObject();
		}

	}

	public long indexShapes(IResource resource) {

		Logging.INSTANCE.info("Start FSCAN indexing for " + resource.getUrn() + "...");

		if (!isOnline()) {
			Logging.INSTANCE.warn("FSCAN indexing aborted: Postgres service is not online");
			return -1;
		}

		Urn urn = new Urn(resource.getUrn());

		/*
		 * reset the boundary tables with bounding box and simplified polygons for all
		 * shapes
		 */
		postgis.resetBoundaries(urn);

		long ret = 0;
		Set<File> files = new HashSet<>();
		for (String key : resource.getParameters().keySet()) {
			if (key.startsWith("filesource.import")) {
				File file = ((Resource) resource).getLocalFile(Path.getLeading(key, '.') + '.' + "name");
				if (file != null && !files.contains(file)) {
					int level = resource.getParameters().get(Path.getLeading(key, '.') + '.' + "level", Integer.class);
					String nameExpression = resource.getParameters().get(Path.getLeading(key, '.') + '.' + "label",
							String.class);
					files.add(file);
					ret += postgis.indexBoundaries(file, urn, nameExpression, level);
				}
			}
		}

		Logging.INSTANCE.info("FSCAN data ingestion complete for " + urn + ": indexing");

		/*
		 * rebuild indices
		 */
		postgis.reindexBoundaries(urn);
		
		Logging.INSTANCE.info("FSCAN indexing complete");

		return ret;
	}

}
