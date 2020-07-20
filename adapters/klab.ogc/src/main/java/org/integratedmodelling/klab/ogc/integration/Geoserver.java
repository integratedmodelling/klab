package org.integratedmodelling.klab.ogc.integration;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Urn;

import kong.unirest.GetRequest;
import kong.unirest.HttpRequestWithBody;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import kong.unirest.json.JSONObject;

public class Geoserver {

	private static final String KLAB_NAMESPACE_URI_PREFIX = "http://data.integratedmodelling.org/ks/";
	String url;
	String username;
	String password;
	private int timeout = 500;

	private Geoserver() {
		this.url = Configuration.INSTANCE.getServiceProperty("geoserver", "url");
		this.username = Configuration.INSTANCE.getServiceProperty("geoserver", "user");
		this.password = Configuration.INSTANCE.getServiceProperty("geoserver", "password");
		this.timeout = Integer.parseInt(Configuration.INSTANCE.getServiceProperty("geoserver", "timeout", "500"));
	}

	public static Geoserver create() {
		return new Geoserver();
	}

	public static boolean isEnabled() {
		return Configuration.INSTANCE.getServiceProperty("geoserver", "url") != null;
	}

	/**
	 * Create the passed namespace unless it exists. Return the normalized name.
	 * 
	 * @param id
	 * @return
	 */
	public String requireNamespace(String id) {
		id = id.replaceAll("\\.", "_");
		if (getNamespaces().contains(id)) {
			return id;
		}
		createNamespace(id);
		return id;
	}

	public void setTimeout(int milliseconds) {
		this.timeout = milliseconds;
	}

	public boolean isOnline() {
		if (isEnabled()) {
			try {
				GetRequest request = Unirest.get(this.url + "/rest/namespaces").header("Accept", "application/json")
						.connectTimeout(timeout);
				if (this.username != null) {
					request = request.basicAuth(username, password);
				}
				HttpResponse<JsonNode> result = request.asJson();
				JSONObject response = result.getBody().getObject();
				if (response.has("namespaces")) {
					return true;
				}
			} catch (UnirestException e) {
				return false;
			}
		}
		return false;
	}

	/**
	 * Create a datastore for the database in the postgis object, using the passed
	 * namespace. Must not exist already.
	 * 
	 * @param postgis
	 * @param namespace
	 * @return
	 */
	public boolean createDatastore(Postgis postgis, String namespace) {

		Map<String, Object> message = new HashMap<>();
		Map<String, Object> payload = new HashMap<>();
		Map<String, String> data = new LinkedHashMap<>();
		data.put("host", postgis.getHost());
		data.put("port", postgis.getPort());
		data.put("database", postgis.getDatabase());
		data.put("user", postgis.getUsername());
		data.put("passwd", postgis.getPassword());
		data.put("dbtype", "postgis");
		payload.put("connectionParameters", data);
		payload.put("name", postgis.getDatabase());
		message.put("dataStore", payload);

		HttpRequestWithBody request = Unirest.post(this.url + "/rest/workspaces/" + namespace + "/datastores")
				.header("Content-Type", "application/json").connectTimeout(timeout);
		if (this.username != null) {
			request = request.basicAuth(username, password);
		}

		return request.body(message).asEmpty().isSuccess();
	}

	/**
	 * Create a datastore from the passed DB if it does not exist already in the
	 * given namespace.
	 * 
	 * @param postgis
	 * @param namespace
	 * @return
	 */
	public boolean requireDatastore(Postgis postgis, String namespace) {

		if (getDatastores(namespace).contains(postgis.getDatabase())) {
			return true;
		}

		return createDatastore(postgis, namespace);
	}

	public boolean createCoverageStore(String namespace, String name, File file) {

		if (getCoveragestores(namespace).contains(name)) {
			deleteCoverageStore(namespace, name);
		}

		Map<String, Object> payload = new HashMap<>();
		Map<String, Object> data = new HashMap<>();

		try {
			data.put("name", name);
			data.put("url", file.toURI().toURL().toString());
			payload.put("coverageStore", data);

			HttpRequestWithBody request = Unirest.post(this.url + "/rest/workspaces/" + namespace + "/coveragestores")
					.header("Content-Type", "application/json").connectTimeout(timeout);

			if (this.username != null) {
				request = request.basicAuth(username, password);
			}

			return request.body(payload).asEmpty().isSuccess();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
		}

		return false;
	}

	/**
	 * Delete a coverage store and all its metadata; leave any data in place.
	 * 
	 * @param namespace
	 * @param name
	 * @return
	 */
	private boolean deleteCoverageStore(String namespace, String name) {
		return Unirest
				.delete(this.url + "/rest/workspaces/" + namespace + "/coveragestores/" + name + "?purge=metadata")
				.connectTimeout(timeout).asEmpty().isSuccess();
	}

	public Set<String> getDatastores(String namespace) {

		Set<String> ret = new HashSet<>();
		GetRequest request = Unirest.get(this.url + "/rest/workspaces/" + namespace + "/datastores")
				.header("Accept", "application/json").connectTimeout(timeout);
		;
		if (this.username != null) {
			request = request.basicAuth(username, password);
		}
		HttpResponse<JsonNode> result = request.asJson();
		JSONObject response = result.getBody().getObject();
		if (response.has("dataStores") && response.get("dataStores") instanceof JSONObject) {
			response = response.getJSONObject("dataStores");
			for (Object datastore : response.getJSONArray("dataStore")) {
				if (datastore instanceof JSONObject) {
					ret.add(((JSONObject) datastore).getString("name"));
				}
			}
		}
		return ret;
	}

	public Set<String> getCoveragestores(String namespace) {

		Set<String> ret = new HashSet<>();
		GetRequest request = Unirest.get(this.url + "/rest/workspaces/" + namespace + "/coveragestores")
				.header("Accept", "application/json").connectTimeout(timeout);
		;
		if (this.username != null) {
			request = request.basicAuth(username, password);
		}
		HttpResponse<JsonNode> result = request.asJson();
		JSONObject response = result.getBody().getObject();
		if (response.has("dataStores") && response.get("coverageStores") instanceof JSONObject) {
			response = response.getJSONObject("coverageStores");
			for (Object datastore : response.getJSONArray("coverageStore")) {
				if (datastore instanceof JSONObject) {
					ret.add(((JSONObject) datastore).getString("name"));
				}
			}
		}
		return ret;
	}

	/**
	 * <dataStore> <name>nyc</name> <connectionParameters> <host>localhost</host>
	 * <port>5432</port> <database>nyc</database> <user>bob</user>
	 * <passwd>postgres</passwd> <dbtype>postgis</dbtype> </connectionParameters>
	 * </dataStore>
	 * 
	 * @param postgis   use the postgis that published the resource
	 * @param namespace geoserver namespace (will be created if needed)
	 * @param table     table name
	 * @return
	 */
	public String publishPostgisVector(Postgis postgis, String namespace, String table) {

		namespace = requireNamespace(namespace);
		String datastore = postgis.getDatabase();
		if (requireDatastore(postgis, namespace)) {

			if (getFeatureTypes(namespace, datastore).contains(table)) {
				deleteFeatureType(namespace, datastore, table);
			}

			Map<String, Object> payload = new HashMap<>();
			Map<String, Object> data = new HashMap<>();

			data.put("name", table);
			payload.put("featureType", data);

			/*
			 * TODO add all other attributes to featureType (data)! Example: { "name":
			 * "poi", "nativeName": "poi", "namespace": { "name": "tiger", "href":
			 * "http://localhost:8080/geoserver/rest/namespaces/tiger.json" }, "title":
			 * "Manhattan (NY) points of interest", "abstract":
			 * "Points of interest in New York, New York (on Manhattan). One of the attributes contains the name of a file with a picture of the point of interest."
			 * , "keywords": { "string": [ "poi", "Manhattan", "DS_poi",
			 * "points_of_interest", "sampleKeyword\\@language=ab\\;",
			 * "area of effect\\@language=bg\\;\\@vocabulary=technical\\;",
			 * "Привет\\@language=ru\\;\\@vocabulary=friendly\\;" ] }, "metadataLinks": {
			 * "metadataLink": [ { "type": "text/plain", "metadataType": "FGDC", "content":
			 * "www.google.com" } ] }, "dataLinks": {
			 * "org.geoserver.catalog.impl.DataLinkInfoImpl": [ { "type": "text/plain",
			 * "content": "http://www.google.com" } ] }, "nativeCRS":
			 * "GEOGCS[\"WGS 84\", \n  DATUM[\"World Geodetic System 1984\", \n    SPHEROID[\"WGS 84\", 6378137.0, 298.257223563, AUTHORITY[\"EPSG\",\"7030\"]], \n    AUTHORITY[\"EPSG\",\"6326\"]], \n  PRIMEM[\"Greenwich\", 0.0, AUTHORITY[\"EPSG\",\"8901\"]], \n  UNIT[\"degree\", 0.017453292519943295], \n  AXIS[\"Geodetic longitude\", EAST], \n  AXIS[\"Geodetic latitude\", NORTH], \n  AUTHORITY[\"EPSG\",\"4326\"]]"
			 * , "srs": "EPSG:4326", "nativeBoundingBox": { "minx": -74.0118315772888,
			 * "maxx": -74.00153046439813, "miny": 40.70754683896324, "maxy":
			 * 40.719885123828675, "crs": "EPSG:4326" }, "latLonBoundingBox": { "minx":
			 * -74.0118315772888, "maxx": -74.00857344353275, "miny": 40.70754683896324,
			 * "maxy": 40.711945649065406, "crs": "EPSG:4326" }, "projectionPolicy":
			 * "REPROJECT_TO_DECLARED", "enabled": true, "metadata": { "entry": [ { "@key":
			 * "kml.regionateStrategy", "$": "external-sorting" }, { "@key":
			 * "kml.regionateFeatureLimit", "$": "15" }, { "@key": "cacheAgeMax", "$":
			 * "3000" }, { "@key": "cachingEnabled", "$": "true" }, { "@key":
			 * "kml.regionateAttribute", "$": "NAME" }, { "@key": "indexingEnabled", "$":
			 * "false" }, { "@key": "dirName", "$": "DS_poi_poi" } ] }, "store": { "@class":
			 * "dataStore", "name": "tiger:nyc", "href":
			 * "http://localhost:8080/geoserver/rest/workspaces/tiger/datastores/nyc.json"
			 * }, "cqlFilter": "INCLUDE", "maxFeatures": 100, "numDecimals": 6,
			 * "responseSRS": { "string": [ 4326 ] }, "overridingServiceSRS": true,
			 * "skipNumberMatched": true, "circularArcPresent": true,
			 * "linearizationTolerance": 10, "attributes": { "attribute": [ { "name":
			 * "the_geom", "minOccurs": 0, "maxOccurs": 1, "nillable": true, "binding":
			 * "org.locationtech.jts.geom.Point" }, {}, {}, {} ] } }
			 * 
			 */

			HttpRequestWithBody request = Unirest
					.post(this.url + "/rest/workspaces/" + namespace + "/datastores/" + datastore + "/featuretypes")
					.connectTimeout(timeout).header("Content-Type", "application/json");

			if (this.username != null) {
				request = request.basicAuth(username, password);
			}

			if (request.body(payload).asEmpty().isSuccess()) {
				return namespace + ":" + table;
			}
		}

		return null;
	}

	private boolean deleteFeatureType(String namespace, String datastore, String featuretype) {
		return Unirest.delete(this.url + "/rest/workspaces/" + namespace + "/datastores/" + datastore + "/featuretypes/"
				+ featuretype).connectTimeout(timeout).asEmpty().isSuccess();
	}

	public Set<String> getFeatureTypes(String namespace, String datastore) {

		Set<String> ret = new HashSet<>();
		GetRequest request = Unirest
				.get(this.url + "/rest/workspaces/" + namespace + "/datastores/" + datastore + "/featuretypes.json");
		if (this.username != null) {
			request = request.basicAuth(username, password);
		}
		HttpResponse<JsonNode> result = request.connectTimeout(timeout).asJson();
		JSONObject response = result.getBody().getObject();
		if (response.has("featureTypes") && response.get("featureTypes") instanceof JSONObject) {
			response = response.getJSONObject("featureTypes");
			for (Object featuretype : response.getJSONArray("featureType")) {
				if (featuretype instanceof JSONObject) {
					ret.add(((JSONObject) featuretype).getString("name"));
				}
			}
		}
		return ret;
	}

	public boolean publishRaster(Urn urn, File file) {
		return false;
	}

	public boolean publishVector(Urn urn, File file) {
		return false;
	}

	public boolean publishPostgisRaster(Urn urn, String table) {
		return false;
	}

	/*
	 * ---- Unirest calls for the relevant parts of the Geoserver API
	 */

	public Set<String> getNamespaces() {
		Set<String> ret = new HashSet<>();
		GetRequest request = Unirest.get(this.url + "/rest/namespaces").header("Accept", "application/json")
				.connectTimeout(timeout);
		if (this.username != null) {
			request = request.basicAuth(username, password);
		}
		HttpResponse<JsonNode> result = request.asJson();
		JSONObject response = result.getBody().getObject();
		if (response.has("namespaces") && response.get("namespaces") instanceof JSONObject) {
			response = response.getJSONObject("namespaces");
			for (Object namespace : response.getJSONArray("namespace")) {
				if (namespace instanceof JSONObject) {
					ret.add(((JSONObject) namespace).getString("name"));
				}
			}
		}
		return ret;
	}

	public void createNamespace(String id) {
		Map<String, Object> payload = new HashMap<>();
		Map<String, String> data = new LinkedHashMap<>();
		data.put("prefix", id);
		data.put("uri", KLAB_NAMESPACE_URI_PREFIX + id);
		payload.put("namespace", data);
		HttpRequestWithBody request = Unirest.post(this.url + "/rest/namespaces")
				.header("Content-Type", "application/json").connectTimeout(timeout);
		if (this.username != null) {
			request = request.basicAuth(username, password);
		}
		request.body(payload).asEmpty();
	}

	public static void main(String[] args) {

		if (!isEnabled()) {
			System.out.println("NOT ENABLED");
			return;
		}

		Geoserver geoserver = create();

		if (!geoserver.isOnline()) {
			System.out.println("NOT ONLINE!");
			return;
		}

		for (String namespace : geoserver.getNamespaces()) {
			System.out.println("NS " + namespace);
			for (String datastore : geoserver.getDatastores(namespace)) {
				System.out.println("   DS " + datastore);
				for (String featuretype : geoserver.getFeatureTypes(namespace, datastore)) {
					System.out.println("     FT " + featuretype);
				}

			}
		}

	}

	public String getServiceUrl() {
		return url.endsWith("ows") ? url : (url + "/ows");
	}

}
