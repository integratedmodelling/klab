package org.integratedmodelling.klab.components.geospace.routing;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.utils.PolylineDecoder;

/*
*  Ridiculously big class to deserialize responses from the valhalla.test.Valhalla server. Only implemented a deserializer for Matrix
*  requests.
* */

public class ValhallaOutputDeserializer {

	public String json;

	public ValhallaOutputDeserializer(String json) {
		this.json = json;
	}

	public ValhallaOutputDeserializer() {
		this.json = "";
	}

	public void setJson(String json) {
		this.json = json;
	}

	public Matrix deserializeMatrixOutput() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(this.json, Matrix.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public OptimizedRoute deserializeOptimizedRoutes() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(this.json, OptimizedRoute.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}

	static public class Matrix {

		public String algorithm;
		public String units;
		public ArrayList<ArrayList<Coordinates>> sources;
		public ArrayList<ArrayList<Coordinates>> targets;
		public Collection<Collection<PairwiseDistance>> sourcesToTargets;

		@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
		public Matrix(@JsonProperty("algorithm") String algorithm, @JsonProperty("units") String units,
				@JsonProperty("sources") ArrayList<ArrayList<Coordinates>> sources,
				@JsonProperty("targets") ArrayList<ArrayList<Coordinates>> targets,
				@JsonProperty("sources_to_targets") Collection<Collection<PairwiseDistance>> sourcesToTargets) {
			this.algorithm = algorithm;
			this.units = units;
			this.sources = sources;
			this.targets = targets;
			this.sourcesToTargets = sourcesToTargets;
		}

		@JsonProperty("algorithm")
		public String algorithm() {
			return algorithm;
		}

		@JsonProperty("units")
		public String units() {
			return units;
		}

		@JsonProperty("sources")
		public ArrayList<ArrayList<Coordinates>> sources() {
			return sources;
		}

		@JsonProperty("targets")
		public ArrayList<ArrayList<Coordinates>> targets() {
			return targets;
		}

		@JsonProperty("sources_to_targets")
		public Collection<Collection<PairwiseDistance>> sourcesToTargets() {
			return sourcesToTargets;
		}

		public String getAlgorithm() {
			return this.algorithm;
		}

		public String getUnits() {
			return this.algorithm;
		}

		public List<Map<String, Double>> getSources() {
			return this.sources.get(0).stream().map(Coordinates::exportAsMap).collect(Collectors.toList());
		}

		public List<Map<String, Double>> getTargets() {
			return this.sources.get(0).stream().map(Coordinates::exportAsMap).collect(Collectors.toList());
		}

		public List<Map<String, Number>> getAdjacencyList() {
			return this.sourcesToTargets.stream().flatMap(x -> x.stream().map(PairwiseDistance::exportAsMap))
					.collect(Collectors.toList());
		}

		public static class Coordinates {
			public double lon;
			public double lat;

			@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
			public Coordinates(@JsonProperty("lon") double lon, @JsonProperty("lat") double lat) {
				this.lon = lon;
				this.lat = lat;
			}

			@JsonProperty("lon")
			public double lon() {
				return lon;
			}

			@JsonProperty("lat")
			public double lat() {
				return lat;
			}

			public Map<String, Double> exportAsMap() {
				return Map.of("lon", lon, "lat", lat);
			}
		}

		public static class PairwiseDistance {
			public double distance;
			public double time;
			public int targetId;
			public int sourceId;

			@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
			public PairwiseDistance(@JsonProperty("distance") double distance, @JsonProperty("time") double time,
					@JsonProperty("to_index") int targetId, @JsonProperty("from_index") int sourceId) {
				this.distance = distance;
				this.time = time;
				this.targetId = targetId;
				this.sourceId = sourceId;
			}

			@JsonProperty("distance")
			public double distance() {
				return distance;
			}

			@JsonProperty("time")
			public double time() {
				return time;
			}

			@JsonProperty("to_index")
			public int targetId() {
				return targetId;
			}

			@JsonProperty("from_index")
			public int sourceId() {
				return sourceId;
			}

			public Map<String, Number> exportAsMap() {
				return Map.of("source", sourceId, "target", targetId, "distance", distance, "time", time);
			}
		}
	}

	static public class OptimizedRoute {
		public Trip trip;

		public IShape getPath() {
			List<String> polylinePath = getPolylineEncodedPath();
			Shape trajectory = PolylineDecoder.decode(polylinePath, 1E6, true);
			return trajectory;
		}

		public List<String> getPolylineEncodedPath() {
			return this.trip.getPath();
		}

		public List<Map<String, Number>> getWaypoints() {
			return this.trip.getWaypointCoordinates();
		}

		public Map<String, Object> getSummaryStatistics() {
			return this.trip.summary.exportSummaryStatisticsAsMap();
		}

		public List<Map<String, Object>> getSummaryStatisticsByLeg() {
			return this.trip.legs.stream().map(navigation -> navigation.summary.exportSummaryStatisticsAsMap())
					.collect(Collectors.toList());
		}

		public String getUnits() {
			return this.trip.units;
		}

		public boolean isPossible() {
			return this.trip.status == 0;
		}

		public static class Trip {
			public List<Location> locations;
			public List<Navigation> legs;
			public TripSummary summary;
			public String statusMessage;
			public Integer status;
			public String units;
			public String language;

			@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
			public Trip(@JsonProperty("locations") List<Location> locations,
					@JsonProperty("legs") List<Navigation> legs, @JsonProperty("summary") TripSummary summary,
					@JsonProperty("status_message") String statusMessage, @JsonProperty("status") Integer status,
					@JsonProperty("units") String units, @JsonProperty("language") String language) {
				this.locations = locations;
				this.legs = legs;
				this.summary = summary;
				this.statusMessage = statusMessage;
				this.status = status;
				this.units = units;
				this.language = language;
			}

			@JsonProperty("locations")
			public List<Location> locations() {
				return locations;
			}

			@JsonProperty("legs")
			public List<Navigation> legs() {
				return legs;
			}

			@JsonProperty("summary")
			public TripSummary summary() {
				return summary;
			}

			@JsonProperty("status_message")
			public String statusMessage() {
				return statusMessage;
			}

			@JsonProperty("status")
			public Integer status() {
				return status;
			}

			@JsonProperty("units")
			public String units() {
				return units;
			}

			@JsonProperty("language")
			public String language() {
				return language;
			}

			public List<Map<String, Number>> getWaypointCoordinates() {
				return locations.stream().map(Location::exportCoordinatesAsMap).collect(Collectors.toList());
			}

			public Map<String, Object> exportTripSummaryAsMap() {
				return this.summary.exportAsMap();
			}

			public List<String> getPath() {
				return legs.stream().map(navigation -> navigation.path).collect(Collectors.toList());
			}

			public static class TripSummary {
				public boolean hasTimeRestrictions;
				public boolean hasToll;
				public boolean hasHighway;
				public boolean hasFerry;
				public Double minLat;
				public Double minLon;
				public Double maxLat;
				public Double maxLon;
				public Double time;
				public Double length;
				public Double cost;

				@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
				public TripSummary(@JsonProperty("hast_time_restrictions") boolean hasTimeRestrictions,
						@JsonProperty("has_toll") boolean hasToll, @JsonProperty("has_highway") boolean hasHighway,
						@JsonProperty("has_ferry") boolean hasFerry, @JsonProperty("min_lat") Double minLat,
						@JsonProperty("min_lon") Double minLon, @JsonProperty("max_lat") Double maxLat,
						@JsonProperty("max_lon") Double maxLon, @JsonProperty("time") Double time,
						@JsonProperty("length") Double length, @JsonProperty("cost") Double cost) {
					this.hasTimeRestrictions = hasTimeRestrictions;
					this.hasToll = hasToll;
					this.hasFerry = hasFerry;
					this.hasHighway = hasHighway;
					this.minLat = minLat;
					this.minLon = minLon;
					this.maxLat = maxLat;
					this.maxLon = maxLon;
					this.time = time;
					this.length = length;
					this.cost = cost;
				}

				@JsonProperty("has_time_restrictions")
				public boolean hasTimeRestrictions() {
					return hasTimeRestrictions;
				}

				@JsonProperty("has_toll")
				public boolean hasToll() {
					return hasToll;
				}

				@JsonProperty("hasFerry")
				public boolean hasFerry() {
					return hasFerry;
				}

				@JsonProperty("hasHighway")
				public boolean hasHighway() {
					return hasHighway;
				}

				@JsonProperty("min_lon")
				public Double minLon() {
					return minLon;
				}

				@JsonProperty("min_lat")
				public Double minLat() {
					return minLat;
				}

				@JsonProperty("max_lon")
				public Double maxLon() {
					return maxLon;
				}

				@JsonProperty("max_lat")
				public Double maxLat() {
					return maxLat;
				}

				@JsonProperty("time")
				public Double time() {
					return time;
				}

				@JsonProperty("length")
				public Double lenght() {
					return length;
				}

				@JsonProperty("cost")
				public Double cost() {
					return cost;
				}

				public Map<String, Object> exportAsMap() {
					return Map.ofEntries(Map.entry("has_time_restrictions", hasTimeRestrictions),
							Map.entry("has_toll", hasToll), Map.entry("has_highway", hasHighway),
							Map.entry("has_ferry", hasFerry), Map.entry("min_lat", minLat),
							Map.entry("min_lon", minLon), Map.entry("max_lat", maxLat), Map.entry("max_lon", maxLon),
							Map.entry("time", time), Map.entry("length", length), Map.entry("cost", cost));
				}

				public Map<String, Object> exportSummaryStatisticsAsMap() {
					return Map.of("time", time, "length", length, "cost", cost);
				}
			}

			public static class Navigation {
				public List<Object> maneuvers;
				public TripSummary summary;
				public String path;

				@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
				public Navigation(@JsonProperty("maneuvers") List<Object> maneuvers,
						@JsonProperty("summary") TripSummary summary, @JsonProperty("shape") String path) {
					this.maneuvers = maneuvers;
					this.summary = summary;
					this.path = path;
				}

				@JsonProperty("maneuvers")
				public List<Object> maneuvers() {
					return maneuvers;
				}

				@JsonProperty("summary")
				public TripSummary summary() {
					return summary;
				}

				@JsonProperty("shape")
				public String path() {
					return path;
				}

				public Map<String, Object> exportTripSummaryAsMap() {
					return this.summary.exportAsMap();
				}

			}

			public static class Location {
				public String type;
				public double lat;
				public double lon;
				public String sideOfStreet;
				public Integer originalIndex;

				@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
				public Location(@JsonProperty("type") String type, @JsonProperty("lat") double lat,
						@JsonProperty("lon") double lon, @JsonProperty("side_of_street") String sideOfStreet,
						@JsonProperty("original_index") Integer originalIndex) {
					this.type = type;
					this.lon = lon;
					this.lat = lat;
					this.sideOfStreet = sideOfStreet;
					this.originalIndex = originalIndex;
				}

				@JsonProperty("type")
				public String type() {
					return type;
				}

				@JsonProperty("lon")
				public double lon() {
					return lon;
				}

				@JsonProperty("lat")
				public double lat() {
					return lat;
				}

				@JsonProperty("side_of_street")
				public String sideOfStreet() {
					return sideOfStreet;
				}

				@JsonProperty("original_index")
				public Integer originalIndex() {
					return originalIndex;
				}

				public Map<String, Number> exportCoordinatesAsMap() {
					return Map.of("lon", lon, "lat", lat, "id", originalIndex);
				}

				public Map<String, Object> exportAsMap() {
					return Map.of("type", type, "lon", lon, "lat", lat, "side_of_street", sideOfStreet,
							"original_index", originalIndex);
				}
			}

		}

	}

}
