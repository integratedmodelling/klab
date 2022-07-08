package org.integratedmodelling.klab.components.geospace.geocoding;

import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.encoding.VisitingDataBuilder;
import org.integratedmodelling.klab.scale.Scale;

public class ResourceGeocodingService extends GeocodingService {

    private String urn;

    protected ResourceGeocodingService(String urn, double maxCallsPerSecond) {
        super(maxCallsPerSecond);
        this.urn = urn;
    }

    @Override
    public IShape getAnnotatedRegion(IEnvelope envelope, IMonitor monitor) {

        // Logging.INSTANCE.info("Attempting to geocode " + envelope + " from resource " + urn);

        IKlabData data = Resources.INSTANCE.getResourceData(urn, new VisitingDataBuilder(), IArtifact.Type.OBJECT, "result",
                Scale.create(envelope.asShape()), monitor);

        if (data.getArtifact() != null) {
            IGeometry geometry = data.getArtifact().getGeometry();
            if (geometry != null) {
                IShape ret = geometry instanceof IScale
                        ? ((IScale) geometry).getSpace().getShape()
                        : Scale.create(geometry).getSpace().getShape();
                if (ret != null) {
                    ret.getMetadata().put(IMetadata.DC_DESCRIPTION, ((IObjectArtifact) data.getArtifact()).getName());
                    return ret;
                } else {
                    // Logging.INSTANCE.warn("Could not geocode " + envelope + ": null shape from
                    // resource with geometry " + geometry);
                }
            } else {
                // Logging.INSTANCE.warn("Could not geocode " + envelope + ": null geometry from
                // resource");
            }
        } else {
            // Logging.INSTANCE.warn("Could not geocode " + envelope + ": null response from
            // getResourceData");
        }

        return null;
    }

}
