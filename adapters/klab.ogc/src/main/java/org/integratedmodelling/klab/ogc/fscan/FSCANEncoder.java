package org.integratedmodelling.klab.ogc.fscan;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResourceCatalog;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.ogc.FSCANAdapter;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Path;

public class FSCANEncoder implements IResourceEncoder {

    public static final String FEATURE_ID = "fscan.feature_id";
    public static final String COLLECTION_ID = "fscan.collection_id";

    @Override
    public boolean isOnline(IResource resource, IMonitor monitor) {
        if (FSCANAdapter.isOnline()) {
            // TODO check for data in resource
            return true;
        }
        return false;
    }

    @Override
    public void getEncodedData(IResource resource, Map<String, String> urnParameters, IGeometry geometry, Builder builder,
            IContextualizationScope context) {

        if (!FSCANAdapter.isOnline()) {
            return;
        }

        Urn urn = new Urn(resource.getUrn());

        /*
         * Formats:
         * 
         * no parameters -> get the largest shape that fits the bounding box at scale level in its
         * pre-simplified form (or use #simplify=false).
         *
         * #id=nnnn,collection=abcd[,simplify=false] -> get the specific shape, simplified to fit
         * the incoming resolution (or not if false)
         * 
         * #within[,id=nnnn,collection=abcd,simplify=false] -> find the largest shape in bounding
         * box (or passed), then return all the shapes of the level below that intersect it; use
         * resolution-dependent simplification or none
         * 
         * #level=n[&simplify=false] -> return all in bounding box at given level,
         * resolution-dependent simplification or none
         */

        boolean intersect = urnParameters.containsKey("intersect") ? Boolean.getBoolean(urnParameters.get("intersect")) : false;
        boolean simplify = urnParameters.containsKey("simplify") ? Boolean.getBoolean(urnParameters.get("simplify")) : true;
        int level = urnParameters.containsKey("level") ? Integer.parseInt(urnParameters.get("level")) : -1;
        String id = urnParameters.get("id");
        String collection = urnParameters.get("collection");
        
        /*
         * default behavior: find the shape that best fits the context
         */
        IScale scale = geometry instanceof IScale ? (IScale) geometry : Scale.create(geometry);
        IEnvelope envelope = scale.getSpace().getShape().getEnvelope().transform(Projection.getLatLon(), false);
        IShape shape = FSCANAdapter.getPostgis().getLargestInScale(urn, envelope);
        // Logging.INSTANCE.info("Query in " + envelope + " returned " + shape);
        // Logging.INSTANCE.info("Builder class is " + builder.getClass());
        if (shape != null) {
            Builder bb = builder.startObject(context.getTargetName() == null ? "result" : context.getTargetName(),
                    shape.getMetadata().get(IMetadata.DC_NAME, String.class), Scale.create(shape));
            for (String key : shape.getMetadata().keySet()) {
                bb.withMetadata(key, shape.getMetadata().get(key));
            }
            bb.finishObject();
        }
    }

    public long indexShapes(IResource resource, IResourceCatalog catalog) {

        Logging.INSTANCE.info("Start FSCAN indexing for " + resource.getUrn() + "...");

        if (!FSCANAdapter.isOnline()) {
            Logging.INSTANCE.warn("FSCAN indexing aborted: Postgres service is not online");
            return -1;
        }

        Urn urn = new Urn(resource.getUrn());

        /*
         * reset the boundary tables with bounding box and simplified polygons for all shapes
         */
        FSCANAdapter.getPostgis().resetBoundaries(urn);

        long ret = 0;
        Set<File> files = new HashSet<>();
        for (String key : resource.getParameters().keySet()) {
            if (key.startsWith("filesource.import")) {
                File file = ((Resource) resource).getLocalFile(Path.getLeading(key, '.') + '.' + "name");
                if (file != null && !files.contains(file)) {
                    int level = resource.getParameters().get(Path.getLeading(key, '.') + '.' + "level", Integer.class);
                    String nameExpression = resource.getParameters().get(Path.getLeading(key, '.') + '.' + "label", String.class);
                    files.add(file);
                    ret += FSCANAdapter.getPostgis().indexBoundaries(file, urn, nameExpression, level);
                }
            }
        }

        Logging.INSTANCE.info("FSCAN data ingestion complete for " + urn + ": indexing");

        /*
         * rebuild indices
         */
        FSCANAdapter.getPostgis().reindexBoundaries(urn);

        Logging.INSTANCE.info("FSCAN indexing complete");

        return ret;
    }

    @Override
    public IResource contextualize(IResource resource, IScale scale, IArtifact targetObservation,
            Map<String, String> urnParameters, IContextualizationScope scope) {
        // TODO Auto-generated method stub
        return resource;
    }

}
