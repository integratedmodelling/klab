package org.integratedmodelling.klab.ogc.integration;

import java.io.File;
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
	 * <dataStore> <name>nyc</name> <connectionParameters> <host>localhost</host>
	 * <port>5432</port> <database>nyc</database> <user>bob</user>
	 * <passwd>postgres</passwd> <dbtype>postgis</dbtype> </connectionParameters>
	 * </dataStore>
	 * 
	 * @param postgis use the postgis that published the resource
	 * @param namespace geoserver namespace (will be created if needed)
	 * @param table   table name
	 * @return
	 */
	public boolean publishPostgisVector(Postgis postgis, String namespace, String table) {

		namespace = requireNamespace(namespace);
		
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
		payload.put("name", table);
		message.put("dataStore", payload);

		HttpRequestWithBody request = Unirest.post(this.url + "/rest/workspaces/" + namespace + "/datastores")
				.header("Content-Type", "application/json");
		if (this.username != null) {
			request = request.basicAuth(username, password);
		}

		return request.body(message).asEmpty().isSuccess();
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
		geoserver.requireNamespace("ziorana");
	}

}
