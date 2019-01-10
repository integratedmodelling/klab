package org.integratedmodelling.klab.components.geospace.processing.osm;

import java.util.Map;

import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.communication.client.Client;
import org.integratedmodelling.klab.components.geospace.extents.Envelope;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.rest.SpatialExtent;

public enum Nominatim {

    INSTANCE;

    Client client = Client.create();

    public String geocode(IEnvelope envelope) {

        IEnvelope env = envelope.transform(Projection.getLatLon(), true);
        String url = "https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat="
                + env.getCenterCoordinates()[1] + "&lon=" + env.getCenterCoordinates()[0] + "&zoom="
                + env.getScaleRank();

        String ret = null;
        try {
            Map<?, ?> res = client.get(url, Map.class);
            if (res != null && res.containsKey("display_name")) {
                ret = res.get("display_name").toString();
            } else if (res != null && res.containsKey("name")) {
                ret = res.get("name").toString();
            } 
        } catch (Throwable t) {

        }

        return ret == null ? "Region of interest" : ret;
    }

    public String geocode(SpatialExtent region) {
        return geocode(Envelope.create(region.getEast(), region.getWest(), region.getSouth(), region
                .getNorth(), Projection.getLatLon()));
    }

}
