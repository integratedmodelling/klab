package org.integratedmodelling.klab.ogc.integration;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.ogc.integration.Postgis.PublishedResource;
import org.integratedmodelling.klab.ogc.integration.Postgis.PublishedResource.Attribute;

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
		Geoserver ret = new Geoserver();
		return ret;
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
	public boolean deleteCoverageStore(String namespace, String name) {
		HttpRequestWithBody request = Unirest.delete(
				this.url + "/rest/workspaces/" + namespace + "/coveragestores/" + name + "?recurse=true&purge=metadata")
				.connectTimeout(timeout);
		if (this.username != null) {
			request = request.basicAuth(username, password);
		}
		return request.asEmpty().isSuccess();
	}

	public boolean deleteNamespace(String namespace) {
		HttpRequestWithBody request = Unirest.delete(this.url + "/rest/namespaces/" + namespace)
				.connectTimeout(timeout);
		if (this.username != null) {
			request = request.basicAuth(username, password);
		}
		return request.asEmpty().isSuccess();
	}

	public boolean deleteWorkspace(String namespace) {
		HttpRequestWithBody request = Unirest.delete(this.url + "/rest/workspaces/" + namespace)
				.connectTimeout(timeout);
		if (this.username != null) {
			request = request.basicAuth(username, password);
		}
		return request.asEmpty().isSuccess();
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
	 * <pre>
	 <featureType>
	<name>annotations</name>
	<nativeName>annotations</nativeName>
	<title>Annotations</title>
	<srs>EPSG:4326</srs>
	<attributes>
	<attribute>
	  <name>the_geom</name>
	  <binding>org.locationtech.jts.geom.Point</binding>
	</attribute>
	<attribute>
	  <name>description</name>
	  <binding>java.lang.String</binding>
	</attribute>
	<attribute>
	  <name>timestamp</name>
	  <binding>java.util.Date</binding>
	</attribute>
	</attributes>
	</featureType>
	 * </pre>
	 * 
	 * @param postgis   use the postgis that published the resource
	 * @param namespace geoserver namespace (will be created if needed)
	 * @param table     table name
	 * @return
	 */
	public String publishPostgisVector(Postgis postgis, String namespace, PublishedResource resource) {

		namespace = requireNamespace(namespace);
		String datastore = postgis.getDatabase();
		if (requireDatastore(postgis, namespace)) {

			if (getFeatureTypes(namespace, datastore).contains(resource.name)) {
				deleteFeatureType(namespace, datastore, resource.name);
			}

			Map<String, Object> payload = new HashMap<>();
			Map<String, Object> data = new HashMap<>();
			List<Map<?,?>> attributes = new ArrayList<>();

			for (Attribute attribute : resource.attributes) {
				Map<String, Object> attr = new HashMap<>();
				attr.put("name", attribute.name);
				attr.put("binding", attribute.binding.getCanonicalName());
			}
			
			data.put("name", resource.name);
			data.put("nativeName", resource.name);
			data.put("srs", resource.srs);
			data.put("attributes", attributes);
			payload.put("featureType", data);
			
			HttpRequestWithBody request = Unirest
					.post(this.url + "/rest/workspaces/" + namespace + "/datastores/" + datastore + "/featuretypes")
					.connectTimeout(timeout).header("Content-Type", "application/json");

			if (this.username != null) {
				request = request.basicAuth(username, password);
			}

			if (request.body(payload).asEmpty().isSuccess()) {
				return namespace + ":" + resource.name;
			}
		}

		return null;
	}

	private boolean deleteFeatureType(String namespace, String datastore, String featuretype) {
		HttpRequestWithBody request = Unirest.delete(this.url + "/rest/workspaces/" + namespace + "/datastores/"
				+ datastore + "/featuretypes/" + featuretype).connectTimeout(timeout);
		if (this.username != null) {
			request = request.basicAuth(username, password);
		}
		return request.asEmpty().isSuccess();
	}

	public boolean deleteDatastore(String namespace, String datastore) {
		HttpRequestWithBody request = Unirest
				.delete(this.url + "/rest/workspaces/" + namespace + "/datastores/" + datastore + "?recurse=true")
				.connectTimeout(timeout);
		if (this.username != null) {
			request = request.basicAuth(username, password);
		}
		return request.asEmpty().isSuccess();
	}

	/**
	 * Delete EVERYTHING. Use with appropriate caution. 
	 */
	public void clear() {
		for (String namespace : getNamespaces()) {
			for (String datastore : getDatastores(namespace)) {
				deleteDatastore(namespace, datastore);
			}
			for (String coveragestore : getCoveragestores(namespace)) {
				deleteCoverageStore(namespace, coveragestore);
			}
			deleteNamespace(namespace);
		}
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
		System.out.println("DELETING EVERYTHING - CIÖCIA LÉ");
		geoserver.clear();
	}

	public String getServiceUrl() {
		return url.endsWith("ows") ? url : (url + "/ows");
	}

}
