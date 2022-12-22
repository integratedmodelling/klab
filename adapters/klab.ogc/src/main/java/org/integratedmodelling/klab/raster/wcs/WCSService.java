package org.integratedmodelling.klab.raster.wcs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.jxpath.JXPathContext;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IProjection;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.geospace.extents.Envelope;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.time.extents.TemporalExtension;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.exceptions.KlabUnsupportedFeatureException;
import org.integratedmodelling.klab.rest.EngineEvent;
import org.integratedmodelling.klab.rest.SpatialExtent;
import org.integratedmodelling.klab.utils.FileUtils;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.integratedmodelling.klab.utils.MapUtils;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.integratedmodelling.klab.utils.Range;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

import com.github.underscore.lodash.U;

/**
 * Wraps a WCS service in a decent API.
 * 
 * @author ferdinando.villa
 *
 */
public class WCSService {

    private static int CONNECT_TIMEOUT_MS = 5000;
    private static int READ_TIMEOUT_MS = 5000;

    public static final String WGS84_BOUNDING_BOX = "ows:WGS84BoundingBox";
    public static final String IDENTIFIER = "ows:Identifier";
    public static final String CRS = "crs";
    public static final String INFINITY = "Infinity";
    public static final String NULL_VALUE = "NullValue";
    public static final String LOWER_CORNER = "ows:LowerCorner";
    public static final String UPPER_CORNER = "ows:UpperCorner";
    public static final String SUPPORTED_CRS = "ows:SupportedCRS";
    public static final String COVERAGE_ID = "wcs:CoverageId";
    public static final String RANGE = "ows:Range";
    public static final String RANGE_TYPE = "gmlcov:rangeType";

    /**
     * Default time of expiration of layer information is 2h
     */
    public long LAYER_INFO_EXPIRATION_MILLISECONDS = 120 * 60 * 1000;
    /**
     * If true (default), double underscores (__) in layer identifiers are translated into namespace
     * separator (:) before retrieval of the layer identifier. This accommodates Geoserver which
     * uses namespaced IDs.
     */
    public boolean TRANSLATE_DOUBLEUNDERSCORE_TO_NAMESPACE_SEPARATOR = true;

    private List<Throwable> errors = new ArrayList<>();
    Map<String, WCSLayer> layers = Collections.synchronizedMap(new HashMap<>());
    // all identifiers, also when the layers are not there
    Set<String> identifiers = Collections.synchronizedSet(new HashSet<>());
    private String serviceUrl;
    private Version version;
    // Parser parser;

    private long cacheExpiration = 0;
    static DB db;
    static Map<String, String> wcsCache;

    public boolean hasErrors() {
        return errors.size() > 0;
    }

    public Collection<WCSLayer> getLayers() {
        return layers.values();
    }

    public WCSLayer getLayer(String id) {
        return layers.get(id);
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public Version getServiceVersion() {
        return version;
    }

    public class WCSLayer {

        class Band {
            public Band(Map<?, ?> data) {
                if (data.containsKey("field")) {
                    data = (Map<?, ?>) data.get("field");
                }
                this.name = data.get("-name").toString();
                Map<?, ?> quantity = (Map<?, ?>) data.get("swe:Quantity");
                if (quantity.containsKey("swe:constraint")) {

                    String interval = MapUtils.get(quantity, "swe:constraint/swe:AllowedValues/swe:interval", String.class);

                    double[] nils = NumberUtils.doubleArrayFromString(interval, "\\s+");
                    if (NumberUtils.equal(nils[0], nils[1])) {
                        // it's the nodata value - but it's supposed to be the allowed interval.
                        this.nodata.add(nils[0]);
                    } else {
                        this.boundaries = new Range(nils[0], nils[1], false, false);
                    }

                    if (quantity.containsKey("swe:nilValues")) {

                        String nilval = MapUtils.get(quantity, "swe:nilValues/swe:NilValues/swe:nilValue/#text", String.class);
                        if (nilval == null) {
                            Logging.INSTANCE.warn("WCS: null key in nilValues is not null anymore: revise Geotools API versions");
                        } else {
                            this.nodata.add(Double.parseDouble(nilval));
                        }
                    }
                }
            }

            String name;
            Set<Double> nodata = new HashSet<>();
            Range boundaries;
        }

        // identifier from capabilities (simple, no namespace)
        private String name;
        // identifier from describeCoverage, to use for retrieval (includes namespace in
        // Geoserver)
        private String identifier;
        // envelope in WGS84 from capabilities
        private IEnvelope wgs84envelope;
        // if this is empty, we don't know from the server and we should just try,
        // signaling an error
        private Set<IProjection> supportedProjections = new HashSet<>();

        // if for any reason we can't parse these, they will be set to the WSG84 from
        // the capabilities
        private IEnvelope originalEnvelope;
        private IProjection originalProjection;
        // this takes over band info when bands are unspecified
        private Set<Double> nodata = new HashSet<>();
        // this may not be filled in despite the existence of at least one band
        private List<Band> bands = new ArrayList<>();

        // set to true when a getCoverage response has been parsed
        private boolean finished = false;
        private String message = "";
        private boolean error = false;
        private int[] gridShape;
        private long timestamp = System.currentTimeMillis();
        private boolean skipRefresh;
        private TemporalExtension temporalExtension;

        public WCSLayer(boolean skipRefresh) {
            this.skipRefresh = skipRefresh;
        }

        public String getName() {
            return name;
        }

        public String getIdentifier() {
            describeCoverage();
            return identifier;
        }

        /**
         * Return the same as {@link #getIdentifier()} but if there are service-specific
         * transformations to adapt it for a request, perform them first.
         * 
         * @return the request-ready identifier
         */
        public String getRequestIdentifier() {
            describeCoverage();
            return TRANSLATE_DOUBLEUNDERSCORE_TO_NAMESPACE_SEPARATOR ? identifier.replaceAll("__", ":") : identifier;
        }

        public IEnvelope getWgs84envelope() {
            return wgs84envelope;
        }

        public Set<IProjection> getSupportedProjections() {
            describeCoverage();
            return supportedProjections;
        }

        public IEnvelope getOriginalEnvelope() {
            describeCoverage();
            return originalEnvelope;
        }

        public IProjection getOriginalProjection() {
            describeCoverage();
            return originalProjection;
        }

        public Set<Double> getNodata(int band) {
            describeCoverage();
            return bands.size() > band ? bands.get(band).nodata : nodata;
        }

        public boolean isError() {
            describeCoverage();
            return error || identifier == null;
        }

        public WCSService getService() {
            return WCSService.this;
        }

        private void describeCoverage() {

            if (!finished || (System.currentTimeMillis() - timestamp) > LAYER_INFO_EXPIRATION_MILLISECONDS) {
                finished = true;
                timestamp = System.currentTimeMillis();

                try {
                    // Version version = Version.create("2.0.0");
                    URL url = new URL(serviceUrl + "?service=WCS&version=" + version + "&request=DescribeCoverage&"
                            + (version.getMajor() >= 2 ? "coverageId=" : "identifiers=") + name);

                    Map<?, ?> coverage = null;
                    if (cacheExpiration > 0) {
                        String cached = wcsCache.get(url.toString());
                        if (cached != null) {
                            Map<?, ?> map = JsonUtils.parseObject(cached, Map.class);
                            Long ts = (Long) map.get("timestamp");
                            if (this.skipRefresh || (System.currentTimeMillis() - ts) < cacheExpiration) {
                                coverage = map;
                            }
                        }
                    }

                    if (coverage == null) {
                        try (InputStream input = url.openStream()) {
                            String content = IOUtils.toString(input, StandardCharsets.UTF_8);
                            coverage = (Map<?, ?>) U.fromXmlMap(content);
                            Map<Object, Object> tocache = new HashMap<>(coverage);
                            tocache.put("timestamp", System.currentTimeMillis());
                            wcsCache.put(url.toString(), JsonUtils.asString(tocache));
                            db.commit();
                        } catch (IOException e) {
                            error = true;
                        }
                    }

                    if (coverage != null) {
                        if (version.getMajor() >= 2) {
                            parseV2(coverage);
                        } else {
                            parseV1(coverage);
                        }
                    }

                } catch (Throwable t) {
                    this.error = true;
                }
            }
        }

        private void parseV1(Map<?, ?> coverage) {

            this.identifier = coverage.get(IDENTIFIER).toString();
            JXPathContext context = JXPathContext.newContext(coverage);
            if (coverage.get(SUPPORTED_CRS) instanceof Collection) {
                for (Object crs : ((Collection<?>) coverage.get(SUPPORTED_CRS))) {
                    IProjection projection = Projection.create(crs.toString());
                    this.supportedProjections.add(projection);
                }
            }

            this.originalEnvelope = wgs84envelope;
            this.originalProjection = Projection.getLatLon();

            for (Iterator<?> it = context.iterate("Domain/BoundingBox"); it.hasNext();) {

                Map<?, ?> bbox = (Map<?, ?>) it.next();

                /*
                 * ignore the EPSG::4326 which has swapped coordinates, and let the other specs
                 * override the defaults
                 */
                if (bbox.get(CRS) instanceof String && !bbox.get(CRS).equals("urn:ogc:def:crs:EPSG::4326")) {
                    this.originalProjection = Projection.create(bbox.get(CRS).toString());
                    double[] upperCorner = NumberUtils.doubleArrayFromString(((Map<?, ?>) bbox).get(UPPER_CORNER).toString(),
                            "\\s+");
                    double[] lowerCorner = NumberUtils.doubleArrayFromString(((Map<?, ?>) bbox).get(LOWER_CORNER).toString(),
                            "\\s+");
                    this.originalEnvelope = Envelope.create(lowerCorner[0], upperCorner[0], lowerCorner[1], upperCorner[1],
                            Projection.getLatLon());
                }
            }

            if (coverage.get(RANGE) instanceof Map) {
                Map<?, ?> range = (Map<?, ?>) coverage.get(RANGE);
                if (range.containsKey(NULL_VALUE) && !range.get(NULL_VALUE).toString().contains(INFINITY)) {
                    // TODO see if we want this on band 0
                    this.nodata.add(Double.parseDouble(range.get(NULL_VALUE).toString()));
                }
                // TODO interpolation methods and default
                // TODO Axis contains band info
            }

            // TODO the rest: Domain/GridCRS (doesn't seem to add anything useful: grid
            // shape seems absent)
            // SupportedFormat
            // Keywords (for URN metadata)
        }

        private void parseV2(Map<?, ?> cov) {

            try {

                // JXPathContext context = JXPathContext.newContext(coverage);

                this.identifier = this.name;
                this.originalEnvelope = this.wgs84envelope;
                this.originalProjection = Projection.getLatLon();

                Map<?, ?> coverage = MapUtils.get(cov, "wcs:CoverageDescriptions/wcs:CoverageDescription", Map.class);

                if (MapUtils.get(coverage, COVERAGE_ID, String.class) instanceof String) {
                    this.identifier = coverage.get(COVERAGE_ID).toString();
                }

                // true bounding box
                Map<?, ?> bounds = (Map<?, ?>) coverage.get("gml:boundedBy");
                this.originalProjection = Projection.create(MapUtils.get(bounds, "gml:Envelope/-srsName", String.class));
                double[] upperCorner = NumberUtils
                        .doubleArrayFromString(MapUtils.get(bounds, "gml:Envelope/gml:upperCorner", String.class), "\\s+");
                double[] lowerCorner = NumberUtils
                        .doubleArrayFromString(MapUtils.get(bounds, "gml:Envelope/gml:lowerCorner", String.class), "\\s+");
                this.originalEnvelope = Envelope.create(lowerCorner[0], upperCorner[0], lowerCorner[1], upperCorner[1],
                        (Projection) this.originalProjection);

                // rangeType: bands
                Map<?, ?> rangeType = MapUtils.get(coverage, RANGE_TYPE, Map.class);
                if (rangeType instanceof Map && !rangeType.isEmpty()) {

                    Object fields = MapUtils.get(rangeType, "swe:DataRecord/swe:field", Object.class);

                    if (fields instanceof Map) {
                        if (((Map<?, ?>) fields).containsKey("field")) {
                            List<?> bandefs = (List<?>) ((Map<?, ?>) fields).get("field");
                            for (Object o : bandefs) {
                                bands.add(new Band((Map<?, ?>) o));
                            }
                        } else if (((Map<?, ?>) fields).containsKey("-name")) {
                            bands.add(new Band((Map<?, ?>) fields));
                        }
                    } else if (fields instanceof List) {
                        for (Object o : (List<?>) fields) {

                            if (o instanceof Map) {
                                if (((Map<?, ?>) o).containsKey("field")) {
                                    List<?> bandefs = (List<?>) ((Map<?, ?>) o).get("field");
                                    for (Object fo : bandefs) {
                                        bands.add(new Band((Map<?, ?>) fo));
                                    }
                                } else if (((Map<?, ?>) o).containsKey("-name")) {
                                    bands.add(new Band((Map<?, ?>) o));
                                }
                            }
                        }
                    }
                }

                // resolution and CRS: domainSet
                Map<?, ?> domain = MapUtils.get(coverage, "gml:domainSet/gml:RectifiedGrid/gml:limits/gml:GridEnvelope",
                        Map.class);
                int[] gridHighRange = NumberUtils.intArrayFromString(domain.get("gml:high").toString(), "\\s+");
                int[] gridLowRange = NumberUtils.intArrayFromString(domain.get("gml:low").toString(), "\\s+");
                this.gridShape = new int[]{gridHighRange[0] - gridLowRange[0], gridHighRange[1] - gridLowRange[1]};

            } catch (Throwable t) {
                this.error = true;
                this.message = "A " + t.getClass().getCanonicalName()
                        + "  exception was raised during parsing of the GetCoverage response: " + t.getMessage();
            }

            // if (this.originalProjection.flipsCoordinates()) {
            // this.gridShape = new int[] { this.gridShape[1], this.gridShape[0] };
            // }
        }

        @Override
        public String toString() {
            return (name == null ? "NULL NAME" : name) + " "
                    + (originalEnvelope == null ? "NO ENVELOPE" : originalEnvelope.toString()) + "\n   "
                    + (getGeometry() == null ? "NO GEOMETRY" : getGeometry().toString());
        }

        /**
         * Build and return the geometry for the layer. If the layer comes from WCS 1.x it won't
         * have a grid shape. The envelope comes in the original projection unless that flips
         * coordinates, in which case EPSG:4326 is used.
         * 
         * @return the geometry.
         */
        public IGeometry getGeometry() {

            Geometry ret = Geometry.create("S2");

            if (gridShape != null) {
                ret = ret.withSpatialShape((long) gridShape[0], (long) gridShape[1]);
            }

            if (originalProjection != null && originalEnvelope != null) {
                if (originalProjection.flipsCoordinates()) {
                    // use the WGS84
                    ret = ret.withBoundingBox(wgs84envelope.getMinX(), wgs84envelope.getMaxX(), wgs84envelope.getMinY(),
                            wgs84envelope.getMaxY()).withProjection(Projection.DEFAULT_PROJECTION_CODE);
                } else {
                    ret = ret.withBoundingBox(originalEnvelope.getMinX(), originalEnvelope.getMaxX(), originalEnvelope.getMinY(),
                            originalEnvelope.getMaxY()).withProjection(originalProjection.getSimpleSRS());
                }
            } else if (wgs84envelope != null) {
                ret = ret.withBoundingBox(wgs84envelope.getMinX(), wgs84envelope.getMaxX(), wgs84envelope.getMinY(),
                        wgs84envelope.getMaxY()).withProjection(Projection.DEFAULT_PROJECTION_CODE);
            }

            if (this.temporalExtension != null) {
                // TODO add the change timepoints
                ret = ret.withTemporalStart(this.temporalExtension.getStart()).withTemporalEnd(this.temporalExtension.getEnd())
                        .withTemporalTransitions(this.temporalExtension.getTimestamps());
            }

            return ret;
        }

        /**
         * This will return null unless the layer has EO extensions, which is supported only with
         * mosaic and NetCDF layers in geoserver.
         * 
         * @return
         */
        public TemporalExtension getTemporalCoverage() {
            return this.temporalExtension;
        }

        public SpatialExtent getSpatialExtent() {

            if (wgs84envelope == null) {
                return null;
            }
            SpatialExtent ret = new SpatialExtent();
            ret.setWest(wgs84envelope.getMinX());
            ret.setEast(wgs84envelope.getMaxX());
            ret.setSouth(wgs84envelope.getMinY());
            ret.setNorth(wgs84envelope.getMaxY());
            return ret;
        }

        public String getMessage() {
            return this.message;
        }
    }

    @SuppressWarnings("unchecked")
    public WCSService(String serviceUrl, Version version) {

        this.serviceUrl = serviceUrl;
        this.version = version;
        this.cacheExpiration = Long
                /*
                 * by default cache the individual coverage info for 12h
                 */
                .parseLong(Configuration.INSTANCE.getProperty("wcs.cache.expiration",
                        "" + (LAYER_INFO_EXPIRATION_MILLISECONDS * 6)));

        if (db == null) {

            File dpath = Configuration.INSTANCE
                    .getDataPath("ogc/wcs/" + Klab.INSTANCE.getRootIdentity().getIdentityType().name().toLowerCase());
            dpath.mkdirs();

            db = DBMaker.fileDB(new File(dpath + File.separator + "wcscache.dat")).closeOnJvmShutdown().transactionEnable()
                    .make();
            wcsCache = db.treeMap("layers", Serializer.STRING, Serializer.STRING).createOrOpen();
        }

        long event = Klab.INSTANCE.notifyEventStart(EngineEvent.Type.ResourceValidation);

        try {
            // this.parser = new Parser(new WCSConfiguration());
            URL url = new URL(serviceUrl + "?service=WCS&request=getCapabilities&version=" + version);

            URLConnection con = url.openConnection();
            con.setConnectTimeout(CONNECT_TIMEOUT_MS);
            con.setReadTimeout(READ_TIMEOUT_MS);

            /*
             * save capabilities XML to file
             */
            File tempfile = File.createTempFile("wcsc", ".xml");
            try (InputStream input = con.getInputStream()) {
                FileUtils.copyInputStreamToFile(input, tempfile);
            } catch (IOException e) {
                throw new KlabIOException(e);
            }

            /*
             * hash the file and if we have a hash and it's the same, no need to call
             * describeCoverage yet.
             */
            String hash = FileUtils.getFileHash(tempfile);
            String prev = wcsCache.get(url.toString());
            boolean skipRefresh = (prev != null && hash.equals(prev));

            if (skipRefresh) {
                Logging.INSTANCE.info("WCS catalog at " + url + " is unchanged since last read: coverage cache is valid");
            } else {
                Logging.INSTANCE.info("WCS catalog at " + url + " has changed since last read: coverage cache expires in 12h");
            }

            wcsCache.put(url.toString(), hash);

            /**
             * Open the file anyway and build the layers following it, even if we have a cache and
             * the hash is the same.
             */
            try (InputStream input = new FileInputStream(tempfile)) {

                String content = IOUtils.toString(input, StandardCharsets.UTF_8);
                Map<?, ?> capabilitiesType = (Map<?, ?>) U.fromXmlMap(content);

                // JXPathContext context = JXPathContext.newContext(capabilitiesType);
                // System.out.println(MapUtils.dump(capabilitiesType) + "");
                for (Object o : MapUtils.get(capabilitiesType, "wcs:Capabilities/wcs:Contents/wcs:CoverageSummary",
                        Collection.class)) {
                    Map<String, Object> item = (Map<String, Object>) o;
                    Object name = item.get(version.getMajor() >= 2 ? COVERAGE_ID : IDENTIFIER);
                    if (name != null) {
                        identifiers.add(name.toString());
                    }
                }

                for (Object o : MapUtils.get(capabilitiesType, "wcs:Capabilities/wcs:Contents/wcs:CoverageSummary",
                        Collection.class)) {

                    Map<String, Object> item = (Map<String, Object>) o;

                    Object name = item.get(version.getMajor() >= 2 ? COVERAGE_ID : IDENTIFIER);
                    Object bbox = item.get(WGS84_BOUNDING_BOX);

                    if (name instanceof String && bbox instanceof Map) {

                        WCSLayer layer = new WCSLayer(skipRefresh);

                        layer.name = name.toString();
                        double[] upperCorner = NumberUtils.doubleArrayFromString(((Map<?, ?>) bbox).get(UPPER_CORNER).toString(),
                                "\\s+");
                        double[] lowerCorner = NumberUtils.doubleArrayFromString(((Map<?, ?>) bbox).get(LOWER_CORNER).toString(),
                                "\\s+");
                        layer.wgs84envelope = Envelope.create(lowerCorner[0], upperCorner[0], lowerCorner[1], upperCorner[1],
                                Projection.getLatLon());

                        layers.put(layer.name, layer);
                    }
                }
            } catch (IOException e) {
                errors.add(e);
                Logging.INSTANCE.error(e);
            }

        } catch (Throwable e) {
            errors.add(e);
            Logging.INSTANCE.error(e);
        } finally {
            db.commit();
            Klab.INSTANCE.notifyEventEnd(event);
        }
    }

    public URL buildRetrieveUrl(WCSLayer layer, Version version, IGeometry geometry, String interpolation) {

        Dimension space = geometry.getDimension(IGeometry.Dimension.Type.SPACE);
        URL url = null;

        if (space.shape().length != 2 || !space.isRegular()) {
            throw new IllegalArgumentException("cannot retrieve  a grid dataset from WCS in a non-grid context");
        }

        String rcrs = space.getParameters().get(Geometry.PARAMETER_SPACE_PROJECTION, String.class);
        Projection crs = Projection.create(rcrs);
        double[] extent = space.getParameters().get(Geometry.PARAMETER_SPACE_BOUNDINGBOX, double[].class);

        int xc = (int) space.shape()[0];
        int yc = (int) space.shape()[1];

        double west = extent[0];
        double east = extent[1];
        double south = extent[2];
        double north = extent[3];

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

        String s = null;

        if (version.getMajor() == 1) {
            if (version.getMinor() == 0) {

                s = serviceUrl + "?service=WCS&version=" + version + "&request=GetCoverage&coverage="
                        + layer.getRequestIdentifier() + "&bbox=" + west + "," + south + "," + east + "," + north + "&crs="
                        + crs.getSimpleSRS() 
                        + "&responseCRS=" + crs.getSimpleSRS()
                        + "&width=" + xc + "&height=" + yc
                        + "&format=" + "GeoTIFF";

            } else {

                // TODO WRONG!
                s = serviceUrl + "?service=WCS&version=" + version + "&request=GetCoverage&identifier="
                        + layer.getRequestIdentifier() + "&boundingbox=" + west + "," + south + "," + east + "," + north + ","
                        + crs.getSimpleSRS() + "&responseCRS=" + crs.getSimpleSRS() + "&width=" + xc + "&height=" + yc
                        + "&format=" + "GeoTIFF";
            }
        } else if (version.getMajor() == 2) {
            // TODO
            // http://194.66.252.155/cgi-bin/BGS_EMODnet_bathymetry/ows?service=WCS&version=2.0.1&request=GetCoverage&CoverageId=BGS_EMODNET_AegeanLevantineSeas-MCol&format=image/png&subset=lat%2834.53627,38.88686%29&subset=long%2825.43366,31.32234%29&
            // http://194.66.252.155/cgi-bin/BGS_EMODnet_bathymetry/ows?service=WCS&version=2.0.1&request=GetCoverage&CoverageId=BGS_EMODNET_AegeanLevantineSeas-MCol&format=image/png&subset=lat,http://www.opengis.net/def/crs/EPSG/0/4326%2834.53627,38.88686%29&subset=long,http://www.opengis.net/def/crs/EPSG/0/4326%2825.43366,31.32234%29&
        } else {
            throw new KlabUnsupportedFeatureException("WCS version " + version + " is not supported");
        }

        /*
         * ACHTUNG this is a 2.0 only request
         */
        if (interpolation != null) {
            s += "&interpolation=" + interpolation;
        }

        try {
            url = new URL(s);
        } catch (MalformedURLException e) {
            throw new KlabInternalErrorException(e);
        }

        return url;
    }

    public boolean containsIdentifier(String string) {
        return identifiers.contains(string);
    }

}