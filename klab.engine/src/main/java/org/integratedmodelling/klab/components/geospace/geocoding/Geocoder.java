package org.integratedmodelling.klab.components.geospace.geocoding;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.communication.client.Client;
import org.integratedmodelling.klab.components.geospace.extents.Envelope;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.components.time.extents.TimeInstant;
import org.integratedmodelling.klab.data.encoding.VisitingDataBuilder;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.rest.ScaleReference;
import org.integratedmodelling.klab.rest.SpatialExtent;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Escape;
import org.integratedmodelling.klab.utils.Parameters;
import org.locationtech.jts.geom.Geometry;

import com.google.common.util.concurrent.RateLimiter;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.dataset.InMemoryMapDataSet;
import de.topobyte.osm4j.core.dataset.MapDataSetLoader;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.core.model.util.OsmModelUtil;
import de.topobyte.osm4j.geometry.GeometryBuilder;
import de.topobyte.osm4j.geometry.MissingEntitiesStrategy;
import de.topobyte.osm4j.geometry.MissingWayNodeStrategy;
import de.topobyte.osm4j.geometry.RegionBuilder;
import de.topobyte.osm4j.geometry.RegionBuilderResult;
import de.topobyte.osm4j.xml.dynsax.OsmXmlIterator;

public enum Geocoder {

    INSTANCE;

    public final static String GEOMETRY_FIELD = "the_geom";

    Client client = Client.create();
    /*
     * Fast client is used for nominating service, we don't want to wait too much for it. TODO
     * implement an asynchronous way to update context information
     */
    Client fastClient = Client.createCustomTimeoutClient(2000);

    Client universal = Client.createUniversalJSON();

    // TODO use ours
    public String[] OSMNAMES_KEYS = {"dgb7TgC5zR0YpsAqbEgb"};

    // TODO add ours
    public String[] OSMNAMES_URL = {"https://search.osmnames.org/q/"};

    // TODO add ours
    public String[] OSM_API_URLS = {"https://www.openstreetmap.org/api/0.6"};

    // TODO add/fix/check. Other like this could be used for major features, protected areas etc
    public String[] TERRESTRIAL_REGIONS_URNS = {"local:ferdinando.villa:im.data.global:admin.countries.un.split#presence=true"};

    public static final String DEFAULT_GEOCODING_STRATEGY = "Map boundaries";
    public static final String WATERSHED_GEOCODING_STRATEGY = "River basin";
    public static final String ADMIN_GEOCODING_STRATEGY = "Administrative region";
    public static final String EEZ_GEOCODING_STRATEGY = "Exclusive Economic Zone";
    public static final String RANDOM_GEOCODING_STRATEGY = "I'm feeling stupid";

    Map<String, GeocodingService> services = Collections.synchronizedMap(new HashMap<>());

    private Geocoder() {
        services.put(DEFAULT_GEOCODING_STRATEGY, new OSMNamesGeocodingService(0.75));
        services.put(ADMIN_GEOCODING_STRATEGY,
                new ResourceGeocodingService("im.geo:gadm.un:boundaries.split:administrative", 0.5));
        services.put(WATERSHED_GEOCODING_STRATEGY, new ResourceGeocodingService("im.geo:fao:hydrological:watersheds.named", 0.5));
        services.put(EEZ_GEOCODING_STRATEGY, new ResourceGeocodingService("im.data:emodnet:administrative:eez.boundaries", 0.5));
        // services.put(RANDOM_GEOCODING_STRATEGY, new RandomGeocodingService(0.5));
        // TODO other services
    }

    // TODO this should be configurable. Also we must provide all this machinery as
    // a remote resource using its own OSM mirror.
    public static final String[] OVERPASS_URLS = {"https://knowledge.integratedmodelling.org/overpass/api/interpreter",
            "http://overpass-api.de/api/interpreter"};

    /**
     * True if the passed envelope intersects at least one shape in any of the resources set in
     * {@link #TERRESTRIAL_REGIONS_URNS}.
     * 
     * @param envelope
     * @return
     */
    public boolean isTerrestrial(IEnvelope envelope) {
        IScale geometry = Scale.create(envelope.asShape(), Time.create(TimeInstant.create().getYear() - 1));
        for (String urn : TERRESTRIAL_REGIONS_URNS) {
            IKlabData data = Resources.INSTANCE.getResourceData(urn, geometry, Klab.INSTANCE.getRootMonitor());
            if (data == null || data.hasErrors()) {
                continue;
            }
            return data.getMetadata().get("presence") instanceof Boolean && ((Boolean)data.getMetadata().get("presence"));
        }
        throw new KlabIOException("cannot establish terrestrial nature of envelope " + envelope);
    }

    public List<Location> lookup(String query) {

        if (query == null || query.length() < 4) {
            return new ArrayList<>();
        }

        query = Escape.forURL(query);
        OsmNamesResult result = null;

        for (int i = 0; i < OSMNAMES_URL.length; i++) {
            try {
                result = universal.get(OSMNAMES_URL[i] + query + ".js?key=" + OSMNAMES_KEYS[i], OsmNamesResult.class);
                break;
            } catch (Throwable e) {
                // continue to next URL
            }
        }

        return result == null ? new ArrayList<>() : result.getResults();
    }

    public IParameters<String> getData(String type, String id) {

        String query = null;
        switch(type) {
        case "node":
            query = "node(" + id + ");\n(._; >;);\nout;";
            break;
        case "relation":
            query = "rel(" + id + ");\n(._; >;);\nout;";
            break;
        case "way":
            query = "way(" + id + ");\n(._; >;);\nout;";
            break;
        }

        if (query == null) {
            throw new IllegalArgumentException("cannot retrieve objects of type " + type + " from OpenStreetMap");
        }

        List<IParameters<String>> result = queryOverpass(query, type);

        return result.isEmpty() ? null : result.get(0);

    }

    public List<IParameters<String>> queryOverpass(String query, String type) {

        List<IParameters<String>> ret = new ArrayList<>();

        for (String ourl : OVERPASS_URLS) {

            String url = ourl + "?data=" + Escape.forURL(query);

            try (InputStream input = new URL(url).openStream()) {

                OsmIterator iterator = new OsmXmlIterator(input, true);
                InMemoryMapDataSet data = MapDataSetLoader.read(iterator, true, true, true);

                if (type.equals("node") && !data.getNodes().isEmpty()) {
                    GeometryBuilder geometryBuilder = new GeometryBuilder();
                    for (OsmNode node : data.getNodes().valueCollection()) {
                        Geometry point = geometryBuilder.build(node);
                        if (point.isEmpty()) {
                            continue;
                        }
                        Parameters<String> pdata = Parameters.create();
                        pdata.putAll(OsmModelUtil.getTagsAsMap(node));
                        pdata.put(GEOMETRY_FIELD, point);
                        ret.add(pdata);

                    }
                } else if (type.equals("relation") && !data.getRelations().isEmpty()) {

                    RegionBuilder regionBuilder = new RegionBuilder();
                    regionBuilder.setMissingEntitiesStrategy(MissingEntitiesStrategy.BUILD_PARTIAL);
                    regionBuilder.setMissingWayNodeStrategy(MissingWayNodeStrategy.OMIT_VERTEX_FROM_POLYLINE);

                    for (OsmRelation rel : data.getRelations().valueCollection()) {

                        RegionBuilderResult region = regionBuilder.build(rel, data);
                        Geometry polygon = region.getMultiPolygon();
                        if (polygon.isEmpty()) {
                            Logging.INSTANCE.warn("empty polygon for query " + url);
                            continue;
                        }
                        polygon = polygon.buffer(0);
                        Parameters<String> pdata = Parameters.create();
                        pdata.putAll(OsmModelUtil.getTagsAsMap(rel));
                        pdata.put(GEOMETRY_FIELD, polygon);
                        ret.add(pdata);
                    }
                } else if (type.equals("way") && !data.getWays().isEmpty()) {
                    GeometryBuilder geometryBuilder = new GeometryBuilder();
                    for (OsmWay way : data.getWays().valueCollection()) {
                        Geometry line = geometryBuilder.build(way, data);
                        if (line.isEmpty()) {
                            continue;
                        }
                        Parameters<String> pdata = Parameters.create();
                        pdata.putAll(OsmModelUtil.getTagsAsMap(way));
                        pdata.put(GEOMETRY_FIELD, line);
                        ret.add(pdata);
                    }
                }

                break;

            } catch (Throwable e) {
                // warn and move on
                Logging.INSTANCE.warn(e);
            }
        }

        return ret;
    }

    public IShape geocodeToShape(IEnvelope envelope, String strategy, IMonitor monitor) {

        /*
         * TODO use cache before everything - Guava CacheBuilder.newBuilder()
         */

        GeocodingService service = services.get(strategy == null ? DEFAULT_GEOCODING_STRATEGY : strategy);
        if (service != null) {
            IShape shape = service.geocode(envelope, monitor);
            if (shape != null) {
                return shape;
            }
        }
        return null;
    }

    public String geocode(IEnvelope envelope, String strategy, String defaultWhenBusy, IMonitor monitor) {

        /*
         * TODO use cache before everything - Guava CacheBuilder.newBuilder()
         */

        GeocodingService service = services.get(strategy == null ? DEFAULT_GEOCODING_STRATEGY : strategy);
        if (service != null) {
            if (service.getRateLimiter().acquire() == 0) {
                IShape shape = service.geocode(envelope, monitor);
                if (shape != null) {
                    return shape.getMetadata().get(IMetadata.DC_DESCRIPTION).toString();
                }
            }
        }
        return defaultWhenBusy;
    }

    public IShape geocodeToShape(SpatialExtent region, String strategy, IMonitor monitor) {
        return geocodeToShape(
                Envelope.create(region.getEast(), region.getWest(), region.getSouth(), region.getNorth(), Projection.getLatLon()),
                strategy, monitor);
    }

    public String geocode(SpatialExtent region, String strategy, String defaultWhenBusy, IMonitor monitor) {

        return geocode(
                Envelope.create(region.getEast(), region.getWest(), region.getSouth(), region.getNorth(), Projection.getLatLon()),
                strategy, defaultWhenBusy, monitor);
    }

    public static void main(String[] args) {
        for (Location location : INSTANCE.lookup("france")) {
            System.out.println(location.getURN() + " -- " + location.getDescription() + ": " + location.getBoundingbox());
        }
    }

    public static class OsmNamesResult {

        private int count;
        private int nextIndex;
        private int startIndex;
        private int totalResults;
        private List<Location> results;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getNextIndex() {
            return nextIndex;
        }

        public void setNextIndex(int nextIndex) {
            this.nextIndex = nextIndex;
        }

        public int getStartIndex() {
            return startIndex;
        }

        public void setStartIndex(int startIndex) {
            this.startIndex = startIndex;
        }

        public int getTotalResults() {
            return totalResults;
        }

        public void setTotalResults(int totalResults) {
            this.totalResults = totalResults;
        }

        public List<Location> getResults() {
            return results;
        }

        public void setResults(List<Location> results) {
            this.results = results;
        }

    }

    public static class Location {

        private String wikidata;
        private long rank;
        private String county;
        private String street;
        private String country_code;
        private String osm_id;
        private String housenumbers;
        private int id;
        private String city;
        private double lon;
        private String state;
        private List<Double> boundingbox;
        private String type;
        private String osm_type;
        private double importance;
        private double lat;
        private String name;
        private String country;
        private int place_rank;
        private String alternative_names;
        private String display_name;

        public String getWikidata() {
            return wikidata;
        }

        public void setWikipedia(String wikidata) {
            this.wikidata = wikidata;
        }

        public long getRank() {
            return rank;
        }

        public void setRank(long rank) {
            this.rank = rank;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getCountry_code() {
            return country_code;
        }

        public void setCountry_code(String country_code) {
            this.country_code = country_code;
        }

        public String getOsm_id() {
            return osm_id;
        }

        public void setOsm_id(String osm_id) {
            this.osm_id = osm_id;
        }

        public String getHousenumbers() {
            return housenumbers;
        }

        public void setHousenumbers(String housenumbers) {
            this.housenumbers = housenumbers;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        /**
         * Bounding box reported is X1, Y1, X2, Y2 with X = longitude.
         * 
         * @return
         */
        public List<Double> getBoundingbox() {
            return boundingbox;
        }

        public void setBoundingbox(List<Double> boundingbox) {
            this.boundingbox = boundingbox;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public double getImportance() {
            return importance;
        }

        public void setImportance(double importance) {
            this.importance = importance;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public int getPlace_rank() {
            return place_rank;
        }

        public void setPlace_rank(int place_rank) {
            this.place_rank = place_rank;
        }

        public String getAlternative_names() {
            return alternative_names;
        }

        public void setAlternative_names(String alternative_names) {
            this.alternative_names = alternative_names;
        }

        public String getRetrieveUrl() {
            return "https://www.openstreetmap.org/api/0.6/" + osm_type + "/" + osm_id;
        }

        public String getURN() {
            return "klab:osm:" + osm_type + ":" + osm_id;
        }

        @Override
        public String toString() {
            return getRetrieveUrl() + ": Location [osm_id=" + osm_id + ", lon=" + lon + ", boundingbox=" + boundingbox + ", type="
                    + type + ", lat=" + lat + ", name=" + name + "]";
        }

        public String getOsm_type() {
            return osm_type;
        }

        public void setOsm_type(String osm_type) {
            this.osm_type = osm_type;
        }

        public String getDescription() {
            return getDisplay_name() + " (" + getType() + ")";
        }

        public String getDisplay_name() {
            return display_name;
        }

        public void setDisplay_name(String display_name) {
            this.display_name = display_name;
        }

    }

    public boolean isRetrievalAccessible() {
        // TODO periodically ping OSM URLs
        return true;
    }

    public boolean isGeocodingAccessible() {
        // TODO periodically ping Nominatim URLs
        return true;
    }

    public RateLimiter getRateLimiter(String strategy) {
        GeocodingService service = services.get(strategy == null ? DEFAULT_GEOCODING_STRATEGY : strategy);
        if (service != null) {
            return service.getRateLimiter();
        }
        return null;
    }

    public ScaleReference finalizeShape(ScaleReference scale, IMonitor monitor) {

        if (scale.getFeatureUrn() != null) {

            org.integratedmodelling.klab.common.Geometry g = org.integratedmodelling.klab.common.Geometry.createGrid(scale);

            IKlabData data = Resources.INSTANCE.getResourceData(scale.getFeatureUrn(), new VisitingDataBuilder(),
                    IArtifact.Type.OBJECT, "result", Scale.create(g), monitor);

            if (data.getArtifact() != null) {
                IGeometry geometry = data.getArtifact().getGeometry();
                if (geometry != null) {
                    IShape shape = geometry instanceof IScale
                            ? ((IScale) geometry).getSpace().getShape()
                            : Scale.create(geometry).getSpace().getShape();
                    if (shape != null) {
                        scale.setShape(((Shape) shape).getJTSGeometry().toString());
                        scale.setFeatureUrn(null);
                        // don't simplify further
                        scale.getMetadata().put("simplified", "true");
                    }
                }
            }

        }
        return scale;
    }
}
