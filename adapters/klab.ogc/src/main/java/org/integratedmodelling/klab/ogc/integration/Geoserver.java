package org.integratedmodelling.klab.ogc.integration;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import javax.annotation.Nullable;
import javax.media.jai.iterator.RandomIter;
import javax.media.jai.iterator.RandomIterFactory;

import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.gce.geotiff.GeoTiffReader;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.ogc.WcsAdapter;
import org.integratedmodelling.klab.ogc.integration.Postgis.PublishedResource;
import org.integratedmodelling.klab.ogc.integration.Postgis.PublishedResource.Attribute;
import org.integratedmodelling.klab.utils.FileUtils;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.opengis.coverage.grid.GridCoverage;

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
            GetRequest request = Unirest.get(this.url + "/rest/namespaces").header("Accept", "application/json")
                    .connectTimeout(timeout);
            if (this.username != null) {
                request = request.basicAuth(username, password);
            }
            try {
                HttpResponse<JsonNode> result = request.asJson();
                if (!result.isSuccess() && result.getStatus() == 401) {
                    Logging.INSTANCE.warn("Unable to connect to geoserver: " + result.getStatusText());
                    return false;
                } else if (result.isSuccess() && result.getBody().getObject().has("namespaces")) {
                    return true;
                } else {
                    Logging.INSTANCE.warn("Unable to detect geoserver namespaces: " + request.getUrl());
                }
            } catch (UnirestException e) {
                Logging.INSTANCE.warn("Unable to connect to geoserver: " + e.getMessage());
                return false;
            }
        }
        return false;
    }

    /**
     * Create a datastore for the database in the postgis object, using the passed namespace. Must
     * not exist already.
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
     * Create a datastore from the passed DB if it does not exist already in the given namespace.
     * 
     * @param postgis
     * @param namespace
     * @return
     */
    public boolean requireDatastore(Postgis postgis, String namespace) {

        requireNamespace(namespace);
        if (getDatastores(namespace).contains(postgis.getDatabase())) {
            return true;
        }

        return createDatastore(postgis, namespace);
    }

    private Map<String, Object> getWorkspaceRecord(String namespace) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("name", namespace);
        ret.put("link", KLAB_NAMESPACE_URI_PREFIX + namespace);
        return ret;
    }

    public boolean createCoverageStore(String namespace, String name, File file) {

        requireNamespace(namespace);

        if (getCoveragestores(namespace).contains(name)) {
            deleteCoverageStore(namespace, name);
        }

        Map<String, Object> payload = new HashMap<>();
        Map<String, Object> data = new HashMap<>();

        try {
            data.put("name", name);
            data.put("url", file.toURI().toURL().toString());
            data.put("enabled", true);
            data.put("workspace", getWorkspaceRecord(namespace));
            data.put("type", getFileExtension(file));

            payload.put("coverageStore", data);

            HttpRequestWithBody request = Unirest.post(this.url + "/rest/workspaces/" + namespace + "/coveragestores")
                    .header("Content-Type", "application/json").connectTimeout(timeout);

            if (this.username != null) {
                request = request.basicAuth(username, password);
            }

            HttpResponse<String> resp = request.body(payload).asString();
            if (!resp.isSuccess()) {
                Logging.INSTANCE.error("Error creating coverage store: HTTP [" + resp.getStatus() + "] - " + resp.getBody());
            }
            return resp.isSuccess();

        } catch (MalformedURLException e) {
            Logging.INSTANCE.error("Error creating coverage store - ", e.getMessage());
        }

        return false;
    }

    public boolean createCoverageLayer(String namespace, String name, File file, @Nullable String nativeName) {

        if (createCoverageStore(namespace, name, file)) {

            Map<String, Object> payload = new HashMap<>();
            Map<String, Object> data = new HashMap<>();

            data.put("name", name);
            data.put("title", name);
            data.put("nativeCoverageName", nativeName == null ? name : nativeName);
            data.put("recalculate", "nativebbox,latlonbbox");

            payload.put("coverage", data);

            HttpRequestWithBody request = Unirest
                    .post(this.url + "/rest/workspaces/" + namespace + "/coveragestores/" + name + "/coverages")
                    .header("Content-Type", "application/json").connectTimeout(timeout);

            if (this.username != null) {
                request = request.basicAuth(username, password);
            }

            HttpResponse<String> resp = request.body(payload).asString();
            if (!resp.isSuccess()) {
                Logging.INSTANCE.error("Error creating coverage layer: HTTP [" + resp.getStatus() + "] - " + resp.getBody());
            }
            return resp.isSuccess();
        }
        return false;
    }

    private String getFileExtension(File file) {
        if (file.toString().endsWith(".tiff") || file.toString().endsWith(".tif")) {
            return "GeoTIFF";
        } else if (file.toString().endsWith(".nc")) {
            return "NetCDF";
        }
        throw new KlabValidationException("Cannot establish Geoserver file type for " + file);
    }

    /**
     * Delete a coverage store and all its metadata; leave any data in place.
     * 
     * @param namespace
     * @param name
     * @return
     */
    public boolean deleteCoverageStore(String namespace, String name) {
        HttpRequestWithBody request = Unirest
                .delete(this.url + "/rest/workspaces/" + namespace + "/coveragestores/" + name + "?recurse=true&purge=metadata")
                .connectTimeout(timeout);
        if (this.username != null) {
            request = request.basicAuth(username, password);
        }
        return request.asEmpty().isSuccess();
    }

    public boolean deleteNamespace(String namespace) {
        HttpRequestWithBody request = Unirest.delete(this.url + "/rest/namespaces/" + namespace).connectTimeout(timeout);
        if (this.username != null) {
            request = request.basicAuth(username, password);
        }
        return request.asEmpty().isSuccess();
    }

    public boolean deleteWorkspace(String namespace) {
        HttpRequestWithBody request = Unirest.delete(this.url + "/rest/workspaces/" + namespace).connectTimeout(timeout);
        if (this.username != null) {
            request = request.basicAuth(username, password);
        }
        return request.asEmpty().isSuccess();
    }

    public Set<String> getDatastores(String namespace) {

        Set<String> ret = new HashSet<>();
        GetRequest request = Unirest.get(this.url + "/rest/workspaces/" + namespace + "/datastores")
                .header("Accept", "application/json").connectTimeout(timeout);;
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
     * @param postgis use the postgis that published the resource
     * @param namespace geoserver namespace (will be created if needed)
     * @param table table name
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
            List<Map<?, ?>> attributes = new ArrayList<>();

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
        HttpRequestWithBody request = Unirest
                .delete(this.url + "/rest/workspaces/" + namespace + "/datastores/" + datastore + "/featuretypes/" + featuretype)
                .connectTimeout(timeout);
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
        HttpRequestWithBody request = Unirest.post(this.url + "/rest/namespaces").header("Content-Type", "application/json")
                .connectTimeout(timeout);
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
        System.out.println("DELETING EVERYTHING - CIÖCIA");
        geoserver.clear();
    }

    public String getServiceUrl() {
        return url.endsWith("ows") ? url : (url + "/ows");
    }

    /**
     * Read a coverage for the passed space into an image that is returned.
     * 
     * @param space
     * @param namespace
     * @param layerId
     * @return
     */
    public GridCoverage2D getWCSCoverage(ISpace space, String namespace, String layerId) {
        try {

            File coverageFile = WcsAdapter.getCachedFile(layerId, space.encode());
            if (coverageFile == null) {

                URL getCov = new URL(getWCSGetCoverageUrl(space, namespace, layerId));
                try (InputStream input = getCov.openStream()) {
                    coverageFile = File.createTempFile("geo", ".tiff");
                    FileUtils.copyInputStreamToFile(input, coverageFile);
                    FileUtils.forceDeleteOnExit(coverageFile);
                    if (Configuration.INSTANCE.isEchoEnabled()) {
                        System.out.println("Data have arrived in " + coverageFile);
                    }
                    WcsAdapter.setCachedFile(coverageFile, layerId, space.encode());
                } catch (Throwable e) {
                    throw new KlabIOException(e);
                }
            }

            GeoTiffReader reader = new GeoTiffReader(coverageFile);
            return reader.read(MiscUtilities.getFileBaseName(coverageFile), null);

        } catch (Throwable e) {
            Logging.INSTANCE.error(e);
        }
        return null;
    }

    public String getWCSGetCoverageUrl(ISpace space, String namespace, String layerId) {

        if (space.shape().length != 2 || !space.isRegular()) {
            throw new IllegalArgumentException("cannot retrieve  a grid dataset from WCS in a non-grid context");
        }

        Projection crs = (Projection) space.getProjection();
        IEnvelope env = space.getEnvelope();

        int xc = (int) space.shape()[0];
        int yc = (int) space.shape()[1];

        double west = env.getMinX();
        double east = env.getMaxX();
        double south = env.getMinY();
        double north = env.getMaxY();

        /*
         * jiggle by the projection's equivalent of a few meters if we're asking for a single point,
         * so WCS does not go crazy.
         */
        if (NumberUtils.equal(west, east)) {
            double delta = (crs.getCoordinateReferenceSystem().getCoordinateSystem().getAxis(0).getMaximumValue()
                    - crs.getCoordinateReferenceSystem().getCoordinateSystem().getAxis(0).getMinimumValue()) / 3900000.0;
            west -= delta;
            east += delta;
        }

        if (NumberUtils.equal(north, south)) {
            double delta = (crs.getCoordinateReferenceSystem().getCoordinateSystem().getAxis(1).getMaximumValue()
                    - crs.getCoordinateReferenceSystem().getCoordinateSystem().getAxis(1).getMinimumValue()) / 3900000.0;
            south -= delta;
            north += delta;
        }

        final String version = "1.0.0";
        return this.getServiceUrl() + "?service=WCS&version=" + version + "&request=GetCoverage&coverage=" + namespace + ":"
                + layerId + "&bbox=" + west + "," + south + "," + east + "," + north + "&crs=" + crs.getSimpleSRS()
                + "&responseCRS=" + crs.getSimpleSRS() + "&width=" + xc + "&height=" + yc + "&format=" + "GeoTIFF";

    }

    /**
     * Encode data into a pre-configured state builder.
     * 
     * @param coverage
     * @param grid
     * @param builder
     * @param band
     * @param noDataValue
     */
    public void encode(GridCoverage coverage, IScale scale, IKlabData.Builder builder, int band, double noDataValue,
            Function<Number, Number> converter) {

        /*
         * Set the data from the transformed coverage
         */
        RenderedImage image = coverage.getRenderedImage();
        RandomIter iterator = RandomIterFactory.create(image, null);
        Set<Double> nodata = new HashSet<>();
        nodata.add(noDataValue);
        Grid grid = null;
        if (scale.getSpace() instanceof Space && ((Space) scale.getSpace()).getGrid() != null) {
            grid = (Grid) ((Space) scale.getSpace()).getGrid();
        }

        if (grid == null) {
            // TODO add error notification
            return;
        }

        // for (ILocator locator : scale) {
        //
        // Cell cell = locator.as(Cell.class);
        //
        // double value = iterator.getSampleDouble((int) cell.getX(), (int) cell.getY(), band);
        //
        // // this is cheeky but will catch most of the nodata and
        // // none of the good data
        // // FIXME see if this is really necessary
        // if (value < -1.0E35 || value > 1.0E35) {
        // value = Double.NaN;
        // }
        //
        // for (double nd : nodata) {
        // if (NumberUtils.equal(value, nd)) {
        // value = Double.NaN;
        // break;
        // }
        // }
        //
        // if (converter != null && Observations.INSTANCE.isData(value)) {
        // value = converter.apply(value).doubleValue();
        // }
        //
        // builder.add(value);
        //
        // }
        //
        for (long ofs = 0; ofs < grid.getCellCount(); ofs++) {

            long[] xy = Grid.getXYCoordinates(ofs, grid.getXCells(), grid.getYCells());
            double value = iterator.getSampleDouble((int) xy[0], (int) xy[1], band);

            // this is cheeky but will catch most of the nodata and
            // none of the good data
            // FIXME see if this is really necessary
            if (value < -1.0E35 || value > 1.0E35) {
                value = Double.NaN;
            }

            for (double nd : nodata) {
                if (NumberUtils.equal(value, nd)) {
                    value = Double.NaN;
                    break;
                }
            }

            if (converter != null && Observations.INSTANCE.isData(value)) {
                value = converter.apply(value).doubleValue();
            }

            builder.add(value);
        }
    }

}
