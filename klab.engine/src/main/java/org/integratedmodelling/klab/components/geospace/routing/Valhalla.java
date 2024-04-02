package org.integratedmodelling.klab.components.geospace.routing;

import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.exceptions.KlabException;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * Java peer to interact with the valhalla.test.Valhalla server via simple
 * function calls using JSON strings as inputs.
 */

public class Valhalla {

	private boolean isOnline = false;
	ValhallaRuntimeEnvironment valhalla;
	ValhallaOutputDeserializer deserializer;
	public String service = "";

	public Valhalla() {
		this("https://routing.integratedmodelling.org");
	}

	public Valhalla(boolean local) {
		String serviceUrl;
		if (local)
			serviceUrl = "http://localhost:8002";
		else
			serviceUrl = "http://192.168.250.240:8002";
		new Valhalla(serviceUrl);
	}

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

	public String isochrone(String input) throws ValhallaException {
		return valhalla.valhallaSendRequest(input, ValhallaRuntimeEnvironment.ValhallaRequestType.ISOCHRONE);
	}

	public static void main(String[] args) throws ValhallaException {

		// valhalla.test.Valhalla Java peer. Connected to "http://localhost:8002" by
		// default.
		Valhalla valhalla = new Valhalla();

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

	public static String buildValhallaJsonInput(IDirectObservation source, IDirectObservation target,
			String transportType, String geometryCollapser) {

		double[] sourceCoordinates = null;
		double[] targetCoordinates = null;

		// Using a switch statement for generality when more methods will be supported.
		switch (geometryCollapser) {
		case "centroid":
			sourceCoordinates = source.getSpace().getStandardizedCentroid();
			targetCoordinates = target.getSpace().getStandardizedCentroid();
			break;
		default:
			throw new KlabException(
					"Invalid method for geometry collapse: " + geometryCollapser + ". Supported: \"centroid\".");
		}

		double sourceLat = sourceCoordinates[1];
		double sourceLon = sourceCoordinates[0];
		double targetLat = targetCoordinates[1];
		double targetLon = targetCoordinates[0];

		String input = "{\"locations\": [{\"lat\":" + sourceLat + ",\"lon\":" + sourceLon + "}, {\"lat\":" + targetLat
				+ ",\"lon\":" + targetLon + "}], \"costing\":" + "\"" + transportType + "\"}";

		return input;
	}

}
