package org.integratedmodelling.random.adapters;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IUrnAdapter;
import org.integratedmodelling.klab.api.extensions.UrnAdapter;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.exceptions.KlabMissingCredentialsException;
import org.integratedmodelling.klab.exceptions.KlabResourceAccessException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.rest.ExternalAuthenticationCredentials;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.scale.Scale;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONException;
import kong.unirest.json.JSONObject;

@UrnAdapter(type = "recreationidb", version = Version.CURRENT)
public class RecreationIDBAdapter implements IUrnAdapter {

	public static final String NAME = "ridb";

	public static final String RECAREAS = "recreation.areas";

	public static final String SITES = "sites";

	public static final String LIMIT = "limit";
	public static final String OFFSET = "offset";
	public static final String STATE = "state";
	public static final String ACTIVITY = "activity";
	public static final String RADIUS = "radius";

	public static final String APIKEY = "apikey";

	// TODO: complete other possible namespaces.
	public static String[] namespace_ids = new String[] { RECAREAS };
	public static String[] area_attribute_ids = new String[] { LIMIT, OFFSET, STATE, ACTIVITY, RADIUS };

	public RecreationIDBAdapter() {
		Arrays.sort(area_attribute_ids);
		Arrays.sort(namespace_ids);
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public boolean isOnline(Urn urn) {
		// TODO
		return true;
	}

	@Override
	public void encodeData(Urn urn, Builder builder, IGeometry geometry, IContextualizationScope scope) {

		Map<String, String> parameters = new HashMap<>();
		if (urn.getParameters().containsKey(LIMIT)) {
			parameters.put(LIMIT, urn.getParameters().get(LIMIT));
		}
		if (urn.getParameters().containsKey(OFFSET)) {
			parameters.put(OFFSET, urn.getParameters().get(OFFSET));
		}
		if (urn.getParameters().containsKey(ACTIVITY)) {
			parameters.put(ACTIVITY, urn.getParameters().get(ACTIVITY));
		}
		if (urn.getParameters().containsKey(RADIUS)) {
			parameters.put(RADIUS, urn.getParameters().get(RADIUS));
		} else {
			parameters.put(RADIUS, "0.0");
		}


		IScale scale = geometry instanceof IScale ? (IScale) geometry : Scale.create(geometry);
        Space space = (Space) geometry.getDimensions().stream().filter(d -> d instanceof Space).findFirst().orElseThrow();
        IEnvelope envelope = space.getEnvelope();

		if (scale.getSpace() != null) {
			RecreationIDB ridb = new RecreationIDB();
			ExternalAuthenticationCredentials credentials = Authentication.INSTANCE.getCredentials(ridb.getServiceURL());
			if (credentials == null) {
				throw new KlabMissingCredentialsException("API key for the Recreation Information Database is missing.");
			}
			String apiKey = credentials.getCredentials().get(0);

			List<String> inputs = new ArrayList<>();
			try {
                inputs = buildRecreationIDBInput(parameters, envelope);
			} catch (UnsupportedEncodingException e) {
				throw new KlabValidationException("Failed to encode the parameters.");
			}

			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			inputs.forEach(input -> data.addAll(ridb.recreationAreas(input, apiKey).getData()));

			IShape shape;

			for (Map<String, Object> area : data) {

				double lat = (double) area.get("lat");
				double lon = (double) area.get("lon");
				String siteName = (String) area.get("name");

				// Create the point.
				// TODO: in the general case recreation areas should be polygons while entrances
				// to the areas are points.
				shape = Shape.create(lon, lat, (Projection) scope.getScale().getSpace().getProjection());

                // Discard points that are not inside the polygon
                if (!space.contains(shape)) {
                    continue;
                }

				Builder obuilder = builder.startObject(scope.getTargetName(), siteName, makeScale(urn, shape, scope));

				// Add attributes to each recreation area like the name and id.
				for (Map.Entry<String, Object> entry : area.entrySet()) {
					if (entry.getKey() != "lat" || entry.getKey() != "lon" || entry.getKey() != "name") {
						obuilder.withMetadata(entry.getKey(), entry.getValue());
					}
				}
				obuilder.finishObject();

			}

		}

	}

    private List<String> getStatesFromEnvelope(IEnvelope envelope) {
        HttpResponse<JsonNode> response = Unirest.get("https://integratedmodelling.org/aux-geoserver/ows?service=WFS&version=2.0.0&request=GetFeature&typeNames=urban_heat_modelling:gadm_level_1_usa&bbox="
                + envelope.getMinX() +"," + envelope.getMinY() + "," + envelope.getMaxX() + "," + envelope.getMaxY()
                + ",EPSG:4326&outputFormat=application/json").asJson();
        if (!response.isSuccess()) {
            throw new KlabResourceNotFoundException("Cannot retrieve information from geoserver resource \"urban_heat_modelling:gadm_level_1_usa\".");
        }

        JSONObject statesData = response.getBody().getObject();
        try {
            return statesData.getJSONArray("features").toList().stream().map(
                    feature -> ((JSONObject) feature).getJSONObject("properties").getString("iso_1").replaceFirst("US-", "")).toList();
        } catch (JSONException e) {
            throw new KlabResourceAccessException("Error while parsing \"urban_heat_modelling:gadm_level_1_usa\"");
        }
    }

    private List<String> buildRecreationIDBInput(Map<String, String> parameters, IEnvelope envelope)
            throws UnsupportedEncodingException {
        // The list is initialized with the list of territories and DC, which cannot be queried by the same means as the states
        List<String> regions = List.of("DC", "AS", "GU", "MP", "PR", "VI");
        regions.addAll(getStatesFromEnvelope(envelope));
        List<String> query = new ArrayList<>();
		for (Map.Entry<String, String> entry : parameters.entrySet()) {
			String parameter = entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString());
			query.add(parameter);
		}
		String finalQuery = String.join("&", query);
		return regions.stream().map(state -> finalQuery + "&" + STATE + "=" + state).toList();
	}

	private IGeometry makeScale(Urn urn, IShape shape, IContextualizationScope scope) {
		List<IExtent> extents = new ArrayList<>();
		extents.add(scope.getContextSubject().getScale().getTime());
		extents.add(shape);
		return Scale.create(extents).asGeometry();
	}

	@Override
	public IResource getResource(String urn) {
		Urn kurn = new Urn(urn);
		ResourceReference ref = new ResourceReference();
		ref.setUrn(urn.toString());
		ref.setAdapterType(getName());
		ref.setLocalName(kurn.getResourceId());
		ref.setGeometry("#S2");
		ref.setVersion(Version.CURRENT);
		ref.setType(getType(kurn));
		return new Resource(ref);
	}

	@Override
	public IResource contextualize(IResource resource, IGeometry scale, IGeometry overallScale, IObservable semantics) {
		return resource;
	}

	@Override
	public Type getType(Urn urn) {
		// Instances of recreation areas are all bounded objects: polygons or points.
		return Type.OBJECT;
	}

	@Override
	public IGeometry getGeometry(Urn urn) {
		// Object geometry.
		return Geometry.create("#S2");
	}

	@Override
	public String getDescription() {
		return "Outdoor recreation areas in USA federal lands.";
	}

	@Override
	public Collection<String> getResourceUrns() {
		List<String> ret = new ArrayList<>();
		// TODO
		return ret;
	}

}
