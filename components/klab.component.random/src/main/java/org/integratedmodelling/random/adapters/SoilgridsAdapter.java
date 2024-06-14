package org.integratedmodelling.random.adapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IUrnAdapter;
import org.integratedmodelling.klab.api.extensions.UrnAdapter;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.rest.ResourceReference;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

@UrnAdapter(type = "soilgrids", version = Version.CURRENT)
public class SoilgridsAdapter implements IUrnAdapter{

    public static final String NAME = "soilgrids";

    public static final String ENDPOINT = "https://rest.isric.org/soilgrids/v2.0";

    public static final String CLASSIFICATION_QUERY = "/classification/query";
    public static final String PROPERTIES_LAYER = "/properties/layers";
    public static final String PROPERTIES_QUERY = "/properties/query";

    public static final String LONGITUDE = "lon";
    public static final String TATITUDE = "lat";
    public static final String NUMBER_CLASSES = "number_classes";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public IResource getResource(String urn) {
        Urn kurn = new Urn(urn);
        ResourceReference ref = new ResourceReference();
        ref.setUrn(urn.toString());
        ref.setAdapterType(getName());
        ref.setLocalName(kurn.getResourceId());
        ref.setGeometry("#S0");
        ref.setVersion(Version.CURRENT);
        ref.setType(getType(kurn));
        return new Resource(ref);
    }

    @Override
    public IResource contextualize(IResource resource, IGeometry scale, IGeometry overallScale, IObservable semantics) {
        return resource;
    }

    @Override
    public boolean isOnline(Urn urn) {
        // TODO
        return true;
    }

    @Override
    public void encodeData(Urn urn, Builder builder, IGeometry geometry, IContextualizationScope scope) {
        Map<String, String> parameters = new HashMap<>();
        if (urn.getParameters().containsKey(NUMBER_CLASSES)) {
            parameters.put(NUMBER_CLASSES, urn.getParameters().get(NUMBER_CLASSES));
        }

        ISpace space = (ISpace) geometry.getDimension(IGeometry.Dimension.Type.SPACE);
        double[] centroid = space.getStandardizedCentroid();

        HttpResponse<JsonNode> response = Unirest.get(ENDPOINT + CLASSIFICATION_QUERY +
                "?" + LONGITUDE + "=" + centroid[0] + "&" + TATITUDE + "=" + centroid[1] +
                "&" + NUMBER_CLASSES + "=" + parameters.get(NUMBER_CLASSES))
                .asJson();
        if (!response.isSuccess()) {
            throw new KlabResourceNotFoundException("Cannot retrieve information from Soilgrids resources.");
        }

        JSONObject responseJSON = response.getBody().getObject();
        String className = responseJSON.getString("wrb_class_name");
        int classValue = responseJSON.getInt("wrb_class_value");

        builder.add(classValue);
    }

    @Override
    public Type getType(Urn urn) {
        // TODO we should translate this value
        return Type.NUMBER;
    }

    @Override
    public IGeometry getGeometry(Urn urn) {
        return Geometry.create("S0");
    }

    @Override
    public String getDescription() {
        return "A system for digital soil mapping based on global compilation of soil profile data and environmental layers";
    }

    @Override
    public Collection<String> getResourceUrns() {
        List<String> ret = new ArrayList<>();
        // TODO
        return ret;
    }

}
