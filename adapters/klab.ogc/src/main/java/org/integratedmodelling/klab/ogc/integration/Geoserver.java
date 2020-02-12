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

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import kong.unirest.GetRequest;
import kong.unirest.HttpRequestWithBody;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

public class Geoserver {

	private static final String KLAB_NAMESPACE_URI_PREFIX = "http://data.integratedmodelling.org/ks/";
	String url;
	String username;
	String password;

	private Geoserver() {
		this.url = Configuration.INSTANCE.getServiceProperty("geoserver", "url");
		this.username = Configuration.INSTANCE.getServiceProperty("geoserver", "user");
		this.password = Configuration.INSTANCE.getServiceProperty("geoserver", "password");
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
				.header("Content-Type", "application/json");
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
					.header("Content-Type", "application/json");

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
				.asEmpty().isSuccess();
	}

	public Set<String> getDatastores(String namespace) {

		Set<String> ret = new HashSet<>();
		GetRequest request = Unirest.get(this.url + "/rest/workspaces/" + namespace + "/datastores");
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
		GetRequest request = Unirest.get(this.url + "/rest/workspaces/" + namespace + "/coveragestores");
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
	public boolean publishPostgisVector(Postgis postgis, String namespace, String table) {

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

			HttpRequestWithBody request = Unirest
					.post(this.url + "/rest/workspaces/" + namespace + "/datastores/" + datastore + "/featuretypes")
					.header("Content-Type", "application/json");

			if (this.username != null) {
				request = request.basicAuth(username, password);
			}

			return request.body(payload).asEmpty().isSuccess();
		}

		return false;
	}

	private boolean deleteFeatureType(String namespace, String datastore, String featuretype) {
		return Unirest.delete(this.url + "/rest/workspaces/" + namespace + "/datastores/" + datastore + "/featuretypes/"
				+ featuretype).asEmpty().isSuccess();
	}

	public Set<String> getFeatureTypes(String namespace, String datastore) {

		Set<String> ret = new HashSet<>();
		GetRequest request = Unirest
				.get(this.url + "/rest/workspaces/" + namespace + "/datastores/" + datastore + "/featuretypes.json");
		if (this.username != null) {
			request = request.basicAuth(username, password);
		}
		HttpResponse<JsonNode> result = request.asJson();
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
		GetRequest request = Unirest.get(this.url + "/rest/namespaces");
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
		HttpRequestWithBody request = Unirest.post(this.url + "/rest/namespaces").header("Content-Type",
				"application/json");
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

}
