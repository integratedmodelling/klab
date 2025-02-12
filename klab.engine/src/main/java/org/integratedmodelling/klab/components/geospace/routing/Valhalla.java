package org.integratedmodelling.klab.components.geospace.routing;

import java.util.List;
import java.util.Map;

import org.geotools.data.geojson.GeoJSONReader;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.components.geospace.routing.ValhallaConfiguration.GeometryCollapser;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabRemoteException;
import org.locationtech.jts.geom.Geometry;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

/**
 * Java peer to interact with the valhalla.test.Valhalla server via simple
 * function calls using JSON strings as inputs.
 */

public class Valhalla {

	private boolean isOnline = false;
	ValhallaRuntimeEnvironment valhalla;
	ValhallaOutputDeserializer deserializer;
	public String service = "";

	public Valhalla(String serviceUrl) {
		this.service = serviceUrl;
		valhalla = new ValhallaRuntimeEnvironment(this.service);
		isOnline = valhalla.isOnline();
		deserializer = new ValhallaOutputDeserializer();
	}

	public boolean isOnline() {
		return isOnline;
	}

	public ValhallaOutputDeserializer.Matrix matrix(String input) throws ValhallaException {
		String response = valhalla.valhallaSendRequest(input, ValhallaRuntimeEnvironment.ValhallaRequestType.MATRIX);
		deserializer.setJson(response);
		return deserializer.deserializeMatrixOutput();
	}

	public String route(String input) throws ValhallaException {
		return valhalla.valhallaSendRequest(input, ValhallaRuntimeEnvironment.ValhallaRequestType.ROUTE);
	}

	public ValhallaOutputDeserializer.OptimizedRoute optimized_route(String input) throws ValhallaException {
		String response = valhalla.valhallaSendRequest(input, ValhallaRuntimeEnvironment.ValhallaRequestType.OPTIMIZE);
		deserializer.setJson(response);
		return deserializer.deserializeOptimizedRoutes();
	}

	public Geometry isochrone(String input) throws ValhallaException {
		String response = valhalla.valhallaSendRequest(input, ValhallaRuntimeEnvironment.ValhallaRequestType.ISOCHRONE);
		JSONObject json = new JsonNode(response).getObject();
		return GeoJSONReader.parseGeometry(json.getJSONArray("features").getJSONObject(0).getJSONObject("geometry").toString());
	}

	public static String buildValhallaJsonInput(double[] source, double[] target,
			String transportType) {
		double sourceLat = source[1];
		double sourceLon = source[0];
		double targetLat = target[1];
		double targetLon = target[0];

		String input = "{\"locations\": [{\"lat\":" + sourceLat + ",\"lon\":" + sourceLon + "}, {\"lat\":" + targetLat
				+ ",\"lon\":" + targetLon + "}], \"costing\":" + "\"" + transportType + "\"}";

		return input;
	}
	
    public static String buildValhallaIsochroneInput(double[] coordinates, String transportType, String isochroneType, double range, boolean isReverse) {
        return new StringBuffer("{\"locations\":[").append("{\"lat\":").append(coordinates[1]).append(",").append("\"lon\":")
                .append(coordinates[0]).append("}],\"costing\":\"").append(transportType).append("\",")
                .append("\"contours\":[{\"").append(isochroneType).append("\":").append(range)
                .append("}],\"polygons\":true,\"reverse\":").append(isReverse).append("}").toString();
    }

    private static JSONArray coordinatesAsJson(List<double[]> coordinates) {
        JSONArray ret = new JSONArray();
        coordinates.forEach(c -> {
            JSONObject coor = new JSONObject().put("lat", c[1]).put("lon", c[0]);
            ret.put(coor);
        });
        return ret;
    }

    public static String buildValhallaMatrixInput(List<double[]> sources, List<double[]> targets, String transportType) {
        JSONArray sourcesAsJson = coordinatesAsJson(sources);
        JSONArray targetsAsJson = coordinatesAsJson(targets);

        return new JSONObject().put("sources", sourcesAsJson).put("targets", targetsAsJson).put("costing", transportType).toString();
    }
    /*
     * Sets the coordinates according to the selected geometry collapser.
     */
    public static double[] getCoordinates(IDirectObservation observation, GeometryCollapser geometryCollapser) {
        switch(geometryCollapser.getType()) {
        case "centroid":
            return observation.getSpace().getStandardizedCentroid();
        default:
            //TODO IM-433 In the future, we should allow for more complex ways of finding a geometry
            throw new KlabException(
                    "Invalid method for geometry collapse: " + geometryCollapser + ". Supported: \"centroid\".");
        }
    }

    public static boolean isServerOnline(String server) {
        HttpResponse<JsonNode> response;
        try {
            response = Unirest.get(server + "/status").asJson();
        } catch (Exception e) {
            throw new KlabRemoteException("Cannot access Valhalla server. Reason: " + e.getMessage());
        }
        if (response.getStatus() != 200) {
            return false;
        }
        return true;
    }
}
