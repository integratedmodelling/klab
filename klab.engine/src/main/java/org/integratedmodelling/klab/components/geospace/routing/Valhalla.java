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

	public static void main(String[] args) throws ValhallaException {
		Valhalla valhalla = new Valhalla("https://routing.integratedmodelling.org");

		/*
		 * Matrix API example.
		 */

		// Coordinates of sources and targets for the travel-time matrix. Note the
		// costing parameter which essentially
		// is the means of transport. For testing make sure that coordinates are within
		// the loaded OSM environment.
//		String input = "{\"sources\":[{\"lat\":42.544014,\"lon\":1.5163911},{\"lat\":42.524014,\"lon\":1.5263911}],\"targets\":[{\"lat\":42.539735,\"lon\":1.4988},{\"lat\":42.541735,\"lon\":1.4888}],\"costing\":\"pedestrian\"}";

		String input = "{\"sources\":[{\"lat\":40.544014,\"lon\":-103},{\"lat\":40.524014,\"lon\":-103}],\"targets\":[{\"lat\":40.539735,\"lon\":-103},{\"lat\":40.541735,\"lon\":-103}],\"costing\":\"auto\"}";

		// Call to matrix method with input, the function returns the deserialized JSON
		// string in a specific format.
		ValhallaOutputDeserializer.Matrix matrix = valhalla.matrix(input);

		// The adjacency list stores information on the distance/time between each
		// source and target in a way that is
		// very friendly for graph creation with JUNG, and probably also with JGraphT.
		List<Map<String, Number>> list = matrix.getAdjacencyList();
		System.out.println(list);

		// Instantiate and populate the graph.
		Graph<String, Double> g = new DirectedSparseGraph<>();
		for (Map<String, Number> m : list) {
			Integer source = (Integer) m.get("source");
			Integer target = (Integer) m.get("target");
			double time = (double) m.get("time");

			// VertexIds are transformed to strings and prefixed with s or t to easily
			// differentiate between sources and
			// targets as the index starts at 0 for both. If needed to use integers ewe can
			// always do
			// target_index += max(source_index)
			String sv = "s" + source.toString();
			String tv = "t" + target.toString();

			// In this case a time accessibility graph is created.
			boolean added = g.addEdge(time, sv, tv, EdgeType.DIRECTED);

			if (!added)
				throw new ValhallaException("Could not add edge to graph");
		}
		System.out.println(g);

		/*
		 * Optimized Route API example.
		 */

		// This is a back and forth trip in Andorra.
//		input = "{\"locations\":[{\"lat\":42.544014,\"lon\":1.5163911},{\"lat\":42.539735,\"lon\":1.4988},{\"lat\":42.544014,\"lon\":1.5163911}],\"costing\":\"auto\"}";

		input = "{\"locations\":[{\"lat\":40.544014,\"lon\":-103},{\"lat\":40.524014,\"lon\":-103}],\"costing\":\"auto\"}";

		// Call to optimized route method with input, the function returns the
		// deserialized JSON string in a specific format.
		ValhallaOutputDeserializer.OptimizedRoute route = valhalla.optimized_route(input);
		IShape path = route.getPath();
		Map<String, Object> stats = route.getSummaryStatistics();
		List<Map<String, Number>> waypoints = route.getWaypoints();

		System.out.println(path);
		System.out.println(stats);
		System.out.println(waypoints);
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
