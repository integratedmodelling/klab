package org.integratedmodelling.klab.components.geospace.processing.osm;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.communication.client.Client;
import org.integratedmodelling.klab.components.geospace.extents.Envelope;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.rest.SpatialExtent;
import org.integratedmodelling.klab.utils.Escape;

import com.slimjars.dist.gnu.trove.map.TLongObjectMap;
import com.vividsolutions.jts.geom.Geometry;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.dataset.InMemoryMapDataSet;
import de.topobyte.osm4j.core.dataset.MapDataSetLoader;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.util.OsmModelUtil;
import de.topobyte.osm4j.geometry.GeometryBuilder;
import de.topobyte.osm4j.xml.dynsax.OsmXmlIterator;

public enum Nominatim {

	INSTANCE;

	public final static String GEOMETRY_FIELD = "the_geom";

	Client client = Client.create();
	Client universal = Client.createUniversalJSON();

	// TODO use ours
	private String[] OSMNAMES_KEYS = { "dgb7TgC5zR0YpsAqbEgb" };

	// TODO add ours
	private String[] OSMNAMES_URL = { "https://search.osmnames.org/q/" };

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

	/**
	 * Return the parsed OSM data for the passed location. The geometry will be in
	 * the "the_geom" field, as a hommage to silly GIS conventions, set in the
	 * {@link #GEOMETRY_FIELD} constant for decency.
	 * 
	 * @param location
	 * @return
	 */
	public Map<String, Object> getData(Location location) {

		String query = location.getRetrieveUrl();
		Map<String, Object> properties = new HashMap<>();

		try (InputStream input = new URL(query).openStream()) {

			OsmIterator iterator = new OsmXmlIterator(input, false);
			InMemoryMapDataSet data = MapDataSetLoader.read(iterator, false, false, true);

			TLongObjectMap<OsmRelation> relations = data.getRelations();

			if (relations.isEmpty()) {
				return null;
			}

			OsmRelation relation = relations.valueCollection().iterator().next();

			Geometry polygon = new GeometryBuilder().build(relation, data);

			Map<String, String> tags = OsmModelUtil.getTagsAsMap(relation);
			for (String key : tags.keySet()) {
				properties.put(key, tags.get(key));
			}
			properties.put(GEOMETRY_FIELD, polygon);

		} catch (Throwable e) {
			return null;
		}

		return properties;
	}

	public String geocode(IEnvelope envelope) {

		IEnvelope env = envelope.transform(Projection.getLatLon(), true);
		String url = "https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat=" + env.getCenterCoordinates()[1]
				+ "&lon=" + env.getCenterCoordinates()[0] + "&zoom=" + env.getScaleRank();

		String ret = null;
		try {
			Map<?, ?> res = client.get(url, Map.class);
			if (res != null && res.containsKey("display_name")) {
				ret = res.get("display_name").toString();
			} else if (res != null && res.containsKey("name")) {
				ret = res.get("name").toString();
			}
		} catch (Throwable t) {
			// shut up
		}

		return ret == null ? "Region of interest" : ret;
	}

	public String geocode(SpatialExtent region) {
		return geocode(Envelope.create(region.getEast(), region.getWest(), region.getSouth(), region.getNorth(),
				Projection.getLatLon()));
	}

	public static void main(String[] args) {
		INSTANCE.lookup("france");
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
			return "klab:osm:osm:" + osm_type + "#" + osm_id; 
		}
		
		@Override
		public String toString() {
			return getRetrieveUrl() + ": Location [osm_id=" + osm_id + ", lon=" + lon + ", boundingbox=" + boundingbox
					+ ", type=" + type + ", lat=" + lat + ", name=" + name + "]";
		}

		public String getOsm_type() {
			return osm_type;
		}

		public void setOsm_type(String osm_type) {
			this.osm_type = osm_type;
		}

	}
}
